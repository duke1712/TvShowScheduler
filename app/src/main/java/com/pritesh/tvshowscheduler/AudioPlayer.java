package com.pritesh.tvshowscheduler;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by prittesh on 16/12/16.
 */

public class AudioPlayer {
    public static MediaPlayer mediaPlayer;

    public static void playAudio(Context c) {
        mediaPlayer = MediaPlayer.create(c, R.raw.alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public static void stopAudio() {
        mediaPlayer.stop();
    }
}
