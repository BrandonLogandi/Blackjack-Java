package players;

import java.util.ArrayList;

import card.*;

public abstract class Person {
    private String name;
    private ArrayList<Card> hand;
    private boolean busted = false;

    public Person(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public boolean getBusted(){
        return busted;
    }
    public void setBusted(boolean busted) {
        this.busted = busted;
    }

    public int getSumOfHand() {
        int sum = 0;
        for (Card card : hand) {
            if (card.getValue().equals(Value.ACE) && (sum + Value.ACE.getNumericValue()) > 21) {
                sum += 1;
            } else {
                sum += card.getNumericValue();
            }
        }
        return sum;
    }

    public void resetHand(){
        hand = new ArrayList<Card>();
    }
}
