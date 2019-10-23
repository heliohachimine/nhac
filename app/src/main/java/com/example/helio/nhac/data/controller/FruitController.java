package com.example.helio.nhac.data.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.helio.nhac.data.FruitDatabase;

public class FruitController {

    private SQLiteDatabase db;
    private static FruitController controller;
    private FruitDatabase banco; //dbHelper

    public FruitController(Context context){
        banco = new FruitDatabase(context);
        db = banco.getWritableDatabase();
    }

    public static FruitController getInstance(Context ctx){
        if(controller == null)
            controller = new FruitController(ctx);
        return controller;
    }

    public SQLiteDatabase getDatabase(){
        return this.db;
    }
}
