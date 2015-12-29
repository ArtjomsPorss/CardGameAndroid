package com.artjomsporss.cardgameandroid.game;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.artjomsporss.cardgameandroid.game.mvp.Presenter;

public class Card extends ImageButton{


    private char suit;
    private String rank;
    private String rankDrawStr;

    private int backId;
    private int frontId;
    private int currentId;

    /**
     * Creates a card. Can be used to create dummy trump card to display in place of deck when deck runs out of cards.
     * @param context getApplicationContext()
     * @param rank or "trump" to load
     * @param suit suit of the card
     * @param id ImageButton's id
     */
    public Card(Context context, String rank, char suit, int id) {
        super(context);
        super.setId(id);

        this.rank = rank;
        this.suit = suit;
        this.setRankStr();

        this.loadImages();
        this.setImage();
        this.addListener();

        this.setMinimumWidth(42);
        this.setMinimumHeight(63);

        this.flipDown();
    }


    public char getSuit() {
        return this.suit;
    }

    public String getRankStr() {
        return rank;
    }

    public int getRankInt(){
        switch(this.rank){
            case "a" : return 14;
            case "k" : return 13;
            case "q" : return 12;
            case "j" : return 11;
            default: return Integer.parseInt(this.rank);
        }
    }


    private void loadImages(){
        this.backId = getResources().getIdentifier("back", "drawable", getContext().getPackageName());
        this.frontId = getResources().getIdentifier(this.rankDrawStr + "_" + this.suit, "drawable", getContext().getPackageName());
        //Log.d("CARDTEXT", this.rankDrawStr + "_" + this.suit);
    }

    private void setImage(){
        this.setBackgroundResource(this.backId);
        this.currentId = this.backId;
    }

    private void setImage(int newId){
        this.currentId = newId;
        this.setBackgroundResource(newId);
    }


    private void addListener(){
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.presenter.onCardClick(v);
            }
        });
    }

    public void flipUp(){
        if(currentId == backId){
            setImage(this.frontId);
        }
    }

    public void flipDown(){
        if(currentId == frontId){
            setImage(this.backId);
        }
    }


    private void setRankStr(){
        switch(this.rank){
            case("6"): this.rankDrawStr = "six"; break;
            case("7"): this.rankDrawStr = "seven"; break;
            case("8"): this.rankDrawStr = "eight"; break;
            case("9"): this.rankDrawStr = "nine"; break;
            case("10"): this.rankDrawStr = "ten"; break;
            default: this.rankDrawStr = this.rank; break;
        }
    }

    @Override
    public String toString(){
        return this.getRankStr() + this.getSuit();
    }
}
