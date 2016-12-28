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
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pritesh.tvshowscheduler.AlarmReciever;
import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.data.Columns;
import com.pritesh.tvshowscheduler.data.ShowProvider;
import com.pritesh.tvshowscheduler.ui.ReminderFragment;
import com.pritesh.tvshowscheduler.ui.ShowsActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by prittesh on 16/12/16.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder> {
    static CursorAdapter mCursorAdapter;
    static int pos;
    Context mContext;

    public ReminderAdapter(Context context, Cursor cursor) {
        mContext = context;

        mCursorAdapter = new CursorAdapter(mContext, cursor, 0) {
          //  ImageView imageView;
            TextView title, time;
            Button button;

            @Override
            public View newView(final Context context, final Cursor cursor, ViewGroup parent) {
                // Inflate the view here
                View view = LayoutInflater.from(context).inflate(R.layout.reminder_list_item, parent);
            //    imageView = (ImageView) view.findViewById(R.id.imageView4);
//                title = (TextView) view.findViewById(R.id.title1);
//                time = (TextView) view.findViewById(R.id.time1);
//                title.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(context,cursor.getString(1),Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                button=(Button)view.findViewById(R.id.cancel);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent cancelIntent=new Intent(context, AlarmReciever.class);
//                        cancelIntent.setAction(context.getString(R.string.alarm));
//                        cancelIntent.putExtra(context.getString(R.string.show), cursor.getString(1));
//                        cancelIntent.putExtra(context.getString(R.string.id), Long.parseLong(String.valueOf(cursor.getInt(0))));
//                        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//                        PendingIntent cancel=PendingIntent.getBroadcast(context, cursor.getInt(0),cancelIntent,0);
//                        cancel.cancel();
//                        alarmManager.cancel(cancel);
//                        String a[]={String.valueOf(cursor.getInt(0))};
//
//
//
//                        int b=context.getContentResolver().delete(ShowProvider.Shows.CONTENT_URI, Columns._ID+"=?",a);
//                        Cursor c=context.getContentResolver().query(ShowProvider.Shows.CONTENT_URI,null,null,null,null);
//                        ReminderFragment.change();
//                    }
//                });
                return view;
            }

            @Override
            public void bindView(View view, final Context context, final Cursor cursor) {
                // Binding operations
                time.setText(cursor.getString(3));
                title.setText(cursor.getString(1));


              //  Picasso.with(context).load(cursor.getString(2)).into(imageView);

            }
        };
    }

    public static void swap(Cursor c) {
        mCursorAdapter.swapCursor(null);
        mCursorAdapter.swapCursor(c);

        mCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mCursorAdapter.getCursor().moveToPosition(position);
       // pos=position;
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCursor().getCount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
