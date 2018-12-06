package com.company.OptionAction;

import com.company.Library.Book;
import com.company.Library.LibraryLogic;
import com.company.Users.Member;
import com.company.Users.UserLogic;
import com.company.Util;


import java.util.Scanner;

public class MenuHandler {

    private Scanner input = new Scanner(System.in);
    private UserLogic userLogic = new UserLogic();
    private LibraryLogic libraryLogic = new LibraryLogic();

    private Menu activeMenu; // starting point
    private Menu lastMenu; // records what menu was previously visited

    // Custom menus
    private Menu testMenu; // keep this for debugging

    private Option back;
    private Option exit;

    public MenuHandler() {
        back = new Option("Back", this::backAction);
        exit = new Option("Exit", () -> System.exit(0));

        init();
    }

    public MenuHandler(Menu startMenu) {
        activeMenu = startMenu;
        back = new Option("Back", this::backAction);
        exit = new Option("Exit", () -> System.exit(0));

        init();
    }

    private void init() {
        // Options about members
        Option viewMember = new Option("View Member", userLogic::viewMembers);
        Option addMember = new Option("Add Member", () -> {
            String ssn = Util.safeStringInput("SSN");
            String name = Util.safeStringInput("Name");
            String address = Util.safeStringInput("Address");
            String telephone = Util.safeStringInput("Telephone number");
            String username = Util.safeStringInput("Username");
            String password = Util.safeStringInput("Password");

            if (userLogic.addMember(new Member(ssn, name, address, telephone, username, password))) {
                System.out.println("Member was added.");
            } else {
                System.out.println("Something went wrong, maybe the user is already in the system?");
            }
        });
        Option editMember = new Option("Edit Member", () -> {
            String ssn = Util.safeStringInput("SSN of user to edit");
            if (userLogic.editUser(ssn)) {
                System.out.println("User was edited.");
            } else {
                System.out.println("No user was found.");
            }
        });
        Option removeMember = new Option("Remove Member", () -> {String ssn = Util.safeStringInput("SSN of member to remove");
            if (userLogic.removeUser(ssn)) {
                System.out.println("Successfully removed member.");
            } else {
                System.out.println("No member was removed.");
            }
        });
        Option login = new Option("Log in", () -> {
            String username = Util.safeStringInput("Username");
            String password = Util.safeStringInput("Password");

            if (userLogic.authenticate(username, password)) {
                System.out.println("Successfully logged in.");
            } else {
                System.out.println("Username and/or password was incorrect.");
            }
        });

        // Options about loaning
        Option issueBook = new Option("Loan", () -> {
            String isbn = Util.safeStringInput("ISBN of book");
            Book book = libraryLogic.getBook(isbn);
            if (book != null) {
                int confirm = Util.safeIntInput("Is '" + book.getTitle() + "' the correct book?\n1) Yes 2) No", 2);
                if (confirm == 1) {
                    userLogic.makeTransaction(book);
                }
            } else {
                System.out.println("No book was found with that ISBN.");
            }
        });
        Option viewTransactions = new Option("View Transactions", userLogic::viewTransactions);

        // Options about books
        Option editBook = new Option ("Edit book", () -> {
            String isbn = Util.safeStringInput("ISBN number of book to edit");
            if (!libraryLogic.editBook(isbn)) {
                System.out.println("Sorry, no book was found.");
            } else {
                System.out.println("\n\nYour book has now been edited. Thank you!\n");
            }
        });
        Option addBookInformation = new Option("Add Book Information", () -> {
            if (userLogic.authorize() == UserLogic.USER_STATE.LIBRARIAN) {
                String isbn = Util.safeStringInput("ISBN");
                String title = Util.safeStringInput("Name");
                String publisher = Util.safeStringInput("Publisher");
                String language = Util.safeStringInput("Language");
                int numberOfPages = Util.safeIntInput("Number of pages");
                String category = Util.safeStringInput("Category");

                int numberOfAuthors = Util.safeIntInput("Number of authors");
                String[] authors = new String[numberOfAuthors];
                for (int i = 0; i < authors.length; i++) {
                    authors[i] = Util.safeStringInput("Name of author #" + (i + 1));
                }

                if (libraryLogic.addBook(new Book(isbn, title, numberOfPages, language, publisher, category,authors))) {
                    System.out.println("Book was added.");
                } else {
                    System.out.println("Something went wrong, maybe the book is already in the system?");
                }
            } else {
                System.out.println("You are not authorized to do this.");
            }
        });
        Option searchBook = new Option("Search Book", () -> {
            String query = Util.safeStringInput("Search query");

            for (Book book : libraryLogic.searchBook(query)) {
                System.out.println(book);
            }
        });
        Option removeBook = new Option("Remove Book", () -> {
            String isbn = Util.safeStringInput("ISBN of book to remove");
            if (libraryLogic.removeBook(isbn)) {
                System.out.println("Book was successfully removed.");
            } else {
                System.out.println("No book was removed.");
            }
        });

        // DEBUG: Test menu for debugging features
        testMenu = new Menu(addMember, viewMember, removeMember, addBookInformation, searchBook, removeBook, issueBook, viewTransactions,login);
        setActiveMenu(testMenu);
    }

    private void backAction() {
        setActiveMenu(lastMenu);
    }

    /**
     * Set the menu to be displayed after action has ran.
     *
     * @param startMenu The menu to display at the end of current action.
     */
    public void setActiveMenu(Menu startMenu) {
        if (activeMenu != null) {
            lastMenu = activeMenu;
        }
        activeMenu = startMenu;
    }

    /**
     * Start the menu system using a do-while loop, this
     * loop should be exited via the exit option or a
     * similar action.
     *
     * @throws Exception If no start menu has been set, this exception is thrown.
     *                   Use method setActiveMenu to properly set the start menu.
     */
    public void runMenu() throws Exception {
        if (activeMenu != null) {
            do { // will terminate from an action
                activeMenu.runMenu();
            } while (true);
        } else {
            throw new Exception("No starting menu was set!");
        }
    }
}
