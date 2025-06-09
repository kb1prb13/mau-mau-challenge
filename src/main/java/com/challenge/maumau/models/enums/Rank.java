package com.challenge.maumau.models.enums;

import lombok.Getter;

/**
 * Ranks of the cards and their values for calculating points
 */
@Getter
public enum Rank {
    SEVEN(0),
    EIGHT(0),
    NINE(0),
    JACK(2),
    QUEEN(3),
    KING(4),
    TEN(10),
    ACE(11);

    private final int value;

    Rank(int value) {
        this.value = value;
    }
}
