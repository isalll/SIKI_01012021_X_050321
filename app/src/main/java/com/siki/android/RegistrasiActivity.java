package com.siki.android;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.siki.library.JSONParser;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrasiActivity extends Activity {
	Button login,registrasi;
	private EditText nama,password,email;
	private ProgressDialog pDialog;
	int flag=0;
	JSONParser jsonParser = new JSONParser();
	//private static String url = "http://10.0.2.2/rdias/register.php";
	private static String url = "http://192.168.43.8/iris/php/register.php";
	private static final String TAG_SUCCESS = "success"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads().detectDiskWrites().detectNetwork()
		.penaltyLog().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
	
	//Go To Login.java	
		login=(Button)findViewById(R.id.btnLinkToLoginScreen);	
        login.setOnClickListener(new View.OnClickListener() 
        {		
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
				
			}
		}); 
    // Close Login.java
        
     //Get all data and log in 
    	registrasi=(Button)findViewById(R.id.btnRegister);	
    	nama=(EditText)findViewById(R.id.registerName);
    	password=(EditText)findViewById(R.id.registerPassword);
    	email=(EditText)findViewById(R.id.registerEmail);
    	
        registrasi.setOnClickListener(new View.OnClickListener() 
        {			
			@Override
			public void onClick(View view) {
				
		//Check all fields		
				if(nama.length()<10)
				{
					Toast.makeText(RegistrasiActivity.this,"Please Enter correct nama", Toast.LENGTH_LONG).show();
					return;
				}
				 if(password.length()<4)
				{				
					Toast.makeText(RegistrasiActivity.this,"Please Enter minimum 4 letters in password", Toast.LENGTH_LONG).show();
					return;
				} 
				 if(email.length()<11)
					{				
						Toast.makeText(RegistrasiActivity.this,"Please Enter correct email", Toast.LENGTH_LONG).show();
						return;
					}
		//check connectivity		
				 if(!isOnline(RegistrasiActivity.this))
				{					
					Toast.makeText(RegistrasiActivity.this,"No network connection", Toast.LENGTH_LONG).show();
					return;	
				}
		
		//from login.java		
					new loginAccess().execute();
			}
   
		//code to check online details
		private boolean isOnline(Context mContext) {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting())
	   	  {
			return true;
     		}
		    return false;
       	}
      //Close code that check online details		
	  }); 
        //Close log in 
    }


class loginAccess extends AsyncTask<String, String, String> {

	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(RegistrasiActivity.this);
		pDialog.setMessage("Sig in...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	@Override
	protected String doInBackground(String... arg0) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String nam=nama.getText().toString();
		String pwd=password.getText().toString();
		String ema=email.getText().toString();
		
		params.add(new BasicNameValuePair("nama", nam));
		params.add(new BasicNameValuePair("password", pwd));
		params.add(new BasicNameValuePair("email", ema));
		
		JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
		
		Log.d("Create Response", json.toString());
		
		try {
			int success = json.getInt(TAG_SUCCESS);
			if (success == 1) 
			 {
			  flag=0;	
			  Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
			  i.putExtra("mobile_number",nam);
			  i.putExtra("password",pwd);
			  startActivity(i);
			  finish();
			 }
			 else
			 {
				// failed to Sign in
				flag=1;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected void onPostExecute(String file_url) {
		pDialog.dismiss();
		if(flag==1)
			Toast.makeText(RegistrasiActivity.this,"Please Enter Correct informations", Toast.LENGTH_LONG).show();
		
	}
	
  }
	
}
