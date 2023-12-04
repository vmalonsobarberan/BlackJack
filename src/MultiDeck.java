import java.util.*;

public class MultiDeck {
    private List<Card> cards;

    public MultiDeck(int numDecks) {
        cards = new ArrayList<Card>();
        for (int i = 1; i <= numDecks; i++) {
            for (Suit suit : Suit.values()) {
                for (int number = 1; number <= 13; number++) {
                    cards.add(new Card(number, suit));
                }
            }
        }
    }

    @Override
    public String toString() {
        boolean first = true;
        String s = "";
        for (Card card : cards) {
            if (first) {
                first = false;
            } else {
                s += ", ";
            }
            s += card;
        }
        return s;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card extractCard() {
        return cards.remove(0);
    }
}
