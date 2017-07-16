package com.ngcourse.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.ngcourse.R;

/**
 * Created by piyush on 16/7/17.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText mobileNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manual_login);
        initView();
    }

    private void initView() {
        mobileNumber = (EditText) findViewById(R.id.mobile_number);
    }
}
