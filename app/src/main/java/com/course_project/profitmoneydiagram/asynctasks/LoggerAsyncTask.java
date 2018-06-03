package com.course_project.profitmoneydiagram.asynctasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.course_project.profitmoneydiagram.R;
import com.course_project.profitmoneydiagram.api.MarketApi;
import com.course_project.profitmoneydiagram.model.CompiledOrderBook;
import com.course_project.profitmoneydiagram.model.DealListData;
import com.course_project.profitmoneydiagram.model.OutputDataSet;
import com.course_project.profitmoneydiagram.network.kucoin.KucoinResponse;
import com.course_project.profitmoneydiagram.network.lab.LabResponse;
import com.course_project.profitmoneydiagram.ui.DealListAdapter;
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


public class LoggerAsyncTask extends AsyncTask<Void, LabResponse, LabResponse> {

    private static final String LOGTAG = "LoggerAsyncTask";
    private static int updateRateSeconds = 10;

    private WeakReference<AppCompatActivity> activityReference;
    private SharedPreferences sp;
    private String currencyPair;
    private String secondCurrency;

    public LoggerAsyncTask(AppCompatActivity activity) {

        this.activityReference = new WeakReference<>(activity);

        sp = PreferenceManager.getDefaultSharedPreferences(activityReference.get());

        Log.d(LOGTAG, "Logger ASYNCTASK STARTED");
    }


    private void showToast(String msg) {
        Toast toast = Toast.makeText(activityReference.get().getApplicationContext(),
                msg,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    private void updateUpdateRateSeconds () {

        try {
            updateRateSeconds = Integer.parseInt(sp.getString("update_rate","10"));
        } catch (java.lang.RuntimeException e) {
            Log.e(LOGTAG, "Wrong formated string: update rate");
            updateRateSeconds = 10;
        }
    }

    private void updateCurrencyPair () {

            currencyPair = sp.getString("currency_pares","NotChosen/");
            secondCurrency = currencyPair.split("/")[1];
    }


    @Override
    protected LabResponse doInBackground(Void... params) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://logger-mongo.azurewebsites.net") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        MarketApi api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        Call<List<LabResponse>> responseCall;

        Response<List<LabResponse>> res;

        LabResponse labResponse = null;

        while (!isCancelled()) {

            updateUpdateRateSeconds();
            updateCurrencyPair();

            try {
                responseCall = api.getLabResponce("btc_usd");
                res = responseCall.execute();
                labResponse = res.body().get(0);
            } catch (IOException e) {
                Log.e(LOGTAG, e.toString());
            }

            publishProgress(labResponse);

            try {
                TimeUnit.SECONDS.sleep(updateRateSeconds);
            } catch(InterruptedException e) {
                Log.d(LOGTAG, e.getMessage());
            }
        }

        return labResponse;
    }


    @Override
    protected void onProgressUpdate(LabResponse ... params) {

        super.onProgressUpdate(params);
        LabResponse response = params[0];

        if(response == null) {
            showToast("Bad Internet connection");
            return;
        }

        LineChart chart = (LineChart) activityReference.get().findViewById(R.id.diagram);
        List <Entry> chartEntries = new ArrayList<>();

        for(int i = 0; i < response.getAmountPoints().size(); ++i) {
            chartEntries.add(new Entry(response.getAmountPoints().get(i).floatValue()
                    ,response.getProfitPoints().get(i).floatValue()));
        }

        LineDataSet ds = new LineDataSet(chartEntries, "Profit/Money Diagram");
        ds.setColor(R.color.colorPrimaryDark);

        LineData ld = new LineData(ds);

        chart.setData(ld);
        chart.invalidate();


        ((TextView)activityReference.get().findViewById(R.id.profit_string))
                .setText(Double.toString((float)Math.round(response.getProfit()*100)/100.0)
                        +" "+secondCurrency);
        ((TextView)activityReference.get().findViewById(R.id.amount_string))
                .setText(Double.toString((float)Math.round(response.getAmount()*100)/100.0)
                        +" "+secondCurrency);
        ((TextView)activityReference.get().findViewById(R.id.currency_pair)).setText(currencyPair);


        DealListData dldata = new DealListData(response.getOrders());

        RecyclerView list = activityReference.get().findViewById(R.id.iknowdaway);
        LinearLayoutManager llm = new LinearLayoutManager(activityReference.get());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        list.setAdapter(new DealListAdapter(dldata));
    }

}