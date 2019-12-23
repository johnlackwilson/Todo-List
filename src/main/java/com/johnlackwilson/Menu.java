package main.java.com.johnlackwilson;

public enum Menu {
    EXIT    ("exit", "Exit the program."),
    ADD     ("add", "Adds a new item to the todo list."),
    LIST    ("list", "View all items in the todo list."),
    DEL     ("del", "Delete an item from the todo list."),
    HELP    ("help", "Show helper.")
    ;

    private final String menuItemName;
    private final String menuItemDesc;

    Menu(String menuItemName, String menuItemDesc) {
        this.menuItemName = menuItemName;
        this.menuItemDesc = menuItemDesc;
    }

    public String getMenuItemName() {
        return this.menuItemName;
    }

    public String getMenuItemDesc() {
        return this.menuItemDesc;
    }
}
