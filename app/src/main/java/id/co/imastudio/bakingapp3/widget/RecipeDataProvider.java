package id.co.imastudio.bakingapp3.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import static id.co.imastudio.bakingapp3.widget.RecipeWidget.ingredientforwidget;


/**
 * Created by idn on 8/17/2017.
 */

public class RecipeDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "RecipeDataProvider";
//    List<Ingredient> ingredientList = new ArrayList<>();
    Context context;
    Intent intent;

    List<String> remoteViewingredientsList;

    void initData(){
//        String sRecipe = intent.getStringExtra(SELECTED_RECIPE);
//        RecipeModel recipe = new GsonBuilder().create().fromJson(sRecipe, RecipeModel.class);
//        ingredientList.addAll(recipe.getIngredients());
//        Log.d(TAG, "initData: "+ingredientList);
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
//        onCreate();
//        Log.d(TAG, "onDataSetChanged: called");
//        String sRecipe = intent.getStringExtra(SELECTED_RECIPE);
//
//        RecipeModel recipe = new GsonBuilder().create().fromJson(sRecipe, RecipeModel.class);
//        ingredientList.addAll(recipe.getIngredients());
//        Log.d(TAG, "initData: "+ingredientList);
//        for (int i = 0; i < recipe.getIngredients().size(); i++) {
//            Log.d(TAG, "initData: "+recipe.getIngredients().get(i).getIngredient());
//        }
        remoteViewingredientsList = ingredientforwidget;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
//        return ingredientList.size();
        return remoteViewingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
//        Ingredient ingredient = ingredientforwidget.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), android.R.layout.simple_list_item_1);
//        views.setTextViewText(android.R.id.text1, String.format(context.getString(R.string.ingredients_detail)
//                , ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
        views.setTextViewText(android.R.id.text1, remoteViewingredientsList.get(position));
        Log.d(TAG, "getViewAt: "+position+ingredientforwidget);
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
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
