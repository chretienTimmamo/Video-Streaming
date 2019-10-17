package mshopmobile.app.com.videostreaming;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import SimpleClass.PhoneInfos;
import util.WebService.ActivityServerCommunication;
import util.WebService.AsyncCommunicationResponse;
import util.ExternalFile;
import util.GlobalService;
import util.ServiceIdentification;
import util.UrlAddress;

public class LoginActivity extends Activity implements AsyncCommunicationResponse {

	public EditText login;
	public String loginText;
	public EditText password;
	public String passwordText;
	public String sessionID ="";
	public UrlAddress urlAddress;
	private String url ;
	ActivityServerCommunication serverCommunication=null;
	LoginActivity loginActivity = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		//end reading configuration informations
		setContentView(R.layout.login);


		login = (EditText) findViewById(R.id.loginText);
		password = (EditText) findViewById(R.id.password);

		//login.setText("camertronix@camertronix.com");
		//password.setText("1234");
		TextView dashboardView = (TextView) findViewById(R.id.btnLogin);

		dashboardView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			   	//loginText = login.getText().toString();
				//passwordText = password.getText().toString();
				loginText = "camertronix@camertronix.com";
				passwordText = "1234";

				// end reading configuration informations
				url = GlobalService.getUrl("login",LoginActivity.this);
				Log.i("url login", url);

				Boolean loginStatus = false;
				Boolean passwordStatus = false;
				// We check if the login or password have as least 6 characters.
				if (loginText.length() < 6) {
					login.setError("Your login must be at least 6 characters");
				} else
					loginStatus = true;

				if (passwordText.length() < 2) {
					password.setError("Your password must match and must be at least 2 characters");
				} else
					passwordStatus = true;

				if (loginStatus && passwordStatus) {

					// we call the web service
					sendUserLoginRequest();


				}

			}
		});
	}

	@Override
	public void onBackPressed() {
		Log.e("BackPressed", "Login back pressed");
		Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(nextScreen);
		finish();


	}
	/*
	public String getUrlLogin(String serviceName){

		//JSONObject serverAddress = ConfigurationFile.serverAddress;
		//JSONObject baseUrl = ConfigurationFile.baseUrl;
		JSONObject serverAddress = GlobalService.getServerAddress(LoginActivity.this,"serverAddress");

		JSONObject baseUrl = GlobalService.getServerAddress(LoginActivity.this, "baseUrl");
		//Log.i("serverAddress",serverAddress.toString());
		//Log.i("baseUrl",baseUrl.toString());
		String urlLogin;
		String baseUrlLogin;
		try {
			baseUrlLogin = baseUrl.getString(serviceName);
			urlLogin = serverAddress.getString("protocol")+serverAddress.getString("serverIpAddress")+":"+serverAddress.getString("port")+baseUrlLogin;
		
		} catch (Exception ex) {
	        ex.printStackTrace();
	        return "";
	    }

		return urlLogin;
	}
	*/
	public void saveLoginUsager(String login,String password,String sessionID){
		SharedPreferences sharedpreferences;
		sharedpreferences = getSharedPreferences(ExternalFile.userLogin, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();


		editor.putString("login", login);
		editor.putString("pwd", password);
		editor.putString("sessionID",sessionID);
		editor.commit();

	}
	public boolean isUserLogin(){

		SharedPreferences sharedpreferences;
		sharedpreferences = getSharedPreferences(ExternalFile.userLogin, Context.MODE_PRIVATE);

		String userLogin = sharedpreferences.getString("login","");
		String userPwd = sharedpreferences.getString("pwd","");

		if (!GlobalService.isEmpty(userLogin)&&(!GlobalService.isEmpty(userPwd))){

			loginText = userLogin;
			passwordText = userPwd;

			return true;
		}
		else return false;

	}

	public void sendUserLoginRequest(){

		// end reading configuration informations
		url = GlobalService.getUrl("login", this);
		Map<String, Object> userLogin = new HashMap<String, Object>();
		userLogin.put("login", loginText);
		userLogin.put("password",passwordText);
		//userLogin.put("registrationId",CommonUtilities.registration_ID);


		serverCommunication = new ActivityServerCommunication(LoginActivity.this,userLogin, "GET", url, ServiceIdentification.userLogin);
		serverCommunication.delegate = LoginActivity.this;
		serverCommunication.execute();

	}
	public void processFinish(int response,Object object,int serviceId){
		//this you will received result fired from async class of onPostExecute(result) method.



		if(response ==-1){
			//an error occurs may be network issue

			Toast.makeText(LoginActivity.this,
					R.string.communication_error, Toast.LENGTH_LONG)
					.show();
			//gotoDashBoard();

			//JSONObject data =(JSONObject)object;
			//saveLoginUsager(loginText,passwordText);
			//gotoDashBoard();
		}
		else{


			if(response==0){
				//ok
				JSONObject data =(JSONObject)object;
/*
					Toast.makeText(LoginActivity.this,
							       "Success!", Toast.LENGTH_LONG)
						 .show();
					gotoDashBoard(LoginActivity.this,data);
					startNotificationService(data);
*/

				//String sessionID;
				try {
					sessionID = data.getString("sessionID");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				saveLoginUsager(loginText,passwordText,sessionID);

				gotoDashBoard();

			}
			else if(response ==300){
				//ok
				Toast.makeText(LoginActivity.this,
						R.string.incorrentLoginPassword, Toast.LENGTH_LONG)
						.show();
				//gotoDashBoard();
			}


			else{

				/*
				JSONObject data =(JSONObject)object;
				saveLoginUsager(loginText,passwordText);
				gotoDashBoard();
				*/
				Toast.makeText(LoginActivity.this,
						R.string.communication_error, Toast.LENGTH_LONG)
						.show();
			}

		}

	}
	public void gotoDashBoard(){

		gotoDashBoard(LoginActivity.this);
		//startNotificationService(data);

		//Intent msgIntent = new Intent(LoginActivity.this, NotificationPushService.class);
		//startService(msgIntent);

	}


	public void gotoDashBoard(Context context){

		    //if (!LibsChecker.checkVitamioLibs(this))
			// return


			Intent intent = new Intent(context,VideoTabManagerActivity.class);
			startActivity(intent);
            finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main_actions, menu);

		return super.onCreateOptionsMenu(menu);
		//return true;
	}

}
