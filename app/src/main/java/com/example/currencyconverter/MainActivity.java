package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.currencyconverter.Logic.ApiCurrency;
import com.example.currencyconverter.Logic.CurrencyListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ApiCurrency apiCurrency;
    Spinner fromSpinner;
    CurrencyListener listener = new CurrencyListener() {
        @Override
        public void onBaseChange(List<String> bases) {
            ArrayAdapter<String> baseAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, bases);
            fromSpinner.setAdapter(baseAdapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fromSpinner = (Spinner) findViewById(R.id.CurrencyChooser);
        apiCurrency = new ApiCurrency(this, "https://v6.exchangerate-api.com/v6/", "317d4391da7c6878cf4ee44a/");
        apiCurrency.addListener(listener);
        apiCurrency.addToQueue(new String[]{"latest","USD"});
    }
}