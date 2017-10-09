package ca.carleton.three_thousand_chore.comp3004.models;

import java.util.Date;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class Event {
    private int id;
    private Date date;

    private Date startTime;
    private Date endTime;

    private String type;
    private String description;

    public Event(int id, Date date, Date startTime, Date endTime, String type, String description, String room, String company) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
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
}
