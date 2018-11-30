package com.company.Users;

import java.util.ArrayList;

public class UserLogic {

    private ArrayList<Account> users;
    private Account loggedIn;

    public void addMember(Member member) {

        users.add(member);

    }

    public void viewMembers() {
        String result;
        result = "***************************\n";
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + 1 + ". " + users.get(i));
        }
        result += "***************************\n";

    }
}