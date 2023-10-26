package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Courses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        ListView listView = findViewById(R.id.listview);

        List<String> list = new ArrayList<>();
        list.add("Basic Statistics 136");
        list.add("Calculus I 108");
        list.add("Calculus II 109");
        list.add("Chemistry for Non-Majors 100");
        list.add("Communication Skills 154");
        list.add("English Literature 101");
        list.add("General Biology 131");
        list.add("History 123");
        list.add("Introduction to Anthropology 149");
        list.add("Introduction to Art History 144");
        list.add("Introduction to Business 111");
        list.add("Introduction to Computer Science 106");
        list.add("Introduction to Environmental Science 139");
        list.add("Introduction to Film Studies 141");
        list.add("Introduction to Philosophy 135");
        list.add("Introduction to Political Science 130");
        list.add("Introduction to Psychology 131");
        list.add("Introduction to Sociology 132");
        list.add("Mathematics 105");
        list.add("Physical Education 110");
        list.add("Principles of Economics 111");
        list.add("Public Speaking 155");
        list.add("Theater Arts 140");
        list.add("World History 151");
        list.add("Astronomy 250");
        list.add("Calculus III 208");
        list.add("Calculus IV 209");
        list.add("Communication Skills 254");
        list.add("Criminal Justice 221");
        list.add("Dance 241");
        list.add("Data Science 231");
        list.add("Data Structures and Algorithms 240");
        list.add("Economics 211");
        list.add("Environmental Science 239");
        list.add("Film Production 241");
        list.add("Geology 231");
        list.add("Graphic Design 211");
        list.add("International Business 212");
        list.add("Intermediate-level Business Management 218");
        list.add("Introduction to Comparative Politics 222");
        list.add("Introduction to Cultural Anthropology 251");
        list.add("Introduction to Ethics 259");
        list.add("Introduction to Media Studies 257");
        list.add("Introduction to Microeconomics 212");
        list.add("Introduction to Macroeconomics 213");
        list.add("Psychology 231");
        list.add("Sociology 232");
        list.add("Marketing 215");
        list.add("Music Theory 220");
        list.add("Nutrition Science 234");
        list.add("Philosophy 235");
        list.add("Photography 242");
        list.add("Physics 202");
        list.add("Principles of Marketing 214");
        list.add("Public Relations 221");
        list.add("Spanish Language 214");
        list.add("Sociology 200");
        list.add("Biochemistry 301");
        list.add("Bioinformatics 301");
        list.add("Digital Marketing 301");
        list.add("Machine Learning 301");
        list.add("Chemistry 301");
        list.add("Artificial Intelligence 301");
        list.add("Foreign Policy Studies 301");
        list.add("Statics 301");
        list.add("Civil Engineering 301");
        list.add("Healthcare Management 301");
        list.add("Mechanical Engineering 301");
        list.add("Electrical Engineering 301");
        list.add("Human Resource Management 301");
        list.add("Environment Engineering 301");
        list.add("Political Science 301");
        list.add("Neuroscience 301");
        list.add("Nursing 301");
        list.add("Anthropology 301");
        list.add("Communication Skills 321");
        list.add("Engineering Ethics 401");
        list.add("Finance 401");
        list.add("Medical Ethics 401");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
    }
}