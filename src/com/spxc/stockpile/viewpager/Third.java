package com.spxc.stockpile.viewpager;

import com.spxc.stockpile.LoginActivity;
import com.spxc.stockpile.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Third extends Fragment implements View.OnClickListener{
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.viewpager_layout3, null);
        
        Button btnDone = (Button)v.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
        	
        @Override
        public void onClick(View view) {		   	
        	//Intent mHelpIntent = new Intent(getActivity(), LoginActivity.class);
        	//getActivity().startActivity(mHelpIntent);
		   	getActivity().finish();
        }
        });
        
        return v;
        
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}