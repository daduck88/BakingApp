package com.bakingapp.android;

import android.app.Application;

import com.bakingapp.android.model.service.ServiceConfig;
import com.bakingapp.android.model.service.RecipesService;
import com.bakingapp.android.model.service.repository.RecipesDataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private static final String STOCK_SERVICE_ENDPOINT = "https://d17h27t6h515a5.cloudfront.net";

    private final Application application;
    private final ServiceConfig serviceConfig;

    AppModule(Application application) {
        this.application = application;
        serviceConfig = new ServiceConfig(STOCK_SERVICE_ENDPOINT);
    }

    @Provides
    @Singleton
    RecipesDataRepository provideRecipesRepository() {
        RecipesService recipesService = new RecipesService(serviceConfig);
        return new RecipesDataRepository(recipesService);
    }


}
