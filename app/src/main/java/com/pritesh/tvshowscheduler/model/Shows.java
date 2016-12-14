package com.pritesh.tvshowscheduler.model;

import java.sql.Time;

/**
 * Created by prittesh on 14/12/16.
 */

public class Shows {
    private String title;
    private String time;
    private String url;

    public Shows() {
    }

    public Shows(String title, String time, String url) {
        this.title = title;
        this.time = time;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
