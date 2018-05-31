package com.example.handingreport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText etName;
    private EditText etStat;
    private EditText etEmails;
    private EditText etPasswords;
    private FirebaseAuth firebaseAuth;
    Button bntregis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        bntregis = (Button) findViewById(R.id.btnregis);
        bntregis.setOnClickListener(this);

        firebaseAuth =FirebaseAuth.getInstance();

        etName = (EditText) findViewById(R.id.etName);
        etStat = (EditText) findViewById(R.id.etStat);
        etEmails = (EditText) findViewById(R.id.etEmails);
        etPasswords = (EditText) findViewById(R.id.etPassword);

    }

    public void register(){
        String email = etEmails.getText().toString().trim();
        String password = etPasswords.getText().toString().trim();

        if (email.isEmpty()) {
            etEmails.setError("Email is required");
            etEmails.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmails.setError("Please enter a valid email");
            etEmails.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPasswords.setError("Password is required");
            etPasswords.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPasswords.setError("Minimum lenght of password should be 6");
            etPasswords.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(RegistrationActivity.this, "Please wait...", "Processing...", true);
        (firebaseAuth.createUserWithEmailAndPassword(etEmails.getText().toString(), etPasswords.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(i);

                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "You are all ready registed", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnregis:
            register();
            break;
        }
    }
}