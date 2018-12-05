package com.company.Library;

import com.company.Util;

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

    public boolean removeBook(String isbn) {
        boolean successful = false;
        for (int i = 0; i < inventory.size(); i++) {
            if (isbn.equals(inventory.get(i).getisbn())) {
                // Make sure user wants to delete this book by displaying title
                String prompt = "Do you really want to delete '" + inventory.get(i).getTitle() + "'?\n1) Yes 2) No\n";
                int answer = Util.safeIntInput(prompt, 2);

                if (answer == 1) {
                    inventory.remove(i);
                    successful = true;
                } else {
                    successful = true;
                }
                break;
            }
        }
        return successful;
    }

    public Book getBook (String isbn) {
        for (Book book : inventory) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }

        return null; // no book was found
    }
}

