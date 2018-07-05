package com.sam.android.pokeprices.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sam.android.pokeprices.Card;
import com.sam.android.pokeprices.database.CardDbSchema.CardTable;

import java.util.UUID;

/**
 * Created by Samuel.Fletcher on 5/16/2017.
 */
public class CardCursorWrapper extends CursorWrapper {
    public CardCursorWrapper(Cursor cursor){ super(cursor); }

    public Card getCard(){
        String uuidString = getString(getColumnIndex(CardTable.Cols.UUID));
        String name = getString(getColumnIndex(CardTable.Cols.NAME));
        String setNumber = getString(getColumnIndex(CardTable.Cols.SET_NUMBER));
        String setName = getString(getColumnIndex(CardTable.Cols.SET_NAME));
        String cardType = getString(getColumnIndex(CardTable.Cols.CARD_TYPE));
        String pokemonType = getString(getColumnIndex(CardTable.Cols.POKEMON_TYPE));
        String rarity = getString(getColumnIndex(CardTable.Cols.RARITY));
        String url = getString(getColumnIndex(CardTable.Cols.WEB_VIEW_URL));

        Card card = new Card(UUID.fromString(uuidString));
        card.setmName(name);
        card.setmSetNumber(setNumber);
        card.setmSetName(setName);
        card.setmCardType(cardType);
        card.setmPokemonType(pokemonType);
        card.setmRarity(rarity);
        card.setmURL(url);

        return card;
    }
}

