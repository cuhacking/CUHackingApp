package ca.carleton.three_thousand_chore.comp3004;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import ca.carleton.three_thousand_chore.comp3004.models.Notification;

/**
 * Created by elisakazan on 2017-10-25.
 */

public class NotificationAdapter extends ArrayAdapter<Notification>
{
    public NotificationAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Notification> objects)
    {
        super(context, resource, objects);
    }
}
