package id.co.imastudio.bakingapp3.Network;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import id.co.imastudio.bakingapp3.MockData;
import id.co.imastudio.bakingapp3.RecipeModel;
import retrofit2.Call;

/**
 * Created by idn on 8/16/2017.
 */

public class NetworkTask extends AsyncTask<Void, Void, Void>  {
    private AtomicBoolean mIsIdleNow;
    @Nullable
//    private volatile ResourceCallback mCallback;

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Service api = Client.getInstanceRetrofit();
            Call<ArrayList<RecipeModel>> recipeCall = api.getRecipe();
            ArrayList<RecipeModel> recipes = recipeCall.execute().body();
            for (int i=0 ; i<recipes.size() ; i++){
                Log.d("Networktask",""+recipes.get(i).getName());
            }
            Type type = new TypeToken<ArrayList<RecipeModel>>(){}.getType();
            MockData.DATA = new GsonBuilder().create().toJson(recipes, type);
            EventBus.getDefault().post(MockData.DATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public boolean isIdleNow() {
//        return mIsIdleNow.get();
//    }
//
//    @Override
//    public void registerIdleTransitionCallback(ResourceCallback callback) {
//        mCallback = callback;
//    }
//
//    /**
//     * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}.
//     * @param isIdleNow false if there are pending operations, true if idle.
//     */
//    public void setIdleState(boolean isIdleNow) {
//        mIsIdleNow.set(isIdleNow);
//        if (isIdleNow && mCallback != null) {
//            mCallback.onTransitionToIdle();
//        }
//    }
}
