package com.example.examinations.ui.courses;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.examinations.databinding.FragmentCoursesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class CoursesFragment extends Fragment {

    private FragmentCoursesBinding binding;
    private CoursesViewModel coursesViewModel;

    Button btn_courses;
    TextView text_courses;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCoursesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            coursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);
            coursesViewModel.retrieveSelectedCoursesFromFirebase(userId);

            final TextView textView = binding.textCourses;
            coursesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

            btn_courses = root.findViewById(R.id.btn_courses);
            text_courses = root.findViewById(R.id.text_courses);

            List<String> selectedCourses = coursesViewModel.getSelectedCourses().getValue();
            if (selectedCourses != null && !selectedCourses.isEmpty()) {
                // User has registered for courses, show the list
                btn_courses.setVisibility(View.GONE); // Hide the registration button
                text_courses.setVisibility(View.GONE); //Hide the registration text

                // Display registered courses in TextViews
                for (int i = 0; i < selectedCourses.size(); i++) {
                    String courseId = "course" + (i + 1);
                    int textViewId = getResources().getIdentifier(courseId, "id", requireActivity().getPackageName());
                    TextView courseTextView = root.findViewById(textViewId);
                    courseTextView.setText(selectedCourses.get(i));
                }
            } else {
                // No courses selected, show the registration text and button
                text_courses.setText("Register for your Courses.");
                btn_courses.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), Courses.class);
                    startActivity(intent);
                });
            }
        }

        return root;
    }
}
