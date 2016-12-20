package com.pritesh.tvshowscheduler.data;

import net.simonvt.schematic.annotation.Table;


/**
 * Created by prittesh on 14/12/16.
 */
@net.simonvt.schematic.annotation.Database(version = Database.VERSION)
public class Database {
    public static final int VERSION = 1;
    @Table(Columns.class)
    public static final String shows = "Shows";

    private Database() {
    }
}
