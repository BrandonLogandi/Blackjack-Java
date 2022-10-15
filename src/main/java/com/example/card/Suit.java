package com.example.card;

public enum Suit {
    DIAMONDS ("Diamonds"), 
    HEARTS ("Hearts"), 
    SPADES ("Spades"), 
    CLUBS ("Clubs");

    private String suitValue;

    Suit (String suitValue) {
        this.suitValue = suitValue;
    }

    public String getSuitValue() {
        return suitValue;
    }
}
