package com.example.dgjung_nycschools.di;

import com.example.dgjung_nycschools.view.BaseActivity;
import com.example.dgjung_nycschools.view.MainActivity;
import com.example.dgjung_nycschools.viewmodel.Repository;
import javax.inject.Singleton;
import dagger.Component;

// Dependency Injection Component interface.
@Singleton
@Component(modules = {DiModule.class})
public interface DiComponent {
    // Inject instances into Repository
    void inject(Repository repository);

    // Inject instances into BaseActivity
    void inject(BaseActivity activity);

    // Inject instances into MainActivity
    void inject(MainActivity activity);
}
