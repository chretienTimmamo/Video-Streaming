package mshopmobile.app.com.videostreaming;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;


import mshopmobile.app.com.videostreaming.thread.ThreadInfo;

public class OneViewFragment extends Fragment {
    View fragmentView;
    ThreadInfo threadInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.activity_one_fragment, parent, false);
        openStreaming();
        return fragmentView;
    }

    public void openStreaming(){
        SurfaceView surfaceView = (SurfaceView) fragmentView.findViewById(R.id.surfaceviewone);
        threadInfo = new ThreadInfo(surfaceView,getActivity());
        new Thread(threadInfo).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        threadInfo.getVlcVideoLibrary().stop();
    }


}

