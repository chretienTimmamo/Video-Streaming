package fragment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mshopmobile.app.com.videostreaming.CameraInformation;
import mshopmobile.app.com.videostreaming.R;

public class CameraAdapterList extends BaseAdapter {
    
    private Activity activity;
    private JSONArray data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    
    public CameraAdapterList(Activity a, JSONObject d) {

        activity = a;
        try {
            data=d.getJSONArray("result");
        } catch (JSONException e) {
            data =new JSONArray();

        }

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {

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
            view = inflater.inflate(R.layout.item_list_image, parent, false);
            holder = new ViewHolder();
            holder.PlanName = (TextView) view.findViewById(R.id.plamMame);
            holder.PlanDescription = (TextView) view.findViewById(R.id.planDescription);
            holder.image = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        try {

            holder.PlanName.setText(data.getJSONObject(position).getString("cameraName"));
            holder.PlanDescription.setText(data.getJSONObject(position).getString("cameraPosition"));
            if(position==0)
                holder.image.setImageResource(R.drawable.cameraicon1);
            else
                holder.image.setImageResource(R.drawable.cameraicon2);

        } catch (JSONException e) {

        }

        //ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.image, options, animateFirstListener);

        return view;
    }

    public void setCameraList(JSONObject d){

        try {
            data=d.getJSONArray("result");
        } catch (JSONException e) {
            data =null;

        }

    }
    static class ViewHolder {
        TextView PlanName;
        TextView PlanDescription;
        ImageView image;
    }

}