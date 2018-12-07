package com.company.Library;

import java.util.Calendar;
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

    public Transaction (String bookId) {
        transactionId = ++idCounter;
        this.bookId = bookId;
        dateOfIssue = new Date();

        Calendar temp = Calendar.getInstance();
        temp.setTime(dateOfIssue);
        temp.add(Calendar.MONTH, 1);
        this.dueDate = temp.getTime();
    }



    public int getTransactionId () {
        return transactionId;
    }

    public Date getDueDate () {
        return dueDate;
    }

    public void setDueDate (Date dueDate) {
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
