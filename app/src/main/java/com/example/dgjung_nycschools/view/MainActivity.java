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

// Main Activity class
public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    @Inject
    SchoolRvAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Get instance of RecyclerView adapter
        App.diComponent.inject(this);
        super.onCreate(savedInstanceState);
        // Get DataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Init RecyclerView adapter & Request School list to server
        initList();
    }

    // Init RecyclerView adapter & Request School list to server
    public void initList() {
        // Adapter starts observing school list data in ViewModel
        adapter.setObserver(this, viewModel);
        // Set adapter to RecyclerView
        binding.rvSchool.setAdapter(adapter);
        binding.rvSchool.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        binding.rvSchool.addItemDecoration(new DividerItemDecoration(this, 1));

        // Request school list data to ViewModel
        viewModel.reqSchools();

        // Start observing school list data in ViewModel for switching ProgressBar visible state
        final Observer<List<SchoolItem>> schoolItemsObserver = schoolItems -> {
            if(schoolItems == null || schoolItems.size() < 1) {
                binding.pbWait.setVisibility(View.VISIBLE);
            } else {
                binding.pbWait.setVisibility(View.GONE);
            }
        };
        viewModel.lvSchoolItems.observe(this, schoolItemsObserver);
    }

    // When user input keyword and press search button, request filtered school list to ViewModel
    public void onBtnSearch(View v) {
        String keyword = binding.etSearch.getText().toString();
        viewModel.searchName(keyword);
        // Hide keypad of EditText
        hideKeypad(binding.etSearch);
    }

}
