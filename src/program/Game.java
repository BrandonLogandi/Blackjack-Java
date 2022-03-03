package program;

import java.util.ArrayList;

import card.*;
import players.*;

public class Game {

    static ArrayList<Card> deck;    // The deck that will be used through the round
    static Dealer dealer;           // The dealer
    static Person[] players;        // The list of players
    static int playerAmount = 1;    // The amount of players, not counting with the dealer

    private static ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();   // Create a new deck for cards to be added into

        // Create cards with all suits and values by using every suit and value possible
        for(Suit face:Suit.values())
            for(Value value:Value.values())
                deck.add(new Card(face, value));

        return deck;
    }

    private static void addCardToHand(Person person){
        Card card = dealer.dealCard(deck);  // Gets a new card from the deck
        person.getHand().add(card);         // Adds that card to the person's hand
    }

    private static void showPersonHand(Person person) {
        System.out.print(person.getName() + "'s hand: ");

        // If showing dealer's hand AND he only has two cards
        if(person instanceof Dealer && person.getHand().size() == 2){
            // Get first card and show it, but not the second
            Card firstCard = person.getHand().get(0);
            System.out.print(firstCard.toString() + " ???? ");
            System.out.print("(Total: ???) \n");
        }
        // If not, show all cards in hand
        else{
            for (Card cardInHand : person.getHand()) {      
                System.out.print(cardInHand.toString() + " ");
            }
            System.out.print("(Total: " + person.getSumOfHand() + ") \n");
        }

    }

    public static void main(String[] args) throws Exception {
    
        // Command line arguments
        boolean ignoreNextArg = false;
        for (int i = 0; i < args.length; i++) {

            if(ignoreNextArg){
                ignoreNextArg = false;
                continue;
            }
            
            switch (args[i]) {
                case "--playerAmount":

                    if (args[i+1].equals("0")) 
                        throw new Exception("Player amount cannot be 0");
                    playerAmount = Integer.parseInt(args[i+1]);

                    ignoreNextArg = true;
                    break;     
            }
        }

        deck = createDeck();
        dealer = new Dealer("Dealer");
        players = new Person[playerAmount + 1];

        // TODO Ask for player names
        for (int i = 0; i < players.length - 1; i++) {
            players[i] = new Player("Player " + (i+1));
        }
        players[players.length - 1] = dealer;

        for(int i = 0; i < 2; i++){
            for (Person p : players) { // Give a card to every player in the game
                addCardToHand(p);
                showPersonHand(p);
            }

        }

        // TODO Gameplay logic

    }

}