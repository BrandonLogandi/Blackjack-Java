package players;

import java.util.ArrayList;

import card.*;

public abstract class Person {
    private String name;
    private ArrayList<Card> hand = new ArrayList<Card>();

    public Person(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getSumOfHand() {
        int sum = 0;
        for (Card card : hand) {
            sum += card.getNumericValue();
        }
        return sum;
    }

    public void resetHand(){
        hand = new ArrayList<Card>();
    }
}
