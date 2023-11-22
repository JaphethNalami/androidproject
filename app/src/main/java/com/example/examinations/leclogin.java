package com.example.examinations;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class leclogin extends AppCompatActivity {

    EditText inputEmail, inputPassword;
    Button btn_login,lec;
    ProgressDialog progdiag;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leclogin);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.pass);
        btn_login = findViewById(R.id.Lec_Marks_Enter);
        progdiag = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        lec = findViewById(R.id.lec_signup);
        lec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(leclogin.this, lecregister.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Performlogin();
            }
        });

    }

    private void Performlogin() {
        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();

        if (!isValidEmail(email)) {
            inputEmail.setError("Enter a valid email address");
            return;
        } else if (pass.isEmpty() || pass.length() < 8) {
            inputPassword.setError("Enter Password Correctly");
            return; // Added return statement
        }
        Intent intent = new Intent(leclogin.this, Lec_Marks_Enter.class);
        startActivity(intent);

        progdiag.setMessage("Login in Progress...");
        progdiag.setTitle("Login");
        progdiag.setCanceledOnTouchOutside(false);
        progdiag.show();

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progdiag.dismiss();
                    sendUserToNextActivity();
                    Toast.makeText(leclogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
                    progdiag.dismiss();
                    Toast.makeText(leclogin.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(leclogin.this, Lec_Marks_Enter.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}
