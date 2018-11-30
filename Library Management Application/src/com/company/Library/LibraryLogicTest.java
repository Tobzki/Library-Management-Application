package com.company.Library;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.Assert;

public class LibraryLogicTest {

    @Test
    public void testBookSearchISBN () {
        LibraryLogic libraryLogic = new LibraryLogic();
        ArrayList<Book> expectedResult = libraryLogic.getInventory();

        ArrayList<Book> result = libraryLogic.searchBook("123");
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testBookSearchNOINPUT () {
        LibraryLogic libraryLogic = new LibraryLogic();
        ArrayList<Book> expectedResult = new ArrayList<>();

        ArrayList<Book> result = libraryLogic.searchBook("");
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testBookSearchONLYSPACE () {
        LibraryLogic libraryLogic = new LibraryLogic();
        ArrayList<Book> expectedResult = new ArrayList<>();

        ArrayList<Book> result = libraryLogic.searchBook(" ");
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testBookSearchNOMATCH () {
        LibraryLogic libraryLogic = new LibraryLogic();
        ArrayList<Book> expectedResult = new ArrayList<>();

        ArrayList<Book> result = libraryLogic.searchBook("13");
        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void testBookSearchMATCH () {
        LibraryLogic libraryLogic = new LibraryLogic();
        ArrayList<Book> expectedResult = libraryLogic.getBook(0);

        ArrayList<Book> result = libraryLogic.searchBook("Paulo"); // Author
        Assert.assertEquals(expectedResult, result);
    }

}
