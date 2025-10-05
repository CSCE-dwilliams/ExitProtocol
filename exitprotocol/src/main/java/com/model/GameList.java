package com.model;

public class GameList {
    private static GameList gameList;

    private GameList() {}

    public static GameList getInstance() {
        if (gameList == null) {
            gameList = new GameList();
        }

        return gameList;
    }

    public void createGame(String theme, int difficulty, int playerCout, String teamName) {}
    public Game getGame() {}
    public void saveGame() {}
}
