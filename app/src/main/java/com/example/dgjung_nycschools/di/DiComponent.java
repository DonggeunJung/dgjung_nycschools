package com.example.dgjung_nycschools.di;

import com.example.dgjung_nycschools.view.BaseActivity;
import com.example.dgjung_nycschools.view.MainActivity;
import com.example.dgjung_nycschools.viewmodel.Repository;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {DiModule.class})
public interface DiComponent {
    void inject(Repository repository);

    void inject(BaseActivity activity);

    void inject(MainActivity activity);
}
