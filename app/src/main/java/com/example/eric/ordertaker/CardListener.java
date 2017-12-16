package com.example.eric.ordertaker;

import org.json.JSONException;

/**
 * Created by eric on 8/3/17.
 */

public interface CardListener {
     void addCard();
     void removeCard();
     void checkout() throws JSONException;
}
