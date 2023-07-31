package com.example.dgjung_nycschools.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.dgjung_nycschools.data.School;

@Database(entities = {School.class}, version = 1, exportSchema = false)
public abstract class SchoolDB extends RoomDatabase {
    public abstract SchoolDao schoolDao();
}
