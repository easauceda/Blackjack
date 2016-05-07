package com.cs437.cswithandroid.blackjack;

/**
 * Created by arendon on 5/5/16.
 */
public class Card {

    private int value;
    private int drawableId;
    private boolean isAce;

    public Card(int value, int drawableId, boolean isAce){
        this.value = value;
        this.drawableId = drawableId;
        this.isAce = isAce;
    }

    public void setIsAce(boolean isAce) { this.isAce = isAce; }

    public void setValue(int value) { this.value = value; }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getDrawableId() {

        return drawableId;
    }

    public int getValue() {

        return value;
    }

    public boolean isAce() {
        return isAce;
    }
}
