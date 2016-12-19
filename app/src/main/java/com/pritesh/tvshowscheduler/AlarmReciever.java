package com.pritesh.tvshowscheduler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;

import com.pritesh.tvshowscheduler.adapter.ReminderAdapter;
import com.pritesh.tvshowscheduler.data.Columns;
import com.pritesh.tvshowscheduler.data.ShowProvider;
import com.pritesh.tvshowscheduler.model.Shows;
import com.pritesh.tvshowscheduler.ui.DialogActivity;
import com.pritesh.tvshowscheduler.ui.ReminderFragment;
import com.pritesh.tvshowscheduler.ui.ShowsActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReciever extends WakefulBroadcastReceiver {
    public final String LOG="com.pritesh";

    public AlarmReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG,"ALARM rec");
    //        NotificationManager notificationManager = (NotificationManager)context.
    //                getSystemService(NOTIFICATION_SERVICE);
    //        Intent intent = new Intent(this, NotificationReceiver.class);
    //        PendingIntent pIntent = PendingIntent.getActivity(context,0, intent, 0);
    //        Notification n  = new Notification.Builder(context)
    //                .setContentTitle(context.getResources().getString(R.string.NOTIFICATION_TITLE))
    //                .setContentTitle("Reminder for "+intent.getStringExtra("SHOW_NAME"))
    //                .setSmallIcon(R.mipmap.ic_launcher)
    //                .setContentIntent(pIntent)
        //int id=context.getResources().getIdentifier("alarm.mp3","raw",context.getPackageName());
        AudioPlayer.playAudio(context);
        String show=intent.getStringExtra("SHOW");
        long id=intent.getLongExtra("ID",-1);
        Intent intent1=new Intent(context,DialogActivity.class);
        intent1.putExtra("SHOW",show);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String a[]={String.valueOf(id)};
        int result=context.getContentResolver().delete(ShowProvider.Shows.CONTENT_URI, Columns._ID+"=?",a);
        ReminderFragment.change();
        context.startActivity(intent1);

    }
}
