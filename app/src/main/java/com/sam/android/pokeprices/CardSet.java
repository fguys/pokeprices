package com.sam.android.pokeprices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sam.android.pokeprices.database.CardBaseHelper;
import com.sam.android.pokeprices.database.CardCursorWrapper;
import com.sam.android.pokeprices.database.CardDbSchema.CardTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Samuel.Fletcher on 5/16/2017.
 */
public class CardSet {
    private static final String TAG = "CardSet";

    private static CardSet sCardSet;

    private Context mContext;
    private SQLiteDatabase mCardDatabase;

    public static CardSet get(Context context){
        if (sCardSet == null){
            sCardSet = new CardSet(context);
        }
        return sCardSet;
    }

    public CardSet(Context context){
        mContext = context.getApplicationContext();
        mCardDatabase = new CardBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addCards(Card c){
        ContentValues values = getContentValues(c);

        mCardDatabase.insert(CardTable.NAME, null, values);
    }

    public void updateCard(Card card){
        String uuidString = card.getmId().toString();
        ContentValues values = getContentValues(card);

        mCardDatabase.update(CardTable.NAME, values,
                CardTable.Cols.UUID + " =?",
                new String[] {uuidString});
    }

    private static ContentValues getContentValues(Card card){
        ContentValues values = new ContentValues();
        values.put(CardTable.Cols.UUID, card.getmId().toString());
        values.put(CardTable.Cols.NAME, card.getmName());
        values.put(CardTable.Cols.SET_NUMBER, card.getmSetNumber());
        values.put(CardTable.Cols.SET_NAME, card.getmSetName());
        values.put(CardTable.Cols.CARD_TYPE, card.getmCardType());
        values.put(CardTable.Cols.POKEMON_TYPE, card.getmPokemonType());
        values.put(CardTable.Cols.RARITY, card.getmRarity());
        values.put(CardTable.Cols.WEB_VIEW_URL, card.getmURL());

        return values;
    }

    public List<Card> getCard(String name){
        List<Card> cards = new ArrayList<>();
        CardCursorWrapper cursor = queryCard(
                "SELECT * FROM " + CardTable.NAME
                        + " WHERE " + CardTable.Cols.NAME + " LIKE \"%" + name +
                        "%\" union " +
                "SELECT * FROM " + CardTable.NAME
                        + " WHERE " + CardTable.Cols.SET_NUMBER + " LIKE \"%" + name +
                        "%\" union " +
                "SELECT * FROM " + CardTable.NAME
                        + " WHERE " + CardTable.Cols.SET_NAME + " LIKE \"%" + name +
                        "%\" union " +
                "SELECT * FROM " + CardTable.NAME
                        + " WHERE " + CardTable.Cols.CARD_TYPE + " LIKE \"%" + name +
                        "%\" union " +
                "SELECT * FROM " + CardTable.NAME
                        + " WHERE " + CardTable.Cols.POKEMON_TYPE + " LIKE \"%" + name +
                        "%\" union " +
                "SELECT * FROM " + CardTable.NAME
                        + " WHERE " + CardTable.Cols.RARITY + " LIKE \"%" + name +
                        "%\" ", new String[] {name}
        );

        try{
            if(cursor.getCount() == 0){
                return null;
            }

            if(!cursor.moveToFirst()){
                return cards;
            }
            do{
                cards.add(cursor.getCard());
            }while(cursor.moveToNext());
        }finally {
            cursor.close();
        }

        return cards;
    }

    private CardCursorWrapper queryCard(String query, String[] args){
        Cursor cursor = mCardDatabase.rawQuery(query, null);

        return new CardCursorWrapper(cursor);
    }

    private CardCursorWrapper queryCards(String whereClause, String[] whereArgs){
        Cursor cursor = mCardDatabase.query(
                CardTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CardCursorWrapper(cursor);
    }

    public void writeCards(Context context, String file) throws IOException{
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(file)));

            String name = "";
            String setNumber = "";
            String setName = "";
            String cardType = "";
            String pokemonType = "";
            String rarity = "";
            String url = "";

            String line = reader.readLine();

            int i = 0;
            while (line != null) {

                if (i == 0) {
                    name = line;
                    i++;
                } else if (i == 1) {
                    setNumber = line;
                    i++;
                } else if (i == 2) {
                    setName = line;
                    i++;
                } else if (i == 3) {
                    cardType = line;
                    i++;
                } else if (i == 4) {
                    pokemonType = line;
                    i++;
                } else {
                    rarity = line;

                    url = "www.pokegoldfish.com/price/" + setName.replaceAll(" ", "+") +
                            "/" + name.replaceAll(" ", "+") + "-" + setNumber + "#paper";

                    Card card = new Card();
                    card.setmName(name);
                    card.setmSetNumber(setNumber);
                    card.setmSetName(setName);
                    card.setmCardType(cardType);
                    card.setmPokemonType(pokemonType);
                    card.setmRarity(rarity);
                    card.setmURL(url);

                    addCards(card);
                    Log.i(TAG, "Added card: " + name + " #" + setNumber);
                    i = 0;
                }
                line = reader.readLine();
            }
            reader.close();
        }catch (IOException ioe){

        }
    }
}
