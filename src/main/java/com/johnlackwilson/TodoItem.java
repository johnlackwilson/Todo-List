package main.java.com.johnlackwilson;

import java.time.LocalDateTime;

/**
 * Class to represent a Todo item.
 */
public class TodoItem {

    /**
     * The ID of the item.
     */
    private int id;

    /**
     * The title of the item.
     */
    private String title;

    /**
     * Whether the item has been complete or not.
     */
    private boolean complete;

    /**
     * When the item was added.
     */
    private LocalDateTime dateAdded;

    /**
     * The due date of the item.
     */
    private LocalDateTime dateDue;

    /**
     * <p>Constructor.</p>
     * @param id the database ID of the item.
     * @param title the database title of the item.
     * @param complete the database complete boolean of the item.
     * @param dateAdded the database datetime when the item was added.
     * @param dateDue the database datetime when the item is due.
     */
    public TodoItem(int id, String title, boolean complete, LocalDateTime dateAdded, LocalDateTime dateDue) {
        this.id = id;
        this.title = title;
        this.complete = complete;
        this.dateAdded = dateAdded;
        this.dateDue = dateDue;
    }

    /**
     * @return the ID of the Todo item.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the database ID of the Todo item.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the title of the Todo item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the database title of the Todo item.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return whether the Todo item is complete or not.
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * @param complete the database boolean whether the Todo item is complete or not.
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * @return the database datetime of when the Todo item was added.
     */
    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    /**
     * @param dateAdded the database datetime when the Todo item was added.
     */
    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * @return the database datetime of when the Todo item is due.
     */
    public LocalDateTime getDateDue() {
        return dateDue;
    }

    /**
     * @param dateDue the database datetime when the Todo item is due.
     */
    public void setDateDue(LocalDateTime dateDue) {
        this.dateDue = dateDue;
    }
}
