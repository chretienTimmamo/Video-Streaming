package util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class ParseXmlResponse {
    public static String parseXml(String xml){
        XmlPullParserFactory factory = null;
        String urlStreaming = null;
        try {
            factory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        factory.setNamespaceAware(true);
        XmlPullParser xpp = null;
        try {
            xpp = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        try {
            xpp.setInput( new StringReader( xml ) );
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        int eventType = 0;
        try {
            eventType = xpp.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        while (eventType != XmlPullParser.END_DOCUMENT) {
           if(eventType == XmlPullParser.START_TAG && xpp.getName().equals("MediaUri")) {
             try {
                 eventType = xpp.next();
                 if(xpp.getName().equals("Uri")) {
                     eventType = xpp.next();
                     if(eventType == XmlPullParser.TEXT) {
                         urlStreaming = xpp.getText();
                         return  urlStreaming;
                     }
                 }
               } catch (IOException e) {
                   e.printStackTrace();
               } catch (XmlPullParserException e) {
                   e.printStackTrace();
               }
            }
            try {
                eventType = xpp.next();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
    }
    return urlStreaming;

    }
}
