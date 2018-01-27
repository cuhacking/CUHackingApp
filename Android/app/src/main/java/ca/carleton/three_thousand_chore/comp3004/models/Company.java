package ca.carleton.three_thousand_chore.comp3004.models;

import org.json.JSONObject;

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

    public static Company railsToJava(JSONObject railsCompany) {
        try {
            return new Company(railsCompany.getInt("id"),
                    railsCompany.getString("name"),
                    railsCompany.getString("tier"),
                    railsCompany.getString("website_url"),
                    railsCompany.getString("logo"));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
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
