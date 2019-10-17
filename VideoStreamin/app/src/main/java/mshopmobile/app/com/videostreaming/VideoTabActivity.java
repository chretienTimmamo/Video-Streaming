package mshopmobile.app.com.videostreaming;

/**
 * Created by root on 9/14/15.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import fragment.CameraAdapterList;
import fragment.RecordAdapterDetailList;
import fragment.ZoneAdapterList;
import util.GlobalService;
import util.ServiceIdentification;
import util.WebService.ActivityServerCommunication;
import util.WebService.AsyncCommunicationResponse;

//public class NotificationTabActivity extends AppCompatActivity implements AsyncCommunicationResponse {
public class VideoTabActivity extends Activity implements AsyncCommunicationResponse{

    ActionBar.Tab alertNotificationFragment,alarmNotificationFragment,otherNotificationFragment;
    /*
    Fragment alertWordCamFragmentTab = new WordCamFragmentTab();

    //Fragment alarmZoneFragmentTab = new MyZoneFragmentTab();

    Fragment otherRecordFragmentTab = new RecordFragmentTab();

    Fragment cameraListTab = new MyCamFragmentTab();
    */
    ActionBar actionBar;
    ActionBar.Tab tabNotification;
    ActivityServerCommunication serverCommunication=null;
    ListView list;
    int currentService;
    static TextView no_items_tv;
    Adapter adapter;
    ListView currrentList;
    private ActionBar.TabListener bar_files_tablistener = null;
    String url;
    View currentView;
    String urlCamera;
    Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //setContentView(R.layout.fragment_notification_tab);
        setContentView(R.layout.activity_main);

        // Asking for the default ActionBar element that our platform supports.
        actionBar = getActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setIcon(null);
        actionBar.setDisplayUseLogoEnabled(false);

        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.custom_actionbar);
        ActionBar.LayoutParams p = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //p.gravity = Gravity.CENTER;
        p.gravity = Gravity.TOP;
        configureVideoTab();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }



    public void configureVideoTab(){
        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Setting custom tab icons.
        tabNotification = actionBar.newTab();

        alertNotificationFragment = tabNotification;

        //tabNotification.setIcon(R.drawable.bmw_logo);
        tabNotification.setText(R.string.word);
        //alarmNotificationFragment = actionBar.newTab().setIcon(R.drawable.toyota_logo);
        alarmNotificationFragment = actionBar.newTab().setText(R.string.myCam);

        //otherNotificationFragment = actionBar.newTab().setIcon(R.drawable.ford_logo);
        otherNotificationFragment = actionBar.newTab().setText(R.string.record);
        // Setting tab listeners.

        //alertNotificationFragment.setTabListener(new TabListener(alertWordCamFragmentTab));

        //alarmNotificationFragment.setTabListener(new TabListener(alarmZoneFragmentTab));

        //otherNotificationFragment.setTabListener(new TabListener(otherRecordFragmentTab));

        // Adding tabs to the ActionBar.

        actionBar.addTab(alertNotificationFragment);

        actionBar.addTab(alarmNotificationFragment);

        actionBar.addTab(otherNotificationFragment);

       // actionBar.setBackgroundDrawable(new ColorDrawable();


        bar_files_tablistener  = new ActionBar.TabListener()
        {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
            {
                Log.i("TAG", "=onTabSelected4 tab=" + tab.getText()+"pos"+tab.getText());
                Toast.makeText(VideoTabActivity.this,
                        tab.getText()+"pos"+tab.getText(), Toast.LENGTH_LONG)
                        .show();

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft){}
            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft){}
        };
    }

    public void callListElements(Adapter adapter,ListView currentListView,TextView no_items_tv_view,int service,View view,Object parameter){
        
        this.currrentList = currentListView;
        this.adapter = adapter;
        this.no_items_tv =no_items_tv_view;
        this.currentService = service;
        this.currentView = view;
        
        //read service parameters
        Map<String, Object> userInfos = new HashMap<String, Object>();
        Log.i("service",currentService+"");
        switch (currentService) {
            case ServiceIdentification.zoneList:
                 url = GlobalService.getUrlService("zoneList",this);
                 userInfos.put("sessionID", GlobalService.getUserSession(this));
                 break;
            case ServiceIdentification.cameraList:
                 url = GlobalService.getUrlService("cameraList",this);
                 userInfos.put("sessionID", GlobalService.getUserSession(this));
                 break;
            case ServiceIdentification.recordList:

                displayRecordList();
                return;

            default:
                break;
        }
        serverCommunication = new ActivityServerCommunication(VideoTabActivity.this,userInfos,"GET", url,currentService);
        serverCommunication.delegate = VideoTabActivity.this;
        serverCommunication.execute();
    }

    /*********************routing service**************************/
    
    public void processFinish(int response,Object object,int serviceId){

        Log.i("serviceId", serviceId + "");
        if(ServiceIdentification.zoneList ==serviceId)
            processZoneListFinish(response,object,serviceId);
        else if(ServiceIdentification.cameraList ==serviceId)
            Toast.makeText(VideoTabActivity.this,
                    "in process", Toast.LENGTH_LONG)
                    .show();


    }
    
    /**********************end routing service********************/

    public void  processZoneListFinish(int response,Object object,int serviceId){
        //this you will received result fired from async class of onPostExecute(result) method.

        if(response ==-1){
            //an error occurs may be network issue


            Toast.makeText(VideoTabActivity.this,
                    R.string.communication_error, Toast.LENGTH_LONG)
                    .show();
        }
        else{


            if(response==0){


                //ok
                int zoneLength;
                JSONObject data =(JSONObject)object;
                try {

                    zoneLength = data.getJSONArray("result").length();

                } catch (JSONException e) {
                    zoneLength =0;
                }
                /*

                //check zone size
                try {

                    zoneLength = data.getJSONArray("result").length();

                } catch (JSONException e) {
                    zoneLength =0;
                }
                Log.i("length",zoneLength+"");
                */
                if(zoneLength == 0){
                    Toast.makeText(VideoTabActivity.this,
                            R.string.empty_list_home, Toast.LENGTH_LONG)
                            .show();

                }else if (zoneLength == 1){
                    //Log.i("notZOne","notZOne");
                    //go directly to dashboard with one element
                    //gotoCameraList(data,0,true);
                }else{
                    displayZoneList(data);
                }


            }
            else if(response ==300){
                //ok
                Toast.makeText(VideoTabActivity.this,
                        R.string.communication_error, Toast.LENGTH_LONG)
                        .show();

            }
            else if(response ==311){
                //ok
                Toast.makeText(VideoTabActivity.this,
                        R.string.require_login, Toast.LENGTH_LONG)
                        .show();

                gotoLogin();

            }

            else{
                //ok

                Toast.makeText(VideoTabActivity.this,
                        R.string.communication_error, Toast.LENGTH_LONG)
                        .show();



            }

        }

    }

    public void displayZoneList(JSONObject ZoneListJson){



        ((ZoneAdapterList)adapter).setZoneList(ZoneListJson);

        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //gotoCameraList(ZoneListToDisplay, position, false);
                //cameraListTab

            }
        });



    }


    /*
    public void gotoCameraList(JSONObject ZoneListToDisplay, int homePosition){
        try {

            homeID = (ZoneListToDisplay.getJSONArray("result").getJSONObject(homePosition).getString("homeID"));
            homeName = (ZoneListToDisplay.getJSONArray("result").getJSONObject(homePosition).getString("homeName"));
            Intent nextScreen = new Intent(getApplicationContext(),VideoTabActivity.class);
            nextScreen.putExtra("homeID", homeID);
            nextScreen.putExtra("homeName",homeName);
            startActivity(nextScreen);
            if(finishActivity) finish();
        } catch (JSONException e) {

        }

    }
    */

    public void processCameraListFinish(int response,Object object,int serviceId){
        //this you will received result fired from async class of onPostExecute(result) method.



        if(response ==-1){
            //an error occurs may be network issue


            Toast.makeText(VideoTabActivity.this,
                    R.string.communication_error, Toast.LENGTH_LONG)
                    .show();
        }
        else{


            if(response==0){
                //ok

                JSONObject data =(JSONObject)object;

                displayCameraList(data);

            }
            else if(response ==300){
                //ok
                Toast.makeText(VideoTabActivity.this,
                        R.string.communication_error, Toast.LENGTH_LONG)
                        .show();

            }
            else if(response ==311){
                //ok
                Toast.makeText(VideoTabActivity.this,
                        R.string.require_login, Toast.LENGTH_LONG)
                        .show();

                gotoLogin();

            }

            else{
                //ok

                JSONObject data =(JSONObject)object;

            }

        }

    }

    public void displayCameraList(JSONObject cameraListJson){

        final JSONObject cameraListToDisplay = cameraListJson;


        ((CameraAdapterList)adapter).setCameraList(cameraListJson);

        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                try {

                    urlCamera = ( cameraListToDisplay.getJSONArray("result").getJSONObject(position).getString("url"));
                    Intent nextScreen = new Intent(getApplicationContext(), StreamCameraActivity.class);
                    nextScreen.putExtra("cameraPosition","position");
                    nextScreen.putExtra("cameraUrl",urlCamera);
                    startActivity(nextScreen);

                } catch (JSONException e) {

                }

            }
        });



    }

    public void displayRecordList(){

        JSONArray jsonRecord = new JSONArray();

        try {
            jsonRecord.put(new JSONObject().put("record of 10-05-2015","rtsp://urlenligne"));
            jsonRecord.put(new JSONObject().put("record of 10-05-2015","rtsp://urlenligne"));
            ((RecordAdapterDetailList)adapter).setRecordList(jsonRecord);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void gotoLogin(){

        Intent nextScreen = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(nextScreen);
        finish();

    }
   /*
    protected void startCameraList(int position) {

        Intent intent = new Intent(getActivity(), SimpleImageActivity.class);
        intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImagePagerFragment.INDEX);
        intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
        startActivity(intent);

    }
    */

    @Override
    protected void onDestroy()
    {
        serverCommunication.cancel(true);
        super.onDestroy();

    }


}