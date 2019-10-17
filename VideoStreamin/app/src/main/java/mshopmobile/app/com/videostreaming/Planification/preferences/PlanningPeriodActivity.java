/* Copyright 2014 Sheldon Blaise Siani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package mshopmobile.app.com.videostreaming.Planification.preferences;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import mshopmobile.app.com.videostreaming.Planification.Planning;
import mshopmobile.app.com.videostreaming.R;
import util.GlobalService;
import util.ServiceIdentification;
import util.WebService.ActivityServerCommunication;
import util.WebService.AsyncCommunicationResponse;

public class PlanningPeriodActivity extends Activity implements View.OnClickListener, AsyncCommunicationResponse {

	private Planning alarm;

	private ListAdapter listAdapter;
	private ListView listView;
	String action;
	public String planning_type;
	int homeID = 0;
	int cameraID =0;
	String copyUrl;
	EditText checkPeriodFrom;
	EditText checkPeriodTo;
	ActivityServerCommunication serverCommunication = null;
	TextView textDay;
	Switch periodStatus;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_preferences);

		Bundle bundle = getIntent().getExtras();
		//planning_type = bundle.getString(Planning.PLANNING_TYPE);
		action = bundle.getString("Edit");
		cameraID = bundle.getInt("cameraID");
		if (bundle.containsKey("alarm")) {
			alarm = new Planning();
			Planning plann = (Planning) bundle.getSerializable("alarm");
			Log.e("planning a modifier id", " "+plann.getPeriodStreamID());
			//alarm.setAlarmTime(plann.getTime());
			alarm.setDays(plann.setDayByString(plann.getDaycode()));
			Log.e("planning a modifier", "" + plann.getDays());
			//alarm.setId(plann.getId());
			alarm.setId(plann.getId());
			alarm.setRecordingFrom(plann.getRecordingFrom());
			alarm.setRecordingTo(plann.getRecordingTo());
			alarm.setStreamCodec(plann.getStreamCodec());
			alarm.setPeriodStreamID(plann.getPeriodStreamID());
			alarm.setStatus(plann.isStatus());

			//alarm.setType(plann.getType());
			setMathAlarm(alarm);
			//Log.e("planning type ", planning_type);
		} else {
			setMathAlarm(new Planning());
		}
		if (bundle != null && bundle.containsKey("adapter")) {
			setListAdapter((PlanningPeriodDetailAdapter) bundle.getSerializable("adapter"));
		} else {
			setListAdapter(new PlanningPeriodDetailAdapter(this, getMathAlarm()));
		}

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				final PlanningPeriodDetailAdapter planningPeriodDetailAdapter = (PlanningPeriodDetailAdapter) getListAdapter();
				final PlanningPeriod planningPeriod = (PlanningPeriod) planningPeriodDetailAdapter.getItem(position);
				AlertDialog.Builder alert;
				final View viewRepeat = v;
				switch (planningPeriod.getType()) {

					case MULTIPLE_LIST:
						alert = new AlertDialog.Builder(PlanningPeriodActivity.this);
						alert.setTitle(planningPeriod.getTitle());
						// alert.setMessage(message);

						CharSequence[] multiListItems = new CharSequence[planningPeriod.getOptions().length];
						for (int i = 0; i < multiListItems.length; i++)
							multiListItems[i] = planningPeriod.getOptions()[i];

						boolean[] checkedItems = new boolean[multiListItems.length];
						for (Planning.Day day : getMathAlarm().getDays()) {
							checkedItems[day.ordinal()] = true;
						}
						alert.setMultiChoiceItems(multiListItems, checkedItems, new OnMultiChoiceClickListener() {

							@Override
							public void onClick(final DialogInterface dialog, int which, boolean isChecked) {

								Planning.Day thisDay = Planning.Day.values()[which];

								if (isChecked) {
									alarm.addDay(thisDay);
								} else {
									// Only remove the day if there are more than 1
									// selected
									if (alarm.getDays().length > 1) {
										alarm.removeDay(thisDay);
									} else {
										// If the last day was unchecked, re-check
										// it

										((AlertDialog) dialog).getListView().setItemChecked(which, true);
									}
								}

							}
						});
						alert.setOnCancelListener(new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								textDay = (TextView) viewRepeat.findViewById(R.id.textDay);
								textDay.setText(alarm.getRepeatDaysString());
								planningPeriodDetailAdapter.setMathAlarm(getMathAlarm());
								planningPeriodDetailAdapter.notifyDataSetChanged();

							}
						});
						alert.show();
						break;
					case TIME:
						TimePickerDialog timePickerDialog = new TimePickerDialog(PlanningPeriodActivity.this, new OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
								Calendar newAlarmTime = Calendar.getInstance();
								newAlarmTime.set(Calendar.MINUTE, minutes);
								newAlarmTime.set(Calendar.HOUR_OF_DAY, hours);
								newAlarmTime.set(Calendar.SECOND, 0);
								alarm.setAlarmTime(newAlarmTime);
								planningPeriodDetailAdapter.setMathAlarm(getMathAlarm());
								planningPeriodDetailAdapter.notifyDataSetChanged();

							}


						},alarm.getAlarmTime().get(Calendar.HOUR_OF_DAY), alarm.getAlarmTime().get(Calendar.MINUTE), true);
						timePickerDialog.setTitle(planningPeriod.getTitle());
						timePickerDialog.show();


					default:
						break;
				}
			}
		});



		//checkPeriodFrom = (EditText) convertView.findViewById(R.id.checkPeriodFrom);
		//checkPeriodTo = (EditText) convertView.findViewById(R.id.checkPeriodTo);
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//boolean result = ;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.planification_menu, menu);
		menu.findItem(R.id.menu_item_new).setVisible(false);
		if (!action.equals("edit")){
			menu.findItem(R.id.menu_item_delete).setVisible(false);
		}
		//menu.findItem(R.id.menu_item_save).setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_save:

				checkPeriodFrom = (EditText)findViewById(R.id.checkPeriodFrom);
				checkPeriodTo = (EditText) findViewById(R.id.checkPeriodTo);
				periodStatus = (Switch) findViewById(R.id.toggleStatus);
				Log.i("checkPeriodFrom","checkPeriodFrom"+checkPeriodFrom.getText()+"checkPeriodTo"+checkPeriodTo.getText());

				if (action.equals("edit")){
					//editPeriodPlanning(String type,String recordingFrom,String recordingTo, String days, int id)
					editPeriodPlanning(alarm.getType(),setEmptySecondRecording(checkPeriodFrom.getText().toString()) ,setEmptySecondRecording(checkPeriodTo.getText().toString()), alarm.mappingDayString(alarm.getRepeatDaysString().toString()), alarm.getId(),periodStatus.isChecked());
				}else {
					//Log.e("Planning information", alarm.getAlarmTimeString().toString() + "  days  " + alarm.mappingDay(alarm.getRepeatDaysString().toString()));
					//creadNewPeriod(planning_type, alarm.getAlarmTimeString().toString(), alarm.mappingDayString(alarm.getRepeatDaysString().toString()));

					//creadNewPeriodPlanning(String type,String recordingFrom,String recordingTo, String days)
					if(checkPeriodFrom.getText().toString().length()==0||checkPeriodTo.getText().toString().length()==0)
						Toast.makeText(getApplicationContext(),
								R.string.emptyPeriod, Toast.LENGTH_LONG)
								.show();
					else
						creadNewPeriodPlanning(planning_type, setEmptySecondRecording(checkPeriodFrom.getText().toString()).toString(), setEmptySecondRecording(checkPeriodTo.getText().toString()), alarm.mappingDayString(alarm.getRepeatDaysString().toString()),periodStatus.isChecked());

				}
				//finish();
				break;
			case R.id.menu_item_delete:
				AlertDialog.Builder dialog = new AlertDialog.Builder(PlanningPeriodActivity.this);
				dialog.setTitle("Delete");
				dialog.setMessage("Delete this period?");
				dialog.setPositiveButton("Ok", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deletePeriod(alarm.getId());
						//finish();
					}
				});
				dialog.setNegativeButton("Cancel", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						//finish();
					}
				});
				dialog.show();

				break;
		}
		return super.onOptionsItemSelected(item);
	}
	private CountDownTimer alarmToneTimer;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable("alarm", getMathAlarm());
		outState.putSerializable("adapter", (PlanningPeriodDetailAdapter) getListAdapter());
	}

	@Override
	protected void onPause() {
		super.onPause();
		// setListAdapter(null);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public Planning getMathAlarm() {
		return alarm;
	}

	public void setMathAlarm(Planning alarm) {
		Log.e("Planning information", alarm.getAlarmTime() + alarm.getDaycode());
		this.alarm = alarm;
	}

	public ListAdapter getListAdapter() {
		return listAdapter;
	}

	public void setListAdapter(ListAdapter listAdapter) {
		this.listAdapter = listAdapter;
		getListView().setAdapter(listAdapter);

	}
	public String setEmptySecondRecording(String recordingPeriod){


		if(recordingPeriod.length()<=5)
			return recordingPeriod+":"+"00";
		else
			return recordingPeriod;
	}

	public ListView getListView() {
		if (listView == null)
			listView = (ListView) findViewById(android.R.id.list);
		return listView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	@Override
	public void onClick(View v) {
		// super.onClick(v);

	}


	public void creadNewPeriodPlanning(String type,String recordingFrom,String recordingTo, String days,boolean periodStatus) {
		String url = GlobalService.getUrl("creatPeriod", PlanningPeriodActivity.this);

		copyUrl = url;
		Map<String, Object> creatperiod = new HashMap<String, Object>();
		creatperiod.put("sessionID", GlobalService.getUserSession(PlanningPeriodActivity.this));
		//creatperiod.put("type", type);
		creatperiod.put("day", days.toString());
		creatperiod.put("recordingFrom",recordingFrom);
		creatperiod.put("recordingTo", recordingTo);
		creatperiod.put("cameraID",cameraID);
		creatperiod.put("streamCodec","MPEG");
		creatperiod.put("streamFormat","mp4");
		creatperiod.put("activated",periodStatus);
		//loadingMore = true;
		serverCommunication = new ActivityServerCommunication(PlanningPeriodActivity.this, creatperiod, "GET", url, ServiceIdentification.planningCreatePeriod);
		serverCommunication.delegate = PlanningPeriodActivity.this;
		serverCommunication.execute();
	}


	public void editPeriodPlanning(String type,String recordingFrom,String recordingTo, String days, int id,boolean periodStatus) {
		String url = GlobalService.getUrl("editPeriod", PlanningPeriodActivity.this);

        copyUrl = url;
		Map<String, Object> editperiod = new HashMap<String, Object>();
		editperiod.put("sessionID", GlobalService.getUserSession(PlanningPeriodActivity.this));
		//editperiod.put("type", type);
		editperiod.put("day", days);
		editperiod.put("recordingFrom", recordingFrom);
		editperiod.put("recordingTo", recordingTo);
		editperiod.put("periodID", id);
		editperiod.put("cameraID",cameraID);
		editperiod.put("streamCodec","MPEG");
		editperiod.put("streamFormat","mp4");
		editperiod.put("activated",periodStatus);

		//loadingMore = true;
		serverCommunication = new ActivityServerCommunication(PlanningPeriodActivity.this, editperiod, "GET",url , ServiceIdentification.planningEditPeriod);
		serverCommunication.delegate = PlanningPeriodActivity.this;
		serverCommunication.execute();
	}

	public void deletePeriod(int id) {
		String url = GlobalService.getUrl("deletedPeriod", PlanningPeriodActivity.this);
		copyUrl = url;
		Map<String, Object> deleteperiod = new HashMap<String, Object>();
		deleteperiod.put("sessionID", GlobalService.getUserSession(PlanningPeriodActivity.this));
		deleteperiod.put("periodID", id);
		Log.e("delete priode ", deleteperiod.toString());
		serverCommunication = new ActivityServerCommunication(PlanningPeriodActivity.this, deleteperiod, "GET", url, ServiceIdentification.planningDeletePeriod);
		serverCommunication.delegate = PlanningPeriodActivity.this;
		serverCommunication.execute();
	}

	@Override
	public void processFinish(int output, Object object, int serviceId) {
		serverCommunication.cancel(true);
		if (ServiceIdentification.planningCreatePeriod == serviceId) {
			newPeriodAddFinish(output, object, serviceId);
		} else if (ServiceIdentification.planningEditPeriod == serviceId)
			upDatePeriodFinish(output, object, serviceId);
		else if (ServiceIdentification.planningDeletePeriod == serviceId)
			deletedPeriodFinish(output, object, serviceId);
	}

	public void newPeriodAddFinish(int response, Object object, int serviceId) {

		Log.e("return", "" + object);
		if (response == -1) {
			//an error occurs may be network issue

			Toast.makeText(getApplicationContext(),
					R.string.communication_error, Toast.LENGTH_LONG)
					.show();
		}else if (response == 0) {
			//an error occurs may be network issue 3008

			Toast.makeText(getApplicationContext(),
					R.string.planning_add, Toast.LENGTH_LONG)
					.show();
			finish();
		}
		else if (response == 3008) {
			//an error occurs may be network issue 3008

			Toast.makeText(getApplicationContext(),
					R.string.existing_period, Toast.LENGTH_LONG)
					.show();
		}

	}

	public void deletedPeriodFinish(int response, Object object, int serviceId) {

		Log.e("return", "" + object);
		if (response == -1) {
			//an error occurs may be network issue

			Toast.makeText(getApplicationContext(),
					R.string.communication_error, Toast.LENGTH_LONG)
					.show();
		}else if (response == 0) {
			//an error occurs may be network issue

			Toast.makeText(getApplicationContext(),
					R.string.menu_item_delete, Toast.LENGTH_LONG)
					.show();
			finish();
		}

	}

	public void upDatePeriodFinish(int response, Object object, int serviceId) {

		Log.e("return", "" + object);
		if (response == -1) {
			//an error occurs may be network issue

			Toast.makeText(getApplicationContext(),
					R.string.communication_error, Toast.LENGTH_LONG)
					.show();
		}else if (response == 0) {
			//an error occurs may be network issue

			Toast.makeText(getApplicationContext(),
					R.string.menu_item_save, Toast.LENGTH_LONG)
					.show();
			finish();
		}

	}
	public void checkPeriodFromEvent(View view){
		Log.i("checkPeriodFromEvent","checkPeriodFromEvent");
		TimePickerDialog timePickerDialog = new TimePickerDialog(PlanningPeriodActivity.this, new OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker timePicker, int hours, int minutes) {

				/*
		        Calendar newAlarmTime = Calendar.getInstance();
				newAlarmTime.set(Calendar.MINUTE, minutes);
				newAlarmTime.set(Calendar.HOUR_OF_DAY, hours);
				newAlarmTime.set(Calendar.SECOND, 0);
				alarm.setAlarmTime(newAlarmTime);
				alarmPreferenceListAdapter.setMathAlarm(getMathAlarm());
				alarmPreferenceListAdapter.notifyDataSetChanged();
				*/
			}


		}, alarm.getAlarmTime().get(Calendar.HOUR_OF_DAY), alarm.getAlarmTime().get(Calendar.MINUTE), true);
		//timePickerDialog.setTitle(alarmPreference.getTitle());
		timePickerDialog.show();

	}
	public void checkPeriodToEvent(View view){

		Log.i("checkPeriodToEvent","checkPeriodToEvent");
	}
}
