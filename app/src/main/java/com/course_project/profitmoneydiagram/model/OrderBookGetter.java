package com.course_project.profitmoneydiagram.model;

import android.util.Log;

import com.course_project.profitmoneydiagram.api.MarketApi;
import com.course_project.profitmoneydiagram.model.CompiledOrderBook;
import com.course_project.profitmoneydiagram.model.PriceAmountPair;
import com.course_project.profitmoneydiagram.network.bitfinex.BitfinexResponse;
import com.course_project.profitmoneydiagram.network.bitstamp.BitstampResponce;
import com.course_project.profitmoneydiagram.network.cex.CexResponse;
import com.course_project.profitmoneydiagram.network.cryptopia.CryptopiaResponse;
import com.course_project.profitmoneydiagram.network.exmo.ExmoResponse;
import com.course_project.profitmoneydiagram.network.gdax.GdaxResponse;
import com.course_project.profitmoneydiagram.network.kucoin.KucoinResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class OrderBookGetter {

    private static final String LOGTAG = "OrderBookGetter";

    private MarketApi api;
    private Retrofit retrofit;

    private List<PriceAmountPair> bids = null;
    private List<PriceAmountPair> asks = null;


    public OrderBookGetter () {

        bids = new ArrayList<PriceAmountPair>();
        asks = new ArrayList<PriceAmountPair>();
    }


    /*JSON not parcing
    private BinanceResponse getBinanceResponse (int limit) {

        Log.d(LOGTAG, "YAP");

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.binance.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        String strLimit = String.valueOf(limit);
        Call<BinanceResponse> responseCall = api.getBinanceOrderBook("BTCUSDT", strLimit);

        Response<BinanceResponse> res;

        BinanceResponse binanceResponse = null;
        try {
            res = responseCall.execute();
            binanceResponse = res.body();
        } catch (IOException e) {
            Log.e(LOGTAG, e.toString());
        }

        return binanceResponse;
    }

    private List<PriceAmountPair> getBinanceCleanOrderBook(int limit) {
        BinanceResponse responce = getBinanceResponse(limit);

        if (responce == null) Log.d(LOGTAG, "GOT NULL RESPONCE");
        else Log.d(LOGTAG, "GOT NOT NULL RESPONCE");

        //Log.d(LOGTAG, Integer.toString(responce.getAsks().size()));
        return new ArrayList<>();
    }*/



    private BitfinexResponse getBitfinexResponse (int limit) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.bitfinex.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        String strLimit = String.valueOf(limit);

        Call<BitfinexResponse> responseCall = api.getBitfinexOrderBook(strLimit, strLimit, "1");

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

    private CompiledOrderBook getBitfinexCleanOrderBook(int limit) {

        BitfinexResponse responce = null;
        responce = getBitfinexResponse(limit);


        if(responce != null) {
            Log.d(LOGTAG, "Bitfenix OK");
        } else {
            Log.d(LOGTAG, "Bitfenix FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < responce.getAsks().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getAsks().get(i).getPrice()));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getAsks().get(i).getAmount()));
            curPriceQtyPair.setMarket("Bitfenix");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < responce.getBids().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getBids().get(i).getPrice()));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getBids().get(i).getAmount()));
            curPriceQtyPair.setMarket("Bitfenix");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }



    private BitstampResponce getBitstampResponse () {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bitstamp.net") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        Call<BitstampResponce> responseCall = api.getBitstampOrderBookBTCUSDT();

        Response<BitstampResponce> res;

        BitstampResponce bitstampResponce = null;
        try {
            res = responseCall.execute();
            bitstampResponce = res.body();
        } catch (IOException e) {
            Log.e(LOGTAG, "IO");
        }

        return bitstampResponce;
    }

    private CompiledOrderBook getBitstampCleanOrderBook() {


        BitstampResponce responce = null;
        responce = getBitstampResponse();

        if(responce != null) {
            Log.d(LOGTAG, "Bitstamp OK");
        } else {
            Log.d(LOGTAG, "Bitstamp FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < responce.getAsks().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getAsks().get(i).get(0)));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getAsks().get(i).get(1)));
            curPriceQtyPair.setMarket("Bitstamp");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < responce.getBids().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getBids().get(i).get(0)));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getBids().get(i).get(1)));
            curPriceQtyPair.setMarket("Bitstamp");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }


    /* Нет маркета BTC-USDT
    private BittrexResponce getBittrexResponse () {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://bittrex.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        Call<BittrexResponce> responseCall = api.getBittrexOrderBook("BTC-USDT", "both");

        Response<BittrexResponce> res;

        BittrexResponce bittrexResponse = null;
        try {
            res = responseCall.execute();
            bittrexResponse = res.body();
        } catch (IOException e) {
            Log.e(LOGTAG, e.toString());
        }

        return bittrexResponse;
    }

    private List<PriceAmountPair> getBittrexCleanOrderBook() {
        BittrexResponce responce = getBittrexResponse();


        Log.d(LOGTAG, Integer.toString(responce.getResult().getBuy().size()));
        return new ArrayList<>();
    }*/



    private CexResponse getCexFullResponse () {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://cex.io") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        Call<CexResponse> responseCall = api.getCexAllOrderBookBTCUSDT();

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

    private CompiledOrderBook getCexFullCleanOrderBook() {

        CexResponse responce = null;
        responce = getCexFullResponse();


        if(responce != null) {
            Log.d(LOGTAG, "Cex OK");
        } else {
            Log.d(LOGTAG, "Cex FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < responce.getAsks().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(responce.getAsks().get(i).get(0));
            curPriceQtyPair.setAmount(responce.getAsks().get(i).get(1));
            curPriceQtyPair.setMarket("Cex");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < responce.getBids().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(responce.getBids().get(i).get(0));
            curPriceQtyPair.setAmount(responce.getBids().get(i).get(1));
            curPriceQtyPair.setMarket("Cex");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }



    private CryptopiaResponse getCryptopiaResponseBTCUSTD () {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cryptopia.co.nz") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        Call<CryptopiaResponse> responseCall = api.getCryptopiaOrderBookBTCUSDT();

        Response<CryptopiaResponse> res;

        CryptopiaResponse cryptopiaResponse = null;
        try {
            res = responseCall.execute();
            cryptopiaResponse = res.body();
        } catch (IOException e) {
            Log.e(LOGTAG, e.toString());
        }

        return cryptopiaResponse;
    }

    private CompiledOrderBook getCryptopiaCleanOrderBookBTCUSTD() {

        CryptopiaResponse responce = null;
        responce = getCryptopiaResponseBTCUSTD();

        if(responce != null) {
            Log.d(LOGTAG, "Cryptopia OK");
        } else {
            Log.d(LOGTAG, "Cryptopia FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < responce.getData().getBuy().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(responce.getData().getBuy().get(i).getPrice());
            curPriceQtyPair.setAmount(responce.getData().getBuy().get(i).getVolume());
            curPriceQtyPair.setMarket("Cryptopia");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < responce.getData().getSell().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(responce.getData().getSell().get(i).getPrice());
            curPriceQtyPair.setAmount(responce.getData().getSell().get(i).getVolume());
            curPriceQtyPair.setMarket("Cryptopia");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }



    private ExmoResponse getExmoResponseBTCUSTD (int limit) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exmo.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        String strLimit = String.valueOf(limit);

        Call<ExmoResponse> responseCall = api.getExmoOrderBook("BTC_USDT", strLimit);

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

    private CompiledOrderBook getExmoCleanOrderBookBTCUSTD(int limit) {

        ExmoResponse responce;
        responce = getExmoResponseBTCUSTD(limit);

        if(responce != null) {
            Log.d(LOGTAG, "Exmo OK");
        } else {
            Log.d(LOGTAG, "Exmo FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < responce.getBTCUSDT().getAsk().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getBTCUSDT().getAsk().get(i).get(0)));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getBTCUSDT().getAsk().get(i).get(1)));
            curPriceQtyPair.setMarket("Exmo");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < responce.getBTCUSDT().getBid().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getBTCUSDT().getBid().get(i).get(0)));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getBTCUSDT().getBid().get(i).get(1)));
            curPriceQtyPair.setMarket("Exmo");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }




    private GdaxResponse getGdaxResponseTop50BTCUSTD () {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gdax.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        Call<GdaxResponse> responseCall = api.getGdaxOrderBookBTCUSD("2");

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

    private CompiledOrderBook getGdaxTop50CleanOrderBookBTCUSTD() {

        GdaxResponse responce = null;
        responce = getGdaxResponseTop50BTCUSTD();

        if(responce != null) {
            Log.d(LOGTAG, "Gdax OK");
        } else {
            Log.d(LOGTAG, "Gdax FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < responce.getAsks().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getAsks().get(i).get(0)));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getAsks().get(i).get(1)));
            curPriceQtyPair.setMarket("Gdax");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < responce.getBids().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(Double.parseDouble(responce.getBids().get(i).get(0)));
            curPriceQtyPair.setAmount(Double.parseDouble(responce.getBids().get(i).get(1)));
            curPriceQtyPair.setMarket("Gdax");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }




    private KucoinResponse getKucoinResponseBTCUSTD (int limit) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.kucoin.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create())
                //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();

        api = retrofit.create(MarketApi.class);
        //Создаем объект, при помощи которого будем выполнять запросы

        String strLimit = String.valueOf(limit);

        Call<KucoinResponse> responseCall = api.getKucoinOrderBook("BTC-USDT", strLimit);

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

    private CompiledOrderBook getKucoinCleanOrderBookBTCUSTD(int limit) {

        KucoinResponse responce = null;
        responce = getKucoinResponseBTCUSTD(limit);

        if(responce != null) {
            Log.d(LOGTAG, "Kucoin OK");
        } else {
            Log.d(LOGTAG, "Kucoin FAIL");
            return new CompiledOrderBook();
        }

        CompiledOrderBook res = new CompiledOrderBook();
        ArrayList <PriceAmountPair> curAsks = new ArrayList<>();
        ArrayList <PriceAmountPair> curBids = new ArrayList<>();

        for(int i = 0; i < responce.getData().getBUY().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(responce.getData().getBUY().get(i).get(0));
            curPriceQtyPair.setAmount(responce.getData().getBUY().get(i).get(1));
            curPriceQtyPair.setMarket("Kucoin");

            curAsks.add(curPriceQtyPair);
        }
        res.setAsks(curAsks);

        for(int i = 0; i < responce.getData().getSELL().size(); ++i) {

            PriceAmountPair curPriceQtyPair = new PriceAmountPair();

            curPriceQtyPair.setPrice(responce.getData().getSELL().get(i).get(0));
            curPriceQtyPair.setAmount(responce.getData().getSELL().get(i).get(1));
            curPriceQtyPair.setMarket("Kucoin");

            curBids.add(curPriceQtyPair);
        }
        res.setBids(curBids);

        return res;
    }




    public CompiledOrderBook getCompiledOrderBook(int limit) {

        CompiledOrderBook result = new CompiledOrderBook();

        result.addAll(getBitfinexCleanOrderBook(1000));
        result.addAll(getBitstampCleanOrderBook()); //Strange results
        result.addAll(getCexFullCleanOrderBook()); //too
        result.addAll(getCryptopiaCleanOrderBookBTCUSTD()); //Strangely high results
        result.addAll(getExmoCleanOrderBookBTCUSTD(1000)); //too
        result.addAll(getGdaxTop50CleanOrderBookBTCUSTD());
        result.addAll(getKucoinCleanOrderBookBTCUSTD(1000));

        result.sort();
        //for(int i = 0; i < result.getAsks().size(); i++) {
        //    Log.d(LOGTAG, Double.toString(result.getAsks().get(i).getPrice())+"----"+Double.toString(result.getAsks().get(i).getAmount()));
        //}

        return result;
    }

}
