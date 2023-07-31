package com.example.dgjung_nycschools.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.dgjung_nycschools.data.School;
import com.example.dgjung_nycschools.data.SchoolItem;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

// ViewModel class. Request data to Repository and save them into LiveData.
// Search school list by user input keyword.
public class SchoolViewModel extends ViewModel implements Repository.RepoListener {
    Repository repository;
    List<SchoolItem> schoolItems;
    public MutableLiveData<List<SchoolItem>> lvSchoolItems = new MutableLiveData<>();
    public MutableLiveData<School> lvCurrSchool = new MutableLiveData<>();

    // Constructor. Get instance of Repository and set event listener.
    @Inject
    public SchoolViewModel(Repository repository) {
        this.repository = repository;
        repository.setListener(this);
    }

    // Request school list data to Repository
    public void reqSchools() {
        repository.reqSchools();
    }

    // Request a detail school information to Repository
    public void reqSchool(String dbn) {
        repository.reqSchool(dbn);
    }

    // Search school list by user input keyword
    public void searchName(String keyword) {
        // When keyword is empty, return full school list
        keyword = keyword.trim();
        if(keyword.length() < 1) {
            lvSchoolItems.postValue(schoolItems);
            return;
        }

        // Filtering school list by keyword
        lvSchoolItems.postValue(null);
        Observable.just(keyword.toLowerCase())
                .subscribeOn(Schedulers.newThread())
                .map(key -> {
                    ArrayList<SchoolItem> filteredList = new ArrayList<>();
                    // When school name contains keyword, add school data in filtered list
                    for (SchoolItem schoolItem : schoolItems) {
                        if(schoolItem != null && schoolItem.school_name.toLowerCase().contains(key)) {
                            filteredList.add(schoolItem);
                        }
                    }
                    return filteredList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                // Save filtered school list to LiveData
                .subscribe(list -> lvSchoolItems.postValue(list));
    }

    // Get all school list from Repository
    @Override
    public void onResponseSchools(List<SchoolItem> schoolItems) {
        this.schoolItems = schoolItems;
        lvSchoolItems.postValue(schoolItems);
    }

    // Get a detail school information from Repository
    @Override
    public void onResponseSchool(School school) {
        lvCurrSchool.postValue(school);
    }
}
