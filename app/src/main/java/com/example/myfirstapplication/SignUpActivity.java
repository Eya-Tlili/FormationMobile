package com.example.myfirstapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.myfirstapplication.Model.User;

public class SignUpActivity extends AppCompatActivity {
    private TextView goToSignIn;
    private EditText fullName, email, phone, cin, password;
    private Button btnSignUp;
    private String fullNameSt, emailSt, phoneSt, cinSt, passwordSt;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        goToSignIn = findViewById(R.id.goToSignIn);
        fullName = findViewById(R.id.nameSignUp);
        email = findViewById(R.id.emailSignUp);
        phone = findViewById(R.id.numberSignUp);
        cin = findViewById(R.id.cinSignUp);
        password = findViewById(R.id.passwordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        goToSignIn.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
        });

        btnSignUp.setOnClickListener((v -> {
            if (validate()) {
                String user_email = email.getText().toString().trim();
                String user_password = password.getText().toString().trim();
                progressDialog.setMessage("Please wait ...!");
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            sendEmailVerification();
                        }else {
                            Toast.makeText(SignUpActivity.this, "Register failed !", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        }));

    }

    private boolean validate() {
        boolean result = false;
        fullNameSt = fullName.getText().toString();
        emailSt = email.getText().toString();
        phoneSt = phone.getText().toString();
        cinSt = cin.getText().toString();
        passwordSt = password.getText().toString();

        if (fullNameSt.isEmpty() || fullNameSt.length() <=7) {
            fullName.setError("FullName is invalid !");
        } else if (emailSt.isEmpty() || !emailSt.contains("@") || !emailSt.contains(".")){
            email.setError("Email is invalid !");
        } else if (phoneSt.isEmpty() || phoneSt.length() !=8){
            phone.setError("Phone is invalid !");
        } else if (cinSt.isEmpty() || cinSt.length() !=8){
            phone.setError("Cin is invalid");
        } else if (passwordSt.isEmpty() || passwordSt.length()<8){
            password.setError("Password is invalid !");
        }
        else{
            result = true;
        }

        return result;

    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(SignUpActivity.this, "Registration done! Please check your email address !", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        progressDialog.dismiss();
                        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Registration failed !", Toast.LENGTH_SHORT).show();
                    }
                }


            });
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users");
        User user = new User(fullNameSt,emailSt,phoneSt,cinSt,passwordSt);
        myRef.child(firebaseAuth.getUid().toString()).setValue(user);
    }
}