package com.f.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.f.myapplication.GPSTracker;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {

        mText = new MutableLiveData<>();

        mText.setValue("Weather Buddy fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}