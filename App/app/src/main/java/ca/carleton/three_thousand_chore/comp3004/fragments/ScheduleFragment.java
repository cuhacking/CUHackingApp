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

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        Event e = new Event(1, "Registration Opens", new Date(0000000000), new Date(1483286400), new Date(0000000000), "Administration", "During this time, hackers will line up in Minto to receive their CUHacking swag bag then have a chance to meet other hackers. Included in the swag bag is a t-shirt, stickers, hackthon handbook and many more fun goodies.", "Minto 2000", "N/A");
        Event e2 = new Event(2, "Opening Ceremonies", new Date(1509357607), new Date(1509357607), new Date(20100521), "Ceremony", "work on some stuff", "HP 3434", "Martello");
        Event e3 = new Event(3, "Hacking Begins", new Date(1509361207), new Date(1509361207), new Date(20100521), "Hacking", "work on some stuff", "HP 3434", "Martello");
        Event e4 = new Event(4, "Lunch", new Date(1509364807), new Date(1509364807), new Date(20100521), "Food", "work on some stuff", "HP 3434", "Martello");
        Event e5 = new Event(5, "iOS Workshop", new Date(1509332407), new Date(1509332407), new Date(20100521), "Workshop", "work on some stuff", "HP 3434", "Martello");
        Event e6 = new Event(6, "Databases Workshop", new Date(20100520), new Date(20100520), new Date(20100521), "Workshop", "work on some stuff", "HP 3434", "Martello");
        Event e7 = new Event(7, "Dinner", new Date(20100520), new Date(20100520), new Date(20100521), "Ceremony", "work on some stuff", "HP 3434", "Martello");
        Event e8 = new Event(8, "Midnight Yoga", new Date(20100520), new Date(20100520), new Date(20100521), "Hacking", "work on some stuff", "HP 3434", "Martello");
        Event e9 = new Event(9, "Breakfast", new Date(20100520), new Date(20100520), new Date(20100521), "Food", "work on some stuff", "HP 3434", "Martello");
        Event e10 = new Event(10, "React Workshop", new Date(20100520), new Date(20100520), new Date(20100521), "Workshop", "work on some stuff", "HP 3434", "Martello");
        Event e11 = new Event(11, "Rails Workshop", new Date(20100520), new Date(20100520), new Date(20100521), "Workshop", "work on some stuff", "HP 3434", "Martello");
//        scheduleList = Event.getAll();
        scheduleList.add(e);    scheduleList.add(e2);   scheduleList.add(e3);
        scheduleList.add(e4);   scheduleList.add(e5);   scheduleList.add(e6);
        scheduleList.add(e7);   scheduleList.add(e8);   scheduleList.add(e9);
        scheduleList.add(e10);  scheduleList.add(e11);

        DateFormat format = new SimpleDateFormat("HH:mm a");

        scheduleNameList = new String[scheduleList.size()];
        for(int i = 0; i < scheduleList.size(); i++){
            scheduleNameList[i] = format.format(scheduleList.get(i).getStartTime()) + " - " + scheduleList.get(i).getName();
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.schedule_list_item, scheduleNameList);
        scheduleListView.setAdapter(adapter);

        scheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putSerializable("event", scheduleList.get(position));
                Fragment fragment = new ScheduleDetailViewFragment();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });
        
        return v;
    }

}
