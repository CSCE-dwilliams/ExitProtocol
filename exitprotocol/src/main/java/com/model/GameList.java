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

    public void loadGames(){
        for(int i =0; i < DataLoader.getGames().size();i++){
            games.put(DataLoader.getGames().get(i).getGameID(),DataLoader.getGames().get(i));
        }
    }

    public Game getUserGames(UUID userID){
        UserList userList = UserList.getInstance();
        User newUser = userList.getUser(null, null) ;
        newUser.getUUID();
        Game nuGame = new Game(null, 0, 0, null, userID);
        return nuGame;
        // thinking like return 
    }
    
    // public void createGame(String theme, int difficulty, int playerCount, String teamName) {
    //     games.add(new Game(theme, difficulty, playerCount, teamName, null));


    // }

    public Game getGame(UUID id){
        return games.get(id);
    }

    public void addgame(Game g){
        games.put(g.getGameID(),g);
    }
    

    public void saveGame() {}
}
