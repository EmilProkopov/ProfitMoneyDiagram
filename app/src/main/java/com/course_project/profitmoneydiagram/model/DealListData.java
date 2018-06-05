package com.course_project.profitmoneydiagram.model;


import com.course_project.profitmoneydiagram.network.lab.Orders;

import java.util.ArrayList;
import java.util.List;

//Data about deals that is sent to list adapter.
public class DealListData {

    private List<Double> prices;
    private List<Double> amounts;
    private List<String> names;
    private List<String> types;

    private double roundTo9(double d) {
        return (Math.round(d*1e9)/1e9);
    }

    public DealListData (OutputDataSet dataSet) {

        prices = new ArrayList<>();
        names = new ArrayList<>();
        amounts = new ArrayList<>();
        types = new ArrayList<>();

        for(int i = 0; i < dataSet.getDeals().size(); ++i) {

            prices.add(dataSet.getDeals().get(i).getPrice());
            types.add(dataSet.getDeals().get(i).getType());
            amounts.add(dataSet.getDeals().get(i).getAmount());
            names.add(dataSet.getDeals().get(i).getMarketName());
        }
    }

    public DealListData (Orders orders) {

        prices = new ArrayList<>();
        names = new ArrayList<>();
        amounts = new ArrayList<>();
        types = new ArrayList<>();

        if(orders.getAsks().getGdax() != null) {
            prices.add(roundTo9(orders.getAsks().getGdax().get(0)));
            amounts.add(roundTo9(orders.getAsks().getGdax().get(1)));
            names.add("Gdax");
            types.add("Buy");
        }
        if(orders.getAsks().getBitfinex() != null) {
            prices.add(roundTo9(orders.getAsks().getBitfinex().get(0)));
            amounts.add(roundTo9(orders.getAsks().getBitfinex().get(1)));
            names.add("Bitfinex");
            types.add("Buy");
        }
        if(orders.getAsks().getBitstamp() != null) {
            prices.add(roundTo9(orders.getAsks().getBitstamp().get(0)));
            amounts.add(roundTo9(orders.getAsks().getBitstamp().get(1)));
            names.add("Bitstamp");
            types.add("Buy");
        }
        if(orders.getAsks().getKraken() != null) {
            prices.add(roundTo9(orders.getAsks().getKraken().get(0)));
            amounts.add(roundTo9(orders.getAsks().getKraken().get(1)));
            names.add("Kraken");
            types.add("Buy");
        }
        if(orders.getAsks().getCex() != null) {
            prices.add(roundTo9(orders.getAsks().getCex().get(0)));
            amounts.add(roundTo9(orders.getAsks().getCex().get(1)));
            names.add("Cex");
            types.add("Buy");
        }
        if(orders.getAsks().getExmo() != null) {
            prices.add(roundTo9(orders.getAsks().getExmo().get(0)));
            amounts.add(roundTo9(orders.getAsks().getExmo().get(1)));
            names.add("Exmo");
            types.add("Buy");
        }


        if(orders.getBids().getGdax() != null) {
            prices.add(roundTo9(orders.getBids().getGdax().get(0)));
            amounts.add(roundTo9(orders.getBids().getGdax().get(1)));
            names.add("Gdax");
            types.add("Sell");
        }
        if(orders.getBids().getBitfinex() != null) {
            prices.add(roundTo9(orders.getBids().getBitfinex().get(0)));
            amounts.add(roundTo9(orders.getBids().getBitfinex().get(1)));
            names.add("Bitfinex");
            types.add("Sell");
        }
        if(orders.getBids().getBitstamp() != null) {
            prices.add(roundTo9(orders.getBids().getBitstamp().get(0)));
            amounts.add(roundTo9(orders.getBids().getBitstamp().get(1)));
            names.add("Bitstamp");
            types.add("Sell");
        }
        if(orders.getBids().getKraken() != null) {
            prices.add(roundTo9(orders.getBids().getKraken().get(0)));
            amounts.add(roundTo9(orders.getBids().getKraken().get(1)));
            names.add("Kraken");
            types.add("Sell");
        }
        if(orders.getBids().getCex() != null) {
            prices.add(roundTo9(orders.getBids().getCex().get(0)));
            amounts.add(roundTo9(orders.getBids().getCex().get(1)));
            names.add("Cex");
            types.add("Sell");
        }
        if(orders.getBids().getExmo() != null) {
            prices.add(roundTo9(orders.getBids().getExmo().get(0)));
            amounts.add(roundTo9(orders.getBids().getExmo().get(1)));
            names.add("Exmo");
            types.add("Sell");
        }
    }

    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    public List<Double> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<Double> amounts) {
        this.amounts = amounts;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
