package com.example.dgjung_nycschools.data;

import androidx.room.Entity;

@Entity
public class SchoolScore extends SchoolItem {
    public String num_of_sat_test_takers;
    public String sat_critical_reading_avg_score;
    public String sat_math_avg_score;
    public String sat_writing_avg_score;

    public SchoolScore(String dbn, String school_name, String location) {
        super(dbn, school_name, location);
    }

}
