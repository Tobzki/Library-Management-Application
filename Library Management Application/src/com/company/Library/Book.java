package com.company.Library;

public class Book {

    private String isbn;
    private String title;
    private String[] authors;
    private int numberOfPages;
    private String language;
    private String publisher;

    public Book(String isbn, String title, int numberOfPages, String language, String publisher, String... authors) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.numberOfPages = numberOfPages;
        this.language = language;
        this.publisher = publisher;
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


    public String getisbn() {
        return isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getLanguage() {
        return language;
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


    @Override
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
        result += isbn + "\n";
        result += "***************************\n";
        return result;
    }

}
