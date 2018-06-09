package com.course_project.profitmoneydiagram.api;


import com.course_project.profitmoneydiagram.network.bitfinex.BitfinexResponse;
import com.course_project.profitmoneydiagram.network.cex.CexResponse;
import com.course_project.profitmoneydiagram.network.exmo.ExmoResponse;
import com.course_project.profitmoneydiagram.network.gdax.GdaxResponse;
import com.course_project.profitmoneydiagram.network.kucoin.KucoinResponse;
import com.course_project.profitmoneydiagram.network.lab.LabResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Used to form a Retrofit request.
public interface MarketApi {

    @GET("/v1/book/btcusd")
    Call<BitfinexResponse> getBitfinexOrderBookBTCUSD(@Query("limit_bids") String limitBids,
                                                      @Query("limit_asks") String limitAsks,
                                                      @Query("group") String group);

    @GET("/v1/book/ethusd")
    Call<BitfinexResponse> getBitfinexOrderBookETHUSD(@Query("limit_bids") String limitBids,
                                                      @Query("limit_asks") String limitAsks,
                                                      @Query("group") String group);


    @GET("/api/order_book/BTC/USD/")
    Call<CexResponse> getCexPartOrderBookBTCUSDT(@Query("depth") String depth);

    @GET("/api/order_book/ETH/USD/")
    Call<CexResponse> getCexPartOrderBookETHUSDT(@Query("depth") String depth);


    @GET("/v1/order_book/")
    Call<ExmoResponse> getExmoOrderBook(@Query("pair") String pair, @Query("limit") String limit);

    @GET("/products/BTC-USD/book")
    Call<GdaxResponse> getGdaxOrderBookBTCUSD(@Query("level") String level);

    @GET("/products/ETH-USD/book")
    Call<GdaxResponse> getGdaxOrderBookETHUSD(@Query("level") String level);

    @GET("/v1/open/orders")
    Call<KucoinResponse> getKucoinOrderBook(@Query("symbol") String symbol,
                                            @Query("limit") String limit);

    @GET("/")
    Call<List<LabResponse>> getLoggerResponse(@Query("pair") String pair);
}
