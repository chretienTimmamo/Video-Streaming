package mshopmobile.app.com.videostreaming.Planification;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maclover on 19/01/16.
 */

public class Planning implements Serializable {
    String time ;
    int id;
    public static final String PLANNING_TYPE ="planning_type";
    String daycode = "[1,2,3,4,5,6,7]";
    String type;
    String recordingFrom;
    String recordingTo;
    int cameraID;
    String streamCodec;
    String streamFormat;
    int periodStreamID;
    boolean status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDaycode() {
        return daycode;
    }

    public void setDaycode(String daycode) {
        this.daycode = mappingIntToStringDay(daycode);
    }

    public String getRecordingFrom() {
        return recordingFrom;
    }

    public void setRecordingFrom(String recordingFrom) {
        this.recordingFrom = recordingFrom;
    }

    public String getRecordingTo() {
        return recordingTo;
    }

    public void setRecordingTo(String recordingTo) {
        this.recordingTo = recordingTo;
    }

    public int getCameraID() {
        return cameraID;
    }

    public void setCameraID(int cameraID) {
        this.cameraID = cameraID;
    }

    public String getStreamCodec() {
        return streamCodec;
    }

    public void setStreamCodec(String streamCodec) {
        this.streamCodec = streamCodec;
    }

    public String getStreamFormat() {
        return streamFormat;
    }

    public void setStreamFormat(String streamFormat) {
        this.streamFormat = streamFormat;
    }

    public int getPeriodStreamID() {
        return periodStreamID;
    }

