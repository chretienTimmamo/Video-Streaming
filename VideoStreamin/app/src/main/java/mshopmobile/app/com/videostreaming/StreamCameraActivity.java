package mshopmobile.app.com.videostreaming;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;

import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.vov.vitamio.MediaMetadataRetriever;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import util.GlobalService;
import util.ServiceIdentification;
import util.WebService.ActivityServerCommunication;
import util.WebService.AsyncCommunicationResponse;


/**
 * Created by root on 06/04/16.
 */
//public class StreamCameraActivity extends Activity implements MediaPlayer.OnBufferingUpdateListener {
public class StreamCameraActivity extends Activity implements MediaPlayer.OnInfoListener, AsyncCommunicationResponse {

    VideoView mVideoView;
    String path;
    //private ProgressDialog progressDialog;
    private ProgressBar mProgressBar;
    private ImageButton mButtonStart;
    private ImageButton mButtonCapture;
    private ImageButton setting;
    public boolean fullScreenMode;
    public FrameLayout panelButton;
    public FrameLayout panelSnapshoot;
    public boolean isFirst_Loading_Complete = false;
    private boolean isStart;
    private boolean is_playing_firstTime = false;
    private boolean playerStarted = false;
    MediaMetadataRetriever mediaMetadataRetriever;
    public MediaPlayer mplayer;
    Bitmap screenShot;
    ImageView  screenShotImageView;
    private ImageButton cancelScreenShot;
    private ImageButton saveScreenShot;
    VideoView videoView;
    String localStorage;
    public final static String APP_PATH_SD_CARD = "/SecuritrixStreaming/";
    public final static String APP_THUMBNAIL_PATH_SD_CARD = "Images";
    private DBHelper mydbImage ;
    public boolean isLocalStorage =true;
    public int cameraID;
    private String url ;
    Map<String, Object> cameraStatus;
    ActivityServerCommunication serverCommunication=null;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();
    //initial timer delais
    int threadInitialTimeDelai =15000;
    //timer delais
    int threadTimeDelai =10000;
    Timer timer;
    TimerTask timerTask;
    public boolean dataReceivedState =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*

        */
        //path = "rtsp://192.168.0.71:8086/rpiKmertronix1.mp4";
        //path = "rtsp://192.168.0.53:8086/rpiKmertronix1.mp4";
        //path = "rtsp://192.168.8.101:8086/rpiKmertronix1.mp4";
        /*
        if(getVersionCode()>8)
            checkCameraAvailable();

        else{

            startVideoViewFromCamera(path,cameraID);

            initVideoViewVariable();
        }
        */


