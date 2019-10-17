package SimpleClass;

/**
 * Created by root on 08/06/16.
 */
public class Image {
    public String fileName;
    public String path;
    public String hours;
    public String date;

    public Image(String fileName, String path, String hours, String date) {
        this.fileName = fileName;
        this.path = path;
        this.hours = hours;
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