    public void setPeriodStreamID(int periodStreamID) {
        this.periodStreamID = periodStreamID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private Calendar alarmTime = Calendar.getInstance();


    public enum Day{
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY;

        @Override
        public String toString() {
            switch(this.ordinal()){
                case 0:
                    return "Sunday";
                case 1:
                    return "Monday";
                case 2:
                    return "Tuesday";
                case 3:
                    return "Wednesday";
                case 4:
                    return "Thursday";
                case 5:
                    return "Friday";
                case 6:
                    return "Saturday";
            }
            return super.toString();
        }

    }
    private Day[] days = {Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY};
    public enum Type {
        ARM,
        DEARM;
        @Override
        public String toString() {
            switch(this.ordinal()) {
                case 0:
                    return "arm";
                case 1:
                    return "dearm";
            }
            return super.toString();

        }
    }
    private  Type[] types = {Type.ARM, Type.DEARM};

    //map day of week to number
    public ArrayList<Integer> mappingDay(String days){
        ArrayList<Integer> dayid = new ArrayList<Integer>();
        if (days.equals("Every Day")){
            Collections.addAll(dayid, 1, 2, 3, 4, 5, 6, 7);
            Log.e("list of days", dayid.toString());
            return dayid;
        }else{
            String[] days1 = days.split(",");
            for (String d :days1){
                if (d.equals("Mon")){
                    dayid.add(1);
                }
                if (d.equals("Tue")){
                    dayid.add(2);
                }
                if (d.equals("Wed")){
                    dayid.add(3);
                }
                if (d.equals("Thu")){
                    dayid.add(4);
                }
                if (d.equals("Fri")){
                    dayid.add(5);
                }
                if (d.equals("Sat")){
                    dayid.add(6);
                }
                if (d.equals("Sun")){
                    dayid.add(7);
                }
            }
            Log.e("list of days", dayid.toString());
            //mappingIntToStringDay(dayid);
            return  dayid;
        }

    }


    //map day of week to number
    public String mappingDayString(String days){
        String dayid = "[";
        if (days.equals("Every Day")){
            dayid +="1,2,3,4,5,6,7]";
            Log.e("list of days", dayid.toString());
            return dayid;
        }else{
            String[] days1 = days.split(",");
            int i = 0;
            for (String d :days1){
                if (d.equals("Mon")){
                    i++;
                    if (i == days1.length){
                        dayid+="1";
                    }else
                     dayid+="1,";
                } if (d.equals("Tue")){
                    i++;
                    if (i == days1.length){
                        dayid+="2";
                    }else
                    dayid+="2,";
                } if (d.equals("Wed")){
                    i++;
                    if (i == days1.length){
                        dayid+="3";
                    }else
                    dayid+="3,";
                } if (d.equals("Thu")){
                    i++;
                    if (i == days1.length){
                        dayid+="4";
                    }else
                    dayid+="4,";
                }if (d.equals("Fri")){
                    i++;
                    if (i == days1.length){
                        dayid+="5";
                    }else
                    dayid+="5,";
                }if (d.equals("Sat")){
                    i++;
                    if (i == days1.length){
                        dayid+="6";
                    }else
                    dayid+="6,";
                } if (d.equals("Sun")){
                    i++;
                    if (i == days1.length){
                        dayid+="7";
                    }else
                    dayid+="7,";
                }

            }
            Log.e("list of days", dayid.toString());
            dayid +="]";

            return  dayid;
        }

    }
    //map a array of int to string represente the day of week

    public String mappingIntToStringDay(String list){
        String result = "";
      //  String result1 = list.substring(1,list.length());
        String result1 = list.replace("[", "");
        String[] dayid = result1.split(",");
        Log.e("liste des id est :", dayid.toString());

        if (result1.contains("1,2,3,4,5,6,7")){
            result = "Every Day";
            Log.e("day of week", result);
            return result;
        }else {
            if (result1.contains(",7")&& result1.contains("7")) {
                result +=","+"Sun" ;
            }else if (result1.contains("7")){
                result +="Sun";
            }
            if (result1.contains(",1")) {
                result +=",Mon";
            }else if ((result1.contains("1"))) {
                result +="Mon";
            }
            if (result1.contains(",2")) {
                result +=","+"Tue";
            }else if(result1.contains("2")){
                result +="Tue";
            }
            if (result1.contains(",3")) {
                result +=","+"Wed";
            }else if (result1.contains("3")) {
                result +="Wed";
            }
            if (result1.contains(",4")) {
                result +=","+"Thu";
            }else if (result1.contains("4")) {
                result +="Thu";
            }
            if (result1.contains(",5")) {
                result +=","+"Fri";
            }else if (result1.contains("5")) {
                result +="Fri";
            }
            if (result1.contains(",6")) {
                result +=","+"Sat";
            }else if (result1.contains("6")) {
                result +="Sat";
            }



            Log.e("day of week", result);
            return result;
        }

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Day[] getDays() {
        return days;
    }

    public void setDays(Day[] days) {
        this.days = days;
    }
    public Day[] setDayByString(String days ){
        List<Day> result = new LinkedList<Day>();
        if (days.equals("Every Day")){
           Day[] days1 = {Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY};
            setDays(days1);
            return getDays();
        }else{
            if (days.contains("Mon")){
                result.add(Day.MONDAY);
            }
            if (days.contains("Tue")){
                result.add(Day.TUESDAY);
            }
            if (days.contains("Wed")){
                result.add(Day.WEDNESDAY);
            }

            if (days.contains("Thu")){
                result.add(Day.THURSDAY);
            }
            if (days.contains("Fri")){
                result.add(Day.FRIDAY);
            }
            if (days.contains("Sat")){
                result.add(Day.SATURDAY);
            }
            if (days.contains("Sun")){
                result.add(Day.SUNDAY);
            }
            setDays(result.toArray(new Day[result.size()]));
        }
        return getDays();
    }
    public Type[] getTypes() {
        return types;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }

    /**
     * @return the alarmTime
     */
    public Calendar getAlarmTime() {
        if (alarmTime.before(Calendar.getInstance()))
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
        while(!Arrays.asList(getDays()).contains(Day.values()[alarmTime.get(Calendar.DAY_OF_WEEK)-1])){
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        return alarmTime;
    }


    /**
     * @return the alarmTime
     */
    public String getAlarmTimeString() {

        String time = "";
        if (alarmTime.get(Calendar.HOUR_OF_DAY) <= 9)
            time += "0";
        time += String.valueOf(alarmTime.get(Calendar.HOUR_OF_DAY));
        time += ":";

        /*
        if(alarmTime.get(Calendar.MINUTE )<= 29){
            time +='0';
            alarmTime.set(Calendar.MINUTE,00);
        }
        else{
            alarmTime.set(Calendar.MINUTE,30);
        }
        */
        time += String.valueOf(alarmTime.get(Calendar.MINUTE));
        return time;
    }
    /**
     * @param alarmTime
     *            the alarmTime to set
     */
    public void setAlarmTime(Calendar alarmTime) {
        this.alarmTime = alarmTime;
    }

    /**
     * @param alarmTime
     *            the alarmTime to set
     */
    public void setAlarmTime(String alarmTime) {

        String[] timePieces = alarmTime.split(":");

        Calendar newAlarmTime = Calendar.getInstance();
        newAlarmTime.set(Calendar.HOUR_OF_DAY,
                Integer.parseInt(timePieces[0]));
        newAlarmTime.set(Calendar.MINUTE, Integer.parseInt(timePieces[1]));
        newAlarmTime.set(Calendar.SECOND, 0);
        setAlarmTime(newAlarmTime);
    }

    public void addDay(Day day){
        boolean contains = false;
        for(Day d : getDays())
            if(d.equals(day))
                contains = true;
        if(!contains){
            List<Day> result = new LinkedList<Day>();
            for(Day d : getDays())
                result.add(d);
            result.add(day);
            setDays(result.toArray(new Day[result.size()]));
        }
    }

    public void removeDay(Day day) {

        List<Day> result = new LinkedList<Day>();
        for(Day d : getDays())
            if(!d.equals(day))
                result.add(d);
        setDays(result.toArray(new Day[result.size()]));
    }
    public String getRepeatDaysString() {
        StringBuilder daysStringBuilder = new StringBuilder();
        if(getDays().length == Day.values().length){
            daysStringBuilder.append("Every Day");
        }else{
            Arrays.sort(getDays(), new Comparator<Day>() {
                @Override
                public int compare(Day lhs, Day rhs) {

                    return lhs.ordinal() - rhs.ordinal();
                }
            });
            for(Day d : getDays()){
                switch(d){
                    case TUESDAY:
                    case THURSDAY:
//					daysStringBuilder.append(d.toString().substring(0, 4));
//					break;
                    default:
                        daysStringBuilder.append(d.toString().substring(0, 3));
                        break;
                }
                daysStringBuilder.append(',');
            }
            daysStringBuilder.setLength(daysStringBuilder.length()-1);
        }

        return daysStringBuilder.toString();
    }
}
