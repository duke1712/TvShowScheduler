package com.pritesh.tvshowscheduler.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.pritesh.tvshowscheduler.Constants;
import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.Utils;
import com.pritesh.tvshowscheduler.adapter.ChannelAdapter;
import com.pritesh.tvshowscheduler.adapter.ShowsAdapter;
import com.pritesh.tvshowscheduler.model.Channel;
import com.pritesh.tvshowscheduler.model.Shows;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.security.AccessController.getContext;

public class ShowsActivity extends AppCompatActivity{
    private Toolbar toolbar;
    static Spinner schedule_spinner;
    RecyclerView recyclerView;
    ShowsAdapter showsAdapter;
    ProgressDialog mProgressDialog;
    OkHttpClient client = new OkHttpClient();
    List<Shows> showsList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);
        Intent intent=getIntent();
        final String name=intent.getStringExtra("CHANNEL_NAME");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mProgressDialog=new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("Loading...");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view1);
        schedule_spinner=(Spinner)findViewById(R.id.date_spinner);
        //Initializing spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.schedule, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schedule_spinner.setAdapter(adapter);

        showsAdapter=new ShowsAdapter(showsList,getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(showsAdapter);
        schedule_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new getShowsTask().execute(name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public static String getDate()
    {
        return schedule_spinner.getSelectedItem().toString();
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public class getShowsTask extends AsyncTask<String,Integer ,String> {
        String data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... channels) {
            String url= Constants.BASE_URL+channels[0]+Constants.END_URL+ Utils.getDate(getDate());
            try {
               data =run(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject root=new JSONObject(s);
                JSONArray list=root.getJSONArray(Constants.SHOW_LIST);
                int l=list.length();
                for(int i=0;i<l;i++)
                {
                    JSONObject show=(JSONObject) list.get(i);
                    showsList.add(
                            new Shows(
                            show.getString(Constants.JSON_TITLE),
                            show.getString(Constants.JSON_TIME),
                            show.getString(Constants.JSON_URL)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showsAdapter.notifyDataSetChanged();
            mProgressDialog.cancel();
            //super.onPostExecute(s);
        }
    }
}
