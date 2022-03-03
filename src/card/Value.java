package card;

public enum Value {
    ACE ("Ace", 11),
    TWO ("2", 2), 
    THREE ("3", 3), 
    FOUR ("4", 4), 
    FIVE ("5", 5), 
    SIX ("6", 6), 
    SEVEN ("7", 7), 
    EIGHT ("8", 8), 
    NINE ("9", 9), 
    TEN ("10", 10), 
    J ("Jack", 10), 
    Q ("Queen", 10), 
    K ("King", 10);

    private String writtenValue;
    private int numericValue;

    Value(String writtenValue, int numericValue){
        this.writtenValue = writtenValue;
        this.numericValue = numericValue;
    }

    public String getWrittenValue() {
        return writtenValue;
    }

    public int getNumericValue() {
        return numericValue;
    }
}
