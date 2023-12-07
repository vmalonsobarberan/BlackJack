import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        MultiDeck deck = new MultiDeck(1);
        deck.shuffle();
        Player house = new Player();

        int numPlayers = Global.readValidInteger("Number of players: ");
        List<Player> players = new ArrayList<Player>();

        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name of player " + (i+1) + ": ");
            String name = Global.inputKeyboard.nextLine();
            int money = Global.readValidInteger("Initial amount of $ for " + name + ": ");
            Player player = new Player(name, money);
            players.add(player);
        }


        Game game = new Game(house, players, deck);
        while (true) {
            game.play();
            System.out.println("Press q to quit");
            if (Global.inputKeyboard.nextLine().equals("q")) {
                break;
            }
        }

    }


}
