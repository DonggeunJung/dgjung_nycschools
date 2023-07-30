package com.example.dgjung_nycschools.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    public static ViewModelFactory provideViewModelFactory() {
        return new ViewModelFactory();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == SchoolViewModel.class) {
            return (T)new SchoolViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}");
    }
}
