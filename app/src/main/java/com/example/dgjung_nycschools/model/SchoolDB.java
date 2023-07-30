package com.example.dgjung_nycschools.model;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.dgjung_nycschools.data.School;

@Database(entities = {School.class}, version = 1, exportSchema = false)
public abstract class SchoolDB extends RoomDatabase {
    public abstract SchoolDao schoolDao();
    static volatile SchoolDB instance = null;

    public static SchoolDB getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            SchoolDB.class, "book_db").build();
        }
        return instance;
    }

}
