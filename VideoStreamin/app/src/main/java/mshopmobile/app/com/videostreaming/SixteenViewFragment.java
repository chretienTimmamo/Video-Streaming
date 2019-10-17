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

public class SixteenViewFragment extends  Fragment {

    View fragmentView;
    List<VlcVideoLibrary> videoLibrary = new ArrayList<>();
    ThreadInfo threadInfo,threadInfo1,threadInfo2,threadInfo3,threadInfo4,threadInfo5,threadInfo6,threadInfo7,threadInfo8,threadInfo9,threadInfo10,threadInfo11,threadInfo12,threadInfo13,threadInfo14,threadInfo15;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        fragmentView = inflater.inflate(R.layout.activity_sixteen_view_fragment, parent, false);
        openStreaming();
        return fragmentView;
    }

    public void openStreaming(){
        SurfaceView surfaceView1 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen1);
        SurfaceView surfaceView2 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen2);
        SurfaceView surfaceView3 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen3);
        SurfaceView surfaceView4 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen4);
        SurfaceView surfaceView5 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen5);
        SurfaceView surfaceView6 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen6);
        SurfaceView surfaceView7 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen7);
        SurfaceView surfaceView8 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen8);
        SurfaceView surfaceView9 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen9);
        SurfaceView surfaceView10 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen10);
        SurfaceView surfaceView11 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen11);
        SurfaceView surfaceView12 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen12);
        SurfaceView surfaceView13 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen13);
        SurfaceView surfaceView14 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen14);
        SurfaceView surfaceView15 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen15);
        SurfaceView surfaceView16 = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewSixteen16);

        SurfaceView surfaceView [] = {surfaceView1,surfaceView2,surfaceView3,surfaceView4,surfaceView5,surfaceView6,surfaceView7,surfaceView8,surfaceView9,surfaceView10,surfaceView11,surfaceView12,surfaceView13,surfaceView14,surfaceView15,surfaceView16};
        threadInfo  = new ThreadInfo(surfaceView[0],getActivity());
        threadInfo1 = new ThreadInfo(surfaceView[1],getActivity());
        threadInfo2 = new ThreadInfo(surfaceView[2],getActivity());
        threadInfo3 = new ThreadInfo(surfaceView[3],getActivity());
        threadInfo4 = new ThreadInfo(surfaceView[4],getActivity());
        threadInfo5 = new ThreadInfo(surfaceView[5],getActivity());
        threadInfo6 = new ThreadInfo(surfaceView[6],getActivity());
        threadInfo7 = new ThreadInfo(surfaceView[7],getActivity());
        threadInfo8 = new ThreadInfo(surfaceView[8],getActivity());
        threadInfo9 = new ThreadInfo(surfaceView[9],getActivity());
        threadInfo10 = new ThreadInfo(surfaceView[10],getActivity());
        threadInfo11 = new ThreadInfo(surfaceView[11],getActivity());
        threadInfo12 = new ThreadInfo(surfaceView[12],getActivity());
        threadInfo13 = new ThreadInfo(surfaceView[13],getActivity());
        threadInfo14 = new ThreadInfo(surfaceView[14],getActivity());
        threadInfo15 = new ThreadInfo(surfaceView[15],getActivity());


        new Thread(threadInfo).start();
        new Thread(threadInfo1).start();
        new Thread(threadInfo2).start();
        new Thread(threadInfo3).start();
        new Thread(threadInfo4).start();
        new Thread(threadInfo5).start();
        new Thread(threadInfo6).start();
        new Thread(threadInfo7).start();
        new Thread(threadInfo8).start();
        new Thread(threadInfo9).start();
        new Thread(threadInfo10).start();
        new Thread(threadInfo11).start();
        new Thread(threadInfo12).start();
        new Thread(threadInfo13).start();
        new Thread(threadInfo14).start();
        new Thread(threadInfo15).start();

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
        threadInfo9.getVlcVideoLibrary().stop();
        threadInfo10.getVlcVideoLibrary().stop();
        threadInfo11.getVlcVideoLibrary().stop();
        threadInfo12.getVlcVideoLibrary().stop();
        threadInfo13.getVlcVideoLibrary().stop();
        threadInfo14.getVlcVideoLibrary().stop();
        threadInfo15.getVlcVideoLibrary().stop();
    }


}
