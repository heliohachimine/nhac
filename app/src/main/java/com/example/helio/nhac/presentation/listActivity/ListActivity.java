package com.example.helio.nhac.presentation.listActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helio.nhac.R;
import com.example.helio.nhac.presentation.Model.Fruit;
import com.example.helio.nhac.presentation.Model.GridRecyclerView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private StickerAdapter mAdapter;
    private ArrayList<Fruit> fruitArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.orange_300));
        setupRecycler();
        dumbData();
        runLayoutAnimation(mRecyclerView);

    }

    @SuppressLint("WrongConstant")
    private void setupRecycler() {
        mRecyclerView = (GridRecyclerView) findViewById(R.id.recycler_view_layout_recycler);
        mAdapter = new StickerAdapter(fruitArrayList);
        mRecyclerView.setAdapter(mAdapter);
        int resId = R.anim.grid_layout_animation;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.scheduleLayoutAnimation();

    }


    public void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private void dumbData(){
        fruitArrayList.add(new Fruit("maçã", true, R.drawable.melancialista));
        fruitArrayList.add(new Fruit("pera", false, R.drawable.morangocamera));
        fruitArrayList.add(new Fruit("goiaba", false, R.drawable.coco));
        fruitArrayList.add(new Fruit("melão", true, R.drawable.limao));
        fruitArrayList.add(new Fruit("cenoura", false, R.drawable.morangocamera));
        fruitArrayList.add(new Fruit("tomate", true, R.drawable.coco));
        fruitArrayList.add(new Fruit("morango", false, R.drawable.melancialista));
        fruitArrayList.add(new Fruit("abobora", false, R.drawable.limao));
        fruitArrayList.add(new Fruit("manga", false, R.drawable.morangocamera));
        fruitArrayList.add(new Fruit("cebola", false, R.drawable.limao));
        fruitArrayList.add(new Fruit("alface", true, R.drawable.melancialista));
        fruitArrayList.add(new Fruit("brocolis", false, R.drawable.coco));
    }
}
