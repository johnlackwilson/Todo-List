package main.java.com.johnlackwilson;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String COMMAND_NOT_RECOGNISED_ERR = "Command not recognised.";

    private Scanner input;
    private DBConnector connector;

    private App() {
        this.input = new Scanner(System.in);
        this.connector = new DBConnector();
    }

    private boolean menuContains(String choice) {
        for(Menu m: Menu.values()) {
            if(m.getMenuItemName().equals(choice)) {
                return true;
            }
        }
        return false;
    }

    private void printMenu() {
        Arrays.asList(Menu.values()).forEach(item -> System.out.println("[" + item.getMenuItemName() + "]: " + item.getMenuItemDesc()));
    }

    private void menuHandler() throws IllegalArgumentException {
        String choice = this.input.next();

        // Unfortunately cannot use a switch statement to check what user wants to do
        // because enum does not produce a constant expression which the switch needs.

        // Check if user wants to exit.
        if(choice.equals(Menu.EXIT.getMenuItemName())) {
            System.out.println("Bye!");
            System.exit(0);
        }

        // Check for valid command.
        if(!menuContains(choice)) {
            throw new IllegalArgumentException();
        }

        // Now we know that we have a valid command so let's handle it.
        if(choice.equals(Menu.HELP.getMenuItemName())) {
            printMenu();
        } else if(choice.equals(Menu.LIST.getMenuItemName())) {
            List<Todo> todoItems = this.connector.getAll();
            for(Todo item: todoItems) {
                System.out.println(String.format("[%d]: %s\n", item.getId(), item.getTitle()));
            }
        } else if(choice.equals(Menu.ADD.getMenuItemName())) {
            // Now we need to parse any further args for the title of the item
            System.out.print("[TITLE] ");
            this.connector.add(input.nextLine());
        } else if(choice.equals(Menu.DEL.getMenuItemName())) {
            System.out.print("[ITEM NUMBER] ");
            try {
                this.connector.delete(input.nextInt());
            } catch (InputMismatchException e) {
                System.err.println("[ERROR] Item number required.");
            }
        }
    }

    public static void main(String[] args) {
        App app = new App();
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
