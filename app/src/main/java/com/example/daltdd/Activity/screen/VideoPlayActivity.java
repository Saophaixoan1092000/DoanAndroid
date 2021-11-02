package com.example.daltdd.Activity.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.daltdd.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoPlayActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String APIKEY= "AIzaSyBQtmqNX6DMkx0yQ4HWxGvSb47BL0A6c0g";

    YouTubePlayerView youTubePlayerView;
    String idVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        idVideo= getIntent().getExtras().getString("idVideo");

        //Toast.makeText(this,idVideo,Toast.LENGTH_LONG).show();
        youTubePlayerView =findViewById(R.id.myVideo);

        youTubePlayerView.initialize(APIKEY,this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(idVideo);
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
        youTubePlayer.setFullscreen(true);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this,123);

        }else{
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==123){
            youTubePlayerView.initialize(APIKEY,this);

        }
        super.onActivityResult(requestCode,resultCode,data);
    }
}
