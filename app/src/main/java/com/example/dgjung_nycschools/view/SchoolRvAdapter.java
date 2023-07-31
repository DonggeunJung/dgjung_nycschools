package com.example.dgjung_nycschools.view;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dgjung_nycschools.R;
import com.example.dgjung_nycschools.data.SchoolItem;
import com.example.dgjung_nycschools.databinding.SchoolItemBinding;
import com.example.dgjung_nycschools.viewmodel.SchoolViewModel;
import java.util.List;

// RecyclerView Adapter class of school list view
public class SchoolRvAdapter extends RecyclerView.Adapter<SchoolRvAdapter.SchoolVH> {
    SchoolViewModel viewModel;
    List<SchoolItem> schoolItems;
    AppCompatActivity activity;

    // Starts observing school list data in ViewModel
    public void setObserver(AppCompatActivity activity, SchoolViewModel viewModel) {
        this.activity = activity;
        this.viewModel = viewModel;
        final Observer<List<SchoolItem>> schoolItemsObserver = schoolItems -> {
            this.schoolItems = schoolItems;
            this.notifyDataSetChanged();
        };
        viewModel.lvSchoolItems.observe(activity, schoolItemsObserver);
    }

    // Return list items count
    @Override
    public int getItemCount() {
        return (schoolItems == null) ? 0 : schoolItems.size();
    }

    // Make ViewHolder & View binding object
    @NonNull
    @Override
    public SchoolVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get the view binding object of custom list item layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SchoolItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.school_item, parent, false);
        // Set the Lifecycle Owner of View binding to fragment
        binding.setLifecycleOwner(activity);
        // Return ViewHolder object
        return new SchoolVH(activity, binding);
    }

    // When ViewHolder is binded, set data to binding object
    @Override
    public void onBindViewHolder(@NonNull SchoolVH holder, int position) {
        holder.bind(schoolItems.get(position));
    }

    // ViewHolder class of school item
    public static class SchoolVH extends RecyclerView.ViewHolder {
        AppCompatActivity activity;
        public SchoolItemBinding binding;
        SchoolItem schoolItem;

        // Constructor. Get Activity & DataBinding instances.
        public SchoolVH(AppCompatActivity activity, SchoolItemBinding binding) {
            super(binding.getRoot());
            this.activity = activity;
            this.binding = binding;
        }

        // Get school data and send it to UI layout.
        public void bind(SchoolItem schoolItem) {
            this.schoolItem = schoolItem;
            binding.setSchoolItem(schoolItem);
            // When user click a school item, launch detail information Activity
            binding.layoutMain.setOnClickListener(v -> {
                Intent intent = new Intent(activity, SchoolActivity.class);
                intent.putExtra(activity.getString(R.string.tag_dbn), schoolItem.dbn);
                activity.startActivity(intent);
            });
        }
    }
}
