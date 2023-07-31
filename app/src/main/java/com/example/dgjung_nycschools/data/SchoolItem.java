package com.example.dgjung_nycschools.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// School data class for list item.
@Entity
public class SchoolItem {
    @PrimaryKey
    @NonNull
    public String dbn;
    public String school_name;
    public String location;

}
