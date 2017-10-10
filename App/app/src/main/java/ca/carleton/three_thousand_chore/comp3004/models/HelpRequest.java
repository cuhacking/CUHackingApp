package ca.carleton.three_thousand_chore.comp3004.models;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class HelpRequest {
    private int id;
    private String location;
    private String problem;
    private String[] mentors;
    private String status;

    public HelpRequest(int id, String location, String problem, String[] mentors, String status) {
        this.id = id;
        this.location = location;
        this.problem = problem;
        this.mentors = mentors;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    
    public String getlocation() {
        return location;
    }

    public String getProblem() {
        return problem;
    }

    public String[] getMentors() {
        return mentors;
    }

    public String getStatus() {
        return status;
    }
}
