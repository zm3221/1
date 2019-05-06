package com.sikalenko.java1101.Organizer.app.src.main.java.com.example.app19;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NTF {

    PendingIntent pendingIntent;
    Context ctx;
    long time;
    long idn;

    public NTF(Context ctx, String text, long time, long idn){
        this.ctx = ctx;
        this.time = time;
        this.idn = idn;

        Notification.Builder builder = new Notification.Builder(ctx);
        builder.setContentTitle(text);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setSmallIcon(R.drawable.ic_dialog_time);

        Intent intent = new Intent(ctx, AddEditActivity.class);
        intent.putExtra("id", idn);
        PendingIntent pIntent = PendingIntent.getActivity(ctx, 0, intent, 0);
        builder.addAction(R.drawable.ic_menu_manage, "Изменить", pIntent);

        Intent notificationIntent = new Intent(ctx, NTFReceiver.class);
        notificationIntent.putExtra(NTFReceiver.NOTIFICATION_ID, idn);
        notificationIntent.setAction(String.valueOf(idn));
        notificationIntent.putExtra(NTFReceiver.NOTIFICATION, builder.build());
        pendingIntent = PendingIntent.getBroadcast
                (ctx, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void startNTF(){
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    public void stopNTF(){
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}


