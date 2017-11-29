package com.bakingapp.android;

import android.app.Application;
import android.content.Context;

import lombok.Getter;

/**
 * Created by Daduck on 11/28/17.
 */

public class App extends Application {

    @Getter
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public static AppComponent getAppComponent(Context context) {
        App myApp = get(context.getApplicationContext());
        return myApp.getAppComponent();
    }
}
