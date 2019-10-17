package fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mshopmobile.app.com.videostreaming.R;
import mshopmobile.app.com.videostreaming.VideoTabActivity;
import util.ServiceIdentification;

/**
 * Created by root on 13/04/16.
 */
public class RecordAdapterDetailList extends BaseAdapter {

    private Activity activity;
    private JSONArray data;
    private static LayoutInflater inflater=null;
    private String recordingFrom;
    private String recordingTo;
    //public ImageLoader imageLoader;

    public RecordAdapterDetailList(Activity a, JSONArray d) {
        super();
        activity = a;

        this.data=d;
        notifyDataSetChanged();
        Log.i("length>>>>>>", "" + data.length() + "actual>>>>>>");
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {

        //Log.i("Incoming data>>>>>>", "" + data.length());
        return data.length();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.record_detail_row, parent, false);
            holder = new ViewHolder();
            holder.PlanName = (TextView) view.findViewById(R.id.recordName);
            holder.PlanDescription = (TextView) view.findViewById(R.id.recordDescription);
            holder.image = (ImageView) view.findViewById(R.id.record_image);
            holder.duration = (TextView) view.findViewById(R.id.duration);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        try {

            holder.PlanName.setText(data.getJSONObject(position).getString("streamName"));
            holder.duration.setText(data.getJSONObject(position).getString("time"));
            recordingFrom = data.getJSONObject(position).getString("recordingFrom");
            recordingTo = data.getJSONObject(position).getString("recordingTo");
            holder.PlanDescription.setText("record from "+recordingFrom+" To "+recordingTo);

        } catch (JSONException e) {

        }

        //holder.image.setImageResource(R.drawable.ic_menu_archive);

        //ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.image, options, animateFirstListener);

        return view;
    }

    public void setRecordList(JSONArray d){

        data=d;

    }
    static class ViewHolder {
        TextView PlanName;
        TextView PlanDescription;
        TextView duration;
        ImageView image;
    }

}