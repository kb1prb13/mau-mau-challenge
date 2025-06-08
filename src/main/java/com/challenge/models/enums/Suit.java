package com.challenge.models.enums;

/**
 * Suits of the cards
 * @see #HEART
 * @see #SPADE
 * @see #CLUB
 * @see #DIAMOND
 */
public enum Suit {
    HEART {
        public String toString() {
            final String ANSI_RESET = "\u001B[0m";
            String c = Character.toString('♥');
            return c + ANSI_RESET;
        }
    }, SPADE {
        public String toString() {
            final String ANSI_RESET = "\u001B[0m";
            String c = Character.toString('♠');
            return c + ANSI_RESET;
        }
    }, CLUB {
        public String toString() {
            final String ANSI_RESET = "\u001B[0m";
            String c = Character.toString('♣');
            return c + ANSI_RESET;
        }
    }, DIAMOND {
        public String toString() {
            final String ANSI_RESET = "\u001B[0m";
            String c = Character.toString('♦');
            return c + ANSI_RESET;
        }
    }
}
