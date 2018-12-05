package com.company.Library;

import java.util.Date;

public class Transaction {

    public static int idCounter = 0;

    private int transactionId;
    private String bookId;
    private Date dateOfIssue, dueDate, returnDate;
    private double lateFee;

    public Transaction(String bookId, Date dateOfIssue, Date dueDate) {
        transactionId = ++idCounter;

        this.bookId = bookId;
        this.dateOfIssue = dateOfIssue;
        this.dueDate = dueDate;
    }

    @Override
    public String toString () {
        String result;
        result = "***************************\n";
        result += "Transaction id: " + transactionId + "\n";
        result += "Date of load: " + dateOfIssue.toString() + "\n";
        result += "Book loaned: " + bookId + "\n";
        result += "Due date: " + dueDate.toString() + "\n";
        result += "***************************\n";
        return result;
    }
}
