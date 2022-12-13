package com.greymatter.heresthedrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    String emailId,password;
    EditText email_et,password_et;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_et = findViewById(R.id.email);
        password_et = findViewById(R.id.pass);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailId = email_et.getText().toString().trim();
                password = password_et.getText().toString().trim();

                if(isValid()){
                    login();
                }
            }
        });

    }

    private void login() {
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(emailId, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isValid() {
        if(emailId.isEmpty()){
            email_et.setError("Invalid EmailID");
            return false;
        }
        if (password.length() < 8){
            password_et.setError("Password must be 8-16 characters");
            return false;
        }
        return true;
    }

    public void navRegister(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

}