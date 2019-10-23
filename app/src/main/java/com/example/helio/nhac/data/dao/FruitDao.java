package com.example.helio.nhac.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.helio.nhac.data.FruitDatabase;
import com.example.helio.nhac.data.controller.FruitController;
import com.example.helio.nhac.model.Fruit;

import java.util.ArrayList;

public class FruitDao {

    private FruitController controller;

    public FruitDao(Context ctx){
        controller = FruitController.getInstance(ctx);
    }

    public boolean insert(String fruit_name, String details, int is_collected, int image){
        ContentValues cv = new ContentValues();
        cv.put(FruitDatabase.FRUIT_NAME, fruit_name);
        cv.put(FruitDatabase.DETAILS, details);
        cv.put(FruitDatabase.IMAGE, image);
        cv.put(FruitDatabase.IS_COLLECTED, is_collected);
        return controller.getDatabase().insert(FruitDatabase.TABELA, null, cv) > 0;
    }

    public ArrayList<Fruit> getAll(){
        ArrayList<Fruit> fruits = new ArrayList<>();
        Cursor cursor = controller.getDatabase().rawQuery("SELECT * FROM " + FruitDatabase.TABELA, null);
        while(cursor.moveToNext()){
            String fruit_name = cursor.getString(cursor.getColumnIndex(FruitDatabase.FRUIT_NAME));
            String details = cursor.getString(cursor.getColumnIndex(FruitDatabase.DETAILS));
            int image = cursor.getInt(cursor.getColumnIndex(FruitDatabase.IMAGE));
            boolean isCollected = cursor.getInt(cursor.getColumnIndex(FruitDatabase.IS_COLLECTED)) > 0;
            fruits.add(new Fruit(fruit_name, isCollected, image, details));
        }
        cursor.close();
        return fruits;
    }

}
