package com.siki.android.sqlite.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.siki.android.Globals;

public class DataBaseHelper extends SQLiteOpenHelper
{

	//private static final String DATABASE_NAME = "iris_dev.db";
	//private static final int DATABASE_VERSION = 1;

	public static final String PERUSAHAAN_TABLE = "perusahaan";
    public static final String KOMENTAR_TABLE = "komentar";
	//public static final String PERUSAHAAN_TABLE = "employee";

	
	public static final String ID_COLUMN = "id";
	public static final String NAME_COLUMN = "nama";
	public static final String PERUSAHAAN_NOIUI = "no_iui";
	public static final String PERUSAHAAN_TGLIUI = "tgl_iui";
	
	public static final String PERUSAHAAN_TENKERASING = "tenaga_kerja_asing";
	public static final String PERUSAHAAN_TENKERDN = "tenaga_kerja_dn";
	public static final String PERUSAHAAN_KDBDNHKM = "kd_badan_hukum";
	public static final String PERUSAHAAN_NPWP = "npwp";
	public static final String PERUSAHAAN_PRODUTAMA = "produksi_utama";
	public static final String PERUSAHAAN_SEKTOR = "sektor";

    //Tabel Komentar
    public static final String KDHS_COLUMN = "kd_hs";
    public static final String KOMENTAR = "komentar";
    public static final String USERNAME = "username";
	
	

	private static DataBaseHelper instance;
	//---------------------------------------------------
	
   // private static String DB_PATH = "/data/data/com.androidopentutorials.sqlite/databases/";
 //   private static String DB_NAME = "employeedb.db";
  //  private SQLiteDatabase myDataBase; 

    private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
    //destination path (location) of our database on device
    private static String DB_PATH = "";
    private static String DB_NAME ="iris_dev.db";// Database name
    private SQLiteDatabase mDataBase;
    private final Context mContext;


	public static synchronized DataBaseHelper getHelper(Context context) {
		if (instance == null)
			instance = new DataBaseHelper(context);
		return instance;
	}

/*	
	private DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
*/
	
    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);// 1? Its database Version
        if(android.os.Build.VERSION.SDK_INT >= 17){
           DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
           //DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";

            //packagename dari playstore : main.2.com.iris.android.obb
            DB_PATH = "/data/data/main.2.com.iris.android.obb/databases/";

            //utk Jelly Bean 4.2
           //DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        Globals.dbasepath=DB_PATH;
        this.mContext = context;

        //Log.e(TAG, "[donnitest] : DB_PATH : "+Globals.dbasepath);

    }
    public void createDataBase() throws IOException
    {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist)
        {
            this.getReadableDatabase();
            this.close();
            try
            {
                //Copy the database from assests
                copyDataBase();
                //Log.e(TAG, "createDatabase database created");
                Log.e(TAG, "[donnitest]  :createDatabase : ");

            }
            catch (IOException mIOException)
            {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase()
    {
        File dbFile = new File(Globals.dbasepath + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

      //Copy the database from assets
    private void copyDataBase() throws IOException
    {
        try {

         InputStream mInput = this.mContext.getAssets().open(DB_NAME);
        String outFileName = Globals.dbasepath + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();

        Log.e(TAG, "[donnitest]  :copyDataBase : "+outFileName);

        } catch (Exception e) {

            Log.e("error", e.toString());

        }

    }
    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException
    {
        String mPath = Globals.dbasepath + DB_NAME;
        //Log.v("mPath", mPath);
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return mDataBase != null;
    }

    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }    
 //---------------------------------------------------------------------   
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL(CREATE_EMPLOYEE_TABLE);
		//db.execSQL(OPEN_EMPLOYEE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
