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
    
    public String getWrittenValue() {
        return value.getWrittenValue();
    }

    public int getNumericValue() {
        return value.getNumericValue();
    }

    @Override
    public String toString() {
        return value.getWrittenValue() + " of " + suit.getSuit();
    }

}
