package com.example.dgjung_nycschools.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.dgjung_nycschools.data.School;

// Define Room database - Data class, DB version, DB DAO
@Database(entities = {School.class}, version = 1, exportSchema = false)
public abstract class SchoolDB extends RoomDatabase {
    // Define DB DAO
    public abstract SchoolDao schoolDao();
}
