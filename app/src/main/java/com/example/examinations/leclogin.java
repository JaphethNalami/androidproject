package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class leclogin extends AppCompatActivity {
      public Button button;
    EditText inputEmail, inputPassword;
    Button btn_login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+";
    ProgressDialog progdiag;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leclogin);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.pass);
        btn_login = findViewById(R.id.leclogin);
        progdiag = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        button=findViewById(R.id.btnlink);
        button.setOnClickListener(View-> {
            gotoUrl("https://mail.google.com/mail/?view=cm&source=mailto&to=nalamijapheth@gmail.com&body=Type%20Email%20and%20password%20Below&su=LECTURER%20ACCOUNT%20REGISTRATION%20EMAIL");
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}