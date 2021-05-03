package com.siki.android;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
//import android.app.ProgressDialog;

import com.siki.android.fragments.HSLinkFragment;
import com.siki.android.fragments.EWSFragment;
import com.siki.android.fragments.RCAFragment;
import com.siki.android.fragments.MediaFragment;
import com.siki.android.sqlite.db.User;
import com.siki.android.sqlite.db.UserRepo;
import com.siki.android.R.id;

import com.siki.library.JSONParser;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {

	private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAivm2ht4GDPA+butYmXYREp02WsZpIE8xwE/sOMlnhkmaDPW8R2+cwYZGIqrmgGBSfb+yoVofz1ZtsUwiEHgmjsf/TPNHA4swD5xHxeQWV7d+AKVA705x7Pg43vyZ5KRGCwU+nqxHTJiO0/V2qOV+BZZ7MgDRiGslmxyRMP0S3rBjRovoWyKu+ScUprK2nREeDlRozid409075z1+gIyJFzUxfY7QeIyXKHcYA+Z+t3AFxGLOhdoGpViQIeGlQ9Z/K0relKbPYKHS4WUBFrcGFyQfwei5akcYRokQ67IYxwUcCVj2CMivJNLLjhXYXtkKIQQpGb92yXN5DL1/ROL9LwIDAQAB";
	public static final int FRAGMENT_DASHBOARD = 0;
	public static final int FRAGMENT_NEWS = 1;
	public static final int FRAGMENT_FINDHS = 2;
	public static final int FRAGMENT_ABOUT = 3;
	public static final int FRAGMENT_PERUSAHAAN = 4;
	public static final int FRAGMENT_KLIPING = 5;
	public static final int FRAGMENT_LONJAKAN = 7;
	public static final int FRAGMENT_DETILHS = 8;
	public static final int FRAGMENT_FINDNEGARA = 9;
	public static final int FRAGMENT_PD = 10;
	public static final int FRAGMENT_EWS = 11;
	public static final int FRAGMENT_HSLINK = 12;
	public static final int FRAGMENT_MEDIAANALISIS = 13;

	private SlidingPaneLayout spl;
	private Context context;
	private ProgressDialog pDialog;

	private Fragment contentFragment;

	int flag = 0;
	JSONParser jsonParser = new JSONParser();
	private static String url = Globals.url_logout;
	//private DataBaseHelper DBHelper;

	private int _User_Id=1;
	private int isandroidlogin=0;
	private int isandroidhadlogin=1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		init();

		//Cek Level user
		View menuPerusahaan = findViewById(id.btnMenuPerusahaan); // hide menu perusahaan
		View menuKliping = findViewById(id.btnMenuKliping); // hide menu kliping krn ada komentar
		View menuTop5Lonjakan = findViewById(id.btnMenuLonjakan);
		View menuNegara = findViewById(id.btnMenuFindNegara);
		View menuFindHS = findViewById(id.btnMenuFindHS);
		View menuMediaAnalisis = findViewById(id.btnMenuMediaAnalisis);



		//MENU SEMENTARA YG DIHILANGKAN
		menuPerusahaan.setVisibility(View.GONE);
		menuKliping.setVisibility(View.GONE);
		menuTop5Lonjakan.setVisibility(View.GONE);
		menuNegara.setVisibility(View.GONE);
		menuFindHS.setVisibility(View.GONE);
		menuMediaAnalisis.setVisibility(View.GONE);

		//KODE LEVEL USER MANAGEMENT
		//[1] superadmin
		//[2] internal kemenperin
		//[3] perusahaan/asosiasi
		//[4] Internal K/L , DUngsi ekonomi, KBRI
		//[5] Guest ---> Trial

		if (Globals.android_user.equals("guest")) {
			menuPerusahaan.setVisibility(View.GONE);
			//c.setVisibility(View.GONE);
			//d.setVisibility(View.GONE);
		}


		//30/11/2020---> diskip dulu, ntar dibuka lagi

        if(isNetworkAvailable() == true){



			if(Globals.fragment.equals("fragment2")){
				setNewPage(new GraphActivity(), FRAGMENT_DASHBOARD);
			}else if(Globals.fragment.equals("fragment1")) {
				setNewPage(new Graph3Activity(), FRAGMENT_DASHBOARD);
			}
				else
			 {


			//	if (Globals.level.equals("coorporate")) {
			//		setNewPage(new Dashboard_coorporateActivity(), FRAGMENT_DASHBOARD);
			//	} else if (Globals.level.equals("pegawai")) {
			//		setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);
			//	} else {
			//		setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);
			//	}


				 setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);
			}


        }
        else{
    		Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
    	    	       Toast.LENGTH_SHORT).show();

        }



		//30/11/2020 --> buat test doang nanti dihapus
