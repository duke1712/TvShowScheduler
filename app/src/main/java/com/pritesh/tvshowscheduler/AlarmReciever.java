package com.pritesh.tvshowscheduler;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.pritesh.tvshowscheduler.data.Columns;
import com.pritesh.tvshowscheduler.data.ShowProvider;
import com.pritesh.tvshowscheduler.ui.DialogActivity;
import com.pritesh.tvshowscheduler.ui.ReminderFragment;

public class AlarmReciever extends WakefulBroadcastReceiver {
    public final String LOG = "com.pritesh";

    public AlarmReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG, "ALARM rec");
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
        String show = intent.getStringExtra(context.getString(R.string.show));
        long id = intent.getLongExtra(context.getString(R.string.id), -1);
        Intent intent1 = new Intent(context, DialogActivity.class);
        intent1.putExtra(context.getString(R.string.show), show);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String a[] = {String.valueOf(id)};
        int result = context.getContentResolver().delete(ShowProvider.Shows.CONTENT_URI, Columns._ID + "=?", a);
        ReminderFragment.change();
        context.startActivity(intent1);

    }
}
