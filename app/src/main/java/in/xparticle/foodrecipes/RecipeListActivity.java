package in.xparticle.foodrecipes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.xparticle.foodrecipes.ViewModels.RecipeListViewModel;
import in.xparticle.foodrecipes.adapters.OnRecipeListener;
import in.xparticle.foodrecipes.adapters.RecipeRecyclerAdapter;
import in.xparticle.foodrecipes.models.Recipe;
import in.xparticle.foodrecipes.requests.RecipeApi;
import in.xparticle.foodrecipes.requests.ServiceGenerator;
import in.xparticle.foodrecipes.requests.responses.RecipeResponse;
import in.xparticle.foodrecipes.requests.responses.RecipeSearchResponse;
import in.xparticle.foodrecipes.util.Constants;
import in.xparticle.foodrecipes.util.Testing;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel mRecipeListViewModel;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);

        mRecipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        testRetrofitRequest();
    }

    private void subscribeObservers(){

        mRecipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if(recipes != null){
                    Testing.printRecipes("network test", recipes);
                    mAdapter.setRecipes(recipes);
                }
            }
        });
    }

    private void testRetrofitRequest(){
        mRecipeListViewModel.searchRecipesApi("chicken", 1);
    }

    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRecipeClick(int position) {
        Log.d(TAG, "onRecipeClick: clicked. " + position);
    }

    @Override
    public void onCategoryClick(int position) {

    }
}
