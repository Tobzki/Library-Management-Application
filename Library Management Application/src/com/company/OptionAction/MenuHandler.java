package com.company.OptionAction;

import java.util.Scanner;

public class MenuHandler {

    private Scanner input = new Scanner(System.in);

    private Menu activeMenu; // starting point
    private Menu lastMenu; // records what menu was previously visited

    private Option back;
    private Option exit;

    public MenuHandler () {
        back = new Option("Back", this::backAction);
        exit = new Option("Exit", () -> System.exit(0));

        init();
    }

    public MenuHandler (Menu startMenu) {
        activeMenu = startMenu;
        back = new Option("Back", this::backAction);
        exit = new Option("Exit", () -> System.exit(0));

        init();
    }

    private void init () {
        // TODO: Set up custom options and menus here
    }

    private void backAction () {
        setActiveMenu(lastMenu);
    }

    /**
     * Set the menu to be displayed after action has ran.
     * @param startMenu The menu to display at the end of current action.
     */
    public void setActiveMenu (Menu startMenu) {
        if (activeMenu != null) {
            lastMenu = activeMenu;
        }
        activeMenu = startMenu;
    }

    /**
     * Start the menu system using a do-while loop, this
     * loop should be exited via the exit option or a
     * similar action.
     * @throws Exception If no start menu has been set, this exception is thrown.
     *                   Use method setActiveMenu to properly set the start menu.
     */
    public void runMenu () throws Exception {
        if (activeMenu != null) {
            do { // will terminate from an action
                activeMenu.runMenu();
            } while(true);
        } else {
            throw new Exception("No starting menu was set!");
        }
    }
}
