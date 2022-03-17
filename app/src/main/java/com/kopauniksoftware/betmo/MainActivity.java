package com.kopauniksoftware.betmo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.SyncFailedException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getBalance(View v){

        Log.d("APICall", "Get balance called");


        //Starts connection thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    APIConnector.getBalance();
                } catch (Exception e){
                    Log.d("APICall", "Failed balance get: " + e.toString());
                }
            }
        });
        thread.start();

    }


    public void transferBalance(View v){
        Log.d("APICall", "transfer called");


        //Starts connection thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    APIConnector.transferBalance();
                } catch (Exception e){
                    Log.d("APICall", "Failed transfer: " + e.toString());
                }
            }
        });
        thread.start();

    }
}