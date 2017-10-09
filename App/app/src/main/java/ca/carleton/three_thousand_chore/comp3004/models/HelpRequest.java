package ca.carleton.three_thousand_chore.comp3004.models;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class HelpRequest {
    private int id;
    private String hackerLocation;
    private String problem;
    private String[] mentors;
    private String status;

    public HelpRequest(int id, int userId, String hackerLocation, String problem, String[] mentors, String status) {
        this.id = id;
        this.hackerLocation = hackerLocation;
        this.problem = problem;
        this.mentors = mentors;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    
    public String getHackerLocation() {
        return hackerLocation;
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
