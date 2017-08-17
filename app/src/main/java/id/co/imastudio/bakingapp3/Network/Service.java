package id.co.imastudio.bakingapp3.Network;

import java.util.ArrayList;

import id.co.imastudio.bakingapp3.RecipeModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by idn on 8/12/2017.
 */

public interface Service {
    @GET("baking.json")
    Call<ArrayList<RecipeModel>> getRecipe();
}
