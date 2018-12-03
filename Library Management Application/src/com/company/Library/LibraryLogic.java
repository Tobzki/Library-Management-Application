package com.company.Library;

import com.company.OptionAction.MenuHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LibraryLogic {
    Scanner input = new Scanner(System.in);

    private ArrayList<Book> inventory;

    public LibraryLogic(Book... books) {
        inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(books));
    }

    public LibraryLogic() {
        inventory = new ArrayList<>();
    }

    /**
     * Takes a query of n symbols and searches for any books with any attributes
     * that correspond with the query.
     *
     * @param query Search query entered by the user.
     * @return A list of books with attributes matching that query.
     */
    public ArrayList<Book> searchBook(String query) {
        ArrayList<Book> result = new ArrayList<>();
        if (query.equals("")) { // Just throw back an empty arraylist since they didn't enter a query
            return result;
        }

        query = query.toLowerCase();

        for (int i = 0; i < inventory.size(); i++) {
            Book current = inventory.get(i);
            String subIsbn = "",
                    subTitle = "",
                    subPublisher = "";

            if (current.getIsbn().length() >= query.length()) {
                subIsbn = current.getIsbn().substring(0, query.length()).toLowerCase();
            }
            if (current.getTitle().length() >= query.length()) {
                subTitle = current.getTitle().substring(0, query.length()).toLowerCase();
            }
            if (current.getPublisher().length() >= query.length()) {
                subPublisher = current.getPublisher().substring(0, query.length());
            }

            String[] subAuthors = new String[current.getAuthors().length];
            boolean authorMatch = false;
            for (int j = 0; j < current.getAuthors().length; j++) {
                if (current.getAuthors()[j].length() >= query.length()) {
                    subAuthors[j] = current.getAuthors()[j].substring(0, query.length()).toLowerCase();
                    if (subAuthors[j].equals(query)) {
                        authorMatch = true;
                        break;
                    }
                }
            }

            if (subIsbn.equals(query) || subTitle.equals(query) || subPublisher.equals(query) || authorMatch) {
                result.add(current);
            }
        }

        return result;
    }

    public void addBook(Book book) {
        inventory.add(book);
    }


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

        } while (successful = true);
        {
            System.out.println("Worked");

        }
        return successful;
    }
}

