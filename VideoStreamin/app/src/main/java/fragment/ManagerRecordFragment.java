package fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.Map;

import mshopmobile.app.com.videostreaming.R;
import util.WebService.ActivityServerCommunication;

/**
 * Created by root on 13/04/16.
 */
public class ManagerRecordFragment extends android.support.v4.app.Fragment {
    View view;
    ListView list;
    RecordAdapterDetailList adapter;
    TextView no_items_tv;
    JSONArray recordListToDisplay;
    ActivityServerCommunication serverCommunication=null;
    Map<String, Object> CameraList;
    // create boolean for fetching data
    private boolean isViewShown = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //View rootView = inflater.inflate(R.layout.fragment_my_zone_list_tab, container, false);
    view = inflater.inflate(R.layout.framerecordview, container, false);
    //rootView.setBackgroundColor(getResources().getColor(android.R.color.white));
    /*
    if(!isViewShown){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_record, new RecordFragmentTab());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        isViewShown =true;
    }
    */
    //performRecordList();
    return view;

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your Work
            // Get a reference to the parent Activity.
            if(isViewShown){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.root_record, new RecordFragmentTab());
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
            }
        } else {
            // Do your Work
        }
    }

}
