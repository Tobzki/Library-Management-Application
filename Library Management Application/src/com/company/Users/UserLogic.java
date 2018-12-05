package com.company.Users;

import com.company.Library.Transaction;
import com.company.Util;

import java.util.ArrayList;

public class UserLogic {

    private ArrayList<Account> users;
    private Account loggedIn;

    public UserLogic() {
        users = new ArrayList<>();
    }

    public Account editUser(String ssn) {
        if (getUser(ssn) != null) {
            Member memberToEdit = (Member) getUser(ssn);
            String tmpName = Util.safeStringInput("Name", memberToEdit.getName());
            memberToEdit.setName(tmpName);

            String tmpTel = Util.safeStringInput("Telephone", memberToEdit.getTelephone());
            memberToEdit.setTelephone(tmpTel);

            String tmpAddress = Util.safeStringInput("Address", memberToEdit.getAddress());
            memberToEdit.setAddress(tmpAddress);

            String tmpPassword = Util.safeStringInput("Password", memberToEdit.getPassword());
            memberToEdit.setPassword(tmpPassword);

            return memberToEdit;
        } else {
            return getUser(ssn); // should just be null, since there's no user with that ssn
        }
    }

    public Account getUser(String ssn) {
        for (Account user : users) {
            if (ssn.equals(user.getSsn())) {
                return user;
            }
        }

        return null; // didn't find any members, don't return anything.
    }

    public void viewMembers() {
        for (int i = 0; i < users.size(); i++) {
            System.out.println(i + 1 + ". " + users.get(i));
        }

    }

    public boolean addMember(Member member) {
        for (Account mem : users) {
            if (mem.getSsn().equals(member.getSsn())) {
                return false;
            }
        }

        users.add(member);
        return true;
    }

    public boolean removeUser(String ssn) {
        boolean successful = false;
        for (int i = 0; i < users.size(); i++) {
            if (ssn.equals(users.get(i).getSsn())) {
                // Make sure user wants to delete this book by displaying title
                String prompt = "Do you really want to delete '" + users.get(i).getName() + "'?\n1) Yes 2) No\n";
                int answer = Util.safeIntInput(prompt, 2);

                if (answer == 1) {
                    users.remove(i);
                    successful = true;
                } else {
                    successful = true;
                }
                break;
            }
        }
        return successful;
    }

    public void makeTransaction (Transaction transaction) {
        if (loggedIn instanceof Member) {
            ((Member) loggedIn).addTransaction(transaction);
        }
    }
}
