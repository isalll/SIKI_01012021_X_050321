package com.siki.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.siki.android.utils.PopupUtil;
import com.siki.library.JSONParser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.siki.android.sqlite.adapter.TestAdapter;
import com.siki.android.sqlite.db.UserRepo;
import com.siki.android.sqlite.db.User;
import android.telephony.TelephonyManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MultipartBody;
import okhttp3.FormBody;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import java.security.AccessController.getContext;


public class LoginActivity extends Activity {
	Button login, signin;
	private EditText username, password;
	private ProgressDialog pDialog;
	int flag = 0;
	JSONParser jsonParser = new JSONParser();
	private static String url = Globals.url_login;
	private static String url_isandroidlogin = Globals.url_androidlogin;

	//private static String url = "http://boemikawani.com/iris/php/login.php";
	//private static String url = "http://10.0.2.2/iris/php/login.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ISLOGIN = "isandroidlogin";
	private static final String TAG_LEVEL = "level";
	private static final String TAG_ID = "id";
	private int _User_Id=1;
	private int isandroidlogin=0;

	JSONArray loginJSON = null;
	private OkHttpClient mOkHttpClient = new OkHttpClient();
	public static final String TAG = "LoginActivity";

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		signin = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		signin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), RegistrasiActivity.class);
				startActivity(i);

			}
		});

		//Get all data and log in
		login = (Button) findViewById(R.id.btnLogin);
		username = (EditText) findViewById(R.id.loginUser);
		password = (EditText) findViewById(R.id.loginPassword);

		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//Check all fields
				if (username.length() < 0) {
					Toast.makeText(getApplicationContext(), "Please Enter usename", Toast.LENGTH_LONG).show();
					return;
				}
				if (password.length() < 1) {
					Toast.makeText(getApplicationContext(), "Please Enter correct password", Toast.LENGTH_LONG).show();
					return;
				}
				new loginAccess().execute();
			}

			//code to check online details
			private boolean isOnline(Context mContext) {
				ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = cm.getActiveNetworkInfo();
				if (netInfo != null && netInfo.isConnectedOrConnecting()) {
					return true;
				}
				return false;
			}
			//Close code that check online details
		});
		//Close log in
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
				.setName("Login Page") // TODO: Define a title for the content shown.
				// TODO: Make sure this auto-generated URL is correct.
				.setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
				.build();
		return new Action.Builder(Action.TYPE_VIEW)
				.setObject(object)
				.setActionStatus(Action.STATUS_TYPE_COMPLETED)
				.build();
	}

	@Override
	public void onStart() {
			super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		AppIndex.AppIndexApi.start(client, getIndexApiAction());

		//Cek Initial Database Pertama kali
		TestAdapter mDbHelper = new TestAdapter(getBaseContext());
		mDbHelper.createDatabase();
		mDbHelper.open();
		mDbHelper.getTestData();

		mDbHelper.close();

		/*
		TelephonyManager  tm=(TelephonyManager)getSystemService(getBaseContext().TELEPHONY_SERVICE);
		String IMEINumber=tm.getDeviceId();
		String subscriberID=tm.getDeviceId();
		String SIMSerialNumber=tm.getSimSerialNumber();
		String networkCountryISO=tm.getNetworkCountryIso();
		String SIMCountryISO=tm.getSimCountryIso();
		String softwareVersion=tm.getDeviceSoftwareVersion();
		String voiceMailNumber=tm.getVoiceMailNumber();
		String PhoneNumber=tm.getLine1Number();

		//Get the phone type
		String strphoneType="";

		int phoneType=tm.getPhoneType();

		switch (phoneType)
		{
			case (TelephonyManager.PHONE_TYPE_CDMA):
				strphoneType="CDMA";
				break;
			case (TelephonyManager.PHONE_TYPE_GSM):
				strphoneType="GSM";
				break;
			case (TelephonyManager.PHONE_TYPE_NONE):
				strphoneType="NONE";
				break;
		}

		//getting information if phone is in roaming
		boolean isRoaming=tm.isNetworkRoaming();

		String info="Phone Details:\n";
		info+="\n IMEI Number:"+IMEINumber;
		info+="\n SubscriberID:"+subscriberID;
		info+="\n Sim Serial Number:"+SIMSerialNumber;
		info+="\n Network Country ISO:"+networkCountryISO;
		info+="\n SIM Country ISO:"+SIMCountryISO;
		info+="\n Software Version:"+softwareVersion;
		info+="\n Voice Mail Number:"+voiceMailNumber;
		info+="\n Phone Network Type:"+strphoneType;
		info+="\n In Roaming? :"+isRoaming;
		info+="\n Line Phone Number:"+PhoneNumber;

		Globals.phoneinfo= info;

*/

		//Cek isandroidlogin=0 -> belum login atau isandroidlogin=1 -> sudah login
		_User_Id = 1;
		UserRepo repo = new UserRepo(this);
		User user = new User();
		user = repo.getUserById(_User_Id);

		Globals.android_user = user.username; //blank(belum login) atau 090022591 (sudah login)
		isandroidlogin = user.isandroidlogin;

		if (isandroidlogin == 1) {
				//Jika sudah pernah login maka langsung masuk ke MainActivity (dashboard)
				flag = 0;
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
				finish();
		} else {
				// nothing todo
				flag = 1;
		}
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
	}


	class loginAccess extends AsyncTask<String, String, String> {


		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LoginActivity.this);
			pDialog.setMessage("Login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			if(isNetworkAvailable() == true){

				@SuppressLint("WrongThread") final String user = username.getText().toString();
				final String pwd = password.getText().toString();


				url = Globals.base_host_bigdata   + "login" ;
				if(url == null) {
					//return;
				}

				Log.d(TAG, "url:" + url);
				RequestBody formBody = new FormBody.Builder()
				                      .add("username", user)
						              .add("password", pwd)
				                      .build();

				Request request = new Request.Builder()
						.url(url)
						.post(formBody)
						.build();

				mOkHttpClient.newCall(request).enqueue(new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						PopupUtil.dismissDialog();
						//PopupUtil.showMsg(getActivity(), "Error load data", PopupUtil.SHORT);
					}

					@Override
					public void onResponse(Call call, Response response) throws IOException {
						PopupUtil.dismissDialog();

						String body = response.body().string();
						Log.d(TAG, "response:" + body);

						try {
							JSONObject json = new JSONObject(body);
							int success = json.getInt("success");
							String message = json.getString("message");
							final String level = json.getString("level");


							Log.d(TAG, "sukses:" + success);

							if (success == 1) {
								//Globals.level = json.getString(TAG_LEVEL);  //test hapus
								//Globals.id = json.getString(TAG_ID);



								Globals.user_on = user;
								Globals.level = level;
								Globals.fragment = "";
								Globals.kode_berita = "";
								Globals.dash_graph = "";

								flag = 0;


								Intent i = new Intent(getApplicationContext(), MainActivity.class);
								//Intent i = new Intent(getApplicationContext(), PdActivity.class);
								i.putExtra("username", user);
								i.putExtra("password", pwd);

								startActivity(i);


								_User_Id = 1;
								UserRepo repo = new UserRepo(getApplicationContext());
								User userx = new User();
								userx = repo.getUserById(_User_Id);

								Globals.android_user = user; // username=blank pada tabel diganti jadi 090022591 (sudah login)
								isandroidlogin = 1; //update isandroidlogin=0 menjadi isandroidlogin=1
								repo.update(userx,isandroidlogin);

								Log.d(TAG, "flag:" + flag);

								runOnUiThread(new Runnable() {
									@Override
									public void run() {

									}
								});


								finish();
							}
							else {
								// failed to login
								flag = 1;
							}


						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});


			}
			else{
				Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
						Toast.LENGTH_SHORT).show();

			}



			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if (flag == 1) {
				//Toast.makeText(getApplicationContext(), "Please Enter Correct informations", Toast.LENGTH_LONG).show();
			}
			if (flag == 0) {


			}



		}

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setMessage("Apa anda ingin keluar?")
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						finish();
						//Globals.level = "";
						Globals.id = "";
						Globals.user_on = "";
						moveTaskToBack(true);
						System.exit(0);
					}
				})
				.setNegativeButton("No", null)
				.show();
	}

}
