package com.example.helio.nhac.presentation;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.helio.nhac.R;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    TextView title;
    ImageView image;
    TextView details;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.light_blue_500));


        title = findViewById(R.id.detail_title);
        title.setText(getIntent().getStringExtra("item_name"));
        details = findViewById(R.id.textview_details);
        details.setText(getIntent().getStringExtra("item_detail"));
        image = findViewById(R.id.detail_image);
        image.setImageResource(getIntent().getIntExtra("item_image",0));
        setTextToSpeech();
        findViewById(R.id.speaker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(getIntent().getStringExtra("item_detail"), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void setTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        convertTextToSpeech(getIntent().getStringExtra("item_detail"));
                    }
                } else {
                    Log.e("error", "Initilization Failed!");
                }
            }
        });
        tts.setSpeechRate((float) 0.95d);
    }

    private void convertTextToSpeech(String text) {
        if (text == null || "".equals(text)) {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


}
