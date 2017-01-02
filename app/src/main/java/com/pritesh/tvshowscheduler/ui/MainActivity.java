package com.pritesh.tvshowscheduler.ui;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.data.ChannelColumn;
import com.pritesh.tvshowscheduler.data.ShowProvider;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    public static Application app;
    public static Context context;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Spinner channelSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();
        Fabric.with(this, new Crashlytics());
        app = getApplication();
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        channelSpinner=(Spinner)findViewById(R.id.channel_spinner);
        ArrayAdapter<CharSequence> category=ArrayAdapter.createFromResource(context,R.array.category,R.layout.spinner_item);
        channelSpinner.setAdapter(category);
        channelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String a[]={(String) channelSpinner.getAdapter().getItem(i)};
                Cursor c;
                if(a[0].equals("All")) {
                    c = getContentResolver().query(ShowProvider.Channels.CONTENT_URI, null, null, null, null);
                }
                else
                c=getContentResolver().query(ShowProvider.Channels.CONTENT_URI,null,ChannelColumn.CATEGORY+"=?",a,null);
                MainFragment.change(c);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        MobileAds.initialize(MainActivity.context, "ca-app-pub-1508240473467237/4999036703");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("F23DCC00AE6CBC85EBBCBCBBE0BA8F47")
                .build();
     //   mAdView.setAdSize(AdSize.BANNER);
        mAdView.loadAd(adRequest);
        SharedPreferences pref=context.getSharedPreferences("pref",0);
        SharedPreferences.Editor editor = pref.edit();
        if(!pref.contains("Data"))
        {
            editor.putBoolean("Data",true).commit();
            createData();
        }

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
            case R.id.feedback:
                String url = "https://goo.gl/forms/NDNqPuCGhmYf7pVF3";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), getApplicationContext().getString(R.string.channels));
        adapter.addFragment(new ReminderFragment(), getString(R.string.reminders));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    private void createData() {
       // ArrayList<ContentValues> contentValues=new ArrayList<>();
        ContentValues[] values = new ContentValues[262];
        for(int i=0;i<262;i++)
        values[i]=new ContentValues();
        values[0].put(ChannelColumn._ID,"%26-pictures");
        values[0].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/&pictures.png");
        values[0].put(ChannelColumn.CATEGORY,"Movies");

        values[1].put(ChannelColumn._ID,"asianet-movies");
        values[1].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/asianet-movies.png");
        values[1].put(ChannelColumn.CATEGORY,"Movies");

        values[2].put(ChannelColumn._ID,"b4u-movies");
        values[2].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/b4umovies.png");
        values[2].put(ChannelColumn.CATEGORY,"Movies");

        values[3].put(ChannelColumn._ID,"cinema-tv");
        values[3].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/cinema%20tv.png");
        values[3].put(ChannelColumn.CATEGORY,"Movies");

        values[4].put(ChannelColumn._ID,"filmy");
        values[4].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/filmy.png");
        values[4].put(ChannelColumn.CATEGORY,"Movies");

        values[5].put(ChannelColumn._ID,"gemini-movies");
        values[5].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/gemini%20movies_.png");
        values[5].put(ChannelColumn.CATEGORY,"Movies");

        values[6].put(ChannelColumn._ID,"hbo");
        values[6].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Movies/HBO.png");
        values[6].put(ChannelColumn.CATEGORY,"Movies");

        values[7].put(ChannelColumn._ID,"india-talkies");
        values[7].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/India-Talkies_.png");
        values[7].put(ChannelColumn.CATEGORY,"Movies");

        values[8].put(ChannelColumn._ID,"jalsha-movies");
        values[8].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movie/jalsha_movies.png");
        values[8].put(ChannelColumn.CATEGORY,"Movies");

        values[9].put(ChannelColumn._ID,"jaya-movie");
        values[9].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Movies/J-Movie.png");
        values[9].put(ChannelColumn.CATEGORY,"Movies");

        values[10].put(ChannelColumn._ID,"kiran-tv");
        values[10].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/kirantv.png");
        values[10].put(ChannelColumn.CATEGORY,"Movies");

        values[11].put(ChannelColumn._ID,"ktv");
        values[11].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/ktv_.png");
        values[11].put(ChannelColumn.CATEGORY,"Movies");

        values[12].put(ChannelColumn._ID,"maa-movies");
        values[12].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/maamovies_.png");
        values[12].put(ChannelColumn.CATEGORY,"Movies");

        values[13].put(ChannelColumn._ID,"movies-now");
        values[13].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/Movies%20Now.png");
        values[13].put(ChannelColumn.CATEGORY,"Movies");

        values[14].put(ChannelColumn._ID,"movies-ok");
        values[14].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/movies-ok.png");
        values[14].put(ChannelColumn.CATEGORY,"Movies");

        values[15].put(ChannelColumn._ID,"raj-digital-plus");
        values[15].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/Raj-Digital-Plus.png");
        values[15].put(ChannelColumn.CATEGORY,"Movies");

        values[16].put(ChannelColumn._ID,"romedy-now");
        values[16].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/romedy-now.png");
        values[16].put(ChannelColumn.CATEGORY,"Movies");

        values[17].put(ChannelColumn._ID,"sony-max");
        values[17].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Movies/Sony-Max.png");
        values[17].put(ChannelColumn.CATEGORY,"Movies");

        values[18].put(ChannelColumn._ID,"sony-max-2");
        values[18].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/ch/a/r/ar8kle1i_ej_3.gif");
        values[18].put(ChannelColumn.CATEGORY,"Movies");

        values[19].put(ChannelColumn._ID,"sony-pix");
        values[19].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/Sony%20Pix1.png");
        values[19].put(ChannelColumn.CATEGORY,"Movies");

        values[20].put(ChannelColumn._ID,"star-gold");
        values[20].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/stargold.png");
        values[20].put(ChannelColumn.CATEGORY,"Movies");

        values[21].put(ChannelColumn._ID,"star-gold-hd");
        values[21].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/stargoldHD.png");
        values[21].put(ChannelColumn.CATEGORY,"Movies");

        values[22].put(ChannelColumn._ID,"star-movies");
        values[22].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Movies/Star-Movies.png");
        values[22].put(ChannelColumn.CATEGORY,"Movies");

        values[23].put(ChannelColumn._ID,"star-movies-action");
        values[23].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/Star-Movies-Action.png");
        values[23].put(ChannelColumn.CATEGORY,"Movies");

        values[24].put(ChannelColumn._ID,"star-movies-hd");
        values[24].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/Star-Movies-HD.png");
        values[24].put(ChannelColumn.CATEGORY,"Movies");

        values[25].put(ChannelColumn._ID,"udaya-movies");
        values[25].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/Udaya-Movies.png");
        values[25].put(ChannelColumn.CATEGORY,"Movies");

        values[26].put(ChannelColumn._ID,"utv-action");
        values[26].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Movies/UTV-Action.png");
        values[26].put(ChannelColumn.CATEGORY,"Movies");

        values[27].put(ChannelColumn._ID,"utv-movies");
        values[27].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/utv-movies.png");
        values[27].put(ChannelColumn.CATEGORY,"Movies");

        values[28].put(ChannelColumn._ID,"wb");
        values[28].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Movies/WB.png");
        values[28].put(ChannelColumn.CATEGORY,"Movies");

        values[29].put(ChannelColumn._ID,"zee-action");
        values[29].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/zee-action_.png");
        values[29].put(ChannelColumn.CATEGORY,"Movies");

        values[30].put(ChannelColumn._ID,"zee-cinema");
        values[30].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/ZC.png");
        values[30].put(ChannelColumn.CATEGORY,"Movies");

        values[31].put(ChannelColumn._ID,"zee-cinema-hd");
        values[31].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movie/zee_cinema_hd.png");
        values[31].put(ChannelColumn.CATEGORY,"Movies");

        values[32].put(ChannelColumn._ID,"zee-classic");
        values[32].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/zee-classic.png");
        values[32].put(ChannelColumn.CATEGORY,"Movies");

        values[33].put(ChannelColumn._ID,"zee-studio");
        values[33].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Movies/Zee-Studio.png");
        values[33].put(ChannelColumn.CATEGORY,"Movies");

        values[34].put(ChannelColumn._ID,"zee-studio-hd");
        values[34].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/ZSHD.png");
        values[34].put(ChannelColumn.CATEGORY,"Movies");

        values[35].put(ChannelColumn._ID,"zee-talkies");
        values[35].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/movies/Zee-Talkies.png");
        values[35].put(ChannelColumn.CATEGORY,"Movies");

        values[36].put(ChannelColumn._ID,"akash-bangla");
        values[36].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Akash-Bangla_.png");
        values[36].put(ChannelColumn.CATEGORY,"Entertainment");

        values[37].put(ChannelColumn._ID,"amrita-tv");
        values[37].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Amrita-Tv.png");
        values[37].put(ChannelColumn.CATEGORY,"Entertainment");

        values[38].put(ChannelColumn._ID,"anjan-tv");
        values[38].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Anjan-Tv.png");
        values[38].put(ChannelColumn.CATEGORY,"Entertainment");

        values[39].put(ChannelColumn._ID,"arirang");
        values[39].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/arirang.png");
        values[39].put(ChannelColumn.CATEGORY,"Entertainment");

        values[40].put(ChannelColumn._ID,"asianet");
        values[40].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/Asianet.png");
        values[40].put(ChannelColumn.CATEGORY,"Entertainment");

        values[41].put(ChannelColumn._ID,"asianet-plus");
        values[41].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/Asianet-Plus.png");
        values[41].put(ChannelColumn.CATEGORY,"Entertainment");

        values[42].put(ChannelColumn._ID,"axn");
        values[42].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/axn.png");
        values[42].put(ChannelColumn.CATEGORY,"Entertainment");

        values[43].put(ChannelColumn._ID,"big-magic");
        values[43].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Big%20magic%20new%20black%20logo.png");
        values[43].put(ChannelColumn.CATEGORY,"Entertainment");

        values[44].put(ChannelColumn._ID,"big-rtl-thrill");
        values[44].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/BIG-RTL-Thrill.png");
        values[44].put(ChannelColumn.CATEGORY,"Entertainment");

        values[45].put(ChannelColumn._ID,"bindass");
        values[45].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Bindass.png");
        values[45].put(ChannelColumn.CATEGORY,"Entertainment");

        values[46].put(ChannelColumn._ID,"bindass-play");
        values[46].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/BP.png");
        values[46].put(ChannelColumn.CATEGORY,"Entertainment");

        values[47].put(ChannelColumn._ID,"channel-[v]");
        values[47].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Channel-[V].png");
        values[47].put(ChannelColumn.CATEGORY,"Entertainment");

        values[48].put(ChannelColumn._ID,"colors");
        values[48].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Colors%202.png");
        values[48].put(ChannelColumn.CATEGORY,"Entertainment");

        values[49].put(ChannelColumn._ID,"colors-hd");
        values[49].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/colors_hd.png");
        values[49].put(ChannelColumn.CATEGORY,"Entertainment");

        values[50].put(ChannelColumn._ID,"dd-bharati");
        values[50].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/DD-Bharati.png");
        values[50].put(ChannelColumn.CATEGORY,"Entertainment");

        values[51].put(ChannelColumn._ID,"dd-india");
        values[51].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/DD-India_.png");
        values[51].put(ChannelColumn.CATEGORY,"Entertainment");

        values[52].put(ChannelColumn._ID,"dd-national");
        values[52].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/DD-National.png");
        values[52].put(ChannelColumn.CATEGORY,"Entertainment");

        values[53].put(ChannelColumn._ID,"dd-urdu");
        values[53].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/DD-Urdu_.png");
        values[53].put(ChannelColumn.CATEGORY,"Entertainment");

        values[54].put(ChannelColumn._ID,"dhamaal-tv");
        values[54].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Dhamaal-TV.png");
        values[54].put(ChannelColumn.CATEGORY,"Entertainment");

        values[55].put(ChannelColumn._ID,"dy-365");
        values[55].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/DY-365.png");
        values[55].put(ChannelColumn.CATEGORY,"Entertainment");

        values[56].put(ChannelColumn._ID,"e-24");
        values[56].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/e24.png");
        values[56].put(ChannelColumn.CATEGORY,"Entertainment");

        values[57].put(ChannelColumn._ID,"epic");
        values[57].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/ch/v/o/votkdo2i_ek_3.gif");
        values[57].put(ChannelColumn.CATEGORY,"Entertainment");

        values[58].put(ChannelColumn._ID,"etv-rajasthan");
        values[58].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Etv-Rajasthan.png");
        values[58].put(ChannelColumn.CATEGORY,"Entertainment");

        values[59].put(ChannelColumn._ID,"etv-up");
        values[59].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Etv-UP.png");
        values[59].put(ChannelColumn.CATEGORY,"Entertainment");

        values[60].put(ChannelColumn._ID,"firangi");
        values[60].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/firangi.png");
        values[60].put(ChannelColumn.CATEGORY,"Entertainment");

        values[61].put(ChannelColumn._ID,"fox-life");
        values[61].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Fox-Life.png");
        values[61].put(ChannelColumn.CATEGORY,"Entertainment");

        values[62].put(ChannelColumn._ID,"fox-life-hd");
        values[62].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Fox-Life-HD.png");
        values[62].put(ChannelColumn.CATEGORY,"Entertainment");

        values[63].put(ChannelColumn._ID,"fx");
        values[63].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/FX.png");
        values[63].put(ChannelColumn.CATEGORY,"Entertainment");

        values[64].put(ChannelColumn._ID,"gemini-tv");
        values[64].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Gemini-Tv.png");
        values[64].put(ChannelColumn.CATEGORY,"Entertainment");

        values[65].put(ChannelColumn._ID,"imayam-tv");
        values[65].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Imayam.png");
        values[65].put(ChannelColumn.CATEGORY,"Entertainment");

        values[66].put(ChannelColumn._ID,"jan-tv");
        values[66].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Jan-TV.png");
        values[66].put(ChannelColumn.CATEGORY,"Entertainment");

        values[67].put(ChannelColumn._ID,"jaya-max");
        values[67].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Jaya-Max.png");
        values[67].put(ChannelColumn.CATEGORY,"Entertainment");

        values[68].put(ChannelColumn._ID,"jaya-plus");
        values[68].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Jaya-Plus.png");
        values[68].put(ChannelColumn.CATEGORY,"Entertainment");

        values[69].put(ChannelColumn._ID,"jaya-tv");
        values[69].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Jaya-Tv_.png");
        values[69].put(ChannelColumn.CATEGORY,"Entertainment");

        values[70].put(ChannelColumn._ID,"jeevan-tv");
        values[70].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Jeevan-Tv.png");
        values[70].put(ChannelColumn.CATEGORY,"Entertainment");

        values[71].put(ChannelColumn._ID,"kairali-tv");
        values[71].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Kairali-Tv.png");
        values[71].put(ChannelColumn.CATEGORY,"Entertainment");

        values[72].put(ChannelColumn._ID,"kairali-we-tv");
        values[72].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/we_tv.png");
        values[72].put(ChannelColumn.CATEGORY,"Entertainment");

        values[73].put(ChannelColumn._ID,"kalaignar-chithiram");
        values[73].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Kalaignar-Chithiram.png");
        values[73].put(ChannelColumn.CATEGORY,"Entertainment");

        values[74].put(ChannelColumn._ID,"kalaignar-sirippoli");
        values[74].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Kalaignar-Sirippoli.png");
        values[74].put(ChannelColumn.CATEGORY,"Entertainment");

        values[75].put(ChannelColumn._ID,"kalaignar-tv");
        values[75].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Kalaignar.png");
        values[75].put(ChannelColumn.CATEGORY,"Entertainment");

        values[76].put(ChannelColumn._ID,"khushboo-tv");
        values[76].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Khushboo-Tv.png");
        values[76].put(ChannelColumn.CATEGORY,"Entertainment");

        values[77].put(ChannelColumn._ID,"life-ok");
        values[77].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Life-Ok.png");
        values[77].put(ChannelColumn.CATEGORY,"Entertainment");

        values[78].put(ChannelColumn._ID,"life-ok-hd");
        values[78].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Life-Ok-HD.png");
        values[78].put(ChannelColumn.CATEGORY,"Entertainment");

        values[79].put(ChannelColumn._ID,"maa-gold");
        values[79].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Maa-Gold.png");
        values[79].put(ChannelColumn.CATEGORY,"Entertainment");

        values[80].put(ChannelColumn._ID,"maa-tv");
        values[80].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Maa-Tv.png");
        values[80].put(ChannelColumn.CATEGORY,"Entertainment");

        values[81].put(ChannelColumn._ID,"maiboli-tv");
        values[81].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/mailboli.png");
        values[81].put(ChannelColumn.CATEGORY,"Entertainment");

        values[82].put(ChannelColumn._ID,"makkal-tv");
        values[82].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Makkal-Tv.png");
        values[82].put(ChannelColumn.CATEGORY,"Entertainment");

        values[83].put(ChannelColumn._ID,"mazhavil-manorama");
        values[83].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Mazhavil-Manorama.png");
        values[83].put(ChannelColumn.CATEGORY,"Entertainment");

        values[84].put(ChannelColumn._ID,"mega-tv");
        values[84].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Mega-TV.png");
        values[84].put(ChannelColumn.CATEGORY,"Entertainment");

        values[85].put(ChannelColumn._ID,"mi-marathi");
        values[85].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/mi-marathi.png");
        values[85].put(ChannelColumn.CATEGORY,"Entertainment");

        values[86].put(ChannelColumn._ID,"om-bangla");
        values[86].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Om-Bangla.png");
        values[86].put(ChannelColumn.CATEGORY,"Entertainment");

        values[87].put(ChannelColumn._ID,"polimer-tv");
        values[87].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Polimer-Tv.png");
        values[87].put(ChannelColumn.CATEGORY,"Entertainment");

        values[88].put(ChannelColumn._ID,"raj-tv");
        values[88].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Raj-Tv.png");
        values[88].put(ChannelColumn.CATEGORY,"Entertainment");

        values[89].put(ChannelColumn._ID,"rishtey");
        values[89].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Rishtey.png");
        values[89].put(ChannelColumn.CATEGORY,"Entertainment");

        values[90].put(ChannelColumn._ID,"ruposhi-bangla");
        values[90].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Ruposhi-Bangla.png");
        values[90].put(ChannelColumn.CATEGORY,"Entertainment");

        values[91].put(ChannelColumn._ID,"saam-tv");
        values[91].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Saam-TV.png");
        values[91].put(ChannelColumn.CATEGORY,"Entertainment");

        values[92].put(ChannelColumn._ID,"sada-channel");
        values[92].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Sada-Channel.png");
        values[92].put(ChannelColumn.CATEGORY,"Entertainment");

        values[93].put(ChannelColumn._ID,"sahara-one");
        values[93].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Sahara-One.png");
        values[93].put(ChannelColumn.CATEGORY,"Entertainment");

        values[94].put(ChannelColumn._ID,"sony-entertainment-tv");
        values[94].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Sony_Entertainment_Television_Logo_2016.jpg");
        values[94].put(ChannelColumn.CATEGORY,"Entertainment");

        values[95].put(ChannelColumn._ID,"sony-pal");
        values[95].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Sony-Pal.png");
        values[95].put(ChannelColumn.CATEGORY,"Entertainment");

        values[96].put(ChannelColumn._ID,"sony-sab");
        values[96].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Sony-SAB.png");
        values[96].put(ChannelColumn.CATEGORY,"Entertainment");

        values[97].put(ChannelColumn._ID,"sony-tv-hd");
        values[97].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Sony_Entertainment_Television_Logo_2016.jpg");
        values[97].put(ChannelColumn.CATEGORY,"Entertainment");

        values[98].put(ChannelColumn._ID,"star-jalsha");
        values[98].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/star-jalsha.png");
        values[98].put(ChannelColumn.CATEGORY,"Entertainment");

        values[99].put(ChannelColumn._ID,"star-plus");
        values[99].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/star-plus.png");
        values[99].put(ChannelColumn.CATEGORY,"Entertainment");

        values[100].put(ChannelColumn._ID,"star-plus-hd");
        values[100].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Star-Plus-HD.png");
        values[100].put(ChannelColumn.CATEGORY,"Entertainment");

        values[101].put(ChannelColumn._ID,"star-pravah");
        values[101].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Star-Pravah.png");
        values[101].put(ChannelColumn.CATEGORY,"Entertainment");

        values[102].put(ChannelColumn._ID,"star-utsav");
        values[102].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/utsav.png");
        values[102].put(ChannelColumn.CATEGORY,"Entertainment");

        values[103].put(ChannelColumn._ID,"star-vijay");
        values[103].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Star-Vijay.png");
        values[103].put(ChannelColumn.CATEGORY,"Entertainment");

        values[104].put(ChannelColumn._ID,"star-world");
        values[104].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/Star-World.png");
        values[104].put(ChannelColumn.CATEGORY,"Entertainment");

        values[105].put(ChannelColumn._ID,"star-world-hd");
        values[105].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/Star-World-HD.png");
        values[105].put(ChannelColumn.CATEGORY,"Entertainment");

        values[106].put(ChannelColumn._ID,"star-world-premier-hd");
        values[106].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Star-World-Premiere-HD.png");
        values[106].put(ChannelColumn.CATEGORY,"Entertainment");

        values[107].put(ChannelColumn._ID,"sun-tv");
        values[107].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Sun-Tv.png");
        values[107].put(ChannelColumn.CATEGORY,"Entertainment");

        values[108].put(ChannelColumn._ID,"sun-tv-hd");
        values[108].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Sun-Tv-HD.png");
        values[108].put(ChannelColumn.CATEGORY,"Entertainment");

        values[109].put(ChannelColumn._ID,"surya-tv");
        values[109].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/surya-tv.png");
        values[109].put(ChannelColumn.CATEGORY,"Entertainment");

        values[110].put(ChannelColumn._ID,"tv5-monde");
        values[110].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/TV5Monde.PNG");
        values[110].put(ChannelColumn.CATEGORY,"Entertainment");

        values[111].put(ChannelColumn._ID,"udaya");
        values[111].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/udaya-tv.png");
        values[111].put(ChannelColumn.CATEGORY,"Entertainment");

        values[112].put(ChannelColumn._ID,"zee-bangla");
        values[112].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Zee-Bangla.png");
        values[112].put(ChannelColumn.CATEGORY,"Entertainment");

        values[113].put(ChannelColumn._ID,"zee-cafe");
        values[113].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Zee-Cafe.png");
        values[113].put(ChannelColumn.CATEGORY,"Entertainment");

        values[114].put(ChannelColumn._ID,"zee-kannada");
        values[114].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/Zee-Kannada.png");
        values[114].put(ChannelColumn.CATEGORY,"Entertainment");

        values[115].put(ChannelColumn._ID,"zee-marathi");
        values[115].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/zee-marathi.png");
        values[115].put(ChannelColumn.CATEGORY,"Entertainment");

        values[116].put(ChannelColumn._ID,"zee-salaam");
        values[116].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/zee_salaam.png");
        values[116].put(ChannelColumn.CATEGORY,"Entertainment");

        values[117].put(ChannelColumn._ID,"zee-smile");
        values[117].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Zee-Smile.png");
        values[117].put(ChannelColumn.CATEGORY,"Entertainment");

        values[118].put(ChannelColumn._ID,"zee-telugu");
        values[118].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Zee-Telugu.png");
        values[118].put(ChannelColumn.CATEGORY,"Entertainment");

        values[119].put(ChannelColumn._ID,"zee-tv");
        values[119].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Entertainment/ZEE-TV.png");
        values[119].put(ChannelColumn.CATEGORY,"Entertainment");

        values[120].put(ChannelColumn._ID,"zee-tv-hd");
        values[120].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Zee-TV-HD.png");
        values[120].put(ChannelColumn.CATEGORY,"Entertainment");

        values[121].put(ChannelColumn._ID,"zing");
        values[121].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/Zing.png");
        values[121].put(ChannelColumn.CATEGORY,"Entertainment");

        values[122].put(ChannelColumn._ID,"zoom");
        values[122].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/entertainment/zoom.png");
        values[122].put(ChannelColumn.CATEGORY,"Entertainment");

        values[123].put(ChannelColumn._ID,"adithya-tv");
        values[123].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Adithya-TV.png");
        values[123].put(ChannelColumn.CATEGORY,"Kids");

        values[124].put(ChannelColumn._ID,"animax");
        values[124].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/animax.png");
        values[124].put(ChannelColumn.CATEGORY,"Kids");

        values[125].put(ChannelColumn._ID,"baby-tv");
        values[125].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Baby-TV.png");
        values[125].put(ChannelColumn.CATEGORY,"Kids");

        values[126].put(ChannelColumn._ID,"cartoon-network");
        values[126].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/cartoon-network.png");
        values[126].put(ChannelColumn.CATEGORY,"Kids");

        values[127].put(ChannelColumn._ID,"chintu-tv");
        values[127].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Chintu-Tv.png");
        values[127].put(ChannelColumn.CATEGORY,"Kids");

        values[128].put(ChannelColumn._ID,"chutti-tv");
        values[128].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Chutti-TV.png");
        values[128].put(ChannelColumn.CATEGORY,"Kids");

        values[129].put(ChannelColumn._ID,"discovery-kids");
        values[129].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Discovery-Kids.png");
        values[129].put(ChannelColumn.CATEGORY,"Kids");

        values[130].put(ChannelColumn._ID,"disney-channel");
        values[130].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Disney-Channel.png");
        values[130].put(ChannelColumn.CATEGORY,"Kids");

        values[131].put(ChannelColumn._ID,"disney-junior");
        values[131].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/disnep_junior.png");
        values[131].put(ChannelColumn.CATEGORY,"Kids");

        values[132].put(ChannelColumn._ID,"kochu-tv");
        values[132].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Kochu-Tv.png");
        values[132].put(ChannelColumn.CATEGORY,"Kids");

        values[133].put(ChannelColumn._ID,"kushi-tv");
        values[133].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Kushi-Tv.png");
        values[133].put(ChannelColumn.CATEGORY,"Kids");

        values[134].put(ChannelColumn._ID,"nick-junior");
        values[134].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Nick-Junior.png");
        values[134].put(ChannelColumn.CATEGORY,"Kids");

        values[135].put(ChannelColumn._ID,"nickelodeon");
        values[135].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Nickelodeon.png");
        values[135].put(ChannelColumn.CATEGORY,"Kids");

        values[136].put(ChannelColumn._ID,"pogo");
        values[136].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/pogo.png");
        values[136].put(ChannelColumn.CATEGORY,"Kids");

        values[137].put(ChannelColumn._ID,"sonic");
        values[137].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Sonic.png");
        values[137].put(ChannelColumn.CATEGORY,"Kids");

        values[138].put(ChannelColumn._ID,"topper-tv");
        values[138].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/Topper-TV.png");
        values[138].put(ChannelColumn.CATEGORY,"Kids");

        values[139].put(ChannelColumn._ID,"zeeq");
        values[139].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/kids/ZeeQ.png");
        values[139].put(ChannelColumn.CATEGORY,"Kids");

        values[140].put(ChannelColumn._ID,"animal-planet");
        values[140].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/Animal-planet.png");
        values[140].put(ChannelColumn.CATEGORY,"Documentry");

        values[141].put(ChannelColumn._ID,"discovery-channel");
        values[141].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/Discovery-Channel.png");
        values[141].put(ChannelColumn.CATEGORY,"Documentry");

        values[142].put(ChannelColumn._ID,"discovery-hd");
        values[142].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/Discovery-HD.png");
        values[142].put(ChannelColumn.CATEGORY,"Documentry");

        values[143].put(ChannelColumn._ID,"discovery-id");
        values[143].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/ch/v/y/vysk8i3i_em_3.gif");
        values[143].put(ChannelColumn.CATEGORY,"Documentry");

        values[144].put(ChannelColumn._ID,"discovery-science");
        values[144].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Documentary/Discovery-Science.png");
        values[144].put(ChannelColumn.CATEGORY,"Documentry");

        values[145].put(ChannelColumn._ID,"discovery-tamil");
        values[145].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/Discovery-Tamil.png");
        values[145].put(ChannelColumn.CATEGORY,"Documentry");

        values[146].put(ChannelColumn._ID,"discovery-turbo");
        values[146].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/Discovery-Turbo.png");
        values[146].put(ChannelColumn.CATEGORY,"Documentry");

        values[147].put(ChannelColumn._ID,"history-tv-18");
        values[147].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/History-Tv-18.png");
        values[147].put(ChannelColumn.CATEGORY,"Documentry");

        values[148].put(ChannelColumn._ID,"history-tv-hd");
        values[148].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/History-TV-HD.png");
        values[148].put(ChannelColumn.CATEGORY,"Documentry");

        values[149].put(ChannelColumn._ID,"nat-geo-people");
        values[149].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/Nat-Geo-People.png");
        values[149].put(ChannelColumn.CATEGORY,"Documentry");

        values[150].put(ChannelColumn._ID,"nat-geo-people-hd");
        values[150].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/Nat-Geo-People-HD.png");
        values[150].put(ChannelColumn.CATEGORY,"Documentry");

        values[151].put(ChannelColumn._ID,"nat-geo-wild");
        values[151].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/Nat-Geo-Wild.png");
        values[151].put(ChannelColumn.CATEGORY,"Documentry");

        values[152].put(ChannelColumn._ID,"national-geographic-channel");
        values[152].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/National-Geographic-Channel.png");
        values[152].put(ChannelColumn.CATEGORY,"Documentry");

        values[153].put(ChannelColumn._ID,"national-geographic-channel-hd");
        values[153].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/National-Geographic-Channel-HD.png");
        values[153].put(ChannelColumn.CATEGORY,"Documentry");

        values[154].put(ChannelColumn._ID,"travel-xp-hd");
        values[154].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/documentary/Travel-XP-HD.png");
        values[154].put(ChannelColumn.CATEGORY,"Documentry");

        values[155].put(ChannelColumn._ID,"7-s-music");
        values[155].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/7-S-Music.png");
        values[155].put(ChannelColumn.CATEGORY,"Music");

        values[156].put(ChannelColumn._ID,"9x-jalwa");
        values[156].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/9x-jalwa.png");
        values[156].put(ChannelColumn.CATEGORY,"Music");

        values[157].put(ChannelColumn._ID,"9x-jhakaas");
        values[157].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/9X-Jhakaas.png");
        values[157].put(ChannelColumn.CATEGORY,"Music");

        values[158].put(ChannelColumn._ID,"9x-tashan");
        values[158].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/9X-Tashan.png");
        values[158].put(ChannelColumn.CATEGORY,"Music");

        values[159].put(ChannelColumn._ID,"9xm");
        values[159].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/9XM.png");
        values[159].put(ChannelColumn.CATEGORY,"Music");

        values[160].put(ChannelColumn._ID,"9xo");
        values[160].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/9xO.png");
        values[160].put(ChannelColumn.CATEGORY,"Music");

        values[161].put(ChannelColumn._ID,"b4u-music");
        values[161].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/b4u-music.png");
        values[161].put(ChannelColumn.CATEGORY,"Music");

        values[162].put(ChannelColumn._ID,"dhoom-music");
        values[162].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Dhoom-Music.png");
        values[162].put(ChannelColumn.CATEGORY,"Music");

        values[163].put(ChannelColumn._ID,"gemini-music");
        values[163].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Gemini-Music.png");
        values[163].put(ChannelColumn.CATEGORY,"Music");

        values[164].put(ChannelColumn._ID,"kalaignar-isai-aruvi");
        values[164].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Kalaignar-Isai-Aruvi.png");
        values[164].put(ChannelColumn.CATEGORY,"Music");

        values[165].put(ChannelColumn._ID,"maa-music");
        values[165].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Maa-Music.png");
        values[165].put(ChannelColumn.CATEGORY,"Music");

        values[166].put(ChannelColumn._ID,"mastiii");
        values[166].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/mastiii.png");
        values[166].put(ChannelColumn.CATEGORY,"Music");

        values[167].put(ChannelColumn._ID,"mega-musiq");
        values[167].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Mega-Musiq.png");
        values[167].put(ChannelColumn.CATEGORY,"Music");

        values[168].put(ChannelColumn._ID,"mtunes");
        values[168].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Mtunes.png");
        values[168].put(ChannelColumn.CATEGORY,"Music");

        values[169].put(ChannelColumn._ID,"mtv");
        values[169].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/mtv.png");
        values[169].put(ChannelColumn.CATEGORY,"Music");

        values[170].put(ChannelColumn._ID,"mtv-indies");
        values[170].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Mtv-Indies.png");
        values[170].put(ChannelColumn.CATEGORY,"Music");

        values[171].put(ChannelColumn._ID,"music-india");
        values[171].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/music-india.png");
        values[171].put(ChannelColumn.CATEGORY,"Music");

        values[172].put(ChannelColumn._ID,"music-xpress");
        values[172].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Music-Xpress.png");
        values[172].put(ChannelColumn.CATEGORY,"Music");

        values[173].put(ChannelColumn._ID,"nat-geo-music");
        values[173].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Nat-Geo-Music.png");
        values[173].put(ChannelColumn.CATEGORY,"Music");

        values[174].put(ChannelColumn._ID,"ptc-chak-de");
        values[174].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/PTC-Chak-De.png");
        values[174].put(ChannelColumn.CATEGORY,"Music");

        values[175].put(ChannelColumn._ID,"raj-music-karnataka");
        values[175].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Raj-Music-Karnataka.png");
        values[175].put(ChannelColumn.CATEGORY,"Music");

        values[176].put(ChannelColumn._ID,"raj-musix");
        values[176].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Raj-Musix.png");
        values[176].put(ChannelColumn.CATEGORY,"Music");

        values[177].put(ChannelColumn._ID,"raj-musix-telugu");
        values[177].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Raj-Musix-Telugu.png");
        values[177].put(ChannelColumn.CATEGORY,"Music");

        values[178].put(ChannelColumn._ID,"sangeet-bangla");
        values[178].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Sangeet-Bangla.png");
        values[178].put(ChannelColumn.CATEGORY,"Music");

        values[179].put(ChannelColumn._ID,"sangeet-bhojpuri");
        values[179].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Sangeet-Bhojpuri.png");
        values[179].put(ChannelColumn.CATEGORY,"Music");

        values[180].put(ChannelColumn._ID,"sony-mix");
        values[180].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/sony-mix.png");
        values[180].put(ChannelColumn.CATEGORY,"Music");

        values[181].put(ChannelColumn._ID,"ss-music");
        values[181].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/SS-Music.png");
        values[181].put(ChannelColumn.CATEGORY,"Music");

        values[182].put(ChannelColumn._ID,"sun-music");
        values[182].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/sun-music.png");
        values[182].put(ChannelColumn.CATEGORY,"Music");

        values[183].put(ChannelColumn._ID,"tarang-music");
        values[183].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/tarang-music.png");
        values[183].put(ChannelColumn.CATEGORY,"Music");

        values[184].put(ChannelColumn._ID,"udaya-music");
        values[184].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/Udaya-Music.png");
        values[184].put(ChannelColumn.CATEGORY,"Music");

        values[185].put(ChannelColumn._ID,"vh1");
        values[185].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/vh1.png");
        values[185].put(ChannelColumn.CATEGORY,"Music");

        values[186].put(ChannelColumn._ID,"z-etc");
        values[186].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/music/z-etc.png");
        values[186].put(ChannelColumn.CATEGORY,"Music");


        values[187].put(ChannelColumn._ID,"abp-news");
        values[187].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/ABPNews.png");
        values[187].put(ChannelColumn.CATEGORY,"News");

        values[188].put(ChannelColumn._ID,"aryan");
        values[188].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Aryan.png");
        values[188].put(ChannelColumn.CATEGORY,"News");

        values[189].put(ChannelColumn._ID,"asianet-news");
        values[189].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Asianet-News.png");
        values[189].put(ChannelColumn.CATEGORY,"News");

        values[190].put(ChannelColumn._ID,"bansal-news");
        values[190].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Bansal-News.png");
        values[190].put(ChannelColumn.CATEGORY,"News");

        values[191].put(ChannelColumn._ID,"bbc-world-news");
        values[191].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/BBC-World-News.png");
        values[191].put(ChannelColumn.CATEGORY,"News");

        values[192].put(ChannelColumn._ID,"bizz-news");
        values[192].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Bizz-News.png");
        values[192].put(ChannelColumn.CATEGORY,"News");

        values[193].put(ChannelColumn._ID,"captain-news");
        values[193].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Captain-News.png");
        values[193].put(ChannelColumn.CATEGORY,"News");

        values[194].put(ChannelColumn._ID,"channel-10");
        values[194].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Channel-10.png");
        values[194].put(ChannelColumn.CATEGORY,"News");

        values[195].put(ChannelColumn._ID,"cnbc-awaaz");
        values[195].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/cnbc-awaaz.png");
        values[195].put(ChannelColumn.CATEGORY,"News");

        values[196].put(ChannelColumn._ID,"cnbc-bajar");
        values[196].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/CNBC-Bajar.png");
        values[196].put(ChannelColumn.CATEGORY,"News");

        values[197].put(ChannelColumn._ID,"cnbc-tv18");
        values[197].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/CNBC-TV18.png");
        values[197].put(ChannelColumn.CATEGORY,"News");

        values[198].put(ChannelColumn._ID,"cnn");
        values[198].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/CNN.png");
        values[198].put(ChannelColumn.CATEGORY,"News");

        values[199].put(ChannelColumn._ID,"dd-news");
        values[199].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/DD-News.png");
        values[199].put(ChannelColumn.CATEGORY,"News");

        values[200].put(ChannelColumn._ID,"dilli-aaj-tak");
        values[200].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/dilli-aaj-tak.png");
        values[200].put(ChannelColumn.CATEGORY,"News");

        values[201].put(ChannelColumn._ID,"etv-2");
        values[201].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Etv-2.png");
        values[201].put(ChannelColumn.CATEGORY,"News");

        values[202].put(ChannelColumn._ID,"focus-tv");
        values[202].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/News/Focus-Tv.png");
        values[202].put(ChannelColumn.CATEGORY,"News");

        values[203].put(ChannelColumn._ID,"gnn-news");
        values[203].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/GNN-News.png");
        values[203].put(ChannelColumn.CATEGORY,"News");

        values[204].put(ChannelColumn._ID,"ibn-lokmat");
        values[204].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/IBN-Lokmat.png");
        values[204].put(ChannelColumn.CATEGORY,"News");

        values[205].put(ChannelColumn._ID,"india-news");
        values[205].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/india-news.png");
        values[205].put(ChannelColumn.CATEGORY,"News");

        values[206].put(ChannelColumn._ID,"india-tv");
        values[206].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/India-TV.png");
        values[206].put(ChannelColumn.CATEGORY,"News");

        values[207].put(ChannelColumn._ID,"indiavision");
        values[207].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Indiavision.png");
        values[207].put(ChannelColumn.CATEGORY,"News");

        values[208].put(ChannelColumn._ID,"jain-tv");
        values[208].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/jain.png");
        values[208].put(ChannelColumn.CATEGORY,"News");

        values[209].put(ChannelColumn._ID,"janasri-news");
        values[209].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Janasri-News.png");
        values[209].put(ChannelColumn.CATEGORY,"News");

        values[210].put(ChannelColumn._ID,"kairali-people-tv");
        values[210].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Kairali-People-TV.png");
        values[210].put(ChannelColumn.CATEGORY,"News");

        values[211].put(ChannelColumn._ID,"kolkata-tv");
        values[211].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/kolkata-tv.png");
        values[211].put(ChannelColumn.CATEGORY,"News");

        values[212].put(ChannelColumn._ID,"lemon-news");
        values[212].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/News/Lemon-News.png");
        values[212].put(ChannelColumn.CATEGORY,"News");

        values[213].put(ChannelColumn._ID,"mahuaa-news");
        values[213].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Mahuaa-News.png");
        values[213].put(ChannelColumn.CATEGORY,"News");

        values[214].put(ChannelColumn._ID,"mathrubhumi-news");
        values[214].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Mathrubhumi-News.png");
        values[214].put(ChannelColumn.CATEGORY,"News");

        values[215].put(ChannelColumn._ID,"mh1-news");
        values[215].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/MH1-News.png");
        values[215].put(ChannelColumn.CATEGORY,"News");

        values[216].put(ChannelColumn._ID,"ndtv-24x7");
        values[216].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/NDTV-24X7.png");
        values[216].put(ChannelColumn.CATEGORY,"News");

        values[217].put(ChannelColumn._ID,"ndtv-india");
        values[217].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/NDTV-India.png");
        values[217].put(ChannelColumn.CATEGORY,"News");

        values[218].put(ChannelColumn._ID,"news-24");
        values[218].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/news24.png");
        values[218].put(ChannelColumn.CATEGORY,"News");

        values[219].put(ChannelColumn._ID,"news-express");
        values[219].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/News-Express.png");
        values[219].put(ChannelColumn.CATEGORY,"News");

        values[220].put(ChannelColumn._ID,"news-time");
        values[220].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/News-Time.png");
        values[220].put(ChannelColumn.CATEGORY,"News");

        values[221].put(ChannelColumn._ID,"newsx");
        values[221].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/NewsX.png");
        values[221].put(ChannelColumn.CATEGORY,"News");

        values[222].put(ChannelColumn._ID,"ptc-news");
        values[222].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/PTC-News.png");
        values[222].put(ChannelColumn.CATEGORY,"News");

        values[223].put(ChannelColumn._ID,"raj-news-24x7");
        values[223].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Raj-News-24x7.png");
        values[223].put(ChannelColumn.CATEGORY,"News");

        values[224].put(ChannelColumn._ID,"raj-news-malyalam");
        values[224].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Raj-News-Malayalam.png");
        values[224].put(ChannelColumn.CATEGORY,"News");

        values[225].put(ChannelColumn._ID,"reporter-tv");
        values[225].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/reporter.png");
        values[225].put(ChannelColumn.CATEGORY,"News");

        values[226].put(ChannelColumn._ID,"sahara-up");
        values[226].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Sahara-UP.png");
        values[226].put(ChannelColumn.CATEGORY,"News");

        values[227].put(ChannelColumn._ID,"samaya-tv");
        values[227].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/samaya-tv.png");
        values[227].put(ChannelColumn.CATEGORY,"News");

        values[228].put(ChannelColumn._ID,"sandesh-news-dnn");
        values[228].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Sandesh-News.png");
        values[228].put(ChannelColumn.CATEGORY,"News");

        values[229].put(ChannelColumn._ID,"sea-news");
        values[229].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Sea-News.png");
        values[229].put(ChannelColumn.CATEGORY,"News");

        values[230].put(ChannelColumn._ID,"seithigal");
        values[230].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Seithigal.png");
        values[230].put(ChannelColumn.CATEGORY,"News");

        values[231].put(ChannelColumn._ID,"studio-n");
        values[231].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Studio-N.png");
        values[231].put(ChannelColumn.CATEGORY,"News");

        values[232].put(ChannelColumn._ID,"sun-news");
        values[232].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/sun-news.png");
        values[232].put(ChannelColumn.CATEGORY,"News");

        values[233].put(ChannelColumn._ID,"suvarna-news-24x7");
        values[233].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Suvarna-News-24x7.png");
        values[233].put(ChannelColumn.CATEGORY,"News");

        values[234].put(ChannelColumn._ID,"tez");
        values[234].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/tez.png");
        values[234].put(ChannelColumn.CATEGORY,"News");

        values[235].put(ChannelColumn._ID,"times-now");
        values[235].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Times-Now.png");
        values[235].put(ChannelColumn.CATEGORY,"News");

        values[236].put(ChannelColumn._ID,"tv24");
        values[236].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/TV%2024.png");
        values[236].put(ChannelColumn.CATEGORY,"News");

        values[237].put(ChannelColumn._ID,"tv9-gujarati");
        values[237].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/News/tv9-gujarati.PNG");
        values[237].put(ChannelColumn.CATEGORY,"News");

        values[238].put(ChannelColumn._ID,"tv9-kannada");
        values[238].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/TV9-Kannada.png");
        values[238].put(ChannelColumn.CATEGORY,"News");

        values[239].put(ChannelColumn._ID,"udaya-news");
        values[239].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/udaya-news.png");
        values[239].put(ChannelColumn.CATEGORY,"News");

        values[240].put(ChannelColumn._ID,"v6-news");
        values[240].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/V6-News.png");
        values[240].put(ChannelColumn.CATEGORY,"News");

        values[241].put(ChannelColumn._ID,"zee-24-gantalu");
        values[241].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Zee-24-Gantalu.png");
        values[241].put(ChannelColumn.CATEGORY,"News");

        values[242].put(ChannelColumn._ID,"zee-24-ghanta");
        values[242].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Zee-24-Ghanta.png");
        values[242].put(ChannelColumn.CATEGORY,"News");

        values[243].put(ChannelColumn._ID,"zee-24-ghante-mp");
        values[243].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Zee-24-Ghante-MP.png");
        values[243].put(ChannelColumn.CATEGORY,"News");

        values[244].put(ChannelColumn._ID,"zee-24-taas");
        values[244].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/ZEE-24-TAAS.png");
        values[244].put(ChannelColumn.CATEGORY,"News");

        values[245].put(ChannelColumn._ID,"zee-business");
        values[245].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Zee-Business.png");
        values[245].put(ChannelColumn.CATEGORY,"News");

        values[246].put(ChannelColumn._ID,"zee-news");
        values[246].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Zee-News.png");
        values[246].put(ChannelColumn.CATEGORY,"News");

        values[247].put(ChannelColumn._ID,"zee-news-up");
        values[247].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Zee-News-UP.png");
        values[247].put(ChannelColumn.CATEGORY,"News");

        values[248].put(ChannelColumn._ID,"zee-punjab-haryana-himachal");
        values[248].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/news/Zee-Punjab-Haryana-Himachal.png");
        values[248].put(ChannelColumn.CATEGORY,"News");

        values[249].put(ChannelColumn._ID,"dd-sports");
        values[249].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/sports/DD-Sports.png");
        values[249].put(ChannelColumn.CATEGORY,"Sports");

        values[250].put(ChannelColumn._ID,"neo-prime");
        values[250].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/sports/Neo-Prime.png");
        values[250].put(ChannelColumn.CATEGORY,"Sports");

        values[251].put(ChannelColumn._ID,"neo-sports");
        values[251].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/sports/Neo-Sports.png");
        values[251].put(ChannelColumn.CATEGORY,"Sports");

        values[252].put(ChannelColumn._ID,"sony-six");
        values[252].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/sports/Sony%20Six1.png");
        values[252].put(ChannelColumn.CATEGORY,"Sports");

        values[253].put(ChannelColumn._ID,"sony-six-hd");
        values[253].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/sports/Sony-Six-HD.png");
        values[253].put(ChannelColumn.CATEGORY,"Sports");

        values[254].put(ChannelColumn._ID,"star-sports-1");
        values[254].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Sports/starsports-1.png");
        values[254].put(ChannelColumn.CATEGORY,"Sports");

        values[255].put(ChannelColumn._ID,"star-sports-2");
        values[255].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Sports/starsports-2.png");
        values[255].put(ChannelColumn.CATEGORY,"Sports");

        values[256].put(ChannelColumn._ID,"star-sports-3");
        values[256].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Sports/starsports-3.png");
        values[256].put(ChannelColumn.CATEGORY,"Sports");

        values[257].put(ChannelColumn._ID,"star-sports-4");
        values[257].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Sports/starsports-4.png");
        values[257].put(ChannelColumn.CATEGORY,"Sports");

        values[258].put(ChannelColumn._ID,"star-sports-hd-1");
        values[258].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Sports/starsports-HD1.png");
        values[258].put(ChannelColumn.CATEGORY,"Sports");

        values[259].put(ChannelColumn._ID,"star-sports-hd-2");
        values[259].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Sports/starsports-HD2.png");
        values[259].put(ChannelColumn.CATEGORY,"Sports");

        values[260].put(ChannelColumn._ID,"star-sports-hd-3");
        values[260].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Sports/star-sports-hd-3.png");
        values[260].put(ChannelColumn.CATEGORY,"Sports");

        values[261].put(ChannelColumn._ID,"star-sports-hd-4");
        values[261].put(ChannelColumn.URL,"http://tvimages.burrp.com/images/channels2/Sports/star-sports-hd-4.png");
        values[261].put(ChannelColumn.CATEGORY,"Sports");

        //adding channel names
        values[0].put(ChannelColumn.NAME,"& Pictures");
        values[1].put(ChannelColumn.NAME,"Asianet Movies");
        values[2].put(ChannelColumn.NAME,"B4U Movies");
        values[3].put(ChannelColumn.NAME,"Cinema TV");
        values[4].put(ChannelColumn.NAME,"FILMY");
        values[5].put(ChannelColumn.NAME,"Gemini Movies");
        values[6].put(ChannelColumn.NAME,"HBO");
        values[7].put(ChannelColumn.NAME,"India Talkies");
        values[8].put(ChannelColumn.NAME,"Jalsha Movies");
        values[9].put(ChannelColumn.NAME,"Jaya Movie");
        values[10].put(ChannelColumn.NAME,"Kiran Tv");
        values[11].put(ChannelColumn.NAME,"KTV");
        values[12].put(ChannelColumn.NAME,"Maa Movies");
        values[13].put(ChannelColumn.NAME,"Movies Now");
        values[14].put(ChannelColumn.NAME,"Movies Ok");
        values[15].put(ChannelColumn.NAME,"Raj Digital Plus");
        values[16].put(ChannelColumn.NAME,"Romedy Now");
        values[17].put(ChannelColumn.NAME,"Sony MAX");
        values[18].put(ChannelColumn.NAME,"Sony Max 2");
        values[19].put(ChannelColumn.NAME,"Sony PIX");
        values[20].put(ChannelColumn.NAME,"Star Gold");
        values[21].put(ChannelColumn.NAME,"Star Gold HD");
        values[22].put(ChannelColumn.NAME,"Star Movies");
        values[23].put(ChannelColumn.NAME,"Star Movies Action");
        values[24].put(ChannelColumn.NAME,"Star Movies HD");
        values[25].put(ChannelColumn.NAME,"Udaya Movies");
        values[26].put(ChannelColumn.NAME,"UTV Action");
        values[27].put(ChannelColumn.NAME,"UTV Movies");
        values[28].put(ChannelColumn.NAME,"WB");
        values[29].put(ChannelColumn.NAME,"Zee Action");
        values[30].put(ChannelColumn.NAME,"Zee Cinema");
        values[31].put(ChannelColumn.NAME,"Zee Cinema HD");
        values[32].put(ChannelColumn.NAME,"Zee Classic");
        values[33].put(ChannelColumn.NAME,"Zee Studio");
        values[34].put(ChannelColumn.NAME,"Zee Studio HD");
        values[35].put(ChannelColumn.NAME,"Zee Talkies");
        values[36].put(ChannelColumn.NAME,"Akash Bangla");
        values[37].put(ChannelColumn.NAME,"Amrita Tv");
        values[38].put(ChannelColumn.NAME,"Anjan Tv");
        values[39].put(ChannelColumn.NAME,"Arirang");
        values[40].put(ChannelColumn.NAME,"Asianet");
        values[41].put(ChannelColumn.NAME,"Asianet Plus");
        values[42].put(ChannelColumn.NAME,"AXN");
        values[43].put(ChannelColumn.NAME,"Big Magic");
        values[44].put(ChannelColumn.NAME,"BIG RTL Thrill");
        values[45].put(ChannelColumn.NAME,"Bindass");
        values[46].put(ChannelColumn.NAME,"Bindass Play");
        values[47].put(ChannelColumn.NAME,"Channel [V]");
        values[48].put(ChannelColumn.NAME,"Colors");
        values[49].put(ChannelColumn.NAME,"Colors HD");
        values[50].put(ChannelColumn.NAME,"DD Bharati");
        values[51].put(ChannelColumn.NAME,"DD India");
        values[52].put(ChannelColumn.NAME,"DD National");
        values[53].put(ChannelColumn.NAME,"DD Urdu");
        values[54].put(ChannelColumn.NAME,"Dhamaal TV");
        values[55].put(ChannelColumn.NAME,"DY 365");
        values[56].put(ChannelColumn.NAME,"E 24");
        values[57].put(ChannelColumn.NAME,"EPIC");
        values[58].put(ChannelColumn.NAME,"Etv Rajasthan");
        values[59].put(ChannelColumn.NAME,"Etv UP");
        values[60].put(ChannelColumn.NAME,"Firangi");
        values[61].put(ChannelColumn.NAME,"Fox Life");
        values[62].put(ChannelColumn.NAME,"Fox Life HD");
        values[63].put(ChannelColumn.NAME,"FX");
        values[64].put(ChannelColumn.NAME,"Gemini Tv");
        values[65].put(ChannelColumn.NAME,"Imayam Tv");
        values[66].put(ChannelColumn.NAME,"Jan TV");
        values[67].put(ChannelColumn.NAME,"Jaya Max");
        values[68].put(ChannelColumn.NAME,"Jaya Plus");
        values[69].put(ChannelColumn.NAME,"Jaya Tv");
        values[70].put(ChannelColumn.NAME,"Jeevan Tv");
        values[71].put(ChannelColumn.NAME,"Kairali Tv");
        values[72].put(ChannelColumn.NAME,"Kairali We TV");
        values[73].put(ChannelColumn.NAME,"Kalaignar Chithiram");
        values[74].put(ChannelColumn.NAME,"Kalaignar Sirippoli");
        values[75].put(ChannelColumn.NAME,"Kalaignar Tv");
        values[76].put(ChannelColumn.NAME,"Khushboo Tv");
        values[77].put(ChannelColumn.NAME,"Life Ok");
        values[78].put(ChannelColumn.NAME,"Life Ok HD");
        values[79].put(ChannelColumn.NAME,"Maa Gold");
        values[80].put(ChannelColumn.NAME,"Maa Tv");
        values[81].put(ChannelColumn.NAME,"Maiboli Tv");
        values[82].put(ChannelColumn.NAME,"Makkal Tv");
        values[83].put(ChannelColumn.NAME,"Mazhavil Manorama");
        values[84].put(ChannelColumn.NAME,"Mega TV");
        values[85].put(ChannelColumn.NAME,"Mi Marathi");
        values[86].put(ChannelColumn.NAME,"Om Bangla");
        values[87].put(ChannelColumn.NAME,"Polimer Tv");
        values[88].put(ChannelColumn.NAME,"Raj Tv");
        values[89].put(ChannelColumn.NAME,"Rishtey");
        values[90].put(ChannelColumn.NAME,"Ruposhi Bangla");
        values[91].put(ChannelColumn.NAME,"Saam TV");
        values[92].put(ChannelColumn.NAME,"Sada Channel");
        values[93].put(ChannelColumn.NAME,"Sahara One");
        values[94].put(ChannelColumn.NAME,"Sony Entertainment TV");
        values[95].put(ChannelColumn.NAME,"Sony Pal");
        values[96].put(ChannelColumn.NAME,"Sony SAB");
        values[97].put(ChannelColumn.NAME,"Sony TV HD");
        values[98].put(ChannelColumn.NAME,"Star Jalsha");
        values[99].put(ChannelColumn.NAME,"Star Plus");
        values[100].put(ChannelColumn.NAME,"Star Plus HD");
        values[101].put(ChannelColumn.NAME,"Star Pravah");
        values[102].put(ChannelColumn.NAME,"Star Utsav");
        values[103].put(ChannelColumn.NAME,"Star Vijay");
        values[104].put(ChannelColumn.NAME,"Star World");
        values[105].put(ChannelColumn.NAME,"Star World HD");
        values[106].put(ChannelColumn.NAME,"Star World Premier HD");
        values[107].put(ChannelColumn.NAME,"Sun TV");
        values[108].put(ChannelColumn.NAME,"Sun Tv HD");
        values[109].put(ChannelColumn.NAME,"Surya Tv");
        values[110].put(ChannelColumn.NAME,"TV5 Monde");
        values[111].put(ChannelColumn.NAME,"Udaya");
        values[112].put(ChannelColumn.NAME,"Zee Bangla");
        values[113].put(ChannelColumn.NAME,"Zee Cafe");
        values[114].put(ChannelColumn.NAME,"Zee Kannada");
        values[115].put(ChannelColumn.NAME,"Zee Marathi");
        values[116].put(ChannelColumn.NAME,"Zee Salaam");
        values[117].put(ChannelColumn.NAME,"Zee Smile");
        values[118].put(ChannelColumn.NAME,"Zee Telugu");
        values[119].put(ChannelColumn.NAME,"ZEE TV");
        values[120].put(ChannelColumn.NAME,"Zee TV HD");
        values[121].put(ChannelColumn.NAME,"Zing");
        values[122].put(ChannelColumn.NAME,"Zoom");
        values[123].put(ChannelColumn.NAME,"Adithya TV");
        values[124].put(ChannelColumn.NAME,"Animax");
        values[125].put(ChannelColumn.NAME,"Baby TV");
        values[126].put(ChannelColumn.NAME,"Cartoon Network");
        values[127].put(ChannelColumn.NAME,"Chintu Tv");
        values[128].put(ChannelColumn.NAME,"Chutti TV");
        values[129].put(ChannelColumn.NAME,"Discovery Kids");
        values[130].put(ChannelColumn.NAME,"Disney Channel");
        values[131].put(ChannelColumn.NAME,"Disney Junior");
        values[132].put(ChannelColumn.NAME,"Kochu Tv");
        values[133].put(ChannelColumn.NAME,"Kushi Tv");
        values[134].put(ChannelColumn.NAME,"Nick Junior");
        values[135].put(ChannelColumn.NAME,"Nickelodeon");
        values[136].put(ChannelColumn.NAME,"POGO");
        values[137].put(ChannelColumn.NAME,"Sonic");
        values[138].put(ChannelColumn.NAME,"Topper TV");
        values[139].put(ChannelColumn.NAME,"ZeeQ");
        values[140].put(ChannelColumn.NAME,"Animal Planet");
        values[141].put(ChannelColumn.NAME,"Discovery Channel");
        values[142].put(ChannelColumn.NAME,"Discovery HD");
        values[143].put(ChannelColumn.NAME,"Discovery ID");
        values[144].put(ChannelColumn.NAME,"Discovery Science");
        values[145].put(ChannelColumn.NAME,"Discovery Tamil");
        values[146].put(ChannelColumn.NAME,"Discovery Turbo");
        values[147].put(ChannelColumn.NAME,"History Tv 18");
        values[148].put(ChannelColumn.NAME,"History TV HD");
        values[149].put(ChannelColumn.NAME,"Nat Geo People");
        values[150].put(ChannelColumn.NAME,"Nat Geo People HD");
        values[151].put(ChannelColumn.NAME,"Nat Geo Wild");
        values[152].put(ChannelColumn.NAME,"National Geographic Channel");
        values[153].put(ChannelColumn.NAME,"National Geographic Channel HD");
        values[154].put(ChannelColumn.NAME,"Travel XP HD");

        values[155].put(ChannelColumn.NAME,"7 S Music");
        values[156].put(ChannelColumn.NAME,"9X Jalwa");
        values[157].put(ChannelColumn.NAME,"9X Jhakaas");
        values[158].put(ChannelColumn.NAME,"9X Tashan");
        values[159].put(ChannelColumn.NAME,"9XM");
        values[160].put(ChannelColumn.NAME,"9xO");
        values[161].put(ChannelColumn.NAME,"B4U Music");
        values[162].put(ChannelColumn.NAME,"Dhoom Music");
        values[163].put(ChannelColumn.NAME,"Gemini Music");
        values[164].put(ChannelColumn.NAME,"Kalaignar Isai Aruvi");
        values[165].put(ChannelColumn.NAME,"Maa Music");
        values[166].put(ChannelColumn.NAME,"Mastiii");
        values[167].put(ChannelColumn.NAME,"Mega Musiq");
        values[168].put(ChannelColumn.NAME,"Mtunes");
        values[169].put(ChannelColumn.NAME,"MTV");
        values[170].put(ChannelColumn.NAME,"Mtv Indies");
        values[171].put(ChannelColumn.NAME,"Music India");
        values[172].put(ChannelColumn.NAME,"Music Xpress");
        values[173].put(ChannelColumn.NAME,"Nat Geo Music");
        values[174].put(ChannelColumn.NAME,"PTC Chak De");
        values[175].put(ChannelColumn.NAME,"Raj Music Karnataka");
        values[176].put(ChannelColumn.NAME,"Raj Musix");
        values[177].put(ChannelColumn.NAME,"Raj Musix Telugu");
        values[178].put(ChannelColumn.NAME,"Sangeet Bangla");
        values[179].put(ChannelColumn.NAME,"Sangeet Bhojpuri");
        values[180].put(ChannelColumn.NAME,"Sony Mix");
        values[181].put(ChannelColumn.NAME,"SS Music");
        values[182].put(ChannelColumn.NAME,"Sun Music");
        values[183].put(ChannelColumn.NAME,"Tarang Music");
        values[184].put(ChannelColumn.NAME,"Udaya Music");
        values[185].put(ChannelColumn.NAME,"Vh1");
        values[186].put(ChannelColumn.NAME,"Z etc");
        values[187].put(ChannelColumn.NAME,"ABP News");
        values[188].put(ChannelColumn.NAME,"Aryan");
        values[189].put(ChannelColumn.NAME,"Asianet News");
        values[190].put(ChannelColumn.NAME,"Bansal News");
        values[191].put(ChannelColumn.NAME,"BBC World News");
        values[192].put(ChannelColumn.NAME,"Bizz News");
        values[193].put(ChannelColumn.NAME,"Captain News");
        values[194].put(ChannelColumn.NAME,"Channel 10");
        values[195].put(ChannelColumn.NAME,"CNBC AWAAZ");
        values[196].put(ChannelColumn.NAME,"CNBC Bajar");
        values[197].put(ChannelColumn.NAME,"CNBC TV18");
        values[198].put(ChannelColumn.NAME,"CNN");
        values[199].put(ChannelColumn.NAME,"DD News");
        values[200].put(ChannelColumn.NAME,"Dilli Aaj Tak");
        values[201].put(ChannelColumn.NAME,"Etv 2");
        values[202].put(ChannelColumn.NAME,"Focus Tv");
        values[203].put(ChannelColumn.NAME,"GNN News");
        values[204].put(ChannelColumn.NAME,"IBN Lokmat");
        values[205].put(ChannelColumn.NAME,"India News");
        values[206].put(ChannelColumn.NAME,"India TV");
        values[207].put(ChannelColumn.NAME,"Indiavision");
        values[208].put(ChannelColumn.NAME,"Jain TV");
        values[209].put(ChannelColumn.NAME,"Janasri News");
        values[210].put(ChannelColumn.NAME,"Kairali People TV");
        values[211].put(ChannelColumn.NAME,"Kolkata TV");
        values[212].put(ChannelColumn.NAME,"Lemon News");
        values[213].put(ChannelColumn.NAME,"Mahuaa News");
        values[214].put(ChannelColumn.NAME,"Mathrubhumi News");
        values[215].put(ChannelColumn.NAME,"MH1 News");
        values[216].put(ChannelColumn.NAME,"NDTV 24X7");
        values[217].put(ChannelColumn.NAME,"NDTV India");
        values[218].put(ChannelColumn.NAME,"News 24");
        values[219].put(ChannelColumn.NAME,"News Express");
        values[220].put(ChannelColumn.NAME,"News Time");
        values[221].put(ChannelColumn.NAME,"NewsX");
        values[222].put(ChannelColumn.NAME,"PTC News");
        values[223].put(ChannelColumn.NAME,"Raj News 24x7");
        values[224].put(ChannelColumn.NAME,"Raj News Malyalam");
        values[225].put(ChannelColumn.NAME,"Reporter TV");
        values[226].put(ChannelColumn.NAME,"Sahara UP");
        values[227].put(ChannelColumn.NAME,"Samaya TV");
        values[228].put(ChannelColumn.NAME,"Sandesh News DNN");
        values[229].put(ChannelColumn.NAME,"Sea News");
        values[230].put(ChannelColumn.NAME,"Seithigal");
        values[231].put(ChannelColumn.NAME,"Studio N");
        values[232].put(ChannelColumn.NAME,"Sun News");
        values[233].put(ChannelColumn.NAME,"Suvarna News 24x7");
        values[234].put(ChannelColumn.NAME,"Tez");
        values[235].put(ChannelColumn.NAME,"Times Now");
        values[236].put(ChannelColumn.NAME,"TV24");
        values[237].put(ChannelColumn.NAME,"TV9 Gujarati");
        values[238].put(ChannelColumn.NAME,"TV9 Kannada");
        values[239].put(ChannelColumn.NAME,"Udaya News");
        values[240].put(ChannelColumn.NAME,"V6 News");
        values[241].put(ChannelColumn.NAME,"Zee 24 Gantalu");
        values[242].put(ChannelColumn.NAME,"Zee 24 Ghanta");
        values[243].put(ChannelColumn.NAME,"Zee 24 Ghante MP");
        values[244].put(ChannelColumn.NAME,"Zee 24 Taas");
        values[245].put(ChannelColumn.NAME,"Zee Business");
        values[246].put(ChannelColumn.NAME,"Zee News");
        values[247].put(ChannelColumn.NAME,"Zee News UP");
        values[248].put(ChannelColumn.NAME,"Zee Punjab Haryana Himachal");
        values[249].put(ChannelColumn.NAME,"DD Sports");
        values[250].put(ChannelColumn.NAME,"Neo Prime");
        values[251].put(ChannelColumn.NAME,"Neo Sports");
        values[252].put(ChannelColumn.NAME,"Sony Six");
        values[253].put(ChannelColumn.NAME,"Sony Six HD");
        values[254].put(ChannelColumn.NAME,"Star Sports 1");
        values[255].put(ChannelColumn.NAME,"Star Sports 2");
        values[256].put(ChannelColumn.NAME,"Star Sports 3");
        values[257].put(ChannelColumn.NAME,"Star Sports 4");
        values[258].put(ChannelColumn.NAME,"Star Sports HD 1");
        values[259].put(ChannelColumn.NAME,"Star Sports HD 2");
        values[260].put(ChannelColumn.NAME,"Star Sports HD 3");
        values[261].put(ChannelColumn.NAME,"Star Sports HD 4");


        getContentResolver().bulkInsert(ShowProvider.Channels.CONTENT_URI,values);
    }
}
