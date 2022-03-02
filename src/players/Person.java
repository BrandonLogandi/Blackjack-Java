package players;

import java.util.ArrayList;

import card.*;

public abstract class Person {
    
    private ArrayList<Card> hand = new ArrayList<Card>();

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getSumOfHand() {
        int sum = 0;
        for (Card card : hand) {
            sum += card.getValue().getRawValue();
        }
        return sum;
    }

    public void resetHand(){
        hand = new ArrayList<Card>();
    }
}