/*
		if (isNetworkAvailable() == true) {
			setNewPage(new MediaFragment(), FRAGMENT_MEDIAANALISIS);
			Log.d("MainActivity", "OnMedia");
		} else {
			Toast.makeText(getBaseContext(), "Tidak Ada Koneksi Internet",
					Toast.LENGTH_SHORT).show();

		}
*/

		Toast.makeText(getApplicationContext(), "user : "+Globals.android_user, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResume() {
		super.onResume();

	}


	private void init() {
		spl = (SlidingPaneLayout) findViewById(id.sliding_pane_layout);
	}

	public void setNewPage(Fragment fragment, int pageIndex) {
		if (spl.isOpen()) {
			spl.closePane();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_frame, fragment, "currentFragment")
				.commit();

	}

	public void onDetailHSDialog(View v) {

		if(isNetworkAvailable() == true){
			Globals.kode_berita=Globals.kd_hs;
			Bundle arguments = new Bundle();
			DetailCariHSFragment detailFragment = new DetailCariHSFragment();
			detailFragment.setArguments(arguments);
			detailFragment.show(this.getSupportFragmentManager(),DetailCariHSFragment.ARG_ITEM_ID);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();
		}

	}

/*
	public void onImportUp(View v){
		if(isNetworkAvailable() == true){
			Globals.dash_graph="ImportUp";
			setNewPage(new GraphActivity(), FRAGMENT_NEWS);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}
	}
	public void onCoorporate(View v){
		if(isNetworkAvailable() == true){
			Globals.dash_graph="ImportUp";
			setNewPage(new GraphPerusahaanActivity(), FRAGMENT_NEWS);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}
	}

	public void onImportDown(View v){
		if(isNetworkAvailable() == true){
			Globals.dash_graph="ImportDown";
			setNewPage(new GraphActivity(), FRAGMENT_NEWS);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}
	}
	public void onExportUp(View v){
		if(isNetworkAvailable() == true){
			Globals.dash_graph="ExportUp";
			setNewPage(new GraphActivity(), FRAGMENT_NEWS);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}
	}
	public void onExportDown(View v){
		if(isNetworkAvailable() == true){
			Globals.dash_graph="ExportDown";
			setNewPage(new GraphActivity(), FRAGMENT_NEWS);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}
	}

*/
	public void onKembali(View v) {

		//masih error........ test lagi script: doInBackground
		//setNewPage(new LoginActivity(), FRAGMENT_PERUSAHAAN);
		Globals.fragment = "";
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		//moveTaskToBack(true);
	}
	public void onKembali2(View v) {

		//masih error........ test lagi script: doInBackground
		//setNewPage(new LoginActivity(), FRAGMENT_PERUSAHAAN);
		Globals.fragment = "fragment2";
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		//moveTaskToBack(true);
	}
	public void onDashBoard(View v) {

        if(isNetworkAvailable()){

        	//30/11/2020---> diskip dulu, ntar dibuka lagi

			/*
				if (Globals.level.equals("coorporate")) {
					setNewPage(new Dashboard_coorporateActivity(), FRAGMENT_DASHBOARD);
				} else {
					setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);
				}
*/
			setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);


        }
        else{
    		Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
    	    	       Toast.LENGTH_SHORT).show();

        }


	}
	public void onDashBoarddetail(View v) {

		if(isNetworkAvailable()){

			//30/11/2020---> diskip dulu, ntar dibuka lagi

			/*
				if (Globals.level.equals("coorporate")) {
					setNewPage(new Dashboard_coorporateActivity(), FRAGMENT_DASHBOARD);
				} else {
					setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);
				}
*/
			setNewPage(new DashboardActivityDetail(), FRAGMENT_DASHBOARD);


		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}


	}
	public void onDashBoarddetailEks(View v) {

		if(isNetworkAvailable()){

			//30/11/2020---> diskip dulu, ntar dibuka lagi

			/*
				if (Globals.level.equals("coorporate")) {
					setNewPage(new Dashboard_coorporateActivity(), FRAGMENT_DASHBOARD);
				} else {
					setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);
				}
*/
			setNewPage(new DashboardActivityDetailExp(), FRAGMENT_DASHBOARD);


		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}


	}

	public void onNews(View v) {
        if(isNetworkAvailable() == true){
    		setNewPage(new LonjakanActivity(), FRAGMENT_NEWS);
        }
        else{
    		Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
    	    	       Toast.LENGTH_SHORT).show();

        }
	}

	public void onKliping(View v) {
		if (isNetworkAvailable() == true) {
			setNewPage(new KlipingActivity(), FRAGMENT_KLIPING);
		} else {
			Toast.makeText(getBaseContext(), "Tidak Ada Koneksi Internet",
					Toast.LENGTH_SHORT).show();

		}
	}
