package com.example.helio.nhac.presentation;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.helio.nhac.R;
import com.example.helio.nhac.databinding.ActivityDetailBinding;

import java.util.Locale;

    public class DetailActivity extends AppCompatActivity {
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.light_blue_500));

        binding.detailTitle.setText(getIntent().getStringExtra("item_name"));
        binding.textviewDetails.setText(getIntent().getStringExtra("item_detail"));

        byte[] decodedString = Base64.decode(getIntent().getExtras().getByteArray("item_image"), Base64.DEFAULT);
        binding.detailImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        setTextToSpeech();
        binding.speaker.setOnClickListener(new View.OnClickListener() {
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
