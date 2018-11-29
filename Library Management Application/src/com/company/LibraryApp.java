package com.company;

import com.company.Library.Book;
import com.company.Library.LibraryLogic;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryApp {

    public static void main(String[] args) {
        LibraryLogic logic = new LibraryLogic();
        Scanner input = new Scanner(System.in);

        System.out.println("Search query:");
        String query = input.nextLine();
        ArrayList<Book> searchResult = logic.searchBook(query);
        for (int i = 0; i < searchResult.size(); i++) {
            System.out.println(searchResult.get(i));
        }
    }
}
