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


public class WorkerAsyncTask extends AsyncTask<Void, LabResponse, LabResponse> {

    private static final String LOGTAG = "AsyncTask";
    private static int updateRateSeconds = 10;

    private WeakReference<AppCompatActivity> activityReference;


    public WorkerAsyncTask(AppCompatActivity activity) {

        this.activityReference = new WeakReference<>(activity);
        Log.d(LOGTAG, "WORKER ASYNCTASK STARTED");
    }

    @Override
    protected LabResponse doInBackground(Void... params) {

        /*OrderBookGetter getter = new OrderBookGetter();
        CompiledOrderBook orderBook = getter.getCompiledOrderBook(1000);

        Double profit = 0.0;
        Double amount = 0.0;
        Integer tradeCnt = 0;
        ArrayList <Double> profitPoints = new ArrayList<>();
        ArrayList <Double> amountPoints = new ArrayList<>();
        Double alpha = 0.1;
        Double optimalAmount = 0.0;
        Double optimalProfit = 0.0;
        Integer num = 0;
        Boolean ok = true;

        int bx=0, ax = 0;

        OutputDataSet outputDataSet = new OutputDataSet();

        while((ax < orderBook.getAsks().size())
                && (bx < orderBook.getBids().size())
                && (orderBook.getBids().get(bx).getPrice() > orderBook.getAsks().get(ax).getPrice())) {



            Double bidVol = orderBook.getBids().get(bx).getAmount();
            Double askVol = orderBook.getAsks().get(ax).getAmount();
            if (bidVol.equals(0.0)) {
                bx += 1;
                continue;
            }
            if (askVol.equals(0.0)) {
                ax += 1;
                continue;
            }

            Double m = min(bidVol, askVol);
            Double currentProfit = (orderBook.getBids().get(bx).getPrice()
                    - orderBook.getAsks().get(ax).getPrice()) * m;

            profit += currentProfit;
            amount += orderBook.getAsks().get(ax).getPrice() * m;

            profitPoints.add(profit);
            amountPoints.add(amount);
            Double oldBidAmount = orderBook.getBids().get(bx).getAmount();
            Double oldAskAmount = orderBook.getAsks().get(ax).getAmount();
            orderBook.getBids().get(bx).setAmount(oldBidAmount - m);
            orderBook.getAsks().get(ax).setAmount(oldAskAmount - m);
            tradeCnt += 1;

            num += 1;

            Double prevAmount = null;
            Double prevProfit = null;
            Double firstK = null;

            if (num.equals(1)) {
                prevAmount = amount;
                prevProfit = profit;
            }
            else if (num.equals(2)) {
                firstK = (profit - prevProfit) / (amount - prevAmount);
                prevAmount = amount;
                prevProfit = profit;
            }
            else {
                Double k = (profit - prevProfit) / (amount - prevAmount);
                if (k / firstK >= alpha) {
                    optimalAmount = amount;
                    optimalProfit = profit;
                } else {
                    ok = false;
                }
                if (ok) {
                    bid_exch = bids[bx][2]
                    if bid_exch in bid_orders:
                    bid_orders[bid_exch][0] = min(bid_orders[bid_exch][0], bids[bx][0])
                    bid_orders[bid_exch][1] += m
                    else:
                    bid_orders[bid_exch] = [bids[bx][0], m]
                    ask_exch = asks[ax][2]
                    if ask_exch in ask_orders:
                    ask_orders[ask_exch][0] = max(ask_orders[ask_exch][0], asks[ax][0])
                    ask_orders[ask_exch][1] += m
                    else:
                    ask_orders[ask_exch] = [asks[ax][0], m]
                    int OMG = 42;
                }
                prevAmount = amount;
                prevProfit = profit;
            }
            ax++;
            bx++;
        }


        outputDataSet.setProfit(profit);
        outputDataSet.setOptimalAmount(amount);
        outputDataSet.setOptimalProfit(profit);
        outputDataSet.setAmountPoints(amountPoints);
        outputDataSet.setProfitPoints(profitPoints);

        return outputDataSet;*/

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