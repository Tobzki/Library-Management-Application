package com.company.Users;

import com.company.FileIO;
import com.company.Library.Book;
import com.company.Library.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Member extends Account {

    private ArrayList<Transaction> transactions;
    private double fee;

    public Member(String ssn, String name, String address, String telephone, String username, String password) {
        super(ssn, name, address, telephone, username, password);
        fee = 0;
        transactions = new ArrayList<>();
        FileIO.create(username); // create a history file for user
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean returnTransaction(int id) {
        Transaction transaction = searchTransactionById(id);
        if (transaction != null) {
            double tempFee = 0.0;
            Date returnDate = new Date();
            if (transaction.getDueDate().before(returnDate)) { // Book is late
                long diff = transaction.getDueDate().getTime() - returnDate.getTime();
                tempFee = 10.0 * Math.abs(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                fee += tempFee;
            }
            ArrayList<String> history = new ArrayList<>();
            history.add("Transaction");
            history.add("Book: " + transaction.getBookId());
            history.add("Was due: " + transaction.getDueDate() + ", returned: " + returnDate);
            history.add("Fee: " + tempFee);
            FileIO.out(getUsername(), history);
            transactions.remove(transaction);

            return true;
        }

        return false;
    }

    // Change back to private after debug
    public Transaction searchTransactionById(int id) {
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId() == id) {
                return transaction;
            }
        }
        return null; // transaction does not exist
    }

    public boolean renewTransaction(int id) {
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

    public boolean checkLateTransactions() {
        Date today = new Date();
        for (int i = 0; i < transactions.size(); i++) {
            if (today.after(transactions.get(i).getDueDate())) {
                return true;
            }
        }
        return false;
    }
}
