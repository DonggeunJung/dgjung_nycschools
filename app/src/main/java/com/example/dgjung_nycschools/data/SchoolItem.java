package com.example.dgjung_nycschools.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SchoolItem {
    @PrimaryKey
    @NonNull
    public String dbn;
    public String school_name;
    public String location;

    public SchoolItem(String dbn, String school_name, String location) {
        this.dbn = dbn;
        this.school_name = school_name;
        this.location = location;
    }

}
