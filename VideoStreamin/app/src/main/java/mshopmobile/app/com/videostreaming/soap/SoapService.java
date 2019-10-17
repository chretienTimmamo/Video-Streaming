package mshopmobile.app.com.videostreaming.soap;

import android.util.Log;

import util.ParseXmlResponse;
import util.ReturnedCode;
import util.UtilsInformations;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SoapService {
    private String userName;
    private String password;
    private String ipAddress;

    public SoapService(String userName, String password,String ipAddress) {
        this.userName = userName;
        this.password = password;
        this.ipAddress = ipAddress;
    }

    public void  getDeviceInformation() {
        //Get necessary informations
        String url = "http://"+this.ipAddress+"/onvif/services";
        UtilsInformations utilsInfos = new UtilsInformations(password);
        String encryPwd = utilsInfos.generatePassword();
        String  requestNew = SoapRequest.getDeviceInformationRequest(this.userName,encryPwd,utilsInfos.getNonce(),utilsInfos.getUcTime());
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/soap+xml; charset=utf-8;");
        RequestBody body = RequestBody.create(mediaType, requestNew.trim());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type","text/xml; charset=utf-8")
                .build();
        try {
            System.out.println("Out:"+requestNew);
            Response response = client.newCall(request).execute();
            Log.d("Response", String.valueOf(response.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  getProfilesInformation() {
        //Get necessary informations
        String url = "http://"+this.ipAddress+"/onvif/services";
        UtilsInformations utilsInfos = new UtilsInformations(password);
        String encryPwd = utilsInfos.generatePassword();
        String  requestNew = SoapRequest.getProfilesRequest(this.userName,encryPwd,utilsInfos.getNonce(),utilsInfos.getUcTime());
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/soap+xml; charset=utf-8;");
        RequestBody body = RequestBody.create(mediaType, requestNew.trim());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type","text/xml; charset=utf-8")
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("Response", String.valueOf(response.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String  getStreamingInformation() {
        //Get necessary informations
        String url = "http://"+this.ipAddress+"/onvif/services";
        String profileToken = "thirdStream_Profile_Token";
        UtilsInformations utilsInfos = new UtilsInformations(password);
        String encryPwd = utilsInfos.generatePassword();
        String  requestNew = SoapRequest.getStreamingRequest(this.userName,encryPwd,profileToken,utilsInfos.getNonce(),utilsInfos.getUcTime());
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/soap+xml; charset=utf-8;");
        RequestBody body = RequestBody.create(mediaType, requestNew.trim());
        Request request = new Request.Builder().url(url)
                .post(body)
                .addHeader("Content-Type","text/xml; charset=utf-8")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response.code()== ReturnedCode.OKRESPONSE){
                String newResponse = response.body().string();
                String rtspUri =   ParseXmlResponse.parseXml(newResponse);
                return utilsInfos.generateGoodRtsp(rtspUri,this.password,this.userName);
              };
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
