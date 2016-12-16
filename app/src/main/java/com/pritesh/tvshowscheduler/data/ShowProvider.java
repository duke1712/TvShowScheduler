package com.pritesh.tvshowscheduler.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by prittesh on 14/12/16.
 */
@ContentProvider(authority=ShowProvider.AUTHORITY, database = Database.class)
public class ShowProvider {
    public static final String AUTHORITY ="com.pritesh.tvshowscheduler.Data";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }
    @TableEndpoint(table = Database.shows)
    public static class Shows {
        @ContentUri(
                path = "Shows",
                type = "vnd.android.cursor.dir/Shows",
                defaultSort = Columns.TITLE)
        public static final Uri CONTENT_URI = buildUri("Shows");

        @InexactContentUri(
                name = "SHOW_ID",
                path = "Shows" + "/#",
                type = "vnd.android.cursor.item/Shows",
                whereColumn = Columns._ID,
                pathSegment = 1
        )
        public static Uri withId(int id) {
            return buildUri("Shows", String.valueOf(id));
        }
    }
}
