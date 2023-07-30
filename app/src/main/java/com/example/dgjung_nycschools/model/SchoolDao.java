package com.example.dgjung_nycschools.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.dgjung_nycschools.data.School;
import com.example.dgjung_nycschools.data.SchoolItem;
import java.util.List;

@Dao
public interface SchoolDao {
    @Insert
    void insert(School school);

    // Get all data for School list
    @Query("SELECT dbn, school_name, location FROM School")
    List<SchoolItem> getSchoolItems();

    // Search data in School table by dbn
    @Query("SELECT * FROM School WHERE dbn LIKE :dbn || '%'")
    List<School> getSchoolByDbn(String dbn);

    // Delete all data in School table
    @Query("DELETE FROM School")
    void delSchools();

    @Query("UPDATE School SET num_of_sat_test_takers=:test_takers, sat_critical_reading_avg_score=:reading, sat_math_avg_score=:math, sat_writing_avg_score=:writing WHERE dbn=:dbn")
    void updateScore(String dbn, String test_takers, String reading, String math, String writing);

}
