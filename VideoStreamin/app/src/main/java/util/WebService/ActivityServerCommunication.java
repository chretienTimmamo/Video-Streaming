package util.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.Map;

import SimpleClass.PhoneInfos;
import mshopmobile.app.com.videostreaming.R;
import util.ServiceIdentification;

public class ActivityServerCommunication extends AsyncTask<Void, JSONObject, JSONObject> {
	
	// Store context for dialogs
	private Context context = null;
	// Store error message
	private Exception e = null;
	// Passed in data object
	private Map<String, Object> data = null;
	// Passed in method
	private String method = "";
	// Passed in url
	private String url = "";
	//Call back interface
	public AsyncCommunicationResponse delegate = null;
	private int serviceId; 
	private AndrestClient rest = new AndrestClient();
	public String phoneId;
	
	private ProgressDialog dialog;
	
	public ActivityServerCommunication (Context context, Map<String, Object> data, String method, String url,int serviceId){
		this.context = context;
		this.data = data;
		this.method = method;
		this.url = url;
		this.serviceId = serviceId;
		phoneId = PhoneInfos.getDeviceIdAndroid(context);
		//Log.i("infos","infos");
	}
	
	@Override
	protected void onPreExecute() {

		data.put("version",getVersionCode());
		//read phone ID
		data.put("phoneId",phoneId);
		if(this.serviceId== ServiceIdentification.userLoginService){

		}else if(this.serviceId== ServiceIdentification.checkCameraAvailability){

		}else if(this.serviceId== ServiceIdentification.disableCameraAvailability){

		}
		else
		{

			dialog = new ProgressDialog(context);
			dialog.setCanceledOnTouchOutside(false);
			if(serviceId== ServiceIdentification.userLogin)
				   dialog.setMessage(context.getResources().getString(R.string.loginPopup));
			else
			
				if(serviceId== ServiceIdentification.userRegistration)
					dialog.setMessage(context.getResources().getString(R.string.userRegistrationPopup));
					 else
						 dialog.setMessage(context.getResources().getString(R.string.processingPopup));
			
				dialog.show();

				
		}

	}
	
	@Override
	protected JSONObject doInBackground(Void... arg0) {
		
		try {
		
			//Log.i("CLOCKS",url);
			return rest.request(url, method, data); // Do request
		} catch (Exception e) {
			this.e = e;	// Store error
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(JSONObject data) {
		
		super.onPostExecute(data);
		
		int error =-1;
		
		//if (this.dialog.isShowing()) {
				if (dialog!=null) {
					dialog.dismiss();
				}
			
		// Display based on error existence
		//e.printStackTrace();
		if(e != null){
			//Log.i("Error Network", e.getMessage());
			//new ResponseDialog(context, "We found an error!", e.getMessage()).showDialog();
			/*
			Toast.makeText(context,
					"We found an error!", Toast.LENGTH_LONG)
					.show();
			*/
			delegate.processFinish(error,(Object)e.getMessage(),serviceId);
		} else {
			//new ResponseDialog(context, "Success!", data.toString()).showDialog();

			//Log.i("data response", data.toString());
			
			int response =0;
			
			try {
				response = Integer.parseInt(data.getString("response").trim());
				
				delegate.processFinish(response,(Object)data,serviceId);
			} catch (Exception e) {
				this.e = e;	// Store error
				/*
				Toast.makeText(context,
						"Msg!"+e.getMessage(), Toast.LENGTH_LONG)
						.show();
				*/
				//e.printStackTrace();
				//delegate.processFinish(error,e.getMessage(),serviceId);
			}
		
			
				
		}
		
		
		
	}

	public String getVersionName(){
		PackageInfo pinfo = null;
		try {
			pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e1) {
			e1.printStackTrace();
		}
		String versionName = pinfo.versionName;
		return versionName;
	}

	public int getVersionCode(){
		PackageInfo pinfo = null;
		try {
			pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e1) {
			e1.printStackTrace();
		}
		int versionNumber = pinfo.versionCode;
		return versionNumber;
	}

}
