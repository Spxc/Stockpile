package com.spxc.stockpile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.spxc.stockpile.fragments.FragList;
import com.spxc.stockpile.helper.DatabaseHandler;
import com.spxc.stockpile.helper.Datas;
import com.spxc.stockpile.parser.JSONParser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AppDetailsActivity extends Activity{

	String url, strId, strInstalled, strPackageName;
	Button btnInstall, btnOpen, btnUninstall;
	TextView txtTitle, txtDeveloper, txtSize, txtCount;
	ProgressBar loader, pBar;
	ImageView imgIcon;
	
	private ProgressDialog pDialog;
	
	public static final int progress_bar_type = 0; 
	
	String file_url = "";
	
	final DatabaseHandler db = new DatabaseHandler(this);
	
	@Override
	  public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  
		  setContentView(R.layout.activity_app);
		  
		  Intent mIntent = getIntent();
	      Bundle mBundle = mIntent.getExtras();
	      
	      strId = (String) mBundle.get("id");
	      int id = Integer.parseInt(strId);
	      
	      int absId = getResources().getIdentifier("action_bar_title", "id", "android");
		  
		  ActionBar abs = getActionBar();
	      TextView abTitle = (TextView) findViewById(absId);
	      abTitle.setTextColor(Color.parseColor("#FFFFFF"));
		  abs.setDisplayHomeAsUpEnabled(true);
	      
	      btnInstall = (Button) findViewById(R.id.btnInstall); 
	      btnOpen = (Button) findViewById(R.id.btnOpen);
	      btnUninstall = (Button) findViewById(R.id.btnUninstall);
	      
	      loader = (ProgressBar) findViewById(R.id.pLoader);
	      pBar = (ProgressBar) findViewById(R.id.progressBar);
	      
	      loader.setVisibility(View.VISIBLE);
	      pBar.setVisibility(View.GONE);
	      
	      Datas data = db.getData(id);
	      
	      int category = Integer.parseInt(data.getCategory());
	      
	      switch (category) {
		  case 1:
			  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#92c04f")));
			  abs.setTitle("Applications");
			  break;
			  
		  case 2:
			  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb474d")));
			  abs.setTitle("Movies & Music");
			  break;
			  
		  case 3:
			  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7c9cb0")));
			  abs.setTitle("Tweaks");
			  break;
			  
		  case 4:
			  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#502150")));
			  abs.setTitle("Themes");
			  break;
			  
		  case 99:
			  Log.d("LOG_TAG", "ERROR: SOMETHING WENT WRONG!");
			  abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#666666")));
			  break;
		  }
	      
	      txtTitle = (TextView) findViewById(R.id.txtAppName);
	      txtDeveloper = (TextView) findViewById(R.id.txtDeveloper);
	      txtCount = (TextView) findViewById(R.id.txtCount);
          imgIcon = (ImageView) findViewById(R.id.imgIcon);
          
          txtTitle.setText(data.getName());
          txtDeveloper.setText(data.getDeveloper());
          
          file_url = data.getDownload(); 
          
          boolean installed = appInstalledOrNot(data.getPackage());
          strPackageName = data.getPackage();
          if (installed){
        	  btnOpen.setVisibility(View.VISIBLE);
		      btnUninstall.setVisibility(View.VISIBLE);
          } else {
        	  btnOpen.setVisibility(View.GONE);
    	      btnUninstall.setVisibility(View.GONE);
          }
	      
          new DownloadImageTask((ImageView) findViewById(R.id.imgIcon))
          .execute(data.getIcon());
          
          btnInstall.setOnClickListener(new OnClickListener() {

        	    public void onClick(View v) {
        	    	new DownloadFileFromURL().execute(file_url);
        	    }
        	 });
          
          btnOpen.setOnClickListener(new OnClickListener() {

      	    public void onClick(View v) {
      	    	Intent LaunchIntent = getPackageManager()
      	                .getLaunchIntentForPackage(strPackageName);
      	            startActivity(LaunchIntent);

      	    }
      	 });
          
          btnUninstall.setOnClickListener(new OnClickListener() {

      	    public void onClick(View v) {
      	        // TODO Auto-generated method stub
      	    	Intent intent = new Intent(Intent.ACTION_DELETE); 
      	    	intent.setData(Uri.parse("package:" + strPackageName));
      	    	startActivityForResult(intent, 0);
      	    }
      	 });
	  }
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case progress_bar_type: // we set this to 0
            /*pDialog = new ProgressDialog(this);
            pDialog.setMessage("Downloading file. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            //pDialog.show();*/
            pBar.setVisibility(View.VISIBLE);
            return pDialog;
        default:
            return null;
        }
    }
	
	class DownloadFileFromURL extends AsyncTask<String, String, String> {
		 
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showDialog(progress_bar_type);
        }
 
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
 
                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
 
                // Output stream
                Log.d("LOG_TAG", "PKG: " + strPackageName);
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/Stockpile/" + strPackageName + ".apk");
 
                byte data[] = new byte[1024];
 
                long total = 0;
 
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    int intCount = (int) (total*100/lenghtOfFile);
 
                    // writing data to file
                    output.write(data, 0, count);
                    Log.d("PROGRESS", "PROGRESS: " + intCount);
                    //txtCount.setText(intCount + "");
                }
 
                // flushing output
                output.flush();
 
                // closing streams
                output.close();
                input.close();
 
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
 
            return null;
        }
 
        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            //pDialog.setProgress(Integer.parseInt(progress[0]));
            pBar.setProgress(Integer.parseInt(progress[0]));
       }
 
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //dismissDialog(progress_bar_type);
 
            // Displaying downloaded image into image view 
            // Reading image path from sdcard
            String packagePath = Environment.getExternalStorageDirectory().toString() + "/Stockpile/" + strPackageName + ".apk";
            
            Intent promptInstall = new Intent(Intent.ACTION_VIEW);
            promptInstall.setDataAndType(Uri.fromFile(new File(packagePath)), "application/vnd.android.package-archive");
            startActivityForResult(promptInstall, 0);
            pBar.setProgress(0);
            pBar.setVisibility(View.GONE);

        }
 
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    boolean installed = appInstalledOrNot(strPackageName);
        if (installed){
      	  btnOpen.setVisibility(View.VISIBLE);
		      btnUninstall.setVisibility(View.VISIBLE);
        } else {
      	  btnOpen.setVisibility(View.GONE);
  	      btnUninstall.setVisibility(View.GONE);
        }
	}
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case android.R.id.home: 
	                // app icon in action bar clicked; go home
	            	setResult(Activity.RESULT_OK);
	                finish();
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		  ImageView imgIcon;

		  public DownloadImageTask(ImageView bmImage) {
		      this.imgIcon = bmImage;
		  } 

		  protected Bitmap doInBackground(String... urls) {
		      String urldisplay = urls[0];
		      Bitmap mIcon11 = null;
		      try {
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in);
		      } catch (Exception e) {
		          Log.e("Error", e.getMessage());
		          e.printStackTrace();
		      }
		      return mIcon11;
		  }

		  protected void onPostExecute(Bitmap result) {
		      imgIcon.setImageBitmap(result);
		      loader.setVisibility(View.GONE);
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
	
}
