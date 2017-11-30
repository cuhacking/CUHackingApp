package ca.carleton.three_thousand_chore.comp3004.models;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ca.carleton.three_thousand_chore.comp3004.Dates;
import ca.carleton.three_thousand_chore.comp3004.JsonArrayRequest;
import ca.carleton.three_thousand_chore.comp3004.JsonObjectRequest;
import ca.carleton.three_thousand_chore.comp3004.Requests;


/**
 * Created by jackmccracken on 2017-10-09.
 */

public class Event implements Serializable, Comparable<Event>{
    private int id;
    private String name;
    private Date date;

    private Date startTime;
    private Date endTime;

    private String type;
    private String description;

    private Room room;
    private Company company;

    public Event(int id, String name, Date startTime, Date endTime, String type, String description, Room room, Company company) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.description = description;
        this.room = room;
        this.company = company;
    }
    public static JsonObjectRequest.ObjectCreationHandler<Event> objectCreationHandler = new JsonObjectRequest.ObjectCreationHandler<Event>() {
        @Override
        public Event fromJson(JSONObject obj) throws JSONException {
            Log.i("JSON",obj.toString());
            return new Event(obj.getInt("id"),
                    obj.getString("name"),
                    Dates.railsToJava(obj.getString("start_time")),
                    Dates.railsToJava(obj.getString("end_time")),
                    obj.getString("event_type"),
                    obj.getString("description"),
                    Room.railsToJava(obj.getJSONObject("room")),
                    Company.railsToJava(obj.getJSONObject("company")));
        }
    };

    public static void getAll(final JsonObjectRequest.CompletionHandler<List<Event>> handler) {
        JsonArrayRequest<Event> request = new JsonArrayRequest<>(Requests.BASE_URL + "/events", objectCreationHandler, handler);
        Requests.getInstance().getQueue().add(request);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Room getRoom() {
        return room;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int compareTo(@NonNull Event event) {
        return -this.startTime.compareTo(event.getStartTime());
    }
}
