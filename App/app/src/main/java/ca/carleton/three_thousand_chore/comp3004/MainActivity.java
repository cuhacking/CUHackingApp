package ca.carleton.three_thousand_chore.comp3004;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import ca.carleton.three_thousand_chore.comp3004.fragments.DefaultFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.LinksFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.MapFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.NotificationFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.RequestHelpFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.RequestHelpSuccessfulFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.ScheduleFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.SponsorsFragment;
import ca.carleton.three_thousand_chore.comp3004.models.HelpRequest;
import ca.carleton.three_thousand_chore.comp3004.models.Notification;
import ca.carleton.three_thousand_chore.comp3004.models.User;

public class MainActivity extends AppCompatActivity implements RequestHelpFragment.HelpRequestSentListener, RequestHelpSuccessfulFragment.HelpRequestCompletedListener {
    // Hamburger menu
    private DrawerLayout drawer;
    private ListView drawerList;
    private ActionBarDrawerToggle toggle;
    private String[] activitiesList;
    private CharSequence drawerTitle;
    private CharSequence title;

    // General
    private User user;
    private HelpRequest activeHelpRequest;
    private int drawerPosition;

    // Page constants
    private static final int NOTIFICATION_PAGE = 0;
    private static final int SCHEDULE_PAGE = 1;
    private static final int MAP_PAGE = 2;
    private static final int HELP_PAGE = 3;
    private static final int LINKS_PAGE = 4;
    private static final int SPONSORSHIP_PAGE = 5;

    // Listeners (usually fragments)
    private UserListener userListener = null;
    private NewNotificationListener notificationListener = null;


    @Override
    protected void onStop() {
        super.onStop();

        stopService(new Intent(this, NotificationFirebaseService.class));
        stopService(new Intent(this, CUHFirebaseInstanceIDService.class));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Requests.getInstance(this);

        startService(new Intent(this, NotificationFirebaseService.class));
        startService(new Intent(this, CUHFirebaseInstanceIDService.class));

        // Drawer menu
        activitiesList = getResources().getStringArray(R.array.activities_array);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.nav_drawer);

        // Screen Title
        title = drawerTitle = getTitle();

        // Set up the menu with screen items
        drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, activitiesList));

        // Set onClick Listeners for each item in the menu
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // ActionBar is the part where the title is, enable to allow changes
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        BroadcastReceiver helpRequestReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                MainActivity.this.activeHelpRequest = intent.getParcelableExtra("help_request");

                if (drawerPosition == HELP_PAGE) {
                    selectItem(HELP_PAGE);
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(helpRequestReceiver, new IntentFilter(NotificationFirebaseService.HR_BROADCAST_NAME));

        BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (notificationListener != null) {
                    notificationListener.newNotification((Notification) intent.getParcelableExtra("notification"));
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(notificationReceiver, new IntentFilter(NotificationFirebaseService.NOTIFICATION_BROADCAST_NAME));

        final SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences_file_key), Context.MODE_PRIVATE);

        if (!preferences.contains(getString(R.string.user_id_key))) {
            // New user
            User.createUser(new JsonObjectRequest.CompletionHandler<User>() {
                @Override
                public void requestSucceeded(User user) {
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putInt(getString(R.string.user_id_key), user.getId());
                    editor.apply();

                    MainActivity.this.user = user;

                    if (userListener != null) {
                        userListener.userReceived(user.getId());
                    }
                }

                @Override
                public void requestFailed(String errorMessage) {
                    Toast.makeText(MainActivity.this, "Failed to create user: " + errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity Logter", "Failed to create user: " + errorMessage);
                }
            });
        }
        else {

            // Existing user
            this.user = new User(preferences.getInt(getString(R.string.user_id_key), -1));
            if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("help_request")) {
                // If we got here from a notification
                String json = getIntent().getExtras().getString("help_request");
                try {
                    JSONObject helpRequestJson = new JSONObject(json);

                    this.activeHelpRequest = HelpRequest.fromJson(helpRequestJson);
                } catch (JSONException e) {
                    Log.e("MainActivity Log", "Help request parse failed: " + e.getMessage());
                }
            }
            else {
                // If we got here from the user tapping on the app
                HelpRequest.forUser(this.user, new JsonObjectRequest.CompletionHandler<HelpRequest>()
                {
                    @Override
                    public void requestSucceeded(HelpRequest object)
                    {
                        MainActivity.this.activeHelpRequest = object;
                    }

                    @Override
                    public void requestFailed(String errorMessage)
                    {
                        Log.e("MainActivity Log", errorMessage);
                    }
                });
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic("announcements");

        // Toggle connects the sliding drawer with action bar app icon
        toggle = new ActionBarDrawerToggle(
                this,                   /* host Activity (aka main) */
                drawer,                 /* DrawerLayout object */
                R.string.drawer_open,   /* "open drawer" description for usability */
                R.string.drawer_close   /* "close drawer" description for usability */
        ) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()

                UIHelper.closeKeyboard(getCurrentFocus(), (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawer.addDrawerListener(toggle);

        int drawerPage;

        if (!isNetworkAvailable()) {
            Toast.makeText(this, R.string.wifi_disconnected_text, Toast.LENGTH_SHORT).show();
            drawerPage = LINKS_PAGE;
        }
        else if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("drawer_page")) {
            drawerPage = Integer.parseInt(getIntent().getExtras().getString("drawer_page"));
        }
        else {
            drawerPage = NOTIFICATION_PAGE;
        }

        selectItem(drawerPage);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Open and close the drawer
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void helpRequestSent(HelpRequest request) {
        this.activeHelpRequest = request;
        selectItem(HELP_PAGE);
    }

    @Override
    public void helpRequestCompleted() {
        this.activeHelpRequest = null;
        selectItem(HELP_PAGE);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        // Listens for which option you have selected from the menu
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void requestUserId(UserListener listener) {
        if (user == null) {
            this.userListener = listener;
        }
        else {
            listener.userReceived(user.getId());
        }
    }

    private void selectItem(int position)
    {
        // Figure out which page was selected
        String page = activitiesList[position];

        // Create Fragment
        Fragment fragment;

        this.userListener = null;
        this.notificationListener = null;

        // Use page to create new Fragment
        switch(position){
            case NOTIFICATION_PAGE:
                // Notifications
                NotificationFragment notificationFragment = new NotificationFragment();
                requestUserId(notificationFragment);
                this.notificationListener = notificationFragment;
                fragment = notificationFragment;
                break;
            case SCHEDULE_PAGE:
                // Schedule
                fragment = new ScheduleFragment();
                break;
            case MAP_PAGE:
                // Map
                fragment = new MapFragment();
                break;
            case HELP_PAGE:
                if (this.activeHelpRequest == null) {
                    // Create new help request
                    RequestHelpFragment helpFragment = RequestHelpFragment.newInstance();
                    requestUserId(helpFragment);
                    fragment = helpFragment;
                }
                else {
                    // Help request either pending mentor or mentor found
                    fragment = RequestHelpSuccessfulFragment.newInstance(activeHelpRequest);
                }

                break;
            case LINKS_PAGE:
                // Links
                fragment = new LinksFragment();
                break;
            case SPONSORSHIP_PAGE:
                // Sponsors
                fragment = new SponsorsFragment();
                break;
            default:
                Toast.makeText(this, "ERROR: fragment not found.", Toast.LENGTH_LONG).show();
                fragment = new DefaultFragment();
                break;
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        this.drawerPosition = position;
        drawerList.setItemChecked(position, true);
        setTitle(activitiesList[position]);
        drawer.closeDrawer(drawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
        getSupportActionBar().setTitle(this.title);
    }
}
