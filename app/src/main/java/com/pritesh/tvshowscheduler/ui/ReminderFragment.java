package com.pritesh.tvshowscheduler.ui;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.adapter.ReminderAdapter;
import com.pritesh.tvshowscheduler.adapter.ReminderAdapter1;
import com.pritesh.tvshowscheduler.data.ShowProvider;
import com.pritesh.tvshowscheduler.model.Shows;

import java.util.ArrayList;
import java.util.List;

import static com.pritesh.tvshowscheduler.adapter.ShowsAdapter.ACTION_DATA_UPDATED;
import static com.pritesh.tvshowscheduler.ui.MainActivity.context;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment {

    static RecyclerView reminderView;
    static ReminderAdapter1 reminderAdapter;
    static List<Shows> showsList;
    public ReminderFragment() {
        // Required empty public constructor
    }

    public static void change() {
        Cursor cursor = context.getContentResolver().query(ShowProvider.Shows.CONTENT_URI, null, null, null, null);
       // ReminderAdapter.swap(cursor);
        showsList=toShows(cursor);
        reminderAdapter=new ReminderAdapter1(context,showsList);
        reminderView.setAdapter(reminderAdapter);
        reminderAdapter.notifyDataSetChanged();
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
        context.sendBroadcast(dataUpdatedIntent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        reminderView = (RecyclerView) view.findViewById(R.id.reminderView);
        Cursor cursor = context.getContentResolver().query(ShowProvider.Shows.CONTENT_URI, null, null, null, null);
        showsList=toShows(cursor);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        reminderView.setLayoutManager(mLayoutManager);
        reminderView.setItemAnimator(new DefaultItemAnimator());
        reminderAdapter = new ReminderAdapter1(getContext(), showsList);
        reminderView.setAdapter(reminderAdapter);
        return view;
    }
    static List<Shows> toShows(Cursor cursor)
    {
        List<Shows> shows=new ArrayList<>(cursor.getCount());
        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++)
        {
            Shows show=new Shows(cursor.getString(1),cursor.getString(3),cursor.getString(2),cursor.getInt(0));
            shows.add(show);
            cursor.moveToNext();
        }
        return shows;
    }

}
