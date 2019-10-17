package mshopmobile.app.com.videostreaming;

/**
 * Created by root on 13/04/16.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import SimpleClass.Constants;
import fragment.CameraAdapterList;
import fragment.ImageGridFragment;
import fragment.RecordAdapterDetailList;
import fragment.RecordAdapterList;
import fragment.TabsPagerAdapter;
import fragment.ZoneAdapterList;
import mshopmobile.app.com.videostreaming.Planification.PlanningActivity;
import util.ExternalFile;
import util.GlobalService;
import util.ServiceIdentification;
import util.WebService.ActivityServerCommunication;
import util.WebService.AsyncCommunicationResponse;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class VideoTabManagerActivity extends AppCompatActivity implements AsyncCommunicationResponse {
    ActivityServerCommunication serverCommunication=null;
    ListView list;
    int currentService;
    static TextView no_items_tv;
    Adapter adapter;
    ListView currrentList;
    String url;
    View currentView;
    String urlCamera;
    String recordList;
    String day;
    String baseUrl;
    static JSONObject ZoneListToDisplay;
    static JSONObject CameraListToDisplay;
    static JSONArray RecordListToDisplay;
    String homeID;
    String homeName;
    Toolbar actionBarToobar;
    JSONArray jsonRecord;
    JSONObject data;
    Menu menu;
    int tabPosition = 0;
    TextView checkConnexion;
    String cameraFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_tab_strip);
        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        //pager.setOffscreenPageLimit(1);
        pager.setAdapter(adapter);

        //2centrales couloirs de la chambre
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int index) {
                // TODO Auto-generated method stub
                Log.e("indexPosition",""+index);
                tabPosition = index;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        //actionBarToobar = (Toolbar) findViewById(R.id.toolbar);
        //actionBarToobar.setLogo(R.id.camera);
        //actionBarToobar.setLogoDescription(R.id.camera);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.dashboardlogout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void logout(){
        Map<String, Object> logoutLRequest = new HashMap<String, Object>();
        logoutLRequest.put("sessionID", GlobalService.getUserSession(VideoTabManagerActivity.this));
        String url = GlobalService.getUrlService("logout",this);

        serverCommunication = new ActivityServerCommunication(VideoTabManagerActivity.this,logoutLRequest, "GET", url, ServiceIdentification.logout);
        serverCommunication.delegate = VideoTabManagerActivity.this;
        serverCommunication.execute();

    }

    public void callListElements(Adapter adapter,ListView currentListView,TextView no_items_tv_view,int service,View view,Object parameter){

        this.currrentList = currentListView;
        this.adapter = adapter;
        this.no_items_tv =no_items_tv_view;
        this.currentService = service;
        this.currentView = view;
        //display error button
        checkConnexion = (TextView) this.currentView.findViewById(R.id.check_connexion);

        //read service parameters
        Map<String, Object> userInfos = new HashMap<String, Object>();
        Log.i("service", currentService + "");
        switch (currentService) {
            case ServiceIdentification.zoneList:
                url = GlobalService.getUrlService("zoneList", this);
                userInfos.put("sessionID", GlobalService.getUserSession(this));
                break;
            case ServiceIdentification.cameraList:
                url = GlobalService.getUrlService("cameraList",this);
                userInfos.put("sessionID", GlobalService.getUserSession(this));
                userInfos.put("homeID",(String)parameter);
                break;
            case ServiceIdentification.recordList:

                //displayRecordList();
                url = GlobalService.getUrlService("recordList",this);
                userInfos.put("sessionID", GlobalService.getUserSession(this));
                break;


            default:
                break;
        }
        serverCommunication = new ActivityServerCommunication(VideoTabManagerActivity.this,userInfos,"GET", url,currentService);
        serverCommunication.delegate = VideoTabManagerActivity.this;
        serverCommunication.execute();
    }

    /*********************routing service**************************/

    public void processFinish(int response,Object object,int serviceId){

        Log.i("serviceId", serviceId + "");
        if(ServiceIdentification.zoneList ==serviceId)
            processZoneListFinish(response,object,serviceId);
        else if(ServiceIdentification.cameraList ==serviceId)
            processCameraListFinish(response,object,serviceId);
        else if(ServiceIdentification.recordList ==serviceId)
            processRecordListFinish(response, object, serviceId);
        else if(serviceId==ServiceIdentification.logout){

            logoutNotificationListFinish(response,object);
        }

    }

    /**********************end routing service********************/

    public void  processZoneListFinish(int response,Object object,int serviceId){
        //this you will received result fired from async class of onPostExecute(result) method.

        if(response ==-1){
            //an error occurs may be network issue


            Toast.makeText(VideoTabManagerActivity.this,
                    R.string.communication_error, Toast.LENGTH_LONG)
                    .show();
            //this.currrentList.setVisibility(View.GONE);
            //checkConnexion.setVisibility(View.VISIBLE);

        }
        else{


            if(response==0){


                //ok
                int zoneLength;
                JSONObject data =(JSONObject)object;
                try {

                    zoneLength = data.getJSONArray("result").length();
                    Log.i("length",zoneLength+"");

                } catch (JSONException e) {
                    zoneLength =0;
                }

                if(zoneLength == 0){
                    Toast.makeText(VideoTabManagerActivity.this,
                            R.string.empty_list_home, Toast.LENGTH_LONG)
                            .show();
                    //this.currrentList.setVisibility(View.GONE);

                }else if (zoneLength == 1){
                    //Log.i("notZOne","notZOne");
                    //go directly to dashboard with one element
                    //gotoCameraList(data,0,true);
                    //this.currrentList.setVisibility(View.VISIBLE);
                    //checkConnexion.setVisibility(View.GONE);
                    displayZoneList(data);
                }else{
                    //this.currrentList.setVisibility(View.VISIBLE);
                    //checkConnexion.setVisibility(View.GONE);
                    displayZoneList(data);
                }


            }
            else if(response ==300){
                //ok
                Toast.makeText(VideoTabManagerActivity.this,
                        R.string.communication_error, Toast.LENGTH_LONG)
                        .show();

            }
            else if(response ==311){
                //ok
                Toast.makeText(VideoTabManagerActivity.this,
                        R.string.require_login, Toast.LENGTH_LONG)
                        .show();

                gotoLogin();

            }

            else{
                //ok

                Toast.makeText(VideoTabManagerActivity.this,
                        R.string.communication_error, Toast.LENGTH_LONG)
                        .show();



            }

        }

    }

    public void displayZoneList(JSONObject ZoneListJson){

        ZoneListToDisplay = ZoneListJson;
        ZoneAdapterList adapterZoneList= ((ZoneAdapterList)adapter);

        //ZoneAdapterList adapterZoneList=new ZoneAdapterList(this,ZoneListToDisplay);
        adapterZoneList.setZoneList(ZoneListJson);
        list.setAdapter(adapterZoneList);
        //adapterZoneList.notifyDataSetChanged();

        // Click event for single list row
        /*
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                gotoCameraList(ZoneListToDisplay,position, false);
                //cameraListTab

            }
        });
        */



    }

    /*
    public void gotoCameraList(JSONObject ZoneListToDisplay, int homePosition){
        try {

            homeID = (ZoneListToDisplay.getJSONArray("result").getJSONObject(homePosition).getString("homeID"));
            homeName = (ZoneListToDisplay.getJSONArray("result").getJSONObject(homePosition).getString("homeName"));
            Intent nextScreen = new Intent(getApplicationContext(),PagerTabStripActivity.class);
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


            Toast.makeText(VideoTabManagerActivity.this,
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
                Toast.makeText(VideoTabManagerActivity.this,
                        R.string.communication_error, Toast.LENGTH_LONG)
                        .show();

            }
            else if(response ==311){
                //ok
                Toast.makeText(VideoTabManagerActivity.this,
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

        CameraListToDisplay = cameraListJson;


        ((CameraAdapterList)adapter).setCameraList(cameraListJson);
        ((CameraAdapterList)adapter).notifyDataSetChanged();

        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    try {

                        //perform video streaming
                        urlCamera = (CameraListToDisplay.getJSONArray("result").getJSONObject(position).getString("url"));
                        Intent nextScreen = new Intent(getApplicationContext(), StreamCameraActivity.class);
                        nextScreen.putExtra("cameraPosition", "position");
                        nextScreen.putExtra("cameraUrl", urlCamera);
                        startActivity(nextScreen);

                    } catch (JSONException e) {

                    }

            }
        });



    }
    public void launchVideo(int position){

        if(tabPosition == 0) {
            try {

                urlCamera = (CameraListToDisplay.getJSONArray("result").getJSONObject(position).getString("url"));
                int cameraID =(CameraListToDisplay.getJSONArray("result").getJSONObject(position).getInt("cameraID"));
                cameraFeed = (CameraListToDisplay.getJSONArray("result").getJSONObject(position).getString("cameraFeed"));

                Intent nextScreen = new Intent(getApplicationContext(),StreamCameraActivity.class);
                nextScreen.putExtra("cameraPosition", "position");
                nextScreen.putExtra("cameraUrl", urlCamera);
                nextScreen.putExtra("cameraID",cameraID);

                System.out.println("============= CameraFeed ======"+ cameraFeed);
                System.out.println("============= cameraID ======"+ cameraID);
                System.out.println("============= urlCamera ======"+ urlCamera);

                Log.i("cameraFeed", cameraFeed + "");
                Log.i("cameraID", cameraID + "");
                Log.i("urlCamera", urlCamera + "");

                startActivity(nextScreen);

            } catch (JSONException e) {

            }
        }
        else if(tabPosition == 2){
            //perform camera planning recording


            try {

                int cameraID = (CameraListToDisplay.getJSONArray("result").getJSONObject(position).getInt("cameraID"));
                planningList(cameraID);

            } catch (JSONException e) {

            }

        }

    }

    public void processRecordListFinish(int response,Object object,int serviceId){
        //this you will received result fired from async class of onPostExecute(result) method.



        if(response ==-1){
            //an error occurs may be network issue


            Toast.makeText(VideoTabManagerActivity.this,
                    R.string.communication_error, Toast.LENGTH_LONG)
                    .show();
        }
        else{


            if(response==0){
                //ok

                data =(JSONObject)object;

                displayRecordList(data);

            }
            else if(response ==300){
                //ok
                Toast.makeText(VideoTabManagerActivity.this,
                        R.string.communication_error, Toast.LENGTH_LONG)
                        .show();

            }
            else if(response ==311){
                //ok
                Toast.makeText(VideoTabManagerActivity.this,
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
    public void displayRecordList(JSONObject data){


        try {

            jsonRecord = data.getJSONArray("days");
            //Log.i("length>>>>>>", "" + jsonRecord.length());

            ((RecordAdapterList)adapter).setRecordList(jsonRecord);
            ((RecordAdapterList)adapter).notifyDataSetChanged();

            ImageView button = (ImageView) findViewById(R.id.pictureList);

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(VideoTabManagerActivity.this, SimpleImageActivity.class);
                    intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageGridFragment.INDEX);
                    startActivity(intent);

                }

            });
            /*
            RecordListToDisplay = new JSONArray();
            RecordAdapterList recordAdapter;
            list=(ListView)currentView.findViewById(R.id.listRecord);
            recordAdapter=new RecordAdapterList(this,jsonRecord);
            list.setAdapter(recordAdapter);
            (recordAdapter).notifyDataSetChanged();
            */
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    /*
                    try {

                        recordList = (jsonRecord.getJSONObject(position).getJSONArray("records")).toString();
                        day = (jsonRecord.getJSONObject(position).getJSONArray("day")).toString();
                        baseUrl = (jsonRecord.getJSONObject(position).getJSONArray("baseUrl")).toString();
                        Intent nextScreen = new Intent(getApplicationContext(), RecordDetailsActivity.class);
                        nextScreen.putExtra("recordList",recordList);
                        //nextScreen.putExtra("cameraUrl", urlCamera);
                        startActivity(nextScreen);
                        //finish();
                    } catch (JSONException e) {

                    }
                    */


                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecordListToDisplay = jsonRecord;

    }
    public void viewRecordDetail(int position){

        try {

            Log.i("clickONComing",""+position);
            recordList = (jsonRecord.getJSONObject(position).getJSONArray("records")).toString();
            day = (jsonRecord.getJSONObject(position).getString("day"));
            baseUrl = (data.getString("baseUrl"));
            Intent nextScreen = new Intent(getApplicationContext(), RecordDetailsActivity.class);
            nextScreen.putExtra("cameraRecords",recordList);
            nextScreen.putExtra("baseUrl",baseUrl);
            nextScreen.putExtra("day",day);
            //nextScreen.putExtra("cameraUrl", urlCamera);
            startActivity(nextScreen);
            //finish();
        } catch (JSONException e) {
            Log.i("ev", e.toString());

        }

    }
    public void launchRecordVideo(int position){
        try {

            urlCamera = (RecordListToDisplay.getJSONObject(position).getString("url"));
            Intent nextScreen = new Intent(getApplicationContext(), StreamCameraActivity.class);
            nextScreen.putExtra("cameraUrl", urlCamera);
            startActivity(nextScreen);

        } catch (JSONException e) {

        }

    }
    public void gotoLogin(){

        Intent nextScreen = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(nextScreen);
        finish();

    }
    public JSONObject getZoneListToDisplay(){
        return ZoneListToDisplay;
    }
    public JSONObject getCameraListToDisplay(){
        return CameraListToDisplay;
    }
   /*
    protected void startCameraList(int position) {

        Intent intent = new Intent(getActivity(), SimpleImageActivity.class);
        intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImagePagerFragment.INDEX);
        intent.putExtra(Constants.Extra.IMAGE_POSITION, position);
        startActivity(intent);

    }
    */
    /*

    lauch planning list

     */
   public void planningList(int cameraID){

           Intent nextScreen = new Intent(getApplicationContext(),PlanningActivity.class);
           nextScreen.putExtra("cameraID",cameraID);
           startActivity(nextScreen);

   }

    public void logoutNotificationListFinish(int response, Object object) {


        if(response ==-1){
            //an error occurs may be network issue
            Toast.makeText(VideoTabManagerActivity.this,
                    R.string.communication_error, Toast.LENGTH_LONG)
                    .show();

        }
        else{


            if(response==0){

			 	/*
			 	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 	*/

                //we delete all informations regarding, login, passwords and sessionID from the apps
                deleteLoginUsager();

                //start login activity
                Intent loginScreen = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginScreen);

                Toast.makeText(VideoTabManagerActivity.this,
                        R.string.logoutSuccess, Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            else if(response ==300){
                //ok
                Toast.makeText(VideoTabManagerActivity.this,
                        R.string.notConnected, Toast.LENGTH_LONG)
                        .show();

                backToLogin();

            }
            else if(response ==311){
                //ok

                Toast.makeText(VideoTabManagerActivity.this,
                        R.string.notConnected, Toast.LENGTH_LONG)
                        .show();

                backToLogin();
            }
            else if(response ==3000){
                //ok
				/*
				Toast.makeText(HomeListActivity.this,
					       "internal Error!", Toast.LENGTH_LONG)
				 .show();
				*/
            }
            else{
                //ok
                Toast.makeText(VideoTabManagerActivity.this,
                        R.string.unexpectedError, Toast.LENGTH_LONG)
                        .show();

            }

        }


    }

    /*
    @Override
    public void onBackPressed() {
        Log.e("BackPressed", "Login back pressed");
        Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
        nextScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(nextScreen);
        finish();

    }
    */

    public void backToLogin(){
        Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
        nextScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(nextScreen);
        finish();

    }

    public void deleteLoginUsager(){
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(ExternalFile.userLogin, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.clear();
        editor.commit();

    }
    @Override
    protected void onDestroy()
    {
        serverCommunication.cancel(true);
        super.onDestroy();

    }



}
