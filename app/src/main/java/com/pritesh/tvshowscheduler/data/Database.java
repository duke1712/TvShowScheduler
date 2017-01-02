package com.pritesh.tvshowscheduler.data;

import com.pritesh.tvshowscheduler.adapter.ShowsAdapter;
import com.pritesh.tvshowscheduler.model.Channel;

import net.simonvt.schematic.annotation.Table;


/**
 * Created by prittesh on 14/12/16.
 */
@net.simonvt.schematic.annotation.Database(version = Database.VERSION)
public class Database {
    public static final int VERSION = 2;
    @Table(Columns.class)
    public static final String shows = "Shows";

    @Table(ChannelColumn.class)
    public static final String channels = "Channels";
    private Database() {

    }
}
