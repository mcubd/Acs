
package com.a.acs2;



import android.os.Bundle;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.VideoSize;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerControlView;
import com.otaliastudios.zoom.ZoomSurfaceView;
import org.jetbrains.annotations.NotNull;



@UnstableApi
public class zz extends AppCompatActivity {

    private ExoPlayer mediaPlayer;
    private ZoomSurfaceView surfaceView;
    private PlayerControlView playerControlView;



    @OptIn(markerClass = UnstableApi.class)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zz);

        surfaceView = findViewById(R.id.surface_view);
        playerControlView = findViewById(R.id.player_control_view);

         mediaPlayer = new ExoPlayer.Builder(this).build();
        playerControlView.setPlayer(mediaPlayer);
        MediaItem mediaItem= MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/test2-5bbd8.appspot.com/o/chat%2Fvideoplayback.mp4?alt=media");
        mediaPlayer.setMediaItem(mediaItem);
        mediaPlayer.prepare();
        mediaPlayer.play();






        surfaceView.addCallback(new ZoomSurfaceView.Callback() {
            @Override
            public void onZoomSurfaceCreated(@NotNull ZoomSurfaceView view) {
                Surface surface = view.getSurface();
                mediaPlayer.setVideoSurface(surface);


            }

            @Override
            public void onZoomSurfaceDestroyed(@NotNull ZoomSurfaceView view) {
            }
        });

        mediaPlayer.addListener(new ExoPlayer.Listener() {
            @Override
            public void onVideoSizeChanged(@NonNull VideoSize videoSize) {
                surfaceView.setContentSize(videoSize.width, videoSize.height);
            }
        });


    };



};