//PlayerControlView with SurfaceView

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".allow_access">

    
    <SurfaceView
        android:id="@+id/surface_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />


    <androidx.media3.ui.PlayerControlView
        android:id="@+id/player_control_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"

        />

</RelativeLayout>

//---------------------------------------------------------------------------------------

package com.a.acs2;



import static com.a.acs2.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceView;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerControlView;
import androidx.media3.ui.PlayerView;
import androidx.media3.common.util.UnstableApi;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;

import androidx.media3.common.MediaItem;

@UnstableApi
public class allow_access extends AppCompatActivity {

    private ExoPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private PlayerControlView playerControlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mediaPlayer = new ExoPlayer.Builder(this).build();

        surfaceView = findViewById(R.id.surface_view);
        playerControlView = findViewById(R.id.player_control_view);

        // Set up SurfaceView for video rendering
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // Set surface for the media player
                mediaPlayer.setVideoSurface(holder.getSurface());
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // Not used
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // Not used
            }
        });

        // Set the player to the PlayerControlView
        playerControlView.setPlayer(mediaPlayer);

        // Prepare the media item
        MediaItem mediaItem = MediaItem.fromUri("https://flame.flameriser78.workers.dev/0:/mcubd/OTHERS/bng/5.mp4");
        mediaPlayer.setMediaItem(mediaItem);

        // Start playback
        mediaPlayer.prepare();
        mediaPlayer.play();


        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerControlView.isShown()) {
                    playerControlView.hide();
                } else {
                    playerControlView.show();
                }
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}



