import java.util.*;

public class Game { // Partida

    public static final String DIVIDER = "--------------\n";
    
    private List<Player> players;
    private Player house;
    private MultiDeck deck;
    
    public Game(Player house, List<Player> players, MultiDeck deck) {
        this.house = house;
        this.players = players;
        this.deck = deck;
    }

    public void removePlayersCards() {
        for (Player player : players) {
            player.removeCards();
        }
        house.removeCards();
    }

    public void play() {
        removePlayersCards();
        setBets();
        doFirstRound();
        System.out.println(this);
        while (arePlayersPlaying()) {
            doRound();
        }
        doHouseRound();
        payBets();
        System.out.println("=============================");
        System.out.println("=     RESULTS               =");
        System.out.println("=============================");
        System.out.println(this);
    }

    private void payBets() {
        for (Player player : players) {
            if (player.getState() == PlayerState.STOOD) {
                int housePoints = house.getPoints();
                int playerPoints = player.getPoints();
                int bet = player.getBet();
                if (housePoints == playerPoints) {
                    player.payBet(bet);
                } else if (player.hasBlackjack()) {
                    player.payBet(3 * bet);
                } else if (playerPoints > housePoints ||
                        house.getState() == PlayerState.BUSTED) {
                    player.payBet(2 * bet);
                }

            }
            if (player.getDollars() == 0) {
                player.setState(PlayerState.BROKE);
            } else {
                player.setState(PlayerState.PLAYING);
            }
        }
    }

    private boolean arePlayersPlaying() {
        for (Player player: players) {
            if (player.getState() == PlayerState.PLAYING) {
                return true;
            }
        }
        return false;
    }

    public void doFirstRound() {
        for (int round = 1; round <= 2; round++) {
            for (Player player : players) {
                if (player.getState() == PlayerState.PLAYING) {
                    player.giveCard(deck.extractCard());
                }
            }
            house.giveCard(deck.extractCard());
        }
    }

    public void doRound() {
        for (Player player : players) {
            doPlayerRound(player);
        }
    }

    private void doHouseRound() {
        while (house.getState() == PlayerState.PLAYING) {
            if (house.getPoints() >= 17) {
                house.setState(PlayerState.STOOD);
            } else {
                house.setState(PlayerState.PLAYING);
                house.giveCard(deck.extractCard());
                house.updateState();
                System.out.println(house);
            }
        }
    }

    private void doPlayerRound(Player player) {
        System.out.println(player);
        if (player.getState() == PlayerState.PLAYING) {
            askPlayer(player);
        }
        switch (player.getState()) {
            case PLAYING:
                player.giveCard(deck.extractCard());
                player.updateState();
                System.out.println(player);
                break;
            case DOUBLED_DOWN:
                player.setBet(2 * player.getBet());
                player.giveCard(deck.extractCard());
                player.setState(PlayerState.STOOD);
                player.updateState();
                System.out.println(player);
                break;
        }
    }

    private void askPlayer(Player player) {
        int option = 1000;
        while (option >= 3) {
            option = Global.readValidInteger("Choose option (1) STAND  (2) HIT  (3) DOUBLE DOWN: ");
            if (option == 3)  {
                if (player.getDollars() < player.getBet()) {
                    System.out.println("You don't have funds to double down.");
                    option = 1000;
                }
            }
        }
        setChosenPlayerState(player, option);
    }

    private static void setChosenPlayerState(Player player, int option) {
        switch (option) {
            case 1: player.setState(PlayerState.STOOD);
            break;
            case 2: player.setState(PlayerState.PLAYING);
            break;
            case 3: player.setState(PlayerState.DOUBLED_DOWN);
        }
    }

    public void setBets() {
        for (Player player : players) {
            if (player.getState() == PlayerState.BROKE) {
                System.out.println(player.getName() + " [" +
                        PlayerState.BROKE + "]");
            } else {
                String message = player.getName() + ". Set your bet [" +
                        player.getDollars() + "]: ";
                int bet = Global.readValidInteger(message);
                player.setBet(bet);
            }

        }
    }

    @Override
    public String toString() {
        String s = "";
        for (Player player : players) {
            s += player + DIVIDER;
        }
        s += house + DIVIDER;
        return s;
    }

}
