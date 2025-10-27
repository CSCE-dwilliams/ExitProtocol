package com.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import javafx.scene.image.Image;

public class CipherChallenge{
    private int shift;
    /** The correct answer for the challenge (unencrypted). */
    private String correctAnswer;
    /** The encrytpted version of the correct answer. */
    private String encryptedAnswer;

    /**
     * Constructs a new {@code CipherChallenge}.
     * @param correctAnswer the correct answer will be encrypted
     * @param shift the number of positions to shift letters in the cipher
     * @param hints a list of hints for the challenge
     * @param clue an optional image clue for the challenge
     */
    public CipherChallenge(String correctAnswer, int shift, ArrayList<String> hints, Image clue) {
        this.shift = shift;
        this.correctAnswer = correctAnswer;
        this.encryptedAnswer = getEncryptedAnswer();
    }

    /**
     * Checks if the provided answer matches with the correct answer.
     * @param answer the answer to verify
     * @return {@code true} if the answer is correct, {@code false} otherwise
     */
    public boolean isCorrect(String answer) {
        return answer.equals(this.correctAnswer);
    }

    /**
     * Encrypts the correct answer using a cipher based on the shift value.
     * @return the encrypted answer as a {@code String}
     */
    private String getEncryptedAnswer() {
        String encryptedAnswer = "";

        for (int i = 0; i < correctAnswer.length(); i++) {
            char currentChar = correctAnswer.charAt(i);
            if (Character.isLetter(currentChar)) {
                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
                char shiftedChar = (char) ((currentChar - base + shift) % 26 + base);
                encryptedAnswer += shiftedChar;
            } else {
                encryptedAnswer += currentChar;
            }
        }

        return encryptedAnswer;
    }

    /**
     * Returns a string representation of the challenge, showing the encrypted phrase.
     * @return the encrypted phrase as a {@code String}
     */
    public String toString() {
        return "Reverse-engineer this encrypted phrase:\n\n" + this.encryptedAnswer;
    }

    public void test() {
        System.out.println(this);
    }

    public static void main(String[] args) {
        CipherChallenge chal = new CipherChallenge("visionary", 3, new ArrayList<String>(), null);
        System.out.println(chal);

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your answer:");

        String answer = sc.nextLine();

        if (!chal.isCorrect(answer)) {
            System.out.println("Wrong answer~!");
        } else {
            System.out.println("Correct answer!");
        }
    }
}
