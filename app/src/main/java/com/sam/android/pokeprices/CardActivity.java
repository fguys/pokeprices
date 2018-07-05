package com.sam.android.pokeprices;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class CardActivity extends SingleFragmentActivity {
    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        CardSet cardSet = new CardSet(this);
        if(cardSet.getCard("Mewtwo")==null){
            Log.i(TAG, "Mewtwo returned null");

            try{
                cardSet.writeCards(this, "xyp.txt");
                cardSet.writeCards(this, "prc.txt");
                cardSet.writeCards(this, "dcr.txt");
                cardSet.writeCards(this, "ros.txt");
                cardSet.writeCards(this, "aor.txt");
                cardSet.writeCards(this, "bkt.txt");
                cardSet.writeCards(this, "bkp.txt");
                cardSet.writeCards(this, "gen.txt");
                cardSet.writeCards(this, "fco.txt");
                cardSet.writeCards(this, "sts.txt");
                cardSet.writeCards(this, "evo.txt");
                cardSet.writeCards(this, "smp.txt");
                cardSet.writeCards(this, "sm.txt");
                cardSet.writeCards(this, "gri.txt");
            }catch (IOException ioe){
                Log.i(TAG, "Didn't work, Sam");
            }
        }
    }

    public static Intent newIntent(Context context){
        return new Intent(context, CardActivity.class);
    }

    @Override
    public Fragment createFragment(){
        return new CardListFragment();
    }
}
