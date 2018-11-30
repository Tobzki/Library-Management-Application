package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {

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
                if (result <= 0) {
                    System.out.println("Enter a valid input.");
                    result = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid input.");
                input.nextLine();
                result = -1;
            }
        } while(result == -1);

        return result;
    }

    public static int safeIntInput (String prompt, int maxValue) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt + " >> ");
        int result = -2;
        do {
            if (result == -1) {
                System.out.print(prompt + " >> ");
            }
            try {
                result = input.nextInt();
                if (result <= 0 || result > maxValue) {
                    System.out.println("Enter a valid input.");
                    result = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid input.");
                input.nextLine();
                result = -1;
            }
        } while(result == -1);

        return result;
    }

    public static String safeStringInput (String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.print(prompt + " >> ");
        String result = "";
        boolean firstIteration = true;
        do {
            if (result.equals(" ") || (result.equals("") && !firstIteration)) {
                System.out.println("Enter a valid input.");
                System.out.print(prompt + " >> ");
            }
            result = input.nextLine();
            firstIteration = false;
        } while (result.equals("") || result.equals(" "));

        return result;
    }

}
