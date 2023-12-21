package com.example.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private TextView goToSignUP, goToForgetPass;
    private EditText emailSignIn, passwordSignIn;
    private Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        goToSignUP = findViewById(R.id.goToSignUp);
        goToForgetPass = findViewById(R.id.goToForgetPass);
        emailSignIn = findViewById(R.id.emailSignIn);
        passwordSignIn = findViewById(R.id.passwordSignIn);
        btnSignIn = findViewById(R.id.buttonSignIn);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        goToSignUP.setOnClickListener( v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });
        goToForgetPass.setOnClickListener( v -> {
            startActivity(new Intent(SignInActivity.this,ForgetActivity.class));
        });

        btnSignIn.setOnClickListener(v -> {
            login(emailSignIn.getText().toString().trim(),passwordSignIn.getText().toString().trim());

        });
    }

    private void login(String email, String password) {
        progressDialog.setMessage("Please wait ...!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    checkEmailVerification();
                }
                else {
                    Toast.makeText(SignInActivity.this, "please verify that your data is correct !", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void checkEmailVerification() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user.isEmailVerified()){
            finish();
            startActivity(new Intent(SignInActivity.this,ContactActivity.class));
        }
        else {
            Toast.makeText(this, "please check your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}