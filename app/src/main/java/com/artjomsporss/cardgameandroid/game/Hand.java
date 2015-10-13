package com.artjomsporss.cardgameandroid.game;

import java.util.ArrayList;

/**
 * Created by artash on 02/10/15.
 */
public class Hand {
    private ArrayList<Card> cards = new ArrayList<Card>(36);

    public String getName() {
        return name;
    }

    private String name;

    public ArrayList<Card> getCards(){
        return this.cards;
    }

    public Hand(String name){
        this.name = name;
    }

    public void setCards(ArrayList<Card> cards){
        this.cards = cards;
    }

    public boolean hasCard(Card c){
        if(cards.contains(c)){
            return true;
        }else{
            return false;
        }
    }

    //TODO move this method to Model, make classes less coupled
    public void moveCardToTable(Card card, Table table){
        if(cards.remove(card)) {
            table.putCardOnTable(card);
        }
    }

    public void addCard(Card card){
        this.cards.add(card);
    }

    public boolean hasNoCards(){
        if(this.cards.size() == 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean hasCards(){
        if(this.cards.size() > 0){
            return true;
        }else{
            return false;
        }
    }
}
