package com.kopauniksoftware.betmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

    public void gotoAccounts(View v){
        Log.d("MainMenu", "Switching to accounts page");

        Intent i = new Intent(this, AccountsScreen.class);
        startActivity(i);
    }

    public void gotoTransfer(View v){
        Log.d("MainMenu", "Switching to balance transfer page");

        Intent i = new Intent(this, BalanceTransfer.class);
        startActivity(i);
    }
}