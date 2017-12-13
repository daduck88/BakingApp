package com.bakingapp.android.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bakingapp.android.App;
import com.bakingapp.android.R;

public class BaseActivity extends AppCompatActivity {
  protected Toolbar mToolbar;
  protected boolean overrideOrientation = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    if(overrideOrientation) {
      if(App.isTablet()) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
      } else {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      }
    }
    super.onCreate(savedInstanceState);
  }

  protected void initToolbar() {
    mToolbar = findViewById(R.id.toolbar);
    setSupportActionBar(mToolbar);
  }
}
