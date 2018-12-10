package com.company.Library;

public class Book {

    private String isbn;
    private String title;
    private String[] authors;
    private int numberOfPages;
    private String language;
    private String publisher;
    private String category;
    private boolean available;


    public Book(String isbn, String title, int numberOfPages, String language, String publisher, String category,
                String... authors) {

        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.numberOfPages = numberOfPages;
        this.language = language;
        this.publisher = publisher;
        this.category = category;
        available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCategory() {
        return category;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    public String toString() {
        StringBuilder authorsString = new StringBuilder();
        String result;
        result = "***************************\n";
        result += title + "\n";
        for (int i = 0; i < authors.length; i++) {
            if (i == authors.length - 1) {
                authorsString.append(authors[i]);
            } else {
                authorsString.append(authors[i]);
                authorsString.append(", ");
            }
        }
        result += authorsString.toString() + "\n";
        result += "ISBN: " + isbn + "\n";
        result += "Pages: " + numberOfPages + "\n";
        result += "Publisher: " + publisher + "\n";
        result += "Category: " + category + "\n";
        result += "***************************\n";
        return result;
    }

}
