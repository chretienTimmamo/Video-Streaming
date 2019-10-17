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

public class RecordAdapterList extends BaseAdapter {

    private Activity activity;
    private JSONArray data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader;

    public RecordAdapterList(Activity a, JSONArray d) {
        super();
        activity = a;

        this.data=d;
        notifyDataSetChanged();
        Log.i("length>>>>>>", "" + data.length() + "actual>>>>>>");
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

            holder.PlanDescription.setText(data.getJSONObject(position).getString("day"));
            holder.image.setImageResource(R.drawable.ic_menu_archive);
            //holder.PlanDescription.setText(data.getJSONObject(position).getString("cameraPosition"));

        } catch (JSONException e) {

        }

        return view;
    }
    public void setRecordList(JSONArray jsonRecord){
        this.data = jsonRecord;

    }
    static class ViewHolder {
        TextView PlanName;
        TextView PlanDescription;
        ImageView image;
    }

}
