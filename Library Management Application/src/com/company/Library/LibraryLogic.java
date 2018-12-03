package com.company.Library;

import java.util.ArrayList;
import java.util.Arrays;

public class LibraryLogic {

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
            String  subIsbn = "",
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

}
