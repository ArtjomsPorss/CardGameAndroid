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

    private static char trumps;

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
        this.trumps = this.deck.setTrump();
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
     * Calls methods that related to current turn phase to handle card onclick event
     * @param card is clicked card's view
     */
    public void cardClicked(Card card){
        switch(this.phase){
            case ATTACK: attackPhase(card); break;
            case DEFEND: defendPhase(card); break;
            case ADD: addPhase(card); break;
        }
    }


    //If card clicked belongs to attacker, move it to the table
    public void attackPhase(Card card){
        if(defender.hasCard(card)){return;} //if it's defender's card, leave it where it is
        attacker.moveCardToTable(card, this.table);

        this.phase = StepPhases.DEFEND;
        Toast.makeText(GameActivity.context, this.defender.getName() + " defends"
                , Toast.LENGTH_SHORT).show();
    }

    public void defendPhase(Card card){
        if(attacker.hasCard(card)){return;} // if it's attacker's card, do nothing
        if(this.table.cardCanBeat(card)) {  //if card
            defender.moveCardToTable(card, this.table);
        }else{return; }

        this.phase = StepPhases.ADD;
        Toast.makeText(GameActivity.context, this.attacker.getName() + " can add cards to table"
                , Toast.LENGTH_SHORT).show();
    }

    //TODO after defend phase attacker can add cards (if have valid ones)
    private void addPhase(Card card) {
    }

    //TODO after onCardClick - to implement turn's phase-dependent actions on button click
    public void buttonClicked(){}

    public void determineAttacker(){
        ArrayList<Card> topHandCards = this.topHand.getCards();
        ArrayList<Card> bottomHandCards = this.bottomHand.getCards();
        int topHandInt = 15;
        int bottomHandInt = 15;

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

        //Log.d("TOP/DOWN", topHandInt + " " + bottomHandInt);

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
                , Toast.LENGTH_SHORT).show();
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

    public ArrayList<Card> getTableCards() {
        return new ArrayList<Card>(this.table.getCards());
    }

    public void setupTable() {
        this.table = new Table();
    }

    public static char getTrumps() {
        return trumps;
    }
}
