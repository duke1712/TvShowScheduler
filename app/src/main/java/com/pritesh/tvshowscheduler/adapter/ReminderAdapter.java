package com.pritesh.tvshowscheduler.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pritesh.tvshowscheduler.R;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

/**
 * Created by prittesh on 16/12/16.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder> {
    CursorAdapter mCursorAdapter;

    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public ReminderAdapter(Context context, Cursor cursor) {
        mContext = context;

        mCursorAdapter = new CursorAdapter(mContext, cursor, 0) {
            ImageView imageView;
            TextView title,time;
            Button button;

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                // Inflate the view here
                View view= LayoutInflater.from(context).inflate(R.layout.reminder_list_item,parent);
                imageView=(ImageView)view.findViewById(R.id.imageView4);
                title=(TextView)view.findViewById(R.id.title1);
                time=(TextView) view.findViewById(R.id.time1);
                button=(Button)view.findViewById(R.id.reminder1);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Binding operations
                time.setText(cursor.getString(3));
                title.setText(cursor.getString(1));
                Picasso.with(context).load(cursor.getString(2)).into(imageView);
            }
        };
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mCursorAdapter.newView(mContext,mCursorAdapter.getCursor(),null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mCursorAdapter.getCursor().moveToPosition(position);
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());


    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCursor().getCount();
    }
}
