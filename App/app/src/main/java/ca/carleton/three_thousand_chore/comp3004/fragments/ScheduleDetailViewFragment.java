package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.models.Event;


public class ScheduleDetailViewFragment extends Fragment {
    TextView name;
    TextView date;
    TextView time;
    TextView type;
    TextView location;
    TextView description;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule_detail_view, null);
        name = v.findViewById(R.id.name);
        date = v.findViewById(R.id.date);
        time = v.findViewById(R.id.time);
        type = v.findViewById(R.id.type);
        location = v.findViewById(R.id.location);
        description = v.findViewById(R.id.description);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Serializable e = bundle.getSerializable("event");
            Event ev = (Event)e;

            DateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d");
            DateFormat timeFormat = new SimpleDateFormat("h:mm a");

            name.setText(ev.getName().toString());
            date.setText(dateFormat.format(ev.getStartTime()));
            time.setText(timeFormat.format(ev.getStartTime()) + " - "+ timeFormat.format(ev.getEndTime()));
            type.setText(ev.getType().toString());
            location.setText(ev.getRoom().getName());
            description.setText(ev.getDescription().toString());

        }

        return v;
    }
}
