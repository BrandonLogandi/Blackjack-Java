package card;

public class Card {

    private Suit suit;
    private Value value;

    public Card(Suit s, Value v) {
        this.suit = s;
        this.value = v;
    }

    public Suit getSuit(){
        return suit;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.getWrittenValue() + " of " + suit.getSuit();
    }

}
