package program;

import java.util.ArrayList;

import card.*;
import players.*;

public class Main {

    static ArrayList<Card> deck; // The deck that will be used through the round
    static Player player = new Player();
    static Dealer dealer = new Dealer();

    private static ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>(); // Create a new deck for cards to be added into

        // Create cards with all suits and values by using every suit and value possible
        for(Suit face:Suit.values())
            for(Value value:Value.values())
                deck.add(new Card(face, value));

        return deck;
    }

    private static void addCardToHand(Person person){
        Card card = dealer.dealCard(deck); // Gets a new card from the deck
        person.getHand().add(card); // Adds that card to the person's hand

        if(person instanceof Player) // Display different message depending on whether the person is a player or a dealer
            System.out.println(card.toString() + " given to player");
        else
            System.out.println(card.toString() + " given to dealer");
    }

    public static void main(String[] args) {
        
        deck = createDeck();

        for(int i = 0; i < 2; i++){
            addCardToHand(player);
            System.out.println("Player has " + player.getSumOfHand() + " points total");
            addCardToHand(dealer);
            System.out.println("Dealer has " + dealer.getSumOfHand() + " points total");
        }

    }

}
