V2  a5+// exoplayer landscapesensitive play,zoom,touch box,touch single skip
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".allow_access">


<!--    <SurfaceView-->
<!--        android:id="@+id/surface_view"-->
<!--        android:layout_centerCrop="true"-->
<!--        android:layout_width="match_parent"-->
<!--        android:layout_height="match_parent" />-->

    <com.otaliastudios.zoom.ZoomSurfaceView
        android:id="@+id/surface_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:transformation="centerInside"
        app:transformationGravity="auto"
        app:alignment="center"
        app:overScrollHorizontal="false"
        app:overScrollVertical="false"
        app:overPinchable="false"
        app:horizontalPanEnabled="true"
        app:verticalPanEnabled="true"
        app:zoomEnabled="true"
        app:flingEnabled="true"
        app:scrollEnabled="true"
        app:oneFingerScrollEnabled="true"
        app:twoFingersScrollEnabled="true"
        app:threeFingersScrollEnabled="true"
        app:minZoom="1"
        app:minZoomType="zoom"
        app:maxZoom="8"
        app:maxZoomType="zoom"
        app:animationDuration="10"/>



    <androidx.media3.ui.PlayerControlView
        android:id="@+id/player_control_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"

        />

    <View
        android:id="@+id/touch_r"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:background="@android:color/black"
        android:layout_gravity="top|start"
        android:layout_marginTop="10dp"
        android:clickable="true"
        android:focusable="true" />

    <View
        android:id="@+id/f"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_alignParentTop="true"
        android:layout_alignParentRight="true"
        android:background="@android:color/black"
        android:layout_marginTop="10dp"
        android:clickable="true"
        android:focusable="true" />

</RelativeLayout>

//---------------------------------------------------------------------------------------------------
package com.a.acs2;



import static com.a.acs2.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import androidx.media3.common.Player;
import androidx.media3.common.VideoSize;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerControlView;
import androidx.media3.ui.PlayerView;
import androidx.media3.common.util.UnstableApi;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.media3.common.MediaItem;

import com.otaliastudios.zoom.ZoomSurfaceView;

import org.jetbrains.annotations.NotNull;

@UnstableApi
public class allow_access extends AppCompatActivity {

    private ExoPlayer mediaPlayer;
    private ZoomSurfaceView surfaceView;
    private PlayerControlView playerControlView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_main);

        View touch_r = findViewById(R.id.touch_r);


        mediaPlayer = new ExoPlayer.Builder(this).build();

        surfaceView = findViewById(R.id.surface_view);
        playerControlView = findViewById(R.id.player_control_view);

        // Set up SurfaceView for video rendering
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                mediaPlayer.setVideoSurface(holder.getSurface());
//            }
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {}
//        });

        surfaceView.addCallback(new ZoomSurfaceView.Callback() {
            @Override
            public void onZoomSurfaceCreated(@NotNull ZoomSurfaceView view) {
                Surface surface = view.getSurface();
                mediaPlayer.setVideoSurface(surface);


                // Use this surface for video players, camera preview, ...
            }

            @Override
            public void onZoomSurfaceDestroyed(@NotNull ZoomSurfaceView view) {
            }
        });

        // Set the player to the PlayerControlView
        playerControlView.setPlayer(mediaPlayer);

        // Prepare the media item
        MediaItem mediaItem = MediaItem.fromUri("https://flame.flameriser78.workers.dev/0:/mcubd/OTHERS/bng/5.mp4");
//        MediaItem mediaItem = MediaItem.fromUri("https://flame.flameriser78.workers.dev/0:/mcubd/0331.mp4");
        mediaPlayer.setMediaItem(mediaItem);


        mediaPlayer.addListener(new ExoPlayer.Listener() {
            @Override
            public void onVideoSizeChanged(@NonNull VideoSize videoSize) {
                Toast.makeText(allow_access.this, "This is a toast message", Toast.LENGTH_SHORT).show();
                surfaceView.setContentSize(videoSize.width, videoSize.height);
            }
        });

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


        // Set onTouchListener for the view
        touch_r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Touching the view, show a toast
                        showToast("Touched!");
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Released the touch, show another toast
                        showToast("Touch Released!");
                        return true;
                }
                return false;
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);


    }
}
