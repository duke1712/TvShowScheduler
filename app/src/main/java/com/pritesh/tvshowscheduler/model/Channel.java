package com.pritesh.tvshowscheduler.model;

/**
 * Created by prittesh on 13/12/16.
 */

public class Channel {
    private String name;
    private String display_name;
    private String image_url;
    private String category;

    Channel() {

    }

    public Channel(String display_name, String name,String image_url) {
        this.display_name = display_name;
        this.name = name;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
