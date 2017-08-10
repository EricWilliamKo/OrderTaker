package com.example.eric.ordertaker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.eric.ordertaker.Drinks.Drink;

import java.util.List;

/**
 * Created by eric on 7/26/17.
 */

public abstract class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{
    private Context context;
    private List<Drink> orderlist;

    public class CardViewHolder extends RecyclerView.ViewHolder{
        public CardViewHolder(View view){
            super(view);
        }

    }
}
