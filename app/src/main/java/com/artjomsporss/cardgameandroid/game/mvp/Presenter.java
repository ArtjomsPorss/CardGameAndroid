package com.artjomsporss.cardgameandroid.game.mvp;

import android.content.Context;
import android.view.View;

import com.artjomsporss.cardgameandroid.GameActivity;
import com.artjomsporss.cardgameandroid.game.Card;

/**
 * Created by artash on 02/10/15.
 */
public class Presenter {
    private GameActivity view;
    private Context context;
    private Model model;
    public static Presenter presenter;

    public Presenter(Context context, GameActivity view){
        this.presenter = this;
        this.view = view;
        this.model = new Model();
        this.context = context;

        model.prepareDeck(this.context);
        model.prepareHands();
        model.setupTable();

        this.view.refreshCardVews(model.getDeckCards(), model.getTopHandCards(), model.getBottomHandCards(), model.getTableCards());

        model.determineAttacker();
    }

    //TODO working on implementing on card click method
    public void onCardClick(View view){
        this.model.cardClicked((Card)view);

        //.. then redraw state in game
        this.view.refreshCardVews(model.getDeckCards(), model.getTopHandCards(), model.getBottomHandCards(), model.getTableCards());
    }

    public void onActionButtonClick(View view){

    }
}
