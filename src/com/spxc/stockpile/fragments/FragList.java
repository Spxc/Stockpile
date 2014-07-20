package com.spxc.stockpile.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.spxc.stockpile.AppDetailsActivity;
import com.spxc.stockpile.R;
import com.spxc.stockpile.adapter.LazyAdapter;
import com.spxc.stockpile.adapter.SearchAdapter;
import com.spxc.stockpile.helper.DatabaseHandler;
import com.spxc.stockpile.helper.Datas;

public class FragList extends Activity { 
    
	public static final String TAG_ICON = "app_icon";
	public static final String TAG_NAME = "app_name";
	private static final String TAG_ID = "id";
	public static final String TAG_DEVELOPER = "app_developer";
	private static final String TAG_SIZE = "app_size";
	public static final String TAG_INSTALLED = "app_installed";
	private static final String TAG_PACKAGE = "app_package";
	private static final int ADD_PARTICIPANT = 123;
	
	ArrayList<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
	
    ListView list;
    LazyAdapter adapter;
    
    String strCat, strCatName, strInstalled, url;
    int intCat;
    
    final DatabaseHandler db = new DatabaseHandler(this);
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
         
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
  	  	strCat = b.getString("strCategory");
  	  	strCatName = b.getString("strCategoryName");
  	  	intCat = Integer.parseInt(strCat);

  	  	int absId = getResources().getIdentifier("action_bar_title", "id", "android");
  	  
  	  	ActionBar abs = getActionBar();
        TextView abTitle = (TextView) findViewById(absId);
        abTitle.setTextColor(Color.parseColor("#FFFFFF"));
  	  	abs.setDisplayHomeAsUpEnabled(true);
  	  	abs.setTitle(strCatName);
  	  
  	  switch (intCat) {
  	  case 1:
  		  Log.d("LOG_TAG", "APPS");
  		  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#92c04f")));
  		  break;
  		  
  	  case 2:
  		  Log.d("LOG_TAG", "MEDIA");
  		  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb474d")));
  		  break;
  		  
  	  case 3:
  		  Log.d("LOG_TAG", "TWEAKS");
  		  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7c9cb0")));
  		  break;
  		  
  	  case 4:
  		  Log.d("LOG_TAG", "THEMES");
  		  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#502150")));
  		  break;
  		  
  	  case 99:
  		  Log.d("LOG_TAG", "ERROR: SOMETHING WENT WRONG!");
  		  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#666666")));
  		  break;
  	  }
  	  
  	  getData();

    }
     
    private void getData() {
    	  List<Datas> datas = db.getAllDatas(strCat);       

      	  for (Datas d : datas) { 	
      	  
      	  boolean installed  =  appInstalledOrNot(d.getPackage());
      	
      	  if (installed){
      		  strInstalled = "Installed";
      		  Log.d("LOG_TAG", strInstalled + " " + d.getPackage());
      	  } else {
      		  strInstalled = "Free";
      		  Log.d("LOG_TAG", strInstalled + " " + d.getPackage());
      	  }
      	  Log.d("LOG_TAG", strInstalled + " " + d.getPackage());
      	
      	  HashMap<String, String> map = new HashMap<String, String>();
      	  map.put(TAG_ID, "" + d.getID());
      	  map.put(TAG_ICON, d.getIcon());
      	  map.put(TAG_NAME, d.getName());
      	  map.put(TAG_DEVELOPER, d.getDeveloper());
      	  map.put(TAG_INSTALLED, strInstalled);
      	  map.put(TAG_PACKAGE, "" + d.getPackage());
         
      	  mList.add(map);
      	  
          list = (ListView)findViewById(R.id.list);
          
          adapter = new LazyAdapter(this, mList);
          
          list.setAdapter(adapter);
          list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                                      int position, long id) {

                  Intent mLoginIntent = new Intent(FragList.this, AppDetailsActivity.class);
                  mLoginIntent.putExtra("id", mList.get(position).get(TAG_ID)); 
                  FragList.this.startActivityForResult(mLoginIntent, ADD_PARTICIPANT);
              }
          });
      	  }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }
    
	
	public void onResume(Bundle savedInstanceState){
		super.onResume();
		Log.d("LOG_TAG", "Resumed");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == ADD_PARTICIPANT && resultCode == Activity.RESULT_OK)
	    {
	    	mList.clear();
	    	adapter.notifyDataSetChanged();
	    	getData();
	    }
	}
}

