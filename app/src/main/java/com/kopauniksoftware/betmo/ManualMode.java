package com.kopauniksoftware.betmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ManualMode extends AppCompatActivity {

    private TextView meganDataView;
    private TextView joshDataView;
    private TextView meganAmountDisp;
    private TextView joshAmountDisp;
    private TextView updateDisp;
    private Button modeBtn;

    private int joshChange = 0;
    private int meganChange = 0;
    private boolean include_wins = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_manual_mode);// Sets up attributes

        meganDataView = findViewById(R.id.meganDataView);
        joshDataView = findViewById(R.id.joshDataView);

        meganAmountDisp = findViewById(R.id.meganAmountDisp);
        joshAmountDisp = findViewById(R.id.joshAmountDisp);

        modeBtn = findViewById(R.id.modeBtn);
        toggleMode(modeBtn);

        updateDisp = findViewById(R.id.updateDisp);


        // Load data
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

    public void joshIncrease(View v){

        Log.d("Manual", "joshIncrease Called");
        joshChange++;
        updateAmountDisp(null);
    }

    public void joshDecrease(View v){
        joshChange--;
        updateAmountDisp(null);
    }

    public void meganIncrease(View v){
        meganChange++;
        updateAmountDisp(null);
    }

    public void meganDecrease(View v){
        meganChange--;
        updateAmountDisp(null);
    }

    public void updateAmountDisp(View v){

        Log.d("Manual", "updating disp values");

        meganAmountDisp.setText(String.valueOf(meganChange));
        joshAmountDisp.setText(String.valueOf(joshChange));

    }

    public void toggleMode(View v){

        if (include_wins){
            include_wins = false;
            Button btn = (Button) v;
            btn.setText(Configs.justBalanceString);
        } else {
            include_wins = true;
            Button btn = (Button) v;
            btn.setText(Configs.doTotalWinsStr);
        }

    }



    public void submit(View v){

        Log.d("Manual", "Submiting manual changes");



        // Sends the request
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    Log.d("Manual", "Sending request");
                    int status = APIConnector.submitManual(meganChange, joshChange, include_wins);
                    Log.d("Manual", "Request finished: " + String.valueOf(status));

                    // Notifies the user
                    String toastText;

                    if (status == 200){
                        toastText = Configs.manualSucceeded;
                    } else {
                        toastText = Configs.manualFailed + String.valueOf(status);
                    }

                    updateDisp.setText(toastText);

                    // Resets screen
                    joshChange = 0;
                    meganChange = 0;

                    updateAmountDisp(null);

                    update();


                } catch (Exception e){
                    Log.d("APICall", "Failed to manually submit: " + e.toString());
                    
                }
            }
        });
        thread.start();



    }

    public void gotoMainMenu(View v){
        Log.d("Login", "Switching to manual mode screen");

        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }
}