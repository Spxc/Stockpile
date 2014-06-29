package com.spxc.stockpile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.spxc.stockpile.LoginActivity.AttemptLogin;
import com.spxc.stockpile.library.JSONParserLogin;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends Activity implements OnClickListener{

	private EditText edFirstName, edLastName, edUsername, edEmail, edPassword, edPasswordRetype;
	private Button  mRegister;
	
	private ProgressDialog pDialog;

	JSONParserLogin jsonParser = new JSONParserLogin();
	
	private static final String LOGIN_URL = "http://www.appwhittle.com/stockpile/api/login/registrer.php";
    
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_signup);
	    
	    int absId = getResources().getIdentifier("action_bar_title", "id", "android");
	    
	    ActionBar abs = getActionBar();
	    TextView abTitle = (TextView) findViewById(absId);
	    abTitle.setTextColor(Color.parseColor("#FFFFFF"));
	    
	    abs.setTitle("Registrer Account Information");
	    abs.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#92c04f")));
	    abs.setDisplayHomeAsUpEnabled(true);
	
	    edFirstName = (EditText)findViewById(R.id.edFirstName);
        edLastName = (EditText)findViewById(R.id.edLastName);
        edUsername = (EditText)findViewById(R.id.edUsername);
        edEmail = (EditText)findViewById(R.id.edEmail);
        edPassword = (EditText)findViewById(R.id.edPassword);
        edPasswordRetype = (EditText)findViewById(R.id.edPasswordRetype);
        
        mRegister = (Button)findViewById(R.id.btnRegistrer);
        mRegister.setOnClickListener(new View.OnClickListener() {
        	
        @Override
        public void onClick(View view) {
        	if (TextUtils.isEmpty(edFirstName.getText())) {
        		Crouton.makeText(SignUpActivity.this, "First name needs to be filled in!", Style.INFO).show();
        	} else {
        		if (TextUtils.isEmpty(edLastName.getText())) {
        			Crouton.makeText(SignUpActivity.this, "Last name needs to be filled in!", Style.INFO).show();
            	} else {
            		if (TextUtils.isEmpty(edUsername.getText())) {
            			Crouton.makeText(SignUpActivity.this, "You need to choose a username!", Style.INFO).show();
                	} else {
                		if (TextUtils.isEmpty(edEmail.getText())) {
                			Crouton.makeText(SignUpActivity.this, "Email field is empty", Style.INFO).show();
                    	} else {
                    		if (TextUtils.isEmpty(edPassword.getText())) {
                    			Crouton.makeText(SignUpActivity.this, "You need to choose a password", Style.INFO).show();
                        	} else {
                        		if (TextUtils.isEmpty(edPasswordRetype.getText())) {
                        			Crouton.makeText(SignUpActivity.this, "You need to retype your password!", Style.INFO).show();
                            	} else {
                            		if (edPassword.getText().toString().equals(edPasswordRetype.getText().toString())) {
                            			new CreateUser().execute();
                            		} else {
                            			Crouton.makeText(SignUpActivity.this, "Your passwords did't match!", Style.INFO).show();
                            		}
                            	}
                        	}
                    	}
                	}
            	}
        	}
        	
        	
        	
      }
    });
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	class CreateUser extends AsyncTask<String, Void, String> {

        /**
        * Before starting background thread Show Progress Dialog
        * */
       boolean failure = false;
       
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(SignUpActivity.this);
           pDialog.setMessage("Creating account...");
           pDialog.setIndeterminate(false);
           pDialog.setCancelable(true);
           pDialog.show();
       }
       
       @Override
       protected String doInBackground(String... args) {
           // TODO Auto-generated method stub
            // Check for success tag
           int success;
           String firstname = edFirstName.getText().toString();
           String lastname = edLastName.getText().toString();
           String username = edUsername.getText().toString();
           String email = edEmail.getText().toString();
           String password = md5(edPassword.getText().toString());
           String uuid = UUID.randomUUID().toString();
           try {
               // Building Parameters
               List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
               params.add(new BasicNameValuePair("txtFirst", firstname));
               params.add(new BasicNameValuePair("txtLast", lastname));
               params.add(new BasicNameValuePair("txtUname", username));
               params.add(new BasicNameValuePair("txtEmail", email));
               params.add(new BasicNameValuePair("txtPass", password));
               params.add(new BasicNameValuePair("txtUID", uuid));

               Log.d("request!", "starting");
               
               //Posting user data to script 
               JSONObject json = jsonParser.makeHttpRequest(
                      LOGIN_URL, "POST", params);

               // full json response
               Log.d("Login attempt", json.toString());

               // json success element
               success = json.getInt(TAG_SUCCESS);
               if (success == 1) {
                   Log.d("User Created!", json.toString());                  
                   finish();
                   return json.getString(TAG_MESSAGE);
               }else{
                   Log.d("Creation Failure!", json.getString(TAG_MESSAGE));
                   return json.getString(TAG_MESSAGE);
                   
               }
           } catch (JSONException e) {
               e.printStackTrace();
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
        	   Crouton.makeText(SignUpActivity.this, file_url, Style.CONFIRM).show();
        	   //Toast.makeText(SignUpActivity.this, file_url, Toast.LENGTH_LONG).show();
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
