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

import java.security.spec.ECField;

public class Submissions extends AppCompatActivity {

    Button guessBtn;
    Button finalBtn;
    Button sendBtn;

    TextView warningLabel;
    TextView guessInput;
    TextView updateDisp;

    Boolean isGuessMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_submissions);

        // Gets instance attributes
        guessBtn = findViewById(R.id.guessBtn);
        finalBtn = findViewById(R.id.finalBtn);
        sendBtn = findViewById(R.id.sendBtn);

        guessInput = findViewById(R.id.guessInput);
        warningLabel = findViewById(R.id.warningLabel);
        updateDisp = findViewById(R.id.updateDisp);

        // Sets it to guess mode
        switchToGuess(null);

    }

    public void send(View v) throws Exception {

        // Logs and updates ui
        Log.d("Submit", "Trying to send submission");
        updateDisp.setText(Configs.updateSubmitInProgress);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    int amount = Integer.parseInt(guessInput.getText().toString());


                    // Checks which mode user is in
                    if (isGuessMode) {

                        // Submit as guess
                        int status = APIConnector.submitGuess(amount);

                        if (status == 200) {
                            // Update display
                            updateDisp.setText(Configs.updateGuessSubmitComplete);
                        } else {
                            updateDisp.setText(Configs.updateGuessSubmitFailed + String.valueOf(status));
                        }

                    } else {
                        // Submit final score
                        int status = APIConnector.submitFinalScore(amount);

                        if (status == 200) {
                            // Update display
                            updateDisp.setText(Configs.updateFinalSubmitComplete);
                        } else {
                            updateDisp.setText(Configs.updateFinalFailed + String.valueOf(status));
                        }
                    }
                } catch (Exception e){
                    Log.d("Submission", "Failed to submit: " + e.toString());
                    updateDisp.setText(Configs.updateSubmitFailedGeneral + e.toString());
                }
            }
        });
        thread.start();

    }

    public void switchToGuess(View v){

        /*
            Disables the final button, and enables the guess btn
         */

        guessBtn.setEnabled(false);
        finalBtn.setEnabled(true);
        warningLabel.setVisibility(View.INVISIBLE);

        isGuessMode = true;

    }

    public void switchToFinal(View v){

        if (Configs.currentUser.contains(Configs.MeganName)) {

            guessBtn.setEnabled(true);
            finalBtn.setEnabled(false);
            warningLabel.setVisibility(View.VISIBLE);

            isGuessMode = false;
        } else {

            Toast.makeText(getBaseContext(), "You are not logged in as Megan", Toast.LENGTH_LONG).show();
        }
    }


    public void gotoMainMenu(View v){
        Log.d("Submit", "Moving to Main Menu");

        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }
}