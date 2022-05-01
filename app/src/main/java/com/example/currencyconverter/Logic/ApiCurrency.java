package com.example.currencyconverter.Logic;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ApiCurrency implements ApiCall {

    private String basicURL;
    private String key;
    private RequestQueue queue;
    private List<BaseCurrency> currencyBases = new ArrayList<BaseCurrency>();
    private List<CurrencyListener> listeners = new ArrayList<CurrencyListener>();


    public ApiCurrency(Context context, String basicURL, String key){
        queue = Volley.newRequestQueue(context);
        this.basicURL = basicURL;
        this.key = key;
    }

    /**Add Request to a simple queue*/
    public void addToQueue(String[] endPoint){
        String url = createURL(endPoint);
        JsonObjectRequest JsonRequest = createJsonRequest(url);
        queue.add(JsonRequest);
    }

    /**Creates A string Request with anonymous function*/
    private JsonObjectRequest createJsonRequest(String url){
        Log.i("FUCK", url);
        return new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    currencyBases = new ArrayList<BaseCurrency>();
                    JSONObject resp = response.getJSONObject("conversion_rates");
                    Iterator<String> keys = resp.keys();
                    while (keys.hasNext()){
                        String key = keys.next();
                        currencyBases.add(new BaseCurrency(key, Double.parseDouble(resp.get(key).toString())));
                    }
                    notifyAllObserversOnBaseChange();
                }catch (Exception e){
                    Log.i("Error", e.getMessage());
                    Log.i("Error", "e.getMessage()");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.getMessage());
            }
        });
    }

    private String createURL(String[] endpoint){
        String tempURL = this.basicURL + this.key;
        for(String string : endpoint){
            tempURL += string +"/";
        }
        return tempURL;
    }

    private void notifyAllObserversOnBaseChange(){
        for(CurrencyListener l : listeners) {
            l.onBaseChange(this.currencyBases);
        }
    }

    public void addListener(CurrencyListener listener){
        this.listeners.add(listener);
    }

    public void removeListener(CurrencyListener listener){
        this.listeners.remove(listener);
    }
}
