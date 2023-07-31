package com.example.dgjung_nycschools.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import android.view.View;
import com.example.dgjung_nycschools.R;
import com.example.dgjung_nycschools.data.School;
import com.example.dgjung_nycschools.databinding.ActivitySchoolBinding;

// School detail Activity class
public class SchoolActivity extends BaseActivity {
    ActivitySchoolBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_school);

        // Request school detail information by DBN code
        initData();
    }

    // Request school detail information by DBN code
    void initData() {
        // Get DBN code from Intent
        String dbn = this.getIntent().getStringExtra(getString(R.string.tag_dbn));
        // Request school detail information to ViewModel
        viewModel.reqSchool(dbn);

        // Start observing school detail information
        final Observer<School> schoolObserver = school -> {
            binding.setSchool(school);
            binding.tvAcademicopportunities.setText(school.getAcademicopportunities());
            binding.tvPrgdesc.setText(school.getPrgdesc());
            binding.tvDirections.setText(school.getDirections());
        };
        viewModel.lvCurrSchool.observe(this, schoolObserver);
    }

    // When user press back button, close this Activity
    public void onBtnBack(View v) {
        finish();
    }
}
