
package fragment;

/**
 * Created by root on 9/14/15.
 */

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
import mshopmobile.app.com.videostreaming.R;
import mshopmobile.app.com.videostreaming.StreamCameraActivity;
import mshopmobile.app.com.videostreaming.VideoTabActivity;
import mshopmobile.app.com.videostreaming.VideoTabManagerActivity;
import util.ServiceIdentification;
import util.WebService.ActivityServerCommunication;

public class MyCamFragmentTab extends android.support.v4.app.Fragment {
    View view;
    ListView list;
    CameraAdapterList adapter;
    TextView no_items_tv;
    JSONObject CameraListToDisplay;
    String urlCamera;
    ActivityServerCommunication serverCommunication=null;
    Map<String, Object> CameraList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_camera_list_tab, container, false);
        view = rootView;
        Bundle bundle = this.getArguments();
        String homeID = bundle.getString("homeID", "");
        performCameraList(homeID);
        return rootView;

    }

    //perform the Camera list
    public void performCameraList(String homeID){

        CameraListToDisplay = new JSONObject();
        list=(ListView)view.findViewById(R.id.listCamera);
        adapter=new CameraAdapterList(getActivity(),CameraListToDisplay);
        //no_items_tv = (TextView) view.findViewById(R.id.empty_list_notification_Alarm);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                ((VideoTabManagerActivity)getActivity()).launchVideo(position);

            }
        });

        ((VideoTabManagerActivity)getActivity()).callListElements(adapter, list, no_items_tv, ServiceIdentification.cameraList,view,homeID);
    }


}