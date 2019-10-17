package mshopmobile.app.com.videostreaming.soap;

public class SoapRequest {
    // GetDeviceInformation soap Request
    public static String  getDeviceInformationRequest(String userName, String pwd,  String nonce, String currentDate){
        String request = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"\n" +
                "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "xmlns:tt=\"http://www.onvif.org/ver10/schema\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"\n"+
                "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
                "<soap12:Header>\n" +
                "<wsse:Security>\n" +
                "<wsse:UsernameToken wsu:Id=\"User\">\n" +
                "<wsse:Username>"+userName.trim()+"</wsse:Username>\n" +
                "<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">"+pwd.trim()+"</wsse:Password>\n"+
                "<wsse:Nonce>"+nonce.trim()+"</wsse:Nonce>\n" +
                "<wsu:Created>"+currentDate+"</wsu:Created>\n" +
                "</wsse:UsernameToken>\n" +
                "</wsse:Security>\n" +
                "</soap12:Header>\n" +
                "<soap12:Body>\n" +
                "<GetDeviceInformation xmlns=\"http://www.onvif.org/ver10/device/wsdl\">\n" +
                "</GetDeviceInformation>\n" +
                "</soap12:Body>\n" +
                "</soap12:Envelope>";
        return  request.trim();
    };

    // GetProfilesInformations soap Request
    public static  String getProfilesRequest(String userName, String pwd, String nonce,String currentDate){
        String request = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope\n" +
                "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "    xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" >\n" +
                "    <soap:Header>\n" +
                "        <wsse:Security\n" +
                "            xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n" +
                "            <wsse:UsernameToken\n" +
                "                xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
                "                <wsse:Username>"+userName.trim()+"</wsse:Username>\n" +
                "                <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">"+pwd.trim()+"</wsse:Password>\n" +
                "                <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">"+nonce.trim()+"</wsse:Nonce>\n" +
                "                <wsu:Created>"+currentDate.trim()+"</wsu:Created>\n" +
                "            </wsse:UsernameToken>\n" +
                "        </wsse:Security>\n" +
                "    </soap:Header>\n" +
                "    <soap:Body>\n" +
                "        <GetProfiles\n" +
                "            xmlns=\"http://www.onvif.org/ver10/media/wsdl\"/>\n" +
                "        </soap:Body>\n" +
                "    </soap:Envelope>";
        return  request.trim();
    };

    // Get streaming soap request
    public static String getStreamingRequest(String userName, String pwd, String profileToken,String nonce,String currentDate){
        String request = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope\n" +
                "    xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"\n" +
                "    xmlns:trt=\"http://www.onvif.org/ver10/media/wsdl\"\n" +
                "    xmlns:tt=\"http://www.onvif.org/ver10/schema\">\n" +
                "        <soap:Header>\n" +
                "            <wsse:Security\n" +
                "                xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">\n" +
                "                <wsse:UsernameToken\n" +
                "                    xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
                "                    <wsse:Username>"+userName.trim()+"</wsse:Username>\n" +
                "                    <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest\">"+pwd.trim()+"</wsse:Password>\n" +
                "                    <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">"+nonce.trim()+"</wsse:Nonce>\n" +
                "                    <wsu:Created>"+currentDate.trim()+"</wsu:Created>\n" +
                "                </wsse:UsernameToken>\n" +
                "            </wsse:Security>\n" +
                "        </soap:Header>\n" +
                "\t<soap:Body>\n" +
                "        <trt:GetStreamUri>\n" +
                "            <trt:StreamSetup>\n" +
                "                <tt:Stream>RTP-Unicast</tt:Stream>\n" +
                "                <tt:Transport>\n" +
                "                    <tt:Protocol>UDP</tt:Protocol>\n" +
                "                </Transport>\n" +
                "            </trt:StreamSetup>\n" +
                "            <trt:ProfileToken>"+profileToken.trim()+"</trt:ProfileToken>\n" +
                "        </trt:GetStreamUri>\n" +
                "    </soap:Body>\n" +
                "</soap:Envelope>";
        return  request.trim();
    };
}
