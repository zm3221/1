package com.sikalenko.java1101.Organizer.app.src.main.java.com.example.app19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

public class BootReceiver extends BroadcastReceiver {

    DB db;

    @Override
    public void onReceive(Context context, Intent intent) {
       if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
           db = new DB(context);
           db.open();
           Cursor c = db.getNtfs();
           c.moveToFirst();
           while (c.moveToNext()) {
               long id = c.getLong(0);
               long dt = c.getLong(1);
               String title = db.getTitle(id);
               NTF ntf = new NTF(context, title, dt, id);
               ntf.startNTF();
           }
           c.close();
           db.close();
        }
    }
}


