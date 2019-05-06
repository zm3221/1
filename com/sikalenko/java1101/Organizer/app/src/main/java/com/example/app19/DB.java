package com.sikalenko.java1101.Organizer.app.src.main.java.com.example.app19;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;

public class DB {

    private DBH dbh;
    private SQLiteDatabase sdb;
    private final Context context;

    public DB(Context ctx) {
        context = ctx;
        dbh = new DBH(context);
    }

    public void open() {
        sdb = dbh.getWritableDatabase();
    }

    public void close() {
        dbh.close();
    }

    public Cursor getData(long code){
        long time = Calendar.getInstance().getTimeInMillis();
        Cursor curs;

        if (code == 0){
            curs = sdb.rawQuery("select * from " + DBH.TB_MAIN +
                            " where " + DBH.M_DT + " >? " +
                            " and " + DBH.M_CB + " =? " +
                            " order by " + DBH.M_DT + " asc",
                    new String[]{String.valueOf(time), "0"});
        }

        else if (code == -1) {
            curs = sdb.rawQuery("select * from " + DBH.TB_MAIN +
                            " where " + DBH.M_DT + " <? " +
                            " and " + DBH.M_CB + " =? " +
                            " order by " + DBH.M_DT + " desc",
                    new String[]{String.valueOf(time), "0"});
        }

        else if (code == -2){
            curs = sdb.rawQuery("select * from " + DBH.TB_MAIN +
                            " where " + DBH.M_CB + " = 1 " +
                            " order by " + DBH.M_DT + " desc", null);

        }

        else
            curs = sdb.rawQuery("select * from " + DBH.TB_MAIN +
                            " where " + DBH.M_IDL + " =? " +
                            " order by " + DBH.M_DT + " asc",
                    new String[]{String.valueOf(code)});
        return curs;
    }

    public Cursor getItem(long id){
        Cursor c = sdb.rawQuery("select * from " + DBH.TB_MAIN + " where " +
                DBH.M_ID + "=?", new String[]{String.valueOf(id)});
        return c;
    }

    public long addItem(String sNm, long dt, long idsp, int cbState){
        ContentValues cv = new ContentValues();
        cv.put(DBH.M_TITLE, sNm);
        cv.put(DBH.M_DT, dt);
        cv.put(DBH.M_IDL, idsp);
        cv.put(DBH.M_CB, cbState);
        long idc = sdb.insert(DBH.TB_MAIN, null, cv);
        return idc;
    }

    public void updItem(long id, String sNm, long dt, long idsp, int cbState){

        ContentValues cv = new ContentValues();
        cv.put(DBH.M_TITLE, sNm);
        cv.put(DBH.M_DT, dt);
        cv.put(DBH.M_IDL, idsp);
        cv.put(DBH.M_CB, cbState);
        sdb.update(DBH.TB_MAIN, cv, DBH.M_ID + " = " + String.valueOf(id), null);
    }

    public void delItem(long id){
        sdb.delete(DBH.TB_MAIN, DBH.M_ID + " = " + String.valueOf(id), null);
    }

    public Cursor getLists(){
        Cursor userCursor = sdb.rawQuery("select * from " + DBH.TB_LISTS, null);
        return userCursor;
    }

    public void addList(String value){
        ContentValues cv = new ContentValues();
        cv.put(DBH.L_TITLE, value);
        sdb.insert(DBH.TB_LISTS, null, cv);
    }

    public void updList(long id, String value){
        ContentValues cv = new ContentValues();
        cv.put(DBH.L_TITLE, value);
        sdb.update(DBH.TB_LISTS, cv, DBH.L_ID + " = " + String.valueOf(id), null);
    }

    public void delList(long id){
        sdb.delete(DBH.TB_LISTS, DBH.L_ID + " = " + String.valueOf(id), null);
    }

    public String getListName(long idl){
        Cursor c = sdb.rawQuery("select * from " + DBH.TB_LISTS +
                        " where " + DBH.L_ID + "=?",
                new String[]{String.valueOf(idl)});
        String name = "";
        if (c.moveToFirst()){
            name = c.getString(1);
        }
        return name;
    }

    public void delItemInList(long id){
        sdb.delete(DBH.TB_MAIN, DBH.M_IDL + " = ?", new String[]{String.valueOf(id)});
    }

    public String getTitle(long id){
        Cursor c = sdb.rawQuery("select * from " + DBH.TB_MAIN + " where " +
                DBH.M_ID + "=?", new String[]{String.valueOf(id)});
        c.moveToFirst();
        return c.getString(1);
    }

    public void addNtf(Context ctx, long idm, long time){

        String title = getTitle(idm);
        ContentValues cv = new ContentValues();
        cv.put(DBH.N_ID, idm);
        cv.put(DBH.N_DT, time);
        long idn = sdb.insert(DBH.TB_NTF, null, cv);

        NTF ntf = new NTF(ctx, title, time, idn);
        ntf.startNTF();

    }

    public void updNtf(Context ctx, long idn, long dt){

        Cursor c = sdb.rawQuery("select * from " + DBH.TB_MAIN + " where " +
                DBH.M_ID + "=?", new String[]{String.valueOf(idn)});
        c.moveToFirst();
        String title = c.getString(1);

        ContentValues cv = new ContentValues();
        cv.put(DBH.N_DT, dt);
        sdb.update(DBH.TB_NTF, cv, DBH.N_ID + " = " + String.valueOf(idn), null);

        NTF ntf = new NTF(ctx, title, dt, idn);
        ntf.startNTF();

    }

    public void delNtf(Context ctx, long idn){
        sdb.delete(DBH.TB_NTF, DBH.N_ID +  " = " + String.valueOf(idn), null);

        NTF ntf = new NTF(ctx, "", 1, idn);
        ntf.stopNTF();

    }

    public Cursor getNtf(long idn){
        Cursor c = sdb.rawQuery("select * from " + DBH.TB_NTF + " where " +
                DBH.N_ID + " = " + String.valueOf(idn), null);
        return c;
    }

    public Cursor getNtfs(){
        long time = Calendar.getInstance().getTimeInMillis();
        Cursor c = sdb.rawQuery("select * from " + DBH.TB_NTF + " where " +
                DBH.N_DT + " > " + String.valueOf(time), null);
        return c;
    }

}
