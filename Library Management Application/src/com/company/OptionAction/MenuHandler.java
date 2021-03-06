package com.company.OptionAction;

import com.company.Library.Book;
import com.company.Library.LibraryLogic;
import com.company.Library.Transaction;
import com.company.Users.Account;
import com.company.Users.Librarian;
import com.company.Users.Member;
import com.company.Users.UserLogic;
import com.company.Util;
import java.util.Date;
import java.util.Scanner;

public class MenuHandler {

    private Scanner input = new Scanner(System.in);
    private UserLogic userLogic = new UserLogic();
    private LibraryLogic libraryLogic = new LibraryLogic();

    private Menu activeMenu; // starting point
    private Menu lastMenu; // records what menu was previously visited

    // Custom menus
    private Menu loginMenu;
    private Menu librarian, handleMembers, handleBooks, handleRequests;
    private Menu member, books, loans;

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
        Option viewMembers = new Option("View members", userLogic::viewListMembers);

        Option addMember = new Option("Add Member", () -> {
            if (userLogic.authorize() == UserLogic.USER_STATE.LIBRARIAN) {
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
            } else {
                System.out.println("You are not permitted to perform this action.");
            }
        });

        Option editMember = new Option("Edit Member", () -> {
            String ssn = Util.safeStringInput("SSN of user to edit");
            if (userLogic.editUser(ssn)) {
                System.out.println("User was edited.");
            } else {
                System.out.println("No user was edited (You can only edit members, and make sure to enter the correct SSN).");
            }
        });

