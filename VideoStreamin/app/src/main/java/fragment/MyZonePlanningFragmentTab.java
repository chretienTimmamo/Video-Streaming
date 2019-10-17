package fragment;

/**
 * Created by root on 9/14/15.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import io.vov.vitamio.utils.Log;
import mshopmobile.app.com.videostreaming.R;
import mshopmobile.app.com.videostreaming.VideoTabManagerActivity;
import util.ServiceIdentification;

public class MyZonePlanningFragmentTab extends android.support.v4.app.Fragment {
    View view;
    ListView list;
    ZoneAdapterList adapter;
    TextView no_items_tv;
    JSONObject ZoneListToDisplay;
    MyCamFragmentTab  myCamFragmentTab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

       Bundle savedInstanceState) {

        //View rootView = inflater.inflate(R.layout.fragment_my_zone_list_tab, container, false);
        View rootView = inflater.inflate(R.layout.zone_list_planning, container, false);
        //rootView.setBackgroundColor(getResources().getColor(android.R.color.white));
        view = rootView;
        performZoneList();
        return rootView;

    }

    //perform the record list
    public void performZoneList(){

        ZoneListToDisplay = new JSONObject();
        list=(ListView)view.findViewById(R.id.listZonePlanning);
        adapter=new ZoneAdapterList(getActivity(),ZoneListToDisplay);

        //no_items_tv = (TextView) view.findViewById(R.id.empty_list_notification_Alarm);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                gotoCameraList(position);

            }
        });

        ((VideoTabManagerActivity)getActivity()).callListElements(adapter, list, no_items_tv, ServiceIdentification.zoneList, view, null);
    }

    public void gotoCameraList(int homePosition){
        String  homeID;
        String homeName;

        JSONObject ZoneListToDisplay = ((VideoTabManagerActivity)getActivity()).getZoneListToDisplay();

        try {

            homeID = (ZoneListToDisplay.getJSONArray("result").getJSONObject(homePosition).getString("homeID"));
            homeName = (ZoneListToDisplay.getJSONArray("result").getJSONObject(homePosition).getString("homeName"));
            Log.i("homeName", homeName);
            myCamFragmentTab = new MyCamFragmentTab();
            //Fragment fragment = new Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("homeID", homeID);
            bundle.putString("homeName", homeName);

            myCamFragmentTab.setArguments(bundle);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(null);
            transaction.replace(R.id.root_planning_record, myCamFragmentTab);
            transaction.commit();

        } catch (JSONException e) {

        }

    }
    //@Override
    public void OnAttach(Activity activity)
    {
        ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
        getActivity().getActionBar().setBackgroundDrawable(colorDrawable);
    }

}
