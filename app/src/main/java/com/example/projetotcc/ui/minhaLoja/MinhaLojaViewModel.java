package com.example.projetotcc.ui.minhaLoja;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MinhaLojaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MinhaLojaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}