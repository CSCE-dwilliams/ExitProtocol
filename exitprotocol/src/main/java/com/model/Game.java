package com.model;

/**
 * Game class helps run the game based off of the Game session parameters.
 * It holds the theme, difficulty, and player count for the game session.
 * @author The Clankers
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Game {
    private String theme;
    private int difficulty;
    private int playerCount;
    private UUID gameID;
    private int score;
    private int itemIndex;
    private int playerIndex;
    private int hintIndex;
    private int hintPenalty = 25;
    private int correctPoints = 100;
    private boolean quit = false;

    private GameSession session;

    ArrayList<Challenge> challenges = new ArrayList<>();
    ArrayList<Item> gameItems = new ArrayList<>();
    ArrayList<String> hints = new ArrayList<>();
    private GameTemplate gameSet;

    /**
     * Creates a game session based on the theme, difficulty, and player count
     * 
     * @param theme       is used in game generation
     * @param difficulty  is used to set the level of challenge
     * @param playerCount is used to set how many players are in the game
     */
    public Game(GameSession session) {
        this.theme = session.getSessionTheme();
        this.difficulty = session.getDifficulty();
        this.playerCount = session.getPlayerCount();
        this.score = session.getScore();
        this.session = session;
    }

    public String getIntro() {
        if (gameSet != null) {
            return gameSet.getIntroText();
        }
        return "";
    }

    public String getTheme() {
        return this.theme;
    }

    public int getPlayerIndex() {
        return this.playerIndex;
    }

    public void setPlayerIndex(int index) {
        this.playerIndex = index;
    }

    public int getHintIndex() {
        return this.hintIndex;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public Challenge getCurrentChallenge() {
        return getChallenges().get(playerIndex);
    }

    public void decScore(int points) {
        int replaceScore = this.score - points;
        this.score = replaceScore;
    }

    public boolean attemptQuestion(String answer) {
        return getCurrentChallenge().getAnswer().equalsIgnoreCase(answer);
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public int getIndex() {
        return playerIndex;
    }

    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Item> getItems() {
        return gameItems;
    }

    public void setGameSet(GameTemplate aGameTemplate) {
        this.gameSet = aGameTemplate;
        challenges = gameSet.getChallenges();
    }

    public void attemptQuestion() {

    }

    public void challengeStart(int startingIndex) {
        playerIndex = startingIndex;
        if (playerIndex < 1) {
            // printing intro
            gameSet.getIntro();
        }
        challenges = gameSet.getChallenges();
    }

    // outdated
    public void assignChallenges() {
        challenges = gameSet.getChallenges();
    }

    public boolean hasItems() {
        if (gameItems.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public UUID getID() {
        return gameID;
    }

    /*
     * 
     * 
     * [GAME LOOP SECTION]
     * 
     */

    public void runGame() {
        // loads game items back into game if game is started at index
        // besides 0
        for (int i = 0; i < playerIndex; i++) {
            ArrayList<Item> items = challenges.get(i).getItems();
            if (items.size() > 0) {
                gameItems.add(items.get(0));
            }
        }

        while (!quit && playerIndex < challenges.size()) {
            int challengeNo = 1 + playerIndex;
            System.out.println("ATTEMPT CHALLENGE " + challengeNo + ":\n");
            System.out.println("Correct answers +100 points\nUsing a hint: -25 points\nSkipping: -75 points");
            System.out.println("--------------------------");

            questionLoop(playerIndex);
            if (quit) {
                break;
            }
            playerIndex++;
        }

        if (playerIndex >= challenges.size()) {
            String diff;

            if (this.difficulty == 1) {
                diff = "Easy";
            } else if (this.difficulty == 2) {
                diff = "Medium";
            } else {
                diff = "Hard";
            }

            try (java.io.FileWriter writer = new java.io.FileWriter("Certificate.txt")) {
                writer.write("========================================\n");
                writer.write("              EXIT PROTOCOL             \n");
                writer.write("========================================\n\n");
                writer.write("Thanks for following the Exit Protocol.\n");
                writer.write("You have escaped ... for now.\n");
                writer.write("Game Details:\n");
                writer.write("-------------\n");
                writer.write("Theme: " + this.theme + "\n");
                writer.write("Difficulty: " + diff + "\n");
                writer.write("Player Count: " + this.playerCount + "\n");
                writer.write("Hints Used: " + session.getHintsUsed() + "\n");
                writer.write("Final Score: " + this.score + "\n\n");
                writer.write("Well done on your achievement!\n");
                writer.write("========================================\n");
                System.out.println("Certificate saved to Certificate.txt");
            } catch (java.io.IOException e) {
                System.out.println("Error writing certificate file: " + e.getMessage());
            }
        }

    }

    public void questionLoop(int startingIndex) {
        Scanner u = new Scanner(System.in);
        hintIndex = 0;
        itemIndex = 0;
        boolean gameValid = true;
        while (gameValid && !quit) {
            if (hasItems()) {
                if (attemptQuestionWithItems(u)) {
                    gameValid = false;
                }
            } else {
                if (attemptQuestionNoItems(u)) {

                    gameValid = false;
                }
            }
            if (quit) {
                gameValid = false;
            }
        }
    }

    public boolean attemptQuestionNoItems(Scanner u) {
        Challenge currentChallenge = challenges.get(playerIndex);
        String questionString = currentChallenge.getQuestion();
        String questionAnswer = currentChallenge.getAnswer();
        String questionPost = currentChallenge.getPostQuestion();
        ArrayList<String> hintList = currentChallenge.getHints();

        System.out.println(questionString);
        System.out.println("----------");

        boolean questionCorrect = false;

        // Menu loop so user can try, ask for hints, or quit without leaving the
        // question
        while (!questionCorrect) {
            System.out.println("Press 1. To attempt question");
            System.out.println("Press 2. To get hint");
            System.out.println("Press 4. To save and quit");
            System.out.print("> ");

            String line = u.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid choice (1, 2 or 4).");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\nAttempt question:");
                    String attempt = u.nextLine();
                    if (attempt.equalsIgnoreCase(questionAnswer)) {
                        System.out.println("\nCorrect | +100 points\n");
                        System.out.println(questionPost + "\n________________");

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ignored) {
                        }
                        score += correctPoints;
                        questionCorrect = true;

                        if (currentChallenge.getItems() != null && currentChallenge.getItems().size() > 0) {
                            Item challengeItem = currentChallenge.getItems().get(itemIndex);
                            gameItems.add(challengeItem);
                            itemIndex++;
                        }

                    } else {
                        System.out.println("Incorrect, try again");
                    }
                    break; // prevent fall-through

                case 2:
                    if (hintList.size() > hintIndex) {
                        String hint = hintList.get(hintIndex);
                        if (hint != null) {
                            System.out.println("\n" + hint + " | -25 points" + "\n");
                            score -= hintPenalty;
                        }
                        hintIndex++;

                        session.addHintUsed();
                    } else {
                        System.out.println("\nNo more hints remaining\n");
                    }
                    break;

                case 4:
                    // handle save & quit: adjust to your save implementation
                    // saveGame();
                    System.out.println("Game saved. Exiting question...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    quit = true;
                    return false; // not correct, outer loop can handle overall quit

                default:
                    System.out.println("Invalid choice. Enter 1, 2 or 4.");
                    break;
            }
        }

        return questionCorrect;
    }

    public boolean attemptQuestionWithItems(Scanner u) {

        // instantiating variables 4 logic
        Challenge currentChallenge = challenges.get(playerIndex);
        String questionString = currentChallenge.getQuestion();
        String questionAnswer = currentChallenge.getAnswer();
        String questionPost = currentChallenge.getPostQuestion();
        ArrayList<String> hintList = currentChallenge.getHints();

        boolean questionCorrect = false;

        System.out.println(questionString);
        System.out.println("----------");
        // Menu loop for this single question so user can press 1/2/3 multiple times
        // until they answer or quit
        while (!questionCorrect) {
            System.out.println("Press 1. To attempt question");
            System.out.println("Press 2. To get hint");
            System.out.println("Press 3. To look at items");
            System.out.println("Press 4. To save and quit");
            System.out.print("> ");

            String line = u.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-4).");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\nAttempt question:");
                    String attempt = u.nextLine();
                    if (attempt.equalsIgnoreCase(questionAnswer)) {
                        System.out.println("\nCorrect | +100 points\n");
                        System.out.println(questionPost + "\n________________");
                        score += correctPoints;
                        if (currentChallenge.getItems().size() > 0) {
                            // only add if there are items in the challenge itself
                            Item challengeItem = currentChallenge.getItems().get(itemIndex);
                            gameItems.add(challengeItem);
                            itemIndex++;
                        }
                        questionCorrect = true;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ignored) {
                        }
                    } else {
                        System.out.println("Incorrect, try again");
                    }
                    break;
                case 2:
                    if (hintList.size() > hintIndex) {
                        String hint = hintList.get(hintIndex);
                        if (hint != null) {
                            System.out.println("\n" + hint + " | -25 points" + "\n");
                            score -= hintPenalty;
                        }
                        hintIndex++;
                        session.addHintUsed();
                    } else {
                        System.out.println("\nNo more hints remaining\n");
                    }
                    break;
                case 3:
                    System.out.println("\nHere are your items:\n");
                    if (gameItems.isEmpty()) {
                        System.out.println("You have no items.\n");
                    } else {
                        int i = 0;
                        for (Item item : gameItems) {
                            i++;
                            System.out.println(i + ". " + item.getName());
                        }
                        // Optionally print descriptions for all items (safely)
                        System.out.println("--------------");
                        System.out.println("\nDescriptions:");
                        for (int j = 0; j < gameItems.size(); j++) {
                            Item it = gameItems.get(j);
                            System.out.println((j + 1) + ". " + it.getDescription() + "\n");
                        }
                        Scanner userInput = new Scanner(System.in);
                        System.out.println("\nPress 1. To select and Item\nPress 2. To return to question");
                        int selectInt = u.nextInt();
                        switch (selectInt) {
                            case 1:
                                System.out.println("Enter the number of the item you want to use");
                                int itemSelection = u.nextInt() - 1;
                                u.nextLine();
                                String itemUse = gameItems.get(itemSelection).getUseCase();
                                System.out.println(itemUse);
                                break;
                            case 2:
                                break;
                            default:
                                System.out.println("Invalid choice");
                                break;
                        }

                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                    System.out.println("----------");
                    System.out.println(questionString);
                    System.out.println("----------");
                    break;
                case 4:
                    // implement save/quit behavior here; for now return false so outer loop can
                    // handle quitting
                    // saveGame();
                    System.out.println("Game saved. Exiting...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    quit = true;
                    // Decide: should quitting count as "questionCorrect"? probably not,
                    // so return false or handle game state elsewhere. Here we return false.
                    return false;
                default:
                    System.out.println("Invalid choice. Enter 1-4.");
                    break;
            }
        }

        // when questionCorrect == true we return true to signal the outer loop to
        // advance
        return questionCorrect;
    }

    // unused
    public void puzzleCompleted() {

    }

    public String toString() {
        return this.getTheme();
    }

}
