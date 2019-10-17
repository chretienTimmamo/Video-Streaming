package fragment;

/**
 * Created by root on 9/14/15.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import mshopmobile.app.com.videostreaming.R;
import mshopmobile.app.com.videostreaming.VideoTabManagerActivity;
import util.ServiceIdentification;

public class PlanningRecordFragmentTab extends android.support.v4.app.Fragment {
    View view;
    ListView list;
    RecordAdapterDetailList adapter;
    TextView no_items_tv;
    JSONArray RecordListToDisplay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_camera_list_tab, container, false);

        view = rootView;

        performPlanningRecordList();

        return rootView;

    }

    //perform the record list
    public void performPlanningRecordList(){

        /*
        RecordListToDisplay = new JSONArray();
        list=(ListView)view.findViewById(R.id.listCamera);
        adapter=new RecordAdapterDetailList(getActivity(),RecordListToDisplay);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                ((VideoTabManagerActivity) getActivity()).launchRecordVideo(position);

            }
        });
        ((VideoTabManagerActivity)getActivity()).callListElements(adapter,list,no_items_tv,ServiceIdentification.planningRecord,view,null);

        */
        //((VideoTabManagerActivity) getActivity()).planningList();
    }

}

