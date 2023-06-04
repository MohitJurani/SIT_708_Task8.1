package com.example.itubeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MimeTypes;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class PlayActivity extends AppCompatActivity {
    PlayerView playerView;
    Button button;

    ExoPlayer exoPlayer;

    private Boolean playWhenReady = true;
    private int currentItem = 0;
    private long playbackPosition = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        playerView = findViewById(R.id.playerView);
        button = findViewById(R.id.button2);

        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(button.getText().toString() == "Play") {

                    MediaItem mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp4));

                    MediaItem mediaItem1 = MediaItem.fromUri(getString(R.string.media_url_mp3));


                    MediaItem mediaItem2 = new MediaItem.Builder()
                            .setUri(getString(R.string.media_url_dash))
                            .setMimeType(MimeTypes.APPLICATION_MPD)
                            .build();


                    exoPlayer.setMediaItem(mediaItem);
                    exoPlayer.addMediaItem(mediaItem1);
                    exoPlayer.addMediaItem(mediaItem2);

                    exoPlayer.setPlayWhenReady(playWhenReady);
                    exoPlayer.seekTo(currentItem, playbackPosition);

                    exoPlayer.prepare();
                    exoPlayer.play();

                    //                    exoPlayer.prepare();

                    button.setText("Stop");

                }
                else {
                    exoPlayer.stop();

                    playbackPosition = exoPlayer.getCurrentPosition();
                    currentItem = exoPlayer.getCurrentMediaItemIndex();
                    playWhenReady = exoPlayer.getPlayWhenReady();


                    //                    exoPlayer.release();

                    button.setText("Play");
                }


            }
        });
    }
}