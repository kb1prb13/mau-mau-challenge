package com.challenge.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    public Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        this.hand = new ArrayList<>();
        this.wins = 0;
        this.points = 0;
        this.mustSkipTurn = false;
    }

    /**
     * Play the given {@link Card}, i.e. remove the card from players hand
     * @param card {@link Card} to play, i.e. remove from hand
     */
    private void playCard(Card card) {
        hand.remove(card);
    }

    /**
     * Draw given {@link Card}, i.e. add card to players hand
     * @param card {@link Card} to draw, i.e. add to hand
     */
    public void drawCard(Card card) {
        hand.add(card);
    }

    /**
     * Check if player has no cards. Used to determine the game end
     * @return true if player has no cards
     */
    public boolean hasNoCards() {
        return hand.isEmpty();
    }
}
