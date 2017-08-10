package com.example.eric.ordertaker.Drinks;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.eric.ordertaker.R;

/**
 * Created by eric on 7/18/17.
 */

public class Name extends OrderParser{

    public Name(Context context, Spinner spinner, ArrayAdapter<CharSequence> adapter){
        mcontext = context;
//        this.voiceOrderListener = voiceOrderListener;
        expressions = mcontext.getResources().getStringArray(R.array.expression_name);
        this.spinner = spinner;
        this.adapter = adapter;
    }
}
