package fragment;

/**
 * Created by root on 9/14/15.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import mshopmobile.app.com.videostreaming.R;

public class WordCamFragmentTab extends android.support.v4.app.Fragment{

    View view;
    ListView list;
    //LazyAdapter adapter;
    static TextView no_items_tv;
    //ArrayList<NotificationInformation> NotificationListToDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_word_camera_list_tab, container, false);
        view = rootView;
        //performAlertList();
        return rootView;

    }

    //perform the alert list
    public void performAlertList(){
        /*
        NotificationListToDisplay = new ArrayList<NotificationInformation>();

        //list=(ListView)findViewById(R.id.listNotification);
        list=(ListView) view.findViewById(R.id.listNotificationAlert);

        // Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(getActivity(),NotificationListToDisplay);
        no_items_tv = (TextView) view.findViewById(R.id.empty_list_notification_Alert);
        list.setAdapter(adapter);
        ((NotificationTabActivity)getActivity()).callNotification(adapter, list, no_items_tv, ExternalFile.notification[0],view);
        */
    }

}
