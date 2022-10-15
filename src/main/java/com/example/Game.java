package com.example;

import java.util.Scanner;

import com.example.card.*;
import com.example.player.Player;

public class Game {

    static Scanner input = new Scanner(System.in); // Scanner that will be used to read input

    static Deck deck; // The deck that will be used through the round
    static Player dealer; // The dealer
    static Player[] players; // The list of players

    static int playerAmount = 1; // The amount of players
    static int dealerStandOn = 17; // The amount of points the dealer must reach or surpass before they stop hitting
    static boolean forceShowDealerHand = false; // Whether to always show dealer's hand without hiding cards

    public static void main(String[] args) throws Exception {
        commandLineArguments(args);

        deck = new Deck();
        dealer = new Player("Dealer");
        players = new Player[playerAmount];
        createPlayers();

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

                case "--forceShowDealerHand": // Always show dealer's full hand, without hiding his second card
                    forceShowDealerHand = true;
                    break;

                default:
                    System.out.println("Invalid argument " + args[i]);
            }
        }
    }

    private static void createPlayers() {
        for (int i = 0; i < playerAmount; i++) {
            System.out.print("Input player " + (i + 1) + " name: ");
            players[i] = new Player(input.nextLine());
        }
    }

    private static void newGame() {
        clearScreen();

        // Reset all players
        for (Player p : players) 
            p.reset();

        dealer.reset();
    
        // If current deck is halfway depleted, reshuffle
        if (deck.size() < 26) 
            deck.reset();

        dealStartingHand();

        if (dealer.getSumOfHand() != 21) { // If dealer did not get a blackjack, let players play
            playersTurn();
            dealersTurn();
        } else {
            System.out.println("Blackjack for the dealer!");
        }

        showResults();
    }

    private static void dealStartingHand() {
        for (int i = 1; i < 3; i++) {
            for (Player p : players) { // Give a card to every player in the game
                p.addCardToHand(deck.deal());
                p.showHand();
            }

            // Give a card to the dealer and show his hand
            dealer.addCardToHand(deck.deal());
            if (i == 2 && !forceShowDealerHand) {
                System.out.println("Dealer's hand \n" + dealer.getHand().get(0).toString() + " ????");
            } else {
                dealer.showHand();
            }

        }
    }

    private static void playersTurn() {
        for (Player p : players) {

            if (p.getSumOfHand() == 21) {
                System.out.println("Blackjack for " + p.getName() + "!");
                continue;
            }

            boolean stop = false;
            do {
                System.out.print(p.getName() + ", what do you want to do?\n[1 - Hit, 2 - Stand]: ");
                switch (Integer.parseInt(input.nextLine())) {
                    case 1:
                        p.addCardToHand(deck.deal());
                        p.showHand();

                        if (p.getSumOfHand() == 21) { // If player has 21 points, don't let them hit anymore
                            System.out.println("21! Just what you needed\n");
                            stop = true;
                        } else if (p.getSumOfHand() > 21) { // If player has gone over 21, they lose
                            System.out.println("Over 21! You bust\n");
                            p.setBusted(true);
                            stop = true;
                        }

                        break;
                    case 2:
                        System.out.println(p.getName() + " stands\n");
                        stop = true;
                        break;
                    default:
                        break;
                }

            } while (!stop);
        }
    }

    private static void dealersTurn() {
        dealer.showHand();

        // Dealer will keep hitting until they reach or surpass "dealerStandOn" or until they bust
        while (dealer.getSumOfHand() < dealerStandOn) {
            dealer.addCardToHand(deck.deal());
            dealer.showHand();

            if (dealer.getSumOfHand() > 21) {
                System.out.println("Dealer busts!");
                dealer.setBusted(true);
                break;
            }
        }
    }

    private static void showResults() {
        for (Player p : players) {
            if (p.isBusted()) {
                System.out.println(p.getName() + " loses!");
                p.incrementLosses();
            } else if (p.getSumOfHand() > dealer.getSumOfHand() || dealer.isBusted()) {
                System.out.println(p.getName() + " wins against the dealer!");
                p.incrementWins();
            } else if (p.getSumOfHand() == dealer.getSumOfHand()) {
                System.out.println(p.getName() + " pushes!");
                p.incrementPushes();
            } else {
                System.out.println(p.getName() + " loses!");
                p.incrementLosses();
            }
        }

        System.out.print("Play again? [y/n] ");
        switch (input.nextLine()) {
            case "y":
                newGame();
                break;
            case "n":
                // Show wins, losses and pushes for every player, then quit
                System.out.println("\nFinal results:");
                for (Player p : players)
                    System.out.println(
                            p.getName() + ": " + p.getWins() + "W " + p.getLoses() + "L " + p.getPushes() + "P");

                System.out.println("Thanks for playing!");
                break;
            default:
        }
    }

    private static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {

        }
    }

}
