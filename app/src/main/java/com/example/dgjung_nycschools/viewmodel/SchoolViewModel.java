package com.example.dgjung_nycschools.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.example.dgjung_nycschools.data.School;
import com.example.dgjung_nycschools.data.SchoolItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchoolViewModel extends ViewModel implements Repository.RepoListener {
    static Context context;
    Repository repository;
    List<SchoolItem> schoolItems;
    public MutableLiveData<List<SchoolItem>> lvSchoolItems = new MutableLiveData<>();
    public MutableLiveData<School> lvCurrSchool = new MutableLiveData<>();

    public SchoolViewModel() {
        repository = Repository.getInstance(context);
        repository.setListener(this);
    }

    public static SchoolViewModel getInstance(Context context) {
        SchoolViewModel.context = context;
        return new ViewModelProvider((ViewModelStoreOwner) context, ViewModelFactory.provideViewModelFactory()).get(SchoolViewModel.class);
    }

    public void reqSchools() {
        repository.reqSchools();
    }

    public void reqSchool(String dbn) {
        repository.reqSchool(dbn);
    }

    public void searchName(String keyword) {
        keyword = keyword.trim();
        if(keyword.length() < 1) {
            lvSchoolItems.postValue(schoolItems);
            return;
        }
        lvSchoolItems.postValue(null);
        Observable.just(keyword.toLowerCase())
                .subscribeOn(Schedulers.newThread())
                .map(key -> {
                    ArrayList<SchoolItem> filteredList = new ArrayList<>();
                    // Save Score data one by one into School DB
                    for (SchoolItem schoolItem : schoolItems) {
                        if(schoolItem != null && schoolItem.school_name.toLowerCase().contains(key)) {
                            filteredList.add(schoolItem);
                        }
                    }
                    return filteredList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> lvSchoolItems.postValue(list));
    }

    @Override
    public void onResponseSchools(List<SchoolItem> schoolItems) {
        this.schoolItems = schoolItems;
        lvSchoolItems.postValue(schoolItems);
    }

    @Override
    public void onResponseSchool(School school) {
        lvCurrSchool.postValue(school);
    }
}
