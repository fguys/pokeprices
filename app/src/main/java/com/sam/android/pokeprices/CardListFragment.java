package com.sam.android.pokeprices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Samuel.Fletcher on 5/16/2017.
 */
public class CardListFragment extends Fragment {

    private RecyclerView mCardRecyclerView;
    private CardAdapter mAdapter;

    private LinearLayout mLinearLayout;
    private Button mSearchButton;
    private EditText mEditText;

    private String search;

    private List<Card> mCards;

    private static final String TAG = "CardListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //cards = savedInstanceState.getSerializable("cards");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_card_list, container, false);

        mCardRecyclerView = (RecyclerView) view
                .findViewById(R.id.card_recycler_view);
        mCardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLinearLayout = (LinearLayout) view.findViewById(R.id.empty_card_list);
        mSearchButton = (Button) view.findViewById(R.id.search_card_button);
        mEditText = (EditText) view.findViewById(R.id.search_card_edit_text);

        updateUI(mCards);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        //updateUI(mCards);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public void updateUI(List<Card> cards){
        //CardSet cardSet = CardSet.get(getActivity());
        mCards = cards;

        if (mAdapter == null){
            mAdapter = new CardAdapter(mCards);
            mCardRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setCards(mCards);
            mAdapter.notifyDataSetChanged();
        }

        if (mCards != null){
            mLinearLayout.setVisibility(View.GONE);
        }else{
            searchCards("Guardians Rising");
        }
    }

    public void searchCards(String query){
        CardSet cardSet = new CardSet(getActivity());
        mCards = cardSet.getCard(query);

        updateUI(mCards);
        //Intent intent = CardPageActivity.newIntent(getActivity(), mCards);
        //startActivity(intent);
    }

    private class CardHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        private TextView mNameTextView;
        private TextView mSetTextView;
        private ImageView mImageView;

        private Card mCard;

        public CardHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.card_name);
            mSetTextView = (TextView) itemView.findViewById(R.id.card_set_details);
            mImageView = (ImageView) itemView.findViewById(R.id.card_photo);
        }

        public void bindCard(Card card){
            mCard = card;
            mNameTextView.setText(mCard.getmName());
            String placeholder = "#" + mCard.getmSetNumber() + ", " + mCard.getmSetName();
            mSetTextView.setText(placeholder);
            String namePlaceholder = mCard.getmName().replaceAll(" ", "-");
            String mURL = "https://s3.amazonaws.com/pokegoldfish/images/gf/" +
                    namePlaceholder + "-" + getSetAbbreviation(mCard.getmSetName()) +
                    "-" + mCard.getmSetNumber() +".jpg";
            Picasso.with(getContext()).load(mURL).into(mImageView);
        }

        @Override
        public void onClick(View v){
            Log.i(TAG, "Received click for: " + mCard.getmName());
            Uri uri;

            uri = Uri.parse(mCard.getmURL());
            Log.i(TAG, "URL: " + mCard.getmURL());

            Intent i = CardPageActivity
                    .newIntent(getActivity(), uri);
            startActivity(i);
        }
    }

    private class CardAdapter extends RecyclerView.Adapter<CardHolder>{
        private List<Card> mCards;

        public CardAdapter(List<Card> cards){ mCards = cards; }

        @Override
        public  CardHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_card, parent, false);

            return new CardHolder(view);
        }

        @Override
        public void onBindViewHolder(CardHolder holder, int position){
            Card card = mCards.get(position);
            holder.bindCard(card);
        }

        @Override
        public int getItemCount(){
            if(mCards != null){
                return mCards.size();
            }else{
                return 0;
            }
        }

        public void setCards(List<Card> cards){ mCards = cards; }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_card_list, menu);

        final MenuItem searchCard = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchCard.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "QueryTextSubmit: " + s);
                QueryPreferences.setStoredQuery(getActivity(), s);
                hideKeyboardFrom(getContext(), getView());
                searchCard.collapseActionView();
                searchCards(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "QueryTextChange: " + s);
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = QueryPreferences.getStoredQuery(getActivity());
                searchView.setQuery(query, false);
            }
        });

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                QueryPreferences.setStoredQuery(getActivity(), null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getSetAbbreviation(String setName){
        switch (setName){
            case "Guardians Rising":
                setName = "GRI";
                break;
            case "Sun Moon":
                setName = "SUM";
                break;
            case "SM-Promo":
                setName = "PR-SM";
                break;
            case "XY-Evolutions":
                setName = "EVO";
                break;
            case "XY-Steam Siege":
                setName = "STS";
                break;
            case "XY-Fates Collide":
                setName = "FAC";
                break;
            case "Generations":
                setName = "GEN";
                break;
            case "XY-BREAKpoint":
                setName = "BKP";
                break;
            case "XY-BREAKthrough":
                setName = "BKT";
                break;
            case "XY-Ancient Origins":
                setName = "AOR";
                break;
            case "XY-Roaring Skies":
                setName = "ROS";
                break;
            case "Double Crisis":
                setName = "DCR";
                break;
            case "XY-Primal Clash":
                setName = "PRC";
                break;
            case "XY-Promo":
                setName = "PR-XY";
                break;
        }
        return setName;
    }

}
