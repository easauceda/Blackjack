package com.cs437.cswithandroid.blackjack;

import android.content.Context;

import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

/**
 * Created by arendon on 5/5/16.
 */
public class Deck {

    private Stack<Card> deckOfCards = new Stack<>();
    protected Context context;

    public Deck(Context context) {
        this.context = context;

        for(int i = 1, j = 1; i <= 52; i++, j++){
            String cardIdName = "card" + Integer.toString(i);
            int resourceId = context.getResources().getIdentifier(cardIdName, "drawable", context.getPackageName());

            int cardValue = j;
            if(j > 10){
                cardValue = 10;
            }

            Card card = new Card(cardValue, resourceId);
            deckOfCards.add(card);

            if((i % 13) == 0) {
                j = 0;
            }

        }


    }

    protected void shuffle(){
        Collections.shuffle(deckOfCards);
    }

    protected Card draw(){
        return deckOfCards.pop();
    }
}
