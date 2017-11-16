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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.carleton.three_thousand_chore.comp3004.JsonObjectRequest;
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

        Event.getAll(new JsonObjectRequest.CompletionHandler<List<Event>>() {
            @Override
            public void requestSucceeded(List<Event> events) {
                scheduleList = new ArrayList<>(events);
                DateFormat format = new SimpleDateFormat("h:mm a");

                scheduleNameList = new String[scheduleList.size()];
                Collections.sort(scheduleList);
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
                                .addToBackStack(getResources().getStringArray(R.array.activities_array)[1]+"Detail")
                                .commit();
                    }
                });
            }

            @Override
            public void requestFailed(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        
        return v;
    }

}
