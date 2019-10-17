/* Copyright 2014 Sheldon Neilson www.neilson.co.za
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

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mshopmobile.app.com.videostreaming.Planification.Planning;
import mshopmobile.app.com.videostreaming.R;

public class PlanningPeriodDetailAdapter extends BaseAdapter implements Serializable {

	private Context context;
	private Planning alarm;
	private List<PlanningPeriod> preferences = new ArrayList<PlanningPeriod>();
	EditText checkPeriodFrom;
	EditText checkPeriodTo;
	private final String[] repeatDays = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

	
	public PlanningPeriodDetailAdapter(Context context, Planning alarm) {
		setContext(context);
	    setMathAlarm(alarm);		
	}

	@Override
	public int getCount() {
		return preferences.size();
	}

	@Override
	public int getItemViewType(int position) {

		if(preferences.get(position).getKey() == PlanningPeriod.Key.ALARM_TIME)
			return 0;
		else if(preferences.get(position).getKey() == PlanningPeriod.Key.ALARM_REPEAT)
			return 1;
		else
		    return 2;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return preferences.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PlanningPeriod planningPeriod = (PlanningPeriod) getItem(position);
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		int type = getItemViewType(position);
		switch (planningPeriod.getType()) {

		case TIME:
		default:
			if( convertView == null)
			//convertView = layoutInflater.inflate(android.R.layout.simple_list_item_2, null);
			if(type==0){
				convertView = layoutInflater.inflate(R.layout.planning_period_detail_check_period_row, null);
				TextView text1 = (TextView) convertView.findViewById(R.id.textTitle);
				checkPeriodFrom = (EditText) convertView.findViewById(R.id.checkPeriodFrom);
				checkPeriodTo = (EditText) convertView.findViewById(R.id.checkPeriodTo);
				text1.setTextSize(18);
				text1.setText(planningPeriod.getTitle());

				checkPeriodFrom.setText(planningPeriod.getSummary());
				checkPeriodTo.setText((String) planningPeriod.getValue());


				checkPeriodFrom.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View view, MotionEvent motionEvent) {
						// your code here....
						//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
						//return false;
						if(motionEvent.getAction() == MotionEvent.ACTION_UP)
						showCalendar(0);
						return false;
					}
				});
				checkPeriodTo.setOnTouchListener(new View.OnTouchListener() {
					public boolean onTouch(View view, MotionEvent motionEvent) {
						// your code here....
						//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
						//return false;
						if(motionEvent.getAction() == MotionEvent.ACTION_UP)
						showCalendar(1);
						return false;
					}
				});


			} else if(type==1){
				convertView = layoutInflater.inflate(R.layout.planning_period_detail_days_list, null);
				TextView text1 = (TextView) convertView.findViewById(R.id.text1);
				text1.setTextSize(18);
				text1.setText(planningPeriod.getTitle());
				TextView text2 = (TextView) convertView.findViewById(R.id.textDay);
				text2.setText(planningPeriod.getSummary());

			}else{

				convertView = layoutInflater.inflate(R.layout.planning_period_detail_status, null);
				Switch toggleStatus = (Switch) convertView.findViewById(R.id.toggleStatus);
				toggleStatus.setChecked(planningPeriod.isStatus());
			}
			break;
		}

		return convertView;
	}

	public void setMathAlarm(Planning alarm) {
		this.alarm = alarm;
		preferences.clear();
		preferences.add(new PlanningPeriod(PlanningPeriod.Key.ALARM_TIME, "Set recording time", alarm.getRecordingFrom(), null, alarm.getRecordingTo(),PlanningPeriod.Type.TIME,alarm.isStatus()));
		preferences.add(new PlanningPeriod(PlanningPeriod.Key.ALARM_REPEAT, "Repeat",alarm.getRepeatDaysString(), repeatDays, alarm.getDays(), PlanningPeriod.Type.MULTIPLE_LIST,alarm.isStatus()));
		preferences.add(new PlanningPeriod(PlanningPeriod.Key.ALARM_CHECKER, "Repeat",alarm.getRepeatDaysString(), repeatDays, alarm.getDays(), PlanningPeriod.Type.MULTIPLE_LIST,alarm.isStatus()));
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String[] getRepeatDays() {
		return repeatDays;
	}

	public void showCalendar(final int recording){
		TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker timePicker, int hours, int minutes) {

		        Calendar newAlarmTime = Calendar.getInstance();
				newAlarmTime.set(Calendar.MINUTE, minutes);
				newAlarmTime.set(Calendar.HOUR_OF_DAY, hours);
				newAlarmTime.set(Calendar.SECOND, 0);
				alarm.setAlarmTime(newAlarmTime);
				setMathAlarm(alarm);

				Log.i("alarm", alarm.getAlarmTimeString());

				if(recording ==0){
					checkPeriodFrom.setText(alarm.getAlarmTimeString());


				}else{
					checkPeriodTo.setText(alarm.getAlarmTimeString());

				}
				notifyDataSetChanged();


			}


		},alarm.getAlarmTime().get(Calendar.HOUR_OF_DAY), alarm.getAlarmTime().get(Calendar.MINUTE),true);
		//timePickerDialog.setTitle(alarmPreference.getTitle());
		timePickerDialog.show();

	}

}
