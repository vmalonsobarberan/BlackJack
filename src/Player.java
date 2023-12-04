import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int dollars;
    private boolean house;
    private List<Card> hand;
    private PlayerState state;
    private int bet;

    public Player(String name, int dollars) {
        this.name = name;
        this.dollars = dollars;
        hand = new ArrayList<Card>();
        house = false;
        state = PlayerState.PLAYING;
        bet = 0;
    }

    public Player() {
        name = "HOUSE";
        dollars = Integer.MAX_VALUE;
        hand = new ArrayList<Card>();
        house = true;
        state = PlayerState.PLAYING;
        bet = 0;
    }

    public boolean isHouse() {
        return house;
    }

    public void removeCards() {
        hand.clear();
    }

    private String getHandStr() {
        boolean first = true;
        String s = "";
        for (Card card : hand) {
            if (first) {
                first = false;
                s += card;
            } else {
                s += ",";
                if (house) {
                    s += state == PlayerState.PLAYING ? Card.BACK_CARD_SYMBOL : card;
                } else {
                    s += card;
                }
            }
        }
        return s;
    }

    public void giveCard(Card card) {
        hand.add(card);
    }

    @Override
    public String toString() {
        String s = name + ": " + getHandStr();
        switch (state) {
            case PLAYING: s += "  [PLAYING]";
            break;
            case STOOD: s += "  [STOOD]";
            break;
            case BUSTED: s += "  [BUSTED]";
            break;
        }
        if (!house || state != PlayerState.PLAYING) {
            s += " Points: " +  getPoints() + "\n";
        }
        if (!house) {
            if (bet > 0) {
                s += "Your bet: " + bet + "\n";
            }
            s += "Money left: $" + dollars;
        }
        s += "\n";
        return s;
    }

    public int getPoints() {
        int points = 0;
        boolean wasAce = false;
        for (Card card : hand) {
            int pointsCard = card.getPoints();
            points += pointsCard;
            if (pointsCard == 1) {
                wasAce = true;
            }
        }
        if (wasAce && points + 10 <= 21) {
            points += 10;
        }
        return points;
    }

    public static Player readPlayerFromKeyboard() {
        System.out.print("Enter name of the player: ");
        String name = Global.inputKeyboard.nextLine();
        int money = Global.readValidInteger("Initial amount of $: ");
        return new Player(name, money);
    }

    public String getName() {
        return name;
    }

    public int getDollars() {
        return dollars;
    }

    public void setBet(int amount) {
        bet = Math.min(amount, dollars);
        dollars -= bet;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public int getBet() {
        return bet;
    }

    public void updateState() {
        if (getPoints() > 21) {
            state = PlayerState.BUSTED;
        }
    }

    public boolean hasBlackjack() {
        if (hand.size() != 2) {
            return false;
        }
        Card c1 = hand.get(0);
        Card c2 = hand.get(1);
        if (c1.getPoints() == 10 && c2.getPoints() == 1 ||
            c1.getPoints() == 1 && c2.getPoints() == 10) {
            return true;
        }
        return false;
    }

    public void payBet(int income) {
        dollars += income;
    }
}
