package com.pritesh.tvshowscheduler.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pritesh.tvshowscheduler.AlarmReciever;
import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.data.Columns;
import com.pritesh.tvshowscheduler.data.ShowProvider;
import com.pritesh.tvshowscheduler.model.Shows;
import com.pritesh.tvshowscheduler.ui.ReminderFragment;

import java.util.List;

/**
 * Created by prittesh on 28/12/16.
 */

public class ReminderAdapter1 extends RecyclerView.Adapter<ReminderAdapter1.MyViewHolder> {

    List<Shows> showsList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, time;
        Button button;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title1);
            time = (TextView) view.findViewById(R.id.time1);
            button=(Button)view.findViewById(R.id.cancel);
        }


    }

    public ReminderAdapter1(Context context,List<Shows> list) {
        this.context=context;
        showsList=list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_list_item, parent,false);

        return new ReminderAdapter1.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Shows shows=showsList.get(position);
        holder.title.setText(showsList.get(position).getTitle());
        holder.time.setText(showsList.get(position).getTime());
        final int pos=position;
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent=new Intent(context, AlarmReciever.class);
                cancelIntent.setAction(context.getString(R.string.alarm));
                cancelIntent.putExtra(context.getString(R.string.show), showsList.get(pos).getTitle());
                cancelIntent.putExtra(context.getString(R.string.id), Long.parseLong(String.valueOf(shows.getId())));
                AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                PendingIntent cancel=PendingIntent.getBroadcast(context, shows.getId(),cancelIntent,0);
                cancel.cancel();
                alarmManager.cancel(cancel);
                String a[]={String.valueOf(shows.getId())};



                int b=context.getContentResolver().delete(ShowProvider.Shows.CONTENT_URI, Columns._ID+"=?",a);
                Cursor c=context.getContentResolver().query(ShowProvider.Shows.CONTENT_URI,null,null,null,null);
                ReminderFragment.change();
            }
        });
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }
}
