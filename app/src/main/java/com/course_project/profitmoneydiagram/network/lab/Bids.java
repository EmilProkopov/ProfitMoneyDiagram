package com.course_project.profitmoneydiagram.network.lab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bids {

    @SerializedName("exmo")
    @Expose
    private List<Double> exmo = null;

    public List<Double> getExmo() {
        return exmo;
    }

    public void setExmo(List<Double> exmo) {
        this.exmo = exmo;
    }

}