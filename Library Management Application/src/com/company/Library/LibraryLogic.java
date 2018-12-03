package com.company.Library;

import com.company.OptionAction.MenuHandler;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryLogic {
    Scanner input = new Scanner(System.in);

    private ArrayList<Book> inventory;

    public boolean removeBooks() {
        String isbn;

        boolean successful = false;
            do {
                System.out.println("Please enter the ISBN number of the book you wish to delete");
                isbn = input.next();
                for (int i = 0; i < inventory.size(); i++) {
                    if (isbn == inventory.get(i).getisbn()) {
                        System.out.println("The Book " + inventory.get(i) + " was removed");
                        inventory.remove(i);
                        successful = true;
                        break;
                    }
                }
                if (!successful) {
                    System.out.println("Book ID " + isbn + " does not exist");
                }

            } while (successful=true); {
            System.out.println("Worked");

        }
        return  successful;}
        
}