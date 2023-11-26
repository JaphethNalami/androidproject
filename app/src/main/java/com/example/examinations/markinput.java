package com.example.examinations;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class markinput extends AppCompatActivity {

    EditText reg,mark;
    Button submit;
    Spinner unit,assesment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markinput);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerUnits);
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.units,
                android.R.layout.simple_spinner_item
        );
// Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinner.setAdapter(adapter);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinnerAssessmentType);
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.assessment_types,
                android.R.layout.simple_spinner_item
        );
// Specify the layout to use when the list of choices appears.
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinner1.setAdapter(adapter1);

        // view initialization
        reg = findViewById(R.id.editTextReg_no);
        mark = findViewById(R.id.assign1);
        submit =findViewById(R.id.btnSubmitScores);

        submit.setOnClickListener(View ->{
            // retrieve values from ui
           String reg_number = reg.getText().toString().trim();
           String marks1 = mark.getText().toString().trim();
           int marks=Integer.parseInt(marks1);
           String units = spinner.getSelectedItem().toString();
           String mark_type = spinner1.getSelectedItem().toString();

           //validate input values
            if(reg_number.isEmpty()|| reg_number.length()<1){
                reg.setError("enter all values");
                Toast.makeText(markinput.this,"enter all values",Toast.LENGTH_SHORT).show();
                return;
            }
            if(marks1.isEmpty()){
                mark.setError("Please Enter valid values");
            }
            else if ( mark_type.equals("Assignment 1")& marks<0 || marks>10) {
                mark.setError("Please Enter valid values");
            }
            else if ( mark_type.equals("Assignment 2")& marks<0 || marks>10) {
                mark.setError("Please Enter valid values");
            }
            else if ( mark_type.equals("Cat 1")& marks<0 || marks>20) {
                mark.setError("Please Enter valid values");
            }
            else if ( mark_type.equals("Cat 2")& marks<0 || marks>20) {
                mark.setError("Please Enter valid values");
            }
            else if ( mark_type.equals("Exam")& marks<0 || marks>70) {
                mark.setError("Please Enter valid values");
            }
            else {
                //toast message
                Toast.makeText(markinput.this, "Marks Submitted", Toast.LENGTH_SHORT).show();
            }


        });

    }
}