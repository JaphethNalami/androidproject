package com.example.examinations.ui.special;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpecialViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    public SpecialViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is special fragment");
    }
    public MutableLiveData<String> getText() {
        return mText;
    }
}