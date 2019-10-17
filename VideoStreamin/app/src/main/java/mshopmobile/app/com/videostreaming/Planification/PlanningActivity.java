package mshopmobile.app.com.videostreaming.Planification;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mshopmobile.app.com.videostreaming.Planification.preferences.PlanningPeriodActivity;
import mshopmobile.app.com.videostreaming.R;
import util.GlobalService;
import util.ServiceIdentification;
import util.WebService.ActivityServerCommunication;
import util.WebService.AsyncCommunicationResponse;

public class PlanningActivity extends Activity implements AsyncCommunicationResponse {
    ListView mathAlarmListView;
    PlanningPeriodListAdapter alarmListAdapter;
    String copyUrl;
    public String homeInfos="";
    public String planning_type;
    int numberOfperiod;
    ActivityServerCommunication serverCommunication = null;
    ArrayList<Planning> periodArmListToDisplay ;
    ArrayList<Planning> periodDearmListToDisplay ;
    JSONObject json= null;
    int homeID;
    int cameraID;
    Intent i;
    Bundle bundle;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   // private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         bundle = getIntent().getExtras();
        setContentView(R.layout.alarm_activity);
        /*
         i = getIntent();
        homeInfos = i.getStringExtra("homeInfos");
        homeInfos ="2502";
        setContentView(R.layout.alarm_activity);

        if (bundle != null) {
            planning_type = bundle.getString(Planning.PLANNING_TYPE);
            Log.e("planning type", planning_type);
        }

        try {
            json = new JSONObject(homeInfos);
            homeID= Integer.parseInt(json.get("homeID").toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        cameraID = bundle.getInt("cameraID");
        retrievedAllPeriodArm();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.planification_menu, menu);
        menu.findItem(R.id.menu_item_save).setVisible(false);
        menu.findItem(R.id.menu_item_delete).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_new) {
            Intent newAlarmIntent = new Intent(this, PlanningPeriodActivity.class);
            //newAlarmIntent.putExtra(Planning.PLANNING_TYPE, planning_type);
            newAlarmIntent.putExtra("Edit","add");
            newAlarmIntent.putExtra("cameraID",cameraID);
            startActivity(newAlarmIntent);
        }
        if (id == R.id.menu_item_delete) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(PlanningActivity.this);
            dialog.setTitle("Delete");
            dialog.setMessage("Delete this Planning?");

            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    deletAllPeriodArm();
                    //callMathAlarmScheduleService();
                    //finish();
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
       super.onResume();

        /*
        if (bundle != null) {
            planning_type = bundle.getString(Planning.PLANNING_TYPE);
            Log.e("planning type", planning_type);
        }
        try {
            json = new JSONObject(homeInfos);
            homeID= Integer.parseInt(json.get("homeID").toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        cameraID = bundle.getInt("cameraID");
        //retrievedAllPeriodArm();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        retrievedAllPeriodArm();
    }

    public void retrievedAllPeriodArm() {
        String url = GlobalService.getUrl("allPeriod", PlanningActivity.this);
        copyUrl = url;
        Map<String, Object> userNotification = new HashMap<String, Object>();
        userNotification.put("sessionID", GlobalService.getUserSession(PlanningActivity.this));
        userNotification.put("cameraID",cameraID);
        serverCommunication = new ActivityServerCommunication(PlanningActivity.this, userNotification, "GET", url, ServiceIdentification.planningallPeriod);
        serverCommunication.delegate = PlanningActivity.this;
        serverCommunication.execute();
    }

    public void deletAllPeriodArm() {
        String url = GlobalService.getUrl("deleteAllPeriod", PlanningActivity.this);

        copyUrl = url;
        Map<String, Object> userNotification = new HashMap<String, Object>();
        userNotification.put("sessionID", GlobalService.getUserSession(PlanningActivity.this));
        userNotification.put("cameraID",cameraID);
        serverCommunication = new ActivityServerCommunication(PlanningActivity.this, userNotification , "GET", url, ServiceIdentification.planningDeleteAllPeriod);
        serverCommunication.delegate = PlanningActivity.this;
        serverCommunication.execute();
    }

    @Override
    public void processFinish(int output, Object object, int serviceId) {
        serverCommunication.cancel(true);
        if (ServiceIdentification.planningallPeriod == serviceId) {
            retreiveAllperiode(output, object, serviceId);

        }
        else if (ServiceIdentification.planningEditPeriod == serviceId)
            Log.e("pla", "blabla");
        else if (ServiceIdentification.planningDeletePeriod == serviceId){
            Log.e("pla", "blabla");

        }
        else if (ServiceIdentification.planningDeleteAllPeriod== serviceId){
            Log.e("pla", "blabla");
            deleteAllPeriod(output, object, serviceId);
        }

    }

    //appel du services qui listes les planifications  affichage des plannnifications
    public void retreiveAllperiode(int response, Object object, int serviceId) {
        if (response == -1) {
            Log.e("all period", "" + object);
            Toast.makeText(getApplicationContext(),
                    R.string.communication_error, Toast.LENGTH_LONG)
                    .show();
        } else if (response == 0) {
            JSONObject data =(JSONObject)object;
            Log.e("contenu du JSON", data.toString());
            displayPeriod(data.toString());

        }

    }
    public void deleteAllPeriod(int response, Object object, int serviceId) {
        if (response == -1) {
            Log.e("all period", "" + object);
            Toast.makeText(getApplicationContext(),
                    R.string.communication_error, Toast.LENGTH_LONG)
                    .show();
        } else if (response == 0) {
            //JSONObject data =(JSONObject)object;
            //Log.e("contenu du JSON", data.toString());

            //displayPeriod(data.toString());
            Toast.makeText(getApplicationContext(),
                    R.string.deleteAllPeriod, Toast.LENGTH_LONG)
                    .show();
            finish();
        }


    }


    public void displayPeriod(String data) {

        JSONObject dataInformations = null;

        try {
            dataInformations = new JSONObject(data);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        if (dataInformations != null) {
            String sessionID = "";

            try {
                sessionID = dataInformations.get("sessionID").toString();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            //Log.e("HomeListActivity 'dataInformations'.sessionID",sessionID);

            JSONArray listHome = null;
            try {
                listHome = dataInformations.getJSONArray("list");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (listHome.length() == 0){

                Toast.makeText(PlanningActivity.this,
                        R.string.empty_list_home, Toast.LENGTH_LONG)
                        .show();


            }

            numberOfperiod = listHome.length();
            periodArmListToDisplay = new ArrayList<Planning>();
            periodDearmListToDisplay = new ArrayList<Planning>();
            TextView no_plannig_period = (TextView)findViewById(android.R.id.empty);

           // http://localhost:8080/streaming.Service/resources/imageService/createPeriod?sessionID=User1459510626901&cameraID=153&day=[1,2,3,4,5,6,7]&recordingFrom=06:50:00&recordingTo=06:59:00&streamCodec=MPEG&streamFormat=mp4
        for (int i = 0; i < numberOfperiod; i++){
            Planning planning = new Planning();
            try {
                planning.setId(listHome.optJSONObject(i).getInt("periodStreamID"));
                planning.setRecordingFrom(listHome.optJSONObject(i).getString("recordingFrom"));
                planning.setRecordingTo(listHome.optJSONObject(i).getString("recordingTo"));
                planning.setStreamFormat(listHome.optJSONObject(i).getString("streamFormat"));
                planning.setStreamCodec(listHome.optJSONObject(i).getString("streamCodec"));
                planning.setDaycode(listHome.optJSONObject(i).getString("dayPeriod"));
                planning.setStatus(listHome.optJSONObject(i).getBoolean("activated"));
                //Log.e("planning data", "" + planning.getId());
                periodArmListToDisplay.add(planning);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

               mathAlarmListView = (ListView) findViewById(R.id.listperiod);
               alarmListAdapter = new PlanningPeriodListAdapter(this,periodArmListToDisplay);

               if (periodArmListToDisplay.size()>0){
                   no_plannig_period.setVisibility(View.GONE);
               }
               mathAlarmListView.setAdapter(alarmListAdapter);


               mathAlarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Planning planning = periodArmListToDisplay.get(position);
                       Intent newAlarmIntent = new Intent(PlanningActivity.this, PlanningPeriodActivity.class);
                       //newAlarmIntent.putExtra(Planning.PLANNING_TYPE, planning_type);
                        newAlarmIntent.putExtra("alarm",planning);
                       newAlarmIntent.putExtra("cameraID",cameraID);
                       newAlarmIntent.putExtra("Edit", "edit");
                       startActivity(newAlarmIntent);

                   }
               });

        }
    }


}
