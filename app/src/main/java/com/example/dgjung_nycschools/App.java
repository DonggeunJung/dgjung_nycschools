package com.example.dgjung_nycschools;

import android.app.Application;
import android.content.Context;
import com.example.dgjung_nycschools.di.DaggerDiComponent;
import com.example.dgjung_nycschools.di.DiComponent;

// Application class. Contains global variables.
public class App extends Application {
    public static Context context;
    public static DiComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        // Global variable - context
        context = this;
        // Global variable - Dagger Component
        diComponent = DaggerDiComponent.builder().build();
    }
}