package com.company.Users;

import com.company.Library.Book;
import com.company.Library.Transaction;
import com.company.Util;

import java.util.ArrayList;

public class UserLogic {

    public enum USER_STATE {MEMBER, LIBRARIAN, NOT_LOGGED_IN}

    private ArrayList<Account> users;
    private Account loggedIn;

    public UserLogic() {
        users = new ArrayList<>();
        users.add(new Member("971217", "Rasmus Nilsson", "Storgatan", "079349", "rani", "dogs"));
        users.add(new Librarian("1234", "Rasmus Nilsson", "Storgatan", "079349", "rani_lib", "dogs"));
    }

    public boolean editUser(String ssn) {
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

            return true;
        } else {
            return false;
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

    public boolean addMember(Member member) {
        for (Account mem : users) {
            if (mem.getSsn().equals(member.getSsn()) || mem.getUsername().equals(member.getUsername())) {
                return false;
            }
        }

        System.out.println(member);
        int validation = Util.safeIntInput("Is this information correct?\n1) Yes 2) No\n", 2);
        if (validation == 1) {
            users.add(member);
            return true;
        }

        return false;
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

    public boolean authenticate (String username, String password) {
        for (Account user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedIn = user;
                return true;
            }
        }

        return false;
    }

    public USER_STATE authorize () {
        if (loggedIn instanceof Librarian) {
            return USER_STATE.LIBRARIAN;
        } else if (loggedIn instanceof Member) {
            return USER_STATE.MEMBER;
        } else {
            return USER_STATE.NOT_LOGGED_IN;
        }
    }

    public void makeTransaction (Book book) {
        if (authorize() == USER_STATE.MEMBER) {
            Transaction transaction = new Transaction(book.getIsbn());
            ((Member) loggedIn).addTransaction(transaction);
        } else {
            System.out.println("No user is logged in.");
        }
    }

    public void viewTransactions () {
        if (authorize() == USER_STATE.MEMBER) {
            if (((Member) loggedIn).getTransactions().size() > 0) {
                for (Transaction transaction : ((Member) loggedIn).getTransactions()) {
                    System.out.println(transaction);
                }
            } else {
                System.out.println("No transactions to show.");
            }
        } else {
            System.out.println("No user is logged in.");
        }
    }

    public Account getLoggedIn () {
        return loggedIn;
    }

    public void viewListMembers () {

        if (authorize() == USER_STATE.LIBRARIAN) {

            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + 1 + ". " + users.get(i));}
        } else {
            System.out.println("\nYou are not permitted to do this action.\n");
        }
    }

}
