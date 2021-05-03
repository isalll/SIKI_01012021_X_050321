package com.siki.android.sqlite.db;

/**
 * Created by IT001 on 23-Jun-16.
 */
public class User {
    // Labels table name
    public static final String TABLE = "android_user";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_username = "username";
    public static final String KEY_isandroidlogin = "isandroidlogin";
    public static final String KEY_logtime = "logtime";

    // property help us to keep data
    public int id;
    public String username;
    public int isandroidlogin;
    public String logtime;
}