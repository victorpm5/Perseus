package com.victor.perseus.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.victor.perseus.R;

/**
 * Created by victor on 26/12/2015.
 */
public class DB extends SQLiteOpenHelper {
    //DB NAME
    final static String DB_NAME = "db_recepta";
    //TABLE NAMES
    private static final String TABLE_RECEPTA = "recepta";
    private static final String TABLE_TIPUS_RECEPTA = "tipus_recepta";
    private static final String TABLE_INGREDIENT_RECEPTA = "ingredient_recepta";
    private static final String TABLE_INGREDIENT = "ingredient";
    private static final String TABLE_INGREDIENT_SUBSTITUT = "ingredient_substitut";
    //COMMON COLUMN NAMES
    private static final String KEY_ID = "id";
    //RECEPTA COLUMN NAMES
    private static final String NOM_RECEPTA = "nom_recepta";
    private static final String RECEPTA = "recepta";
    private static final String IMATGE = "imatge";
    private static final String ID_TIPUS = "id_tipus";
    //TIPUS RECEPTA COLUMN NAMES
    private static final String NOM_TIPUS = "nom_tipus";
    //INGREDIENT DE RECEPTA COLUMN NAMES
    private static final String ID_INGREDIENT = "id_ingredient";
    private static final String ID_RECEPTA = "id_recepta";
    //INGREDIENT
    private static final String NOM_INGREDIENT = "nom_ingredient";
    //INGREDIENT SUBSTITUT
    private static final String ID_INGREDIENT_RECEPTA = "id_ingredient_recepta";
    //CREATE TABLE RECEPTA
    private static final String CREATE_TABLE_RECEPTA = "CREATE TABLE "
            + TABLE_RECEPTA + "(" + KEY_ID + " INTEGER PRIMARY KEY," + NOM_RECEPTA
            + " TEXT," + RECEPTA + " TEXT," + IMATGE + " BLOB," + ID_TIPUS
            + " INTEGER" + ")";
    //CREATE TABLE TIPUS RECEPTA
    private static final String CREATE_TABLE_TIPUS_RECEPTA = "CREATE TABLE "
            + TABLE_TIPUS_RECEPTA + "(" + KEY_ID + " INTEGER PRIMARY KEY," + NOM_TIPUS
            + " TEXT" +  ")";
    //CREATE TABLE INGREDIENT DE RECEPTA
    private static final String CREATE_TABLE_INGREDIENT_RECEPTA = "CREATE TABLE "
            + TABLE_INGREDIENT_RECEPTA + "(" + KEY_ID + " INTEGER PRIMARY KEY," + ID_INGREDIENT
            + " INTEGER," + ID_RECEPTA + " INTEGER" + ")";
    //CREATE TABLE TIPUS INGREDIENT
    private static final String CREATE_TABLE_INGREDIENT = "CREATE TABLE "
            + TABLE_INGREDIENT + "(" + KEY_ID + " INTEGER PRIMARY KEY," + NOM_INGREDIENT
            + " TEXT" +  ")";
    //CREATE TABLE INGREDIENT SUBSTITUT
    private static final String CREATE_TABLE_INGREDIENT_SUBSTITUT = "CREATE TABLE "
            + TABLE_INGREDIENT_SUBSTITUT + "(" + KEY_ID + " INTEGER PRIMARY KEY," + ID_INGREDIENT_RECEPTA
            + " INTEGER," + ID_INGREDIENT + " INTEGER" + ")";


    public DB (Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TIPUS_RECEPTA);
        db.execSQL(CREATE_TABLE_RECEPTA);
        db.execSQL(CREATE_TABLE_INGREDIENT);
        db.execSQL(CREATE_TABLE_INGREDIENT_RECEPTA);
        db.execSQL(CREATE_TABLE_INGREDIENT_SUBSTITUT);

        inicialitzacio(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEPTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPUS_RECEPTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT_RECEPTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT_SUBSTITUT);

        onCreate(db);

    }

    public void inicialitzacio(SQLiteDatabase db){
        ContentValues values = new ContentValues();

        //INICIALITXEM ALGUNS TIPUS DE RECEPTA
        values.put(KEY_ID, "1");
        values.put(NOM_TIPUS, "MEDITERRÀNIA");
        db.insert(TABLE_TIPUS_RECEPTA, KEY_ID, values);

        values.put(KEY_ID, "2");
        values.put(NOM_TIPUS, "ASIÀTICA");
        db.insert(TABLE_TIPUS_RECEPTA, KEY_ID, values);

        //INICIALITZEM LA BD AMB ALGUNA RECEPTA
        values.put(KEY_ID, "1");
        values.put(NOM_RECEPTA, "Arroz");
        values.put(RECEPTA, "aquí va la recepta");
        values.put(IMATGE, R.drawable.arroz);
        values.put(ID_TIPUS,"1");
        db.insert(TABLE_RECEPTA, KEY_ID, values);

        //INICIALITZEM LA BD AMB ALGUN INGREDIENT
        values.put(KEY_ID, "1");
        values.put(NOM_INGREDIENT, "Arros");
        db.insert(TABLE_INGREDIENT, KEY_ID, values);

        //INICIALITZEM LA BD AMB INGREDIENT DE RECEPTA
        values.put(KEY_ID, "1");
        values.put(ID_INGREDIENT, "1");
        values.put(ID_RECEPTA, "1");
        db.insert(TABLE_INGREDIENT_RECEPTA, KEY_ID, values);
    }

}


