 {
				
	serverAddress:  {

						 //"serverIpAddress":'192.168.0.28',
                         //"serverIpAddress":'192.168.1.2',

                          // "serverIpAddress":'192.168.0.21',
                         //"serverIpAddress":'192.168.0.10',
                         //"serverIpAddress":'securitrix.com',
					     //"serverIpAddress":'23.251.158.168',
					     // @Author: Chretien Timmamo -> Serveur En ligne Vidéo Securitrix -> http://104.154.79.205
					     "serverIpAddress":'104.154.79.205',
				         "port":'8080',				
				         "protocol":"http://",
				         "protocolWebSocket":"ws://"		
			        },
				 
	uri          :  {

										 "login"                    : "/streaming.Service/resources/userService/login",
										 "logout"                   : "/streaming.Service/resources/userService/logout",
										 "registration"             : "/HomeTrack.Restfull/resources/userService/createAccount/",
										 "notificationList"         : "/HomeTrack.Restfull/resources/configServiceSFLK1/notificationList",
										 "notificationListNumber"   : "/HomeTrack.Restfull/resources/configServiceSFLK1/notificationListNumber",
										 "markNotificationsAsView"  : "/HomeTrack.Restfull/resources/configServiceSFLK1/markNotificationsAsView",
			 					         "updateAccountInformations":"/HomeTrack.Restfull/resources/userService/updateAccount",
                                         "accountInformations"      :"/HomeTrack.Restfull/resources/userService/listAccountInformation",
										 "imageList" 				:"/homeTrack.Image/resources/imageService/imageList",
										 "zoneList" 				:"/streaming.Service/resources/userService/listHomeOfUser",
										 "cameraList" 				:"/streaming.Service/resources/imageService/listCameraOfHome",
										 "recordList" 				:"/streaming.Service/resources/imageService/recordList",
										 "VideoView_error_text_unknown" :"Sorry, this video cannot be played.",
										  //plannification module

                                          //add a periode
                                    	  "creatPeriod": "/streaming.Service/resources/imageService/createPeriod",
                                    	  //deleted periode
                                    	  "deletedPeriod": "/streaming.Service/resources/imageService/deletePeriod",
                                    	  //edited period
                                    	  "editPeriod": "/streaming.Service/resources/imageService/editPeriod",
                                    	  //retrieved all periods
                                    	  "allPeriod": "/streaming.Service/resources/imageService/PeriodOfUser",
                                    	  // deleted all periods
                                    	  "deleteAllPeriod": "/streaming.Service/resources/imageService/deletePeriodsOfCamera",
                                    	  "imageList"                : "/vpl_catalogue.php",
                                    	  "cameraCheckAvailability"  : "/streaming.Service/resources/imageService/canIviewTheCamera",
                                    	  "cameradisableAvailability"  : "/streaming.Service/resources/imageService/disableUserCamera"


			}	
			 
   }
    

