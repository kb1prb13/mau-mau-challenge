package com.challenge.models;

import com.challenge.models.enums.CardType;
import com.challenge.models.enums.Rank;
import com.challenge.models.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Card {
    private Rank rank;
    private Suit suit;

    public int getRankValue() {
        return rank.getValue();
    }

    public boolean isSpecialCard() {
        return rank.getValue() == 0;
    }

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
