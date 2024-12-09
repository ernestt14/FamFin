package com.example.famfin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean hasSeenIntroduction = prefs.getBoolean("hasSeenIntroduction", false);

        if (hasSeenIntroduction) {
            // If the user has already seen the introduction, go straight to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finish the main activity to prevent it from showing again
        } else {
            // If the user hasn't seen the introduction, go to the first introduction page
            Intent intent = new Intent(MainActivity.this, IntroductionPage1Activity.class);
            startActivity(intent);
            finish(); // Finish the main activity to prevent it from showing again
        }
    }
}
