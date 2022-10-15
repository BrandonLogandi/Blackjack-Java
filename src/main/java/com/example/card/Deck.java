package com.example.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        reset();
    }

    public Card deal() {
        return cards.remove(0);
    }

    public void reset() {
        System.out.println("Shuffling deck!");
        cards.clear();
        for (Suit suit : Suit.values())
            for (Value value : Value.values())
                cards.add(new Card(suit, value));

        Collections.shuffle(cards);
    }

    public int size() {
        return cards.size();
    }
    
}
