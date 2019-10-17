package util;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import  util.UtilsInformations;

public class UtilsInformations {

    private final SecureRandom nonceRandom;
    private String nonce;
    private String ucTime;
    private String pwd;

    public String getNonce() {
        return nonce;
    }

    public String getUcTime() {
        return ucTime;
    }


    public UtilsInformations(String pwd ) {
        this.pwd = pwd;
        this.nonceRandom = new SecureRandom();
        // Get Time Current Time Zone
        Date dt = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String strUTCDate = dateFormatter.format(dt);
        this.ucTime = strUTCDate;

        // Get Nonce Value
        String nonceStr =  String.valueOf(nonceRandom.nextInt(ReturnedCode.NUMBERRAMDOM1)*ReturnedCode.NUMBERRAMDOM2);
        String nonceNew = null;
        try {
            nonceNew = Base64.encodeToString(nonceStr.getBytes("UTF-8"),Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.nonce = nonceNew;
    }

    public String generatePassword(){
        byte[] decodeValue = Base64.decode(this.nonce, Base64.DEFAULT);
        String decodeValueNew =  new String(decodeValue);
        String value = decodeValueNew+this.ucTime+this.pwd;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(value.getBytes());
        String encrypt =  null;
        encrypt= Base64.encodeToString(messageDigest,Base64.DEFAULT);
        return  encrypt;
    }

    public String  generateGoodRtsp(String url,String pwd, String userName){
        String url1 = url.split("//")[0];
        String url2 = url.split("//")[1];
        String rtspUrl = url1+"//"+userName+":"+pwd+"@"+url2;
        return  rtspUrl;
    }
}
