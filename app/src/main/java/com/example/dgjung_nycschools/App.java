package com.example.dgjung_nycschools;

import android.app.Application;
import android.content.Context;
import com.example.dgjung_nycschools.di.DaggerDiComponent;
import com.example.dgjung_nycschools.di.DiComponent;

public class App extends Application {
    public static Context context;
    public static DiComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        diComponent = DaggerDiComponent.builder().build();
    }
}