//package com.pritesh.tvshowscheduler.data;
//
//import android.net.Uri;
//
//import net.simonvt.schematic.annotation.ContentProvider;
//import net.simonvt.schematic.annotation.ContentUri;
//import net.simonvt.schematic.annotation.InexactContentUri;
//import net.simonvt.schematic.annotation.TableEndpoint;
//
///**
// * Created by prittesh on 26/12/16.
// */
//@ContentProvider(authority = ChannelProvider.AUTHORITY, database = ChannelDatabase.class)
//public class ChannelProvider {
//    public static final String AUTHORITY = "com.pritesh.tvshowscheduler.data";
//    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
//
//
//    private static Uri buildUri(String... paths) {
//        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
//        for (String path : paths) {
//            builder.appendPath(path);
//        }
//        return builder.build();
//    }
//
//    @TableEndpoint(table = ChannelDatabase.channels)
//    public static class Channels {
//        @ContentUri(
//                path = "Channels",
//                type = "vnd.android.cursor.dir/Channels",
//                defaultSort = ChannelColumn.NAME)
//        public static final Uri CONTENT_URI = buildUri("Channels");
//
//        @InexactContentUri(
//                name = "Channel_id",
//                path = "Channel" + "/#",
//                type = "vnd.android.cursor.item/Channel",
//                whereColumn = ChannelColumn._ID,
//                pathSegment = 1
//        )
//        public static Uri withId(int id) {
//            return buildUri("Channels", String.valueOf(id));
//        }
//    }
//}
