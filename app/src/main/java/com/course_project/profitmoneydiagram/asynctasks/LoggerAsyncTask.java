package com.course_project.profitmoneydiagram.asynctasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.course_project.profitmoneydiagram.R;
import com.course_project.profitmoneydiagram.api.MarketApi;
import com.course_project.profitmoneydiagram.model.DealListData;
import com.course_project.profitmoneydiagram.network.lab.LabResponse;
import com.course_project.profitmoneydiagram.ui.DealListAdapter;
import com.course_project.profitmoneydiagram.ui.MainActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Creates new thread, gets data from logger (https://logger-mongo.azurewebsites.net) and displays it.
public class LoggerAsyncTask extends AsyncTask<Void, LabResponse, LabResponse> {

    private static final String LOGTAG = "LoggerAsyncTask";
    private static int updateRateSeconds = 10;

    private WeakReference<MainActivity> activityReference;
    private SharedPreferences sp;
    private String currencyPair;
    private String secondCurrency; //Second currency in the pair.

    public LoggerAsyncTask(MainActivity activity) {

        this.activityReference = new WeakReference<>(activity);

        sp = PreferenceManager.getDefaultSharedPreferences(activityReference.get());

        Log.d(LOGTAG, "Logger ASYNCTASK STARTED");
    }

    //Show a text message on the screan.
    private void showToast(String msg) {
        Toast toast = Toast.makeText(activityReference.get().getApplicationContext(),
                msg,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //Gets updateRateSeconds value from settings.
    private void updateUpdateRateSeconds() {

        try {
            updateRateSeconds = Integer.parseInt(sp.getString("update_rate", "10"));
        } catch (java.lang.RuntimeException e) {
            Log.e(LOGTAG, "Wrong formatted string: update rate");
            updateRateSeconds = 10;
        }
    }

    //Get Currency pair from settings.
    private void updateCurrencyPair() {

        currencyPair = "BTC/USD"; //Avoiding the NullPointerException during the first launch.
        currencyPair = sp.getString("currency_pares", "BTC/USD");
        secondCurrency = currencyPair.split("/")[1];
    }


    @Override
    protected LabResponse doInBackground(Void... params) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://logger-mongo.azurewebsites.net") //Base url part.
                .addConverterFactory(GsonConverterFactory.create())
                //Converter for converting JSON's to objects.
                .build();

        MarketApi api = retrofit.create(MarketApi.class);

        Call<List<LabResponse>> responseCall = null;
        Response<List<LabResponse>> res;
        LabResponse labResponse = null;

        //Main loop.
        while (!isCancelled()) {

            //Check if settings were changed.
            updateUpdateRateSeconds();
            updateCurrencyPair();

            //Try to get data from Internet.
            try {
                labResponse = null; //Delete previous response.

                if (currencyPair.equals("BTC/USD")) {
                    responseCall = api.getLoggerResponse("btc_usd");
                } else if (currencyPair.equals("ETH/USD")) {
                    responseCall = api.getLoggerResponse("eth_usd");
                }
                res = responseCall.execute();
                labResponse = res.body().get(0);
            } catch (IOException e) {
                Log.e(LOGTAG, e.toString());
            }

            //Display data.
            publishProgress(labResponse);

            //Stop executing if user has changed settings and data should be updated
            // without using logger.
            if (sp.getBoolean("extract_data_directly", false)) {
                Log.d(LOGTAG, "CANCELLED");
                cancel(true);
                activityReference.get().startSoloAsyncTask();
            }

            //Wait before next data updating.
            try {
                TimeUnit.SECONDS.sleep(updateRateSeconds);
            } catch (InterruptedException e) {
                Log.d(LOGTAG, e.getMessage());
            }
        }
        return labResponse;
    }


    @Override
    protected void onProgressUpdate(LabResponse... params) {

        super.onProgressUpdate(params);
        LabResponse response = params[0];
        Log.e(LOGTAG, "LoggerAsyncTask RUNNING");

        if (response == null) {
            showToast("Bad Internet connection");
            return;
        }

        //Display profit points on the diagram.
        LineChart chart = (LineChart) activityReference.get().findViewById(R.id.diagram);
        //List of points of the chart.
        List<Entry> chartEntries = new ArrayList<>();
        //Put all points in the list.
        for (int i = 0; i < response.getAmountPoints().size(); ++i) {
            chartEntries.add(new Entry(response.getAmountPoints().get(i).floatValue()
                    , response.getProfitPoints().get(i).floatValue()));
        }
        //Make a DataSet with ordinary points.
        LineDataSet ds = new LineDataSet(chartEntries, "Profit/Money Diagram");
        ds.setColor(R.color.colorPrimaryDark);
        ds.setCircleColors(activityReference.get()
                .getResources().getColor(R.color.diagramCircleOrdinary));

        //Make DataSet with optimal point.
        Float optimalAmount = response.getOptimalPoint().getAmount().floatValue();
        Float optimalProfit = response.getOptimalPoint().getProfit().floatValue();

        List<Entry> optimalChartEntries = new ArrayList<>();
        optimalChartEntries.add(new Entry(optimalAmount,optimalProfit));
        LineDataSet ds2 = new LineDataSet(optimalChartEntries, "");

        ds2.setColor(R.color.colorPrimaryDark);
        ds2.setCircleColors(activityReference.get()
                .getResources().getColor(R.color.diagramCircleOptimal));

        LineDataSet[] lineDataSets = new LineDataSet[2];
        lineDataSets[0] = ds;
        lineDataSets[1] = ds2;
        LineData ld = new LineData(lineDataSets);

        chart.setData(ld);
        chart.getDescription().setText("Horizontal: amount; Vertical: profit");
        chart.getLegend().setEnabled(false);
        chart.invalidate();

        //Display optimal profit.
        ((TextView) activityReference.get().findViewById(R.id.profit_string))
                .setText("Profit: "+(Math.round(optimalProfit * 100) / 100.0)+ " " +secondCurrency);
        //Display optimal amount.
        ((TextView) activityReference.get().findViewById(R.id.amount_string))
                .setText("Amount: "+(Math.round(optimalAmount * 100) / 100.0)+ " " +secondCurrency);
        //Display current currency pair.
        ((TextView) activityReference.get().findViewById(R.id.currency_pair)).setText(currencyPair);

        //Prepare data about deals for displaying.
        DealListData dldata = new DealListData(response.getOrders());
        //Display it.
        RecyclerView list = activityReference.get().findViewById(R.id.iknowdaway);
        LinearLayoutManager llm = new LinearLayoutManager(activityReference.get());

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        list.setAdapter(new DealListAdapter(dldata));

    }

}