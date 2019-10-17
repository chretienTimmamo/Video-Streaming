package mshopmobile.app.com.videostreaming;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import io.vov.vitamio.MediaMetadataRetriever;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by root on 05/05/16.
 */

//public class StreamCameraActivity extends Activity implements MediaPlayer.OnBufferingUpdateListener {
public class StreamRecordActivity extends Activity {

    VideoView mVideoView;
    String path;
    public boolean fullScreenMode;
    public FrameLayout panelButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        */
        Vitamio.isInitialized(this);
        panelButton = (FrameLayout) findViewById(R.id.panelButton);
        panelButton.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            path = extras.getString("recordPath");
            Log.i("path", path);
            //update link
            //path ="rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
            //path ="http://192.168.0.71:8800/video1.mp4";
            //path ="rtsp://192.168.0.71:8800/video1.mp4";
            // and get whatever type user account id is
        }
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);

        mVideoView.setVideoPath(path);


        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        mVideoView.getHolder().setFormat(PixelFormat.RGBX_8888);
        mVideoView.setVideoChroma(MediaPlayer.VIDEOCHROMA_RGB565);
        //mVideoView.setKeepScreenOn(true);
        //mVideoView.setBufferSize();
        //mVideoView.setOnBufferingUpdateListener(this);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                // close the progress bar and play the video
                //progressDialog.dismiss();
                mProgressBar.setVisibility(View.INVISIBLE);
                //mediaPlayer.setBufferSize(0);
                mediaPlayer.setPlaybackSpeed(1.0f);
                if (mVideoView.isPlaying() == false) {

                    try {

                        mVideoView.start();
                    } catch (Exception e) {

                    }
                }
            }

        });
        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("TAG", "Video 1 clicked, starting playback");

                return false;
            }
        });

        if(mVideoView.isPlaying() == false){

            try {

                mVideoView.start();
                //panelButton.setVisibility(View.VISIBLE);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            Log.e("On Config Change", "LANDSCAPE");

        } else {
            Log.e("On Config Change", "PORTRAIT");

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toggleFullScreen(){

        if(fullScreenMode){

            fullScreenMode =false;
            mVideoView.setVideoLayout(VodView.VIDEO_LAYOUT_ORIGIN, VideoView.VIDEO_LAYOUT_FIT_PARENT);
        }else{

            fullScreenMode =true;
            mVideoView.setVideoLayout(VodView.VIDEO_LAYOUT_STRETCH, 0);

        }

    }
    public boolean isFullScreen(){

        return fullScreenMode;
    }


    @Override
    protected void onRestart() {
        /*
        if (mVideoView.isPlaying() == false) {

            mProgressBar.setVisibility(View.VISIBLE);
        }
        */

        mVideoView.start();

        super.onRestart();
        //mVideoView.resume();

    }

    @Override
    protected void onPause() {

        mVideoView.pause();
        super.onPause();
    }

}
