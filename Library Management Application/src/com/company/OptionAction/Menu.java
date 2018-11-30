package com.company.OptionAction;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private Option[] options;
    private Scanner input = new Scanner(System.in);

    public Menu(Option... options) {
        this.options = options;
    }

    private void printMenu() {
        System.out.println("--- Menu ---");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ") " + options[i]);
        }
    }

    /**
     * Runs 'this' menu object and handles command input.
     * If command input is invalid, the user gets prompted to try again.
     */
    public void runMenu() {
        printMenu();

        System.out.print("Command >> ");
        int command;
        try {
            command = input.nextInt();
        } catch (InputMismatchException e) {
            command = -1;
        }

        input.nextLine(); // clear buffer

        command--; // make the choice represent an element in options array
        if (command >= 0 && command < options.length) {
            options[command].doAction();
        } else {
            System.out.println("Enter a valid command, please.");
        }
    }

}
