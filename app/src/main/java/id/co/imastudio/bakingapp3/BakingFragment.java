package id.co.imastudio.bakingapp3;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import id.co.imastudio.bakingapp3.Network.Client;
import id.co.imastudio.bakingapp3.Network.NetworkTask;
import id.co.imastudio.bakingapp3.Network.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.imastudio.bakingapp3.MainActivity.ALL_RECIPES;


/**
 * A simple {@link Fragment} subclass.
 */
public class BakingFragment extends Fragment {

//turunkan harga bajuuuuuuu
    private static final String SAVED_LAYOUT_MANAGER = "slm";
    private static final String TAG = "BakingFragment";

    public BakingFragment() {
        // Required empty public constructor
    }


    private ArrayList<RecipeModel> bakingList;
    private RecyclerView recyclerView;
    private BakingAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_baking, container, false);

//        bakingList = new ArrayList<>();

        recyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_baking);

//        for (int i = 0 ; i< 10; i++) {
//            RecipeModel film2 = new RecipeModel();
//            film2.setServings(8);
//            film2.setName("Nutellad");
//            bakingList.add(film2);
//        }

        new NetworkTask().execute();

//        bakingList = new GsonBuilder().create().fromJson(MockData.DATA, new TypeToken<ArrayList<RecipeModel>>() {
//        }.getType());
//        for (int i=0 ; i<bakingList.size() ; i++){
//            Log.d("bakinglist",""+bakingList.get(0).getName());
//        }

        if (isNetworkConnected()) {
            getDataWithRetrofit();
        } else {
            Snackbar.make(fragmentView, "No Internet Connection", Toast.LENGTH_SHORT)
                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                    .setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDataWithRetrofit();
                }
            }).show();

        }



        return fragmentView;
    }

    private void getDataWithRetrofit() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Loading Data", "Mohon Bersabar", true, true);
//        Service service = Client.Retrieve();
//        final Call<ArrayList<RecipeModel>> recipe = service.getRecipe();
        ////////////
        Service api = Client.getInstanceRetrofit();
        Call<ArrayList<RecipeModel>> call = api.getRecipe();

        call.enqueue(new Callback<ArrayList<RecipeModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeModel>> call, Response<ArrayList<RecipeModel>> response) {
                loading.dismiss();
                Integer statusCode = response.code();
                Log.v("status code: ", statusCode.toString());

                ArrayList<RecipeModel> recipes = response.body();
                for (int i=0 ; i<recipes.size() ; i++){
                    Log.d("Networktask Response",""+recipes.get(i).getName());
                }

                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

                adapter = new BakingAdapter(recipes, getActivity());
                recyclerView.setAdapter(adapter);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int widthPixels = displayMetrics.widthPixels;
                float scaleFactor = displayMetrics.density;
                float widthDp = widthPixels / scaleFactor;
                if (widthDp > 600) {
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Math.round(widthDp / 300)));

                } else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                //https://github.com/mathemandy/BakingApp/blob/master/app/src/main/java/com/ng/tselebro/bakingapp/recipe/MainActivity.java

            }

            @Override
            public void onFailure(Call<ArrayList<RecipeModel>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
                Toast.makeText(getActivity(), "http fail: "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LAYOUT_MANAGER, recyclerView.getLayoutManager().onSaveInstanceState());
        Log.d(TAG, "onSaveInstanceState: "+recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: called");
        if(savedInstanceState != null) {
//            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
//            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
//            Log.d(TAG, "onViewStateRestored() returned: " + savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER));

        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


}
