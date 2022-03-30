package com.kopauniksoftware.betmo;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APIConnector {

    public static int transferBalance(String from, String to, int amount) throws Exception{

        /*
            A method to transfer an amount from a user to another user

            params:
                String from: The username of the sender
                String to: The username of the receiver
                int amount: The amount to transfer

            return:
                void
         */



        String data = "{\"from\": \"" + from +"\", \"to\": \"" + to +"\", \"amount\": " + amount + " }";
        JSONObject response = sendRequest(Configs.transferAddr, data);


        return response.getInt("status");

    }

    public static int getBalance() throws Exception {

        /*
            A method to get the current balance

            params:
                None

            return:
                int: status code
         */


        // Sends the request
        String data = "{}";
        JSONObject response = sendRequest(Configs.getBalanceAddr, data);


        try {

            // Parses the data
            JSONObject balances = new JSONObject(response.getString("balances"));
            JSONObject joshBalance = new JSONObject(balances.getString(Configs.JoshName));
            JSONObject meganBalance = new JSONObject(balances.getString(Configs.MeganName));

            Configs.meganBalance = meganBalance.getInt("balance");
            Configs.joshBalance = joshBalance.getInt("balance");

        } catch (Exception e){
            Log.d("APICall", "Failed to save balances: " + e.toString());
            return 100;
        }


        // Returns the status code
        return response.getInt("status");

    }

    public static int submitGuess(int newGuess) throws Exception {

        /*
            A method to submit a guess to the server under the name of Configs.currentUser

            params:
                int newGuess: the new guess to submit

            return:
                status code
         */

        String data = "{\"User\": \"" + Configs.currentUser +"\", \"Guess\": " + String.valueOf(newGuess) +"}";
        JSONObject response = sendRequest(Configs.submitGuessAddr, data);


        return response.getInt("status");

    }

    public static int submitFinalScore(int finalScore) throws Exception{

        /*
            A method to submit the final score at the end of the day

            params:
                int finalScore: the final score

            return:
                int: status code
         */

        String data = "{\"final_score\": " + String.valueOf(finalScore) +"}";
        JSONObject response = sendRequest(Configs.submitFinalScoreAddr, data);



        return response.getInt("status");
    }

    public static int getCurrentGuesses() throws Exception {

        /*
           Gets the current guesses and saves to Configs

           params:
                None

           return:
                int: status code
         */


        // Gets data
        String data = "{}";
        JSONObject response = sendRequest(Configs.getCurrentGuessAddr, data);


        try {
            // Parses data
            JSONObject guesses = new JSONObject(response.getString("guesses"));
            JSONObject joshGuess = new JSONObject(guesses.getString(Configs.JoshName));
            JSONObject meganGuess = new JSONObject(guesses.getString(Configs.MeganName));

            // Saves data
            Configs.joshCurGuess = joshGuess.getInt("Guess");
            Configs.joshLastGuessTime = joshGuess.getString("Guess_Time");

            Configs.meganCurGuess = meganGuess.getInt("Guess");
            Configs.meganLastGuessTime = meganGuess.getString("Guess_Time");
        } catch (Exception e){
            Log.d("APICall", "get cur guesses failed: " + e.toString());

            return 100;
        }



        return response.getInt("status");
    }

    public static int getTotalWins() throws Exception{

        /*
           Gets the total wins of players

           params:
                None

           return:
                void
         */


        String data = "{}";
        JSONObject response = sendRequest(Configs.getTotalWinsAddr, data);

        try {
            // Parses data
            JSONObject total_wins = new JSONObject(response.getString("total_wins"));
            JSONObject joshWins = new JSONObject(total_wins.getString(Configs.JoshName));
            JSONObject meganWins = new JSONObject(total_wins.getString(Configs.MeganName));

            // Saves data

            Configs.meganTotalWins = meganWins.getInt("total_wins");
            Configs.joshTotalWins = joshWins.getInt("total_wins");

            Log.d("APICall", "Thing: " +  joshWins.toString());
        } catch (Exception e){
            Log.d("APICall", "get total wins failed" + e.toString());

            return 100;
        }


        return response.getInt("status");
    }

    public static int submitManual(int meganDelta, int joshDelta, boolean includeWins) throws Exception{

        String data = "{\"delta_josh\": " + String.valueOf(joshDelta) +", \"delta_megan\": " + String.valueOf(meganDelta) +", \"include_wins\": " + String.valueOf(includeWins) + " }";
        Log.d("APICall", "Sending request...");
        JSONObject response = sendRequest(Configs.manual_submissionAddr, data);



        return response.getInt("status");
    }

    public static int updateAll() throws Exception{

        try {
            getCurrentGuesses();
            getTotalWins();
            getBalance();
        } catch (Exception e){
            Log.d("APICall", "Failed to update data: " + e.toString());
            return 400;
        }

        return 200;
    }


    private static JSONObject sendRequest(String endpointURL, String requestBody) throws Exception {

        /*
          Sends a POST request to the url to the inputted endpoint

          params:
            String endpointURL: Desired endpoint url
            String requestBody: JSON formatted string containing the request body

          return:
            JSONObject response: a basic JSONObject of the request response. This will only be processed to a single JSON level.

         */

        URL url = new URL(endpointURL);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        String data = requestBody; //"{\"from\": \"" + from +"\", \"to\": \"" + to +"\", \"amount\": " + amount + " }";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        Log.d("APICall", "Here");

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        BufferedReader br = null;
        if (http.getResponseCode() == 200) {
            br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String strCurrentLine;
            String finalstr = "finalStr is still default???";
            while ((strCurrentLine = br.readLine()) != null) {
                //System.out.println(strCurrentLine);
                finalstr = strCurrentLine;
            }

            Log.d("APICall", http.getResponseCode() + " " + http.getResponseMessage());
            http.disconnect();

            Log.d("APICall", finalstr);


            JSONObject response = new JSONObject(finalstr);

            return response;

        } else {
            return new JSONObject("{\"status\":400}");
        }

    }
}
