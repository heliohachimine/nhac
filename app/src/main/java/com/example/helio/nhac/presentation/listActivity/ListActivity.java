package com.example.helio.nhac.presentation.listActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helio.nhac.R;
import com.example.helio.nhac.data.dao.FruitDao;
import com.example.helio.nhac.databinding.ActivityListBinding;
import com.example.helio.nhac.model.Fruit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private StickerAdapter mAdapter;
    private ArrayList<Fruit> fruitArrayList = new ArrayList<>();
    private ActivityListBinding binding;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("veggies");
    FruitDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.orange_300));
        binding.loading.setAnimation(R.raw.loading);

        dao = new FruitDao(this);
        fruitArrayList = dao.getAll();
        if (fruitArrayList.size() == 0) {
            getFromFirebase();
            binding.loading.playAnimation();
        } else {
            binding.loading.setVisibility(View.GONE);
            setupRecycler();
        }
    }

    @SuppressLint("WrongConstant")
    private void setupRecycler() {
        mRecyclerView = binding.recyclerViewLayoutRecycler;
        mAdapter = new StickerAdapter(this, fruitArrayList);
        mRecyclerView.setAdapter(mAdapter);
        int resId = R.anim.grid_layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.scheduleLayoutAnimation();

    }

    private void getFromFirebase() {
        database.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fruitArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("id_name").getValue(String.class);
                    String details = snapshot.child("details").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    Fruit fruit = new Fruit(id, name, details, false, image.getBytes());
                    fruitArrayList.add(fruit);
                }
                dao.insertList(fruitArrayList);
                binding.loading.cancelAnimation();
                binding.loading.setVisibility(View.GONE);
                setupRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase", "Erro ao ler dados do Firebse");
            }
        });
    }


}
