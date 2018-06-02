package com.course_project.profitmoneydiagram.asynctasks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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

    private static final String LOGTAG = "AsyncTask";
    private static int updateRateSeconds = 10;

    private WeakReference<AppCompatActivity> activityReference;


    public LoggerAsyncTask(AppCompatActivity activity) {

        this.activityReference = new WeakReference<>(activity);
        Log.d(LOGTAG, "WORKER ASYNCTASK STARTED");
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
                .setText(Double.toString((float)Math.round(response.getProfit()*100)/100.0)+" USDT");


        DealListData dldata = new DealListData(response.getOrders());

        RecyclerView list = activityReference.get().findViewById(R.id.iknowdaway);
        LinearLayoutManager llm = new LinearLayoutManager(activityReference.get());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        list.setAdapter(new DealListAdapter(dldata));
    }

}