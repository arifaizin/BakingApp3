package id.co.imastudio.bakingapp3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idn on 8/17/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{

private ArrayList<Ingredient> ingredientList;
private Context context;

//Create constructor
public IngredientAdapter(List<Ingredient> ingredientList, Context context){
        this.ingredientList= new ArrayList<>();
        this.ingredientList.addAll(ingredientList);
        this.context=context;
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView ingredientName;

    public ViewHolder(View itemView) {
        super(itemView);
        ingredientName = (TextView) itemView.findViewById(R.id.tv_ingredient_list);

    }

}

    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(itemView);
        //item layout
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.ViewHolder holder, final int position) {
        //proses komponen
        holder.ingredientName.setText(String.format(
                context.getString(R.string.ingredients_detail),
                ingredientList.get(position).getQuantity(),
                ingredientList.get(position).getMeasure().toString(),
                ingredientList.get(position).getIngredient().toString()
                ));


    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
        //jumlah list
    }
}
