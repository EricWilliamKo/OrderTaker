package com.example.eric.ordertaker;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.eric.ordertaker.Drinks.Drink;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CardListener{
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Drink> drinkList;
    private static final String TAG = "Google Now test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = findViewById(R.id.recycler_view);
        drinkList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, drinkList,this);

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // callback for drag-n-drop, false to skip this feature
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // callback for swipe to dismiss, removing item from data and adapter
                removeOrderCard(viewHolder.getAdapterPosition());
            }
        });

        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderAdapter);
        addOrderCard();
        try {
            Glide.with(this).load(R.drawable.logo).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public void addOrderCard() {
        Drink drink = new Drink();
        drinkList.add(drink);
        orderAdapter.notifyDataSetChanged();
    }

    public void removeOrderCard(int position){
        drinkList.remove(position);
        Drink drink = drinkList.get(drinkList.size()-1);
        drink.lastOne = true;
        orderAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult():requestCode:" + requestCode + ",resultCode:" + resultCode);

        if (resultCode == RESULT_OK && null != data) {
            Log.i(TAG, "RESULT_OK, data:" + data.toString());
            ArrayList<String> matches = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            Drink drink = drinkList.get(requestCode);

            for (String match : matches) {
                Log.i(TAG, "onActivityResult, text:" + match);
                drink.takeVoiceOrder(match);
                orderAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void addCard() {
        addOrderCard();
    }

    @Override
    public void removeCard() {

    }
}

