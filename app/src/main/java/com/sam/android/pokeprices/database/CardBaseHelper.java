package com.sam.android.pokeprices.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sam.android.pokeprices.database.CardDbSchema.CardTable;

/**
 * Created by Samuel.Fletcher on 5/16/2017.
 */
public class CardBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "cardBase.db";

    public CardBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + CardTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CardTable.Cols.UUID + ", " +
                CardTable.Cols.NAME + ", " +
                CardTable.Cols.SET_NUMBER + ", " +
                CardTable.Cols.SET_NAME + ", " +
                CardTable.Cols.CARD_TYPE + ", " +
                CardTable.Cols.POKEMON_TYPE + ", " +
                CardTable.Cols.RARITY + ", " +
                CardTable.Cols.WEB_VIEW_URL +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
