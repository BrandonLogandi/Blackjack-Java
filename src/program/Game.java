package program;

import java.util.ArrayList;
import java.util.Scanner;

import card.*;
import players.*;

public class Game {
    static Scanner input = new Scanner(System.in); // Scanner that will be used to read input

    static ArrayList<Card> deck;    // The deck that will be used through the round
    static Dealer dealer;           // The dealer
    static Player[] players;        // The list of players

    static int playerAmount = 1;    // The amount of players
    static int dealerStandOn = 17;  // The amount of points the dealer must reach or surpass before they stop hitting

    private static Player createPlayer(){
        Player p = new Player(input.nextLine());
        return p;
    }

    private static ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>();   // Create a new deck for cards to be added into

        // Create cards with all suits and values by using every suit and value possible
        for(Suit suit:Suit.values())
            for(Value value:Value.values())
                deck.add(new Card(suit, value));

        return deck;
    }

    private static void addCardToHand(Person person){
        Card card = dealer.dealCard(deck);  // Gets a new card from the deck
        person.getHand().add(card);         // Adds that card to the person's hand
    }

    private static void showPersonHand(Person person) {
        System.out.print(person.getName() + "'s hand: \n");

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
            System.out.print("(Total: " + person.getSumOfHand() + ") \n\n");
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
                    int parsePlayerAmount = Integer.parseInt(args[i+1]);
                    if (parsePlayerAmount <= 0) 
                        throw new Exception("Invalid player amount");

                    playerAmount = parsePlayerAmount;
                    ignoreNextArg = true;
                    break; 

                case "--dealerStandOn":
                    int parseDealerStandOn = Integer.parseInt(args[i+1]);
                    if (parseDealerStandOn > 21 || parseDealerStandOn <= 0) 
                        throw new Exception("Invalid dealer points stand value");

                    dealerStandOn = parseDealerStandOn;
                    ignoreNextArg = true;
                    break;   
            }
        }

        deck = createDeck();
        dealer = new Dealer("Dealer");
        players = new Player[playerAmount];

        for (int i = 0; i < players.length; i++) {
            System.out.print("Input player " + (i+1) + " name: ");
            players[i] = createPlayer();
        }

        for(int i = 0; i < 2; i++){
            for (Person p : players) { // Give a card to every player in the game
                addCardToHand(p);
                showPersonHand(p);
            }

            addCardToHand(dealer);
            showPersonHand(dealer);

        }

        for (Person p : players) {

            if (p.getSumOfHand() == 21) {
                System.out.println("Blackjack for " + p.getName() + "!");
                continue;
            }

            boolean stop = false;

            do {
                System.out.println(p.getName() + ", what do you want to do?\n1 - Hit, 2 - Stand");

                switch (Integer.parseInt(input.nextLine())) {
                    case 1:
                        addCardToHand(p);
                        showPersonHand(p);

                        if (p.getSumOfHand() == 21) { // If player has 21 points, don't let them hit anymore
                            System.out.println("21! Just what you needed");
                            stop = true;
                        } else if (p.getSumOfHand() > 21) { // If player has gone over 21, they lose
                            System.out.println("Over 21! You bust");
                            p.setBusted(true);
                            stop = true;
                        }

                        break;

                    case 2:
                        System.out.println(p.getName() + " stands");
                        stop = true;
                        break;
                
                    default:
                        break;
                }

            } while (!stop);
        }

        // Dealer plays after everybody else
        System.out.println("Dealer has " + dealer.getSumOfHand());

        if (dealer.getSumOfHand() == 21) {
            System.out.println("Blackjack for the dealer!");
        } else {
            // Dealer will keep hitting until they reach or surpass "dealerStandOn" or until they bust
            while (dealer.getSumOfHand() < dealerStandOn) {
                addCardToHand(dealer);
                showPersonHand(dealer);

                if (dealer.getSumOfHand() > 21) {
                    System.out.println("Dealer busts!");
                    dealer.setBusted(true);
                    break;
                }
            }
        }

        // Results
        for (Player p : players) {
            if (p.getBusted()) {
                System.out.println(p.getName() + " loses!");

            } else if (p.getSumOfHand() > dealer.getSumOfHand() || dealer.getBusted()) {
                System.out.println(p.getName() + " wins against the dealer!");
            } else if (p.getSumOfHand() == dealer.getSumOfHand()) {
                System.out.println(p.getName() + " pushes!");
            } else {
                System.out.println(p.getName() + " loses!");
            }
        }

    }

}
