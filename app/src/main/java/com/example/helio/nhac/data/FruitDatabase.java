package com.example.helio.nhac.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FruitDatabase extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "data.db";
    public static final String ID_NAME = "id_name";
    public static final String TABELA = "fruits";
    public static final String ID = "_id";
    public static final String FRUIT_NAME = "fruit";
    public static final String IS_COLLECTED = "is_collected";
    public static final String IMAGE = "image";
    public static final String DETAILS = "details";
    public static final int VERSAO = 1;

    public FruitDatabase(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+ TABELA +"("
                + ID + " INTEGER primary key autoincrement,"
                + IMAGE + " INTEGER,"
                + IS_COLLECTED + " INTEGER,"
                + DETAILS + " TEXT,"
                + FRUIT_NAME + " TEXT,"
                + ID_NAME + " TEXT"
                +");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
