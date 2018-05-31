package com.course_project.profitmoneydiagram.network.lab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptimalPoint {

    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("profit")
    @Expose
    private Double profit;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

}