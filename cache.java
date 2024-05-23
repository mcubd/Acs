// 1st video caching exoplayer 
package com.a.acs2;



import static com.a.acs2.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import androidx.media3.common.Player;
import androidx.media3.common.VideoSize;
import androidx.media3.database.ExoDatabaseProvider;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultDataSourceFactory;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.cache.CacheDataSource;
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor;
import androidx.media3.datasource.cache.SimpleCache;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.LoadControl;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.ui.PlayerControlView;
import androidx.media3.ui.PlayerView;
import androidx.media3.common.util.UnstableApi;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.media3.common.MediaItem;

import com.airbnb.lottie.LottieAnimationView;
import com.otaliastudios.zoom.ZoomSurfaceView;

import org.jetbrains.annotations.NotNull;
import java.io.File;


@UnstableApi
public class video extends AppCompatActivity {

    private ExoPlayer mediaPlayer;
    private ZoomSurfaceView surfaceView;
    private PlayerControlView playerControlView;
    private ProgressBar progressBar;
    private LottieAnimationView loading;
    private SimpleCache cache;

    private SimpleCache simpleCache;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(layout.activity_video);

        File cacheDir = new File(getCacheDir(), "media");
        simpleCache = new SimpleCache(cacheDir, new LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024)); // 100 MB max

        // Create HTTP data source factory
        DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true);

        // Create default data source factory
        DefaultDataSource.Factory defaultDataSourceFactory = new DefaultDataSource.Factory(this, httpDataSourceFactory);

        // Create cache data source factory
        CacheDataSource.Factory cacheDataSourceFactory = new CacheDataSource.Factory()
                .setCache(simpleCache)
                .setUpstreamDataSourceFactory(defaultDataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);






        /*        progressBar = findViewById(R.id.exo_buffering);*/
        loading = findViewById(R.id.loading);

        View touch_r = findViewById(R.id.touch_r);
            boolean isLandscape = true;



        LoadControl loadControl = new DefaultLoadControl.Builder()
                .setBufferDurationsMs(
                       // DefaultLoadControl.DEFAULT_MIN_BUFFER_MS, // Minimum buffer size before playback starts
                     //   DefaultLoadControl.DEFAULT_MAX_BUFFER_MS, // Maximum buffer size
                       // DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS, // Minimum buffer size before starting playback
                       // DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS // Minimum buffer size after a rebuffer
                        1_000 , 50_000, 500, 1_000
                )
                .build();


        mediaPlayer = new ExoPlayer.Builder(this)
                .setLoadControl(loadControl).setMediaSourceFactory(new DefaultMediaSourceFactory(cacheDataSourceFactory))
                .build();

        surfaceView = findViewById(R.id.surface_view);
          playerControlView = findViewById(R.id.player_control_view);
        //-------------------------------------------------------------




        ImageButton fullscreen = playerControlView.findViewById(R.id.exo_fullscreen_icon);


        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLandscape){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    boolean    isLandscape=false;
                } else  {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    boolean  isLandscape=true;


                }
            }
        });








        ImageButton customButton = new ImageButton(this);
        customButton.setImageResource(drawable.fullscreen_exit); // Set your button icon here
        /*customButton.setBackgroundColor(Color.TRANSPARENT);*/
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                showToast("Custom button clicked!");
                int rotation =  getWindowManager().getDefaultDisplay().getRotation();
                showToast(String.valueOf(rotation));
                if (isLandscape){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    boolean    isLandscape=false;
                } else  {
                    boolean  isLandscape=true;
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

                }

            }
        });

        // Add the custom button to the player control view
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.END | Gravity.BOTTOM; // Adjust the gravity as per your requirement
/*        playerControlView.addView(customButton, params);*/



        
        //-------------------------------------------


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
        String value;
        Bundle bundle = getIntent().getExtras();
            value = bundle.getString("sample_name");


        Uri rawUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.m2);
     // MediaItem mediaItem = MediaItem.fromUri(rawUri);
        // MediaItem mediaItem = MediaItem.fromUri(value);



 //     MediaItem mediaItem = MediaItem.fromUri("https://flame.flameriser78.workers.dev/0:/mcubd/0331.mp4");
/*        DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true);
        DefaultDataSource.Factory defaultDataSourceFactory = new DefaultDataSource.Factory(this, httpDataSourceFactory);
        CacheDataSource.Factory cacheDataSourceFactory = new CacheDataSource.Factory()
                .setCache(simpleCache)
                .setUpstreamDataSourceFactory(defaultDataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(mediaItem);*/
        MediaItem mediaItem = new MediaItem.Builder().setUri(value).build();

        // Set the media item to be played




        mediaPlayer.setMediaItem(mediaItem);


        mediaPlayer.addListener(new ExoPlayer.Listener() {
            @Override
            public void onVideoSizeChanged(@NonNull VideoSize videoSize) {
               // Toast.makeText(video.this, "This is a toast message", Toast.LENGTH_SHORT).show();
                surfaceView.setContentSize(videoSize.width, videoSize.height);
            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
                    loading.setVisibility(View.GONE);
                } else {
                    loading.setVisibility(View.GONE);
                }
            }

        });

        // Start playback
        mediaPlayer.prepare();
        mediaPlayer.play();


        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerControlView.isShown()) {
                    loading.setVisibility(View.GONE);
                    playerControlView.hide();
                } else {
                    loading.setVisibility(View.GONE);
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
        cache.release();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);


    }
}

//
