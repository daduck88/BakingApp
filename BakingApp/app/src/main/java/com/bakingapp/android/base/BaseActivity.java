package com.bakingapp.android.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bakingapp.android.R;

public class BaseActivity extends AppCompatActivity {
  protected boolean isTablet;
  protected Toolbar mToolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    isTablet = getResources().getBoolean(R.bool.isTablet);
    if(isTablet) {
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    } else {
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    super.onCreate(savedInstanceState);
  }

  protected void initToolbar() {
    mToolbar = findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);
  }
}
