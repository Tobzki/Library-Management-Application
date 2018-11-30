package com.company.Users;

import java.util.ArrayList;

public class UserLogic {

    private ArrayList<Account> users;
    private Account loggedIn;

    public void addMember (Member member) {

        users.add(member);

    }

}
