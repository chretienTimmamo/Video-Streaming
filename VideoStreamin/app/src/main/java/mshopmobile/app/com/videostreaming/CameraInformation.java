package mshopmobile.app.com.videostreaming;

/**
 * Created by root on 25/03/16.
 */

public class CameraInformation {
    public String cameraName;
    public String cameraStreet;
    public String cameraFeed;
    public String cameraID;
    public String cameraUrl;
    public String cameraType;

    public CameraInformation(String cameraName, String cameraStreet, String cameraFeed, String cameraID, String cameraUrl, String cameraType) {
        this.cameraName = cameraName;
        this.cameraStreet = cameraStreet;
        this.cameraFeed = cameraFeed;
        this.cameraID = cameraID;
        this.cameraUrl = cameraUrl;
        this.cameraType = cameraType;
    }

    public String getCameraType() {
        return cameraType;
    }

    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getCameraStreet() {
        return cameraStreet;
    }

    public void setCameraStreet(String cameraStreet) {
        this.cameraStreet = cameraStreet;
    }

    public String getCameraFeed() {
        return cameraFeed;
    }

    public void setCameraFeed(String cameraFeed) {
        this.cameraFeed = cameraFeed;
    }

    public String getCameraID() {
        return cameraID;
    }

    public void setCameraID(String cameraID) {
        this.cameraID = cameraID;
    }

    public String getCameraUrl() {
        return cameraUrl;
    }

    public void setCameraUrl(String cameraUrl) {
        this.cameraUrl = cameraUrl;
    }
}
