package com.company.Users;

import com.company.Util;

import java.util.ArrayList;

public class UserLogic {

    private ArrayList<Account> users;
    private Account loggedIn;

    public void editUser (String ssn) {
        if (getUser(ssn) != null) {
            Member memberToEdit = (Member)getUser(ssn);
            String tempSsn = Util.safeStringInput("SSN", memberToEdit.getSsn());
        }
    }

    public Account getUser (String ssn) {
        for (Account user : users) {
            if (ssn.equals(user.getSsn())) {
                return user;
            }
        }

        return null; // didn't find any members, don't return anything.
    }

    public void addMember (Member member) {
        users.add(member);
    }
}
