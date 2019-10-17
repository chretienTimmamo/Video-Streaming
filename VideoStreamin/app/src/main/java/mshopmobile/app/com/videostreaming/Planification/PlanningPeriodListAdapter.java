package mshopmobile.app.com.videostreaming.Planification;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import mshopmobile.app.com.videostreaming.R;

/**
 * Created by maclover on 25/01/16.
 */
public class PlanningPeriodListAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Planning> data;
    private static LayoutInflater inflater=null;


    //contructor
    public PlanningPeriodListAdapter(Activity a, ArrayList<Planning> d) {
        //Log.e("INSTANTIATION0", "starting instanciating LazyAdapterHomeList");
        this.activity = a;
        this.data=d;
        Log.i("INSTANTIATION1", ""+d.size());
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // imageLoader=new ImageLoader(activity.getApplicationContext());
        //Log.e("INSTANTIATION2", "finish instanciating LazyAdapterHomeList");
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.alarm_list_element, null);
        TextView time = (TextView) vi.findViewById(R.id.textView_alarm_time);
        TextView days = (TextView) vi.findViewById(R.id.textView_alarm_days);
        ImageView periodStatus = (ImageView)vi.findViewById(R.id.periodStatus);
        Planning planning  = new Planning();
        planning = data.get(position);
        Log.i("period", ""+planning.getDaycode());
        time.setText("from \t"+planning.getRecordingFrom()+"\tTo\t"+planning.getRecordingTo());
        days.setText(planning.getDaycode());
        if(planning.isStatus())
            periodStatus.setImageResource(R.drawable.period_activated);
        else
            periodStatus.setImageResource(R.drawable.period_desactivated);
        return vi;
    }
}
