package ca.carleton.three_thousand_chore.comp3004.models;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class Notification {
    private int id;
    private String title;
    private String description;

    public Notification(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
