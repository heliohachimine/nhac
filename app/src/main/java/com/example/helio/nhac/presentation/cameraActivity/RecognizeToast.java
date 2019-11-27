package com.example.helio.nhac.presentation.cameraActivity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.helio.nhac.R;
import com.example.helio.nhac.databinding.ToastRecognizedBinding;

public class RecognizeToast extends Dialog {

    private Activity context;
    private String text;
    private byte[] image;

    public RecognizeToast(Activity context, String text, byte[] image) {
        super(context);
        this.context = context;
        this.text = text;
        this.image = image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastRecognizedBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.toast_recognized, null, false);
        setContentView(binding.getRoot());
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
        binding.textview.setText(text);
        if (image != null) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            binding.imgFruit.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
        } else {
            binding.imgFruit.setImageResource(R.drawable.morangocamera);
        }

        binding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
