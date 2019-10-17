package mshopmobile.app.com.videostreaming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import fragment.RecordAdapterDetailList;

/**
 * Created by root on 05/05/16.
 */


public class RecordDetailsActivity extends Activity {
    String cameraRecordString;
    JSONArray cameraRecords;
    ListView list;
    RecordAdapterDetailList adapter;
    String day;
    String baseUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_record_list_detail_tab);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cameraRecordString = extras.getString("cameraRecords");
            day = extras.getString("day");
            baseUrl = extras.getString("baseUrl");
            Log.i("cameraRecords",cameraRecordString);
            // and get whatever type user account id is
            try {
                cameraRecords = new JSONArray(cameraRecordString);
                list=(ListView)findViewById(R.id.listRecordDetail);
                //Log.e("HomeListActivity","Avant d'instancier adapter");
                // Getting adapter by passing xml data ArrayList
                adapter=new RecordAdapterDetailList(this,cameraRecords);

                list.setAdapter(adapter);
                // Click event for single list row
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        //MapJson mapJson= new MapJson();
                        //Log.e("HubListActivity event","position="+position);
                        Intent nextScreen = new Intent(getApplicationContext(), StreamRecordActivity.class);

                        try {
                            nextScreen.putExtra("recordPath",baseUrl+""+cameraRecords.getJSONObject(position).getString("streamName"));
                            startActivity(nextScreen);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }








}
