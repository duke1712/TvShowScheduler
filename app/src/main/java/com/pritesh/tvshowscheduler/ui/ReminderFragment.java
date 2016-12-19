package com.pritesh.tvshowscheduler.ui;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.adapter.ReminderAdapter;
import com.pritesh.tvshowscheduler.data.Database;
import com.pritesh.tvshowscheduler.data.ShowProvider;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment {

   static  RecyclerView reminderView;
    static ReminderAdapter reminderAdapter;
    public ReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reminder, container, false);
        reminderView=(RecyclerView)view.findViewById(R.id.reminderView);
        Cursor cursor=MainActivity.context.getContentResolver().query(ShowProvider.Shows.CONTENT_URI,null,null,null,null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.context);
        reminderView.setLayoutManager(mLayoutManager);
        reminderView.setItemAnimator(new DefaultItemAnimator());
        reminderAdapter=new ReminderAdapter(getContext(),cursor);
        reminderView.setAdapter(reminderAdapter);

        return  view;
    }
    public static void change(){
        Cursor cursor=MainActivity.context.getContentResolver().query(ShowProvider.Shows.CONTENT_URI,null,null,null,null);
        //reminderAdapter.notify();
        reminderAdapter.swap(cursor);
        reminderAdapter.notifyDataSetChanged();
    }

}
