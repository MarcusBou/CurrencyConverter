package com.example.currencyconverter.Logic;

import android.util.Log;

public class BaseCurrency {
    private String letterCode;
    private double value;

    public BaseCurrency(String code, double value){
        this.letterCode = code;
        this.value = value;
    }
    public String getLetterCode(){
        return this.letterCode;
    }

    /**Will take current value and times it with
     * @param amount*/
    public void SetValueAfterAmount(double amount){
        Log.i("ValueCheck", String.valueOf(this.value));
        this.value = this.value * amount;
        Log.i("ValueCheck", String.valueOf(this.value));
    }
    @Override
    public String toString(){
        return String.format(this.letterCode + ":" + String.valueOf(this.value));
    }
}
