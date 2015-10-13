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

        addListenersToGameButtons();

        model.prepareDeck(this.context);
        model.prepareHands();
        model.setupTable();
        model.setupDiscard();

        this.view.refreshCardVews(model.getDeckCards(), model.getTopHandCards(), model.getBottomHandCards(), model.getDiscard(), model.getTableCards());

        model.determineAttacker();
    }

    private void addListenersToGameButtons() {
        this.view.turnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.this.onTurnButtonClick(v);
            }
        });
    }


    public void onCardClick(View view){
        this.model.cardClicked((Card) view);
        this.view.refreshCardVews(model.getDeckCards(), model.getTopHandCards(), model.getBottomHandCards(), model.getDiscard(), model.getTableCards());
    }

    public void onTurnButtonClick(View view){
        this.model.turnButtonClicked();
        this.view.refreshCardVews(model.getDeckCards(), model.getTopHandCards(), model.getBottomHandCards(), model.getDiscard(), model.getTableCards());
    }

}
