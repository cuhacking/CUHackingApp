package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ca.carleton.three_thousand_chore.comp3004.Dates;
import ca.carleton.three_thousand_chore.comp3004.JsonObjectRequest;
import ca.carleton.three_thousand_chore.comp3004.NewNotificationListener;
import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.UserListener;
import ca.carleton.three_thousand_chore.comp3004.models.Notification;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class NotificationFragment extends Fragment implements UserListener, NewNotificationListener {
    public class NotificationAdapter extends BaseAdapter {
        class ViewHolder {
            TextView headline;
            TextView body;
            TextView createdAtTime;
        }

        List<Notification> notifications;
        LayoutInflater inflater;

        public NotificationAdapter(@NonNull Context context, @NonNull List<Notification> objects) {
            this.notifications = objects;
            Collections.sort(notifications);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return notifications.size();
        }

        @Override
        public Object getItem(int i) {
            return notifications.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @NonNull
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewInfo;

            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.notification_cell, null);
                viewInfo = new ViewHolder();
                viewInfo.body = view.findViewById(R.id.notification_body);
                //viewInfo.headline = view.findViewById(R.id.notification_headline);
                viewInfo.createdAtTime = view.findViewById(R.id.created_at_time);
                view.setTag(viewInfo);
            }
            else {
                view = convertView;
                viewInfo = (ViewHolder)view.getTag();
            }

            Notification notification = notifications.get(position);

            viewInfo.body.setText(notification.getDescription());
            viewInfo.createdAtTime.setText(Dates.prettyDate(notification.getCreatedAt()));

            return view;
        }

        public void addNotification(Notification notif) {
            this.notifications.add(notif);
            Collections.sort(notifications);
            notifyDataSetChanged();
        }
    }

    ListView notificationList;
    NotificationAdapter adapter;
    private boolean viewCreated = false;
    private int userId = -1;
    TextView countdown;
    TextView timeUntil;
    Calendar nowCalendar;
    FrameLayout frameLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void userReceived(int userId) {
        this.userId = userId;

        if (viewCreated) {
            setupNotificationList();
        }
    }

    @Override
    public void newNotification(Notification notif) {
        if (viewCreated) {
            adapter.addNotification(notif);
        }
    }

    public void setupNotificationList() {
        Notification.getAll(userId, new JsonObjectRequest.CompletionHandler<List<Notification>>() {
            @Override
            public void requestSucceeded(List<Notification> notifications) {
                adapter = new NotificationAdapter(getContext(), notifications);
                adapter.notifyDataSetChanged();
                notificationList.setAdapter(adapter);
            }

            @Override
            public void requestFailed(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_notifications, null);

        notificationList = v.findViewById(R.id.notification_list);
        countdown = v.findViewById(R.id.countdown);
        timeUntil = v.findViewById(R.id.timeUntil);
        frameLayout = v.findViewById(R.id.frameLayout);

        viewCreated = true;

        if (userId != -1) {
            setupNotificationList();
        }
        //https://stackoverflow.com/questions/32773659/how-to-countdown-to-a-day-using-android-countdowntimer

        nowCalendar = Calendar.getInstance();
        Calendar hackingStartCalendar = Calendar.getInstance();

        hackingStartCalendar.set(2018, Calendar.FEBRUARY, 10);
        hackingStartCalendar.set(Calendar.HOUR, 10);//0 is noon or midnight
        hackingStartCalendar.set(Calendar.MINUTE, 0);
        hackingStartCalendar.set(Calendar.SECOND, 0);
        hackingStartCalendar.set(Calendar.AM_PM, Calendar.AM);

        long startMillis = nowCalendar.getTimeInMillis();
        long endMillis = hackingStartCalendar.getTimeInMillis();
        long totalMillis = (endMillis - startMillis);

        //1000 = 1 second interval
        new CountDownTimer(totalMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                countdown.setText(days +" "+ getString(R.string.days) +" "+ hours + " "+ getString(R.string.hour) +" "+ minutes + " " + getString(R.string.min) +" "+ seconds + " "+ getString(R.string.sec));

            }

            @Override
            public void onFinish() {
                timeUntil.setText(getString(R.string.time_until_hacking_end));

                Calendar hackingEndCalendar = Calendar.getInstance();

                hackingEndCalendar.set(2018, Calendar.FEBRUARY, 11);
                hackingEndCalendar.set(Calendar.HOUR, 3);//0 is noon or midnight
                hackingEndCalendar.set(Calendar.MINUTE, 0);
                hackingEndCalendar.set(Calendar.SECOND, 0);
                hackingEndCalendar.set(Calendar.AM_PM, Calendar.PM);

                long startMillis = nowCalendar.getTimeInMillis();
                long endMillis = hackingEndCalendar.getTimeInMillis();
                long totalMillis = (endMillis - startMillis);

                new CountDownTimer(totalMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished);
                        millisUntilFinished -= TimeUnit.DAYS.toMillis(days);

                        long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                        millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                        countdown.setText(days +" "+ getString(R.string.days) +" "+ hours + " "+ getString(R.string.hour) +" "+ minutes + " " + getString(R.string.min) +" "+ seconds + " "+ getString(R.string.sec)); //You can compute the millisUntilFinished on hours/minutes/seconds
                    }

                    @Override
                    public void onFinish() {
                        timeUntil.setText(getString(R.string.time_hacking_over));
                        countdown.setVisibility(View.INVISIBLE);

                        ViewGroup.LayoutParams params = frameLayout.getLayoutParams();

                        params.height = 100;
                        frameLayout.setLayoutParams(params);
                    }
                }.start();
            }
        }.start();

        return v;
    }
}
