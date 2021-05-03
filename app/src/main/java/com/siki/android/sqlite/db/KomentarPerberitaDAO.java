package com.siki.android.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.siki.android.sqlite.to.KomentarPerberita;
import com.siki.android.sqlite.to.Perusahaan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class KomentarPerberitaDAO extends DBDAO {

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	public KomentarPerberitaDAO(Context context) {
		super(context);
	}

	public long save(KomentarPerberita Koper) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KDHS_COLUMN, Koper.getKDHS());
		values.put(DataBaseHelper.KOMENTAR, Koper.getKomentar());
		values.put(DataBaseHelper.USERNAME, Koper.getUsername());


		return database.insert(DataBaseHelper.KOMENTAR_TABLE, null, values);
	}

	public long update(KomentarPerberita Koper) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.KDHS_COLUMN, Koper.getKDHS());
		values.put(DataBaseHelper.KOMENTAR, Koper.getKomentar());
		values.put(DataBaseHelper.USERNAME, Koper.getUsername());

		long result = database.update(DataBaseHelper.KOMENTAR_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(Koper.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;

	}

	public int delete(KomentarPerberita Koper) {
		return database.delete(DataBaseHelper.KOMENTAR_TABLE, WHERE_ID_EQUALS,
				new String[] { Koper.getId() + "" });
	}

	//USING query() method
	public ArrayList<KomentarPerberita> getKomentar() {
		ArrayList<KomentarPerberita> KomentarList = new ArrayList<KomentarPerberita>();

		Cursor cursor = database.query(DataBaseHelper.KOMENTAR_TABLE,
				new String[] { DataBaseHelper.ID_COLUMN,
						DataBaseHelper.KDHS_COLUMN,
						DataBaseHelper.KOMENTAR,
						DataBaseHelper.USERNAME
						
						 }, null, null, null,
				null, null);

		while (cursor.moveToNext()) {
			KomentarPerberita Komentar = new KomentarPerberita();
			Komentar.setId(cursor.getInt(0));
			Komentar.setKDHS(cursor.getString(1));
			Komentar.setKomentar(cursor.getString(2));
			Komentar.setUsername(cursor.getString(3));

			KomentarList.add(Komentar);
		}
		return KomentarList;
	}
	
	//USING rawQuery() method
	/*public ArrayList<Perusahaan> getPerusahaans() {
		ArrayList<Perusahaan> Perusahaans = new ArrayList<Perusahaan>();

		String sql = "SELECT " + DataBaseHelper.ID_COLUMN + ","
				+ DataBaseHelper.NAME_COLUMN + ","
				+ DataBaseHelper.Perusahaan_DOB + ","
				+ DataBaseHelper.Perusahaan_SALARY + " FROM "
				+ DataBaseHelper.Perusahaan_TABLE;

		Cursor cursor = database.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			Perusahaan Perusahaan = new Perusahaan();
			Perusahaan.setId(cursor.getInt(0));
			Perusahaan.setName(cursor.getString(1));
			try {
				Perusahaan.setDateOfBirth(formatter.parse(cursor.getString(2)));
			} catch (ParseException e) {
				Perusahaan.setDateOfBirth(null);
			}
			Perusahaan.setSalary(cursor.getDouble(3));

			Perusahaans.add(Perusahaan);
		}
		return Perusahaans;
	}*/
	
	//Retrieves a single Komentar record with the given id
	public KomentarPerberita getKomentarPerberita(long id) {
		KomentarPerberita Koper = null;

		String sql = "SELECT * FROM " + DataBaseHelper.KOMENTAR_TABLE
				+ " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";

		Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

		if (cursor.moveToNext()) {
			Koper = new KomentarPerberita();
			Koper.setId(cursor.getInt(0));
			Koper.setKDHS(cursor.getString(1));
			Koper.setKomentar(cursor.getString(2));
			Koper.setUsername(cursor.getString(3));

		}
		return Koper;
	}
}
