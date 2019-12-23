package main.java.com.johnlackwilson;

import java.time.LocalDateTime;

public class Todo {

    private int id;
    private String title;
    private boolean complete;
    private LocalDateTime dateAdded;
    private LocalDateTime dateDue;

    public Todo(int id, String title, boolean complete, LocalDateTime dateAdded, LocalDateTime dateDue) {
        this.id = id;
        this.title = title;
        this.complete = complete;
        this.dateAdded = dateAdded;
        this.dateDue = dateDue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDateTime getDateDue() {
        return dateDue;
    }

    public void setDateDue(LocalDateTime dateDue) {
        this.dateDue = dateDue;
    }
}
