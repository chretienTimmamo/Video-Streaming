package util;

import android.app.ProgressDialog;

public class ExternalFile {

	public static String serverAddress ="serverAddress.js";
	public static String responseCode ="responseCodes.js";
	public static String operation="operationCodes.js";
	public static String globalMethod ="globalMethod.js";
	public static String imageList = "imageList";
	public static String userLogin = "userLogin";
	public static String configurationFile = "configurationFile";
	public static int  threadInitialTimeDelai=1000;
	public static int  threadTimeDelai=10000;
	
	public static int  vibrationTime=1000;
	public static int alertNotificationOperationCode =1000;

	public static int disarm =33;
	public static int armAtHome =32;
	public static int  armOutSideHome=31;

	public static int  oldNotificationNumber=0;

	//allow us to know if the login has been performed during the phone booting
	//or it has been done after the user has manually launch application
	public static boolean loginStartDuringBoot =false;
	
	public static ProgressDialog dialogAutoLogin =null;

	public static String[] notification= {"word cam","my Cam","record"};
	public static String[] notificationPeriod= {"today","yesterday","week","month","year","all"};
}

