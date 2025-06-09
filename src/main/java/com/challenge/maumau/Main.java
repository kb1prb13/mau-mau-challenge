package com.challenge.maumau;

import com.challenge.maumau.models.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class creating players (without user interaction) and starting the game
 */
public class Main {
    public static void main(String[] args) {
        // due to lack of interactiveness, hardcoded three players to show the basic game process
        List<Player> players = new ArrayList<>();
        players.add(new Player("Andreas", true));
        players.add(new Player("Philipp", false));
        players.add(new Player("Gerd", false));

        // create the game, assign players and start a game
        Game game = new Game();
        game.setPlayers(players);
        game.startGame();

        // example of displaying the game info for human player
        game.displayGameInfo();
    }
}
