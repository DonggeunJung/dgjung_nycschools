package com.example.dgjung_nycschools.view;

import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dgjung_nycschools.App;
import com.example.dgjung_nycschools.viewmodel.SchoolViewModel;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {
    @Inject
    protected SchoolViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.diComponent.inject(this);
        super.onCreate(savedInstanceState);
    }

    void hideKeypad(EditText et) {
        InputMethodManager mImm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        mImm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
}
