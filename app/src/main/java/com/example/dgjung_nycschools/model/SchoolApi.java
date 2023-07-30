package com.example.dgjung_nycschools.model;

import com.example.dgjung_nycschools.data.School;
import com.example.dgjung_nycschools.data.SchoolScore;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface SchoolApi {
    String BASE_URL = "https://data.cityofnewyork.us/resource/";

    // Make Retrofit object
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SchoolApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
            .build();

    @GET("s3k6-pzi2.json")
    Call<List<School>> getSchools();

    @GET("f9bf-2cp4.json")
    Call<List<SchoolScore>> getScores();

}
