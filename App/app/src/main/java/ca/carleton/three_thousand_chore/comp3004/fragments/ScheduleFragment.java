package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.models.Event;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class ScheduleFragment extends Fragment {

    ListView scheduleListView;
    ArrayList <Event> scheduleList;
    String[] scheduleNameList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_schedule, null);
        scheduleListView = v.findViewById(R.id.scheduleListView);
//        TODO headers for days

        scheduleList = new ArrayList<>();
        Event e = new Event(1, "event", new Date(20100520), new Date(20100520), new Date(20100521), "hacking", "work on some stuff", "HP 3434", "Martello");
//        scheduleList = Event.getAll();
        scheduleList.add(e);

        scheduleNameList = new String[scheduleList.size()];
        for(int i = 0; i < scheduleList.size(); i++){
            scheduleNameList[i] = scheduleList.get(i).getName();
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.schedule_list_item, scheduleNameList);
        scheduleListView.setAdapter(adapter);

        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new ScheduleDetailViewFragment())
                        .commit();
            }
        });
        
        return v;
    }

}