/*
	public void onPD(View v) {
		if (isNetworkAvailable() == true) {
			setNewPage(new PDFragment(), FRAGMENT_PD);
			Log.d("MainActivity", "OnPD");
		} else {
			Toast.makeText(getBaseContext(), "Tidak Ada Koneksi Internet",
					Toast.LENGTH_SHORT).show();

		}
	}
*/
	public void onEWS(View v) {
		if (isNetworkAvailable() == true) {
			setNewPage(new EWSFragment(), FRAGMENT_EWS);
			Log.d("MainActivity", "OnEWS");
		} else {
			Toast.makeText(getBaseContext(), "Tidak Ada Koneksi Internet",
					Toast.LENGTH_SHORT).show();

		}
	}

	public void onRCA(View v) {
		if (isNetworkAvailable() == true) {
			setNewPage(new RCAFragment(), FRAGMENT_EWS);
			Log.d("MainActivity", "OnEWS");
		} else {
			Toast.makeText(getBaseContext(), "Tidak Ada Koneksi Internet",
					Toast.LENGTH_SHORT).show();

		}
	}


	public void onLonjakan(View v) {
		if (isNetworkAvailable() == true) {
			setNewPage(new LonjakanActivity(), FRAGMENT_LONJAKAN);
		} else {
			Toast.makeText(getBaseContext(), "Tidak Ada Koneksi Internet",
					Toast.LENGTH_SHORT).show();

		}
	}


	public void onFindHS(View v) {
        if(isNetworkAvailable() == true){
    		setNewPage(new FindHS(), FRAGMENT_FINDHS);
			//startActivity(intent);
        }
        else{
    		Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
    	    	       Toast.LENGTH_SHORT).show();

        }

	}

	public void onHSLink(View v) {
		if(isNetworkAvailable() == true){
			setNewPage(new HSLinkFragment(), FRAGMENT_HSLINK);
			//startActivity(intent);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}

	}
	public void onMediaAnalisis(View v) {
		if(isNetworkAvailable() == true){
			setNewPage(new MediaFragment(), FRAGMENT_MEDIAANALISIS);
			//startActivity(intent);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}

	}

	public void onFindNegara(View v) {
		if(isNetworkAvailable() == true){
			setNewPage(new FindNegara(), FRAGMENT_FINDNEGARA);
			//startActivity(intent);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}

	}

	public void onAbout(View v) {
		setNewPage(new AboutActivity(), FRAGMENT_ABOUT);
	}
	public void onPerusahaan(View v) {

		if(isNetworkAvailable() == true){
			//setNewPage(new PerusahaanActivity(), FRAGMENT_PERUSAHAAN);
			setNewPage(new Pencarian_perusahaan(), FRAGMENT_PERUSAHAAN);
		}
		else{
			Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
					Toast.LENGTH_SHORT).show();

		}
	}




	public void onLogout(View v){



		//masih error........ test lagi script: doInBackground
		//setNewPage(new LoginActivity(), FRAGMENT_PERUSAHAAN);

		//SCRIPT DIBAWH INI DI SKIP DULU ....................................................
		/*
			new AlertDialog.Builder(this)
					.setMessage("Apa kalian yakin ingin LogOut?")
					.setCancelable(false)
					.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

							List<NameValuePair> params = new ArrayList<NameValuePair>();
							String user = Globals.user_on;
							params.add(new BasicNameValuePair("username", user));
							JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
							Log.d("Create Response", json.toString());

							//simpan data di sqllite : tabel android_user


							Globals.user_on = "";
							Globals.islogout = true;
							Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
							startActivity(intent);
							finish();
						}
					})
					.setNegativeButton("No", null)
					.show();
		*/


//kode utk save diserver ini error...........................
/*
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String userx = Globals.user_on;
		params.add(new BasicNameValuePair("username", userx));
		JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
		Log.d("Create Response", json.toString());
*/

		//simpan data di sqllite : tabel android_user

		_User_Id =1;
		isandroidlogin=0;

		Globals.android_user = "blank";
		Globals.islogout = true;

		UserRepo repo = new UserRepo(this);
		User user = new User();
		user=repo.getUserById(_User_Id);
		//user=Globals.user_on;

		repo.update(user,isandroidlogin);


		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		finish();

	}

	public void onSliding(View v) {
		if (spl.isOpen()) {
			spl.closePane();
		} else {
			spl.openPane();
		}
	}

	public void onFinishDialog() {
		//if (employeeListFragment != null) {
		//	employeeListFragment.updateView();
		//}
	}

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@SuppressLint("NewApi")

	public void onBackPressedChart(View v){

		FragmentManager fm = getFragmentManager();
	    if (fm.getBackStackEntryCount() > 0) {
	        //Log.i("MainActivity", "popping backstack");
	        fm.popBackStack();
	    } else {
	        //Log.i("MainActivity", "nothing on backstack, calling super");
	        //super.onBackPressed();

	        if(isNetworkAvailable() == true){
		    	//setNewPage(new DashboardActivity(), FRAGMENT_DASHBOARD);
				if(Globals.menu=="lonjakan") {
					setNewPage(new LonjakanActivity(), FRAGMENT_LONJAKAN);
				}
				if(Globals.menu=="carihs") {
					setNewPage(new FindHS(), FRAGMENT_FINDHS);
				}

			}
	        else{
	    		Toast.makeText(getBaseContext(),"Tidak Ada Koneksi Internet" ,
	    	    	       Toast.LENGTH_SHORT).show();

	        }

	    }


	}



}

