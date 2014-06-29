package com.spxc.stockpile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.spxc.stockpile.adapter.NavDrawerListAdapter;
import com.spxc.stockpile.fragments.FragHome;
import com.spxc.stockpile.fragments.FragSettings;
import com.spxc.stockpile.helper.DatabaseHandler;
import com.spxc.stockpile.helper.Datas;
import com.spxc.stockpile.model.NavDrawerItem;
import com.spxc.stockpile.parser.JSONParser;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
 
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
 
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    
    JSONArray mJsonArray = null;
    
    String strCategory, strEmail, strName, url;
	int strCount;
    
    private static final String TAG_ID = "id";
    private static final String TAG_ITEM = "app_item";
	private static final String TAG_ICON = "app_icon";
	private static final String TAG_NAME = "app_name";
	private static final String TAG_DL = "app_dl_url";
	private static final String TAG_DESC = "app_description";
	private static final String TAG_CATEGORY = "app_category";
	private static final String TAG_UPDATED = "app_updated";
	private static final String TAG_VERSION = "app_ver";
	private static final String TAG_DEVELOPER = "app_developer";
	private static final String TAG_SIZE = "app_size";
 
	final DatabaseHandler db = new DatabaseHandler(this);
	
	boolean showCount = true; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        try {
			isExternalStoragePresent();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        mTitle = mDrawerTitle = getTitle();
 
        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
        strName = (String) mBundle.get("name");
        strEmail = (String) mBundle.get("email");
        strCount = (int) mBundle.getInt("count");

        
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
        
        // adding nav drawer items to array
        if (strCount == 0) {
        	navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1), false, "0", false, ""));
        } else {
        	navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1), showCount, ""+strCount, false, ""));
        }
        	
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1), false, "0", false, ""));
        
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1), false, "0", false, ""));
       
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), false, "0", false, ""));
        
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1), false, "0", false, ""));
        
        navDrawerItems.add(new NavDrawerItem(strName, navMenuIcons.getResourceId(5, -1), false, "0", true, "" + strEmail));
         
 
        // Recycle the typed array
        navMenuIcons.recycle();
 
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
 
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        int absId = getResources().getIdentifier("action_bar_title", "id", "android");
        
        TextView abTitle = (TextView) findViewById(absId);
        abTitle.setTextColor(Color.parseColor("#FFFFFF"));
        
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#666666")));
        //getActionBar().setTitleColor(new ColorDrawable(Color.parseColor("#666666")));
        //getActionBar().setIcon(R.drawable.ab_icon);
        
        
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }
 
    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(true);
        if (drawerOpen) {
        	getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#92C04F")));
        } else if (!drawerOpen) {
        	getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#666666")));
        }
        return super.onPrepareOptionsMenu(menu);
    }
 
    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 0:
            fragment = new FragHome();
            getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#666666")));
            showCount = false;
            break;
        case 1:
//          fragment = new PhotosFragment();
            break;
        case 2:
//            fragment = new PhotosFragment();
            break;
        case 3:
            fragment = new FragSettings();
            break;
        case 4:
//            fragment = new PagesFragment();
            break;
        case 5:
//            fragment = new WhatsHotFragment();
            break;
        case 6:
//          fragment = new WhatsHotFragment();
          break;
 
        default:
            break;
        }
 
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	public void setActionBarTitle(String title) {
		// TODO Auto-generated method stub
		getActionBar().setTitle(title);
	}

	public void setBackgroundDrawable(ColorDrawable colorDrawable) {
		// TODO Auto-generated method stub
		getActionBar().setBackgroundDrawable(colorDrawable);
	}	
	
	private boolean isExternalStoragePresent() throws FileNotFoundException {

        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        //Context context;
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
            File downloadDir = new File(Environment.getExternalStorageDirectory().getPath() + "/Stockpile/");
            downloadDir.mkdir();
            File outputFile = new File(downloadDir, "filename.jpg");
            FileOutputStream fos = new FileOutputStream(outputFile);
            
            File directory = new File(Environment.getExternalStorageDirectory().getPath() + "/Stockpile/");
            File[] files = directory.listFiles();
            for (File file : files)
            {
            	if (!file.delete())
            	{
            		System.out.println("Failed to delete "+file);
            	}
            } 
            
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        if (!((mExternalStorageAvailable) && (mExternalStorageWriteable))) {
            Toast.makeText(this, "SD card not present", Toast.LENGTH_LONG)
                    .show();

        }
        return (mExternalStorageAvailable) && (mExternalStorageWriteable);
    }	
}