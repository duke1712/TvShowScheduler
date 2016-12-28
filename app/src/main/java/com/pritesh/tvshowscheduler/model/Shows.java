package com.pritesh.tvshowscheduler.model;

/**
 * Created by prittesh on 14/12/16.
 */

public class Shows {
    private String title;
    private String time;
    private String url;
    private int id;

    public Shows() {
    }

    public Shows(String title, String time, String url) {
        this.title = title;
        this.time = time;
        this.url = url;
    }

    public Shows(String title, String time, String url, int id) {
        this.title = title;
        this.time = time;
        this.url = url;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
