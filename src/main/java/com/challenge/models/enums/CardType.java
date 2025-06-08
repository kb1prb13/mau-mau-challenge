package com.challenge.models.enums;

/**
 * Card types used in a game
 *  @see #SKIP
 *  @see #TWO
 *  @see #REVERSE
 *  @see #NORMAL
 */
public enum CardType {
    /**
     * Next player should skip turn
     */
    SKIP,

    /**
     * Next player should take two cards from deck
     */
    TWO,

    /**
     * Reverse game {@link Direction}, e.g. from CLOCKWISE to COUNTERCLOCKWISE
     */
    REVERSE,

    /**
     * Normal card without abilities
     */
    NORMAL
}
