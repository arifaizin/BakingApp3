package id.co.imastudio.bakingapp3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static id.co.imastudio.bakingapp3.MainActivity.POSISIRESEP;
import static id.co.imastudio.bakingapp3.MainActivity.SELECTED_RECIPE;

/**
 * Created by idn on 8/12/2017.
 */

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.ViewHolder> {

    private ArrayList<RecipeModel> recipeList;
    private Context context;

    //Create constructor
    public BakingAdapter(ArrayList<RecipeModel> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bakingName, bakingServing;
        ImageView bakingImage;

        public ViewHolder(View itemView) {
            super(itemView);
            bakingName = (TextView) itemView.findViewById(R.id.tv_item_name);
            bakingServing = (TextView) itemView.findViewById(R.id.tv_iten_serving);
            bakingImage = (ImageView) itemView.findViewById(R.id.iv_item_bake);

        }
    }
//mantab jiwaaaaaa
    @Override
    public BakingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baking_item, parent, false);
        return new ViewHolder(itemView);
        //item layout
    }

    @Override
    public void onBindViewHolder(BakingAdapter.ViewHolder holder, final int position) {
        //proses komponen
        holder.bakingName.setText(recipeList.get(position).getName());
        holder.bakingServing.setText("Servings : "+recipeList.get(position).getServings().toString());

        if (recipeList.get(position).getImage().isEmpty()) {
            Picasso.with(context).load(R.drawable.baking).into(holder.bakingImage);
        } else {
            Picasso.with(context).load(recipeList.get(position).getImage()).into(holder.bakingImage);
        }


        holder.bakingImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(context, DetailRecipeListActivity.class);
                pindah.putParcelableArrayListExtra(SELECTED_RECIPE, recipeList);
                pindah.putExtra(POSISIRESEP, position);
                context.startActivity(pindah);

            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
        //jumlah list
    }
}
