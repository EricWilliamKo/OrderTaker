package com.example.eric.ordertaker.Drinks;

import android.content.Context;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eric on 7/18/17.
 */

public abstract class OrderParser {
    protected String[] expressions;
    protected Context mcontext;
    protected VoiceOrderListener voiceOrderListener;
    protected String value;
    protected Spinner spinner;
    protected ArrayAdapter<CharSequence> adapter;

    public void parseVoiceOrder(final String order) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean matched;
                Matcher matcher;
                for (String expression : expressions) {
                    matcher = Pattern.compile(regularize(expression)).matcher(order);
                    matched = matcher.find();
                    if (matched) {
                        setValue(expression);
                    }
                }
            }
        }).start();
        return;
    }

    public void setValue(String value) {
        this.value = value;
        final int spinnerPosition = adapter.getPosition(value);
        new Handler(Looper.getMainLooper()).post(new Runnable(){
            @Override
            public void run(){
                spinner.setSelection(spinnerPosition);
            }
        });
    }

    public String regularize(String input) {
        String output = ".*" + input + ".*";
        return output;
    }

    public String getValue() {
        return value;
    }

}
