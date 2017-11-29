package com.bakingapp.android.model.service.repository;

import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.model.service.RecipesService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

//import timber.log.Timber;

public class RecipesDataRepository extends BaseRepository {

    private static final String CACHE_PREFIX_GET_RECIPES = "getRecipes";

    private final RecipesService service;

    public RecipesDataRepository(RecipesService service) {
        this.service = service;
    }

    public Observable<List<Recipe>> getRecipes() {
//        Timber.i("method: %s, symbol: %s", CACHE_PREFIX_GET_RECIPES, symbol);
        Observable<List<Recipe>> observableToCache = service
                .getRecipes().delay(3, TimeUnit.SECONDS).cache();
        return cacheObservable(CACHE_PREFIX_GET_RECIPES, observableToCache)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
