package com.challenge;

import com.challenge.models.Card;
import com.challenge.models.Deck;
import com.challenge.models.Player;
import com.challenge.models.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class representing a gameplay and all actions during the game
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
     * Deck for game cards
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
    /**
     * The last played card or initial card at the game start
     */
    private Card activeCard;

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
     */
    public void startNewRound() {
        cardDeck.initializeDeck();
        cardDeck.shuffle();
        cardDeck.dealCards(players);
        setRoundStarter();
        activeCard = cardDeck.revealInitialCard();

        // set round starter player as active player
        activePlayer = roundStarter;
        // increment the round count, as the round is started
        currentRound++;

        // start playing the game
        playRound();
    }

    /**
     * Controls the game process inside the round
     */
    private void playRound() {
        // the whole process of the turn for each player
        // run while loop till round is over
        while (isRoundOver()) {
            // check if active player has to draw cards or/and skip turn
            if (activePlayer.isMustDrawCards()) {
                activePlayer.drawCards(cardDeck.drawCards(2));
                activePlayer = getNextPlayer(false);
                continue;
            } else if (activePlayer.isMustSkipTurn()) {
                activePlayer = getNextPlayer(false);
                continue;
            }

            // display the game info if active player is a human player
            if (activePlayer.isHuman()) {
                displayGameInfo();
            }

            // play card
            Card playedCard = activePlayer.playCard();

            // handle played card action and determine next player according special card action
            activePlayer = getNextPlayer(handlePlayedCard(playedCard));

            // update active card and add it to already played cards
            activeCard = playedCard;
            cardDeck.addCardToPlayedCards(playedCard);
        }

        // calculate points and display results after round is over
        calculateRoundPoints();
        displayFinalResults();
    }

    /**
     * Prints cards of active player, the top card, and an amount of cards of players
     */
    public void displayGameInfo() {
        System.out.format("You have %s cards: %s\n", activePlayer.getHand().size(), activePlayer.getHandAsString());
        System.out.println("Top card is: " + activeCard.toString());

        displayOtherPlayersCardsCount();
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
     *
     * @return true if one of players has no cards
     */
    public boolean isRoundOver() {
        return players.stream().anyMatch(Player::hasNoCards);
    }

    /**
     * Handles a special card and takes actions according {@link com.challenge.models.enums.CardType}.
     *
     * @param card a card, to handle his action
     * @return true if game direction has been changed, false if game direction remains same
     */
    public boolean handlePlayedCard(Card card) {
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
                    return true;
                }
            }
        }

        return false;
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
     *
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
        System.out.println(System.lineSeparator() + "Game Results:");
        for (Player player : players) {
            System.out.println(player.getName() + ": ");
            System.out.println("Wins: " + player.getWins());
            System.out.println("Points: " + player.getPoints() + System.lineSeparator());
        }
    }

    /**
     * Prints the amount of cards of players except active
     */
    public void displayOtherPlayersCardsCount() {
        System.out.println("Cards number of other players: ");
        players.stream()
                .filter(p -> !p.equals(activePlayer))
                .forEach(p -> System.out.println(p.getName() + " has " + p.getHand().size() + " cards"));
    }
}
