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
    public boolean lastOne = true;
    public boolean initStatus = false;
    public int spinnerVisible;
    public int resultVisible;

    String[] teaArray;
    String[] iceArray;
    String[] sugarArray;

    public Drink(){}

    public void init (Context context, HashMap<String,Spinner> spinnerMap,
                      HashMap<String, ArrayAdapter<CharSequence>> adapterMap){

        name = new Name(context, spinnerMap.get("name"), adapterMap.get("name"));
        ice = new Ice(context, spinnerMap.get("ice"), adapterMap.get("ice"));
        sugar = new Sugar(context, spinnerMap.get("sugar"), adapterMap.get("sugar"));

        teaArray = context.getResources().getStringArray(R.array.expression_name);
        iceArray = context.getResources().getStringArray(R.array.expression_ice);
        sugarArray = context.getResources().getStringArray(R.array.expression_sugar);

        name.setValue(teaArray[0]);
        ice.setValue(iceArray[0]);
        sugar.setValue(sugarArray[0]);

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


    public void setSpinnerVisible(int visibility){
        spinnerVisible = visibility;
    }

    public void setResultVisible(int visibility){
        resultVisible = visibility;
    }

    public DrinkJson getJson(){
        DrinkJson drinkJson = new DrinkJson();
        int black_tea = 0;
        int wm_tea = 0;
        int wm_balck_tea = 0;
        int sugarInt = 0;
        int iceInt = 0;
        int ingredients = 0;
        if(name.getValue().equals(teaArray[1])){
            black_tea = 10;
        }else if (name.getValue().equals(teaArray[2])){
            wm_tea = 10;
        }else if (name.getValue().equals(teaArray[3])){
            wm_balck_tea = 10;
        } else if (name.getValue().equals(teaArray[4])){
            black_tea = 8;
            ingredients = 10;
        } else if (name.getValue().equals(teaArray[5])){
            wm_balck_tea = 8;
            ingredients = 10;
        }else if (name.getValue().equals(teaArray[6])) {
            wm_balck_tea = 8;
            ingredients = 10;
        }

        if (ice.getValue().equals(iceArray[0])){
            iceInt = 10;
            black_tea -= 4;
            wm_tea -= 4;
            wm_balck_tea -= 4;
        }else if (ice.getValue().equals(iceArray[1]) || ice.getValue().equals(iceArray[2])){
            iceInt = 5;
            black_tea -= 2;
            wm_tea -= 2;
            wm_balck_tea -= 2;
        }else if (ice.getValue().equals(iceArray[3])){
            iceInt = 0;
        }

        if (sugar.getValue().equals(sugarArray[0]) ){
            sugarInt = 10;
        }else if (sugar.getValue().equals(sugarArray[1]) ){
            sugarInt = 5;
        }else if (sugar.getValue().equals(sugarArray[2]) || sugar.getValue().equals(sugarArray[3])){
            sugarInt = 3;
        }else if (sugar.getValue().equals(sugarArray[4]) || sugar.getValue().equals(sugarArray[5])){
            sugarInt = 0;
        }

        if (black_tea < 0){black_tea = 0;}
        if (wm_tea < 0){wm_tea = 0;}
        if (wm_balck_tea <0) {wm_balck_tea = 0;}
        if (wm_balck_tea >0) {
            black_tea = wm_balck_tea/2;
            wm_tea = wm_balck_tea/2;
        }
        drinkJson.setValue(black_tea,wm_tea,sugarInt,iceInt,ingredients);

        return drinkJson;
    }

}
