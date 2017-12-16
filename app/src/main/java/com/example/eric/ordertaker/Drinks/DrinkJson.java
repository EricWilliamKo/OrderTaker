package com.example.eric.ordertaker.Drinks;

/**
 * Created by eric on 12/11/17.
 */

public class DrinkJson {

    int black_tea = 0;
    int wm_tea = 0;
    int sugar = 0;
    int ice = 0;
    int ingredients = 0;

    public DrinkJson(){
    }

    public void setValue(int black_tea,int wm_tea,int sugar,int ice, int ingredients){
        this.black_tea = black_tea;
        this.wm_tea = wm_tea;
        this.sugar = sugar;
        this.ice = ice;
        this.ingredients = ingredients;
    }

}
