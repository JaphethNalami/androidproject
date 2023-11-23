package com.example.examinations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_lecregister extends AppCompatActivity {

    EditText lecid, lecname, lecemail, lecphone, lecnid, lecpass, lecconfirm_pass;
    Button btnreg;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+";
    ProgressDialog progdiag;
    FirebaseAuth mAuth;
    DatabaseReference rootdatabaseref = FirebaseDatabase.getInstance().getReference().child("Lecturers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lecregister);

        lecid = findViewById(R.id.lec_id);
        lecemail = findViewById(R.id.lec_email);
        lecname = findViewById(R.id.lec_name);
        lecphone = findViewById(R.id.lec_phone);
        lecnid = findViewById(R.id.lec_national_id);
        lecpass = findViewById(R.id.lec_password);
        lecconfirm_pass = findViewById(R.id.lec_confirm_password);
        btnreg = findViewById(R.id.lec_register);

        progdiag = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        rootdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot lecturerSnapshot : snapshot.getChildren()) {
                        String id = lecturerSnapshot.getKey(); // This will be the lecturer ID
                        String email = lecturerSnapshot.child("email").getValue(String.class);
                        String fullname = lecturerSnapshot.child("fullname").getValue(String.class);
                        String password = lecturerSnapshot.child("password").getValue(String.class);
                        String phone_no = lecturerSnapshot.child("phone_no").getValue(String.class);

                        // Now you have the details, you can set them to your EditText fields
                        lecid.setText(id);
                        lecemail.setText(email);
                        lecname.setText(fullname);
                        lecpass.setText(password);
                        lecphone.setText(phone_no);

                        break; // Assuming you only want to retrieve details of the first lecturer
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle Errors
            }
        });



        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = lecid.getText().toString();
                String name = lecname.getText().toString();
                String email = lecemail.getText().toString();
                String phone = lecphone.getText().toString();
                String nationalId = lecnid.getText().toString();

                // Add your conditions here
                if (id.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || nationalId.isEmpty()) {
                    Toast.makeText(admin_lecregister.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else if (phone.length() < 10) {
                    Toast.makeText(admin_lecregister.this, "Phone number must be 10 characters", Toast.LENGTH_SHORT).show();
                } else if (nationalId.length() < 8) {
                    Toast.makeText(admin_lecregister.this, "Invalid National ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Assuming you want to update the lecturer data in the database
                DatabaseReference lecturerRef = rootdatabaseref.child(id);
                lecturerRef.child("email").setValue(email);
                lecturerRef.child("fullname").setValue(name);
                lecturerRef.child("phone_no").setValue(phone);
                lecturerRef.child("national_id").setValue(nationalId);
                lecturerRef.child("password").removeValue();



                PerformAuth();

                lecpass.setText("");
            }
        });
    }

    private void PerformAuth() {
        String email = lecemail.getText().toString();
        String pass = lecpass.getText().toString();
        String confirm_pass = lecconfirm_pass.getText().toString();

        if (!isValidEmail(email)) {
            lecemail.setError("Enter a valid email address");
            return;
        }
        else if(pass.isEmpty() || pass.length()<8){
            lecpass.setError("Enter Password Correctly");

        }
        else if(!pass.equals(confirm_pass)){
            lecconfirm_pass.setError("Passwords do not match");
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
                        Toast.makeText(admin_lecregister.this, "Register Successful",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progdiag.dismiss();
                        Toast.makeText(admin_lecregister.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(admin_lecregister.this,adminhomepage.class);
        intent.putExtra("email", lecemail.getText().toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}