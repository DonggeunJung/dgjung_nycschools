package com.example.dgjung_nycschools.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.example.dgjung_nycschools.R;
import com.example.dgjung_nycschools.data.School;
import com.example.dgjung_nycschools.databinding.ActivitySchoolBinding;

// School detail Activity class
public class SchoolActivity extends BaseActivity {
    ActivitySchoolBinding binding;
    School school;

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
            this.school = school;
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

    public void onBtnWebsite(View v) {
        String url = school.website;
        if(url == null || url.length() < 4) return;
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void onBtnPhone(View v) {
        String url = school.phone_number;
        if(url == null || url.length() < 4) return;
        if (!url.startsWith("tel:"))
            url = "tel:" + url;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void onBtnEmail(View v) {
        String url = school.school_email;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { url });
        startActivity(Intent.createChooser(intent, ""));
    }

    public void onBtnLocation(View v) {
        String url = school.location;
        int l = url.indexOf('(');
        int r = url.indexOf(')');
        if(l < 0 || r < 0) return;
        url = url.substring(l+1, r);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=" + url));
        startActivity(intent);
    }

}
