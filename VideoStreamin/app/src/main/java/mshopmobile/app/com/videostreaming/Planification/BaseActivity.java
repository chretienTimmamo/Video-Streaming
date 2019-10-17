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
package mshopmobile.app.com.videostreaming.Planification;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import mshopmobile.app.com.videostreaming.Planification.preferences.PlanningPeriodActivity;
import mshopmobile.app.com.videostreaming.R;


public abstract class BaseActivity extends Activity implements android.view.View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(R.drawable.ic_launcher);
		actionBar.setDisplayUseLogoEnabled(false);
		// Creating ActionBar tabs.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//setting custom tab icon

		ActionBar.Tab addPlannig = actionBar.newTab();
		addPlannig.setText(R.string.menu_item_new);
		actionBar.show();
		ActionBar.Tab savePlanning = actionBar.newTab();
		savePlanning.setText(R.string.menu_item_save);

		ActionBar.Tab deletedPlannig = actionBar.newTab();
		deletedPlannig.setText(R.string.menu_item_delete);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.planification_menu,menu);
		//menu.findItem(R.id.menu_item_delete).setVisible(false);
		//menu.findItem(R.id.menu_item_save).setVisible(false);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String url = null;
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.menu_item_new:
			Intent newAlarmIntent = new Intent(this, PlanningPeriodActivity.class);
			startActivity(newAlarmIntent);
			break;

		}
		return super.onOptionsItemSelected(item);
	}

//	protected void callMathAlarmScheduleService() {
//		Intent mathAlarmServiceIntent = new Intent(this, AlarmServiceBroadcastReciever.class);
//		sendBroadcast(mathAlarmServiceIntent, null);
//	}
}
