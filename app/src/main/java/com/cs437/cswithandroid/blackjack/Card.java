package com.cs437.cswithandroid.blackjack;

/**
 * Created by arendon on 5/5/16.
 */
public class Card {

    private int value;
    private int drawableId;

    public Card(int value, int drawableId){
        this.value = value;
        this.drawableId = drawableId;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getDrawableId() {

        return drawableId;
    }

    public int getValue() {

        return value;
    }
}
