package com.pritesh.tvshowscheduler.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pritesh.tvshowscheduler.R;
import com.pritesh.tvshowscheduler.model.Channel;
import com.pritesh.tvshowscheduler.ui.ShowsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by prittesh on 13/12/16.
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.MyViewHolder> {
    private List<Channel> channelList;
    Context context;



    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
    public ChannelAdapter(List<Channel> channelList, Context context){
        this.channelList=channelList;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Channel channel=channelList.get(position);
        holder.name.setText(channel.getDisplay_name());
        Picasso.with(context).load(Uri.parse(channel.getImage_url().trim())).fit().into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ShowsActivity.class);
                intent.putExtra("CHANNEL_NAME",channel.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }
}
