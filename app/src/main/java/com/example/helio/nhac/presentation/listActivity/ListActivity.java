package com.example.helio.nhac.presentation.listActivity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helio.nhac.R;
import com.example.helio.nhac.data.FruitDatabase;
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
//        dumbData();
        runLayoutAnimation(mRecyclerView);

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

    private Boolean initialize(){
        return dao.insert(getString(R.string.coconut), getString(R.string.coconut_details),
                0, R.drawable.coco) &
        dao.insert(getString(R.string.pineapple), getString(R.string.pineapple_details),
                0, R.drawable.abacaxi) &
        dao.insert(getString(R.string.avocado), getString(R.string.avocado_details),
                0, R.drawable.abacate) &
        dao.insert(getString(R.string.banana),getString(R.string.banana_details),
                0, R.drawable.banana) &
        dao.insert(getString(R.string.lemon), getString(R.string.lemon_details),
                0, R.drawable.limao) &
        dao.insert(getString(R.string.tomato), getString(R.string.tomato),
                0, R.drawable.tomate) &
        dao.insert(getString(R.string.strawberry), getString(R.string.strawberry_details),
                0, R.drawable.morango) &
        dao.insert(getString(R.string.watermelon), getString(R.string.watermelon_details),
                0, R.drawable.melancia);
    }

    private void dumbData(){
        fruitArrayList.add(new Fruit("coco", true, R.drawable.coco, getString(R.string.coconut_details)));
        fruitArrayList.add(new Fruit("abacaxi", false, R.drawable.abacaxi));
        fruitArrayList.add(new Fruit("abacate", false, R.drawable.abacate));
        fruitArrayList.add(new Fruit("banana", true, R.drawable.banana, getString(R.string.banana_details)));
        fruitArrayList.add(new Fruit("limâo", false, R.drawable.limao));
        fruitArrayList.add(new Fruit("tomate", true, R.drawable.tomate, getString(R.string.tomato_details)));
        fruitArrayList.add(new Fruit("morango", false, R.drawable.morango));
        fruitArrayList.add(new Fruit("melancia", false, R.drawable.melancia));
    }
}
