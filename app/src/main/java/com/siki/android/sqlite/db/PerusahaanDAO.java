package com.siki.android.sqlite.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.siki.android.sqlite.to.Perusahaan;

public class PerusahaanDAO extends PerusahaanDBDAO {

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUMN
			+ " =?";
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	public PerusahaanDAO(Context context) {
		super(context);
	}

	public long save(Perusahaan Perusahaan) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, Perusahaan.getNama());
		//Log.d("dob", Perusahaan.getDateOfBirth().getTime() + "");
		values.put(DataBaseHelper.PERUSAHAAN_NOIUI, Perusahaan.getno_iui());
		values.put(DataBaseHelper.PERUSAHAAN_TGLIUI, formatter.format(Perusahaan.gettgl_iui()));
	
		values.put(DataBaseHelper.PERUSAHAAN_TENKERASING, Perusahaan.gettenaga_kerja_asing());
		values.put(DataBaseHelper.PERUSAHAAN_TENKERDN, Perusahaan.gettenaga_kerja_dn());
		values.put(DataBaseHelper.PERUSAHAAN_KDBDNHKM, Perusahaan.getkd_badan_hukum());
		values.put(DataBaseHelper.PERUSAHAAN_NPWP, Perusahaan.getnpwp());
		values.put(DataBaseHelper.PERUSAHAAN_PRODUTAMA, Perusahaan.getproduksi_utama());
		values.put(DataBaseHelper.PERUSAHAAN_SEKTOR, Perusahaan.getsektor());

		return database.insert(DataBaseHelper.PERUSAHAAN_TABLE, null, values);
	}

	public long update(Perusahaan Perusahaan) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NAME_COLUMN, Perusahaan.getNama());
		//Log.d("dob", Perusahaan.getDateOfBirth().getTime() + "");
		values.put(DataBaseHelper.PERUSAHAAN_NOIUI, Perusahaan.getno_iui());
		values.put(DataBaseHelper.PERUSAHAAN_TGLIUI, formatter.format(Perusahaan.gettgl_iui()));
/*
		values.put(DataBaseHelper.PERUSAHAAN_TENKERASING, Perusahaan.gettenaga_kerja_asing());
		values.put(DataBaseHelper.PERUSAHAAN_TENKERDN, Perusahaan.gettenaga_kerja_dn());
		values.put(DataBaseHelper.PERUSAHAAN_KDBDNHKM, Perusahaan.getkd_badan_hukum());
		values.put(DataBaseHelper.PERUSAHAAN_NPWP, Perusahaan.getnpwp());
		values.put(DataBaseHelper.PERUSAHAAN_PRODUTAMA, Perusahaan.getproduksi_utama());
		values.put(DataBaseHelper.PERUSAHAAN_SEKTOR, Perusahaan.getsektor());
*/
		long result = database.update(DataBaseHelper.PERUSAHAAN_TABLE, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(Perusahaan.getId()) });
		Log.d("Update Result:", "=" + result);
		return result;

	}

	public int delete(Perusahaan Perusahaan) {
		return database.delete(DataBaseHelper.PERUSAHAAN_TABLE, WHERE_ID_EQUALS,
				new String[] { Perusahaan.getId() + "" });
	}

	//USING query() method
	public ArrayList<Perusahaan> getPerusahaans() {
		ArrayList<Perusahaan> PerusahaanList = new ArrayList<Perusahaan>();

		Cursor cursor = database.query(DataBaseHelper.PERUSAHAAN_TABLE,
				new String[] { DataBaseHelper.ID_COLUMN,
						DataBaseHelper.NAME_COLUMN,
						DataBaseHelper.PERUSAHAAN_NOIUI,
						DataBaseHelper.PERUSAHAAN_TGLIUI,
						DataBaseHelper.PERUSAHAAN_TENKERASING,
						DataBaseHelper.PERUSAHAAN_TENKERDN,
						DataBaseHelper.PERUSAHAAN_KDBDNHKM,
						DataBaseHelper.PERUSAHAAN_NPWP,
						DataBaseHelper.PERUSAHAAN_PRODUTAMA,
						DataBaseHelper.PERUSAHAAN_SEKTOR
						
						 }, null, null, null,
				null, null);

		while (cursor.moveToNext()) {
			Perusahaan Perusahaan = new Perusahaan();
			Perusahaan.setId(cursor.getInt(0));
			Perusahaan.setNama(cursor.getString(1));
			Perusahaan.setno_iui(cursor.getString(2));
			/*
			try {
				Perusahaan.settgl_iui(formatter.parse(cursor.getString(3)));
			} catch (ParseException e) {
				Perusahaan.settgl_iui(null);
			}
			*/
			//error karena ada data yg null atau 0000-00-00
			Perusahaan.settgl_iui(cursor.getString(3));
			
			
			
			Perusahaan.settenaga_kerja_asing(cursor.getString(4));
			Perusahaan.settenaga_kerja_dn(cursor.getString(5));
			Perusahaan.setkd_badan_hukum(cursor.getInt(6));
			Perusahaan.setnpwp(cursor.getString(7));
			Perusahaan.setproduksi_utama(cursor.getString(8));
			Perusahaan.setsektor(cursor.getString(9));

			
			//Perusahaan.setSalary(cursor.getDouble(3));

			PerusahaanList.add(Perusahaan);
		}
		return PerusahaanList;
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
	
	//Retrieves a single Perusahaan record with the given id
	public Perusahaan getPerusahaan(long id) {
		Perusahaan Perusahaan = null;

		String sql = "SELECT * FROM " + DataBaseHelper.PERUSAHAAN_TABLE
				+ " WHERE " + DataBaseHelper.ID_COLUMN + " = ?";

		Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

		if (cursor.moveToNext()) {
			Perusahaan = new Perusahaan();
			Perusahaan.setId(cursor.getInt(0));
			Perusahaan.setNama(cursor.getString(1));
			Perusahaan.setno_iui(cursor.getString(2));
			/*
			try {
				Log.d("Error disini:", "=" );

				Perusahaan.settgl_iui(formatter.parse(cursor.getString(3)));
			} catch (ParseException e) {
				Perusahaan.settgl_iui(null);
			}
			*/
			
			Perusahaan.settenaga_kerja_asing(cursor.getString(4));
			Perusahaan.settenaga_kerja_dn(cursor.getString(5));
			Perusahaan.setkd_badan_hukum(cursor.getInt(6));
			Perusahaan.setnpwp(cursor.getString(7));
			Perusahaan.setproduksi_utama(cursor.getString(8));
			Perusahaan.setsektor(cursor.getString(9));

		}
		return Perusahaan;
	}
}
