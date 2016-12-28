package com.pritesh.tvshowscheduler.ui;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.pritesh.tvshowscheduler.Constants;
import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.Utils;
import com.pritesh.tvshowscheduler.adapter.ShowsAdapter;
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

public class ShowsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static ShowsAdapter showsAdapter;
    static Spinner schedule_spinner;
    static ProgressDialog mProgressDialog;
    static OkHttpClient client = new OkHttpClient();
    RecyclerView recyclerView;
    String name, displayName;
    List<Shows> showsList = new ArrayList<>();
    public static Context context;
    private Toolbar toolbar;

    public static String getDate() {
        return schedule_spinner.getSelectedItem().toString();
    }

    static String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(this,About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);
        context=this;
        Intent intent = getIntent();
        name = intent.getStringExtra("CHANNEL_NAME");
        displayName = intent.getStringExtra("CHANNEL_DISPLAY_NAME");
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        mProgressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("Loading...");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        schedule_spinner = (Spinner) findViewById(R.id.date_spinner);
        //Initializing spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.schedule, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schedule_spinner.setAdapter(adapter);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(displayName);
        showsAdapter = new ShowsAdapter(showsList, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(showsAdapter);
        Bundle bundle = new Bundle();
        bundle.putString("NAME", name);
        getLoaderManager().initLoader(0, null, this);

        schedule_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // new getShowsTask().execute(name);
                if (schedule_spinner.getSelectedItem().toString().equals("Today")) {
                    start(0);
                } else if (schedule_spinner.getSelectedItem().toString().equals("Tomorrow"))
                    start(1);
                else {
                    start(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    void start(int i) {
        getLoaderManager().initLoader(i, null, this);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new getShowsTask(this, name);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        try {
            JSONObject root = new JSONObject(s);
            JSONArray list = root.getJSONArray(Constants.SHOW_LIST);
            int l = list.length();
            for (int i = 0; i < l; i++) {
                JSONObject show = (JSONObject) list.get(i);
                showsList.add(
                        new Shows(
                                show.getString(Constants.JSON_TITLE),
                                show.getString(Constants.JSON_TIME),
                                show.getString(Constants.JSON_URL)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
        showsAdapter.notifyDataSetChanged();
        mProgressDialog.cancel();
    }


    @Override
    public void onLoaderReset(Loader loader) {

    }

    public static class getShowsTask extends AsyncTaskLoader<String> {
        String data;
        String channel;

        public getShowsTask(Context context, String channel) {
            super(context);
            this.channel = channel;
        }

        @Override
        protected void onStartLoading() {
            mProgressDialog.show();
            forceLoad();
        }

        @Override
        public String loadInBackground() {
            String url = Constants.BASE_URL + channel + Constants.END_URL + Utils.getDate(getDate());
            try {
                data = run(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
    }
}
