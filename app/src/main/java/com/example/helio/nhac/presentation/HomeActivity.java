package com.example.helio.nhac.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.helio.nhac.R;
import com.example.helio.nhac.presentation.cameraActivity.CameraActivity;
import com.example.helio.nhac.presentation.listActivity.ListActivity;

import static android.view.View.*;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.purple_300));
        findViewById(R.id.home_camera_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), CameraActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.home_list_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }


}
