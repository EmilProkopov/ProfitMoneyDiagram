package com.course_project.profitmoneydiagram.network.exmo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExmoResponse {

    @SerializedName("BTC_USD")
    @Expose
    private BTCUSD BTCUSD;

    @SerializedName("ETH_USD")
    @Expose
    private ETHUSD ETHUSD;

    public com.course_project.profitmoneydiagram.network.exmo.BTCUSD getBTCUSD() {
        return BTCUSD;
    }

    public void setBTCUSD(com.course_project.profitmoneydiagram.network.exmo.BTCUSD BTCUSD) {
        this.BTCUSD = BTCUSD;
    }

    public com.course_project.profitmoneydiagram.network.exmo.ETHUSD getETHUSD() {
        return ETHUSD;
    }

    public void setETHUSD(com.course_project.profitmoneydiagram.network.exmo.ETHUSD ETHUSD) {
        this.ETHUSD = ETHUSD;
    }
}