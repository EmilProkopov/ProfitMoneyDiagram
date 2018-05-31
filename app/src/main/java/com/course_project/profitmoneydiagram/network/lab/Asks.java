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

}