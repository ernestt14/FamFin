package com.example.famfin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class IncomePageActivity extends AppCompatActivity {

    // Declare EditText fields for user input
    private EditText dateEditText, amountEditText, descriptionEditText, memoEditText;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_page); // Link to income_page.xml

        // Find the Back Button by ID
        ImageView backButton = findViewById(R.id.back_button);

        // Find the Income and Expense buttons
        Button incomeButton = findViewById(R.id.incomeExpenseButton);
        Button expenseButton = findViewById(R.id.expenseButton);

        // Initialize the EditText fields for user input
        dateEditText = findViewById(R.id.dateEditText);
        amountEditText = findViewById(R.id.amountEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        memoEditText = findViewById(R.id.memoEditText);
        Button saveButton = findViewById(R.id.saveButton);

        // Initialize Calendar for Date Picker
        calendar = Calendar.getInstance();

        // Set OnClickListener to show DatePickerDialog when the date field is clicked
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(IncomePageActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Format the date and set it to the EditText
                        String formattedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        dateEditText.setText(formattedDate);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        // Set up OnClickListener for Income Button to refresh IncomePageActivity
        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomePageActivity.this, IncomePageActivity.class);
                startActivity(intent);
            }
        });

        // Set up OnClickListener for Expense Button to navigate to ExpensePageActivity
        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomePageActivity.this, ExpensePageActivity.class);
                startActivity(intent);
            }
        });

        // Set up OnClickListener to handle back button click
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IncomePageActivity.this, HomePageActivity.class);
                startActivity(intent);
                finish(); // Optionally finish this activity to remove it from the back stack
            }
        });

        // Handle saving or submitting the income data
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input from the EditTexts
                String date = dateEditText.getText().toString();
                String amount = amountEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String memo = memoEditText.getText().toString();

                // Validate the user input (ensure no fields are empty)
                if (date.isEmpty() || amount.isEmpty() || description.isEmpty() || memo.isEmpty()) {
                    Toast.makeText(IncomePageActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // If validation passes, show a confirmation Toast
                    Toast.makeText(IncomePageActivity.this, "Income saved:\n" +
                                    "Date: " + date + "\nAmount: " + amount + "\nDescription: " + description + "\nMemo: " + memo,
                            Toast.LENGTH_LONG).show();

                    // Optionally, clear the fields after saving
                    dateEditText.setText("");
                    amountEditText.setText("");
                    descriptionEditText.setText("");
                    memoEditText.setText("");

                    // Or navigate to another activity if needed
                    // startActivity(new Intent(IncomePageActivity.this, MainActivity.class));
                    // finish();
                }
            }
        });
    }
}
