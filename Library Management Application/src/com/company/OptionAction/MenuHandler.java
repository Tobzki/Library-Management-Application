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
        Runnable addMemberAction = () -> {
            int answer;

            do {
                String ssn = Util.safeStringInput("SSN");
                String name = Util.safeStringInput("Name");
                String address = Util.safeStringInput("Address");
                String telePhoneNumber = Util.safeStringInput("Telephonenumber");
                String userName = Util.safeStringInput("Username");
                String password = Util.safeStringInput("Password");

                System.out.print("\n- - - This is your information below - - -");
                System.out.printf("\n\nSSN: %s%nName: %s%nAddress: %s%nTelephonenumber: %s%nUsername: %s%nPassword: %s%n", ssn, name, address, telePhoneNumber, userName, password);
                answer = Util.safeIntInput("\n\nIs this information valid? Press 1 for Yes and 2 for No.");
                if (answer == 1) {

                    System.out.println("You are now registered in our system '" + name + "'. Thanks for your contribution!");
                    Member member = new Member(ssn, name, address, telePhoneNumber, userName, password);
                    userLogic.addMember(member);
                } else if (answer == 2) {
                    System.out.println("\n\nPlease be more accurate with your information.\n\n");
                }
            } while (answer != 1);
        }; // Option addMember
        Runnable editMemberAction = () -> {
            String ssn = Util.safeStringInput("SSN of user to edit");
            if (userLogic.editUser(ssn) == null) {
                System.out.println("Sorry, no user was found.");
            }
        }; // Option edit a member based on SSN.

        Runnable addBookInformationAction = () -> {

            int answer;

            do {
                String isbn = Util.safeStringInput("ISBN");
                String name = Util.safeStringInput("Name");
                String publisher = Util.safeStringInput("Publisher");
                String language = Util.safeStringInput("Language");
                int numberOfPages = Util.safeIntInput("Number of pages");

                int numberOfAuthors = Util.safeIntInput("Number of authors");
                String[] authors = new String[numberOfAuthors];
                for (int i = 0; i < authors.length; i++) {
                    authors[i] = Util.safeStringInput("Name of author #" + (i + 1));
                }

                System.out.print("\n- - - This is your information below - - -");
                System.out.printf("\n\nISBN: %s%nName: %s%nPublisher: %s%nLanguage: %s%nNumber of pages: %s%n", isbn, name, publisher, language, numberOfPages);
                answer = Util.safeIntInput("\n\nIs this information valid? Press 1 for Yes and 2 for No.");
                if (answer == 1) {

                    System.out.println("\n\nThe book '" + name + "' is now added into the library. Thank you!\n\n");
                    Book book = new Book(isbn, name, numberOfPages, language, publisher, authors);
                    libraryLogic.addBook(book);
                } else if (answer == 2) {
                    System.out.println("\n\nPlease be more accurate with your information. \n\n");
                }

            } while (answer != 1);
        }; // Option add book
        Runnable searchBookAction = () -> {
            String query = Util.safeStringInput("Search query");

            for (Book book : libraryLogic.searchBook(query)) {
                System.out.println(book);
            }
        }; // Option search book
        Runnable removeBookAction = () -> {
            String isbn = Util.safeStringInput("ISBN of book to remove");
            if (libraryLogic.removeBook(isbn)) {
                System.out.println("Book was successfully removed.");
            } else {
                System.out.println("No book was removed.");
            }
        };

        Option viewMember = new Option("View Member", userLogic::viewMembers);
        Option addMember = new Option("Add Member", addMemberAction);
        Option editMember = new Option("Edit Member", editMemberAction);

        Option addBookInformation = new Option("Add Book Information", addBookInformationAction);
        Option searchBook = new Option("Search Book", searchBookAction);
        Option removeBook = new Option("Remove Book", removeBookAction);

        testMenu = new Menu(addMember, viewMember, editMember, addBookInformation, searchBook, removeBook);
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
