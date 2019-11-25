package com.example.helio.nhac.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.helio.nhac.R;
import com.example.helio.nhac.core.NhacConstants;
import com.example.helio.nhac.data.dao.FruitDao;
import com.example.helio.nhac.model.Fruit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child(NhacConstants.FIREBASE_TABLE_VEGGIES);
    FruitDao dao;

    private ArrayList<Fruit> fruitArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green_300));
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.item_animation_from_bottom);
        findViewById(R.id.logo).startAnimation(animation);

        dao = new FruitDao(this);
        fruitArrayList = dao.getAll();
        if (fruitArrayList.size() == 0) {
            getFromFirebase();
        } else {
            new CountDownTimer(3000, 1000) {
                public void onFinish() {
                    goToHome();
                }

                public void onTick(long millisUntilFinished) {
                }
            }.start();

        }
    }

    private void getFromFirebase() {
        database.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fruitArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.child(NhacConstants.FIREBASE_ENABLE).getValue(Integer.class) == 1){
                        String id = snapshot.child(NhacConstants.FIREBASE_ID_NAME).getValue(String.class);
                        String details = snapshot.child(NhacConstants.FIREBASE_DETAILS).getValue(String.class);
                        String name = snapshot.child(NhacConstants.FIREBASE_NAME).getValue(String.class);
                        String image = snapshot.child(NhacConstants.FIREBASE_IMAGE).getValue(String.class);
                        Fruit fruit = new Fruit(id, name, details, false, image.getBytes());
                        fruitArrayList.add(fruit);
                    }
                }
                dao.insertList(fruitArrayList);
                goToHome();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase", "Erro ao ler dados do Firebse");
            }
        });
    }

    private void goToHome(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
