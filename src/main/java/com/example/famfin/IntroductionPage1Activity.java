package com.example.famfin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class IntroductionPage1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_page1);

        // Handle the "Done" button click
        findViewById(R.id.boxDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Set SharedPreferences flag to indicate the user has seen the introduction
                SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("hasSeenIntroduction", true); // Mark as seen
                editor.apply();

                // Navigate to the second introduction page
                Intent intent = new Intent(IntroductionPage1Activity.this, IntroductionPage2Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
