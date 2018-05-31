package com.course_project.profitmoneydiagram.model;


import com.course_project.profitmoneydiagram.network.lab.Orders;

import java.util.ArrayList;
import java.util.List;

public class DealListData {

    private List<Double> prices;
    private List<Double> amounts;
    private List<String> names;
    private List<String> types;

    public DealListData(Orders orders) {

        prices = new ArrayList<>();
        names = new ArrayList<>();
        amounts = new ArrayList<>();
        types = new ArrayList<>();

        if(orders.getAsks().getGdax() != null) {
            prices.add(orders.getAsks().getGdax().get(0));
            amounts.add(orders.getAsks().getGdax().get(1));
            names.add("Gdax");
            types.add("Buy");
        }
        if(orders.getAsks().getKraken() != null) {
            prices.add(orders.getAsks().getKraken().get(0));
            amounts.add(orders.getAsks().getKraken().get(1));
            names.add("Kraken");
            types.add("Buy");
        }
        if(orders.getBids().getExmo() != null) {
            prices.add(orders.getBids().getExmo().get(0));
            amounts.add(orders.getBids().getExmo().get(1));
            names.add("Exmo");
            types.add("Buy");
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
