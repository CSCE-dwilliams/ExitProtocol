package com.model;

public class UI {
    
    public void runScenarios() {
        loginSuccessfulAndAnswerFirstQuestion();
    }

    public void loginSuccessfulAndAnswerFirstQuestion() {
        EscapeManager manager = new EscapeManager();
/*
        if(!manager.logIn("mister@email.com", "1234")){
            System.out.println("Sorry");
            return;
        }

        System.out.println("We've logged in " + manager.getCurrentUser());

        ArrayList<Game> games = manager.getGames();

        manager.selectGame(game);

        Challenge challenge = manager.getNextChallenge();
        System.out.println("Your challenge is " + challenge.getDescription());
        Hint hint = challenge.getHint();
        System.out.println("The hint is " + hint);

        if(!challenge.answer("sdfawefwawe")){
            System.out.println("not good");
            return;
        }

        System.out.println("You won the challenge");
*/

    }

    public static void main(String[] args){
        (new UI()).runScenarios();
    }
}
