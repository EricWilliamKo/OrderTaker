package com.example.eric.ordertaker.Drinks;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.eric.ordertaker.R;

import java.util.HashMap;

/**
 * Created by eric on 7/18/17.
 */

public class Drink {
    public Name name;
    public Sugar sugar;
    public Ice ice;
    public boolean newCardadded = false;
    public boolean initStatus = false;
    public int spinnerVisible;
    public int resultVisible;

    public Drink(){}

    public void init (Context context, HashMap<String,Spinner> spinnerMap,
                      HashMap<String, ArrayAdapter<CharSequence>> adapterMap){

        name = new Name(context, spinnerMap.get("name"), adapterMap.get("name"));
        ice = new Ice(context, spinnerMap.get("ice"), adapterMap.get("ice"));
        sugar = new Sugar(context, spinnerMap.get("sugar"), adapterMap.get("sugar"));

        name.setValue(context.getResources().getStringArray(R.array.expression_name)[0]);
        ice.setValue(context.getResources().getStringArray(R.array.expression_ice)[0]);
        sugar.setValue(context.getResources().getStringArray(R.array.expression_sugar)[0]);
        spinnerVisible = View.VISIBLE;
        resultVisible = View.GONE;

        initStatus = true;
    }

    public void takeVoiceOrder(String order){
        name.parseVoiceOrder(order);
        ice.parseVoiceOrder(order);
        sugar.parseVoiceOrder(order);
    }

    public String getOrderresult(){
        String result;
        result = name.getValue() + sugar.getValue() + ice.getValue();
        return result;
    }

    public void cardadded(){
        newCardadded = true;
    }

    public void setSpinnerVisible(int visibility){
        spinnerVisible = visibility;
    }

    public void setResultVisible(int visibility){
        resultVisible = visibility;
    }

}
