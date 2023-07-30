package com.example.dgjung_nycschools.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import android.view.View;
import com.example.dgjung_nycschools.R;
import com.example.dgjung_nycschools.data.School;
import com.example.dgjung_nycschools.databinding.ActivitySchoolBinding;

public class SchoolActivity extends BaseActivity {
    ActivitySchoolBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_school);

        initData();
    }

    void initData() {
        String dbn = this.getIntent().getStringExtra(getString(R.string.tag_dbn));
        viewModel.reqSchool(dbn);

        final Observer<School> schoolObserver = school -> {
            binding.setSchool(school);
            binding.tvAcademicopportunities.setText(school.getAcademicopportunities());
            binding.tvPrgdesc.setText(school.getPrgdesc());
            binding.tvDirections.setText(school.getDirections());
        };
        viewModel.lvCurrSchool.observe(this, schoolObserver);
    }

    public void onBtnBack(View v) {
        finish();
    }
}
