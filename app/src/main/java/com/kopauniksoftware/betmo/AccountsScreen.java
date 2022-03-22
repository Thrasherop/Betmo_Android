package com.kopauniksoftware.betmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AccountsScreen extends AppCompatActivity {


    private TextView meganDataView;
    private TextView joshDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_screen);
        // Sets up attributes
        meganDataView = findViewById(R.id.meganDataView);
        joshDataView = findViewById(R.id.joshDataView);

        // Load data
        update();

    }

    public void gotoMainMenu(View v){
        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }

    public void updateForBtn(View v){
        update();
    }

    public void update(){

        /*
            Load data onto screen
            Specifically: Balance, Total wins, Current guess, last guess submission
         */


        Log.d("Update", "Updating accounts info");

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    int response = APIConnector.updateAll();

                    if (response == 200){
                        // it worked
                        sendUpdatesToScreen();
                    } else {
                        // Something failed

                        //Toast.makeText(getBaseContext(), "Could not update info", Toast.LENGTH_LONG).show();
                        Log.d("APICall", "could not update info");
                    }
                } catch (Exception e){
                    Log.d("APICall", "Failed to submit final score: " + e.toString());
                    Toast.makeText(getBaseContext(), "Failed to submit final score: " + e.toString(), Toast.LENGTH_LONG).show();

                }
            }
        });
        thread.start();

    }

    public void sendUpdatesToScreen(){

        String balanceStr = "Balance: ";
        String totalWinsStr = "Total Wins: ";
        String currentGuessStr = "Current Guess: ";
        String lastTimeStr = "Time of last guess: ";

        String meganOuputStr = "Megan:\n";
        String joshOutputStr = "Josh:\n";

        // Adds balance to strings
        meganOuputStr += balanceStr + String.valueOf(Configs.meganBalance) + "\n";
        joshOutputStr += balanceStr + String.valueOf(Configs.joshBalance) + "\n";

        // Adds total wins to strings
        meganOuputStr += totalWinsStr + String.valueOf(Configs.meganTotalWins) + "\n";
        joshOutputStr += totalWinsStr + String.valueOf(Configs.joshTotalWins) + "\n";

        // Adds current guess to strings
        meganOuputStr += currentGuessStr + String.valueOf(Configs.meganCurGuess) + "\n";
        joshOutputStr += currentGuessStr + String.valueOf(Configs.joshCurGuess) + "\n";

        // Adds the time of the last guess time
        meganOuputStr += lastTimeStr + String.valueOf(Configs.meganLastGuessTime) + "\n";
        joshOutputStr += lastTimeStr + String.valueOf(Configs.joshLastGuessTime) + "\n";

        // Actually applies the strings
        meganDataView.setText(meganOuputStr);
        joshDataView.setText(joshOutputStr);


    }
}