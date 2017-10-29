package ca.carleton.three_thousand_chore.comp3004.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.carleton.three_thousand_chore.comp3004.JsonObjectRequest;
import ca.carleton.three_thousand_chore.comp3004.R;
import ca.carleton.three_thousand_chore.comp3004.models.Notification;

/**
 * Created by elisakazan on 2017-10-09.
 */

public class NotificationFragment extends Fragment {
    public class NotificationAdapter extends BaseAdapter {
        class ViewHolder {
            TextView headline;
            TextView body;
        }

        List<Notification> notifications;
        LayoutInflater inflater;

        public NotificationAdapter(@NonNull Context context, @NonNull List<Notification> objects) {
            this.notifications = objects;
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
                view = getLayoutInflater().inflate(R.layout.notification_cell, null);
                viewInfo = new ViewHolder();
                viewInfo.body = view.findViewById(R.id.notification_body);
                viewInfo.headline = view.findViewById(R.id.notification_headline);
                view.setTag(viewInfo);
            }
            else {
                view = convertView;
                viewInfo = (ViewHolder)view.getTag();
            }

            Notification notification = notifications.get(position);

            viewInfo.headline.setText(notification.getTitle());
            viewInfo.body.setText(notification.getDescription());

            return view;
        }
    }

    ListView notificationList;
    NotificationAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_notifications, null);

        notificationList = v.findViewById(R.id.notification_list);

        Notification.getAll(1, new JsonObjectRequest.CompletionHandler<List<Notification>>() {
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

        return v;
    }
}
