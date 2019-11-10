package com.example.helio.nhac.presentation.listActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helio.nhac.R;
import com.example.helio.nhac.core.NhacConstants;
import com.example.helio.nhac.model.Fruit;
import com.example.helio.nhac.presentation.DetailActivity;

import java.util.ArrayList;

public class StickerAdapter extends RecyclerView.Adapter<StickerViewHolder> {

    private final ArrayList<Fruit> fruitList;
    private Activity activity;


    public StickerAdapter(Activity context, ArrayList<Fruit> fruitList) {
        this.activity = context;
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
    public void onBindViewHolder(@NonNull final StickerViewHolder holder, final int position) {
        byte[] decodedString = Base64.decode(fruitList.get(position).getImage(), Base64.DEFAULT);
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        if (!fruitList.get(position).getCollected()) {
            holder.imageView.setColorFilter(R.color.black, PorterDuff.Mode.MULTIPLY);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), activity.getString(R.string.sticker_is_blocked), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.imageView.setColorFilter(0xFFFFFFFF,  PorterDuff.Mode.MULTIPLY);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("item_name",fruitList.get(position).getName());
                    intent.putExtra("item_detail",fruitList.get(position).getDetails());
                    intent.putExtra("item_image",fruitList.get(position).getImage());
                    final View sharedElement = holder.imageView;
                    sharedElement.setTransitionName(NhacConstants.SHARED_ELEMENT_STICKER);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, NhacConstants.SHARED_ELEMENT_STICKER);
                    v.getContext().startActivity(intent, options.toBundle());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return fruitList != null ? fruitList.size() : 0;
    }

}
