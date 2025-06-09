package com.challenge.maumau.models;

import com.challenge.maumau.models.enums.CardType;
import com.challenge.maumau.models.enums.Rank;
import com.challenge.maumau.models.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing play card
 * @see Rank
 * @see Suit
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Card {
    private Rank rank;
    private Suit suit;

    /**
     * Get the value for a rank to calculate points
     * @return value of a rank
     */
    public int getRankValue() {
        return rank.getValue();
    }

    /**
     * Check if the card is a special card
     * @return true if a {@link CardType} differs from {@link CardType#NORMAL}
     */
    public boolean isSpecialCard() {
        return getCardType() != CardType.NORMAL;
    }

    /**
     * Get type of the card
     * @return {@link CardType#TWO} for {@link Rank#SEVEN}, {@link CardType#SKIP} for {@link Rank#EIGHT},
     * {@link CardType#REVERSE} for {@link Rank#NINE} and {@link CardType#NORMAL} for all other cards
     */
    public CardType getCardType() {
        return switch (rank) {
            case SEVEN -> CardType.TWO;
            case EIGHT -> CardType.SKIP;
            case NINE -> CardType.REVERSE;
            default -> CardType.NORMAL;
        };
    }

    @Override
    public String toString() {
        return String.format("%s of %s", rank, suit);
    }
}
