package com.challenge.maumau.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing player
 */
@Setter
@Getter
public class Player {
    /**
     * Name of the player
     */
    private String name;
    /**
     * Players cards, i.e. cards on hand
     */
    private List<Card> hand;
    /**
     * Count of wins
     */
    private int wins;
    /**
     * Point earned
     */
    private int points;
    /**
     * Flag to differentiate human player and PC
     */
    private boolean isHuman;
    /**
     * Flag if the player must skip his turn
     */
    private boolean mustSkipTurn;
    /**
     * Flag if the player must draw cards in case a special card has been used
     */
    private boolean mustDrawCards;

    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        this.hand = new ArrayList<>();
        this.wins = 0;
        this.points = 0;
        this.mustSkipTurn = false;
        this.mustDrawCards = false;
    }

    /**
     * Plays best matching {@link Card}, i.e. removes it from players hand
     *
     * @return best matching {@link Card}, i.e. removed from players hand
     */
    public Card playCard() {
        // TODO realize strategies to interact with human player and to choose best matching card for PC
        return hand.removeFirst();
    }

    /**
     * Draw given {@link Card}, i.e. add card to players hand
     *
     * @param card {@link Card} to draw, i.e. add to hand
     */
    public void drawCard(Card card) {
        hand.add(card);
    }

    /**
     * Draws given amount of {@link Card}, i.e. add cards to players hand
     *
     * @param cards {@link List<Card>} to draw, i.e. add them to hand
     */
    public void drawCards(List<Card> cards) {
        hand.addAll(cards);
    }

    /**
     * Check if player has no cards. Used to determine the game end
     *
     * @return true if player has no cards
     */
    public boolean hasNoCards() {
        return hand.isEmpty();
    }

    /**
     * Returns players cards in readable format
     *
     * @return players cards in readable format
     */
    public String getHandAsString() {
        return Arrays.toString(hand.toArray()).replace("[", "").replace("]", "");
    }
}
