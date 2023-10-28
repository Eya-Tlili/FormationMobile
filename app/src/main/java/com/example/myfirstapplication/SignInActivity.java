package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    private Button GoToSignUp;
    private TextView goToForgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        GoToSignUp = findViewById(R.id.GoToSignUp);
        GoToSignUp.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });

        goToForgotPass = findViewById(R.id.goToForgotPass);
        goToForgotPass.setOnClickListener(view -> {
            startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
        });
    }


}