package com.spxc.stockpile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.spxc.stockpile.helper.DatabaseHandler;
import com.spxc.stockpile.helper.Datas;
import com.spxc.stockpile.library.JSONParserLogin;
import com.spxc.stockpile.parser.JSONParser;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class LoginActivity extends Activity{

	Button btnLogin, btnSignup;
	EditText inputEmail, inputPassword;
	String url;
	
	Context mContext;
	
	boolean flag = true;
	
	private ProgressDialog pDialog;
	
	JSONParserLogin jsonParser = new JSONParserLogin();
    
	private static final String LOGIN_URL = "http://www.appwhittle.com/stockpile/api/login/login_test.php";
	
    private static String ERROR_MSG_PASSWORD = "Password field empty";
    private static String ERROR_MSG_EMAIL = "Email field empty";
    private static String ERROR_MSG_USERNAMEPASS = "Incorrect Email or Password";
    
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    
    private static final String TAG_ID = "id";
    private static final String TAG_ITEM = "app_item";
	private static final String TAG_ICON = "app_icon";
	private static final String TAG_NAME = "app_name";
	private static final String TAG_DL = "app_dl_url";
	private static final String TAG_DESC = "app_description";
	private static final String TAG_CATEGORY = "app_category";
	private static final String TAG_UPDATED = "app_updated";
	private static final String TAG_VERSION = "app_ver";
	private static final String TAG_DEVELOPER = "app_developer";
	private static final String TAG_SIZE = "app_size";
	private static final String TAG_STATISTICS = "app_statistics";
	private static final String TAG_PACKAGE = "app_package";
	
	JSONArray mJsonArray = null;
	
	final DatabaseHandler db = new DatabaseHandler(this);
	SQLiteDatabase db1;
	
	int count;
	
	SharedPreferences prefs = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_login);
	    
	    ActionBar abs = getActionBar();
	    abs.hide();
	    
	    SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	    prefs = getSharedPreferences("com.spxc.stockpile", MODE_PRIVATE);

	    /*boolean firstRun = prefs.getBoolean("firstRun", true);
	    if (firstRun) {
	    	
		   	//finish();
	    } else {*/
	    	
	    boolean bLogin = SP.getBoolean("pref_save_user", false); 
	    boolean bUpdate = SP.getBoolean("pref_update_rep", true);
	    
	    if(bUpdate){
	    	new JSONParse().execute();
	    }

	    inputEmail = (EditText)findViewById(R.id.edMail);
	    inputPassword = (EditText)findViewById(R.id.edPassword);
	    
	    if (bLogin) {
	    	inputEmail.setText(prefs.getString("username", ""));
	    	inputPassword.setText(prefs.getString("password", ""));
	    	new AttemptLogin().execute();
	    } else {}
	    
	    btnLogin = (Button)findViewById(R.id.btnLogin);
	    btnLogin.setOnClickListener(new View.OnClickListener() {
        	
        @Override
        public void onClick(View view) {
        	if (TextUtils.isEmpty(inputEmail.getText())) {
        		Crouton.makeText(LoginActivity.this, ERROR_MSG_EMAIL, Style.INFO).show();
        	} else {
        		if (TextUtils.isEmpty(inputPassword.getText())) {
        			Crouton.makeText(LoginActivity.this, ERROR_MSG_PASSWORD, Style.INFO).show();
            	} else {
            		new AttemptLogin().execute();
            	}
        	}
      }
    });
	    
	    btnSignup = (Button)findViewById(R.id.btnRegistrer);
	    btnSignup.setOnClickListener(new View.OnClickListener() {
        	
        @Override
        public void onClick(View view) {
        	Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);
      }
    });
	    }
	//}
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
	       private ProgressDialog pDialog;
	      @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	             
	            pDialog = new ProgressDialog(LoginActivity.this);
	            pDialog.setMessage("Updating Catalog...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	      }
	      
	      @Override
	        protected JSONObject doInBackground(String... args) {
	        JSONParser jParser = new JSONParser();
	        // Getting JSON from URL  
	        url = "http://appwhittle.com/stockpile/api/getdata.php?app_category=99";
	        JSONObject json = jParser.getJSONFromUrl(url);
	        
	        return json;
	      }
	       @Override
	         protected void onPostExecute(JSONObject json) {
	         pDialog.dismiss();
	         try {
	            mJsonArray = json.getJSONArray(TAG_ITEM);
	            for(int i = 0; i < mJsonArray.length(); i++){
	            JSONObject c = mJsonArray.getJSONObject(i);

	            int id = c.getInt(TAG_ID);
	            String name = c.getString(TAG_NAME);
	            String icon = c.getString(TAG_ICON);
	            String developer = c.getString(TAG_DEVELOPER);
	            String description = c.getString(TAG_DESC);
	            String version = c.getString(TAG_VERSION);
	            String download = c.getString(TAG_DL);
	            String updated = c.getString(TAG_UPDATED); 
	            String size = c.getString(TAG_SIZE);
	            String category = c.getString(TAG_CATEGORY);
	            String statistics = c.getString(TAG_STATISTICS);
	            String compackage = c.getString(TAG_PACKAGE);
	            		
	            Log.d("LOG_TAG", compackage);
	            
	            db.checkIfExist(name);

	            boolean status = db.checkIfExist(name); 
	             
	            if (status == true) {
	            	db.updateDatas(id, statistics);
	            } else {
	            	db.addData(new Datas (i, name, icon, developer, description, version, download, updated, size, category, statistics, compackage));
	            	count++; 
	            } 
	            } 
	            
	            if (mJsonArray.length() < db.getDatasCount()) {
	            	db.deleteAll(); 
	            	Log.d("LOG_TAG", "S COUNT: " + db.getDatasCount());
	            	count = 0;
	            	try {
	    	            mJsonArray = json.getJSONArray(TAG_ITEM);
	    	            for(int i = 0; i < mJsonArray.length(); i++){
	    	            JSONObject c = mJsonArray.getJSONObject(i);

	    	            int id = c.getInt(TAG_ID);
	    	            String name = c.getString(TAG_NAME);
	    	            String icon = c.getString(TAG_ICON);
	    	            String developer = c.getString(TAG_DEVELOPER);
	    	            String description = c.getString(TAG_DESC);
	    	            String version = c.getString(TAG_VERSION);
	    	            String download = c.getString(TAG_DL);
	    	            String updated = c.getString(TAG_UPDATED); 
	    	            String size = c.getString(TAG_SIZE);
	    	            String category = c.getString(TAG_CATEGORY);
	    	            String statistics = c.getString(TAG_STATISTICS);
	    	            String compackage = c.getString(TAG_PACKAGE);
	    	            		
	    	            Log.d("LOG_TAG", compackage);
	    	            
	    	            db.checkIfExist(name); 

	    	            boolean status = db.checkIfExist(name); 
	    	             
	    	            if (status == true) {
	    	            	db.updateDatas(id, statistics);
	    	            } else {
	    	            	db.addData(new Datas (i, name, icon, developer, description, version, download, updated, size, category, statistics, compackage));
	    	            	count++; 
	    	            } 
	    	            }
	            		} catch (JSONException e) {
	            			e.printStackTrace();
	            		}
	            	
	            } else {
	            	
	            }
	            //db.deleteSingleItem(2);
	            Log.d("LOG_TAG", "A COUNT: " + mJsonArray.length());
	            Log.d("LOG_TAG", "S COUNT: " + db.getDatasCount());
	        } catch (JSONException e) {
	          e.printStackTrace();
	        }
	         Log.d("Catalog", "Catalog Updated");
	            Log.d("LOG_TAG", "" + count);
	       }
	    }
	
	class AttemptLogin extends AsyncTask<String, Void, String> {

        /**
        * Before starting background thread Show Progress Dialog
        * */
       boolean failure = false;
       
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(LoginActivity.this);
           pDialog.setMessage("Signing in...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(true);
           pDialog.show();
       }
       
       @Override
       protected String doInBackground(String... args) {
           // TODO Auto-generated method stub
            // Check for success tag
           int success;
           String username = inputEmail.getText().toString();
           String password = md5(inputPassword.getText().toString());
           // Building Parameters
		   List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		   params.add(new BasicNameValuePair("txtUName", username));
		   params.add(new BasicNameValuePair("txtPass", password));
		   
		   Log.d("request!", "starting");
		   // getting product details by making HTTP request
		   try {
		   JSONObject json = jsonParser.makeHttpRequest(
		          LOGIN_URL, "POST", params);
		   
		   // check your log for json response
		   Log.d("Login attempt", json.toString());

		   // json success tag
		   success = json.getInt(TAG_SUCCESS);
		   if (success == 1) {
		       Log.d("Login Successful!", json.toString());
		       Intent mLoginIntent = new Intent(LoginActivity.this, MainActivity.class);
		       mLoginIntent.putExtra("name", json.getString("message")); 
		       mLoginIntent.putExtra("email", username); 
		       mLoginIntent.putExtra("count", count); 
		   	   LoginActivity.this.startActivity(mLoginIntent);
		   	   
		   	   SharedPreferences.Editor editor = prefs.edit();
		   	   editor.putString("username", inputEmail.getText().toString());
		   	   editor.putString("password", inputPassword.getText().toString());
		   	   editor.commit();
		   	   
		   	   finish();
		       return json.getString(TAG_MESSAGE);
		   }else if (success == 0){
		       Log.d("Login Failure!", json.getString(TAG_MESSAGE));
		       return json.getString(TAG_MESSAGE); 
		   }
		   
		   } catch(Exception e) {
			   Log.d("LOG_TAG", e.toString());
			   
			   runOnUiThread(new Runnable() {
	                public void run() {
	                	Crouton.makeText(LoginActivity.this, ERROR_MSG_USERNAMEPASS, Style.ALERT).show();
	                }
	            });
		   }
          return null;  
          
       }
       /**
        * After completing background task Dismiss the progress dialog
        * **/
       protected void onPostExecute(String file_url) {
           // dismiss the dialog once product deleted
           pDialog.dismiss();
           if (file_url != null){
           }
       }   
   }
	
	private static final String md5(final String password) {
	    try {
	 
	        MessageDigest digest = java.security.MessageDigest
	                .getInstance("MD5");
	        digest.update(password.getBytes());
	        byte messageDigest[] = digest.digest();
	 
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++) {
	            String h = Integer.toHexString(0xFF & messageDigest[i]);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();
	 
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	@Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
        	Intent mHelpIntent = new Intent(LoginActivity.this, HelpActivity.class);
		   	LoginActivity.this.startActivity(mHelpIntent);
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
}
