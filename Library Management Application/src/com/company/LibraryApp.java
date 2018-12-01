package com.company;

import com.company.OptionAction.MenuHandler;

public class LibraryApp {

    public static void main(String[] args) {
        MenuHandler menuHandler = new MenuHandler();
        try {
            menuHandler.runMenu();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}