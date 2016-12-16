package com.pritesh.tvshowscheduler.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pritesh.tvshowscheduler.AlarmReciever;
import com.pritesh.tvshowscheduler.data.Columns;
import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.data.ShowProvider;
import com.pritesh.tvshowscheduler.model.Shows;
import com.pritesh.tvshowscheduler.ui.ReminderFragment;
import com.pritesh.tvshowscheduler.ui.ShowsActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by prittesh on 14/12/16.
 */

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.MyViewHolder> {
    private List<Shows> showsList;
    Context context;
    public final String LOG="com.pritesh";
    public ShowsAdapter(List<Shows> showsList, Context context){
        this.showsList=showsList;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shows_list_item, parent, false);
        return new ShowsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Shows shows=showsList.get(position);
        holder.title.setText(shows.getTitle());
        holder.time.setText(shows.getTime());
        Picasso.with(context).load(shows.getUrl()).fit().into(holder.imageView);
        holder.reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar timeStamp=Calendar.getInstance();
                long id=timeStamp.getTimeInMillis();
                shows.setId(id);
//                ContentValues values=new ContentValues();
//                values.put(Columns._ID,id);
//                values.put(Columns.TITLE,shows.getTitle());
//                values.put(Columns.TIME,shows.getTime());
//                values.put(Columns.URL,shows.getUrl());
               // context.getContentResolver().insert(ShowProvider.Shows.CONTENT_URI,values);
                setTime(shows);
            }
        });
    }

    private void setTime(Shows shows) {
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        try {
            Date date=sdf.parse(shows.getTime());
            Calendar cal=Calendar.getInstance();
            //checking if the show is for tomorrow or after that
            if(ShowsActivity.getDate().equals("Tomorrow"))
                cal.add(Calendar.DATE,1);
            else if(ShowsActivity.getDate().equals("Day after Tomorrow"))
                cal.add(Calendar.DATE,2);
            //Setting the date and time of the show
            cal.set(Calendar.HOUR_OF_DAY,date.getHours());
            cal.set(Calendar.MINUTE,date.getMinutes());
//            cal.set(Calendar.HOUR_OF_DAY,12);
//            cal.set(Calendar.MINUTE,52);
            cal.set(Calendar.SECOND,0);


            //Content Values
            ContentValues values=new ContentValues();
            values.put(Columns._ID,shows.getId());
            values.put(Columns.TITLE,shows.getTitle());
            values.put(Columns.TIME,shows.getTime());
            values.put(Columns.URL,shows.getUrl());
            if(cal.after(Calendar.getInstance())) {
                Uri uri=context.getContentResolver().insert(ShowProvider.Shows.CONTENT_URI, values);
                setAlarm(shows,cal);
            }
            else
            {
                Toast.makeText(context,"Cannot make Timer for previous shows.",Toast.LENGTH_SHORT).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void setAlarm(Shows shows, Calendar cal) {
        Intent alarmIntent=new Intent(context, AlarmReciever.class);
        alarmIntent.setAction("ALARM");
        alarmIntent.putExtra("SHOW",shows.getTitle());
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,alarmIntent,0);
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        Log.d(LOG,"ALARM");
        ReminderFragment.change();
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title,time;
        public ImageView imageView;
        public Button reminder;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            time=(TextView)itemView.findViewById(R.id.time);
            imageView=(ImageView)itemView.findViewById(R.id.imageView3);
            reminder=(Button)itemView.findViewById(R.id.reminder);
        }
    }
}
