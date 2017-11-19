package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

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

        viewCreated = true;

        if (userId != -1) {
            setupNotificationList();
        }

        return v;
    }
}
