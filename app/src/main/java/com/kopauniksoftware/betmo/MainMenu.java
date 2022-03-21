package com.kopauniksoftware.betmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    public void switchUser(View v){
        Log.d("Login", "Switch User pushed. Moving to login screen");

        Intent i = new Intent(this, LoginScreen.class);
        startActivity(i);
    }
}