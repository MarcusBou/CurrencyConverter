package com.example.currencyconverter.Logic;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class CurrencyCalculator{
    private List<String> history;
    private List<String> spinnerInfo;
    private ApiCall calculationApi;
    private ApiCall infoApi;

    private String baseUrl = "https://v6.exchangerate-api.com/v6/";
    private String key = "317d4391da7c6878cf4ee44a/";
    private String fromString;
    private String targetString;
    private double amountToBeCalculated;

    private BaseCurrency fromBase;
    private BaseCurrency targetBase;

    private List<GuiListener> spinnerListeners;
    private List<GuiListener> listListeners;

    private CurrencyListener calculationListener = new CurrencyListener() {
        @Override
        public void onBaseChange(List<BaseCurrency> bases) {
            for (BaseCurrency base : bases) {
                if (base.getLetterCode().equals(fromString)) { fromBase = base; }
                if (base.getLetterCode().equals(targetString)) { targetBase = base; }
            }
            addToHistory();
            calculationApi.removeListener(calculationListener);
        }
    };

    private CurrencyListener spinnerInfoListener = new CurrencyListener() {
        @Override
        public void onBaseChange(List<BaseCurrency> bases) {
            for(BaseCurrency base : bases){spinnerInfo.add(base.getLetterCode());}
            notifySpinnerListeners();
        }
    };

    /**Constructor taking context from gui*/
    public  CurrencyCalculator(Context ctx){
        this.spinnerListeners = new ArrayList<GuiListener>();
        this.listListeners = new ArrayList<GuiListener>();
        this.history = new ArrayList<String>();
        this.spinnerInfo = new ArrayList<String>();
        this.calculationApi = new ApiCurrency(ctx, baseUrl, key);
        this.infoApi = new ApiCurrency(ctx, baseUrl, key);
        this.infoApi.addListener(this.spinnerInfoListener);
        this.infoApi.addToQueue(new String[]{"latest","AED"});
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

    /**Adds Currency base to history*/
    private void addToHistory(){
        if (fromBase != null && targetBase != null) {
            if (!fromBase.equals(targetBase)) {
                fromBase.SetValueAfterAmount(this.amountToBeCalculated);
                targetBase.SetValueAfterAmount(this.amountToBeCalculated);
            }else{
                fromBase.SetValueAfterAmount(this.amountToBeCalculated);
            }
            String something = String.format(fromBase.toString() + " -> " + targetBase.toString());
            history.add(something);
            notifyListListeners();
        }
    }

    /** notifies everyone in the GuyListener*/
    private void notifySpinnerListeners(){
        for (GuiListener l : this.spinnerListeners){
            l.onChange(this.spinnerInfo);
        }
    }

    /** notifies everyone in the GuyListener*/
    private void notifyListListeners(){
        for (GuiListener l : this.listListeners){
            l.onChange(this.history);
        }
    }


    /**For adding listeners for spinners*/
    public void addSpinnerListener(GuiListener listener){ this.spinnerListeners.add(listener); }

    /**For Removing listeners for spinners*/
    public void removeSpinnerListener(GuiListener listener){ this.spinnerListeners.remove(listener); }

    /**For adding listeners for List view*/
    public void addListListener(GuiListener listener){ this.listListeners.add(listener); }

    /**For Removing listeners for List view*/
    public void removeListListener(GuiListener listener){ this.listListeners.remove(listener); }
}
