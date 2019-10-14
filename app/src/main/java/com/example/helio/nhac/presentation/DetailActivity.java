package com.example.helio.nhac.presentation;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.helio.nhac.R;

public class DetailActivity extends AppCompatActivity {
    TextView title;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.light_blue_500));
        title = findViewById(R.id.detail_title);
        title.setText(getIntent().getStringExtra("item_name"));
        image = findViewById(R.id.detail_image);
        image.setImageResource(getIntent().getIntExtra("item_image",0));

    }
}
