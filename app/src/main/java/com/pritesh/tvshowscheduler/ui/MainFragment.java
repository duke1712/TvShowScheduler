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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.pritesh.tvshowscheduler.Constants;
import com.pritesh.tvshowscheduler.TvShow;
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
    private Tracker mTracker;

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
        TvShow application = (TvShow) MainActivity.app;
        mTracker = application.getDefaultTracker();
        channelList=new ArrayList<>();
        mTracker.setScreenName("MainFragment~");
        MobileAds.initialize(MainActivity.context, "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
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

