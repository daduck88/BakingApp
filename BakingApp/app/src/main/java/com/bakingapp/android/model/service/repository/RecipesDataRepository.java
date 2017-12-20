package com.bakingapp.android.model.service.repository;

import android.content.ContentUris;
import android.net.Uri;
import android.util.Log;

import com.bakingapp.android.App;
import com.bakingapp.android.data.Ingredient;
import com.bakingapp.android.data.Recipe;
import com.bakingapp.android.data.Step;
import com.bakingapp.android.model.service.RecipesService;
import com.bakingapp.android.provider.ingredient.IngredientCursor;
import com.bakingapp.android.provider.ingredient.IngredientSelection;
import com.bakingapp.android.provider.recipe.RecipeContentValues;
import com.bakingapp.android.provider.recipe.RecipeCursor;
import com.bakingapp.android.provider.recipe.RecipeSelection;
import com.bakingapp.android.provider.step.StepCursor;
import com.bakingapp.android.provider.step.StepSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

//import timber.log.Timber;

public class RecipesDataRepository {

    private final RecipesService service;

    public RecipesDataRepository(RecipesService service) {
        this.service = service;
    }

    public Observable<List<Recipe>> getRecipes() {
//        Timber.i("method: %s, symbol: %s", CACHE_PREFIX_GET_RECIPES, symbol);
        return Observable.concatArray(getRecipesDB(), getRecipesAPI()
                .observeOn(AndroidSchedulers.mainThread()));
    }

    public Observable<List<Recipe>> getRecipesAPI() {
        Observable<List<Recipe>> observableAPI = service
            .getRecipes();
        observableAPI.flatMap((Function<List<Recipe>, ObservableSource<?>>) recipes -> {
            saveRecipesDB(recipes);
            return Observable.just(recipes);
        });
        return observableAPI;
    }

    private Observable<List<Recipe>> getRecipesDB() {
        Observable<List<Recipe>> observableDB = Observable.create(e -> {
            RecipeSelection recipeSelection = new RecipeSelection();
            RecipeCursor recipeCursor = recipeSelection.query(App.getContext());
            List<Recipe> recipes = new ArrayList<>();
            while(recipeCursor.moveToNext()){
                Recipe recipe = new Recipe(recipeCursor);
                StepSelection stepSelection = new StepSelection();
                stepSelection.idRecipe(recipe.getId());
                StepCursor stepCursor = stepSelection.query(App.getContext());
                recipe.setSteps(new ArrayList<>());
                while(stepCursor.moveToNext()){
                    Step step = new Step(stepCursor);
                    recipe.getSteps().add(step);
                }

                IngredientSelection ingredientSelection = new IngredientSelection();
                ingredientSelection.idRecipe(recipe.getId());
                IngredientCursor ingredientCursor = ingredientSelection.query(App.getContext());
                recipe.setIngredients(new ArrayList<>());
                while(ingredientCursor.moveToNext()){
                    Ingredient ingredient = new Ingredient(ingredientCursor);
                    recipe.getIngredients().add(ingredient);
                }
                recipes.add(recipe);
            }
            e.onNext(recipes);
            e.onComplete();
        });
        return observableDB;
    }

    private void saveRecipesDB(List<Recipe> recipes){
        for(Recipe recipe: recipes){
            Uri uri = recipe.insertUri();
            long recipeDBID =  ContentUris.parseId(uri);
            Log.e("TEMP ", "RECIPE inserted " + recipeDBID);
            for(Step step: recipe.getSteps()){
                Uri uriStep = step.insertUri(recipe.getId());
                long stepDBID =  ContentUris.parseId(uriStep);
                Log.e("TEMP ", "STEP inserted " + stepDBID);
            }
            for(Ingredient ingredient: recipe.getIngredients()){
                Uri uriIngredient = ingredient.insertUri(recipe.getId());
                long ingredientDBID =  ContentUris.parseId(uriIngredient);
                Log.e("TEMP ", "INGREDIENT inserted " + ingredientDBID);
            }
        }
    }
}
