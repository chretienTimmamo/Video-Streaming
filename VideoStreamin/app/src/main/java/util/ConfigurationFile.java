package util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class ConfigurationFile {
	//server address
	public static JSONObject serverAddress;
	
	public static JSONObject baseUrl;
	//response code
	public static JSONObject responseCode;
	
	public static JSONObject operationCode;
	
	public static JSONObject operationType;
	
	public static JSONObject action;
	

	public static JSONObject getServerAddress() {
		return serverAddress;
	}

	public static void setServerAddress(JSONObject serverAddress) {
		ConfigurationFile.serverAddress = serverAddress;
	}

	public static JSONObject getBaseUrl() {
		return baseUrl;
	}

	public static void setBaseUrl(JSONObject baseUrl) {
		ConfigurationFile.baseUrl = baseUrl;
	}

	public static JSONObject getResponseCode() {
		return responseCode;
	}

	public static void setResponseCode(JSONObject responseCode) {
		ConfigurationFile.responseCode = responseCode;
	}

	public static JSONObject getOperationCode() {
		return operationCode;
	}

	public static void setOperationCode(JSONObject operationCode) {
		ConfigurationFile.operationCode = operationCode;
	}

	public static JSONObject getOperationType() {
		return operationType;
	}

	public static void setOperationType(JSONObject operationType) {
		ConfigurationFile.operationType = operationType;
	}
	
	

	public static JSONObject getAction() {
		return action;
	}

	public static void setAction(JSONObject action) {
		ConfigurationFile.action = action;
	}

	public static String loadJSONFromAsset(String fileName,Context context) {
	    String json = null;
	    //Log.e("loadJSONFromAsset  filename=",fileName );
	    try {

	        InputStream is = context.getAssets().open(fileName);

	        int size = is.available();

	        byte[] buffer = new byte[size];

	        is.read(buffer);

	        
	        is.close();

	        json = new String(buffer, "UTF-8");

              
	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    
	    //Log.e("loadJSONFromAsset  json=",json );
	    return json;

	}
    
	public static void serverAddressFileObject(String fileName,Context context) {
		//Log.e("ConfigurationFile baseUrl   filename=",fileName );
		try {
			
			 JSONObject obj = new JSONObject(loadJSONFromAsset(fileName,context));
		     JSONObject serverAddress = obj.getJSONObject("serverAddress");
		     JSONObject baseUrl = obj.getJSONObject("uri");
		     //Log.d("Details-->",jo_inside.getString("formule"));
		     //Log.i("baseUrl-->",baseUrl.toString());
		     //Log.i("serverAddress-->",serverAddress.toString());
		    ConfigurationFile.baseUrl = baseUrl;
	         // Log.e("ConfigurationFile baseUrl",baseUrl.toString() );
		    ConfigurationFile.serverAddress = serverAddress;
		     
		     //save baseUrl
		     
		     //save serverAddress
	         
		} catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	public static void responseAddressFileObject(String fileName,Context context) {
		
		try {
			
			 JSONObject obj = new JSONObject(loadJSONFromAsset(fileName,context));
		     JSONObject responseCode = obj.getJSONObject("responseCode");
		     
		     //Log.i("responseCode-->",responseCode.toString());
		     ConfigurationFile.responseCode = responseCode;
	         
		     //save responseCode
		     
		} catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	public static void operationCodeAddressFileObject(String fileName,Context context) {
		
		try {
			
			 JSONObject obj = new JSONObject(loadJSONFromAsset(fileName,context));
		     JSONObject operationCode = obj.getJSONObject("operationCode");
		     JSONObject operationType = obj.getJSONObject("operationType");
		     JSONObject action = obj.getJSONObject("action");
		     //Log.i("responseCode-->",responseCode.toString());
	         ConfigurationFile.operationCode = operationCode;
	         ConfigurationFile.operationType = operationType;
	         ConfigurationFile.action = action;
	         
	         //save operationCode
	         
	         //save operationType
	         
	         //save action
	         
		} catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	public static void setAllFileObject(Context context){
		
		serverAddressFileObject(ExternalFile.serverAddress,context);
		responseAddressFileObject(ExternalFile.responseCode,context);
		operationCodeAddressFileObject(ExternalFile.operation,context);
		saveConfigurationInformations(context);

	}
	 //persist informations to avoid delete
	//save configuration informations like address port and protocol
    public static void saveConfigurationInformations(Context context){
		SharedPreferences sharedpreferences;
		sharedpreferences = context.getSharedPreferences(ExternalFile.configurationFile, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
	    
	    editor.putString("serverAddress", ConfigurationFile.serverAddress.toString());
	    editor.putString("baseUrl", ConfigurationFile.baseUrl.toString());
	    editor.putString("responseCode", ConfigurationFile.responseCode.toString());
	    editor.putString("operationCode", ConfigurationFile.operationCode.toString());
	    editor.putString("operationType", ConfigurationFile.operationType.toString());
	    editor.putString("action", ConfigurationFile.action.toString());

	    editor.commit();
	   
	}
}
