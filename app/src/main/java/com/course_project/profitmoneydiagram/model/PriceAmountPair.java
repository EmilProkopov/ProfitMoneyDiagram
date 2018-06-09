package com.course_project.profitmoneydiagram.model;

//Stores information about one bid/ask.
public class PriceAmountPair {

    private Double price;
    private Double amount;
    private String marketName;

    public Double getPrice() {return price;}

    public void setPrice(Double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }
}
