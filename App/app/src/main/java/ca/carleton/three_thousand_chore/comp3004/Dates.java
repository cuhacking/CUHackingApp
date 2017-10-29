package ca.carleton.three_thousand_chore.comp3004;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jackmccracken on 2017-10-28.
 */

public class Dates {
    public static Date railsToJava(String railsTimestamp) {
        String format = "yyyy-MM-dd'T'HH:mm:ss.sss";

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(railsTimestamp);
        } catch (ParseException e) {
            Log.e("Dates Log", "Failed to parse date from Rails: " + e.getMessage());
        }

        return date;
    }
}
