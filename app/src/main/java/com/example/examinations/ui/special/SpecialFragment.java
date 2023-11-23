package com.example.examinations.ui.special;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.examinations.databinding.FragmentSpecialBinding;

public class SpecialFragment extends Fragment {

    private FragmentSpecialBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SpecialViewModel specialViewModel =
                new ViewModelProvider(this).get(SpecialViewModel.class);

        binding = FragmentSpecialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSpecial;
        specialViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}