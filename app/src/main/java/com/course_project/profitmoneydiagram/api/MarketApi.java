package com.course_project.profitmoneydiagram.api;


import com.course_project.profitmoneydiagram.network.binance.BinanceResponse;
import com.course_project.profitmoneydiagram.network.bitfinex.BitfinexResponse;
import com.course_project.profitmoneydiagram.network.bitstamp.BitstampResponce;
import com.course_project.profitmoneydiagram.network.bittrex.BittrexResponce;
import com.course_project.profitmoneydiagram.network.cex.CexResponse;
import com.course_project.profitmoneydiagram.network.cryptopia.CryptopiaResponse;
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
    @GET("/api/v1/depth")
    Call<BinanceResponse> getBinanceOrderBook(@Query("symbol") String symbol,
                                              @Query("limit") String limit);

    @GET("/v1/book/btcusd")
    Call<BitfinexResponse> getBitfinexOrderBook(@Query("limit_bids") String limitBids,
                                                @Query("limit_asks") String limitAsks,
                                                @Query("group") String group);


    @GET("/api/v2/order_book/btcusd/")
    Call<BitstampResponce> getBitstampOrderBookBTCUSDT();

    @GET("/api/v1.1/public/getorderbook")
    Call<BittrexResponce> getBittrexOrderBook(@Query("market") String market,
                                              @Query("type") String type);

    @GET("/api/order_book/BTC/USD/")
    Call<CexResponse> getCexAllOrderBookBTCUSDT();

    @GET("/api/order_book/BTC/USD/")
    Call<CexResponse> getCexPartOrderBookBTCUSDT(@Query("depth") String depth);

    @GET("/api/GetMarketOrders/BTC_USDT/1000")
    Call<CryptopiaResponse> getCryptopiaOrderBookBTCUSDT();

    @GET("/v1/order_book/")
    Call<ExmoResponse> getExmoOrderBook(@Query("pair") String pair, @Query("limit") String limit);

    @GET("/products/BTC-USD/book")
    Call<GdaxResponse> getGdaxOrderBookBTCUSD(@Query("level") String level);

    @GET("/v1/open/orders")
    Call<KucoinResponse> getKucoinOrderBook(@Query("symbol") String symbol,
                                            @Query("limit") String limit);

    @GET("/")
    Call<List<LabResponse>> getLabResponce(@Query("pair") String pair);
}
