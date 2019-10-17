package mshopmobile.app.com.videostreaming;

import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

import io.vov.vitamio.utils.Log;

/**
 * Created by root on 25/04/16.
 */
public class MyScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
    private int mW, mH;
    static final int MIN_WIDTH = 100;
    // Root view's LayoutParams
    private FrameLayout.LayoutParams mRootParam;
    // Custom Video View
    private VodView mVodView;

    public MyScaleGestureListener(VodView mVodView){
        this.mVodView = mVodView;
    }
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        // scale our video view
        mW *= detector.getScaleFactor();
        mH *= detector.getScaleFactor();
        if (mW < MIN_WIDTH) { // limits width
            mW = mVodView.getWidth();
            mH = mVodView.getHeight();
        }
        Log.d("onScale", "scale=" + detector.getScaleFactor() + ", w=" + mW + ", h=" + mH);
        mVodView.setFixedVideoSize(mW, mH); // important
        mRootParam.width = mW;
        mRootParam.height = mH;
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        mW = mVodView.getWidth();
        mH = mVodView.getHeight();
        Log.d("onScaleBegin", "scale=" + detector.getScaleFactor() + ", w=" + mW + ", h=" + mH);
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d("onScaleEnd", "scale=" + detector.getScaleFactor() + ", w=" + mW + ", h=" + mH);
    }

}
