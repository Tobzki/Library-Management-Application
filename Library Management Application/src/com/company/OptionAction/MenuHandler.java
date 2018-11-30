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
    private Menu debugMemberMenu;

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

            String ssn;
            String name;
            String address;
            String telePhoneNumber;
            String userName, password;
            int answer;

            do {
                ssn = Util.safeStringInput("SSN");
                name = Util.safeStringInput("Name");
                address = Util.safeStringInput("Address");
                telePhoneNumber = Util.safeStringInput("Telephonenumber");
                userName = Util.safeStringInput("Username");
                password = Util.safeStringInput("Password");

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
            userLogic.editUser(ssn);
        }; // Option edit a member based on SSN.

        Runnable viewMemberAction = () -> {
            userLogic.toString();
        };

        Runnable addBookInformationAction = () -> {

            String isbn;
            String name, publisher;
            String language;
            int numberOfPages;
            int answer;
            do {
                isbn = Util.safeStringInput("ISBN");
                name = Util.safeStringInput("Name");
                publisher = Util.safeStringInput("Publisher");
                language = Util.safeStringInput("Language");
                numberOfPages = Util.safeIntInput("Number of pages");

                System.out.print("\n- - - This is your information below - - -");
                System.out.printf("\n\nISBN: %s%nName: %s%nPublisher: %s%nLanguage: %s%nNumber of pages: %s%n", isbn, name, publisher, language, numberOfPages);
                answer = Util.safeIntInput("\n\nIs this information valid? Press 1 for Yes and 2 for No.");
                if (answer == 1) {

                    System.out.println("\n\nThe book '" + name + "' is now added into the library. Thank you!\n\n");
                    Book book = new Book(isbn, name, numberOfPages, language, publisher);
                    libraryLogic.addBook(book);
                } else if (answer == 2) {
                    System.out.println("\n\nPlease be more accurate with your information. \n\n");
                }

            } while (answer != 1);
        };

        Option viewMember = new Option("View Member", userLogic::viewMembers);
        Option addMember = new Option("Add Member", addMemberAction);
        Option editMember = new Option("Edit Member", editMemberAction);
        Option addBookInformation = new Option("Add Book Information", addBookInformationAction);

        debugMemberMenu = new Menu(addMember, editMember, viewMember, addBookInformation);
        setActiveMenu(debugMemberMenu);
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
