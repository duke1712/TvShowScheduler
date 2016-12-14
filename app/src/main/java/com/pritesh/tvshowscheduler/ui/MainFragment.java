package com.pritesh.tvshowscheduler.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pritesh.tvshowscheduler.Constants;
import com.pritesh.tvshowscheduler.Utils;
import com.pritesh.tvshowscheduler.model.Channel;
import com.pritesh.tvshowscheduler.adapter.ChannelAdapter;
import com.pritesh.tvshowscheduler.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainFragment extends Fragment {
    public RecyclerView recyclerView;
    public ChannelAdapter channelAdapter;
    public List<Channel> channelList;

    public MainFragment() {
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
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        channelList=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        channelAdapter=new ChannelAdapter(channelList,getContext());
        for(int i=0;i<10;i++)
            //Adding Channels to the adapter
        channelList.add(new Channel(Constants.CHANNELS_NAME[i],Constants.CHANNELS[i],Constants.CHANNELS_URL[i]));

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(channelAdapter);

        return view;
    }


}

