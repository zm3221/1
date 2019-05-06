package com.sikalenko.java1101.Organizer.app.src.main.java.com.example.app19;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DBH extends SQLiteOpenHelper {

    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;

    static final String TB_MAIN = "main";
    static final String TB_LISTS = "lists";
    static final String TB_NTF = "notifications";

    public static final String M_ID = "_id";
    public static final String M_TITLE = "title";
    public static final String M_DT = "datetime";
    public static final String M_IDL = "idl";
    public static final String M_CB = "cb";

    public static final String L_ID = "_id";
    public static final String L_TITLE = "ltitle";

    public static final String N_ID = "idn";
    public static final String N_DT = "dtntf";

    public DBH(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TB_MAIN + " ("
                + M_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + M_TITLE + " TEXT, " + M_DT + " INTEGER, "
                + M_IDL + " INTEGER, " + M_CB + " INTEGER);");

        db.execSQL("CREATE TABLE " + TB_LISTS + " ("
                + L_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + L_TITLE + " TEXT);");

        db.execSQL("CREATE TABLE " + TB_NTF + " ("
                + N_ID + " INTEGER PRIMARY KEY, "
                + N_DT + " INTEGER);");

        ContentValues cv = new ContentValues();
        cv.put(DBH.L_TITLE, "По умолчанию");
        db.insert(DBH.TB_LISTS, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_MAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LISTS);
        db.execSQL("DROP TABLE IF EXISTS " + TB_NTF);
        onCreate(db);
    }
}
