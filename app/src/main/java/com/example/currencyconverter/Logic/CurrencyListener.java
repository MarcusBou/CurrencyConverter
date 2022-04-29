package com.example.currencyconverter.Logic;

import java.util.List;

public interface CurrencyListener {
    void onBaseChange(List<BaseCurrency> bases);
}
