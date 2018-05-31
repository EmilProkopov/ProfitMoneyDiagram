package com.course_project.profitmoneydiagram.network.lab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders {

    @SerializedName("bids")
    @Expose
    private Bids bids;
    @SerializedName("asks")
    @Expose
    private Asks asks;

    public Bids getBids() {
        return bids;
    }

    public void setBids(Bids bids) {
        this.bids = bids;
    }

    public Asks getAsks() {
        return asks;
    }

    public void setAsks(Asks asks) {
        this.asks = asks;
    }

}