package com.example.eric.ordertaker.Drinks;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.eric.ordertaker.R;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eric on 7/18/17.
 */

public class Name extends OrderParser{
    protected String[] formalname;

    public Name(Context context, Spinner spinner, ArrayAdapter<CharSequence> adapter){
        mcontext = context;
//        this.voiceOrderListener = voiceOrderListener;
        expressions = mcontext.getResources().getStringArray(R.array.expression_oral_name);
        formalname = mcontext.getResources().getStringArray(R.array.expression_name);
        this.spinner = spinner;
        this.adapter = adapter;
    }

    @Override
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
                        setValue(oralToFormal(expression));
                    }
                }
            }
        }).start();
        return;
    }

    public String oralToFormal(String oral){
        int nameIndex = Arrays.asList(expressions).indexOf(oral);
        if (nameIndex == 0){
            return formalname[1];
        }else if (nameIndex > 0 && nameIndex <= 3){
            return formalname[4];
        }else if (nameIndex == 4){
            return formalname[2];
        }else if (nameIndex > 4 && nameIndex <= 6){
            return formalname[5];
        }else if (nameIndex == 6){
            return formalname[3];
        }else if (nameIndex > 6 && nameIndex <= 11){
            return formalname[6];
        }
        return formalname[0];
    }
}
