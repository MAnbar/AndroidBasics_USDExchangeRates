package com.example.currency.models;

public class CurrencyRate {

    private String rateName;
    private String rate;

    public CurrencyRate(String rateName, String rate) {
        this.rateName = rateName;
        this.rate = rate;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "rateName='" + rateName + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
