package com.example.famfin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

public class IntroductionPage2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_page2);

        // "Done" button in Intro Page 2
        RelativeLayout boxDone = findViewById(R.id.boxDone);
        boxDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Sign-Up Page
                Intent intent = new Intent(IntroductionPage2Activity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
