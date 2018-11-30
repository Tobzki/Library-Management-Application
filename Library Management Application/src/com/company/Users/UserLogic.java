package com.company.Users;

import java.util.ArrayList;

public class UserLogic {

    private ArrayList<Account> users;
    private Account loggedIn;

    public UserLogic () {
        users = new ArrayList<>();
    }

    public Account getUser (String ssn) {
        for (Account user : users) {
            if (ssn.equals(user.getSsn())) {
                return user;
            }
        }

        return null; // didn't find any members, don't return anything.
    }

    public void viewMembers() {
        String result;
        result = "***************************\n";
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + 1 + ". " + users.get(i));
        }
        result += "***************************\n";

    }

    public void addMember (Member member) {
        users.add(member);
    }
}
