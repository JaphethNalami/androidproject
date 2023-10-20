package com.example.examinations;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class register extends AppCompatActivity {

    EditText inputEmail, inputPassword, inputConfirmPassword;
    Button btn_reg;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+";
    ProgressDialog progdiag;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.pass);
        inputConfirmPassword = findViewById(R.id.confirm_pass);
        btn_reg = findViewById(R.id.btn_reg);
        progdiag = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the function to perform authentication
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();
        String confirm_pass = inputConfirmPassword.getText().toString();

        if (!isValidEmail(email)) {
            inputEmail.setError("Enter a valid email address");
            return;
        }
        else if(pass.isEmpty() || pass.length()<8){
            inputPassword.setError("Enter Password Correctly");
        }
        else if(!pass.equals(confirm_pass)){
            inputConfirmPassword.setError("Passwords do not match");
        }
        else{
            progdiag.setMessage("Registration in Progress...");
            progdiag.setTitle("Registration");
            progdiag.setCanceledOnTouchOutside(false);
            progdiag.show();

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progdiag.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(register.this, "Register Successful",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progdiag.dismiss();
                        Toast.makeText(register.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    private void sendUserToNextActivity(){
        Intent intent = new Intent(register.this,Dataregister.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
