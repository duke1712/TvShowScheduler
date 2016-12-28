package com.pritesh.tvshowscheduler.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pritesh.tvshowscheduler.BuildConfig;
import com.pritesh.tvshowscheduler.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        TextView textView=(TextView)findViewById(R.id.version1);
        textView.setText(BuildConfig.VERSION_NAME);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
