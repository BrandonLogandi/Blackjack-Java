package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();
        reset();
    }

    public Card deal() {
        Card c = deck.get(0);
        deck = deck.subList(1, deck.size());

        return c;
    }

    public void reset() {
        System.out.println("Shuffling deck!");
        deck.clear();
        for (Suit suit : Suit.values())
            for (Value value : Value.values())
                deck.add(new Card(suit, value));

        Collections.shuffle(deck);
    }

    public int size() {
        return deck.size();
    }
    
}
