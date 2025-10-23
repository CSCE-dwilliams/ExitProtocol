package com.model;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class GameList {
    private static GameList gameList;
    private ArrayList<GameTemplate> games = new ArrayList<>();

    private GameList() { }

    public static GameList getInstance() {
        if (gameList == null) {
            gameList = new GameList();
        }

        return gameList;
    }

    // public void createGame(String theme, int difficulty, int playerCount, String
    // teamName) {
    // games.add(new Game(theme, difficulty, playerCount, teamName));
    // }

    public void loadGames() {
        games = DataLoader.getGames();
    }
    public ArrayList<GameTemplate> getTemplates(){
        return games;
    }
    public void showGames() {
        System.out.println("Set of Games:");
        for (int i = 0; i < games.size(); i++) {
            for (String s : games.get(i).getQuestions()) {
                System.out.println(s);
            }
        }
    }

    public void getGameData(Game aGame){
        String gameTheme = aGame.getTheme();
        for(GameTemplate g : games){
            if(g.getTheme().equalsIgnoreCase(gameTheme)){
                aGame.setGameSet(g);
            }
        }
    }
    // public Game getGame() {}

    public void saveGame() {
    }
}
