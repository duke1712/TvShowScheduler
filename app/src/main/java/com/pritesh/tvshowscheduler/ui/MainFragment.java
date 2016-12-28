package com.pritesh.tvshowscheduler.ui;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.pritesh.tvshowscheduler.Constants;
import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.TvShow;
import com.pritesh.tvshowscheduler.adapter.ChannelAdapter;
import com.pritesh.tvshowscheduler.data.ChannelColumn;
import com.pritesh.tvshowscheduler.data.ShowProvider;
import com.pritesh.tvshowscheduler.model.Channel;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    public static RecyclerView recyclerView;
    public   ChannelAdapter channelAdapter;
    public List<Channel> channelList;
    private Tracker mTracker;
    SearchView search;

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
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        TvShow application = (TvShow) MainActivity.app;
        mTracker = application.getDefaultTracker();
        channelList = new ArrayList<>();
        mTracker.setScreenName("MainFragment~");
//        MobileAds.initialize(MainActivity.context, "ca-app-pub-1508240473467237/4999036703");
//        AdView mAdView = (AdView) view.findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("F23DCC00AE6CBC85EBBCBCBBE0BA8F47")
//                .build();
//        mAdView.loadAd(adRequest);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        channelAdapter = new ChannelAdapter(channelList, getContext());
        search=(SearchView) view.findViewById(R.id.search);
        search.setQueryHint("SearchView");
        search.setIconified(false);
        search.clearFocus();
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

//                Toast.makeText(MainActivity.context, String.valueOf(hasFocus),
//                        Toast.LENGTH_SHORT).show();
            }
        });
        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
//                Toast.makeText(MainActivity.context,query,Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                List<Channel>newList=new ArrayList<Channel>();
                for(int i=0;i<262;i++)
                {
                    if(channelList.get(i).getDisplay_name().contains(newText))
                    {
                        newList.add(channelList.get(i));
                    }
                }

               channelAdapter=new ChannelAdapter(newList,getActivity().getApplicationContext());
                recyclerView.setAdapter(channelAdapter);
               // channelAdapter.data(newList);
                recyclerView.scrollToPosition(0);
//                Toast.makeText(MainActivity.context, newText,Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        Cursor cursor=MainActivity.context.getContentResolver().query(ShowProvider.Channels.CONTENT_URI,null,null,null ,null );
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            //Adding Channels to the adapter

         channelList.add(new Channel(cursor.getString(1),cursor.getString(0),cursor.getString(2)));
            cursor.move(1);
         //   channelList.add(new Channel(Constants.CHANNELS_NAME[i], Constants.CHANNELS[i], Constants.CHANNELS_URL[i]));
        }
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(channelAdapter);
        return view;
    }
    public static void change(Cursor cursor){
        cursor.moveToFirst();
        List<Channel> channels=new ArrayList<>();
        for(int i=0;i<cursor.getCount();i++)
        {
            channels.add(new Channel(cursor.getString(1),cursor.getString(0),cursor.getString(2)));
            cursor.move(1);
        }
        ChannelAdapter channelAdapter=new ChannelAdapter(channels,MainActivity.context);

        recyclerView.setAdapter(channelAdapter);
    }
}

