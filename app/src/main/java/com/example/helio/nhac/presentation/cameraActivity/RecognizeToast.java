package com.example.helio.nhac.presentation.cameraActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.helio.nhac.R;
import com.example.helio.nhac.databinding.ToastRecognizedBinding;

public class RecognizeToast extends Dialog {

    CameraActivity activity;

    public RecognizeToast(CameraActivity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastRecognizedBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.toast_recognized, null, false);
        setContentView(binding.getRoot());
        getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.transparent)));
        binding.textview.setText(activity.textToast);
        binding.imgFruit.setImageResource(activity.imageToast);
        binding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
