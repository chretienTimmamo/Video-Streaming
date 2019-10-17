package mshopmobile.app.com.videostreaming;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.pedro.vlc.VlcVideoLibrary;

import java.util.ArrayList;
import java.util.List;

import mshopmobile.app.com.videostreaming.thread.ThreadInfo;

public class NineViewFragment extends  Fragment {

    View fragmentView;
    List<VlcVideoLibrary> videoLibrary = new ArrayList<>();
    ThreadInfo threadInfo,threadInfo1,threadInfo2,threadInfo3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        fragmentView = inflater.inflate(R.layout.activity_nine_fragment, parent, false);
        openStreaming();
        return fragmentView;
    }

    public void openStreaming(){
        SurfaceView surfaceView1 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewThree1);
        SurfaceView surfaceView2 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewThree2);
        SurfaceView surfaceView3 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewThree3);
        SurfaceView surfaceView4 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewThree4);

        SurfaceView surfaceView [] = {surfaceView1,surfaceView2,surfaceView3,surfaceView4};
        threadInfo  = new ThreadInfo(surfaceView[0],getActivity());
        threadInfo1 = new ThreadInfo(surfaceView[1],getActivity());
        threadInfo2 = new ThreadInfo(surfaceView[2],getActivity());
        threadInfo3 = new ThreadInfo(surfaceView[3],getActivity());
        new Thread(threadInfo).start();
        new Thread(threadInfo1).start();
        new Thread(threadInfo2).start();
        new Thread(threadInfo3).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadInfo.getVlcVideoLibrary().stop();
        threadInfo1.getVlcVideoLibrary().stop();
        threadInfo2.getVlcVideoLibrary().stop();
        threadInfo3.getVlcVideoLibrary().stop();
    }


}
