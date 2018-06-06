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

    public void uniteDealsMadeOnSameMarkets () {

        ArrayList <Deal> newDealList = new ArrayList<>();

        ArrayList <String> marketNames = new ArrayList<>();

        for (int i = 0; i < deals.size(); ++i) {
            if(! marketNames.contains(deals.get(i).getMarketName())) {
                marketNames.add(deals.get(i).getMarketName());
            }
        }

        for(int i = 0; i < marketNames.size(); ++i) {

            Deal curDeal = new Deal("Buy", marketNames.get(i), 0.0, 0.0);

            for(int j = 0; j < deals.size(); ++j) {
                if(deals.get(j).getMarketName().equals(marketNames.get(i))
                        && deals.get(i).getType().equals("Buy")) {

                    curDeal.setAmount(curDeal.getAmount() + deals.get(i).getAmount());
                    curDeal.setPrice(Math.max(curDeal.getPrice(), deals.get(i).getPrice()));
                }
            }

            if(!curDeal.getPrice().equals(0.0)) {
                newDealList.add(curDeal);
            }

            curDeal = new Deal("Sell", marketNames.get(i), 0.0, 0.0);

            for(int j = 0; j < deals.size(); ++j) {
                if(deals.get(j).getMarketName().equals(marketNames.get(i))
                        && deals.get(i).getType().equals("Sell")) {

                    curDeal.setAmount(curDeal.getAmount() + deals.get(i).getAmount());
                    curDeal.setPrice(Math.min(curDeal.getPrice(), deals.get(i).getPrice()));
                }
            }

            if(!curDeal.getPrice().equals(0.0)) {
                newDealList.add(curDeal);
            }
        }
        deals = newDealList;
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
