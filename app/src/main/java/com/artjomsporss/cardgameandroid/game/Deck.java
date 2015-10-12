package com.artjomsporss.cardgameandroid.game;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by artash on 01/10/15.
 */
public class Deck {

    private ArrayList<Card> deck = new ArrayList<Card>(36);
    private char trumps;

    /**
     * created deck of cards, assigns ID's to all card views
     * @param context
     */
    public Deck(Context context){
        this.createDeck(context);
        this.assignIDsToCards();
    }

    public boolean hasCards(){
        if(deck.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * If deck has cards, removes card at index 0 and returns it
     * if no cards, returns null
     * @return card
     */
    public Card drawCard(){
        Card card = null;
        if(this.hasCards()){
            card = this.deck.remove(0);
        }
        return card;
    }

    /**
     * Draws cards until there are 6 cards in hand
     * if there are no more cards in hand, returns
     * @param hand which will draw cards
     */
    public void drawHand(Hand hand){
        ArrayList<Card> cards = hand.getCards();
        int numCards = hand.getCards().size();

        Card tempCard = null;

        for (int i = numCards; i < 6; i++) {
            tempCard = this.drawCard();
            if(tempCard == null){
                return;
            }else{
                cards.add(tempCard);
                tempCard.flipUp();
            }
        }

        hand.setCards(cards);
    }

    /**
     * Created a deck of Card objects
     * @param context
     */
    public void createDeck(Context context){
        char suit = ' ';
        String rank = " ";
        int id = 0;
        for(int s = 0; s < 4; ++s){			//sets card suits
            switch(s){
                case 0 : suit = 'h'; break;
                case 1 : suit = 'd'; break;
                case 2 : suit = 'c'; break;
                case 3 : suit = 's'; break;
            }

            for(int r = 6; r < 15; ++r){	//sets card ranks
                if(r > 10) {
                    switch(r){
                        // if rank is greater than 10, change it to J, Q, K or A respectively
                        case 11 : rank = "j"; break;
                        case 12 : rank = "q"; break;
                        case 13 : rank = "k"; break;
                        case 14 : rank = "a"; break;
                    }
                } else {
                    rank = Integer.toString(r);		// if rank is below 11, set it
                }
                id = Integer.parseInt(s + "" + r);

                deck.add(new Card(context, rank, suit, id));
            }
        }
    }

    /**
     * assigns ID to every view of card
     */
    private void assignIDsToCards(){
        for (int i = 0; i < this.deck.size(); i++) {
            deck.get(i).setId(i);
        }
    }

    /**
     * shuffles cards in ArrayList<Deck> using Collections.shuffle()
     */
    public void shuffleDeck(){
        int last = deck.size() - 1;
        do{
            Collections.shuffle(deck);
        }while("a".equals(deck.get(last).getRankStr()));
    }

    /**
     * sets the trump card for this game
     */
    public void setTrump(){
        int last = deck.size() - 1;
        this.trumps = this.deck.get(last).getSuit();
        //Log.d("TRUMPS-SETTRUMPS", deck.get(deck.size() - 1).getRankStr() + deck.get(deck.size() - 1).getSuit());
    }

    public char getTrumps(){
        return this.trumps;
    }

    /**
     * @return copy of deck ArrayList<Card>
     */
    public ArrayList<Card> getCards(){
        return this.deck;
    }
}
