package com.example.famfin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RadioGroup radioGroup = findViewById(R.id.radio_group_categories);
        Button btnDone = findViewById(R.id.btn_done);

        btnDone.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedCategory", selectedRadioButton.getText().toString());
                setResult(RESULT_OK, resultIntent);
            } else {
                setResult(RESULT_CANCELED);  // If no category is selected
            }
            finish();
        });
    }
}
