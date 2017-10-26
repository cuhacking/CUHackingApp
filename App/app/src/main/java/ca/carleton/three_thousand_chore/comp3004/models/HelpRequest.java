package ca.carleton.three_thousand_chore.comp3004.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ca.carleton.three_thousand_chore.comp3004.JsonRequest;
import ca.carleton.three_thousand_chore.comp3004.Requests;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class HelpRequest implements Parcelable{
    private int id;
    private String location;
    private String problem;
    private String[] mentors;
    private String status;

    private String profilePictureURL;

    public HelpRequest(int id, String location, String problem, String status, String[] mentors, String profilePictureURL) {
        this.id = id;
        this.location = location;
        this.problem = problem;
        this.status = status;
        this.mentors = mentors;
        this.profilePictureURL = profilePictureURL;
    }

    public HelpRequest(Parcel p) {
        this(p.readInt(), p.readString(), p.readString(), p.readString(), p.createStringArray(), p.readString());
    }

    public int getId() {
        return id;
    }
    
    public String getLocation() {
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

    public static void forId(int id, JsonRequest.CompletionHandler<HelpRequest> handler) {
        JsonRequest<HelpRequest> request = new JsonRequest<>(Request.Method.GET, Requests.BASE_URL + "/help_requests/" + id, null, objectCreationHandler, handler);
        Requests rh = Requests.getInstance();
        rh.getQueue().add(request);
    }

    public static void forUser(User user, JsonRequest.CompletionHandler<HelpRequest> handler) {
        JsonRequest<HelpRequest> request = new JsonRequest<>(Request.Method.GET, Requests.BASE_URL + "/users/" + user.getId() + "/help_request/", null, objectCreationHandler, handler);
        Requests rh = Requests.getInstance();
        rh.getQueue().add(request);
    }

    private static JsonRequest.ObjectCreationHandler<HelpRequest> objectCreationHandler = new JsonRequest.ObjectCreationHandler<HelpRequest>() {
        @Override
        public HelpRequest fromJson(JSONObject obj) throws JSONException {
            JSONArray mentorsJsonArray = obj.getJSONArray("mentors");
            String[] mentors = new String[mentorsJsonArray.length()];

            for (int i = 0; i < mentors.length; i ++) {
                mentors[i] = mentorsJsonArray.getString(i);
            }

            return new HelpRequest(obj.getInt("id"), obj.getString("location"), obj.getString("problem"), obj.getString("status"), mentors, obj.getString("profile_pic_link"));
        }
    };

    public static void createHelpRequest(String location, String problem, int userId, String usersName, final JsonRequest.CompletionHandler<HelpRequest> handler) {
        try {
            JSONObject requestParams = new JSONObject();
            JSONObject helpRequestObject = new JSONObject();
            helpRequestObject.put("location", location);
            helpRequestObject.put("problem", problem);
            requestParams.put("help_request", helpRequestObject);
            requestParams.put("user_name", usersName);
            requestParams.put("user_id", userId);

            JsonRequest<HelpRequest> request = new JsonRequest<>(Request.Method.POST, Requests.BASE_URL + "/help_requests", requestParams, objectCreationHandler, handler);
            Requests rh = Requests.getInstance();

            rh.getQueue().add(request);
        } catch (JSONException e) {
            Log.e(HelpRequest.class.getClass().getSimpleName(), "Failed to create help request: " + e.getMessage());
        }
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public HelpRequest createFromParcel(Parcel in) {
                    return new HelpRequest(in);
                }

                public HelpRequest[] newArray(int size) {
                    return new HelpRequest[size];
                }
            };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(id);
        parcel.writeString(location);
        parcel.writeString(problem);
        parcel.writeString(status);
        parcel.writeStringArray(getMentors());
        parcel.writeString(profilePictureURL);
    }

    private JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("location", location);
        obj.put("problem", problem);
        obj.put("status", status);
        obj.put("mentors", mentors);
        obj.put("profile_pic_link", profilePictureURL);

        return obj;
    }

    public static HelpRequest fromJson(JSONObject obj) throws JSONException {
        return objectCreationHandler.fromJson(obj);
    }

    private void updateOnServer(JsonRequest.CompletionHandler<JSONObject> handler) throws JSONException {
        JSONObject requestParameters = toJson();

        JsonRequest<HelpRequest> request = new JsonRequest<>(Request.Method.PUT, Requests.BASE_URL + "/help_requests/" + id, requestParameters, handler);
        RequestQueue rh = Requests.getInstance().getQueue();
        rh.add(request);
    }

    public void setStatus(String status, final JsonRequest.CompletionHandler<JSONObject> completion) throws JSONException {
        this.status = status;

        updateOnServer(completion);
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }
}