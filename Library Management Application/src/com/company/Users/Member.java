package com.company.Users;

import com.company.Library.Transaction;

import java.util.ArrayList;

public class Member extends Account {

    private ArrayList<Transaction> transactions;

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
}
