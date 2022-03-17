package com.kopauniksoftware.betmo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APIConnector {



    public static void transferBalance() throws Exception{
        URL url = new URL(Configs.transferAddr);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        String data = "{\"from\": \"Josh\", \"to\": \"Megan\", \"amount\": 2}";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

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


//            lastReply = new JSONObject(finalstr);
//            lastChoice = new JSONArray(lastReply.get("choices").toString());
//            lastCompletion = new JSONObject(lastChoice.get(0).toString());
//            lastString = lastCompletion.getString("text");
//
//            isGoing = false;
//            return lastString;
        }
    }

    public static void getBalance() throws Exception {
        URL url = new URL(Configs.getBalanceAddr);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");

        String data = "{\n  \"prompt\": \"Hello my old friend\",\n  \"max_tokens\": 15\n}";//
        //String data = "{\n  \"prompt\": \""  + prompt  +"\",\n  \"max_tokens\": " + maxTokens + "\n}";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

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


//            lastReply = new JSONObject(finalstr);
//            lastChoice = new JSONArray(lastReply.get("choices").toString());
//            lastCompletion = new JSONObject(lastChoice.get(0).toString());
//            lastString = lastCompletion.getString("text");
//
//            isGoing = false;
//            return lastString;
        }
    }


}
