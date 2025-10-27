package com.model;

public class UI{
    public void runScenarios() {
    
    }

    public void mainLoop(){
        System.out.println("WELCOME TO EXIT PROTOCOL\n*********************\nAn Escape Room Bonanza Picture by The Clankers (Copyright 2006)\n");
        System.out.println(
  "+----------------------------------------+\n" +
  "|  _____  _  _  _   _  _____  _   _  _   |\n" +
  "| |  ___|| || || | | ||_   _|| \\ | || |  |\n" +
  "| | |__  | || || |_| |  | |  |  \\| || |  |\n" +
  "| |  __| | || ||  _  |  | |  | . ` || |  |\n" +
  "| | |___ | || || | | |  | |  | |\\  || |  |\n" +
  "| |_____||_||_||_| |_|  |_|  |_| \\_||_|  |\n" +
  "|                                        |\n" +
  "|            EXIT   PROTOCOL             |\n" +
  "+----------------------------------------+\n"
);  
        EscapeManager manager = EscapeManager.getInstance();
        manager.logIn();

        System.out.println("Thank you for playing our game.\n[Insert quote something here]");
        System.exit(0);
    }
    public void signInLoop(){
        UserList userList = UserList.getInstance(); 

    }

    public static void main(String[] args){
        (new UI()).mainLoop();
    }
}