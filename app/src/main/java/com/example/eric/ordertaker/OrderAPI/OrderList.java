package com.example.eric.ordertaker.OrderAPI;

import com.example.eric.ordertaker.Drinks.DrinkJson;

import java.util.ArrayList;

/**
 * Created by eric on 12/13/17.
 */

public class OrderList {
    public ArrayList<DrinkJson> orderList;
    public OrderList(){orderList = new ArrayList<>(); }

    public void addDrink(DrinkJson drinkJson){
        orderList.add(drinkJson);
    }
}
