package ca.carleton.three_thousand_chore.comp3004.models;

import org.json.JSONObject;

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

    public static Room railsToJava(JSONObject railsRoom) {
        try {
            Room room = new Room(railsRoom.getInt("id"),
                    railsRoom.getString("type"),
                    railsRoom.getString("name"));
            return room;
        }
        catch(Exception ex){
          ex.printStackTrace();
        }
        return null;
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
