package main.java.com.johnlackwilson;

import java.util.*;

/**
 * <p>The main app class to interact as the CLI with the user.</p>
 */
public class App {

    private static final String APP_TITLE = "==================================\n" +
                                            "     ______        ____           \n" +
                                            "    /_  __/____   / __ \\ ____    \n" +
                                            "     / /  / __ \\ / / / // __ \\  \n" +
                                            "    / /  / /_/ // /_/ // /_/ /    \n" +
                                            "   /_/   \\____//_____/ \\____/   \n" +
                                            "                                  \n" +
                                            "==================================\n";

    private static final String COMMAND_NOT_RECOGNISED_ERR = "Command not recognised.";
    private static final String PROMPT = "$";

    /**
     * <p>The Scanner to ask for user input.</p>
     */
    private Scanner input;

    /**
     * <p>The connector to interface with the apps' database.</p>
     */
    private DBConnector connector;

    /**
     * <p>Constructor.</p>
     */
    private App() {
        this.input = new Scanner(System.in);
        this.connector = new DBConnector();
    }

    /**
     * <p>Checks if the Menu contains a particular choice.</p>
     * @param choice the Menu selection.
     * @return true if the choice is in the Menu enum, false otherwise.
     */
    private boolean menuContains(String choice) {
        for(Menu m: Menu.values()) {
            if(m.getMenuItemName().equals(choice)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Prints the welcome message.</p>
     */
    private void welcomeMessage() {
        System.out.printf("%s\n", APP_TITLE);
    }

    /**
     * <p>Clears the console by sending ANSI escape codes.</p>
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * <p>Prints the menu to the console.</p>
     */
    private void printMenu() {
        welcomeMessage();
        Arrays.asList(Menu.values()).forEach(item -> System.out.printf("%-5s- %s\n", item.getMenuItemName(), item.getMenuItemDesc()));
    }

    /**
     * <p>Prints the menu.</p>
     */
    private void help() {
        clearScreen();
        printMenu();
    }

    /**
     * <p>Prints all items.</p>
     */
    private void list() {
        List<TodoItem> todoItems = this.connector.getAll();

        // Print message to say there's no items found
        if(todoItems.isEmpty()) {
            System.out.println("No items found. Add your first todo item with the 'add' command.");
        }
        // If there are items in the db then print them.
        else {
            for (TodoItem item : todoItems) {
                System.out.printf("%-4s %s\n", ("[" + item.getId() + "]"), item.getTitle());
            }
        }
    }

    /**
     * <p></p>
     */
    private void add(List<String> args) {
        String title = null;

        // If the list is empty then ask the user for their title.
        if(args.isEmpty()) {
            System.out.printf("%s Title: ", PROMPT);
            title = input.nextLine().trim();
        } else {
            StringBuilder sb = new StringBuilder();
            for(String arg: args) {
                sb.append(arg);
                sb.append(" ");
            }
            title = sb.toString().trim();
        }

        this.connector.add(title);
    }

    /**
     * <p></p>
     */
    private void delete(List<String> args) {
        String itemNumString;

        // If user supplied args then assume the first is the number of the item to delete.
        if(!args.isEmpty()) {
            itemNumString = args.get(0);
        } else {
            System.out.printf("%s Item number: ", PROMPT);
            itemNumString = input.nextLine();
        }

        try {
            int itemNum = Integer.parseInt(itemNumString);
            this.connector.delete(itemNum);
        } catch (InputMismatchException e) {
            System.err.printf("%s ERROR: Item number required.\n", PROMPT);
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Item number required.");
        }
    }

    /**
     * <p>Handles input from the user.</p>
     * @throws IllegalArgumentException if user inputs an invalid command.
     */
    private void menuHandler() throws IllegalArgumentException {
        System.out.printf("\n%s ", PROMPT);
        String[] userInput = this.input.nextLine().trim().split("\\s+");

        // Make sure nothing funny is going on with the split array.
        if(userInput.length < 1) {
            return;
        }
        String command = userInput[0];

        // Check if user wants to exit.
        if(command.equals(Menu.EXIT.getMenuItemName())) {
            System.out.println("Bye!");
            System.exit(0);
        }

        // Check for valid command.
        if(!menuContains(command)) {
            throw new IllegalArgumentException();
        }

        // Get any args the user has passed in
        List<String> userArgs = new ArrayList<>();
        if(userInput.length > 1) {
            userArgs.addAll(Arrays.asList(userInput).subList(1, userInput.length));
        }

        // Unfortunately cannot use a switch statement to check what user wants to do
        // because enum does not produce a constant expression which the switch needs.

        // HELP
        if(command.equals(Menu.HELP.getMenuItemName())) {
            help();
            return;
        }

        // Make sure we can connect to the db.
        if(!connector.isAccessible()) {
            clearScreen();
            System.err.println("Sorry! Database is not accessible. Has the install script been ran?");
            System.exit(-1);
        }

        // LIST
        if(command.equals(Menu.LIST.getMenuItemName())) {
            list();
        }
        // ADD
        else if(command.equals(Menu.ADD.getMenuItemName())) {
            add(userArgs);
        }
        // DELETE
        else if(command.equals(Menu.DEL.getMenuItemName())) {
            delete(userArgs);
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.clearScreen();
        app.printMenu();

        while(true) {
            try {
                app.menuHandler();
            } catch(Exception e) {
                System.err.println(COMMAND_NOT_RECOGNISED_ERR);
            }
        }
    }
}
