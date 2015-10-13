package com.artjomsporss.cardgameandroid.game;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by artash on 02/10/15.
 */
public class Discard {
    private ArrayList<Card> cards = new ArrayList<Card>(36);

    public void moveFromTableToDiscard(Table table){
        ArrayList<Card> tableCards = table.getCards();
        int tableCardsSize = tableCards.size();

        for (int i = 0; i < tableCardsSize; i++) {
            this.cards.add(tableCards.remove(0));
        }

//        for (Card c :this.cards) {
//            Log.d("DISCARD_D", c.toString());
//        }
//
//        for (Card c :table.getCards()) {
//            Log.d("DISCARD_T", c.toString());
//        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
