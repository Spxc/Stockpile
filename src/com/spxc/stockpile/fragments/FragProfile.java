package com.spxc.stockpile.fragments;


import com.spxc.stockpile.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class FragProfile extends Fragment {
     
    public FragProfile(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
          
        return rootView;
    }
}