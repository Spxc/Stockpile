package com.spxc.stockpile.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fedorvlasov.lazylist.ImageLoader;
import com.spxc.stockpile.R;
import com.spxc.stockpile.fragments.FragList;
import com.spxc.stockpile.helper.Datas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
 
public class LazyAdapter extends BaseAdapter {
     
    private Activity activity;
    //private String[] data;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
     
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data = d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.size(); 
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
     
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_v, null);
 
        TextView name = (TextView)vi.findViewById(R.id.name);
        TextView developer = (TextView)vi.findViewById(R.id.developer);
        TextView installed = (TextView)vi.findViewById(R.id.installed);
        ImageView image = (ImageView)vi.findViewById(R.id.icon);
        ProgressBar loader = (ProgressBar)vi.findViewById(R.id.pLoader);
        
        HashMap<String, String> resto = new HashMap<String, String>();
        resto = data.get(position);
        
        name.setText(resto.get(FragList.TAG_NAME));
        developer.setText(resto.get(FragList.TAG_DEVELOPER));
        installed.setText(resto.get(FragList.TAG_INSTALLED));
        imageLoader.DisplayImage(resto.get(FragList.TAG_ICON), image);
        loader.setVisibility(View.GONE);
        return vi;
    }
}