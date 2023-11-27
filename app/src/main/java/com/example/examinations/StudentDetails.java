package com.example.examinations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDetails extends AppCompatActivity {

    private EditText inputFullName, inputRegistrationNumber, inputNationalID, inputDateofBirth, inputPhone;
    private Button btn_datareg;
    private Spinner spinner;
    private DatabaseReference rootdatabaseref;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        inputFullName = findViewById(R.id.fname);
        inputRegistrationNumber = findViewById(R.id.reg_no);
        inputNationalID = findViewById(R.id.nid);
        inputDateofBirth = findViewById(R.id.dob);
        inputPhone = findViewById(R.id.phone_no);
        btn_datareg = findViewById(R.id.btn_datareg);
        spinner = findViewById(R.id.semester);

        // Set up the spinner with the string array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.semester,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set the default selection to "Semester 1"
        spinner.setSelection(adapter.getPosition("Semester 1"));

        rootdatabaseref = FirebaseDatabase.getInstance().getReference();

        btn_datareg.setOnClickListener(v -> {
            String fname = inputFullName.getText().toString();
            String reg_no = inputRegistrationNumber.getText().toString();
            String nid = inputNationalID.getText().toString();
            String dob = inputDateofBirth.getText().toString();
            String phone_no = inputPhone.getText().toString();
            String selectedSemester = spinner.getSelectedItem().toString();

            // Add your conditions here
            if (reg_no.isEmpty() || nid.isEmpty() || dob.isEmpty() || phone_no.isEmpty()) {
                Toast.makeText(StudentDetails.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            } else if (phone_no.length() < 10) {
                Toast.makeText(StudentDetails.this, "Phone number must be 10 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mUser != null) {
                // Assuming you have a node in your database where you want to save this data
                DatabaseReference userRef = rootdatabaseref.child("Students").child(mUser.getUid());

                userRef.child("fullname").setValue(fname);
                userRef.child("reg_no").setValue(reg_no);
                userRef.child("nid").setValue(nid);
                userRef.child("dob").setValue(dob);
                userRef.child("phone_no").setValue(phone_no);
                userRef.child("semester").setValue(selectedSemester);

                Toast.makeText(StudentDetails.this, "Data saved successfully", Toast.LENGTH_SHORT).show();

                 Intent intent = new Intent(StudentDetails.this, StudentHomeActivity.class);
                 startActivity(intent);
            }
        });

        // Add a listener to handle item selection in the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item (if needed)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }
}
