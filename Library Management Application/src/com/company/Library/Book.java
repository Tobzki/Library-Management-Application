package com.company.Library;

public class Book {

    private String isbn;
    private String title;
    private String[] authors;
    private int numberOfPages;
    private String language;
    private String publisher;

    public Book (String isbn, String title, int numberOfPages, String language, String publisher, String... authors) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.numberOfPages = numberOfPages;
        this.language = language;
        this.publisher = publisher;
    }

    public String getisbn() {
        return isbn;
    }
}
