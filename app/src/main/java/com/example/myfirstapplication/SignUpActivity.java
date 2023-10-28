package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {
private Button GoToSignIn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        GoToSignIn=findViewById(R.id.GoToSignIn);
        GoToSignIn.setOnClickListener(view -> {startActivity(new Intent(SignUpActivity.this, SignInActivity.class));});
    }
}