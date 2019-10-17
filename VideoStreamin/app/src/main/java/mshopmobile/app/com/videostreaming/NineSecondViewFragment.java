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

public class NineSecondViewFragment extends Fragment {

    View fragmentView;
    List<VlcVideoLibrary> videoLibrary = new ArrayList<>();
    ThreadInfo threadInfo,threadInfo1,threadInfo2,threadInfo3,threadInfo4,threadInfo5,threadInfo6,threadInfo7,threadInfo8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        fragmentView = inflater.inflate(R.layout.activity_nine_second_view_fragment, parent, false);
        openStreaming();
        return fragmentView;
    }

    public void openStreaming(){
        SurfaceView surfaceView1 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSecondNineteen1);
        SurfaceView surfaceView2 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSecondNineteen2);
        SurfaceView surfaceView3 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSecondNineteen3);
        SurfaceView surfaceView4 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSecondNineteen4);
        SurfaceView surfaceView5 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSecondNineteen5);
        SurfaceView surfaceView6 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSecondNineteen6);
        SurfaceView surfaceView7 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSecondNineteen7);
        SurfaceView surfaceView8 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSecondNineteen8);
        SurfaceView surfaceView9 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSecondNineteen9);


        SurfaceView surfaceView [] = {surfaceView1,surfaceView2,surfaceView3,surfaceView4,surfaceView5,surfaceView6,surfaceView7,surfaceView8,surfaceView9};
        threadInfo  = new ThreadInfo(surfaceView[0],getActivity());
        threadInfo1 = new ThreadInfo(surfaceView[1],getActivity());
        threadInfo2 = new ThreadInfo(surfaceView[2],getActivity());
        threadInfo3 = new ThreadInfo(surfaceView[3],getActivity());
        threadInfo4 = new ThreadInfo(surfaceView[4],getActivity());
        threadInfo5 = new ThreadInfo(surfaceView[5],getActivity());
        threadInfo6 = new ThreadInfo(surfaceView[6],getActivity());
        threadInfo7 = new ThreadInfo(surfaceView[7],getActivity());
        threadInfo8 = new ThreadInfo(surfaceView[8],getActivity());


        new Thread(threadInfo).start();
        new Thread(threadInfo1).start();
        new Thread(threadInfo2).start();
        new Thread(threadInfo3).start();
        new Thread(threadInfo4).start();
        new Thread(threadInfo5).start();
        new Thread(threadInfo6).start();
        new Thread(threadInfo7).start();
        new Thread(threadInfo8).start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadInfo.getVlcVideoLibrary().stop();
        threadInfo1.getVlcVideoLibrary().stop();
        threadInfo2.getVlcVideoLibrary().stop();
        threadInfo3.getVlcVideoLibrary().stop();
        threadInfo4.getVlcVideoLibrary().stop();
        threadInfo5.getVlcVideoLibrary().stop();
        threadInfo6.getVlcVideoLibrary().stop();
        threadInfo7.getVlcVideoLibrary().stop();
        threadInfo8.getVlcVideoLibrary().stop();
    }


}
