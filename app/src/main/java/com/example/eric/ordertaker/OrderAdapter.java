package com.example.eric.ordertaker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eric.ordertaker.Drinks.Drink;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by eric on 7/27/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.CardViewHolder> {
    public Context context;
    public List<Drink> drinkList;
    public CardListener cardListener;

    public OrderAdapter(Context context, List<Drink> drinkList, CardListener cardListener) {
        this.context = context;
        this.drinkList = drinkList;
        this.cardListener = cardListener;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public Spinner name_value, sugar_value, ice_value;
        public ArrayAdapter<CharSequence> nameAdapter, iceAdapter, sugarAdapter;
        public ImageButton voiceOrder;
        public Button confirm;
        public LinearLayout spinSelector, resultLayout;
        public TextView orderResult;


        public CardViewHolder(View view) {
            super(view);

            name_value = view.findViewById(R.id.name_value);
            sugar_value = view.findViewById(R.id.sugar_value);
            ice_value = view.findViewById(R.id.ice_value);
            voiceOrder = view.findViewById(R.id.voice_order);
            spinSelector = view.findViewById(R.id.spinselector);
            resultLayout = view.findViewById(R.id.result_layout);
            orderResult = view.findViewById(R.id.order_result);
            confirm = view.findViewById(R.id.confirm);

            nameAdapter =
                    ArrayAdapter.createFromResource(context, R.array.expression_name, R.layout.spinner_item);

            iceAdapter =
                    ArrayAdapter.createFromResource(context, R.array.expression_ice, R.layout.spinner_item);

            sugarAdapter =
                    ArrayAdapter.createFromResource(context, R.array.expression_sugar, R.layout.spinner_item);

            name_value.setAdapter(nameAdapter);
            ice_value.setAdapter(iceAdapter);
            sugar_value.setAdapter(sugarAdapter);
        }
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_card, parent, false);

        return new CardViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        final Drink drink = drinkList.get(position);

        if(drink.initStatus == false){
            HashMap<String, Spinner> spinnerMap = new HashMap<>();
            spinnerMap.put("name", holder.name_value);
            spinnerMap.put("sugar", holder.sugar_value);
            spinnerMap.put("ice", holder.ice_value);
            HashMap<String, ArrayAdapter<CharSequence>> adapterHashMap = new HashMap<>();
            adapterHashMap.put("name", holder.nameAdapter);
            adapterHashMap.put("sugar", holder.sugarAdapter);
            adapterHashMap.put("ice", holder.iceAdapter);

            drink.init(context, spinnerMap, adapterHashMap);
        }else{
            holder.name_value.setSelection(holder.nameAdapter.getPosition(drink.name.getValue()));
            holder.sugar_value.setSelection(holder.sugarAdapter.getPosition(drink.sugar.getValue()));
            holder.ice_value.setSelection(holder.iceAdapter.getPosition(drink.ice.getValue()));
            holder.spinSelector.setVisibility(drink.spinnerVisible);
            holder.orderResult.setVisibility(drink.resultVisible);
            holder.orderResult.setText(drink.getOrderresult());
        }

        holder.name_value.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String value = (String) adapterView.getItemAtPosition(position);
                drink.name.setValue(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        holder.ice_value.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String value = (String) adapterView.getItemAtPosition(position);
                drink.ice.setValue(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        holder.sugar_value.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String value = (String) adapterView.getItemAtPosition(position);
                drink.sugar.setValue(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        holder.voiceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGoogleNow(position);
            }
        });

        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("position",String.valueOf(position));
                Log.d("positionad",String.valueOf(holder.getAdapterPosition()));
                holder.spinSelector.setVisibility(View.GONE);
                String order = drink.getOrderresult();
                holder.orderResult.setText(order);
                holder.orderResult.setVisibility(View.VISIBLE);
                drink.setSpinnerVisible(View.GONE);
                drink.setResultVisible(View.VISIBLE);
                Log.d("newCardadded1",String.valueOf(drink.newCardadded));
                if(drink.newCardadded == false){
                    cardListener.addCard();
                    drink.cardadded();
                }

            }
        });

        holder.resultLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("position",String.valueOf(position));
                Log.d("positionad",String.valueOf(holder.getAdapterPosition()));
                holder.spinSelector.setVisibility(View.VISIBLE);
                holder.orderResult.setVisibility(View.GONE);
                drink.setSpinnerVisible(View.VISIBLE);
                drink.setResultVisible(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }

    private void startGoogleNow(int position) {
        Intent googleNowIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        googleNowIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.TAIWAN);
        googleNowIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.TAIWAN);

        try {
            Log.d("hey", "google now start");
            ((Activity) context).startActivityForResult(googleNowIntent, position);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(context,
                    "Google now not found", Toast.LENGTH_SHORT).show();
        }
    }

}
