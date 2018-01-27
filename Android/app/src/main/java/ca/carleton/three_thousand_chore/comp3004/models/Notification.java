package ca.carleton.three_thousand_chore.comp3004.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import ca.carleton.three_thousand_chore.comp3004.Dates;
import ca.carleton.three_thousand_chore.comp3004.JsonArrayRequest;
import ca.carleton.three_thousand_chore.comp3004.JsonObjectRequest;
import ca.carleton.three_thousand_chore.comp3004.Requests;

/**
 * Created by jackmccracken on 2017-10-09.
 */

public class Notification implements Parcelable, Comparable<Notification> {
    private int id;
    private String title;
    private String description;

    public Date getCreatedAt() {
        return createdAt;
    }

    private Date createdAt;

    private Notification(int id, String title, String description, Date createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    private Notification(Parcel in) {
        this(in.readInt(), in.readString(), in.readString(), (Date)in.readSerializable());
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static JsonObjectRequest.ObjectCreationHandler<Notification> objectCreationHandler = new JsonObjectRequest.ObjectCreationHandler<Notification>() {
        @Override
        public Notification fromJson(JSONObject obj) throws JSONException {
            return new Notification(obj.getInt("id"), obj.getString("title"), obj.getString("description"), Dates.railsToJava(obj.getString("created_at")));
        }
    };

    public static void getAll(int userId, final JsonObjectRequest.CompletionHandler<List<Notification>> handler) {
        JsonArrayRequest<Notification> request = new JsonArrayRequest<>(Requests.BASE_URL + "/users/" + userId + "/notifications", objectCreationHandler, handler);
        Requests.getInstance().getQueue().add(request);
    }

    public static Notification fromJson(JSONObject obj) throws JSONException {
        return objectCreationHandler.fromJson(obj);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeSerializable(createdAt);
    }

    @Override
    public int compareTo(@NonNull Notification notification) {
        return -this.getCreatedAt().compareTo(notification.getCreatedAt());
    }
}
