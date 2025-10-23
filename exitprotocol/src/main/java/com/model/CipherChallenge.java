package com.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import javafx.scene.image.Image;

public class CipherChallenge extends Challenge {
    private int shift;
    private String correctAnswer;
    private String encryptedAnswer;

    public CipherChallenge(String correctAnswer, int shift, ArrayList<String> hints, Image clue) {
        super(hints, clue);
        this.shift = shift;
        this.correctAnswer = correctAnswer;
        this.encryptedAnswer = getEncryptedAnswer();
    }

    public boolean isCorrect(String answer) {
        return answer.equals(this.correctAnswer);
    }

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

