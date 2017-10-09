package ca.carleton.three_thousand_chore.comp3004.models;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class Company {
    private int id;
    private String name;
    private String tier;
    private String websiteURL;
    private String logo;

    public Company(int id, String name, String tier, String websiteURL, String logo) {
        this.id = id;
        this.name = name;
        this.tier = tier;
        this.websiteURL = websiteURL;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTier() {
        return tier;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public String getLogoURL() {
        return logo;
    }
}
