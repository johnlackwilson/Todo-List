package main.java.com.johnlackwilson;

/**
 * Menu items that can be called by the user.
 *
 * <li>{@link #EXIT}</li>
 * <li>{@link #ADD}</li>
 * <li>{@link #LIST}</li>
 * <li>{@link #DEL}</li>
 * <li>{@link #HELP}</li>
 */
public enum Menu {
    /**
     * Exit the program.
     */
    EXIT    ("exit", "Exit the program."),

    /**
     * Add an item.
     */
    ADD     ("add", "Adds a new item to the todo list."),

    /**
     * List all items.
     */
    LIST    ("list", "View all items in the todo list."),

    /**
     * Delete an item.
     */
    DEL     ("del", "Delete an item from the todo list."),

    /**
     * Show the help.
     */
    HELP    ("help", "Show menu.")
    ;

    private final String menuItemName;
    private final String menuItemDesc;

    /**
     * <p>Menu enum constructor</p>
     * @param menuItemName name of the menu item.
     * @param menuItemDesc description of the menu item.
     */
    Menu(String menuItemName, String menuItemDesc) {
        this.menuItemName = menuItemName;
        this.menuItemDesc = menuItemDesc;
    }

    /**
     * <p>Get the name of the menu item.</p>
     * @return String of the menu item name.
     */
    public String getMenuItemName() {
        return this.menuItemName;
    }

    /**
     * <p>Get the description of the menu item.</p>
     * @return String of the menu item description.
     */
    public String getMenuItemDesc() {
        return this.menuItemDesc;
    }
}
