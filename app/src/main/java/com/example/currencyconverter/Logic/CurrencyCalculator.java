package com.example.currencyconverter.Logic;

import android.content.Context;
import android.util.Log;

import com.example.currencyconverter.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CurrencyCalculator{
    private Stack<String> history;
    private ApiCurrency calculationApi;
    private String fromString;
    private String targetString;
    private BaseCurrency fromBase;
    private BaseCurrency targetBase;

    private CurrencyListener calculationListener = new CurrencyListener() {
        @Override
        public void onBaseChange(List<BaseCurrency> bases) {
            for(BaseCurrency base: bases){
                if (base.getLetterCode().equals(fromString)){fromBase = base;}
                if (base.getLetterCode().equals(targetString)){targetBase = base;}
            }
        }
    };



    public  CurrencyCalculator(Context ctx){
        history = new Stack<String>();
        calculationApi = new ApiCurrency(ctx, "https://v6.exchangerate-api.com/v6/", "317d4391da7c6878cf4ee44a/");
    }

    /**
     * Method for calculating differences in the Two currencies
     * @param from is to target which base currency from
     * @param target is to target the base currency it shall be
     * @param amount is to calculate the amount that is needed*/
    public Stack<String> PrepareApiCall(String from, String target, double amount){
        this.fromString = from;
        this.targetString = target;
        boolean shitfuck = false;
        this.calculationApi.addListener(calculationListener);
        this.calculationApi.addToQueue(new String[]{"latest", from});
        this.calculationApi.removeListener(this.calculationListener);
        Log.i("error", String.format(String.valueOf(amount)));
        fromBase.SetValueAfterAmount(amount);
        targetBase.SetValueAfterAmount(amount);
        history.push(String.format(fromBase.toString() + " -> " + targetBase.toString()));
        return history;
    }
}
