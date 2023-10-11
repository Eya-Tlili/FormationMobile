package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    private TextView GoToSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        GoToSignUp = findViewById(R.id.GoToSighUp);

        GoToSignUp.setOnClickListener(view -> { startActivity(new Intent(SignInActivity.this, SignUpActivity.class));});
    }
}