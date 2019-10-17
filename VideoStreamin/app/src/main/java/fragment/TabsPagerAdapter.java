package fragment;

/**
 * Created by root on 13/04/16.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //return PageFragment.newInstance(position + 1);

        if(position ==0)  return new ManagerZoneCameraFragment();
            else if(position ==1)  return new ManagerRecordFragment();
                else if(position ==2) return  new ManagerPlanningFragment();
                    else {
                        return new ManagerZoneCameraFragment();
                    }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        //return "TAB " + (position + 1);
        /*if(position ==0 )return "My Cam" ;
        else if (position ==1 ) return  "Records" ;
        else if (position ==2 ) return "Planning" ;
        else return "My Cam";
        */

        if(position ==0 )return "Camera Zones" ;
        else if (position ==1 ) return  "Records Video" ;
        else if (position ==2 ) return "Planning" ;
        else return "Camera Zones";


        //else if(position ==1 )return "My Cam" ;
        //else return  "World Cam";

    }
}