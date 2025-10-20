package com.model;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class GameList {
    private static GameList gameList;

    private GameList() {}
    private ArrayList<Game> games = new ArrayList<>();

    public static GameList getInstance() {
        if (gameList == null) {
            gameList = new GameList();
        }

        return gameList;
    }

    public void createGame(String theme, int difficulty, int playerCount, String teamName) {
        games.add(new Game(theme, difficulty, playerCount, teamName));
    }

    public void loadGames(){
        
    }

    // public Game getGame() {}

    

    public void saveGame() {}
}
