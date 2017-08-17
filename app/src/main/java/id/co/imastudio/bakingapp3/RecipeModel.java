package id.co.imastudio.bakingapp3;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idn on 8/12/2017.
 */

public class RecipeModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;


//    ArrayList<RecipeModel> array;
//
//    public ArrayList<RecipeModel> getArray() {
//        return array;
//    }
//
//    public void setArray(ArrayList<RecipeModel> array) {
//        this.array = array;
//    }

    RecipeModel(){

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeValue(this.servings);
        dest.writeString(this.image);
    }

    protected RecipeModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.ingredients = new ArrayList<Ingredient>();
        in.readList(this.ingredients, Ingredient.class.getClassLoader());
        this.steps = new ArrayList<Step>();
        in.readList(this.steps, Step.class.getClassLoader());
        this.servings = (Integer) in.readValue(Integer.class.getClassLoader());
        this.image = in.readString();
    }

    public static final Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {
        @Override
        public RecipeModel createFromParcel(Parcel source) {
            return new RecipeModel(source);
        }

        @Override
        public RecipeModel[] newArray(int size) {
            return new RecipeModel[size];
        }
    };
}
