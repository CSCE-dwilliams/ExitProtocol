package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;

public class GameList {
    private static GameList gameList;
    private HashMap<UUID, Game> games = new HashMap<>();
    
    private GameList() {}
    public static GameList getInstance() {
        if (gameList == null) {
            gameList = new GameList();
        }
        return gameList;
    }

    public void createGame(String theme, int difficulty, int playerCount, String teamName, ArrayList<Challenge> gameset) {
        games.add(new Game(theme, difficulty, playerCount, teamName, gameset));
    }

    // public Game getGame() {}

    public void getGames(UUID id)
    {
        ArrayList<Game> gamesbyid = new ArrayList<>();
        /* 
        for(Game game : gameList)
        {
            
            if (game.getUUID().equals(id))
            {

            }
            
            //cannot finish method until GamesList is made
        }
    }

    public void addgame(Game g){
        games.put(g.getGameID(),g);
    }
    
    public Game getGame(UUID id){
        return games.get(id);
    }  

    // public void createGame(String theme, int difficulty, int playerCount, String teamName) {
    //     games.add(new Game(theme, difficulty, playerCount, teamName, null));


    // }

    public void saveGame() {}
}
