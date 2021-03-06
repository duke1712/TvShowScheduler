package com.pritesh.tvshowscheduler.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pritesh.tvshowscheduler.AudioPlayer;
import com.pritesh.tvshowscheduler.R;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Context context;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(context);
        //   builder.setTitle("SHOW REMINDER");
        builder.setMessage(intent.getStringExtra(getApplicationContext().getString(R.string.show)) + getApplicationContext().getString(R.string.alarm_message))
                .setCancelable(false)
                .setPositiveButton("STOP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AudioPlayer.stopAudio();
                        finish();

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
