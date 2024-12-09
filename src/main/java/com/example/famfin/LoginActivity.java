package com.example.famfin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    private TextView signUpTextView;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        // Initialize UI components
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        signUpTextView = findViewById(R.id.textNoAccount);

        // Set up the Sign-Up link
        signUpTextView.setOnClickListener(v -> {
            // Start the SignUpActivity
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent); // Navigate to SignUpActivity
        });

        // Login button logic
        findViewById(R.id.boxNext).setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                loginUser(email, password);
            } else {
                Toast.makeText(LoginActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Verify login credentials with Firebase Realtime Database
    private void loginUser(String email, String password) {
        String sanitizedEmail = sanitizeEmail(email);

        Log.d(TAG, "Checking login for email: " + sanitizedEmail);

        usersRef.child(sanitizedEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange called for email: " + sanitizedEmail);
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "User found in database");
                    String storedPassword = dataSnapshot.child("password").getValue(String.class);
                    Log.d(TAG, "Stored password retrieved: " + storedPassword);

                    if (storedPassword != null && storedPassword.equals(password)) {
                        // If credentials are correct, navigate to WelcomeActivity
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Login successful");
                        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                        intent.putExtra("user_email", email); // Pass user email to WelcomeActivity
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d(TAG, "Invalid password entered");
                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "Email not found in database");
                    Toast.makeText(LoginActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Database error: " + databaseError.getMessage());
                Toast.makeText(LoginActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Replace '.' with ',' in email to make it compatible with Firebase
    private String sanitizeEmail(String email) {
        String sanitizedEmail = email.replace(".", ",");
        Log.d(TAG, "Sanitized email: " + sanitizedEmail);
        return sanitizedEmail;
    }
}
