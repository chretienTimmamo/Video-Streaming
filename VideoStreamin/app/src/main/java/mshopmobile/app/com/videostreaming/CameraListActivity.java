/*******************************************************************************
 * Copyright 2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package mshopmobile.app.com.videostreaming;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fragment.CameraAdapterList;

import util.WebService.ActivityServerCommunication;
import util.WebService.AsyncCommunicationResponse;
import util.GlobalService;
import util.MapJson;
import util.ServiceIdentification;
import util.UrlAddress;


/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class CameraListActivity extends Activity  implements AsyncCommunicationResponse {
	ListView list;
	CameraAdapterList adapter;
	private String url ;
	private String homeID ;
	private String urlCamera ;
	private int cameraID;
	private String position;
	private int cameraLength;

	//========= Parameter to parse cameraFree
	private String cameraFeed;

	JSONObject cameraListToDisplay=null;
	ActivityServerCommunication serverCommunication=null;
	Map<String, Object> cameraList;
	TextView no_items_tv;

	Bundle extras;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_list);
		/*
		if (!LibsChecker.checkVitamioLibs(this))
			return;

		cameraListToDisplay =new ArrayList<CameraInformation>();

		JSONObject serverAddress = GlobalService.getServerAddress(SimpleImageActivity.this,"serverAddress");
		try {
			urlLogin = serverAddress.getString("serverIpCamera")+":"+serverAddress.getString("portCamera");
		} catch (Exception ex) {
			//urlLogin = "192.168.0.19";

		}
		Toast.makeText(SimpleImageActivity.this,
				"rtsp://" + urlLogin + "/cam1.mp4", Toast.LENGTH_LONG)
				.show();
		cameraListToDisplay.add(new CameraInformation("rtsp://" + urlLogin + "/cam1.mp4", "Camera du Salom", "Camera1"));
		cameraListToDisplay.add(new CameraInformation("rtsp://"+urlLogin+"/cam1.mp4","Camera du Couloir","Camera2"));
		cameraListToDisplay.add(new CameraInformation("rtsp://"+urlLogin+"/cam1.mp4","Camera de la Cuisine","Camera3"));
		list=(ListView)findViewById(R.id.list);


		adapter=new CameraAdapterList(this,cameraListToDisplay);
		list.setAdapter(adapter);


		// Log.e("HomeListActivity","Apres list.setAdapter(adapter);");

		final Context context =this;
		// Click event for single list row
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				Intent intent = new Intent(context,VideoViewDemo.class);
				intent.putExtra("cameraUrl",cameraListToDisplay.get(position).getStream());
				startActivity(intent);

			}
		});
		*/
		sendCameraListRequest();
	}

	public void sendCameraListRequest(){

		list=(ListView)findViewById(R.id.listCamera);

		// end reading configuration informations
		url = GlobalService.getUrl("cameraList",this);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			homeID = extras.getString("homeID");
			// and get whatever type user account id is
		}
		cameraList = new HashMap<String, Object>();
		cameraList.put("sessionID",GlobalService.getUserSession(this));
		cameraList.put("homeID",homeID);
		//userLogin.put("registrationId",CommonUtilities.registration_ID);


		serverCommunication = new ActivityServerCommunication(CameraListActivity.this,cameraList, "GET", url, ServiceIdentification.cameraList);
		serverCommunication.delegate = CameraListActivity.this;
		serverCommunication.execute();

	}
	public void processFinish(int response,Object object,int serviceId){
		//this you will received result fired from async class of onPostExecute(result) method.



		if(response ==-1){
			//an error occurs may be network issue


			Toast.makeText(CameraListActivity.this,
					R.string.communication_error, Toast.LENGTH_LONG)
					.show();
		}
		else{


			if(response==0){
				//ok

				JSONObject data =(JSONObject)object;
				/*
				int zoneLength;
				//check zone size
				try {

					zoneLength = data.getJSONArray("result").length();

				} catch (JSONException e) {
					cameraLength =0;
				}

				if(cameraLength == 0){
					Toast.makeText(CameraListActivity.this,
							R.string.empty_list_home, Toast.LENGTH_LONG)
							.show();
					no_items_tv = (TextView) findViewById(R.id.empty_list_home);
					no_items_tv.setVisibility(View.VISIBLE);

				}else if (cameraLength == 1){
					no_items_tv.setVisibility(View.GONE);
					displayCameraList(data);


				}else{
					no_items_tv.setVisibility(View.GONE);
					displayCameraList(data);
				}
				*/
				displayCameraList(data);

			}
			else if(response ==300){
				//ok
				Toast.makeText(CameraListActivity.this,
						R.string.communication_error, Toast.LENGTH_LONG)
						.show();

			}
			else if(response ==311){
				//ok
				Toast.makeText(CameraListActivity.this,
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

		cameraListToDisplay = cameraListJson;
		//Log.e("HomeListActivity","Avant d'instancier adapter");
		// Getting adapter by passing xml data ArrayList
		adapter=new CameraAdapterList(this,cameraListToDisplay);
		//Log.e("HomeListActivity","Apres instancier adapter");
		list.setAdapter(adapter);
		no_items_tv = (TextView) findViewById(R.id.empty_list_home);
		list.setEmptyView(no_items_tv);

		// Click event for single list row
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@SuppressLint("LongLogTag")
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {

				try {


						urlCamera = (cameraListToDisplay.getJSONArray("result").getJSONObject(position).getString("url"));
						cameraID  = (cameraListToDisplay.getJSONArray("result").getJSONObject(position).getInt("cameraID"));
					    cameraFeed  = (cameraListToDisplay.getJSONArray("result").getJSONObject(position).getString("cameraFeed"));

					Intent nextScreen = new Intent(getApplicationContext(), StreamCameraActivity.class);
						nextScreen.putExtra("cameraPosition","position");
						nextScreen.putExtra("cameraUrl",urlCamera);
						nextScreen.putExtra("cameraID",cameraID);
						nextScreen.putExtra("cameraFeed",cameraFeed);

					    //Log.v("cameraID............",""+cameraID);
					    Log.e(" ============ cameraFeed =====================",""+cameraFeed);
						//startActivity(nextScreen);

				} catch (JSONException e) {

				}

			}
		});



	}

	public void gotoLogin(){

		Intent nextScreen = new Intent(getApplicationContext(),LoginActivity.class);
		startActivity(nextScreen);
		finish();

	}

}