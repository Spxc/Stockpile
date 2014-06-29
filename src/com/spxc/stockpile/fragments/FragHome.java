package com.spxc.stockpile.fragments;


import com.spxc.stockpile.MainActivity;
import com.spxc.stockpile.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
 
public class FragHome extends Fragment{
     
	TextView txtInformation;
	String ver, stage, codename, reldate;
	Button btnApps, btnMedia, btnTweaks, btnThemes;
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
  				
  				/*fragment = new FragList();
  				FragmentManager fm=getFragmentManager();
  				android.app.FragmentTransaction ft=fm.beginTransaction();
  				Bundle args = new Bundle();
  				args.putString("strCategory", strCategory);
  				args.putString("strCategoryName", strCategoryName);
  				fragment.setArguments(args);
  				ft.replace(R.id.frame_container, fragment);
  				ft.commit();*/
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
  				
  				/*fragment = new FragList();
  				FragmentManager fm=getFragmentManager();
  				android.app.FragmentTransaction ft=fm.beginTransaction();
  				Bundle args = new Bundle();
  				args.putString("strCategory", strCategory);
  				args.putString("strCategoryName", strCategoryName);
  				fragment.setArguments(args);
  				ft.replace(R.id.frame_container, fragment);
  				ft.commit();*/
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
  				
  				/*fragment = new FragList();
  				FragmentManager fm=getFragmentManager();
  				android.app.FragmentTransaction ft=fm.beginTransaction();
  				Bundle args = new Bundle();
  				args.putString("strCategory", strCategory);
  				args.putString("strCategoryName", strCategoryName);
  				fragment.setArguments(args);
  				ft.replace(R.id.frame_container, fragment);
  				ft.commit();*/
  		        } 
  		   }); 
  		   return view;
    }
    
    public void onStart() {
    	super.onStart();
    	
    	ver = getResources().getString(R.string.str_ver);
    	stage = getResources().getString(R.string.str_stage);
    	codename = getResources().getString(R.string.str_code_name);
    	reldate = getResources().getString(R.string.str_rel_date);
    	
    	txtInformation = (TextView) getView().findViewById(R.id.id_app);
    	txtInformation.setText("Ver. " + ver + "(" + stage + "), " + codename +"\n Rel. Date: " + reldate);
    }
}