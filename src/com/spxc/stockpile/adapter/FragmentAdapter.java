package com.spxc.stockpile.adapter;

import com.spxc.stockpile.viewpager.First;
import com.spxc.stockpile.viewpager.Second;
import com.spxc.stockpile.viewpager.Third;
import com.viewpagerindicator.IconPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter{

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getIconResId(int index) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Fragment getItem(int position) 
    {
        // TODO Auto-generated method stub
        Fragment fragment = new First();
        switch(position){
        case 0:
            fragment = new First();
            break;
        case 1:
            fragment = new Second();
            break;
        case 2:
            fragment = new Third();
            break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }
    
    @Override
    public CharSequence getPageTitle(int position){
        String title = "";
        switch(position){
        case 0:
            title = "First";
            break;
        case 1:
            title = "Second";
            break;
        case 2:
            title = "Third";
            break;
        }
        return title;
    }

}