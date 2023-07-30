package com.example.dgjung_nycschools.viewmodel;

import android.content.Context;
import android.util.Log;
import com.example.dgjung_nycschools.data.School;
import com.example.dgjung_nycschools.data.SchoolItem;
import com.example.dgjung_nycschools.data.SchoolScore;
import com.example.dgjung_nycschools.model.SchoolApi;
import com.example.dgjung_nycschools.model.SchoolDB;
import com.example.dgjung_nycschools.model.SchoolDao;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    Context context;
    static Repository instance = null;
    SchoolApi api = SchoolApi.retrofit.create(SchoolApi.class);
    SchoolDao schoolDao;

    public Repository(Context context) {
        this.context = context;
        schoolDao = SchoolDB.getInstance(context).schoolDao();
    }

    public static Repository getInstance(Context context) {
        if(instance == null)
            instance = new Repository(context);
        return instance;
    }

    public void reqSchools() {
        if(listener == null) return;
        Observable.just(schoolDao)
                .subscribeOn(Schedulers.newThread())
                .map(dao -> {
                    List<SchoolItem> schoolItems = dao.getSchoolItems();
                    if(schoolItems != null && schoolItems.size() > 0) {
                        return schoolItems;
                    }
                    reqSchoolsApi();
                    return schoolItems;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(schoolItems -> listener.onResponseSchools(schoolItems));
    }

    void reqSchoolsApi() {
        Call<List<School>> call = api.getSchools();
        call.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                List<School> schools = response.body();
                if(listener != null) {
                    ArrayList<SchoolItem> schoolItems = new ArrayList<>();
                    for(School school : schools) {
                        SchoolItem schoolItem = new SchoolItem(school.dbn, school.school_name, school.location);
                        schoolItems.add(schoolItem);
                    }
                    listener.onResponseSchools(schoolItems);
                }
                setSchoolsDB(schools);
                reqScoresApi();
            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });
    }

    // Save School list to DB
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
                .subscribe(school -> listener.onResponseSchool(school));
    }

    void reqScoresApi() {
        Call<List<SchoolScore>> call = api.getScores();
        call.enqueue(new Callback<List<SchoolScore>>() {
            @Override
            public void onResponse(Call<List<SchoolScore>> call, Response<List<SchoolScore>> response) {
                setScoresDB(response.body());
            }

            @Override
            public void onFailure(Call<List<SchoolScore>> call, Throwable t) {
                Log.d("tag", t.getMessage());
            }
        });
    }

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

    public interface RepoListener {
        void onResponseSchools(List<SchoolItem> schoolItems);
        void onResponseSchool(School school);
    }

    private RepoListener listener = null;

    public void setListener(RepoListener listener) {
        this.listener = listener;
    }

}
