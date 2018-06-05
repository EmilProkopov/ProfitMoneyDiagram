package com.course_project.profitmoneydiagram.model;

public class Deal {
        private String type;
        private String marketName;
        private Double amount;
        private Double price;

        public Deal(String t, String n, Double a, Double p) {
            type = t;
            marketName = n;
            amount = a;
            price = p;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }
    }