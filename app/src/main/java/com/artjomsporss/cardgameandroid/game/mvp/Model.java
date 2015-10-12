package com.artjomsporss.cardgameandroid.game.mvp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.artjomsporss.cardgameandroid.GameActivity;
import com.artjomsporss.cardgameandroid.game.Card;
import com.artjomsporss.cardgameandroid.game.Deck;
import com.artjomsporss.cardgameandroid.game.Discard;
import com.artjomsporss.cardgameandroid.game.Hand;
import com.artjomsporss.cardgameandroid.game.StepPhases;
import com.artjomsporss.cardgameandroid.game.Table;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by artash on 01/10/15.
 */
public class Model {
    private Deck deck;
    private Hand topHand;
    private Hand bottomHand;

    private Table table;
    private Discard discard;

    private Hand attacker;
    private Hand defender;

    private StepPhases phase = StepPhases.ATTACK;

    /**
     * creates deck object
     *  Deck constructor loads images and assigns ID's to views
     * shuffles it
     * sets the trump
     * @param context
     */
    public void prepareDeck(Context context){
        this.deck = new Deck(context);
        this.deck.shuffleDeck();
        this.deck.setTrump();
    }

    /**
     * creates top and bottom hand objects
     * draws handfull of cards for both
     */
    public void prepareHands(){
        this.bottomHand = new Hand("Bottom");
        this.topHand = new Hand("Top");

        this.drawTopHandOfCards();
        this.drawBottomHandOfCards();
    }

    public void drawTopHandOfCards(){
        this.deck.drawHand(this.topHand);
    }

    public void drawBottomHandOfCards(){ this.deck.drawHand(this.bottomHand); }

    /**
     * Makes changes in model's state depending on at which
     * phase of turn and which card is it clicked
     * @param view is clicked card's view
     */
    public void cardClicked(View view){
        switch(this.phase){
            case ATTACK: /* do something */ break;
            case DEFEND: /* do something */ break;
            case ADD: /* do something */ break;
        }
    }

    //TODO after onCardClick - to implement turn's phase-dependent actions on button click
    public void buttonClicked(){}

    public void determineAttacker(){
        ArrayList<Card> topHandCards = this.topHand.getCards();
        ArrayList<Card> bottomHandCards = this.bottomHand.getCards();
        int topHandInt = 15;
        int bottomHandInt = 15;

        //TODO iterate through cards, if its trump and handLowestTrump is higher than it's value, set it
        for(Card c : topHandCards){
            if(c.getSuit() == this.deck.getTrumps()){
                if(c.getRankInt() < topHandInt){
                    topHandInt = c.getRankInt();
                }
            }
        }

        for(Card c : bottomHandCards){
            if(c.getSuit() == this.deck.getTrumps()){
                if(c.getRankInt() < bottomHandInt){
                    bottomHandInt = c.getRankInt();
                }
            }
        }

        Log.d("TOP/DOWN", topHandInt + " " + bottomHandInt);

        if(topHandInt < bottomHandInt){
            this.attacker = this.topHand;
            this.defender = this.bottomHand;
        }else if(topHandInt > bottomHandInt){
            this.attacker = this.bottomHand;
            this.defender = this.topHand;
        }else{
            Random r = new Random();
            if(r.nextBoolean()){
                this.attacker = this.topHand;
                this.defender = this.bottomHand;
            }else{
                this.attacker = this.bottomHand;
                this.defender = this.topHand;
            }
        }
        Toast.makeText(GameActivity.context, this.attacker.getName() + " makes first move"
                , Toast.LENGTH_LONG).show();
    }

    public ArrayList<Card> getDeckCards(){
        return new ArrayList<Card>(this.deck.getCards());
    }

    public ArrayList<Card> getTopHandCards() {
        return new ArrayList<Card>(this.topHand.getCards());
    }

    public ArrayList<Card> getBottomHandCards() {
        return new ArrayList<Card>(this.bottomHand.getCards());
    }

}
