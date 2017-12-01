package com.bakingapp.android;

import com.bakingapp.android.recipes.RecipesFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(RecipesFragment fragment);
}
