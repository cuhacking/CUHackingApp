package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Date;

import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.models.Event;


public class ScheduleDetailViewFragment extends Fragment {
    TextView name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule_detail_view, null);

        name = v.findViewById(R.id.title);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Serializable e = bundle.getSerializable("event");
            Event ev = (Event)e;
            name.setText(ev.getName().toString());
        }

        return v;
    }
}
