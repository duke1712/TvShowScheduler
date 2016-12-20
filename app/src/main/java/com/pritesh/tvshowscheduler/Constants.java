package com.pritesh.tvshowscheduler;

/**
 * Created by prittesh on 14/12/16.
 */

public class Constants {
    public final static String BASE_URL = "http://indian-television-guide.appspot.com/indian_television_guide?channel=";
    public final static String END_URL = "&date=";
    public final static String CHANNELS[] = {
            "colors", "life-ok", "sony-entertainment-tv", "sony-sab", "star-plus", "star-world", "zee-tv", "9xm", "mtv", "hbo"};
    public final static String CHANNELS_NAME[] = {
            "Colors", "Life Ok", "Sony", "Sab Tv", "StarPlus", "StarWorld", "ZeeTv", "9xm", "Mtv", "HBO"};
    public final static String CHANNELS_URL[] = {
            "https://yt3.ggpht.com/-RrcWZbQ0dKs/AAAAAAAAAAI/AAAAAAAAAAA/1KoxEfT0ZFY/s900-c-k-no-mo-rj-c0xffffff/photo.jpg",
            "http://www.indiantelevision.com/sites/drupal7.indiantelevision.co.in/files/images/tv-images/2014/07/16/life_ok_logo.jpg",
            "https://yt3.ggpht.com/-ZHjW5xTENKE/AAAAAAAAAAI/AAAAAAAAAAA/xI4YdGRWkFM/s900-c-k-no-mo-rj-c0xffffff/photo.jpg",
            "http://www.afaqs.com/all/news/images/news_story_grfx/2015/04/43951/Sab%20TV%20logo.jpg",
            "https://upload.wikimedia.org/wikipedia/en/2/27/Star_Plus_New_Logo_2016.jpg",
            "http://vignette2.wikia.nocookie.net/logopedia/images/6/62/STAR_World_2013.png",
            "http://vignette2.wikia.nocookie.net/logopedia/images/3/38/Zee_TV_2011.png",
            "https://s3.amazonaws.com/s3.urosd.com/uploads/9xm_new.jpg",
            "http://www.officialpsds.com/images/thumbs/MTV-Logo-psd26045.png",
            "http://cordcuttersnews.com/wp-content/uploads/2014/04/hbologo.gif",
    };
    public final static String SHOW_LIST = "listOfShows";
    public final static String JSON_TITLE = "showTitle";
    public final static String JSON_TIME = "showTime";
    public final static String JSON_URL = "showThumb";

}