        Option removeMember = new Option("Remove Member", () -> {
            String ssn = Util.safeStringInput("SSN of member to remove");
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
                if (userLogic.authorize() == UserLogic.USER_STATE.LIBRARIAN) {
                    setActiveMenu(librarian);
                } else {
                    setActiveMenu(member);
                }
            } else {
                System.out.println("Username and/or password was incorrect.");
            }
        });

        // Options about loaning
        Option issueBook = new Option("Loan", () -> {
            if (userLogic.authorize() == UserLogic.USER_STATE.MEMBER) {
                String isbn = Util.safeStringInput("ISBN of book");
                Book book = libraryLogic.getBook(isbn);
                if (book == null) {
                    System.out.println("No book was found with that ISBN, please try again.");
                } else if (!book.isAvailable()) {
                    // search for when the book is back in stock, super slow algorithm but i'm working with
                    // the current structure of the program
                    Date dueDate = new Date();
                    for (Account acc : userLogic.getUsers()) {
                        if (acc instanceof Member) {
                            for (Transaction trans : ((Member) acc).getTransactions()) {
                                if (isbn.equals(trans.getBookId())) {
                                    dueDate = trans.getDueDate();
                                }
                            }
                        }
                    }
                    System.out.println("This book has been loaned out and will be back " + dueDate);
                } else {
                    int confirm = Util.safeIntInput("Is '" + book.getTitle() + "' the correct book?\n1) Yes 2) No", 2);
                    if (confirm == 1 && userLogic.makeTransaction(book)) {
                        System.out.println("You have loaned '" + book.getTitle() + "'");
                    }
                }
            } else {
                System.out.println("You are not permitted to perform this action.");
            }
        });

        Option viewTransactions = new Option("View Transactions", userLogic::viewTransactions);

        Option renewTransaction = new Option("Renew Transaction", () -> {
            if (userLogic.authorize() == UserLogic.USER_STATE.MEMBER) {
                int index = Util.safeIntInput("Transaction id");
                if (((Member) userLogic.getLoggedIn()).renewTransaction(index)) {
                    System.out.println("Transaction was renewed.");
                } else {
                    System.out.println("Incorrect transaction id or transaction passed due date already.");
                }
            }
        });

        Option returnBook = new Option("Return book", () -> {
            int transactionId = Util.safeIntInput("Transaction id:");
            Book book = libraryLogic.getBook(userLogic.getTransactionBookId(transactionId));
            if (book != null && userLogic.returnBook(transactionId)) {
                book.setAvailable(true);
                System.out.println("Book was returned.");
            } else {
                System.out.println("Something went wrong.");
            }
        });

        Option viewMembersAfterDue = new Option("View members after due", userLogic::printMembersAfterDue);

        // Request options
        Option requestBook = new Option("Request a new book", () -> {
            if (userLogic.authorize() == UserLogic.USER_STATE.MEMBER) {
                String title = Util.safeStringInput("Title of book");
                if (libraryLogic.bookExists(title)) {
                    System.out.println("This book is already in the library!\nTry searching for it instead.");
                } else if (Librarian.requestExists(title)) {
                    System.out.println("We have already recieved a request for this book, if possible it will be added soon.");
                } else {
                    Librarian.addRequest(title);
                }
            }
        });

        Option viewRequests = new Option ("View book requests", () -> {
            if (userLogic.authorize() == UserLogic.USER_STATE.LIBRARIAN) {
                ((Librarian) userLogic.getLoggedIn()).printRequests();
            }
        });

        Option removeRequest = new Option ("Remove request", () -> {
            if (userLogic.authorize() == UserLogic.USER_STATE.LIBRARIAN) {
                String title = Util.safeStringInput("Request/title");
                if (Librarian.requestExists(title)) {
                    ((Librarian) userLogic.getLoggedIn()).removeRequest(title);
                } else {
                    System.out.println("Could not find the request, go back and control spelling.");
                }
            }
        });

        // Options about books
        Option editBook = new Option("Edit book", () -> {
            String isbn = Util.safeStringInput("ISBN number of book to edit");
            if (!libraryLogic.editBook(isbn)) {
                System.out.println("Please try again.");
            } else {
                System.out.println("\n\nYour book has now been edited. Thank you!\n");
            }
        });

        Option viewTransactionHistoryMember = new Option("View transaction history of member", () -> {

            if (userLogic.authorize() == UserLogic.USER_STATE.LIBRARIAN) {

                String ssn = Util.safeStringInput("SSN of member");
                if (userLogic.getUser(ssn) != null) {

                    userLogic.viewMembersTransactionHistory(userLogic.getUser(ssn).getUsername());
                } else {
                    System.out.println("No member was found.");
                }
            } else if (userLogic.authorize() == UserLogic.USER_STATE.MEMBER) {

                userLogic.viewMembersTransactionHistory(userLogic.getLoggedIn().getUsername());
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

                if (libraryLogic.addBook(new Book(isbn, title, numberOfPages, language, publisher, category, authors))) {
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

        Option printBooksCategory = new Option("Print books in category", () -> {
            libraryLogic.viewCategories();
            int answer = Util.safeIntInput("Index of category to print", libraryLogic.getCategories().size());
            libraryLogic.printCategoryBooks(answer);

        });

        // Menu navigation
        Option goToHandleBooks = new Option("Handle books", () -> setActiveMenu(handleBooks));
        Option goToHandleMembers = new Option("Handle members", () -> setActiveMenu(handleMembers));
        Option goToHandleRequests = new Option("Handle requests", () -> setActiveMenu(handleRequests));
        Option goToLoans = new Option("View loans", () -> setActiveMenu(loans));
        Option goToBooks = new Option("View books", () -> setActiveMenu(books));
        Option logOut = new Option ("Log out", () -> {

            userLogic.logOut();
            setActiveMenu(loginMenu);
        });

        loginMenu = new Menu(login, exit);
        librarian = new Menu(goToHandleBooks, goToHandleMembers, goToHandleRequests, logOut, exit);
        handleBooks = new Menu(addBookInformation, editBook, removeBook, searchBook, printBooksCategory, back);
        handleMembers = new Menu(addMember, editMember, removeMember, viewMembers, viewMembersAfterDue, viewTransactionHistoryMember, back);
        handleRequests = new Menu(viewRequests, removeRequest, back);
        member = new Menu(goToLoans, goToBooks, requestBook, logOut, exit);
        books = new Menu(searchBook, printBooksCategory, back);
        loans = new Menu(issueBook, searchBook, returnBook, viewTransactions, viewTransactionHistoryMember, renewTransaction, back);
        setActiveMenu(loginMenu);
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