        initStreamingVariable();
        checkCameraAvailable();
        //start timer to check video availability
        //startTimer();
        //initVideoViewVariable();


    }

    public int getVersionCode(){
        PackageInfo pinfo = null;
        try {
            pinfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        int versionNumber = pinfo.versionCode;
        return versionNumber;
    }
    public void captureStream(){
        screenShot = getCurrentFrame();
        if( screenShot !=null){
            screenShotImageView.setImageBitmap(screenShot);
            //manage view to show screenshop panel/imageView and hide vitamid view/PanelButton
            ManageViewForScreenShot(true);
        }



    }
    public void ManageViewForScreenShot(boolean isScreenShot){
        if(isScreenShot){

            //videoView.setVisibility(View.INVISIBLE);
            panelButton.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            panelSnapshoot.setVisibility(View.VISIBLE);
            screenShotImageView.setVisibility(View.VISIBLE);


        }else{


            panelSnapshoot.setVisibility(View.GONE);
            screenShotImageView.setVisibility(View.GONE);
            //videoView.setVisibility(View.VISIBLE);
            panelButton.setVisibility(View.VISIBLE);

        }

        //start saving screenShot

    }

    public void initStreamingVariable(){



        mButtonStart = (ImageButton) findViewById(R.id.start);
        mButtonCapture = (ImageButton) findViewById(R.id.capture);
        setting = (ImageButton) findViewById(R.id.settings);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        panelButton = (FrameLayout) findViewById(R.id.panelButton);
        panelSnapshoot = (FrameLayout) findViewById(R.id.panelSnapshoot);
        panelButton.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        videoView = (VideoView) findViewById(R.id.vitamio_videoView);
        screenShotImageView = (ImageView) findViewById(R.id.imageViewScreenShot);
        cancelScreenShot = (ImageButton) findViewById(R.id.cancelSnapshoot);
        saveScreenShot = (ImageButton) findViewById(R.id.saveSnapshoot);
        fullScreenMode = false;
        localStorage = "/SecuritrixStreaming/Images/";

        mVideoView = (VideoView) findViewById(R.id.vitamio_videoView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            path = extras.getString("cameraUrl");

            // and get whatever type user account id is
            cameraID = extras.getInt("cameraID");
            Log.v("path"+"camera",path+"camera"+cameraID);

        }

    }

    public void startVideoViewFromCamera(String path,int cameraID){

        SystemClock.sleep(2000);
        Vitamio.isInitialized(this);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        mVideoView.getHolder().setFormat(PixelFormat.RGBX_8888);
        mVideoView.setVideoChroma(MediaPlayer.VIDEOCHROMA_RGB565);
        mVideoView.setMediaController(null);

        mVideoView.setVideoPath(path);


    }
    public void initVideoViewVariable(){

        //mVideoView.setKeepScreenOn(true);
        //mVideoView.setBufferSize(256*256);
        //mVideoView.setOnBufferingUpdateListener(this);
        //fast
        mVideoView.setOnInfoListener(this);
        //mVideoView.setOnBufferingUpdateListener(this);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                // close the progress bar and play the video
                //progressDialog.dismiss();
                mProgressBar.setVisibility(View.INVISIBLE);
                mediaPlayer.setScreenOnWhilePlaying(true);
                mediaPlayer.setBufferSize(256*256);
                mediaPlayer.setPlaybackSpeed(1.0f);

            }

        });

        mVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("TAG", "Video 1 clicked, starting playback");
                if(mVideoView.isPlaying()&&(panelSnapshoot.getVisibility()!=View.VISIBLE)){
                    if(isFullScreen()){
                        //toggle panel button
                        if(panelButton.getVisibility()==View.VISIBLE)
                            panelButton.setVisibility(View.INVISIBLE);
                        else
                            panelButton.setVisibility(View.VISIBLE);
                    }else
                        panelButton.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        mButtonCapture.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                captureStream();

            }
        });
        mButtonStart.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggleFullScreen();
            }
        });

        cancelScreenShot.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //cancel screenshop
                ManageViewForScreenShot(false);
            }
        });
        saveScreenShot.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //ManageViewForScreenShot(false);
                //start saving screenshoot
                startSavingScreenShot();
                ManageViewForScreenShot(false);
            }
        });
        setting.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent nextScreen = new Intent(getApplicationContext(), SettingsActivity.class);
                nextScreen.putExtra("cameraUrl",path);
                startActivity(nextScreen);
            }
        });
        //init database image
        mydbImage = new DBHelper(this);

    }

    public void checkCameraAvailable(){
        //read the camera path
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            path = extras.getString("cameraUrl");

            // and get whatever type user account id is
            cameraID = extras.getInt("cameraID");
            Log.v("path"+"camera",path+"camera"+cameraID);
            //send request to check camera
            // end reading configuration informations
            url = GlobalService.getUrl("cameraCheckAvailability",this);

            cameraStatus = new HashMap<String, Object>();
            cameraStatus.put("sessionID",GlobalService.getUserSession(this));
            cameraStatus.put("cameraID",cameraID);

            serverCommunication = new ActivityServerCommunication(StreamCameraActivity.this,cameraStatus, "GET", url, ServiceIdentification.checkCameraAvailability);
            serverCommunication.delegate = StreamCameraActivity.this;
            serverCommunication.execute();
        }


    }

    public void processFinish(int response,Object object,int serviceId){
        //this you will received result fired from async class of onPostExecute(result) method.


        if(response ==-1){
            //an error occurs may be network issue
            /*
            Toast.makeText(StreamCameraActivity.this,
                    R.string.communication_error, Toast.LENGTH_LONG)
                    .show();
            */
            backToDashBoardActivity("Error",getResources().getString(R.string.communication_error));
            //gotoDashBoard();

        }
        else{


            if(response==0){
                /*
                Toast.makeText(StreamCameraActivity.this,
                        "camera is available", Toast.LENGTH_LONG)
                        .show();
                */

                startVideoViewFromCamera(path,cameraID);

                initVideoViewVariable();

                //start timer to check video availability
                //startTimer();
            }
            else if(response ==344){
                //ok
                /*
                Toast.makeText(StreamCameraActivity.this,
                        R.string.camera_unavailable, Toast.LENGTH_LONG)
                        .show();
                */
                //gotoDashBoard();
                AlertDialog.Builder dialog = new AlertDialog.Builder(StreamCameraActivity.this);
                dialog.setTitle("Camera Not Available");
                dialog.setMessage(getResources().getString(R.string.VideoView_error_text_unknown));
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        backToDashBoard();
                    }
                }).show();

                backToDashBoardActivity("Error",getResources().getString(R.string.communication_error));
            }


            else{

                /*
                Toast.makeText(StreamCameraActivity.this,
                        R.string.camera_unavailable, Toast.LENGTH_LONG)
                        .show();
                */
                AlertDialog.Builder dialog = new AlertDialog.Builder(StreamCameraActivity.this);
                dialog.setTitle("Camera Not Available");
                dialog.setMessage(getResources().getString(R.string.VideoView_error_text_unknown));
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        backToDashBoard();


                    }
                }).show();
            }

        }

    }

    public void backToDashBoard(){
        super.onBackPressed();

    }
    public void backToDashBoardActivity(String title,String msg){
        AlertDialog.Builder dialog = new AlertDialog.Builder(StreamCameraActivity.this);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            backToDashBoard();
            }
        }).show();

    }
    public void startSavingScreenShot(){
        //String date =new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss",Locale.getDefault()).format(new Date());
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss",Locale.getDefault()).format(new Date());
        //storeImage(screenShot,"IMG_"+timeStamp);
        //storeImage(screenShot,"IMG_001");
        //PictUtil.saveToFile(PictUtil.getSavePath()+"IMG_001",screenShot);
        //0 is for SD Card while 1 is for phone storage
        if(getDefaultLocalStorage() == 1){
            saveImageToInternalStorage(screenShot,"IMG_"+timeStamp,timeStamp,timeStamp);
        }else{
            saveImageToExternalStorage(screenShot,"IMG_"+timeStamp,timeStamp,timeStamp);
        }



    }

    private boolean storeImage(Bitmap imageData, String filename) {
        //get path to external storage (SD card)
        //String iconsStoragePath = Environment.getExternalStorageDirectory() + "/myAppDir/myImages/"
        String iconsStoragePath = Environment.getExternalStorageDirectory() + "/SecuritrixStreaming/Images/";
        File sdIconStorageDir = new File(iconsStoragePath);

        //create storage directories, if they don't exist
        sdIconStorageDir.mkdirs();

        try {
            String filePath = sdIconStorageDir.toString() + filename;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

            //choose another format if PNG doesn't suit you
            imageData.compress(Bitmap.CompressFormat.PNG, 100, bos);

            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving FileNotFoundException: " + e.getMessage());
            Toast.makeText(StreamCameraActivity.this,
                    R.string.error_saving_image,
                    Toast.LENGTH_LONG).show();
            return false;
        } catch (IOException e) {
            Log.w("TAG", "Error saving IOException: " + e.getMessage());
            Toast.makeText(StreamCameraActivity.this,
                    R.string.error_saving_image,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    public boolean saveImageToExternalStorage(Bitmap image,String fileName,String hours,String date) {
        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;

        if(mydbImage.hasSDCard()){
            try {
                File dir = new File(fullPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                OutputStream fOut = null;
                File file = new File(fullPath,fileName+".png");
                file.createNewFile();
                fOut = new FileOutputStream(file);

                // 100 means no compression, the lower you go, the stronger the compression
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();

                MediaStore.Images.Media.insertImage(this.getContentResolver(),file.getAbsolutePath(), file.getName(), file.getName());
                if(mydbImage.insertImage(fileName,file.getAbsolutePath(),hours,date)){

                    Toast.makeText(StreamCameraActivity.this,
                            R.string.saving_image_well,
                            Toast.LENGTH_LONG).show();
                }

                else{

                    Toast.makeText(StreamCameraActivity.this,
                            R.string.error_saving_image,
                            Toast.LENGTH_LONG).show();
                }
                return true;

            } catch (Exception e) {
                Log.e("saveToExternalStorage()", e.getMessage());
                Toast.makeText(StreamCameraActivity.this,
                        R.string.error_saving_image,
                        Toast.LENGTH_LONG).show();
                return false;
            }

        }else{
            Toast.makeText(StreamCameraActivity.this,
                    R.string.sd_Card_Not_found,
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    //set the default local storage
    public int getDefaultLocalStorage(){
        //O if for phone storage while 1 is for SD card
        int storage =1;
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        localStorage = SP.getString("localStorage","");
        if(localStorage.equals("")){
            SharedPreferences.Editor editor = SP.edit();

            if(mydbImage.hasSDCard()){
                //set sd card has default
                editor.putString("localStorage","0");
                editor.commit();
                storage =0;
                localStorage="0";
            }else{
                //set mobile storage as default
                editor.putString("localStorage","1");
                editor.commit();
                storage =1;
                localStorage="1";
            }

        }
        //if the selected storage is SD Card while there isn't SD Card notify user
        if((storage==0)&&(!mydbImage.hasSDCard()))
                return 1;

        return Integer.parseInt(localStorage);
    }
    /*
    public boolean saveImageToInternalStorage(Bitmap image,String fileName) {
SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        try {
            // Use the compress method on the Bitmap object to write image to  the OutputStream
            FileOutputStream fos = this.openFileOutput(fileName+".png", Context.MODE_PRIVATE);

            // Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            Toast.makeText(StreamCameraActivity.this,
                    R.string.saving_image_well,
                    Toast.LENGTH_LONG).show();
            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            Toast.makeText(StreamCameraActivity.this,
                    R.string.error_saving_image,
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }
    */
    //save image to internal storage
    private String saveImageToInternalStorage(Bitmap bitmapImage,String fileName,String hours,String date){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        //APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD
        // directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        //File directory = cw.getDir(APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD, Context.MODE_PRIVATE);
        File directory = cw.getDir(APP_THUMBNAIL_PATH_SD_CARD, Context.MODE_PRIVATE);
        // Create imageDir
        //File mypath=new File(directory,"profile.jpg");

        File file=new File(directory,fileName+".png");
        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);

            //save imageinto database
            if(mydbImage.insertImage(fileName,file.getAbsolutePath(),hours,date)){

                Toast.makeText(StreamCameraActivity.this,
                        R.string.saving_image_well,
                        Toast.LENGTH_LONG).show();
            }

            else{

                Toast.makeText(StreamCameraActivity.this,
                        R.string.error_saving_image,
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
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
    public boolean onInfo(MediaPlayer mMediaPlayer, int what, int mCurrentBufferPercentage) {
        //Log.d("onInfo: (%d, %d)", what, extra);
        android.util.Log.i("Multisight","Mediaplayer onInfo: ("+what+", "+mCurrentBufferPercentage+")");
        dataReceivedState =true;
        /*
        if (this != null) {
            this.onInfo(mMediaPlayer, what,mCurrentBufferPercentage);
        } else if (mMediaPlayer != null) {
        */
        mplayer = mMediaPlayer ;
        if (mMediaPlayer != null) {
            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                /*
                Toast.makeText(StreamCameraActivity.this,
                        "Buffer Start: ",
                        Toast.LENGTH_LONG).show();
                */
                /*
                mMediaPlayer.pause();
                if (mProgressBar != null)
                    mProgressBar.setVisibility(View.VISIBLE);
                */
                if(!playerStarted)
                {

                        playerStarted=true;
                        mMediaPlayer.start();
                        if (mProgressBar != null)
                            mProgressBar.setVisibility(View.GONE);
                        //mMediaPlayer.getVideoHeight();

                }
            } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                /*
                Toast.makeText(StreamCameraActivity.this,
                        "buffer End: ",
                        Toast.LENGTH_LONG).show();
                */

                mMediaPlayer.start();
                if (mProgressBar != null)
                    mProgressBar.setVisibility(View.GONE);
            }
            else if (what == MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED) {
                /*
                Toast.makeText(StreamCameraActivity.this,
                        "buffer download: "+mCurrentBufferPercentage,
                        Toast.LENGTH_LONG).show();
                */
                /*
                if(!playerStarted)
                {
                    if(mCurrentBufferPercentage>10)
                    {
                        playerStarted=true;
                        mMediaPlayer.start();
                        if (mProgressBar != null)
                            mProgressBar.setVisibility(View.GONE);
                        //mMediaPlayer.getVideoHeight();

                    }
                }
                */
                /*
                if(mCurrentBufferPercentage>30)
                {
                    mMediaPlayer.start();
                    if (mProgressBar != null)
                        mProgressBar.setVisibility(View.GONE);
                }
                else if(mCurrentBufferPercentage<10)
                {
                    mMediaPlayer.pause();
                    if (mProgressBar != null)
                        mProgressBar.setVisibility(View.VISIBLE);
                }
                */
            }
        }
        return true;
    }
    public Bitmap getCurrentFrame(){
        if(mplayer==null)
            return null;
        return mplayer.getCurrentFrame();
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
    /*
    @Override
    public void onBufferingUpdate(MediaPlayer mMediaPlayer, int percent) {

        // TODO Auto-generated method stub
        if(mMediaPlayer.isPlaying()){
            is_playing_firstTime = true;
        }else{
            System.out.println("MP stopped");
        }
        System.out.println(percent + "% 0");
        if(isFirst_Loading_Complete) {
            System.out.println(percent + "% 1");
        }
        if (percent < 5) {
            mMediaPlayer.pause();
            isStart = true;
            if(isFirst_Loading_Complete) {
                System.out.println(percent + "% 2");
            }
        }
        else if (percent < 20) {
            // loadRateView.setText(percent + "%");
            // allows the user to actively aware Play
        } else { // > 20%
            // cho phep play + tat spin
            mMediaPlayer.start();
            System.out.println(percent + "% 3");
            isFirst_Loading_Complete = true;// true because first loading is displayed
            mMediaPlayer.setBufferSize(5000);
            System.out.println(percent + "% 4");
        }
    }
    */

    public void toggleFullScreen(){

        if(fullScreenMode){

            fullScreenMode =false;
            mVideoView.setVideoLayout(VodView.VIDEO_LAYOUT_ORIGIN, 0);
            panelButton.setVisibility(View.VISIBLE);
        }else{

            fullScreenMode =true;
            mVideoView.setVideoLayout(VodView.VIDEO_LAYOUT_STRETCH, 0);

            panelButton.setVisibility(View.INVISIBLE);
        }

    }
    public boolean isFullScreen(){

        return fullScreenMode;
    }

    /*
    @Override
    protected void onResume() {
        if (mVideoView.isPlaying() == false) {

            mProgressBar.setVisibility(View.VISIBLE);
        }

        mVideoView.start();
        super.onResume();
    }

    */
    @Override
    protected void onRestart() {

        if (mVideoView.isPlaying() == false) {

            mProgressBar.setVisibility(View.VISIBLE);
        }
        checkCameraAvailable();
        mVideoView.start();

        //start timer to check video availability

        super.onRestart();
        //mVideoView.resume();

    }
    /*
    @Override
    protected void onRestart() {
        if (mVideoView.isPlaying() == false) {

            mProgressBar.setVisibility(View.VISIBLE);
        }

        mVideoView.start();
        super.onResume();
    }
    */
    @Override
    protected void onPause() {

        mVideoView.pause();
        super.onPause();
    }
    @Override
    protected void onStop() {

        //Log.i("onStop....","onStop");
        mVideoView.pause();
        disableCamera();
        //stoptimertask();
        super.onStop();
    }
    @Override
    protected void onDestroy() {

        //Log.i("destroy....","destroy");
        //disable the camera
        //stoptimertask();
        //disableCamera();
        super.onDestroy();

    }
    public void disableCamera(){
        cameraStatus = new HashMap<String, Object>();
        cameraStatus.put("sessionID",GlobalService.getUserSession(this));
        cameraStatus.put("cameraID",cameraID);
        url = GlobalService.getUrl("cameradisableAvailability",this);
        serverCommunication = new ActivityServerCommunication(StreamCameraActivity.this,cameraStatus, "GET", url, ServiceIdentification.disableCameraAvailability);
        serverCommunication.delegate = StreamCameraActivity.this;
        serverCommunication.execute();
    }

    public void startTimer() {

        //set a new Timer
        timer = new Timer();
        dataReceivedState =false;
        //initialize the TimerTask's job
        initializeTimerTask();

        timer.schedule(timerTask,threadInitialTimeDelai,threadTimeDelai); //

    }

    public void initializeTimerTask() {


        timerTask = new TimerTask() {

            public void run() {


                //use a handler to run a toast that shows the current timestamp

                handler.post(new Runnable() {

                    public void run() {
                        checkDataReceivedState();

                    }

                });

            }

        };

    }

    public void stoptimertask() {

        //stop the timer, if it's not already null
        if (timer != null) {

            timer.cancel();

        }

    }

    public void checkDataReceivedState(){

        //if data has not been received (dataReceivedState=false)
        if(!dataReceivedState){
            Log.i("ischeckplaying",""+mVideoView.isPlaying());

            //if (mVideoView.isPlaying() == false) {

                //mProgressBar.setVisibility(View.VISIBLE);
                mVideoView.seekTo(mVideoView.getCurrentPosition());

                mVideoView.start();
            //}

            //restart
            //mVideoView.resume();


            //mVideoView.start();
            //startVideoViewFromCamera(path,cameraID);
        }
        //else (dataReceiveState=true) reset dataReceivedState =false
        else

            dataReceivedState =false;
    }
}
