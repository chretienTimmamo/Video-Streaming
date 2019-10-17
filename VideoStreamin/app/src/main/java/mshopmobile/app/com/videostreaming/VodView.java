package mshopmobile.app.com.videostreaming;

import android.content.Context;
import android.util.AttributeSet;

import io.vov.vitamio.widget.VideoView;

/**
 * Created by root on 25/04/16.
 */
public class VodView extends VideoView {

    public VodView(Context context) {
        super(context);
    }

    public VodView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VodView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * Resize video view by using SurfaceHolder.setFixedSize(...). See {@link android.view.SurfaceHolder#setFixedSize}
     * @param width
     * @param height
     */
    public void setFixedVideoSize(int width, int height)
    {
        getHolder().setFixedSize(width, height);
    }
}
