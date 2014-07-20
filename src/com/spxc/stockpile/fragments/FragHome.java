package com.spxc.stockpile.fragments;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.spxc.stockpile.AppDetailsActivity;
import com.spxc.stockpile.R;
import com.spxc.stockpile.helper.DatabaseHandler;
import com.spxc.stockpile.helper.Datas;
 
public class FragHome extends Fragment{
     
	TextView txtInformation;
	String ver, stage, codename, reldate;
	Button btnApps, btnMedia, btnTweaks, btnThemes;
	Button btnFeaturedApp1, btnFeaturedApp2, btnFeaturedApp3;
	Button btnFeaturedTweak1, btnFeaturedTweak2, btnFeaturedTweak3;
	Fragment fragment = null;
	String strCategory, strCategoryName;
	
    public FragHome(){}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
  	  	View view = inflater.inflate(R.layout.fragment_home, container, false);
  	  	
  		btnApps = (Button) view.findViewById(R.id.btn_apps);
  		btnApps.setOnClickListener(new OnClickListener(){
  			@Override
  		    public void onClick(View v) {
  				strCategory = "1";
  				strCategoryName = "Applications";

  				Intent intent = new Intent(getActivity(), FragList.class);
  				intent.putExtra("strCategory", strCategory);
  				intent.putExtra("strCategoryName", strCategoryName);
  				startActivity(intent);
  		        } 
  		   }); 
  		
  		btnMedia = (Button) view.findViewById(R.id.btn_media);
  		btnMedia.setOnClickListener(new OnClickListener(){
  			@Override
  		    public void onClick(View v) {
  				strCategory = "2";
  				strCategoryName = "Movies & Music";
  				
  				Intent intent = new Intent(getActivity(), FragList.class);
  				intent.putExtra("strCategory", strCategory);
  				intent.putExtra("strCategoryName", strCategoryName);
  				startActivity(intent);
  		        } 
  		   }); 
  		
  		btnTweaks = (Button) view.findViewById(R.id.btn_tweaks);
  		btnTweaks.setOnClickListener(new OnClickListener(){
  			@Override
  		    public void onClick(View v) {
  				strCategory = "3";
  				strCategoryName = "Tweaks";
  				
  				Intent intent = new Intent(getActivity(), FragList.class);
  				intent.putExtra("strCategory", strCategory);
  				intent.putExtra("strCategoryName", strCategoryName);
  				startActivity(intent);
  		        } 
  		   }); 
  		
  		btnThemes = (Button) view.findViewById(R.id.btn_themes);
  		btnThemes.setOnClickListener(new OnClickListener(){
  			@Override
  		    public void onClick(View v) {
  				strCategory = "4";
  				strCategoryName = "Themes";
  				
  				Intent intent = new Intent(getActivity(), FragList.class);
  				intent.putExtra("strCategory", strCategory);
  				intent.putExtra("strCategoryName", strCategoryName);
  				startActivity(intent);
  		        } 
  		   }); 
  		
  		btnFeaturedApp1 = (Button) view.findViewById(R.id.btn_apps1);
  		btnFeaturedApp2 = (Button) view.findViewById(R.id.btn_apps2);
  		btnFeaturedApp3 = (Button) view.findViewById(R.id.btn_apps3);
  		
  		btnFeaturedTweak1 = (Button) view.findViewById(R.id.btn_tweaks1);
  		btnFeaturedTweak2 = (Button) view.findViewById(R.id.btn_tweaks2);
  		btnFeaturedTweak3 = (Button) view.findViewById(R.id.btn_tweaks3);
  		
  		btnFeaturedApp1.setText("None");
  		btnFeaturedApp2.setText("None");
  		btnFeaturedApp3.setText("None");
  		
  		btnFeaturedTweak1.setText("None");
  		btnFeaturedTweak2.setText("None");
  		btnFeaturedTweak3.setText("None");
  		
  		DatabaseHandler db = new DatabaseHandler(getActivity().getBaseContext());
        
        List<Datas> datas = db.getFeaturedDatas();       
        int count = 0;
        int count2 = 0;
        
