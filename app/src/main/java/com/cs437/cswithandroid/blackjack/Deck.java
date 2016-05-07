package com.cs437.cswithandroid.blackjack;

import android.content.Context;
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

            // Makes sure J, Q, and K values are 10
            int cardValue = j;
            if(j > 10){
                cardValue = 10;
            }

            // makes new card, if j = 1 then it is an Ace
            Card card;
            if(j == 1){
                card = new Card(cardValue, resourceId, true);
            }
            else{
                card = new Card(cardValue, resourceId, false);
            }

            deckOfCards.add(card);

            // Reset the value of j for the card object values
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
