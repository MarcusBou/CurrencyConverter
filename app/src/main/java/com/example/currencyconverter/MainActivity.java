package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.currencyconverter.Logic.ApiCurrency;
import com.example.currencyconverter.Logic.BaseCurrency;
import com.example.currencyconverter.Logic.CurrencyCalculator;
import com.example.currencyconverter.Logic.CurrencyListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ApiCurrency apiCurrency;
    private Spinner currencyStartValueSpinner;
    private Spinner currencyTargetValueSpinner;
    private EditText amountText;
    private ListView historyListview;
    private CurrencyCalculator calculator;


    CurrencyListener listener = new CurrencyListener() {
        @Override
        public void onBaseChange(List<BaseCurrency> bases) {
            List<String> baseCodes = new ArrayList<String>();
            for(BaseCurrency base : bases){baseCodes.add(base.getLetterCode());}
            ArrayAdapter<String> baseAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, baseCodes);
            currencyStartValueSpinner.setAdapter(baseAdapter);
            currencyTargetValueSpinner.setAdapter(baseAdapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.currencyStartValueSpinner = (Spinner) findViewById(R.id.CurrencyChooser);
        this.currencyTargetValueSpinner = (Spinner) findViewById(R.id.TargetCurrency);
        this.amountText = (EditText) findViewById(R.id.Amount);
        this.historyListview = (ListView) findViewById(R.id.HistoryListView);
        this.calculator = new CurrencyCalculator(this);
        this.apiCurrency = new ApiCurrency(this, "https://v6.exchangerate-api.com/v6/", "317d4391da7c6878cf4ee44a/");
        this.apiCurrency.addListener(listener);
        this.apiCurrency.addToQueue(new String[]{"latest","AED"});
    }

    /**Starts calculation Process*/
    public void onCalculateButtonClick(View view){
        List<String> history = calculator.PrepareApiCall(currencyStartValueSpinner.getSelectedItem().toString(), currencyTargetValueSpinner.getSelectedItem().toString(), Double.parseDouble(amountText.getText().toString()));
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(this, android.R.layout.list_content, history);
        this.historyListview.setAdapter(historyAdapter);
    }
}