package ca.carleton.three_thousand_chore.comp3004;

import android.app.FragmentManager;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.Toast;

import ca.carleton.three_thousand_chore.comp3004.fragments.DefaultFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.LinksFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.MapFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.NotificationFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.RequestHelpFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.RequestHelpSuccessfulFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.ScheduleFragment;
import ca.carleton.three_thousand_chore.comp3004.fragments.SponsorsFragment;
import ca.carleton.three_thousand_chore.comp3004.models.HelpRequest;
import ca.carleton.three_thousand_chore.comp3004.models.User;

public class MainActivity extends AppCompatActivity implements RequestHelpFragment.HelpRequestSentListener {

    // Hamburger menu
    private DrawerLayout drawer;
    private ListView drawerList;
    private ActionBarDrawerToggle toggle;
    private String[] activitiesList;
    private CharSequence drawerTitle;
    private CharSequence title;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestHelper.getInstance(this);

        // Drawer menu
        activitiesList = getResources().getStringArray(R.array.activities_array);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.nav_drawer);

        // Screen Title
        title = drawerTitle = getTitle();

        // Set up the menu with screen items
        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, activitiesList));

        // Set onClick Listeners for each item in the menu
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // ActionBar is the part where the title is, enable to allow changes
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final SharedPreferences preferences = getSharedPreferences(getString(R.string.preferences_file_key), Context.MODE_PRIVATE);

        if (!preferences.contains(getString(R.string.user_id_key))) {
            User.createUser(new JsonRequest.CompletionHandler<User>() {
                @Override
                public void requestSucceeded(User user) {
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putInt(getString(R.string.user_id_key), user.getId());
                    editor.apply();

                    MainActivity.this.user = user;
                }

                @Override
                public void requestFailed(String errorMessage) {
                    Toast.makeText(MainActivity.this, "Failed to create user: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            this.user = new User(preferences.getInt(getString(R.string.user_id_key), -1));
        }

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
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawer.addDrawerListener(toggle);

        // Opens to Notifications (aka activitiesList[0])
        if (savedInstanceState == null) {
            selectItem(0);
        }
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
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new RequestHelpSuccessfulFragment())
                .commit();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        // Listens for which option you have selected from the menu
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position)
    {
        // Figure out which page was selected
        String page = activitiesList[position];

        // Create Fragment
        Fragment fragment;

        // Use page to create new Fragment
        switch(position){
            case 0:
                // Notifications
                fragment = new NotificationFragment();
                break;
            case 1:
                // Schedule
                fragment = new ScheduleFragment();
                break;
            case 2:
                // Map
                fragment = new MapFragment();
                break;
            case 3:
                // Request Help
                fragment = RequestHelpFragment.newInstance(user.getId());
                break;
            case 4:
                // Links
                fragment = new LinksFragment();
                break;
            case 5:
                // Sponsors
                fragment = new SponsorsFragment();
                break;
            default:
                Toast.makeText(this, "ERROR: fragment not found.", Toast.LENGTH_LONG).show();
                fragment = new DefaultFragment();
                break;
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
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
