package id.co.imastudio.bakingapp3.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import id.co.imastudio.bakingapp3.Ingredient;
import id.co.imastudio.bakingapp3.R;
import id.co.imastudio.bakingapp3.RecipeModel;

import static id.co.imastudio.bakingapp3.MainActivity.SELECTED_RECIPE;

/**
 * Created by idn on 8/17/2017.
 */

public class RecipeDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "RecipeDataProvider";
    List<Ingredient> ingredientList = new ArrayList<>();
    Context context;
    Intent intent;
    int posisiResep;

    void initData(){
//        String sRecipe = intent.getStringExtra(SELECTED_RECIPE);
//        Recipe recipe = new GsonBuilder().create().fromJson(sRecipe, Recipe.class);
//        ingredientList.addAll(recipe.getIngredients());

//        recipeList = intent.getParcelableArrayListExtra(SELECTED_RECIPE);
//        posisiResep = intent.getIntExtra(POSISIRESEP, 0);
        

        String sRecipe = intent.getStringExtra(SELECTED_RECIPE);
        RecipeModel recipe = new GsonBuilder().create().fromJson(sRecipe, RecipeModel.class);
        ingredientList.addAll(recipe.getIngredients());
        Log.d(TAG, "initData: "+ingredientList);
    }

    public RecipeDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        initData();
        Log.d(TAG, "onCreate: RDP");
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = ingredientList.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
        views.setTextViewText(android.R.id.text1, String.format(context.getString(R.string.ingredients_detail)
                , ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
        Log.d(TAG, "getViewAt: "+ingredient.getMeasure());
        return views;    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
