package ca.carleton.three_thousand_chore.comp3004.models;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class Room {
    private int id;
    private String type;
    private String name;

    public Room(int id, String type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
