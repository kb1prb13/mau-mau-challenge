package com.challenge.models;

import com.challenge.models.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing a gameplay and all actions during game
 */
@AllArgsConstructor
@Setter
@Getter
public class Game {
    /**
     * List of players
     */
    private List<Player> players;
    /**
     * The player whose turn it is
     */
    private Player activePlayer;
    /**
     * Deck for the game
     */
    private Deck cardDeck;
    /**
     * Direction of the game
     */
    private Direction gameDirection;
    /**
     * Player starting round
     */
    private Player roundStarter;
    /**
     * Counter for the rounds
     */
    private int currentRound;
    /**
     * Flag for game end
     */
    private boolean isGameEnded;

    public Game() {
        this.players = new ArrayList<>();
        this.cardDeck = new Deck();
        this.gameDirection = Direction.CLOCKWISE;
        this.isGameEnded = false;
        this.currentRound = 0;
    }

    /**
     * Starts new game
     */
    public void startGame() {
        startNewRound();
    }

    /**
     * Starts the round by initializing deck, shuffling and dealing cards to the players
     * and choosing the starting player for round
     * */
    public void startNewRound() {
        cardDeck.initializeDeck();
        cardDeck.shuffle();
        cardDeck.dealCards(players);
        setRoundStarter();

        // set round starter player as active player
        activePlayer = roundStarter;
        // increment the round count, as the round is started
        currentRound++;
    }

    /**
     * Randomly chooses a starting player for initial round.
     * For second and next rounds chooses next to previous round starter
     */
    public void setRoundStarter() {
        // if the game starts the first time, pick random player as round starter
        if (currentRound == 0) {
            int randomElementIndex = new Random().nextInt(players.size());
            roundStarter = players.get(randomElementIndex);
        } else {
            int roundStarterIndex = players.indexOf(roundStarter);

            // safely check the previous round starter and choose the next player
            if (roundStarterIndex >= players.size() - 1) {
                roundStarter = players.getFirst();
            } else {
                roundStarter = players.get(roundStarterIndex + 1);
            }
        }
    }

    /**
     * Checks if one of players has no cards
     * @return true if one of players has no cards
     */
    public boolean isRoundOver() {
        return players.stream().anyMatch(Player::hasNoCards);
    }

    /**
     * Handles a special card and takes action according {@link com.challenge.models.enums.CardType}.
     * Also sets the next player, because direction change card affects on game
     * @param card a card to take an action
     */
    public void handleSpecialCard(Card card) {
        // get the next player
        Player nextPlayer = getNextPlayer(false);
        
        // actions must take only for special cards
        // set parameters if the next player must take cards, skip his turn or game direction changed
        if (card.isSpecialCard()) {
            switch (card.getCardType()) {
                case TWO -> nextPlayer.setMustDrawCards(true);
                case SKIP -> nextPlayer.setMustSkipTurn(true);
                default -> {
                    reverseDirection();

                    // TODO change the logic to avoid setting next player in this method
                    nextPlayer = getNextPlayer(true);
                }
            }
        }

         activePlayer = nextPlayer;
    }

    /**
     * Reverses a game {@link Direction} in the opposite direction
     */
    public void reverseDirection() {
        if (gameDirection == Direction.CLOCKWISE) {
            gameDirection = Direction.COUNTERCLOCKWISE;
        } else {
            gameDirection = Direction.CLOCKWISE;
        }
    }

    /**
     * Returns the next player, taking into account the change of direction
     * @param directionReversed flag if direction is reversed or changed
     * @return the next player, taking into account the direction change too
     */
    public Player getNextPlayer(boolean directionReversed) {
        // determine current and last players, in order to avoid IndexOutOfBoundsException
        int activePlayerIndex = players.indexOf(activePlayer);
        int lastPlayerIndex = players.size() - 1;

        // if direction reversed, return previous player to current
        if (directionReversed) {
            return activePlayerIndex == 0 ? players.getLast() : players.get(activePlayerIndex - 1);
        }
        
        // get and return the next player after current
        return activePlayerIndex == lastPlayerIndex ? players.getFirst() : players.get(activePlayerIndex + 1);
    }

    /**
     * Calculates points for all players excluding winner
     */
    public void calculateRoundPoints() {
        // Determine a winner
        Player winner = players.stream().filter(Player::hasNoCards).findFirst().orElse(null);

        if (winner != null) {
            // Increment the wins count for winner player
            winner.setWins(winner.getWins() + 1);

            // Calculate points for other players
            for (Player player : players) {
                if (player != winner) {
                    int points = player.getHand().stream().mapToInt(Card::getRankValue).sum();
                    player.setPoints(player.getPoints() + points);
                }
            }
        }
    }

    /**
     * Displays game results in a readable format
     */
    public void displayFinalResults() {
        System.out.println("\nGame Results:\n");
        for (Player player : players) {
            System.out.println(player.getName() + ":");
            System.out.println("\nWins: " + player.getWins());
            System.out.println("\nPoints: " + player.getPoints());
        }
    }
}
