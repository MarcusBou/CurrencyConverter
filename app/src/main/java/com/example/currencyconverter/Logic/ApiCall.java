package com.example.currencyconverter.Logic;

public interface ApiCall {
    void addToQueue(String[] endPoint);
    void addListener(CurrencyListener listener);
    void removeListener(CurrencyListener listener);
}
