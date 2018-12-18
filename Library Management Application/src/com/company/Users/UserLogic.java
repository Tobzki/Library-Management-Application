package com.company.Users;

import com.company.Library.Book;
import com.company.Library.Transaction;
import com.company.Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserLogic {

    public enum USER_STATE {MEMBER, LIBRARIAN, NOT_LOGGED_IN}

    private ArrayList<Account> users;
    private Account loggedIn;

    public UserLogic() {
        users = new ArrayList<>();
        users.add(new Member("971217", "Rasmus Nilsson", "Storgatan", "079349", "rani", "dogs"));
        users.add(new Librarian("1234", "Rasmus Nilsson", "Storgatan", "079349", "rani_lib", "dogs"));
        users.add(new Librarian("1253", "Tobias Andersson", "Ystadsvägen", "43434", "tobski_lib", "dogs"));
        ((Member)users.get(0)).addTransaction(new Transaction("978-1"));
        Date date = new Date();
        Calendar temp = Calendar.getInstance();
        temp.setTime(date);
        temp.roll(Calendar.MONTH, -1);
        ((Member)users.get(0)).searchTransactionById(1).setDueDate(temp.getTime());

    }

    public boolean editUser(String ssn) {
        Account memberToEdit = getUser(ssn);
        if (getUser(ssn) != null && memberToEdit instanceof Member) {
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

    public boolean authenticate(String username, String password) {
        for (Account user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedIn = user;
                return true;
            }
        }

        return false;
    }

    public USER_STATE authorize() {
        if (loggedIn instanceof Librarian) {
            return USER_STATE.LIBRARIAN;
        } else if (loggedIn instanceof Member) {
            return USER_STATE.MEMBER;
        } else {
            return USER_STATE.NOT_LOGGED_IN;
        }
    }

    public boolean makeTransaction(Book book) {
        if (authorize() == USER_STATE.MEMBER && book.isAvailable()) {
            Transaction transaction = new Transaction(book.getIsbn());
            ((Member) loggedIn).addTransaction(transaction);
            book.setAvailable(false);

            return true;
        }
        return false; // either user is not logged in or book is not available
    }

    public boolean returnBook(int transactionId) {
        if (authorize() == USER_STATE.MEMBER && ((Member) loggedIn).returnTransaction(transactionId)) {
            return true;
        } else {
            return false;
        }
    }

    public String getTransactionBookId(int transactionId) {
        if (authorize() == USER_STATE.MEMBER) {
            Transaction tmp = ((Member) loggedIn).searchTransactionById(transactionId);
            if (tmp != null) {
                return tmp.getBookId();
            } else {
                return null;
            }
        }
        return null;
    }

    public void viewTransactions() {
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

    public Account getLoggedIn() {
        return loggedIn;
    }

    public void viewListMembers() {

        if (authorize() == USER_STATE.LIBRARIAN) {
            for (int i = 0; i < users.size(); i++) {
                System.out.println(i + 1 + ". " + users.get(i));
            }
        } else {
            System.out.println("\nYou are not permitted to do this action.\n");
        }
    }

    public void printMembersAfterDue() {
        Date today = new Date();
        if (authorize() == USER_STATE.LIBRARIAN) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i) instanceof Member) {
                    if (((Member) users.get(i)).checkLateTransactions()) {
                        ArrayList<Transaction> feeAmount = ((Member) users.get(i)).getTransactions();
                        for (int j = 0; j < feeAmount.size(); j++) {
                            if (today.after(feeAmount.get(j).getDueDate())) {
                                System.out.println("The book " + feeAmount.get(j).getBookId() + "is over due, the current penalty fee is " + feeAmount.get(j).getPenaltyfee() + "SEK");
                            }
                        }
                        System.out.println(i + 1 + ". " + users.get(i));
                    }
                }
            }
        } else {
            System.out.println("\n\nThere are no members to print.\n\n");
        }
    }

    public void viewMembersTransactionHistory(String name) {

        String fileName = FileSystems.getDefault().getPath("Histories/" + name + ".txt").toString();
        String line;

        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }

    public void logOut () {

        if (authorize() != USER_STATE.NOT_LOGGED_IN) {

            loggedIn = null;
        }
    }

    public ArrayList<Account> getUsers() {
        return users;
    }

}
