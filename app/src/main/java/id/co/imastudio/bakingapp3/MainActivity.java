package id.co.imastudio.bakingapp3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean isTablet = false;
    public static final String ALL_RECIPES = "AllRecipes";
    public static final String SELECTED_RECIPE = "SelectedRecipes";
    public static final String POSISIRESEP = "PosisiResep";
    public static final String POSISISTEP = "PosisiStep";
    public static final String SELECTED_INGREDIENT = "SelectedIngredient";


    // For Unit Testing
    @Nullable
    private SimpleIdlingResource mIdlingResource;
    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if (findViewById(R.id.bakesGrid) != null) {
                isTablet = true;
                FragmentManager fragmentManager = getSupportFragmentManager();
                BakingFragment bakesFragment = new BakingFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.bakesGrid, bakesFragment)
                        .commit();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                BakingFragment bakesFragment = new BakingFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.bakesframe, bakesFragment)
                        .commit();
            }
        }


    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(String data) {
//        /* Do something */
////        startActivity(new Intent(MainActivity.this, RecipeActivity.class));
//        new NetworkTask().execute();
//    }


//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }
}
