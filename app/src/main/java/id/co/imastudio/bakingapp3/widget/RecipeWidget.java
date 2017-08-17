package id.co.imastudio.bakingapp3.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import id.co.imastudio.bakingapp3.MockData;
import id.co.imastudio.bakingapp3.R;
import id.co.imastudio.bakingapp3.RecipeModel;

import static id.co.imastudio.bakingapp3.MainActivity.SELECTED_RECIPE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.test_layout);
        views.setTextViewText(R.id.appwidget_text, widgetText);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, mView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.test_layout);

        Intent intent = new Intent(context, RecipeService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        Type type = new TypeToken<List<RecipeModel>>(){}.getType();
        List<RecipeModel> recipes = new GsonBuilder().create().fromJson(MockData.DATA_1, type);
        Log.d("RecipeWidget", "initViews: "+ recipes.get(0).getName());


        RecipeModel recipe = recipes.get(0);
        String sRecipe = new GsonBuilder().create().toJson(recipe);

        intent.putExtra(SELECTED_RECIPE, sRecipe);
        mView.setRemoteAdapter(widgetId, R.id.list, intent);

        return mView;
    }
}

