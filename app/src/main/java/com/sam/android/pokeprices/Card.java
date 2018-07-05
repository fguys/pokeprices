package com.sam.android.pokeprices;

import java.util.UUID;

/**
 * Created by Samuel.Fletcher on 5/16/2017.
 */
public class Card {

    private UUID mId;
    private String mName;
    private String mSetNumber;
    private String mSetName;
    private String mCardType;
    private String mPokemonType;
    private String mRarity;
    private String mURL;

    public Card(){
        // Generate unique identifier
        mId = UUID.randomUUID();
    }

    public Card(UUID id){
        mId = id;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSetNumber() {
        return mSetNumber;
    }

    public void setmSetNumber(String mSetNumber) {
        this.mSetNumber = mSetNumber;
    }

    public String getmSetName() {
        return mSetName;
    }

    public void setmSetName(String mSetName) {
        this.mSetName = mSetName;
    }

    public String getmCardType() {
        return mCardType;
    }

    public void setmCardType(String mCardType) {
        this.mCardType = mCardType;
    }

    public String getmPokemonType() {
        return mPokemonType;
    }

    public void setmPokemonType(String mPokemonType) {
        this.mPokemonType = mPokemonType;
    }

    public String getmRarity() {
        return mRarity;
    }

    public void setmRarity(String mRarity) {
        this.mRarity = mRarity;
    }

    public String getmURL() {
        if (mURL.contains("http")){
            return mURL;
        }else{
            return "http://" + mURL;
        }
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }
}

