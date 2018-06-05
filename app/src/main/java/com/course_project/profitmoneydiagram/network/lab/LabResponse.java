package com.course_project.profitmoneydiagram.network.lab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//Object that is returned by Logger.
public class LabResponse {

    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("amount_points")
    @Expose
    private List<Double> amountPoints = null;
    @SerializedName("optimal_point")
    @Expose
    private OptimalPoint optimalPoint;
    @SerializedName("profit")
    @Expose
    private Double profit;
    @SerializedName("profit_points")
    @Expose
    private List<Double> profitPoints = null;
    @SerializedName("ticker")
    @Expose
    private List<Ticker> ticker = null;
    @SerializedName("orders")
    @Expose
    private Orders orders;
    @SerializedName("trade_cnt")
    @Expose
    private Integer tradeCnt;
    @SerializedName("timestamp")
    @Expose
    private Long timestamp;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<Double> getAmountPoints() {
        return amountPoints;
    }

    public void setAmountPoints(List<Double> amountPoints) {
        this.amountPoints = amountPoints;
    }

    public OptimalPoint getOptimalPoint() {
        return optimalPoint;
    }

    public void setOptimalPoint(OptimalPoint optimalPoint) {
        this.optimalPoint = optimalPoint;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public List<Double> getProfitPoints() {
        return profitPoints;
    }

    public void setProfitPoints(List<Double> profitPoints) {
        this.profitPoints = profitPoints;
    }

    public List<Ticker> getTicker() {
        return ticker;
    }

    public void setTicker(List<Ticker> ticker) {
        this.ticker = ticker;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public Integer getTradeCnt() {
        return tradeCnt;
    }

    public void setTradeCnt(Integer tradeCnt) {
        this.tradeCnt = tradeCnt;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}