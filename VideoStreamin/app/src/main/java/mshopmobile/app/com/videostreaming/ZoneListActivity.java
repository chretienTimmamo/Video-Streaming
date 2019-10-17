package mshopmobile.app.com.videostreaming;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fragment.ZoneAdapterList;
import util.WebService.ActivityServerCommunication;
import util.WebService.AsyncCommunicationResponse;
import util.GlobalService;
import util.MapJson;
import util.ServiceIdentification;
import util.UrlAddress;
/**
 * Created by root on 07/04/16.
 */
public class ZoneListActivity extends Activity implements AsyncCommunicationResponse{
    ListView list;
    JSONObject serverAddress;
    ZoneAdapterList adapter;
    public UrlAddress urlAddress;
    private String url;
    private String homeID ;
    private String homeName;
    TextView no_items_tv;
    JSONObject ZoneListToDisplay=null;
    ActivityServerCommunication serverCommunication=null;
    Map<String, Object> ZoneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zone_list);
        sendZoneListRequest();
    }

    public void sendZoneListRequest(){

        // end reading configuration informations
        url = GlobalService.getUrlService("zoneList",this);

        ZoneList = new HashMap<String, Object>();
        ZoneList.put("sessionID",GlobalService.getUserSession(this));
        //ZoneList.put("homeID","homeID");
        //userLogin.put("registrationId",CommonUtilities.registration_ID);
        list=(ListView)findViewById(R.id.listZone);

        serverCommunication = new ActivityServerCommunication(ZoneListActivity.this,ZoneList, "GET", url, ServiceIdentification.zoneList);
        serverCommunication.delegate = ZoneListActivity.this;
        serverCommunication.execute();

    }
    public void processFinish(int response,Object object,int serviceId){
        //this you will received result fired from async class of onPostExecute(result) method.



        if(response ==-1){
            //an error occurs may be network issue


            Toast.makeText(ZoneListActivity.this,
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
                    Toast.makeText(ZoneListActivity.this,
                            R.string.empty_list_home, Toast.LENGTH_LONG)
                            .show();

                }else if (zoneLength == 1){
                    //Log.i("notZOne","notZOne");
                    //go directly to dashboard with one element
                    gotoCameraList(data,0,true);
                    //=================== Comment gotoCameraList
                    //gotoCameraListMultiView(data, 0, true);
                   // gotoOnVifCamera();

                }else{
                    displayZoneList(data);
                }


            }
            else if(response ==300){
                //ok
                Toast.makeText(ZoneListActivity.this,
                        R.string.communication_error, Toast.LENGTH_LONG)
                        .show();

            }
            else if(response ==311){
                //ok
                Toast.makeText(ZoneListActivity.this,
                        R.string.require_login, Toast.LENGTH_LONG)
                        .show();

                gotoLogin();

            }

            else{
                //ok

                Toast.makeText(ZoneListActivity.this,
                        R.string.communication_error, Toast.LENGTH_LONG)
                        .show();



            }

        }

    }

    public void displayZoneList(JSONObject ZoneListJson){

        ZoneListToDisplay = ZoneListJson;

        //Log.e("HomeListActivity","Avant d'instancier adapter");
        // Getting adapter by passing xml data ArrayList
        adapter=new ZoneAdapterList(this,ZoneListToDisplay);

        //Log.e("HomeListActivity","Apres instancier adapter");
        list.setAdapter(adapter);
        //list.notify();
        no_items_tv = (TextView) findViewById(R.id.empty_list_home);
        list.setEmptyView(no_items_tv);

        // Click event for single list row
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                    //=========== comment the go to gotocameraListZone
                    gotoCameraList(ZoneListToDisplay,position,false);
               // gotoCameraListMultiView(ZoneListToDisplay, 0, true);
               // gotoOnVifCamera();


            }
        });



    }

    public void gotoCameraList(JSONObject ZoneListToDisplay, int homePosition,boolean finishActivity){
        try {

            homeID = (ZoneListToDisplay.getJSONArray("result").getJSONObject(homePosition).getString("homeID"));
            homeName = (ZoneListToDisplay.getJSONArray("result").getJSONObject(homePosition).getString("homeName"));
            Intent nextScreen = new Intent(getApplicationContext(),CameraListActivity.class);
            nextScreen.putExtra("homeID", homeID);
            nextScreen.putExtra("homeName",homeName);
            startActivity(nextScreen);
            if(finishActivity) finish();
        } catch (JSONException e) {

        }

    }



    public void gotoLogin(){

        Intent nextScreen = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(nextScreen);
        finish();

    }
}
