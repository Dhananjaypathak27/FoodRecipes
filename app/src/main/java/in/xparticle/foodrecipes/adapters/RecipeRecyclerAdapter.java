package in.xparticle.foodrecipes.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import in.xparticle.foodrecipes.R;
import in.xparticle.foodrecipes.models.Recipe;
import in.xparticle.foodrecipes.util.Constants;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;
    private static final int EXHAUSTED_TYPE = 4;

    private List<Recipe> mRecipes;
    private OnRecipeListener mOnRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        mOnRecipeListener = onRecipeListener;
        mRecipes = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = null;
        switch (i){
            case RECIPE_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recipe_list_item, viewGroup, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            }

            case LOADING_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_list_item, viewGroup, false);
                return new LoadingViewHolder(view);
            }

            case EXHAUSTED_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_search_exhaused, viewGroup, false);
                return new SearchExhaustedViewHolder(view);
            }
            case CATEGORY_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category_list_item, viewGroup, false);
                return new CategoryViewHolder(view, mOnRecipeListener);
            }
            default:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recipe_list_item, viewGroup, false);
                return new RecipeViewHolder(view, mOnRecipeListener);
            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        int itemViewType = getItemViewType(i);
        if(itemViewType == RECIPE_TYPE){

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Glide.with(((RecipeViewHolder) viewHolder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(mRecipes.get(i).getImage_url())
                    .into(((RecipeViewHolder) viewHolder).image);

            ((RecipeViewHolder) viewHolder).title.setText(mRecipes.get(i).getTitle());
            ((RecipeViewHolder) viewHolder).publisher.setText(mRecipes.get(i).getPublisher());
            ((RecipeViewHolder) viewHolder).socialScore.setText(String.valueOf(Math.round(mRecipes.get(i).getSocial_rank())));
        }
        else if(itemViewType == CATEGORY_TYPE){

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background);

            Uri path = Uri.parse("android.resource://in.xparticle.foodrecipes/drawable/" + mRecipes.get(i).getImage_url());
            Glide.with(((CategoryViewHolder) viewHolder).itemView)
                    .setDefaultRequestOptions(options)
                    .load(path)
                    .into(((CategoryViewHolder) viewHolder).categoryImage);

            ((CategoryViewHolder) viewHolder).categoryTitle.setText(mRecipes.get(i).getTitle());

        }


    }


    @Override
    public int getItemViewType(int position) {

        if(mRecipes.get(position).getSocial_rank() == -1){
            return CATEGORY_TYPE;
        }
        else if(mRecipes.get(position).getTitle().equals("LOADING...")){
            return LOADING_TYPE;
        }
        else if(mRecipes.get(position).getTitle().equals("EXHAUSTED...")){
            return EXHAUSTED_TYPE;
        }
        else if(position == mRecipes.size() - 1
                && position != 0
                && !mRecipes.get(position).getTitle().equals("EXHAUSTED...")){
            return LOADING_TYPE;
        }
        else {
            return RECIPE_TYPE;
        }
    }

    public void setQueryExhausted(){
        hideLoading();
        Recipe exhaustedRecipe  = new Recipe();
        exhaustedRecipe.setTitle("EXHAUSTED...");
        mRecipes.add(exhaustedRecipe);
        notifyDataSetChanged();
    }

    private void hideLoading(){
        if(isLoading()){
            for(Recipe recipe:mRecipes){
                if(recipe.getTitle().equals("LOADING...")){
                    mRecipes.remove(recipe);
                }
            }
            notifyDataSetChanged();
        }
    }


    public void displayLoading(){
        if(!isLoading()){
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            mRecipes = loadingList;
            notifyDataSetChanged();;
        }

    }

    private boolean isLoading(){
        if(mRecipes != null){
            if(mRecipes.size() > 0 ){
                if(mRecipes.get(mRecipes.size() -1).getTitle().equals("LOADING...")){
                    return true;
                }
            }
        }

        return  false;
    }

    public void displaySearchCategories(){
        List<Recipe> categories  = new ArrayList<>();
        for(int i =0; i< Constants.DEFAULT_SEARCH_CATEGORIES.length; i++){
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setSocial_rank(-1);
            categories.add(recipe);
        }
        mRecipes = categories;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mRecipes !=null)
            return mRecipes.size();
        else
            return 0;
    }

    public void setRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public Recipe getSelectedRecipe(int position){
        if(mRecipes != null){
            if(mRecipes.size() > 0){
                return mRecipes.get(position);
            }
        }
        return null;
    }
}