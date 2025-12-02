package com.model;

public class UI {

    public void mainLoop() {
        System.out.println(
                "WELCOME TO EXIT PROTOCOL\n*********************\nAn Escape Room Bonanza Picture by The Clankers (Copyright 2006)\n");
        System.out.println("""
                  ________   _______ _______   _____  _____   ____ _______ ____   _____ ____  _
                 |  ____\\ \\ / /_   _|__   __| |  __ \\|  __ \\ / __ \\__   __/ __ \\ / ____/ __ \\| |
                 | |__   \\ V /  | |    | |    | |__) | |__) | |  | | | | | |  | | |   | |  | | |
                 |  __|   > <   | |    | |    |  ___/|  _  /| |  | | | | | |  | | |   | |  | | |
                 | |____ / . \\ _| |_   | |    | |    | | \\ \\| |__| | | | | |__| | |___| |__| | |____
                 |______/_/ \\_\\_____|  |_|    |_|    |_|  \\_\\\\____/  |_|  \\____/ \\_____\\____/|______|


                """);
        System.out.println("------------------------------------------------");
        EscapeManager manager = EscapeManager.getInstance();
        manager.logIn();

        System.out.println("Thank you for playing our game.\n[Insert quote something here]");
        System.exit(0);
    }

    public void signInLoop() {
        UserList userList = UserList.getInstance();
    }

    public static void main(String[] args) {
        (new UI()).mainLoop();
    }
}