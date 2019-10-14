package com.example.helio.nhac.presentation.listActivity;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.helio.nhac.R;

public class StickerViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public LottieAnimationView lottieAnimationView;

    public StickerViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image);
        lottieAnimationView = (LottieAnimationView) itemView.findViewById(R.id.animation);
    }


}
