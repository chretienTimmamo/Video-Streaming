package util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GlobalService {
	
	public static ArrayList<String> countryList =null;
	
	public static ArrayList<String> cityList =null;

	public static String userSession;
    
	/*
	public static String getUrl(String serviceName){
		
		JSONObject serverAddress = ConfigurationFile.serverAddress;
		JSONObject baseUrl = ConfigurationFile.baseUrl;
		String urlService;
		String baseUrlService;
		try {
			baseUrlService = baseUrl.getString(serviceName);
			urlService = serverAddress.getString("protocol")+serverAddress.getString("serverIpAddress")+":"+serverAddress.getString("port")+baseUrlService;
		
		} catch (Exception ex) {
	        ex.printStackTrace();
	        return "";
	    }
		return urlService;
	}
	*/
	public static String getUrl(String serviceName,Context context){
		
		JSONObject serverAddress = getServerAddress(context,"serverAddress");
		JSONObject baseUrl = getServerAddress(context,"baseUrl");
		String urlService;
		String baseUrlService;
		try {
			baseUrlService = baseUrl.getString(serviceName);
			urlService = serverAddress.getString("protocol")+serverAddress.getString("serverIpAddress")+":"+serverAddress.getString("port")+baseUrlService;
		
		} catch (Exception ex) {
	        ex.printStackTrace();
	        return "";
	    }
		return urlService;
	}
	
	public static boolean isEmpty(String data){
		 if(data==null || data.equalsIgnoreCase("") || data.equalsIgnoreCase("null")) {
	            return true;
	     }else{
	    	    return false;
	     }
	}


	public static JSONObject getServerAddress(Context context,String object){
		SharedPreferences sharedpreferences;
		sharedpreferences = context.getSharedPreferences(ExternalFile.configurationFile, Context.MODE_PRIVATE);

		//String serverAddressJson = sharedpreferences.getString("serverAddress","");//second parameter is necessary ie.,Value to return if this preference does not exist.
		String serverAddressJson = sharedpreferences.getString(object,"");//second parameter is necessary ie.,Value to return if this preference does not exist.
		if((serverAddressJson != null)&& (serverAddressJson !=""))
			try {

				return new JSONObject(serverAddressJson);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return null;
			}
		return null;
	}

	public static String getUserSession(Context context){
		SharedPreferences sharedpreferences;
		sharedpreferences = context.getSharedPreferences(ExternalFile.userLogin, Context.MODE_PRIVATE);

		String sessionID = sharedpreferences.getString("sessionID","");

		if(sessionID.equals(""))
			return  "";
		return sessionID;

	}

	public static String getUrlService(String serviceName,Context ctx){

		JSONObject serverAddress = GlobalService.getServerAddress(ctx,"serverAddress");

		JSONObject baseUrl = GlobalService.getServerAddress(ctx, "baseUrl");
		String url;
		String baseUrlLink;
		try {
			baseUrlLink = baseUrl.getString(serviceName);
			url = serverAddress.getString("protocol")+serverAddress.getString("serverIpAddress")+":"+serverAddress.getString("port")+baseUrlLink;

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}

		return  url;
	}

	public static String getUrlStreaming(String serviceName,Context ctx){

		JSONObject serverAddress = GlobalService.getServerAddress(ctx,"serverAddress");

		JSONObject baseUrl = GlobalService.getServerAddress(ctx, "baseUrl");
		String url;
		String baseUrlLink;
		try {
			baseUrlLink = baseUrl.getString(serviceName);
			url = serverAddress.getString("protocolStreaming")+serverAddress.getString("serverIpAddressStreaming")+":"+serverAddress.getString("portStreaming")+baseUrlLink;

		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}

		return  url;
	}

}
