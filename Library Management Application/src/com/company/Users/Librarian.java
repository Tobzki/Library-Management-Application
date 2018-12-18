package com.company.Users;

import java.util.ArrayList;

public class Librarian extends Account {

    private static ArrayList<String> requests = new ArrayList<>(); // initiate list here since its' static

    public Librarian(String ssn, String name, String address, String telephone, String username, String password) {
        super(ssn, name, address, telephone, username, password);
    }

    public ArrayList<String> getRequests () {
        return requests;
    }

    public static void addRequest (String title) {
        requests.add(title);
    }

    public void removeRequest (String title) {
        // find all matches of particular book and remove them
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).toLowerCase().equals(title.toLowerCase())) {
                requests.remove(i);
            }
        }
    }

    public static boolean requestExists (String title) {
        for (String s : requests) {
            if (s.toLowerCase().equals(title.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public void printRequests () {
        for (int i = 0; i < requests.size(); i++) {
            System.out.println((i + 1) + ". " + requests.get(i));
        }
        System.out.println();
    }
}
