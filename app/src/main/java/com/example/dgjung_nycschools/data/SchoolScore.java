package com.example.dgjung_nycschools.data;

import androidx.room.Entity;

// School data class for sat scores. Inherits from SchoolItem class.
@Entity
public class SchoolScore extends SchoolItem {
    public String num_of_sat_test_takers;
    public String sat_critical_reading_avg_score;
    public String sat_math_avg_score;
    public String sat_writing_avg_score;

    public String sat_total() {
        if(sat_critical_reading_avg_score == null || sat_math_avg_score == null || sat_writing_avg_score == null)
            return null;
        int total = Integer.parseInt(sat_critical_reading_avg_score) + Integer.parseInt(sat_math_avg_score) + Integer.parseInt(sat_writing_avg_score);
        return Integer.toString(total);
    }

}
