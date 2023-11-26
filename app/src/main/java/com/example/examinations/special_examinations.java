package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.examinations.ui.special.SpecialViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class special_examinations extends AppCompatActivity {

    ListView listView;
    List<String> list;
    ArrayAdapter arrayAdapter1;
    Button btn_submit;
    private DatabaseReference rootdatabaseref;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    List<String> selectedSpecials = new ArrayList<>();
    int selectedCount = 0;
    private SpecialViewModel specialViewModel;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_examinations);

        listView = findViewById(R.id.listview1);
        searchView = findViewById(R.id.search_bar1);
        btn_submit = findViewById(R.id.submit_special);

        rootdatabaseref = FirebaseDatabase.getInstance().getReference();

        specialViewModel = new ViewModelProvider(this).get(SpecialViewModel.class);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        list = new ArrayList<>();
        list.add("Calculus");
        list.add("Statistics");
        list.add("Database Systems");
        list.add("Compiler Constructions");
        list.add("Mobile Programming");
        list.add("Internet Programming");
        list.add("Software Engineering");
        list.add("Machine Learning");
        list.add("Networking");
        list.add("Digital Logic");
        arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listView.setAdapter(arrayAdapter1);

        btn_submit.setOnClickListener(v ->{
            if (selectedCount == 0) {
                Toast.makeText(this, "Please select at least one course", Toast.LENGTH_SHORT).show();
            } else {
                saveSelectedSpecial(selectedSpecials);
            }
        });
    }

    private void filterList(String newText) {
        List<String> filteredList = new ArrayList<>();
        for (String item : list) {
            if (item.toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }
       if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
        arrayAdapter1.clear();
        arrayAdapter1.addAll(filteredList);
        arrayAdapter1.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpecial = list.get(position);
                Log.d("Specials", "onItemClick: Clicked");

            if (selectedSpecials.contains(selectedSpecial)) {
                selectedSpecials.remove(selectedSpecial);
                selectedCount--;
            } else if(selectedCount < 5) {
                selectedSpecials.add(selectedSpecial);
                selectedCount++;
                Toast.makeText(special_examinations.this, "Course selected. Total courses: " + selectedCount, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(special_examinations.this, "You can only select 5 courses.", Toast.LENGTH_SHORT).show();
            }
        }
    });
}

    private void saveSelectedSpecial(List<String> specials) {
        String key = mUser.getUid();
        DatabaseReference userRef = rootdatabaseref.child("Students").child(key);
        DatabaseReference specialsRef = userRef.child("Specials");
        specialsRef.removeValue();

        for (String special : specials) {
            specialsRef.child(special).setValue(true);
        }

        // Update ViewModel with selected special
        specialViewModel.setSelectedSpecials(specials);



        Toast.makeText(special_examinations.this, "Courses saved successfully!", Toast.LENGTH_SHORT).show();
    }
}