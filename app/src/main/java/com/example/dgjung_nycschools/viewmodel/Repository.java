package com.example.dgjung_nycschools.viewmodel;

import android.util.Log;
import com.example.dgjung_nycschools.App;
import com.example.dgjung_nycschools.data.School;
import com.example.dgjung_nycschools.data.SchoolItem;
import com.example.dgjung_nycschools.data.SchoolScore;
import com.example.dgjung_nycschools.model.SchoolApi;
import com.example.dgjung_nycschools.model.SchoolDao;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Repository class. Get data from Retrofit API and Room DB and reply requests from ViewModel
public class Repository {
    @Inject
    SchoolApi api;
    @Inject
    SchoolDao schoolDao;

    // Constructor. Get Retrofit API & Room DAO instances.
    @Inject
    public Repository() {
        App.diComponent.inject(this);
    }

    // Read all school list from DB. If no data call Retrofit API
    public void reqSchools() {
        if(listener == null) return;
        Observable.just(schoolDao)
                .subscribeOn(Schedulers.newThread())
                .map(dao -> {
                    // Read all school list from DB
                    List<SchoolItem> schoolItems = dao.getSchoolItems();
                    if(schoolItems != null && schoolItems.size() > 0) {
                        return schoolItems;
                    }
                    // If no data call Retrofit API
                    reqSchoolsApi();
                    return schoolItems;
                })
                .observeOn(AndroidSchedulers.mainThread())
                // Send school list to ViewModel
                .subscribe(schoolItems -> listener.onResponseSchools(schoolItems));
    }

    // Request all school list to API
    void reqSchoolsApi() {
        Call<List<School>> call = api.getSchools();
        call.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                // Get all school list
                List<School> schools = response.body();
                if(listener != null) {
                    // Type cast detail school data to super class for list item
                    ArrayList<SchoolItem> schoolItems = new ArrayList<>();
                    for(School school : schools) {
                        schoolItems.add((SchoolItem)school);
                    }
                    // Send all school list to ViewModel
                    listener.onResponseSchools(schoolItems);
                }
                // Save all school list to DB
                setSchoolsDB(schools);
                // Request all sat score data to API
                reqScoresApi();
            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });
    }

    // Save all School list to DB
    void setSchoolsDB(List<School> schools) {
        Observable.just(schools)
                .subscribeOn(Schedulers.newThread())
                .map(list -> {
                    // Delete current School data in DB
                    schoolDao.delSchools();
                    // Save new School data one by one
                    for (School school : schools) {
                        schoolDao.insert(school);
                    }
                    return schools.size();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    // Search school data in DB.
    public void reqSchool(String dbn) {
        Observable.just(dbn)
                .subscribeOn(Schedulers.newThread())
                .map(key -> {
                    // Search data in School table by dbn
                    List<School> schools = schoolDao.getSchoolByDbn(key);
                    if(schools == null || schools.size() < 1) return null;
                    return schools.get(0);
                })
                .observeOn(AndroidSchedulers.mainThread())
                // Send result to ViewModel
                .subscribe(school -> listener.onResponseSchool(school));
    }

    // Request all sat score data to API
    void reqScoresApi() {
        Call<List<SchoolScore>> call = api.getScores();
        call.enqueue(new Callback<List<SchoolScore>>() {
            @Override
            public void onResponse(Call<List<SchoolScore>> call, Response<List<SchoolScore>> response) {
                // Save all sat score data to DB
                setScoresDB(response.body());
            }

            @Override
            public void onFailure(Call<List<SchoolScore>> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });
    }

    // Save all sat score data to DB
    void setScoresDB(List<SchoolScore> schoolScores) {
        Observable.just(schoolScores)
                .subscribeOn(Schedulers.newThread())
                .map(list -> {
                    // Save Score data one by one into School DB
                    for (SchoolScore schoolScore : list) {
                        schoolDao.updateScore(schoolScore.dbn, schoolScore.num_of_sat_test_takers
                                , schoolScore.sat_critical_reading_avg_score, schoolScore.sat_math_avg_score
                                , schoolScore.sat_writing_avg_score);
                    }
                    return schoolScores.size();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    // Interface for sending data from Repository to ViewModel
    public interface RepoListener {
        // Get all school list from Repository
        void onResponseSchools(List<SchoolItem> schoolItems);

        // Get a detail school information from Repository
        void onResponseSchool(School school);
    }

    // Instance of event listener interface
    private RepoListener listener = null;

    // Set instance of event listener interface
    public void setListener(RepoListener listener) {
        this.listener = listener;
    }

}
