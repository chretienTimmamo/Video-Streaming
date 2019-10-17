package util.WebService;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class AppelService {
	private WebServiceConfig wsConf; // WebService config Wrapper object

	SoapObject request;

	public AppelService() {
		wsConf = new WebServiceConfig();
		// request = new SoapObject(wsConf.getNamespace(),
		// wsConf.getMethod_name());
	}

	public WebServiceConfig getWsConf() {
		return wsConf;
	}

	public SoapObject getRequest() {
		return request;
	}

	public void renewRequest(String method_name) {
		wsConf.setMethod_name(method_name);
		request = new SoapObject(wsConf.getNamespace(), wsConf.getMethod_name());
	}

	public String execute(boolean timeoutexcep, boolean httpexcep,
			boolean genexcep) {
		// allElts is used to know if there's a total count number in the
		// eventual list of elements returned. (false -> getRange.. ; true ->
		// others)
		timeoutexcep = false;
		httpexcep = false;
		genexcep = false;
		String jsonResult = "";

		try {
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					wsConf.getUrl());

			Log.e("wsConf.getSoap_action()", wsConf.getSoap_action());
			Log.e("url", wsConf.getUrl());
			Log.e("getNamespace", wsConf.getNamespace());
			// Log.e("envelope", envelope.bodyIn.toString());

			androidHttpTransport.call(wsConf.getSoap_action(), envelope);

			// Get the SoapResult from the envelope body.
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			jsonResult = response.toString();

		} catch (SocketTimeoutException e) {
			timeoutexcep = true;
			jsonResult= "";
			Log.e("exeption", "", e);

		} catch (UnknownHostException e) {
			httpexcep = true;
			jsonResult= "";
			Log.e("exeption", "", e);
			e.printStackTrace();
		} catch (Exception e) {
			genexcep = true;
			jsonResult= "";
			Log.e("exeption", "", e);
			e.printStackTrace();
		}
		return jsonResult;
	}
}
