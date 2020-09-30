package in.xparticle.foodrecipes.requests;

import in.xparticle.foodrecipes.models.Recipe;
import in.xparticle.foodrecipes.requests.responses.RecipeResponse;
import in.xparticle.foodrecipes.requests.responses.RecipeSearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    //Search
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("key") String key,
            @Query("q") String query,
            @Query("page") String page
    );

    //Get recipe request
    @GET("api/get")
    Call<RecipeResponse> getRecipe(
        @Query("key") String key,
        @Query("rId") String recipe_id
    );




}
