package card;

public enum Suit {
    DIAMONDS ("Diamonds"), 
    HEARTS ("Hearts"), 
    SPADES ("Spades"), 
    CLUBS ("Clubs");

    private String suit;

    Suit (String suit){
        this.suit = suit;
    }

    public String getSuit() {
        return suit;
    }
}
