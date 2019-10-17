package SimpleClass;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class PhoneInfos {
    boolean isDualSim;

    String phoneId;

    String mark;
    int simNumber;
    String version;
    public PhoneInfos(String phoneId){

    	this.phoneId = phoneId;

    }
   
	public PhoneInfos() {
		// TODO Auto-generated constructor stub
	}


	
	public String getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

    	
	public static String getDeviceIdTm(Context context)
	{
	    TelephonyManager tm=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
	    return tm.getDeviceId();
	}
	
	public static String getDeviceIdAndroid(Context context)
	{
	    return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}
    
	public static String getDeviceIdPseudo(Context context)
	{
	     String tstr="";
	     if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
	         tstr+= Build.SERIAL;
	         tstr += "::" + (Build.PRODUCT.length() % 10) + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10);
	         return tstr;
	    }
	    return tstr; 
	}
	
	//return the deviceID
	public String getDeviceIdUnique(Context context)
	 {
	     try {
	         String a = getDeviceIdTm(context);
	         String b = getDeviceIdAndroid(context);
	         String c = getDeviceIdPseudo(context);

	         if (a!=null && a.length()>0 && a.replace("0", "").length()>0){ 
	             
	        	 this.phoneId = a;
	        	 return a;
	             
	         }
	         else if (b!=null && b.length()>0 && b.equals("9774d56d682e549c")==false){ 
	           
	        	 this.phoneId = b;
	        	 
	        	 return b;
	             
	         }
	         else if (c!=null && c.length()>0){ 
	          
	        	 this.phoneId = c;
	        	 return c;
	         
	         }    
	         else{
	        	 this.phoneId = "";
	        	 return "";
	             
	         }
	     }
	     catch(Exception ex)
	     {
	         return "";
	     }
	 }
	
	
	 
	 public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public static String getAPIVerison() {

	    float f=1f;
	    try {
	        StringBuilder strBuild = new StringBuilder();
	        strBuild.append(android.os.Build.VERSION.RELEASE.substring(0, 2));
	        f= Float.valueOf(strBuild.toString());
	    } catch (NumberFormatException e) {
	        //Log.e("myApp", "error retriving api version" + e.getMessage());
	    	return "";
	    }

	    return f+"";
	 }

	/*
	 public String getDeviceName(){
		 String manufacturer = Build.MANUFACTURER;
		 String model = Build.MODEL;
		 
		 if(model.startsWith(manufacturer)){
			 
			 return Capitalize(model);
		 }
	     
		 return Capitalize(manufacturer)+" "+model;
		 
	 }
    */
	/*
	 * 
	 * 
	 * performing the following  during activity starting:
	 * - if it is the first time for a user:
	 *    -ask him to provide informations (personal and phone infos)
	 *      -if the sim Number isn't readable then ask him to enter it manually
	 *    -save phone information locally after having save informations remotely
	 * 
	 *  - If the apps is booting then:      
	 * 
	 *      -read phone informations (after 5 sec)
	 * 
	 *      - then if at least one sim card has been changed, then alert the 
	 * alertNumber 
	 * 
	 * 
	 * /
	 */
}
