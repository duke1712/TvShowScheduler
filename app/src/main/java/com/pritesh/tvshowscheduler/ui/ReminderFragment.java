package com.pritesh.tvshowscheduler.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pritesh.tvshowscheduler.R;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderFragment extends Fragment {


    public ReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reminder, container, false);
        ImageView imageView=(ImageView)view.findViewById(R.id.imageView2);
        Picasso.with(getContext()).load(Uri.parse("http://tvimages.burrp.com/images/s/k/m/kmqplo2i_34zj_1_75.jpg")).fit().into(imageView);

    return  view;
    }

}
