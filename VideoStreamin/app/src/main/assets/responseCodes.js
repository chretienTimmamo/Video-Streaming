{									
	// copy updated JSONs from master text file to here
    "responseCode" :  {
										"ok"                        : 0,
										"homeNameExit"      	    : 302, 
										"sensorNameExit"      	    : 302, 
										"centralModuleExist"        : 303,	
										"centralModuleInProcessing" : 317,	
										//"centralModuleInProcessing" : 0,	
										// registration related errors are 01xx
										"regionNotSupported"      	: 100, 
										"addressError"				: 101,
										"streetError"				: 102,
										"localeError"				: 103,
										"marketingNameExists"		: 104,
										"companyExists"				: 105,
										"addressMissing"			: 106,
										"cityMissing"				: 107,
										"countryMissing"			: 108,
										"codeMissing"				: 109,
										"businessAddressError"		: 110,
										"bankAddressError"			: 111,
										"nameMissing"				: 112,
										"emailMissing"				: 113,
										"phoneMissing"				: 114,
										"pwdMissing"				: 115,
										"bankNameMissing"			: 116,
										"bankAddressMissing"		: 117,
										"bankCityMissing"			: 118,
										"bankCountryMissing"		: 119,
										"bankCodeMissing"			: 120,
										"ibanMissing"				: 121,
										"swiftMissing"				: 122,
										"localeMissing"				: 123,
										"businessNameMissing"		: 124,
										"gpsMissing"				: 125,
										"paymodeError"				: 126,
										"gpsCoordError"				: 127,
										"userExists"				: 128,
										"emailExists"				: 129,
										
										// login related errors are 03xx
										"loginWrong"              	: 300,
										"codeWrong"               	: 301,
										"codeRequired"            	: 302,
										//"userBanned"              	: 303,	
										"loginRequired"             : 9200,	

										// item-listing errors are 04xx
										"itemNameRequired"          : 400,	
										"listingModeRequired"       : 401,	
										"quantityRequired"       	: 402,
										"priceRequired"       		: 403,
										"itemExists"       			: 404,
										"imageExists"       		: 405,
										
										"noItem"                    : 406,
										"noMoreItems"               : 407,
										"noMoreOrder"               : 500,
										"noDelete"                  : 504,
										// tour related errors are 05xx
										"noTour"          			: 421,
										
										// Midend related errors are 1xxx
										
										// general backend errors are 2xxx
										"invalidRequest"			: 2000,
										"internalError"				: 2001,
										"permissionDenied"			: 2002,
										
										// session management protocols begin with 3xxx 
										"setSession"				: 3000,	
										"sessionExpired"			: 3002,	
										"sessionUnknown"			: 3003,
										"sessionNotUnique"			: 3004,
										"sessionMissing"			: 3005,
										
										"mispelling"                : 4000,
									   
										//optimization error are 6xxx
										
										"optimizationError"         : 601,
										"noOrderPool"               : 602,
										// network related errors begin with 7xxx
										"buyerExists"               : 7000, // invited buyer is already in the system
										"sellerExists"              : 7001,  // invited seller is already in the system
									   
										"regionError"               : 410,
										"noMoreBack"                : 423,
										"buyError"                  : 424,
										"buyCodeUsed"               : 425,
										"buyConfirmCode"            : 426,
										"inviteCodeUsed"            : 427,
										"promoCodeMissing"          : 428,
										"promoCodeNotValid"         : 429,
										"promoCodeAlreadyApplied"   : 430,
										"promoCodeExpired"          : 431,
										"promoCodeAlreadyUsed"      : 432,
										"refreshVersion"            : 1111    
										
   }

}
