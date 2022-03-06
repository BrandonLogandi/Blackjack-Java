package program;

import java.util.ArrayList;
import java.util.Scanner;

import card.*;
import players.*;

public class Game {
    static Scanner input = new Scanner(System.in); // Scanner that will be used to read input

    static ArrayList<Card> deck; // The deck that will be used through the round
    static Dealer dealer; // The dealer
    static Player[] players; // The list of players

    static int playerAmount = 1; // The amount of players
    static int dealerStandOn = 17; // The amount of points the dealer must reach or surpass before they stop hitting
    static boolean forceShowDealerHand = false; // Whether to always show dealer's hand without hiding cards

    public static void main(String[] args) throws Exception {
        commandLineArguments(args);

        deck = createDeck();
        dealer = new Dealer("Dealer");
        players = new Player[playerAmount];
        createPlayer();

        newGame();
    }

    private static void commandLineArguments(String[] args) throws Exception {
        boolean ignoreNextArg = false; // Next argument is the value of previous argument, so ignore it

        for (int i = 0; i < args.length; i++) {

            if (ignoreNextArg) {
                ignoreNextArg = false;
                continue;
            }

            switch (args[i]) {
                case "--playerAmount": // Change the amount of players
                    int parsePlayerAmount = Integer.parseInt(args[i + 1]);
                    if (parsePlayerAmount <= 0)
                        throw new Exception("Invalid player amount");

                    playerAmount = parsePlayerAmount;
                    ignoreNextArg = true;
                    break;

                case "--dealerStandOn": // Change the amount of points the dealer must stand on
                    int parseDealerStandOn = Integer.parseInt(args[i + 1]);
                    if (parseDealerStandOn > 21 || parseDealerStandOn <= 0)
                        throw new Exception("Invalid dealer points stand value");

                    dealerStandOn = parseDealerStandOn;
                    ignoreNextArg = true;
                    break;

                case "--forceShowDealerHand": // Always show dealer's hand, without hiding his second card
                    forceShowDealerHand = true;
                    break;

                default:
                    throw new Exception("Invalid argument " + args[i]);
            }
        }
    }

    private static void newGame() {
        // Reset all player's hands
        for (Person p : players) {
            p.resetHand();
            p.setBusted(false);
        }

        dealer.resetHand();
        dealer.setBusted(false);

        // If current deck is halfway depleted, make a new one
        if (deck.size() < 26) {
            System.out.println("Shuffling deck!");
            deck = createDeck();
        }

        // Run each game phase
        dealStartingHand();
        playersPlay();
        dealerPlay();
        showResults();
    }

    private static ArrayList<Card> createDeck() {
        ArrayList<Card> deck = new ArrayList<Card>(); // Create a new deck for cards to be added into

        // Create cards with all suits and values by using every suit and value possible
        for (Suit suit : Suit.values())
            for (Value value : Value.values())
                deck.add(new Card(suit, value));

        return deck;
    }

    private static void createPlayer() {
        for (int i = 0; i < playerAmount; i++) {
            System.out.print("Input player " + (i + 1) + " name: ");
            players[i] = new Player(input.nextLine());
        }
    }

    private static void dealStartingHand() {
        for (int turn = 1; turn < 3; turn++) {
            for (Person p : players) { // Give a card to every player in the game
                addCardToHand(p);
                showPersonHand(p);
            }

            // Give a card to the dealer and show his hand
            addCardToHand(dealer);
            if (turn == 2) {
                if (forceShowDealerHand) {
                    showPersonHand(dealer);
                } else {
                    System.out.println("Dealer's hand \n" + dealer.getHand().get(0).toString() + " ????");
                }
            } else {
                showPersonHand(dealer);
            }

        }
    }

    private static void addCardToHand(Person person) {
        Card card = dealer.dealCard(deck); // Gets a new card from the deck
        person.getHand().add(card); // Adds that card to the person's hand
    }

    private static void showPersonHand(Person person) {
        System.out.print(person.getName() + "'s hand: \n");

        for (Card cardInHand : person.getHand()) {
            System.out.print(cardInHand.toString() + " // ");
        }
        System.out.print("(Total: " + person.getSumOfHand() + ") \n\n");

    }

    private static void playersPlay() {
        for (Person p : players) {

            if (p.getSumOfHand() == 21) {
                System.out.println("Blackjack for " + p.getName() + "!");
                continue;
            }

            boolean stop = false;
            do {
                System.out.print(p.getName() + ", what do you want to do?\n[1 - Hit, 2 - Stand]:");

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
                }

            } while (!stop);
        }
    }

    private static void dealerPlay() {
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
    }

    private static void showResults() {
        for (Player p : players) {
            if (p.getBusted()) {
                System.out.println(p.getName() + " loses!");
                p.incrementLoses();

            } else if (p.getSumOfHand() > dealer.getSumOfHand() || dealer.getBusted()) {
                System.out.println(p.getName() + " wins against the dealer!");
                p.incrementWins();
            } else if (p.getSumOfHand() == dealer.getSumOfHand()) {
                System.out.println(p.getName() + " pushes!");
                p.incrementPushes();
            } else {
                System.out.println(p.getName() + " loses!");
                p.incrementLoses();
            }
        }

        System.out.print("Play again? [y/n] ");
        switch (input.nextLine()) {
            case "y":
                newGame();
                break;
            case "n":
                // Show wins, loses and pushes for every player, then quit
                System.out.println("Final results:");
                for (Person p : players) 
                    System.out.println(p.getName() + ": " + p.getWins() + "W " + p.getLoses() + "L " + p.getPushes() + "P");

                System.out.println("Thanks for playing!");
                System.exit(0);
        }
    }

}
