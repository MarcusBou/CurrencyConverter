package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.example.currencyconverter.Logic.CurrencyCalculator;
import com.example.currencyconverter.Logic.GuiListener;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner currencyStartValueSpinner;
    private Spinner currencyTargetValueSpinner;
    private EditText amountText;
    private ListView historyListview;
    private CurrencyCalculator calculator;

    /**Listener for spinner*/
    GuiListener spinnerListener = new GuiListener() {
        @Override
        public void onChange(List<String> text) {
            ArrayAdapter<String> baseAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, text);
            currencyStartValueSpinner.setAdapter(baseAdapter);
            currencyTargetValueSpinner.setAdapter(baseAdapter);
        }
    };

    /**Listener for List*/
    GuiListener listListener = new GuiListener() {
        @Override
        public void onChange(List<String> text) {
            Collections.reverse(text);
            ArrayAdapter<String> historyAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, text);
            historyListview.setAdapter(historyAdapter);
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
        this.calculator.addSpinnerListener(spinnerListener);
        this.calculator.addListListener(listListener);
    }

    /**Starts calculation Process*/
    public void onCalculateButtonClick(View view){
        try {
            calculator.createApiCall(currencyStartValueSpinner.getSelectedItem().toString(), currencyTargetValueSpinner.getSelectedItem().toString(), Double.parseDouble(amountText.getText().toString()));
        }catch (Exception e){
            Log.i("error", e.getMessage());
            if (e.getMessage().equals("empty String")){
                Toast toast = Toast.makeText(this, "You need to write in amount", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }


}