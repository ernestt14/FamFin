package com.example.famfin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private DatabaseReference usersRef;

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        // Firebase setup
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI components
        emailEditText = findViewById(R.id.editTextEmail1);
        passwordEditText = findViewById(R.id.editTextPassword1);
        RelativeLayout boxNext = findViewById(R.id.boxNext);
        TextView textAlreadyAccount = findViewById(R.id.textAlreadyAccount);

        // Sign-up button logic
        boxNext.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                signUpUser(email, password);
            } else {
                Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Sign-up failed: Empty email or password.");
            }
        });

        // Navigate to Login
        textAlreadyAccount.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void signUpUser(String email, String password) {
        String sanitizedEmail = sanitizeEmail(email);

        Log.d(TAG, "Attempting to sign up user with email: " + sanitizedEmail);

        usersRef.child(sanitizedEmail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().exists()) {
                    Log.d(TAG, "Email does not exist in database. Proceeding with account creation.");

                    usersRef.child(sanitizedEmail).child("password").setValue(password)
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "Account created and stored successfully for email: " + sanitizedEmail);

                                    // Navigate to Login Page
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Error: Could not create account", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Failed to store account data in database for email: " + sanitizedEmail, task1.getException());
                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Sign-up failed: Email already exists in database.");
                }
            } else {
                Toast.makeText(SignUpActivity.this, "Error: Could not check email", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to check if email exists in database", task.getException());
            }
        });
    }

    private String sanitizeEmail(String email) {
        String sanitizedEmail = email.replace(".", ",");
        Log.d(TAG, "Sanitized email: " + sanitizedEmail);
        return sanitizedEmail;
    }
}
