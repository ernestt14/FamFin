package com.example.famfin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ExpensePageActivity extends AppCompatActivity {

    private EditText dateEditText, amountEditText, descriptionEditText, memoEditText;
    private Calendar calendar;
    private TextView categoryTextView;

    private static final int CATEGORY_REQUEST_CODE = 1;  // Define a request code for category activity
    private static final int CAMERA_REQUEST_CODE = 99;   // Request code for camera intent

    private Button btnSnap;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_page); // Link to expense_page.xml

        ImageView backButton = findViewById(R.id.back_button);
        Button incomeButton = findViewById(R.id.incomeExpenseButton);
        Button expenseButton = findViewById(R.id.expenseButton);

        dateEditText = findViewById(R.id.dateEditText);
        amountEditText = findViewById(R.id.amountEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        memoEditText = findViewById(R.id.memoEditText);
        Button saveButton = findViewById(R.id.saveButton);

        categoryTextView = findViewById(R.id.categoryTextView);  // Initialize category TextView

        calendar = Calendar.getInstance();

        // Set up DatePicker for date field
        dateEditText.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(ExpensePageActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String formattedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        dateEditText.setText(formattedDate);
                    }, year, month, day);

            datePickerDialog.show();
        });

        // Set up OnClickListener for category selection
        categoryTextView.setOnClickListener(v -> {
            Intent intent = new Intent(ExpensePageActivity.this, CategoryActivity.class);
            startActivityForResult(intent, CATEGORY_REQUEST_CODE);  // Start CategoryActivity and wait for result
        });

        // Income and Expense button functionality
        incomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExpensePageActivity.this, IncomePageActivity.class);
            startActivity(intent);
        });

        expenseButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExpensePageActivity.this, ExpensePageActivity.class);
            startActivity(intent);
        });

        // Back button functionality
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ExpensePageActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish(); // Optionally finish this activity to remove it from the back stack
        });

        // Save button functionality
        saveButton.setOnClickListener(v -> {
            String date = dateEditText.getText().toString();
            String amount = amountEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String memo = memoEditText.getText().toString();

            if (date.isEmpty() || amount.isEmpty() || description.isEmpty() || memo.isEmpty()) {
                Toast.makeText(ExpensePageActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ExpensePageActivity.this, "Expense saved:\n" +
                                "Date: " + date + "\nAmount: " + amount + "\nDescription: " + description + "\nMemo: " + memo,
                        Toast.LENGTH_LONG).show();

                dateEditText.setText("");
                amountEditText.setText("");
                descriptionEditText.setText("");
                memoEditText.setText("");
            }
        });

        // Camera button functionality
        btnSnap = findViewById(R.id.btncamera);
        imageView = findViewById(R.id.imageView1);

        btnSnap.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        });
    }

    // Combined onActivityResult to handle both CategoryActivity and Camera result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CATEGORY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the selected category from CategoryActivity
            String selectedCategory = data.getStringExtra("selectedCategory");

            // Set the category in the categoryTextView
            categoryTextView.setText(selectedCategory);
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the image as Bitmap and set it to the ImageView
            Bitmap picTaken = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(picTaken);
        } else {
            // Show a Toast message if camera action was canceled
            Toast.makeText(this, "Camera Canceled", Toast.LENGTH_SHORT).show();
        }
    }
}
