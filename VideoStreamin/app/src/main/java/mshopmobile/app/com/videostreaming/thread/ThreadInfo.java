package mshopmobile.app.com.videostreaming.thread;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.SurfaceView;
import android.widget.Toast;

import dmax.dialog.SpotsDialog;
import mshopmobile.app.com.videostreaming.R;
import mshopmobile.app.com.videostreaming.soap.SoapService;
import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;

public class ThreadInfo implements  Runnable,VlcListener {

    private String name;
    private SurfaceView surfaceView;
    private VlcVideoLibrary vlcVideoLibrary;
    private Context context;
    ProgressDialog progressDoalog;
    private AlertDialog progressDialog;
    //@IP camera: 192.168.0.72

    //Read

    public ThreadInfo(SurfaceView view, Context context) {
        this.surfaceView = view;
        this.context = context;
    }

    @Override
    public void run() {
        SoapService soapService  = new  SoapService("admin", "admin","192.168.0.72:10253");
        vlcVideoLibrary = new VlcVideoLibrary(this.context, this, this.surfaceView);
        if (!vlcVideoLibrary.isPlaying()) {
            vlcVideoLibrary.play(soapService.getStreamingInformation());
        } else {
            vlcVideoLibrary.stop();
        }
    }



    @Override
    public void onComplete() {
        progressDoalog = new ProgressDialog(this.context, R.style.Custom);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading Stream....");
        progressDoalog.setIndeterminate(true);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        final int[] i = {0};
        progressDoalog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDoalog.getProgress() <= progressDoalog
                            .getMax()) {
                        Thread.sleep(140);
                        i[0]++;
                        if (i[0]== progressDoalog
                                .getMax()) {
                            progressDoalog.dismiss();
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
    }


   /* @Override
    public void onComplete() {
        progressDoalog = new ProgressDialog(this.context);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Loading Stream....");
        progressDoalog.setIndeterminate(true);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        final int[] i = {0};
        progressDoalog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDoalog.getProgress() <= progressDoalog
                            .getMax()) {
                        Thread.sleep(115);
                        i[0]++;
                        if (i[0]== progressDoalog
                                .getMax()) {
                            progressDoalog.dismiss();
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
    }
    */

    @Override
    public void onError() {
        Toast.makeText(this.context, "Error, make sure your endpoint is correct", Toast.LENGTH_SHORT).show();
        vlcVideoLibrary.stop();
    }

    public VlcVideoLibrary getVlcVideoLibrary() {
        return vlcVideoLibrary;
    }
}
