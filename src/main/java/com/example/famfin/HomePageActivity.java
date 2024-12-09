package com.example.famfin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page); // Link to home_page.xml

        // Find the Add Button (ImageView) by ID
        ImageView addButton = findViewById(R.id.add_button); // Ensure you have this ID in your XML

        // Set an OnClickListener to handle button clicks
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open expense_page when the Add button is clicked
                Intent intent = new Intent(HomePageActivity.this, ExpensePageActivity.class);
                startActivity(intent);
            }
        });

        // If you don't need the transaction section, no need to add the listener for transactionText
    }
}
