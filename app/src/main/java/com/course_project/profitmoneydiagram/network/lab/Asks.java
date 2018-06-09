package com.course_project.profitmoneydiagram.network.lab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Asks {

    @SerializedName("kraken")
    @Expose
    private List<Double> kraken = null;
    @SerializedName("gdax")
    @Expose
    private List<Double> gdax = null;
    @SerializedName("exmo")
    @Expose
    private List<Double> exmo = null;
    @SerializedName("cex")
    @Expose
    private List<Double> cex = null;
    @SerializedName("bitstamp")
    @Expose
    private List<Double> bitstamp = null;
    @SerializedName("bitfinex")
    @Expose
    private List<Double> bitfinex = null;
    @SerializedName("coinsbank")
    @Expose
    private List<Double> coinsbank = null;
    @SerializedName("lakebtc")
    @Expose
    private List<Double> lakebtc = null;
    @SerializedName("livecoin")
    @Expose
    private List<Double> livecoin = null;

    public List<Double> getCoinsbank() {
        return coinsbank;
    }

    public void setCoinsbank(List<Double> coinsbank) {
        this.coinsbank = coinsbank;
    }

    public List<Double> getLakebtc() {
        return lakebtc;
    }

    public void setLakebtc(List<Double> lakebtc) {
        this.lakebtc = lakebtc;
    }

    public List<Double> getLivecoin() {
        return livecoin;
    }

    public void setLivecoin(List<Double> livecoin) {
        this.livecoin = livecoin;
    }

    public List<Double> getKraken() {
        return kraken;
    }

    public void setKraken(List<Double> kraken) {
        this.kraken = kraken;
    }

    public List<Double> getGdax() {
        return gdax;
    }

    public void setGdax(List<Double> gdax) {
        this.gdax = gdax;
    }

    public List<Double> getExmo() {
        return exmo;
    }

    public void setExmo(List<Double> exmo) {
        this.exmo = exmo;
    }

    public List<Double> getCex() {
        return cex;
    }

    public void setCex(List<Double> cex) {
        this.cex = cex;
    }

    public List<Double> getBitstamp() {
        return bitstamp;
    }

    public void setBitstamp(List<Double> bitstamp) {
        this.bitstamp = bitstamp;
    }

    public List<Double> getBitfinex() {
        return bitfinex;
    }

    public void setBitfinex(List<Double> bitfinex) {
        this.bitfinex = bitfinex;
    }
}