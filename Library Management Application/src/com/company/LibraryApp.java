package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryApp {

    public static void main(String[] args) {

    }

    public static int safeIntInput (String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt + " >> ");
        int result = -2;
        do {
            if (result == -1) {
                System.out.print(prompt + " >> ");
            }
            try {
                result = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid input.");
                input.nextLine();
                result = -1;
            }
        } while(result == -1);

        return result;
    }
}