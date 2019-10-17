package mshopmobile.app.com.videostreaming;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import io.vov.vitamio.widget.VideoView;
import util.ConfigurationFile;
import util.ExternalFile;
import util.GlobalService;

public class MainActivity extends Activity {
    VideoView mVideoView;
    String path;


    /*
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            if (!LibsChecker.checkVitamioLibs(this))
                return;
            mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);
            //path = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
            path = "rtsp://192.168.0.19:8084/webcam.mp4";
            mVideoView.setVideoPath(path);
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.requestFocus();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setBufferSize(0);
                    mediaPlayer.setPlaybackSpeed(2.0f);
                }
            });
            if(mVideoView.isPlaying() == false){

                try {

                    mVideoView.start();
                } catch (Exception e) {

                }
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
    }

    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            //setContentView(R.layout.activity_main);

        //start
        ConfigurationFile.setAllFileObject(MainActivity.this);
            if(!isUserLogin()){
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                finish();
            }else{
                //Intent intent = new Intent(this, ZoneListActivity.class);
                Intent intent = new Intent(this,VideoTabManagerActivity.class);
                startActivity(intent);
                finish();
            }
    }


    public boolean isUserLogin(){

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(ExternalFile.userLogin, Context.MODE_PRIVATE);

        String userLogin = sharedpreferences.getString("login","");
        String userPwd = sharedpreferences.getString("pwd","");

        if (!GlobalService.isEmpty(userLogin)&&(!GlobalService.isEmpty(userPwd))){

            return true;
        }
        else return false;

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
}
