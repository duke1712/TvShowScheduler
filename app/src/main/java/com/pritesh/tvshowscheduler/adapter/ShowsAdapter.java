package com.pritesh.tvshowscheduler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.model.Channel;
import com.pritesh.tvshowscheduler.model.Shows;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by prittesh on 14/12/16.
 */

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.MyViewHolder> {
    private List<Shows> showsList;
    Context context;
    public ShowsAdapter(List<Shows> showsList, Context context){
        this.showsList=showsList;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shows_list_item, parent, false);
        return new ShowsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Shows shows=showsList.get(position);
        holder.title.setText(shows.getTitle());
        holder.time.setText(shows.getTime());
        Picasso.with(context).load(shows.getUrl()).fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title,time;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            time=(TextView)itemView.findViewById(R.id.time);
            imageView=(ImageView)itemView.findViewById(R.id.imageView3);
        }
    }
}
