package com.course_project.profitmoneydiagram.model;


import java.util.ArrayList;
import java.util.List;

public class OutputDataSet {

    private List <Double> amountPoints;
    private List <Double> profitPoints;
    private List <Deal> deals;
    private Double profit;
    private Double amount;
    private Double optimalAmount;
    private Double optimalProfit;

    public OutputDataSet () {
        amountPoints = new ArrayList<>();
        profitPoints = new ArrayList<>();
        deals = new ArrayList<>();
        profit = 0.0;
        optimalAmount = 0.0;
        optimalProfit = 0.0;
    }

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

    public List<Double> getProfitPoints() {
        return profitPoints;
    }

    public void setProfitPoints(List<Double> profitPoints) {
        this.profitPoints = profitPoints;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }


    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getOptimalAmount() {
        return optimalAmount;
    }

    public void setOptimalAmount(Double optimalAmount) {
        this.optimalAmount = optimalAmount;
    }

    public Double getOptimalProfit() {
        return optimalProfit;
    }

    public void setOptimalProfit(Double optimalProfit) {
        this.optimalProfit = optimalProfit;
    }

}
