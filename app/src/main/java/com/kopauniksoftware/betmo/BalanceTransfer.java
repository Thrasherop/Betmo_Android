package com.kopauniksoftware.betmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class BalanceTransfer extends AppCompatActivity {

    TextView meganBalanceDisp;
    TextView joshBalanceDisp;
    TextView amountInput;
    TextView updateDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_transfer);

        // Assigns instance attributes
        meganBalanceDisp = findViewById(R.id.meganBalanceDisp);
        joshBalanceDisp = findViewById(R.id.joshBalanceDisp);
        amountInput = findViewById(R.id.transferAmountInput);
        updateDisp = findViewById(R.id.updateDisp);

        renderBalances();
    }

    public void renderBalances() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    APIConnector.getBalance();

                    meganBalanceDisp.setText(Configs.meganBalanceMessage + String.valueOf(Configs.meganBalance));
                    joshBalanceDisp.setText(Configs.joshBalanceMessage + String.valueOf(Configs.joshBalance));

                    Log.d("APICall", "Data updated");


                } catch (Exception e){
                    Log.d("APICall", "Failed to submit final score: " + e.toString());
                    Toast.makeText(getBaseContext(), "Failed to submit final score╝: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        thread.start();
    }

    public void transferBalance(View v){

        Log.d("Transfer", "Transfer balance called");
        updateDisp.setText(Configs.updateTransferInProgress);

        // Verifies sufficient funds exist
        if (Configs.currentUser.contains(Configs.MeganName)) {
            if (Integer.parseInt(amountInput.getText().toString()) > Configs.meganBalance) {
                Toast.makeText(getBaseContext(), "Insufficient funds", Toast.LENGTH_LONG).show();
                updateDisp.setText(Configs.updateInsufficientFunds);
                return;
            }
        } else {
            if (Integer.parseInt(amountInput.getText().toString()) > Configs.joshBalance){
                Toast.makeText(getBaseContext(), "Insufficient funds", Toast.LENGTH_LONG).show();
                updateDisp.setText(Configs.updateInsufficientFunds);
                return;
            }
        }


        // Gets the amount from the input
        try {

            Log.d("Transfer", "1");
            Log.d("Transfer", String.valueOf(amountInput.getText()));

            //Object foo = Integer.parseInt(amountInput.getText().toString());

            final int amount = Integer.parseInt(amountInput.getText().toString());

            String otherUser = "foo";


            Log.d("Transfer", "12");
            // Finds who the other is
            if (Configs.currentUser.contains(Configs.JoshName)){
                otherUser = Configs.MeganName;
            } else {//if (Configs.currentUser.contains(Configs.MeganName)){
                otherUser = Configs.JoshName;
            }

            final String finalOtherUser = otherUser;
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        // Gets status code and logs it
                        int status = APIConnector.transferBalance(Configs.currentUser, finalOtherUser, amount);
                        Log.d("Transfer", "Transfer finished: " + String.valueOf(status));

                        // Notifies the user
                        String toastText;

                        if (status == 200){
                            toastText = "Successfully transferred funds :)";
                        } else {
                            toastText = "Failed to transfer funds. Code: " + String.valueOf(status);
                        }


                        // Update the ui
                        renderBalances();

                        updateDisp.setText(Configs.updateTransferComplete);



                    } catch (Exception e){
                        Log.d("APICall", "Failed to transfer balance: " + e.toString());
                        updateDisp.setText(Configs.updateTransferFailed + e.toString());
                        //Toast.makeText(getBaseContext(), "Failed to submit final score: " + e.toString(), Toast.LENGTH_LONG).show();

                    }
                }
            });
            thread.start();

        } catch (Exception e){

            Log.e("Transfer", "Failed to transfer: " + e.toString());
            updateDisp.setText(Configs.updateTransferFailed + e.toString());
            //Toast.makeText(getBaseContext(), "Failed to transfer funds: " + e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    public void gotoMainMenu(View v){
        Log.d("Transfer", "Moving to Main Menu");

        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }
}