package id.co.imastudio.bakingapp3.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import id.co.imastudio.bakingapp3.MockData;
import id.co.imastudio.bakingapp3.R;
import id.co.imastudio.bakingapp3.RecipeModel;

import static id.co.imastudio.bakingapp3.MainActivity.SELECTED_RECIPE;
import static id.co.imastudio.bakingapp3.widget.UpdateBakingService.FROM_ACTIVITY_INGREDIENTS_LIST;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {
    private static final String TAG = "RecipeWidget";
    private int posisiResepAwal = 0;
    static ArrayList<String> ingredientforwidget = new ArrayList<>();


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            Log.d(TAG, "onUpdate: Called");
////            RemoteViews mView = initViews(context, appWidgetManager, appWidgetId);
//            updateAppWidget(context, appWidgetManager, appWidgetId, posisiResepAwal);
////            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list);
////            appWidgetManager.updateAppWidget(appWidgetId, mView);
//        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager, int widgetId) {



        Intent intent = new Intent(context, RecipeService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        Type type = new TypeToken<List<RecipeModel>>() {
        }.getType();
        List<RecipeModel> recipes = new GsonBuilder().create().fromJson(MockData.DATA_1, type);
        Log.d("RecipeWidget", "initViews: " + recipes.get(posisiResepAwal).getName());

        RecipeModel recipe = recipes.get(posisiResepAwal);
        String sRecipe = new GsonBuilder().create().toJson(recipe);
        Log.d("RecipeWidget", "initViews: sRecipe " + sRecipe);


        intent.putExtra(SELECTED_RECIPE, sRecipe);
        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.ingredient_widget);
        mView.setRemoteAdapter(widgetId, R.id.list, intent);

        return mView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class));
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.layout.ingredient_widget);
//
//            updateBakingWidgets(context, appWidgetManager, appWidgetIds);

        Log.d(TAG, "onReceive: called");
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            int posisiResepPilihan = intent.getExtras().getInt(POSISIRESEP, 0);
//            ingredientforwidget =intent.getExtras().getStringArrayList(SELECTED_RECIPE);
//                Log.d("RecipeWidget", "ingredient4widget: " + ingredientforwidget);
//
//
//            Log.d("RecipeWidget", "posisi widget: " + posisiResepPilihan);
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), RecipeWidget.class.getName());
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(thisAppWidget), R.layout.ingredient_widget);
//
////            onUpdate(context, appWidgetManager, appWidgetIds);
//            updateBakingWidgets(context, appWidgetManager, appWidgetIds, posisiResepPilihan);
//        }
//        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeWidget.class));

        final String action = intent.getAction();

        if (action.equals("android.appwidget.action.APPWIDGET_UPDATE2")) {
            ingredientforwidget = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list);
            //Now update all widgets
            RecipeWidget.updateBakingWidgets(context, appWidgetManager, appWidgetIds, 0);
            super.onReceive(context, intent);
        }

    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, int posisiResep) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, posisiResep);
////            RemoteViews mView = initViews(context, appWidgetManager, appWidgetId);
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list);
//            appWidgetManager.updateAppWidget(appWidgetId, mView);
        }
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, int posisiResep) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);

//            RemoteViews mView = initViews(context, appWidgetManager, appWidgetId);
//            appWidgetManager.updateAppWidget(appWidgetId, mView);

        Intent intent = new Intent(context, RecipeService.class);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//
//        Type type = new TypeToken<List<RecipeModel>>() {
//        }.getType();
//        List<RecipeModel> recipes = new GsonBuilder().create().fromJson(MockData.DATA_1, type);
//        Log.d("RecipeWidget", "updateAppWidget: " + recipes.get(posisiResep).getName());
//
//        RecipeModel recipe = recipes.get(posisiResep);
//        String sRecipe = new GsonBuilder().create().toJson(recipe);
//        Log.d("RecipeWidget", "updateView: sRecipe " + sRecipe);
//
//        intent.putExtra(SELECTED_RECIPE, sRecipe);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        views.setRemoteAdapter(R.id.list, intent);

        //masuk aplikasi
//        Intent pindah = new Intent(context, DetailRecipeListActivity.class);
//        ArrayList<RecipeModel> recipeList = new ArrayList<>();
//        recipeList.addAll(recipes);
//        pindah.putParcelableArrayListExtra(SELECTED_RECIPE, recipeList);
//        pindah.putExtra(POSISIRESEP, posisiResep);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, pindah, 0);
//        views.setOnClickPendingIntent(R.id.list, pendingIntent);

//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.list);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }
}

