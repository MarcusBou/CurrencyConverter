package com.example.currencyconverter.Logic;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.util.Log;

import androidx.constraintlayout.widget.Guideline;

import com.example.currencyconverter.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CurrencyCalculator{
    private Stack<String> history;
    private ApiCurrency calculationApi;
    private String fromString;
    private String targetString;
    private double amountToBeCalculated;
    private BaseCurrency fromBase;
    private BaseCurrency targetBase;
    private List<HistoryListener> guiListerners;

    private CurrencyListener calculationListener = new CurrencyListener() {
        @Override
        public void onBaseChange(List<BaseCurrency> bases) {
            for(BaseCurrency base: bases){
                Log.i("this", "not working");
                if (base.getLetterCode().equals(fromString)){fromBase = base;}
                if (base.getLetterCode().equals(targetString)){targetBase = base;}
            }
            addToHistory();
        }
    };



    public  CurrencyCalculator(Context ctx){
        this.guiListerners = new ArrayList<HistoryListener>();
        this.history = new Stack<String>();
        this.calculationApi = new ApiCurrency(ctx, "https://v6.exchangerate-api.com/v6/", "317d4391da7c6878cf4ee44a/");
    }

    /**
     * Method for calculating differences in the Two currencies
     * @param from is to target which base currency from
     * @param target is to target the base currency it shall be
     * @param amount is to calculate the amount that is needed*/
    public void createApiCall(String from, String target, double amount){
        this.fromString = from;
        this.targetString = target;
        this.amountToBeCalculated = amount;
        this.calculationApi.addListener(calculationListener);
        this.calculationApi.addToQueue(new String[]{"latest", from});

    }
    private void addToHistory(){
        if (fromBase != null && targetBase != null) {
            fromBase.SetValueAfterAmount(this.amountToBeCalculated);
            targetBase.SetValueAfterAmount(this.amountToBeCalculated);
            history.push(String.format(fromBase.toString() + " -> " + targetBase.toString()));
            notifyHistoryListeners();
        }
    }

    private void notifyHistoryListeners(){
        for (HistoryListener l : guiListerners){
             Log.i("Info", "HEllo");
            l.onChange(this.history);
        }
    }
    public void addHistoryListener(HistoryListener listener){
        this.guiListerners.add(listener);
    }

    public void removeHistoryListener(HistoryListener listener){ this.guiListerners.remove(listener); }
}
