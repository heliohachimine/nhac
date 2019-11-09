package com.example.helio.nhac.presentation.listActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helio.nhac.R;
import com.example.helio.nhac.data.dao.FruitDao;
import com.example.helio.nhac.model.Fruit;
import com.example.helio.nhac.model.GridRecyclerView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    private StickerAdapter mAdapter;
    private ArrayList<Fruit> fruitArrayList = new ArrayList<>();
    FruitDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.orange_300));
        setupRecycler();
        runLayoutAnimation(mRecyclerView);
        findViewById(R.id.title_list).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dumbData();
                return false;
            }
        });

    }

    @SuppressLint("WrongConstant")
    private void setupRecycler() {
        mRecyclerView = (GridRecyclerView) findViewById(R.id.recycler_view_layout_recycler);
        dao = new FruitDao(this);
        fruitArrayList = dao.getAll();
        if (fruitArrayList.size() == 0) {
            if (initialize()) {
                fruitArrayList = dao.getAll();
            }
        }
        mAdapter = new StickerAdapter(this, fruitArrayList);
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

    private Boolean initialize() {
        return dao.insert("coconut",
                getString(R.string.coconut), getString(R.string.coconut_details),
                0, R.drawable.coco) &
                dao.insert("pineapple",
                        getString(R.string.pineapple), getString(R.string.pineapple_details),
                        0, R.drawable.abacaxi) &
                dao.insert("avocado",
                        getString(R.string.avocado), getString(R.string.avocado_details),
                        0, R.drawable.abacate) &
                dao.insert("banana",
                        getString(R.string.banana), getString(R.string.banana_details),
                        0, R.drawable.banana) &
                dao.insert("lemon",
                        getString(R.string.lemon), getString(R.string.lemon_details),
                        0, R.drawable.limao) &
                dao.insert("tomato",
                        getString(R.string.tomato), getString(R.string.tomato),
                        0, R.drawable.tomate) &
                dao.insert("strawberry",
                        getString(R.string.strawberry), getString(R.string.strawberry_details),
                        0, R.drawable.morango) &
                dao.insert("watermelon",
                        getString(R.string.watermelon), getString(R.string.watermelon_details),
                        0, R.drawable.melancia) &
                dao.insert("pumpkin",
                        getString(R.string.pumpkin), getString(R.string.pumpkin_details),
                        0, R.drawable.abobora) &
                dao.insert("broccoli",
                        getString(R.string.broccoli), getString(R.string.broccoli_details),
                        0, R.drawable.brocolis) &
                dao.insert("carrot",
                        getString(R.string.carrot), getString(R.string.carrot_details),
                        0, R.drawable.cenoura);
    }

    private void dumbData() {
        fruitArrayList.clear();
        fruitArrayList.add(new Fruit("coconut", "coco", true, R.drawable.coco, getString(R.string.coconut_details)));
        fruitArrayList.add(new Fruit("pineapple", "abacaxi", true, R.drawable.abacaxi, getString(R.string.pineapple_details)));
        fruitArrayList.add(new Fruit("avocado", "abacate", true, R.drawable.abacate, getString(R.string.avocado_details)));
        fruitArrayList.add(new Fruit("banana", "banana", true, R.drawable.banana, getString(R.string.banana_details)));
        fruitArrayList.add(new Fruit("lemon", "limâo", true, R.drawable.limao, getString(R.string.lemon_details)));
        fruitArrayList.add(new Fruit("tomato", "tomate", true, R.drawable.tomate, getString(R.string.tomato_details)));
        fruitArrayList.add(new Fruit("strawberry", "morango", true, R.drawable.morango, getString(R.string.strawberry_details)));
        fruitArrayList.add(new Fruit("watermelon", "melancia", true, R.drawable.melancia, getString(R.string.watermelon_details)));
        mAdapter = new StickerAdapter(this, fruitArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
