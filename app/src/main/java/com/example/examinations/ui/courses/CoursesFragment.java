package com.example.examinations.ui.courses;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.examinations.Courses;
import com.example.examinations.R;
import com.example.examinations.databinding.ActivityCoursesBinding;
import com.example.examinations.databinding.FragmentCoursesBinding;

public class CoursesFragment extends Fragment {

    private FragmentCoursesBinding binding;

    Button btn_courses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CoursesViewModel coursesViewModel =
                new ViewModelProvider(this).get(CoursesViewModel.class);

        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCourses;
        coursesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        btn_courses = root.findViewById(R.id.btn_courses);

        btn_courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Courses.class);
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
