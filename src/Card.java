public class Card {
    public static final String SPADES_SYMBOL = "♠";
    public static final String HEARTS_SYMBOL = "♥";
    public static final String DIAMONDS_SYMBOL = "♦";
    public static final String CLOVERS_SYMBOL = "♣";
    public static final String BACK_CARD_SYMBOL = "\uD83C\uDCA0";

    private int number;
    private Suit suit;

    public Card(int number, Suit suit) {
        this.number = number;
        this.suit = suit;
    }

    @Override
    public String toString() {
        String s = "";
        switch (number) {
            case 11: s += "J";
            break;
            case 12: s += "Q";
            break;
            case 13: s += "K";
            break;
            default: s += number;
        }
        switch (suit) {
            case SPADES: s += SPADES_SYMBOL;
            break;
            case HEARTS: s += HEARTS_SYMBOL;
            break;
            case DIAMONDS: s += DIAMONDS_SYMBOL;
            break;
            case CLOVERS: s += CLOVERS_SYMBOL;
        }
        return s;
    }

    public int getPoints() {
        if (number >= 11) {
            return 10;
        } else {
            return number;
        }
    }
}
