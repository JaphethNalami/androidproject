package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LecturerDetails extends AppCompatActivity {

    private EditText fullname, regno, natid, Dob, Phone_no;
    private Spinner coursespinner;
    private Button btn_datareg;
    private DatabaseReference rootdatabaseref;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_details);

        fullname = findViewById(R.id.fname);
        regno = findViewById(R.id.reg_no);
        natid = findViewById(R.id.nid);
        Dob = findViewById(R.id.dob);
        coursespinner = findViewById(R.id.course_spinner);  // Added spinner
        Phone_no = findViewById(R.id.phone_no);
        btn_datareg = findViewById(R.id.btn_datareg);

        // Set up spinner with array adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coursespinner.setAdapter(adapter);

        // Set default selection to Calculus
        int position = adapter.getPosition("Calculus");
        coursespinner.setSelection(position);

        String email = getIntent().getStringExtra("email");

        rootdatabaseref = FirebaseDatabase.getInstance().getReference();

        btn_datareg.setOnClickListener(v -> {
            String fname = fullname.getText().toString();
            String reg_no = regno.getText().toString();
            String nid = natid.getText().toString();
            String dob = Dob.getText().toString();
            String phone_no = Phone_no.getText().toString();
            String selectedUnit = coursespinner.getSelectedItem().toString();  // Get selected unit

            // Add your conditions here
            if (reg_no.isEmpty() || nid.isEmpty() || dob.isEmpty() || phone_no.isEmpty()) {
                Toast.makeText(LecturerDetails.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            } else if (phone_no.length() < 10) {
                Toast.makeText(LecturerDetails.this, "Phone number must be 10 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mUser != null) {
                // Assuming you have a node in your database where you want to save this data
                DatabaseReference userRef = rootdatabaseref.child("Lecturers").child(mUser.getUid());

                userRef.child("fullname").setValue(fname);
                userRef.child("reg_no").setValue(reg_no);
                userRef.child("nid").setValue(nid);
                userRef.child("dob").setValue(dob);
                userRef.child("phone_no").setValue(phone_no);
                userRef.child("unit").setValue(selectedUnit);  // Save selected unit

                Intent intent = new Intent(LecturerDetails.this, markinput.class);
                startActivity(intent);
            }
        });
    }
}
