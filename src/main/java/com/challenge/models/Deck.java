package com.challenge.models;

import com.challenge.models.enums.Rank;
import com.challenge.models.enums.Suit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class representing Deck for the game
 */
@NoArgsConstructor
@Setter
@Getter
public class Deck {
    private List<Card> cards;
    private List<Card> playedCards;

    /**
     * Initializes the deck creating an initial stack of a {@link Card}s.
     * If a game is continuing just clear the stacks of cards
     */
    public void initializeDeck() {
        if (!cards.isEmpty()) {
            cards.clear();
        }

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }

        playedCards.clear();
    }

    /**
     * Shuffles the cards on the deck
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Deal cards to the players, each player must have 5 cards
     */
    public void dealCards(List<Player> players) {
        for (Player player : players) {
            for (int i = 0; i < 4; i++) {
                if (!cards.isEmpty()) {
                    player.drawCard(drawCard());
                }
            }
        }
    }

    /**
     * Reveals an initial card for the game
     *
     * @return the first card from cards on deck
     */
    private Card revealInitialCard() {
        return drawCard();
    }

    /**
     * Draws card from deck and remove it from there. If there are no cards, then shuffle already played cards
     *
     * @return card drawn from deck
     */
    private Card drawCard() {
        if (cards.isEmpty()) {
            cards = playedCards;
            playedCards.clear();
            shuffle();
        }

        return cards.removeFirst();
    }

    /**
     * Draws cards in given amount fromm deck
     *
     * @param count amount of cards to draw from deck
     * @return list of drawn cards from deck
     */
    private List<Card> drawCards(int count) {
        List<Card> drawnCards = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            drawnCards.add(drawCard());
        }

        return drawnCards;
    }
}
