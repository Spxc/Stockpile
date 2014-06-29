package com.spxc.stockpile.fragments;


import com.spxc.stockpile.R;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class FragSettings extends PreferenceFragment {
     
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	  
	  // Load the preferences from an XML resource
	        addPreferencesFromResource(R.xml.preferences);
	 }

}