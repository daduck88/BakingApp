package com.bakingapp.android.data.net;

import com.bakingapp.android.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by destevancardozoj on 11/8/17.
 */

public interface BakingService {
  @GET("topher/2017/May/59121517_baking/baking.json")
  Call<List<Recipe>> getRecipies();
}
