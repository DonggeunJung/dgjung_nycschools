package com.example.dgjung_nycschools.di;

import androidx.room.Room;
import com.example.dgjung_nycschools.App;
import com.example.dgjung_nycschools.model.SchoolApi;
import com.example.dgjung_nycschools.model.SchoolDB;
import com.example.dgjung_nycschools.model.SchoolDao;
import com.example.dgjung_nycschools.view.SchoolRvAdapter;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

// Dependency Injection Module class.
@Module
public class DiModule {
    // Make instance of Retrofit API
    @Provides
    @Singleton
    SchoolApi provideSchoolApi() {
        return SchoolApi.retrofit.create(SchoolApi.class);
    }

    // Make instance of Database DAO
    @Provides
    @Singleton
    SchoolDao provideSchoolDao() {
        return Room.databaseBuilder(App.context, SchoolDB.class, "book_db").build().schoolDao();
    }

    // Make instance of RecyclerView adapter
    @Provides
    @Singleton
    SchoolRvAdapter provideSchoolRvAdapter() {
        return new SchoolRvAdapter();
    }

}
