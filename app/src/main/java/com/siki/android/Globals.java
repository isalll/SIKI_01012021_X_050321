package com.siki.android;

import java.util.ArrayList;
import java.util.List;

public class Globals {
    public static int ivar1,ivar2,jlhbaris;
    public static String svar1,svar2;
    public static int[] myarray1=new int[10];
	public static String kd_hs,uraian_hs,hasil_loadbaris,max_bulan,max_tahun;
	public static double[] values_hs_arr,values;
	public static String[] kd_hs_arr;
	public static String[] negara_arr;
	//public static String[] periode_arr;
	public static List<String> periode_arr = new ArrayList<>();

	public static boolean isLoadingDataImpor=false;
	public static boolean isLoadingDataEkspor=false;
	public static boolean islogout=false;

	public static String dbasepath = "";
	public static String phonenumber = "";
	public static String phoneinfo = "";
	public static String dash_graph = "";

	public static String kode_hs 		= "";
	public static String kode_berita 		= "";
	public static String kode_negara 		= "";
	public static String id_kliping 		= "";
	public static String user_on 			= "";
	public static String level				= "";
	public static String id					= "";
	public static String fragment			= "";
	public static String android_user		= "";
	public static int isandroidlogin		= 0;
	public static String host 				= "iris.kemenperin.go.id";
	//public static String host 				= "boemikawani.com";
	public static String gethasil 			= "";
	public static String bulanmax 			= "";
	public static String tahunmax 			= "";
	public static String bulanmin 			= "";
	public static String tahunmin 			= "";
	public static String menu 				= "";
	public static String thn_max 			= "";
	public static String thn_min 			= "";
	public static String bln_max 			= "";


	//LogIn
	//public static String url_login 				= "http://"+host+"/android/login.php";
	public static String url_login 				= "http://siki.kemenperin.go.id/r-api/login";
	public static String url_logout 			= "http://"+host+"/android/logout.php";
	public static String url_androidlogin 		= "http://"+host+"/android/isandroidlogin.php";
	//public static String url_infodatabase 		= "http://"+host+"/android/infodatabase.php";
	//public static String url_infodatabase 				= "http://siki.kemenperin.go.id/r-api/infodatabase";

	//Graph login sebagai pegawai dan fragment berita
	public static String url_dsb_impdown		= "http://"+host+"/android/dsb_impdown.php";
	public static String url_dsb_impup			= "http://"+host+"/android/dsb_impup.php";
	public static String url_dsb_eksdown		= "http://"+host+"/android/dsb_eksdown.php";
	public static String url_dsb_eksup			= "http://"+host+"/android/dsb_eksup.php";


	//Graph login sebagai pegawai
	public static String url_graph 				= "http://"+host+"/android/graph.php";
	//public static String url_graph 				= "http://siki.kemenperin.go.id/r-api/graph";

	public static String url_lonjakan			= "http://"+host+"/android/lonjakan.php";

	//Graph login sebagai perusahaan
	public static String url_graph_perusahaan 	= "http://"+host+"/android/graph_perusahaan.php";
	public static String url_graph_perusahaan2 	= "http://"+host+"/android/graph_perusahaan2.php";
	public static String url_lonjakan2			= "http://"+host+"/android/lonjakan2.php";

	//fragment cari hs
	public static String url_cari_hs2			= "http://"+host+"/android/cari_hs2.php";
	public static String url_cari_negara		= "http://"+host+"/android/cari_negara.php";

	//fragment berita
	public static String url_detailberita   	= "http://"+host+"/android/detail_berita1.php";
	public static String url_listkomen 			= "http://"+host+"/android/list_komen.php";
	public static String url_komen	 			= "http://"+host+"/android/komen.php";
	public static String url_detailberita1   	= "http://"+host+"/android/detail_berita.php";

	//fragment kliping
	public static String url_dsb_kliping			= "http://"+host+"/android/dsb_kliping.php";
	public static String url_detailkliping1   	= "http://"+host+"/android/detail_kliping1.php";
	public static String url_listkomenkliping	= "http://"+host+"/android/list_komenkliping.php";
	public static String url_komenkliping		= "http://"+host+"/android/komenkliping.php";
	public static String url_detailkliping   	= "http://"+host+"/android/detail_kliping.php";

	//fragment perusahaan
	public static String url_perusahaan			= "http://"+host+"/android/perusahaan.php";
	public static String url_cari_perusahaan	= "http://"+host+"/android/cari_perusahaan.php";
	public static String url_pencarian_perusahaan	= "http://"+host+"/android/pencarian_perusahaan.php";


	//fragment Peringatan Dini (29 Sept 2017)

	public static String base_host 				= "http://iris.kemenperin.go.id/android/ews/";

	public static String base_host_bigdata 	    = "https://siki.kemenperin.go.id/r-api/";



	public static	ArrayList<double[]> db_results = new ArrayList<double[]>();

//============================================================================================
private static Globals instance = new Globals();
	// Getter-Setters
	public static Globals getInstance() {
		return instance;
	}

	public static void setInstance(Globals instance) {
		Globals.instance = instance;
	}

	private String notification_index;


	private Globals() {

	}


	public String getValue() {
		return notification_index;
	}


	public void setValue(String notification_index) {
		this.notification_index = notification_index;
	}

}
