package com.spxc.stockpile.fragments;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.spxc.stockpile.LoginActivity;
import com.spxc.stockpile.R;
import com.spxc.stockpile.helper.Datas;
import com.spxc.stockpile.parser.JSONParser;
 
public class FragProfile extends Fragment {
	
	TextView txtName, txtDeveloper, txtCreated, txtEmail;
	
	String url;
	
	SharedPreferences prefs = null;
	
	JSONArray mJsonArray = null;
	
	ListView mListView;
	
	private ArrayAdapter<String> listAdapter ;
	
    public FragProfile(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        
        new JSONParse().execute();
        
        mListView = (ListView) rootView.findViewById(R.id.listView1);   
        
        String[] repos = new String[] { "Not supported", "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" , "Placeholder" };
        
        ArrayList<String> planetList = new ArrayList<String>();  
        planetList.addAll( Arrays.asList(repos) );  
        
        // Create ArrayAdapter using the planet list.  
        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow, planetList); 
        mListView.setAdapter( listAdapter ); 
        
        return rootView;
    }
        
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
	       private ProgressDialog pDialog; 
	      @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	      }
	      
	      @Override
	        protected JSONObject doInBackground(String... args) {
	        JSONParser jParser = new JSONParser();
	        // Getting JSON from URL  
	        prefs = getActivity().getSharedPreferences("com.spxc.stockpile", Context.MODE_PRIVATE);
	        url = "http://appwhittle.com/stockpile/api/getdata_profile.php?email=" + prefs.getString("username", "");
	        JSONObject json = jParser.getJSONFromUrl(url); 
	        
	        return json;
	      }
	       @Override
	         protected void onPostExecute(JSONObject json) {
	         try {
	            mJsonArray = json.getJSONArray("app_item");
	            for(int i = 0; i < mJsonArray.length(); i++){
	            JSONObject c = mJsonArray.getJSONObject(i);

	            int id = c.getInt("uid");
	            String fname = c.getString("firstname");
	            String lname = c.getString("lastname");
	            String developer = c.getString("username");
	            String email = c.getString("email");
	            String created = c.getString("created_at");
	            		
	            new DownloadImageTask((CircularImageView) getActivity().findViewById(R.id.imgProfile))
	            .execute("http://lovemeow.com/wp-content/uploads/2013/06/031D8D7F-AC33-4117-B9B0-903957FAF506.jpg");
	            
	            txtName = (TextView)getActivity().findViewById(R.id.txtFullName);
	            txtDeveloper = (TextView)getActivity().findViewById(R.id.txtDeveloperAcc);
	            txtEmail = (TextView)getActivity().findViewById(R.id.txtEmail);
	            //txtCreated = (TextView)getActivity().findViewById(R.id.txtCreated);
	            
	            txtName.setText(fname + " " + lname);
	            txtDeveloper.setText(developer);
	            txtEmail.setText(email);
	            
	            }
	        } catch (JSONException e) {
	          e.printStackTrace();
	        }
	       }
	    }
    
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    	CircularImageView imgIcon;

		  public DownloadImageTask(CircularImageView bmImage) {
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
			  imgIcon = (CircularImageView)getActivity().findViewById(R.id.imgProfile);
			  imgIcon.setBorderWidth(2);
			  imgIcon.addShadow();
		      imgIcon.setImageBitmap(result);
		  }
		}
}