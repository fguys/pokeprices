package com.sam.android.pokeprices.database;

/**
 * Created by Samuel.Fletcher on 5/16/2017.
 */
public class CardDbSchema {
    public static final class CardTable{
        public static final String NAME = "cards";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String SET_NUMBER = "set_number";
            public static final String SET_NAME = "set_name";
            public static final String CARD_TYPE = "card_type";
            public static final String POKEMON_TYPE = "pokemon_type";
            public static final String RARITY = "rarity";
            public static final String WEB_VIEW_URL = "url";
        }
    }
}
