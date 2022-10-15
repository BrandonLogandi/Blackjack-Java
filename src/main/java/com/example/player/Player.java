package com.example.player;

import java.util.ArrayList;
import java.util.List;

import com.example.card.*;

public class Player {

    private String name;
    private List<Card> hand = new ArrayList<>();

    // Game states
    private boolean busted = false;
    private int wins;
    private int losses;
    private int pushes;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public boolean isBusted() {
        return busted;
    }

    public void setBusted(boolean busted) {
        this.busted = busted;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return losses;
    }

    public int getPushes() {
        return pushes;
    }

    public void incrementWins() {
        wins++;
    }

    public void incrementLosses() {
        losses++;
    }

    public void incrementPushes() {
        pushes++;
    }

    public void addCardToHand(Card c) {
        hand.add(c);
    }

    public void showHand() {
        System.out.print(name + "'s hand: \n");

        for (Card cardInHand : hand) 
            System.out.print(cardInHand.toString() + " // ");
        
        System.out.print("(Total: " + getSumOfHand() + ") \n\n");
    }

    public int getSumOfHand() {
        int sum = 0;
        for (Card card : hand) {
            if (card.getValue().equals(Value.ACE) && (sum + 11) > 21) 
                sum += 1;
            else 
                sum += card.getNumericValue();
        }
        return sum;
    }

    public void reset() {
        hand.clear();
        setBusted(false);
    }
}
