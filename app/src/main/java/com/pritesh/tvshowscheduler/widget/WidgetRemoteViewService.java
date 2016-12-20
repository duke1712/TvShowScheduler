package com.pritesh.tvshowscheduler.widget;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.data.ShowProvider;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by prittesh on 19/12/16.
 */

public class WidgetRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            Cursor data;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                data = getContentResolver().query(ShowProvider.Shows.CONTENT_URI,
                        null,
                        null,
                        null, null);
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();

            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION || data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_listview_item);
                String title = data.getString(1);
                String time = data.getString(3);
                String url = data.getString(2);
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(getApplicationContext()).load(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                views.setTextViewText(R.id.title_widget, title);
                views.setTextViewText(R.id.time_widget, time);
                views.setImageViewBitmap(R.id.imageView_widget, bitmap);
                final Intent fillInIntent = new Intent();
                views.setOnClickFillInIntent(R.id.widget_list_view, fillInIntent);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_listview_item);

            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getInt(0);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }
        };
    }
}
