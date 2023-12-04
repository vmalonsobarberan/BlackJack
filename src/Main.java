import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        MultiDeck deck = new MultiDeck(1);
        deck.shuffle();
        Player house = new Player();
        Player p2 = new Player("Victor", 1000);
        Player p3 = new Player("Carlos", 1000);
        List<Player> list = new ArrayList<Player>();

        list.add(p2);
        list.add(p3);

        Game game = new Game(house, list, deck);
        while (true) {
            game.play();
        }
    }


}
