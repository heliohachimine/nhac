package com.example.helio.nhac.presentation.listActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helio.nhac.R;
import com.example.helio.nhac.model.Fruit;
import com.example.helio.nhac.presentation.DetailActivity;

import java.util.ArrayList;

public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder> {

    private final ArrayList<Fruit> fruitList;


    public StickerAdapter(ArrayList<Fruit> fruitList) {
        this.fruitList = fruitList;
    }
    public StickerAdapter(){
        this.fruitList = new ArrayList<>();
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_health_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, final int position) {
        holder.imageView.setImageResource(fruitList.get(position).getImage());

        if (!fruitList.get(position).getCollected()) {
            holder.imageView.setColorFilter(R.color.black, PorterDuff.Mode.MULTIPLY);
//            holder.lottieAnimationView.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Figurinha bloqueada", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
//            holder.lottieAnimationView.playAnimation();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("item_name",fruitList.get(position).getName());
                    intent.putExtra("item_detail",fruitList.get(position).getDetails());
                    intent.putExtra("item_image",fruitList.get(position).getImage());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return fruitList != null ? fruitList.size() : 0;
    }

}
