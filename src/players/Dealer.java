package players;

import java.util.ArrayList;

import card.*;

public class Dealer extends Person {

    public Dealer(String name) {
        super(name);
    }

    public Card dealCard(ArrayList<Card> deck){
        int index = (int) (Math.random() * deck.size()); // Get a random index to pick a card from
        Card pickedCard = deck.get(index); // Save the picked card to a variable

        deck.remove(index); // Remove the card from the deck
        return pickedCard; // Return the card
    }

}
