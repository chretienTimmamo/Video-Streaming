package mshopmobile.app.com.videostreaming;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fragment.RecordFragmentTab;

/**
 * Created by root on 02/05/16.
 */
public class ManagerPlanningRecordFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_my_zone_list_tab, container, false);
        View rootView = inflater.inflate(R.layout.frameplanningrecord, container, false);
        //rootView.setBackgroundColor(getResources().getColor(android.R.color.white));

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.root_planning_record,new RecordFragmentTab());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();

        return rootView;

    }
}
