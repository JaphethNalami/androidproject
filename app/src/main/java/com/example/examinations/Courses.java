package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.examinations.ui.courses.CoursesViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Courses extends AppCompatActivity {

    ListView listView;
    List<String> list;
    ArrayAdapter arrayAdapter;
    Button btn_submit;
    private DatabaseReference rootdatabaseref;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    List<String> selectedCourses = new ArrayList<>();
    int selectedCount = 0;
    private CoursesViewModel coursesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        SearchView searchView;

        listView = findViewById(R.id.listview);
        searchView = findViewById(R.id.search_bar);
        btn_submit = findViewById(R.id.submit_courses);

        rootdatabaseref = FirebaseDatabase.getInstance().getReference();

        coursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
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
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);

        btn_submit.setOnClickListener(v -> {
            if (selectedCount == 5) {
                saveSelectedCourses(selectedCourses);
            } else {
                Toast.makeText(Courses.this, "Please select exactly 5 courses.", Toast.LENGTH_SHORT).show();
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
        arrayAdapter.clear();
        arrayAdapter.addAll(filteredList);
        arrayAdapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourse = list.get(position);
                Log.d("Courses", "onItemClick: Clicked");

                if (selectedCourses.contains(selectedCourse)) {
                    selectedCourses.remove(selectedCourse);
                    selectedCount--;
                } else if (selectedCount < 5) {
                    selectedCourses.add(selectedCourse);
                    selectedCount++;
                    Toast.makeText(Courses.this, "Course selected. Total courses: " + selectedCount, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Courses.this, "You can only select 5 courses.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void saveSelectedCourses(List<String> courses) {
        String key = mUser.getUid();
        DatabaseReference userRef = rootdatabaseref.child("Students").child(key);
        DatabaseReference coursesRef = userRef.child("Courses");
        coursesRef.removeValue();

        for (String course : courses) {
            coursesRef.child(course).setValue(true);
        }

        // Update ViewModel with selected courses
        coursesViewModel.setSelectedCourses(courses);


        Toast.makeText(Courses.this, "Courses saved successfully!", Toast.LENGTH_SHORT).show();
    }

}
