package com.model;

import java.util.ArrayList;

/**
 * The GameList class is a singleton that manages a collection of
 * GameTemplate objects. It serves as a list of game templates
 * that users can select and use to create or load game instances.
 * 
 * @author Clankers
 */
public class GameList {
    private static GameList gameList;
    private ArrayList<GameTemplate> games;

    /**
     * Singleton game list that manages a collection of game objects.
     */
    private GameList() {
        games = DataLoader.getGames();
    }

    /**
     * This will return the different instances of games
     * 
     * @return singleton instance of games
     */
    public static GameList getInstance() {
        if (gameList == null) {
            gameList = new GameList();
        }
        return gameList;
    }

    /**
     * Loads the game templates from storage into games??
     */
    public void loadGames() {
        games = DataLoader.getGames();
    }

    /**
     * Will return a list of available game templates
     * 
     * @return an array list with available games
     */
    public ArrayList<GameTemplate> getTemplates() {
        return games;
    }

    /**
     * Populates the provided Game instance with data from the corresponding
     * GameTemplate, based on the game's theme.
     *
     * @param aGame the game instances populated data
     */

    // lowkey this should probably return a game with the selected theme, would make
    // management easier

    public Game selectAndCreateGame(GameSession session) {
        Game gameObject = new Game(session);
        String theme = gameObject.getTheme();
        for (GameTemplate g : games) {
            if (g.getTheme().equalsIgnoreCase(theme)) {
                gameObject.setGameSet(g);
            }
        }
        return gameObject;
    }

    public void setGameData(Game aGame) {
        String gameTheme = aGame.getTheme();
        for (GameTemplate g : games) {
            if (g.getTheme().equalsIgnoreCase(gameTheme)) {
                aGame.setGameSet(g);
            }
        }
    }

    /**
     * Saves current game data
     */
    public void saveGame() {
    }

}
