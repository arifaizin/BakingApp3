package id.co.imastudio.bakingapp3;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.co.imastudio.bakingapp3.dummy.DummyContent;
import id.co.imastudio.bakingapp3.widget.RecipeWidget;

import static id.co.imastudio.bakingapp3.MainActivity.POSISIRESEP;
import static id.co.imastudio.bakingapp3.MainActivity.POSISISTEP;
import static id.co.imastudio.bakingapp3.MainActivity.SELECTED_RECIPE;

/**
 * An activity representing a list of DetailRecipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DetailRecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class DetailRecipeListActivity extends AppCompatActivity {

    private static final String SAVED_LAYOUT_MANAGER = "slm";
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static final String TAG = "DetailRecipe";
    private ArrayList<RecipeModel> recipeList;
    private int posisiResep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailrecipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//         Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (getIntent() != null){
            recipeList = getIntent().getParcelableArrayListExtra(SELECTED_RECIPE);
            posisiResep = getIntent().getIntExtra(POSISIRESEP,0);
        } else {
            recipeList = savedInstanceState.getParcelableArrayList(SELECTED_RECIPE);
            posisiResep = savedInstanceState.getInt(POSISIRESEP,0);
        }
        Log.d("Hasil", ""+recipeList+posisiResep);

        ////
        Intent intent = new Intent(this, RecipeWidget.class);
//        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        intent.putExtra(POSISIRESEP,posisiResep);
        sendBroadcast(intent);

        ////
        List<Ingredient> ingredients = recipeList.get(posisiResep).getIngredients();
        RecyclerView rvIngredients = (RecyclerView) findViewById(R.id.detailrecipe_ingredient);
        IngredientAdapter ingredientsAdapterView = new IngredientAdapter(ingredients, DetailRecipeListActivity.this);
        rvIngredients.setAdapter(ingredientsAdapterView);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));

        View recyclerView = findViewById(R.id.detailrecipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.detailrecipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.detailrecipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(recipeList.get(posisiResep).getSteps().get(position).getShortDescription());
            if (recipeList.get(posisiResep).getSteps().get(position).getThumbnailURL().isEmpty()){
                holder.thubmnailView.setVisibility(View.GONE);
            } else {
                holder.thubmnailView.setVisibility(View.VISIBLE);
                Picasso.with(DetailRecipeListActivity.this)
                        .load(recipeList.get(posisiResep).getSteps().get(position).getThumbnailURL())
                        .into(holder.thubmnailView);
            }

            Log.d(TAG, "onBindViewHolder: "+ recipeList.get(posisiResep).getSteps().get(position).getThumbnailURL());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(DetailRecipeDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        arguments.putParcelableArrayList(SELECTED_RECIPE, recipeList);
                        arguments.putInt(POSISIRESEP, posisiResep);
                        arguments.putInt(POSISISTEP, position);
                        DetailRecipeDetailFragment fragment = new DetailRecipeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detailrecipe_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, DetailRecipeDetailActivity.class);
                        intent.putExtra(DetailRecipeDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        intent.putParcelableArrayListExtra(SELECTED_RECIPE, recipeList);
                        intent.putExtra(POSISIRESEP, posisiResep);
                        intent.putExtra(POSISISTEP, position);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return recipeList.get(posisiResep).getSteps().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public final ImageView thubmnailView;
            public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                thubmnailView = (ImageView) view.findViewById(R.id.thumbnail_url);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SELECTED_RECIPE, recipeList);
        outState.putInt(POSISIRESEP, posisiResep);
//        outState.putParcelable(SAVED_LAYOUT_MANAGER, recy.getLayoutManager().onSaveInstanceState());
    }
}
