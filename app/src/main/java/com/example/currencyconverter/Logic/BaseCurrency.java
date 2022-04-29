package com.example.currencyconverter.Logic;

import android.util.Log;

public class BaseCurrency {
    private String letterCode;
    private Double value;

    public BaseCurrency(String code, Double value){
        this.letterCode = code;
        this.value = value;
    }
    public String getLetterCode(){
        return this.letterCode;
    }

    /**Will take current value and times it with
     * @param amount*/
    public void SetValueAfterAmount(double amount){
        Log.i("errror", "Virker det?");
        this.value = ((double)this.value * amount);
    }
    @Override
    public String toString(){
        return String.format(this.letterCode + ":" + this.value);
    }
}
