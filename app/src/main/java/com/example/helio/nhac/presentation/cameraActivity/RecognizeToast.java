package com.example.helio.nhac.presentation.cameraActivity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.helio.nhac.R;

public class RecognizeToast extends Dialog {

    CameraActivity activity;
    ImageView imageView;
    TextView textView;
    RelativeLayout layout;

    public RecognizeToast(CameraActivity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.toast_recognized);
        getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.transparent)));
        textView = (TextView) findViewById(R.id.textview);
        textView.setText(activity.textToast);
        imageView = (ImageView) findViewById(R.id.img_fruit);
        imageView.setImageResource(activity.imageToast);
        layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
