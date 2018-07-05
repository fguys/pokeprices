package com.sam.android.pokeprices.database;

/**
 * Created by Samuel.Fletcher on 5/19/2017.
 */
public class DeckDbSchema {
    public static final class DeckTable{
        public static final String NAME = "decks";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String QUANTITY = "quantity";
        }
    }
}
