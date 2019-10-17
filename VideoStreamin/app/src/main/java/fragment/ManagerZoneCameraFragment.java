package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mshopmobile.app.com.videostreaming.R;

/**
 * Created by root on 13/04/16.
 */
public class ManagerZoneCameraFragment extends android.support.v4.app.Fragment {
    // create boolean for fetching data
    private boolean isViewShown = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //View rootView = inflater.inflate(R.layout.fragment_my_zone_list_tab, container, false);
    View rootView = inflater.inflate(R.layout.framezonecamera, container, false);
    //rootView.setBackgroundColor(getResources().getColor(android.R.color.white));
    /*
    FragmentTransaction transaction = getFragmentManager().beginTransaction();

    transaction.replace(R.id.root_frame, new MyZoneFragmentTab());
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    transaction.commit();
    */


    if(!isViewShown){

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.root_frame, new MyZoneFragmentTab());
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
            isViewShown =true;
    }
    return rootView;

    }
    // Called when the Fragment is attached to its parent Activity.
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Get a reference to the parent Activity.
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.root_frame, new MyZoneFragmentTab());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
    */
    //onStart()
/*
    @Override
    public void onStart() {

        // Get a reference to the parent Activity.
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.root_frame, new MyZoneFragmentTab());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Do your Work

            if(isViewShown){
                // Get a reference to the parent Activity.
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.root_frame, new MyZoneFragmentTab());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }


        } else {
            // Do your Work
        }
    }


}
