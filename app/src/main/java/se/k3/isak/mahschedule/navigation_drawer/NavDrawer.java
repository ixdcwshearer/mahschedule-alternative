package se.k3.isak.mahschedule.navigation_drawer;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import se.k3.isak.mahschedule.fragments.ScheduleSettings;
import se.k3.isak.mahschedule.fragments.SettingsFragment;
import se.k3.isak.mahschedule.helpers.FragmentHelper;
import se.k3.isak.mahschedule.R;

/**
 * Created by isak on 2015-04-02.
 */
public class NavDrawer implements ListView.OnItemClickListener {

    Activity activity;
    FragmentHelper fragmentHelper;
    Toolbar toolbar;

    String[] drawerItems;
    DrawerLayout drawerLayout;
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;

    public boolean isDrawerOpened = false;

    public NavDrawer(final Activity activity, FragmentHelper fragmentHelper, Toolbar toolbar) {
       this.activity = activity;
       this.fragmentHelper = fragmentHelper;
       this.toolbar = toolbar;

       drawerItems = new String[2];
       drawerItems[0] = activity.getResources().getString(R.string.manage_schedules);
       drawerItems[1] = activity.getResources().getString(R.string.settings);

       drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
       drawerList = (ListView) activity.findViewById(R.id.drawer_list);
       drawerList.setAdapter(new ArrayAdapter<String>(activity, R.layout.drawer_list_item, drawerItems));
       drawerList.setOnItemClickListener(this);

       drawerToggle = (new ActionBarDrawerToggle(activity, drawerLayout, this.toolbar, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                activity.invalidateOptionsMenu();
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                activity.invalidateOptionsMenu();
                isDrawerOpened = false;
            }
        });

        drawerLayout.setDrawerListener(drawerToggle);
        ((ActionBarActivity)activity).getSupportActionBar().setHomeButtonEnabled(true);
        drawerToggle.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        String tag = drawerItems[position];

        if(!fragmentHelper.isFragmentVisible(tag)) {
            if(position == 0) {
                fragmentHelper.addFragment(new ScheduleSettings(), tag, true);
            }
            if(position == 1) {
                fragmentHelper.addFragment(new SettingsFragment(), tag, true);
            }
        }

        // drawerList.setItemChecked(position, true); implement later with Selector, but don't forget Ripple!
        closeDrawer();
    }

    public void closeDrawer() {
        drawerLayout.closeDrawer(drawerList);
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return this.drawerToggle;
    }
}
