package com.company.Library;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.Assert;

public class LibraryLogicTest {

    @Test
    public void testBookSearch () {
        LibraryLogic libraryLogic = new LibraryLogic();
        ArrayList<Book> expectedResult = libraryLogic.getInventory();

        ArrayList<Book> result = libraryLogic.searchBook("123");
        Assert.assertEquals(expectedResult, result);
    }

}
