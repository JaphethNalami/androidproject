package com.example.examinations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class lecregister extends AppCompatActivity {

    EditText lecname, lecemail, lecpass, lecphone;
    Button btnreg;
    private DatabaseReference rootdatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecregister);

        lecname = findViewById(R.id.edt_lec_name);
        lecemail = findViewById(R.id.edt_lec_email);
        lecpass = findViewById(R.id.edt_lec_password);
        lecphone = findViewById(R.id.edt_lec_phone);
        btnreg = findViewById(R.id.lec_reg);

        rootdatabaseref = FirebaseDatabase.getInstance().getReference();

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lec_name = lecname.getText().toString();
                String lec_email = lecemail.getText().toString();
                String lec_pass = lecpass.getText().toString();
                String lec_phone = lecphone.getText().toString();

                DatabaseReference userRef = rootdatabaseref.child("Lecturers").push();

                userRef.child("fullname").setValue(lec_name);
                userRef.child("email").setValue(lec_email);
                userRef.child("password").setValue(lec_pass);
                userRef.child("phone_no").setValue(lec_phone)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(lecregister.this, leclogin.class);
                                    Toast.makeText(lecregister.this, "Details Sent!!", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(lecregister.this, "Error sending details.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}