package com.artjomsporss.cardgameandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artjomsporss.cardgameandroid.game.Card;
import com.artjomsporss.cardgameandroid.game.mvp.Presenter;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private Presenter presenter;

    private LinearLayout topHandPane;
    private LinearLayout tablePane;
    private LinearLayout bottomHandPane;
    private RelativeLayout deckPane;

    public static ImageButton turnButton;

    public static GameActivity view;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        TextView chatView = (TextView)findViewById(R.id.chat_view);
        String chatText = "John: Hi!"
                + "\nSarah: What's up?"
                + "\nJohn: just came in for couple games.."
                + "\nSarah: cool";
        chatView.setText(chatText);

        this.context = this.getApplicationContext();

        topHandPane = (LinearLayout)findViewById(R.id.top_pane);
        tablePane = (LinearLayout) findViewById(R.id.middle_pane);
        bottomHandPane = (LinearLayout) findViewById(R.id.bottom_pane);
        deckPane = (RelativeLayout) findViewById(R.id.deck_pane);
        turnButton = (ImageButton) findViewById(R.id.tick_button);

        presenter = new Presenter(this.getApplicationContext(), this);

        view = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showCardsOnTable(ArrayList<Card> table){
        for(Card card : table){
            this.removeOldView(card);
        }

        for(Card card : table) {
            LinearLayout middleLayout = (LinearLayout)findViewById(R.id.middle_pane);
            int childCount = middleLayout.getChildCount();
            middleLayout.addView(card);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)card.getLayoutParams();

            //sets margin size of every even card added
            if (childCount % 2 != 0) {
                layoutParams.setMargins(-30, 55, 0, 0);
            } else {
                layoutParams.setMargins(5, 0, 0, 0);
            }

            card.setLayoutParams(layoutParams);
            card.flipUp();
        }
    }

    public void refreshCardVews(ArrayList<Card> deck, ArrayList<Card> topHand, ArrayList<Card> bottomHand
            , ArrayList<Card> discard, ArrayList<Card> table){
        this.showCardsInBottomPane(bottomHand);
        this.showCardsInTopPane(topHand);
        this.showDeck(deck);
        this.showDiscard(discard);
        this.showCardsOnTable(table);
    }

    public void showDeck(ArrayList<Card> deck){
/*        if(deck.size() == 0){
            //TODO show empty trump card here..
            //get trump
            //show trump image, not assigned to any arraylist
            return;
        }*/
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            removeOldView(card);
            this.deckPane.addView(card);

            if(i == deck.size() - 1) {
                RelativeLayout.LayoutParams reLayoutParams = (RelativeLayout.LayoutParams) card.getLayoutParams();
                reLayoutParams.setMargins(-35, 0, 0, 0);
                card.setLayoutParams(reLayoutParams);
                card.flipUp();
            }else{
                RelativeLayout.LayoutParams reLayoutParams = (RelativeLayout.LayoutParams) card.getLayoutParams();
                reLayoutParams.setMargins(35, 0, 0, 0);
                card.setLayoutParams(reLayoutParams);
                card.flipDown();
            }
        }
        Log.d("TRUMPS-DECK", deck.get(deck.size() - 1).getRankStr() + deck.get(deck.size() - 1).getSuit());
    }

    /**
     * Doesn't really shows discard pile. Removes Table view from cards that are moved
     * from table to discard pile/
     * @param discard ArrayList<Card>
     */
    public void showDiscard(ArrayList<Card> discard){
        for(Card c : discard){
            this.removeOldView(c);
        }
    }


    public void showCardsInTopPane(ArrayList<Card> topHand){
        for(Card c : topHand){

            //remove previous Parent of this cards View
            this.removeOldView(c);

            //flip card down in opponents hand
            // c.flipDown();

            //assign this view to top hand pane
            this.topHandPane.addView(c);

            //set space between cards
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) c.getLayoutParams();
            layoutParams.setMargins(0, 0, 5, 0);
            c.setLayoutParams(layoutParams);
        }
    }


    public void showCardsInBottomPane(ArrayList<Card> bottomHand){
        for(Card c : bottomHand){

            //remove previous Parent of this cards View
            this.removeOldView(c);

            this.bottomHandPane.addView(c);

            //set space between cards
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) c.getLayoutParams();
            layoutParams.setMargins(0, 0, 8, 0);
            c.setLayoutParams(layoutParams);
        }
    }

    public void removeOldView(View v){
        ViewGroup parent = (ViewGroup)v.getParent();
        if(parent != null){
            parent.removeView(v);
        }
    }
}

