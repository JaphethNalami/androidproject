package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dataregister extends AppCompatActivity {

    private EditText inputFullName, inputRegistrationNumber, inputNationalID, inputDateofBirth, inputPhone;
    private Button btn_datareg;
    private DatabaseReference rootdatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataregister);
        inputFullName.findViewById(R.id.fname);
        inputRegistrationNumber.findViewById(R.id.reg_no);
        inputNationalID.findViewById(R.id.nid);
        inputDateofBirth.findViewById(R.id.dob);
        inputPhone.findViewById(R.id.phone_no);
        btn_datareg.findViewById(R.id.btn_datareg);

        rootdatabaseref = FirebaseDatabase.getInstance().getReference();

        btn_datareg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = inputFullName.getText().toString();
                String reg_no = inputRegistrationNumber.getText().toString();
                String nid = inputNationalID.getText().toString();
                String dob = inputDateofBirth.getText().toString();
                String phone_no = inputPhone.getText().toString();

                rootdatabaseref.setValue(fname);
                rootdatabaseref.setValue(reg_no);
                rootdatabaseref.setValue(nid);
                rootdatabaseref.setValue(dob);
                rootdatabaseref.setValue(phone_no);

            }
        });
    }

}