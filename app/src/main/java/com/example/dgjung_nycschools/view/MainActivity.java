package com.example.dgjung_nycschools.view;

import android.os.Bundle;
import android.view.View;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.dgjung_nycschools.App;
import com.example.dgjung_nycschools.R;
import com.example.dgjung_nycschools.data.SchoolItem;
import com.example.dgjung_nycschools.databinding.ActivityMainBinding;
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    @Inject
    SchoolRvAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.diComponent.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initList();
    }

    // Init RecyclerView adapter & Request School list to server
    public void initList() {
        adapter.setObserver(this, viewModel);
        binding.rvSchool.setAdapter(adapter);
        binding.rvSchool.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.rvSchool.addItemDecoration(new DividerItemDecoration(this, 1));

        viewModel.reqSchools();

        final Observer<List<SchoolItem>> schoolItemsObserver = schoolItems -> {
            if(schoolItems == null || schoolItems.size() < 1) {
                binding.pbWait.setVisibility(View.VISIBLE);
            } else {
                binding.pbWait.setVisibility(View.GONE);
            }
        };
        viewModel.lvSchoolItems.observe(this, schoolItemsObserver);
    }

    public void onBtnSearch(View v) {
        String keyword = binding.etSearch.getText().toString();
        viewModel.searchName(keyword);
        hideKeypad(binding.etSearch);
    }

}
