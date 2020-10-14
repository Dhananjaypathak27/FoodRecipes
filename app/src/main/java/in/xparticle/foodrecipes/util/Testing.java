package in.xparticle.foodrecipes.util;

import android.util.Log;

import java.util.List;

import in.xparticle.foodrecipes.models.Recipe;

public class Testing {


    public static void printRecipes(String tag, List<Recipe> list){
        for(Recipe recipe: list){
            Log.d(tag, "printRecipes: " + recipe.getRecipe_id() + ", " + recipe.getTitle());
        }
    }
}
