package com.example.myfirstapplication;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
private TextView goToSignIn;
    private EditText etName,etPassword,etMail,etPhone,etCin;
    private Button btnSignUp;
    private String etNameS,etPasswordS,etMailS,etPhoneS,etCinS;
    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName=findViewById(R.id.etNameSignUp);
        etPassword=findViewById(R.id.etPasswordSignUp);
        etMail=findViewById(R.id.etMailSignUp);
        etPhone=findViewById(R.id.etPhoneSignUp);
        etCin=findViewById(R.id.etCinSignUp);
        btnSignUp=findViewById(R.id.btnSignUp);
        goToSignIn=findViewById(R.id.goToSignIn);

        //firebase
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        btnSignUp.setOnClickListener(view -> {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            if (validate()) {
                String email_user=etMail.getText().toString().trim();
                String password_user=etPassword.getText().toString().trim();
                
                firebaseAuth.createUserWithEmailAndPassword(email_user,password_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            sendEmailVerification();
                        }else {
                            Toast.makeText(SignUpActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });
        goToSignIn.setOnClickListener(view -> {startActivity(new Intent(SignUpActivity.this, SignInActivity.class));});
    }

    private void sendEmailVerification() {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "registration done ! Please check your email", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                    }else {
                        Toast.makeText(SignUpActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                    }
                        
                }
            });
        }
    }

    private boolean validate() {
         boolean result=false;

         etNameS=etName.getText().toString();
         etPasswordS=etPassword.getText().toString();
         etCinS=etCin.getText().toString();
         etMailS=etMail.getText().toString();
         etPhoneS=etPhone.getText().toString();

         if (etNameS.isEmpty() || etNameS.length()<7){
             etName.setError("Name is invalid !");
         } else if (!isValidEmail(etMailS) ) {
             etMail.setError("EMail is invalid");

         } else if (etPhoneS.isEmpty() ||etPhoneS.length()!=8) {
             etPhone.setError("Phone is invalid");
         } else if (etCinS.isEmpty()|| etCinS.length()!=8) {
             etCin.setError("cin is invalid");
         } else if (etPasswordS.isEmpty()|| etPasswordS.length()<5) {
             etPassword.setError("password is invalid");
         } else {
             result=true;
         }

         return result;
    }
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}