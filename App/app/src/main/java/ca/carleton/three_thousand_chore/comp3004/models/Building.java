package ca.carleton.three_thousand_chore.comp3004.models;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class Building {
    private int id;
    private String name;
    private String initials;

    public Building(int id, String name, String initials) {
        this.id = id;
        this.name = name;
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public String getInitials() {
        return initials;
    }

    public int getId() {
        return id;
    }
}
