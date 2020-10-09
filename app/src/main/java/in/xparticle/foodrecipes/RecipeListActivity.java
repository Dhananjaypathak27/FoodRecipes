package in.xparticle.foodrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.xparticle.foodrecipes.models.Recipe;
import in.xparticle.foodrecipes.requests.RecipeApi;
import in.xparticle.foodrecipes.requests.ServiceGenerator;
import in.xparticle.foodrecipes.requests.responses.RecipeResponse;
import in.xparticle.foodrecipes.requests.responses.RecipeSearchResponse;
import in.xparticle.foodrecipes.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {

    private static final String TAG = "RecipeListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofitRequest();
            }
        });

    }

    private void testRetrofitRequest(){
        final RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
//        Call<RecipeSearchResponse> responseCall = recipeApi
//                .searchRecipe(
//                        Constants.API_KEY,
//                        "chicken Breast",
//                        "1"
//                );
//
//        responseCall.enqueue(new Callback<RecipeSearchResponse>() {
//            @Override
//            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
//                Log.d(TAG, "onResponse: server response" + response.toString());
//                if(response.code() == 200){
//                    Log.d(TAG, "onResponse: "+ response.body().toString());
//                    List<Recipe> recipes = new ArrayList<>(response.body().getRecipes());
//                    for(Recipe recipe : recipes){
//                        Log.d(TAG, "onResponse: "+ recipe.getTitle());
//                    }
//                }
//                else{
//                    try{
//                        Log.d(TAG, "onResponse: "+ response.errorBody().string());
//                    }
//                    catch (IOException e){
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
//
//            }
//        });

        Call<RecipeResponse> responseCall = recipeApi
                .getRecipe(
                        Constants.API_KEY,
                        "35382"
                );
        responseCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.d(TAG, "onResponse: server response" + response.toString());
                if(response.code() == 200){
                    Log.d(TAG, "onResponse: "+ response.body().toString());
                    Recipe recipe = response.body().getRecipe();
                    Log.d(TAG, "onResponse: RETRIVE RECIPES"+ recipe.toString());
                }
                else{
                    try{
                        Log.d(TAG, "onResponse: "+ response.errorBody().string());
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    catch (Exception e){
                        Log.d(TAG, "onResponse: "+e.getMessage());
                    }

                }
            }
            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {

            }
        });
    }

}