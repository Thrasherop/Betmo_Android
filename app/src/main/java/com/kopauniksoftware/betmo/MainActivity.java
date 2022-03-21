package com.kopauniksoftware.betmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
          TODO:
            Add a way to load in the logged in user. Probably just save to a file if it exists
            Add a log out button.
         */

        FileOutputStream outputStream;

//        String fileName = "test_save.txt";
//        String data = "ahhh";

        // Checks if user is logged in
        if (getBaseContext().getFileStreamPath(Configs.currentUserSaveFile).exists()){
            Log.d("FileSave", "User is logged in. Will load the name and continue to main menu");

            // Loads user
            try {

                FileInputStream fin = openFileInput(Configs.currentUserSaveFile);
                int c;
                String temp="";

                while( (c = fin.read()) != -1){
                    temp = temp + Character.toString((char)c);
                }

                // Checks for megans name
                if (temp.toUpperCase().contains(Configs.MeganName))

                //tv.setText(temp);
                Log.d("Login", "Data: " + temp);
                Toast.makeText(getBaseContext(), "User data read", Toast.LENGTH_SHORT).show();

                // Switches to main menu screen
                Intent i = new Intent(this, MainMenu.class);
                startActivity(i);
            }
            catch(Exception e){

                Log.d("Login", "Couldn't load in currentUser");

            }



        } else {

            Log.d("FileSave", "Cur user file doesn't exist. Sending to login");

            Intent i = new Intent(this, LoginScreen.class);
            startActivity(i);

        }
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

    public void gotoLoginPage(View v){

        Log.d("Login", "MainActivity: Go to login page button pressed ");

        Intent i = new Intent(this, LoginScreen.class);
        startActivity(i);

    }

    





    /*
      Functions that were used during development that are all depreciated
     */

    public void transferBalance(View v){
        Log.d("APICall", "transfer called");


        final String sender = "Megan";
        final String receiver = "Josh";
        final int amount = 2;


        //Starts connection thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    APIConnector.transferBalance(sender, receiver, amount);
                } catch (Exception e){
                    Log.d("APICall", "Failed transfer: " + e.toString());
                }
            }
        });
        thread.start();

    }

    public void submitGuess(View v){
        Log.d("APICall", "submit guess called");


        final int new_guess = 12;


        //Starts connection thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    APIConnector.submitGuess(new_guess);
                } catch (Exception e){
                    Log.d("APICall", "Failed submit guess: " + e.toString());
                }
            }
        });
        thread.start();
    }

    public void getCurrentGuesses(View v){
        Log.d("APICall", "get current guesses called");




        //Starts connection thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    APIConnector.getCurrentGuesses();
                } catch (Exception e){
                    Log.d("APICall", "Failed get current guess: " + e.toString());
                }
            }
        });
        thread.start();
    }

    public void getTotalWins(View v){
        Log.d("APICall", "get totals wins called");



        //Starts connection thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    APIConnector.getTotalWins();

                    Log.d("APICall", "Thing: " + String.valueOf(Configs.meganTotalWins));
                    Log.d("APICall", "Thing: " + String.valueOf(Configs.joshTotalWins));
                } catch (Exception e){
                    Log.d("APICall", "Failed total wins call: " + e.toString());
                }
            }
        });
        thread.start();
    }

    public void submitFinalScore(View v){
        Log.d("APICall", "submit final score called");


        final int finalScore = 12;


        //Starts connection thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    APIConnector.submitFinalScore(finalScore);
                } catch (Exception e){
                    Log.d("APICall", "Failed to submit final score: " + e.toString());
                }
            }
        });
        thread.start();
    }
}