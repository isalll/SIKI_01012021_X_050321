package com.siki.android.sqlite.db;

/**
 * Created by IT001 on 23-Jun-16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import android.text.format.Time;

import com.siki.android.Globals;

public class UserRepo {
    private DataBaseHelper DbaseHelper;

    public UserRepo(Context context) {
        DbaseHelper = new DataBaseHelper(context);
    }

    public int insert(User user) {

        //Open connection to write data
        SQLiteDatabase db =  DbaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_logtime, user.logtime);
        values.put(User.KEY_isandroidlogin,user.isandroidlogin);
        values.put(User.KEY_username, user.username);

        // Inserting Row
        long User_Id = db.insert(User.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) User_Id;
    }

    public void delete(int User_Id) {

        SQLiteDatabase db =  DbaseHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(User.TABLE, User.KEY_ID + "= ?", new String[] { String.valueOf(User_Id) });
        db.close(); // Closing database connection
    }

    public void update(User user,int isandroidlogin) {

        SQLiteDatabase db =  DbaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        Time now = new Time();
        now.setToNow();
        String sTime = now.format("%Y_%m_%d_%H_%M_%S");

        values.put(User.KEY_username, Globals.android_user);
        values.put(User.KEY_isandroidlogin,isandroidlogin);
        values.put(User.KEY_logtime, sTime);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(User.TABLE, values, User.KEY_ID + "= ?", new String[] { String.valueOf(user.id) });
        db.close(); // Closing database connection
    }

     public ArrayList<HashMap<String, String>>  getUserList() {
        //Open connection to read only
        SQLiteDatabase db =  DbaseHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_ID + "," +
                User.KEY_username + "," +
                User.KEY_isandroidlogin + "," +
                User.KEY_logtime +
                " FROM " + User.TABLE;

        //User User = new User();
        ArrayList<HashMap<String, String>> UserList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> user = new HashMap<String, String>();
                user.put("id", cursor.getString(cursor.getColumnIndex(User.KEY_ID)));
                user.put("name", cursor.getString(cursor.getColumnIndex(User.KEY_username)));
                UserList.add(user);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return UserList;

    }

    public User getUserById(int Id){
        SQLiteDatabase db =  DbaseHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                User.KEY_ID + "," +
                User.KEY_username + "," +
                User.KEY_isandroidlogin + "," +
                User.KEY_logtime +
                " FROM " + User.TABLE
                + " WHERE " +
                User.KEY_ID + "=?";

        int iCount =0;
        User User = new User();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                User.id =cursor.getInt(cursor.getColumnIndex(User.KEY_ID));
                User.username =cursor.getString(cursor.getColumnIndex(User.KEY_username));
                User.isandroidlogin  =cursor.getInt(cursor.getColumnIndex(User.KEY_isandroidlogin));
                User.logtime =cursor.getString(cursor.getColumnIndex(User.KEY_logtime));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return User;
    }

}