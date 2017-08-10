package com.example.eric.ordertaker.Drinks;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.eric.ordertaker.R;

/**
 * Created by eric on 7/18/17.
 */

public class Sugar extends OrderParser {

    public Sugar(Context context, Spinner spinner, ArrayAdapter<CharSequence> adapter) {
        mcontext = context;
        expressions = mcontext.getResources().getStringArray(R.array.expression_sugar);
        this.spinner = spinner;
        this.adapter = adapter;
//        this.voiceOrderListener = voiceOrderListener;
    }
}
