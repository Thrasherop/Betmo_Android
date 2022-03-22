package com.kopauniksoftware.betmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.FileOutputStream;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_login_screen);
    }

    public void meganLogin(View v){

        // Checks if the user is currently logged in
        if (getBaseContext().getFileStreamPath(Configs.currentUserSaveFile).exists()) {
            Log.d("Login", "Current user file exists. Deleting and replacing with new one");

            // Deletes the old current user save file
            getBaseContext().getFileStreamPath(Configs.currentUserSaveFile).delete();

        }

        // Finally, create the new currentUSer file with megans name
        createNewFile(Configs.currentUserSaveFile, Configs.MeganName);

        // Finally go back to mainActivity
        gotoMainActivity();

    }

    public void joshLogin(View v){

        // Checks if the user is currently logged in
        if (getBaseContext().getFileStreamPath(Configs.currentUserSaveFile).exists()) {
            Log.d("Login", "Current user file exists. Deleting and replacing with new one");

            // Deletes the old current user save file
            getBaseContext().getFileStreamPath(Configs.currentUserSaveFile).delete();

        }

        // Create the new currentUSer file with josh's name
        createNewFile(Configs.currentUserSaveFile, Configs.JoshName);

        // Finally go back to mainActivity
        gotoMainActivity();
    }

    private void gotoMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void createNewFile(String fileName, String data){

        try {
            FileOutputStream fOut = openFileOutput(fileName, Context.MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();

            Toast.makeText(getBaseContext(), "User data saved", Toast.LENGTH_SHORT).show();

            Log.d("Login", "Worked");

        } catch (Exception e) {
            Log.d("Login", "Couldn't save a file: " + e.toString());
        }
    }
}