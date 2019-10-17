package util.WebService;

public class WebServiceConfig {
	// public static String server_adr = "54.191.138.121"; // Tests server
	//public static String server_adr = "192.168.0.45"; // Boston
	// public static String server_adr = "54.201.157.23"; // production
	// private server_adr = "jobsbid.trucksanddirt.com";
	// public static String server_adr = "192.168.0.116"; // Local server
	 //public static String server_adr = "192.168.0.110"; // pour ma machine creolink
	 //public static String server_adr = "192.168.0.13"; // pour ma machine mtn
	// public static String server_adr = "192.168.0.115";
	public static String server_adr = "104.154.79.205"; // pour Notre Machine Online sur le serveur
	private String namespace;
	private String url;
	private String method_name;
	private String soap_action;

	public WebServiceConfig() {
		namespace = "http://serviceEssay/";
		url = "http://" + getServer_adr()
				+ ":8080/Webservices/WebServiceEssay?wsdl";
		method_name = "fois";
		soap_action = namespace + method_name;
	}

	public String getServer_adr() {
		return server_adr;
	}

	public void setServer_adr(String serve_adr) {
		WebServiceConfig.server_adr = serve_adr;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
		this.soap_action = "\"" + namespace + this.method_name + "\"";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String local_wsdl_url) {
		this.url = "http://" + getServer_adr() + ":8080/" + local_wsdl_url;
	}

	public String getMethod_name() {
		return method_name;
	}

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
		this.soap_action = this.namespace + method_name;
	}

	public String getSoap_action() {
		return soap_action;
	}

}
