package com.siki.android.sqlite.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PerusahaanDBDAO {

	protected SQLiteDatabase database;
	private DataBaseHelper dbHelper;
	private Context mContext;

	public PerusahaanDBDAO(Context context) {
		this.mContext = context;
		dbHelper = DataBaseHelper.getHelper(mContext);
		open();
		
	}

	public void open() throws SQLException {
		if(dbHelper == null)
			dbHelper = DataBaseHelper.getHelper(mContext);
		database = dbHelper.getWritableDatabase();
	}
	
	/*public void close() {
		dbHelper.close();
		database = null;
	}*/
}
