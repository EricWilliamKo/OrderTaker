package com.example.eric.ordertaker.OrderAPI;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import com.example.eric.ordertaker.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by eric on 12/13/17.
 */

public class OrderAPI {
    private static final String BASE_URL = "http://192.168.30.225:8080/";
    private static OrderDrink orderService;
    private Activity activity;

    public OrderAPI(Activity activity){
        this.activity = activity;
    }

    private static synchronized OrderDrink getOrderService(){
        if(orderService == null){
            final GsonConverterFactory converterFactory = GsonConverterFactory.create();
            final RxJavaCallAdapterFactory callAdapterFactory = RxJavaCallAdapterFactory.create();
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(callAdapterFactory)
                    .build();
            orderService = retrofit.create(OrderDrink.class);
        }
        return orderService;
    }

    public void takeOrder(OrderList orderList){
        Call<OrderResponse> call = getOrderService().takeOrder(orderList);
        Log.d("order",orderList.toString());
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.isSuccessful()){
                    popAlertDialog(R.string.response,response.body().getResponse());
                }else {
                    int statusCode = response.code();
                    String result = "Error Code " + Integer.toString(statusCode);
                    popAlertDialog(R.string.response,result);
                }
            }
            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                popAlertDialog(R.string.error,"connect fail");
            }
        });
    }


    private interface OrderDrink{
        @POST("order")
        Call<OrderResponse> takeOrder(@Body OrderList orderList);
    }

    private void popAlertDialog(final int topic, final String msg){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(activity);
                builder.setMessage(msg)
                        .setTitle(topic);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
