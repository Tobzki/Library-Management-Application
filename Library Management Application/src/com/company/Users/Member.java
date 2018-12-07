package com.company.Users;

import com.company.Library.Book;
import com.company.Library.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Member extends Account {

    private ArrayList <Transaction> transactions;
    Date today = new Date();

    public Member(String ssn, String name, String address, String telephone, String username, String password) {
        super(ssn, name, address, telephone, username, password);
        transactions = new ArrayList<>();
    }

    /*
     *   TODO: Add member-specific methods such as 'issueBook', 'getTransactionHistory' etc.
     */

    public void addTransaction (Transaction transaction) {
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions () {
        return transactions;
    }

    // Change back to private after debug
    public Transaction searchTransactionById (int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId() == id) {
                return transaction;
            }
        }
        return null; // transaction does not exist
    }

    public boolean renewTransaction (int id) {
        if (searchTransactionById(id) != null) {
            Transaction transaction = searchTransactionById(id);
            Date dueDate = transaction.getDueDate();
            if (dueDate.after(new Date())) {
                Calendar temp = Calendar.getInstance();
                temp.setTime(dueDate);
                temp.add(Calendar.MONTH, 1);
                transaction.setDueDate(temp.getTime());

                return true;
            }
        }

        return false;
    }

    public boolean checkLateTransactions () {

        for (int i = 0; i < transactions.size(); i++) {

            if (today.after(transactions.get(i).getDueDate())) {

                return true;
            }
        }

    return false;}
}
