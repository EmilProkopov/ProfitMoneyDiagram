package com.course_project.profitmoneydiagram.model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.course_project.profitmoneydiagram.api.MarketApi;
import com.course_project.profitmoneydiagram.network.bitfinex.BitfinexResponse;
import com.course_project.profitmoneydiagram.network.cex.CexResponse;
import com.course_project.profitmoneydiagram.network.exmo.ExmoResponse;
import com.course_project.profitmoneydiagram.network.gdax.GdaxResponse;
import com.course_project.profitmoneydiagram.network.kucoin.KucoinResponse;
import com.course_project.profitmoneydiagram.ui.MainActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//Gets data from markets and compiles it into the CompiledOrderBook.
public class OrderBookGetter {

    private static final String LOGTAG = "OrderBookGetter";

    private MarketApi api;
    private Retrofit retrofit;

    private BitfinexResponse getBitfinexResponse (int limit, String currencyPair) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.bitfinex.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MarketApi.class);

        String strLimit = String.valueOf(limit);

        Call<BitfinexResponse> responseCall = null;
        if (currencyPair.equals("BTC/USD")) {
            responseCall = api.getBitfinexOrderBookBTCUSD(strLimit,
                    strLimit, "1");
        } else if (currencyPair.equals("ETH/USD")) {
            responseCall = api.getBitfinexOrderBookETHUSD(strLimit,
                    strLimit, "1");
        }

        Response<BitfinexResponse> res;

        BitfinexResponse bitfinexResponse = null;
        try {
            res = responseCall.execute();
            bitfinexResponse = res.body();
        } catch (IOException e) {
            Log.e(LOGTAG, "IO");
        }

        return bitfinexResponse;
    }
    //Convert Bitfinex responce into CompiledOrderBook.
    private CompiledOrderBook getBitfinexCleanOrderBook(int limit, String currencyPair) {

        BitfinexResponse response = getBitfinexResponse(limit, currencyPair);

        if(response != null) {
            Log.e(LOGTAG, "Bitfinex OK");
        } else {
            Log.e(LOGTAG, "Bitfinex FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < response.getAsks().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(response.getAsks().get(i).getPrice()));
            curPriceQtyPair.setAmount(Double.parseDouble(response.getAsks().get(i).getAmount()));
            curPriceQtyPair.setMarketName("Bitfenix");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < response.getBids().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(response.getBids().get(i).getPrice()));
            curPriceQtyPair.setAmount(Double.parseDouble(response.getBids().get(i).getAmount()));
            curPriceQtyPair.setMarketName("Bitfenix");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }


    private CexResponse getCexPartResponse(int limit, String currencyPair) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://cex.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MarketApi.class);

        String strLimit = String.valueOf(limit);

        Call<CexResponse> responseCall = null;
        if (currencyPair.equals("BTC/USD")) {
           responseCall = api.getCexPartOrderBookBTCUSDT(strLimit);
        } else if (currencyPair.equals("ETH/USD")) {
            responseCall = api.getCexPartOrderBookETHUSDT(strLimit);
        }

        Response<CexResponse> res;

        CexResponse cexResponse = null;
        try {
            res = responseCall.execute();
            cexResponse = res.body();
        } catch (IOException e) {
            Log.e(LOGTAG, e.toString());
        }

        return cexResponse;
    }
    //Convert Cex response into CompiledOrderBook.
    private CompiledOrderBook getCexPartCleanOrderBook(int limit, String currencyPair) {

        CexResponse response = getCexPartResponse(limit, currencyPair);

        if(response != null) {
            Log.e(LOGTAG, "Cex OK");
        } else {
            Log.e(LOGTAG, "Cex FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < response.getAsks().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(response.getAsks().get(i).get(0));
            curPriceQtyPair.setAmount(response.getAsks().get(i).get(1));
            curPriceQtyPair.setMarketName("Cex");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < response.getBids().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(response.getBids().get(i).get(0));
            curPriceQtyPair.setAmount(response.getBids().get(i).get(1));
            curPriceQtyPair.setMarketName("Cex");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }


    private ExmoResponse getExmoResponseBTCUSTD (int limit, String currencyPair) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exmo.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MarketApi.class);

        String strLimit = String.valueOf(limit);

        Call<ExmoResponse> responseCall = null;
        if (currencyPair.equals("BTC/USD")) {
            responseCall = api.getExmoOrderBook("BTC_USD", strLimit);
        } else if(currencyPair.equals("ETH/USD")) {
            responseCall = api.getExmoOrderBook("ETH_USD", strLimit);
        }

        Response<ExmoResponse> res;

        ExmoResponse exmoResponse = null;
        try {
            res = responseCall.execute();
            exmoResponse = res.body();
        } catch (IOException e) {
            Log.e(LOGTAG, "IO");
        }

        return exmoResponse;
    }
    //Convert Exmo response into CompiledOrderBook.
    private CompiledOrderBook getExmoCleanOrderBook(int limit, String currencyPair) {

        ExmoResponse response;
        response = getExmoResponseBTCUSTD(limit, currencyPair);

        if(response != null) {
            Log.e(LOGTAG, "Exmo OK");
        } else {
            Log.e(LOGTAG, "Exmo FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        if (currencyPair.equals("BCT/USD")) {

            for (int i = 0; i < response.getBTCUSD().getAsk().size(); ++i) {

                PriceAmountPair curPriceQtyPair = new PriceAmountPair();

                curPriceQtyPair.setPrice(Double.parseDouble(response.getBTCUSD().getAsk().get(i).get(0)));
                curPriceQtyPair.setAmount(Double.parseDouble(response.getBTCUSD().getAsk().get(i).get(1)));
                curPriceQtyPair.setMarketName("Exmo");

                curAsks.add(curPriceQtyPair);
            }
            res.setAsks(curAsks);

            for (int i = 0; i < response.getBTCUSD().getBid().size(); ++i) {

                PriceAmountPair curPriceQtyPair = new PriceAmountPair();

                curPriceQtyPair.setPrice(Double.parseDouble(response.getBTCUSD().getBid().get(i).get(0)));
                curPriceQtyPair.setAmount(Double.parseDouble(response.getBTCUSD().getBid().get(i).get(1)));
                curPriceQtyPair.setMarketName("Exmo");

                curBids.add(curPriceQtyPair);
            }
            res.setBids(curBids);
        }
        else if (currencyPair.equals("ETH/USD")) {

            for (int i = 0; i < response.getETHUSD().getAsk().size(); ++i) {

                PriceAmountPair curPriceQtyPair = new PriceAmountPair();

                curPriceQtyPair.setPrice(Double.parseDouble(response.getETHUSD().getAsk().get(i).get(0)));
                curPriceQtyPair.setAmount(Double.parseDouble(response.getETHUSD().getAsk().get(i).get(1)));
                curPriceQtyPair.setMarketName("Exmo");

                curAsks.add(curPriceQtyPair);
            }
            res.setAsks(curAsks);

            for (int i = 0; i < response.getETHUSD().getBid().size(); ++i) {

                PriceAmountPair curPriceQtyPair = new PriceAmountPair();

                curPriceQtyPair.setPrice(Double.parseDouble(response.getETHUSD().getBid().get(i).get(0)));
                curPriceQtyPair.setAmount(Double.parseDouble(response.getETHUSD().getBid().get(i).get(1)));
                curPriceQtyPair.setMarketName("Exmo");

                curBids.add(curPriceQtyPair);
            }
            res.setBids(curBids);
        }

        return res;
    }


    private GdaxResponse getGdaxResponseTop50BTCUSTD (String currencyPair) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gdax.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MarketApi.class);

        Call<GdaxResponse> responseCall = null;
        if (currencyPair.equals("BTC/USD")) {
            responseCall = api.getGdaxOrderBookBTCUSD("2");
        } else if (currencyPair.equals("ETH/USD")) {
            responseCall = api.getGdaxOrderBookETHUSD("2");
        }

        Response<GdaxResponse> res;

        GdaxResponse gdaxResponse = null;
        try {
            res = responseCall.execute();
            gdaxResponse = res.body();
        } catch (IOException e) {
            Log.e(LOGTAG, "IO");
        }

        return gdaxResponse;
    }
    //Convert Gdax response into CompiledOrderBook.
    private CompiledOrderBook getGdaxTop50CleanOrderBook(String currencyPair) {

        GdaxResponse responce;
        responce = getGdaxResponseTop50BTCUSTD(currencyPair);

        if(responce != null) {
            Log.e(LOGTAG, "Gdax OK");
        } else {
            Log.e(LOGTAG, "Gdax FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < responce.getAsks().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getAsks().get(i).get(0)));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getAsks().get(i).get(1)));
            curPriceQtyPair.setMarketName("Gdax");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < responce.getBids().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getBids().get(i).get(0)));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getBids().get(i).get(1)));
            curPriceQtyPair.setMarketName("Gdax");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }


    private KucoinResponse getKucoinResponseBTCUSTD (int limit, String currencyPair) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.kucoin.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MarketApi.class);

        String strLimit = String.valueOf(limit);

        Call<KucoinResponse> responseCall = null;
        if (currencyPair.equals("BTC/USD")) {
            responseCall = api.getKucoinOrderBook("BTC-USDT", strLimit);
        } else if (currencyPair.equals("ETH/USD")) {
            responseCall = api.getKucoinOrderBook("ETH-USDT", strLimit);
        }

        Response<KucoinResponse> res;

        KucoinResponse kucoinResponse = null;
        try {
            res = responseCall.execute();
            kucoinResponse = res.body();
        } catch (IOException e) {
            Log.e(LOGTAG, "IO");
        }

        return kucoinResponse;
    }
    //Convert KuCoin response into CompiledOrderBook.
    private CompiledOrderBook getKucoinCleanOrderBook(int limit, String currencyPair) {

        KucoinResponse response;
        response = getKucoinResponseBTCUSTD(limit, currencyPair);

        if(response != null) {
            Log.e(LOGTAG, "Kucoin OK");
        } else {
            Log.e(LOGTAG, "Kucoin FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < response.getData().getBUY().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(response.getData().getBUY().get(i).get(0));
            curPriceQtyPair.setAmount(response.getData().getBUY().get(i).get(1));
            curPriceQtyPair.setMarketName("Kucoin");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < response.getData().getSELL().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(response.getData().getSELL().get(i).get(0));
            curPriceQtyPair.setAmount(response.getData().getSELL().get(i).get(1));
            curPriceQtyPair.setMarketName("Kucoin");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }

    //Get Order books from all markets and unite them into one.
    public CompiledOrderBook getCompiledOrderBook(int limit,
                                                  WeakReference <MainActivity> activityReference,
                                                  String currencyPair) {

        CompiledOrderBook result = new CompiledOrderBook();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activityReference.get());
        //If this market is set active in settings.
        if (sp.getBoolean("bitfinex", true)) {
            //Add all it's orders into the order book.
            result.addAll(getBitfinexCleanOrderBook(limit, currencyPair));
        }
        if (sp.getBoolean("cex", true)) {
            result.addAll(getCexPartCleanOrderBook(limit, currencyPair));
        }
        if (sp.getBoolean("exmo", true)) {
            result.addAll(getExmoCleanOrderBook(limit, currencyPair));
        }
        if (sp.getBoolean("gdax", true)) {
            result.addAll(getGdaxTop50CleanOrderBook(currencyPair));
        }
        if (sp.getBoolean("kucoin", true)) {
            result.addAll(getKucoinCleanOrderBook(limit, currencyPair));
        }
        //Sort the order book.
        //Bids sorted in descending order by price.
        //Asks sorted in ascending order by price.
        result.sort();

        return result;
    }

}
