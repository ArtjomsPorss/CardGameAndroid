package com.artjomsporss.cardgameandroid.game;

import com.artjomsporss.cardgameandroid.game.mvp.Model;

import java.util.ArrayList;

/**
 * Created by artash on 02/10/15.
 */
public class Table {

    private ArrayList<Card> cards = new ArrayList<Card>(36);

    public void putCardOnTable(Card card){
        cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public boolean cardCanBeat(Card cardInHand) {
        Card cardOnTable = cards.get(cards.size()-1);

        if(cardInHand.getSuit() == cardOnTable.getSuit()){
            //same suit
            if(cardInHand.getRankInt() > cardOnTable.getRankInt()){
                return true;
            }else{
                return false;
            }
        }else if(cardInHand.getSuit() == Model.getTrumps()
                //player's is a trump, on table - not a trump
                && cardOnTable.getSuit() != Model.getTrumps()){
            return true;
        }else{
            return false;
        }
        //TODO to put checking logic

    }

}