  	  	for (final Datas d : datas) {
  	  		
  	  		count++;
  	  		
  	  		switch (count) {
  	  			case 1:
  	  				new DownloadImageTask((Button)view.findViewById(R.id.btn_apps1))
  	  				.execute(d.getIcon());
  	  				btnFeaturedApp1.setText(d.getName());
  	  				btnFeaturedApp1.setOnClickListener(new OnClickListener(){
  	  	  			@Override
  	    		    public void onClick(View v) {
  	    				String id = String.valueOf(d.getID());
  	    				Intent intent = new Intent(getActivity(), AppDetailsActivity.class);
  	    				intent.putExtra("id", id);
  	    				startActivity(intent);
  	  	  				}
  	    		    }); 
  	  				Log.d("LOG_TAG", "COUNT: " + count);
  	  				break;
  	  			
  	  			case 2:
  	  				new DownloadImageTask((Button)view.findViewById(R.id.btn_apps2))
	  				.execute(d.getIcon());
  	  				btnFeaturedApp2.setText(d.getName());
  	  				btnFeaturedApp2.setOnClickListener(new OnClickListener(){
  	  	  			@Override
  	    		    public void onClick(View v) {
  	    				String id = String.valueOf(d.getID());
  	    				Intent intent = new Intent(getActivity(), AppDetailsActivity.class);
  	    				intent.putExtra("id", id);
  	    				startActivity(intent);
  	  	  				}
  	    		    }); 
  	  				Log.d("LOG_TAG", "COUNT: " + count);
  	  				break;
  	  			
  	  			case 3:
  	  				new DownloadImageTask((Button)view.findViewById(R.id.btn_apps3))
	  				.execute(d.getIcon());
  	  				btnFeaturedApp3.setText(d.getName());
  	  				btnFeaturedApp3.setOnClickListener(new OnClickListener(){
  	  	  			@Override
  	    		    public void onClick(View v) {
  	    				String id = String.valueOf(d.getID());
  	    				Intent intent = new Intent(getActivity(), AppDetailsActivity.class);
  	    				intent.putExtra("id", id);
  	    				startActivity(intent);
  	  	  				}
  	    		    }); 
  	  				Log.d("LOG_TAG", "COUNT: " + count);
  	  				break;
  	  		}
  	  	}
  	  	
  	  	List<Datas> datas2 = db.getFeaturedTweaksDatas("3");       

	  	for (final Datas d2 : datas2) {
	  		count2++;
  	  		
  	  		switch (count2) {
  	  			case 1:
  	  				new DownloadImageTask((Button)view.findViewById(R.id.btn_tweaks1))
	  				.execute(d2.getIcon());
  	  				btnFeaturedTweak1.setText(d2.getName());
  	  				btnFeaturedTweak1.setOnClickListener(new OnClickListener(){
  	  	  			@Override
  	    		    public void onClick(View v) {
  	    				String id = String.valueOf(d2.getID());
  	    				Intent intent = new Intent(getActivity(), AppDetailsActivity.class);
  	    				intent.putExtra("id", id);
  	    				startActivity(intent);
  	  	  				}
  	    		    }); 
  	  				Log.d("LOG_TAG", "COUNT: " + count2);
  	  				break;
  	  			
  	  			case 2:
  	  				new DownloadImageTask((Button)view.findViewById(R.id.btn_tweaks2))
	  				.execute(d2.getIcon());
  	  				btnFeaturedTweak2.setText(d2.getName());
  	  				btnFeaturedTweak2.setOnClickListener(new OnClickListener(){
  	  	  			@Override
  	    		    public void onClick(View v) {
  	    				String id = String.valueOf(d2.getID());
  	    				Intent intent = new Intent(getActivity(), AppDetailsActivity.class);
  	    				intent.putExtra("id", id);
  	    				startActivity(intent);
  	  	  				}
  	    		    }); 
  	  				Log.d("LOG_TAG", "COUNT: " + count2);
  	  				break;
  	  			
  	  			case 3:
  	  				new DownloadImageTask((Button)view.findViewById(R.id.btn_tweaks3))
	  				.execute(d2.getIcon());
  	  				btnFeaturedTweak3.setText(d2.getName());
  	  				btnFeaturedTweak3.setOnClickListener(new OnClickListener(){
  	  	  			@Override
  	    		    public void onClick(View v) {
  	    				String id = String.valueOf(d2.getID());
  	    				Intent intent = new Intent(getActivity(), AppDetailsActivity.class);
  	    				intent.putExtra("id", id);
  	    				startActivity(intent);
  	  	  				}
  	    		    }); 
  	  				Log.d("LOG_TAG", "COUNT: " + count2);
  	  				break;
  	  		}
	  	}
  		 
  		   return view;
    }
    
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		  Button imgIcon;

		  public DownloadImageTask(Button button) {
		      this.imgIcon = button;
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
		      //imgIcon.setImageBitmap(result);
			  try {
				  Drawable mDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(result, 130, 130, true));
				  imgIcon.setCompoundDrawablesWithIntrinsicBounds(null, mDrawable, null, null);
			  } catch (Exception e) {
				  //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
				  Log.d("LOG_TAG", "BAD IMAGE SOURCE!!!!!");
			  }
		      //imgIcon.setCompoundDrawablesWithIntrinsicBounds(0, mDrawable, 0, 0);
		      
		      //loader.setVisibility(View.GONE);
		  }
		}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
    
    public void onStart() {
    	super.onStart();
    	
    	/*List<Datas> datas = db.getAllDatas("1");       

  	  	for (Datas d : datas) {
  	  		Log.d("LOG_TAG", d.getName());
  	  	}*/
    	
    	ver = getResources().getString(R.string.str_ver);
    	stage = getResources().getString(R.string.str_stage);
    	codename = getResources().getString(R.string.str_code_name);
    	reldate = getResources().getString(R.string.str_rel_date);
    	
    	txtInformation = (TextView) getView().findViewById(R.id.id_app);
    	txtInformation.setText("Ver. " + ver + "(" + stage + "), " + codename +"\n Rel. Date: " + reldate);
    }
}