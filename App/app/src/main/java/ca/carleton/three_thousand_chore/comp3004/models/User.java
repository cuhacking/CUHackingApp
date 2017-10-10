package ca.carleton.three_thousand_chore.comp3004.models;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class User {
    private int id;
    private String name;

    public User(int id, int[] helpRequests, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
