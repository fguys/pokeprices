package com.sam.android.pokeprices;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * Created by Samuel.Fletcher on 5/16/2017.
 */
public class CardPageActivity extends SingleFragmentActivity{

    public static Intent newIntent(Context context, Uri cardPageUri){
        Intent i = new Intent(context, CardPageActivity.class);
        i.setData(cardPageUri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return CardFragment.newInstance(getIntent().getData());
    }
}
