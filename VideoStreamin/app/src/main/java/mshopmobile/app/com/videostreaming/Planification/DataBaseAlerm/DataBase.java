package mshopmobile.app.com.videostreaming.Planification.DataBaseAlerm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import mshopmobile.app.com.videostreaming.Planification.Alarm;


/**
 * Created by Blaise Siani  on 19/11/15.
 */
public class DataBase extends SQLiteOpenHelper {

    static DataBase instance = null;
    static SQLiteDatabase database = null;

    static final String DATABASE_NAME = "DB";
    static final int DATABASE_VERSION = 1;

    public static final String ALARM_TABLE = "alarm";
    public static final String COLUMN_ALARM_ID = "_id";
    public static final String COLUMN_ALARM_TIME = "alarm_time";
    public static final String COLUMN_ALARM_DAYS = "alarm_days";

    public static void init(Context context) {
        if (null == instance) {
            instance = new DataBase(context);
        }
    }

    public static SQLiteDatabase getDatabase() {
        if (null == database) {
            database = instance.getWritableDatabase();
        }
        return database;
    }

    public static void deactivate() {
        if (null != database && database.isOpen()) {
            database.close();
        }
        database = null;
        instance = null;
    }
    public static ArrayList<Integer> create(Alarm alarm) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ALARM_TIME, alarm.getAlarmTimeString());

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(bos);
            oos.writeObject(alarm.getDays());
            byte[] buff = bos.toByteArray();
            cv.put(COLUMN_ALARM_DAYS, buff);
        } catch (Exception e){
        }
        getDatabase().insert(ALARM_TABLE, null, cv);
        return mappingDay(alarm.getDays());
    }
    public static int update(Alarm alarm) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ALARM_TIME, alarm.getAlarmTimeString());

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(bos);
            oos.writeObject(alarm.getDays());
            byte[] buff = bos.toByteArray();

            cv.put(COLUMN_ALARM_DAYS, buff);

        } catch (Exception e){
        }

        return getDatabase().update(ALARM_TABLE, cv, "_id=" + alarm.getId(), null);
    }


    public static int deleteEntry(Alarm alarm){
        return deleteEntry(alarm.getId());
    }

    public static int deleteEntry(int id){
        return getDatabase().delete(ALARM_TABLE, COLUMN_ALARM_ID + "=" + id, null);
    }

    public static int deleteAll(){

        return getDatabase().delete(ALARM_TABLE, "1", null);
    }
    public static Alarm getAlarm(int id) {
        // TODO Auto-generated method stub
        String[] columns = new String[] {
                COLUMN_ALARM_ID,
                COLUMN_ALARM_TIME,
                COLUMN_ALARM_DAYS,

        };
        Cursor c = getDatabase().query(ALARM_TABLE, columns, COLUMN_ALARM_ID+"="+id, null, null, null,
                null);
        Alarm alarm = null;

        if(c.moveToFirst()){

            alarm =  new Alarm();
            alarm.setId(c.getInt(0));
            alarm.setAlarmTime(c.getString(1));
            byte[] repeatDaysBytes = c.getBlob(2);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(repeatDaysBytes);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Alarm.Day[] repeatDays;
                Object object = objectInputStream.readObject();
                if(object instanceof Alarm.Day[]){
                    repeatDays = (Alarm.Day[]) object;
                    alarm.setDays(repeatDays);
                }
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //alarm.setAlarmName(c.getString(8));
        }
        c.close();
        return alarm;
    }

    public static Cursor getCursor() {
        // TODO Auto-generated method stub
        String[] columns = new String[] {
                COLUMN_ALARM_ID,
                COLUMN_ALARM_TIME,
                COLUMN_ALARM_DAYS

        };
        return getDatabase().query(ALARM_TABLE, columns, null, null, null, null,
                null);
    }

    DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ALARM_TABLE + " ( "
                        + COLUMN_ALARM_ID + " INTEGER primary key autoincrement,"
                        + COLUMN_ALARM_TIME + " TEXT NOT NULL,"
                        + COLUMN_ALARM_DAYS + " BLOB NOT NULL" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ALARM_TABLE);
        onCreate(db);
    }

    public static List<Alarm> getAll() {
        List<Alarm> alarms = new ArrayList<Alarm>();
        Cursor cursor = DataBase.getCursor();
        if (cursor.moveToFirst()) {

            do {

                Alarm alarm = new Alarm();
                alarm.setId(cursor.getInt(0));
                alarm.setAlarmTime(cursor.getString(1));
                byte[] repeatDaysBytes = cursor.getBlob(2);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                        repeatDaysBytes);
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(
                            byteArrayInputStream);
                    Alarm.Day[] repeatDays;
                    Object object = objectInputStream.readObject();
                    if (object instanceof Alarm.Day[]) {
                        repeatDays = (Alarm.Day[]) object;
                        alarm.setDays(repeatDays);
                    }
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //alarm.setAlarmName(cursor.getString());

                alarms.add(alarm);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return alarms;
    }

    public static ArrayList<Integer> mappingDay(Alarm.Day[] days){
        ArrayList<Integer> dayid = new ArrayList<Integer>();
        for (Alarm.Day d :days){
            if (d.equals(Alarm.Day.MONDAY)){
                dayid.add(1);
            }
            if (d.equals(Alarm.Day.TUESDAY)){
                dayid.add(2);
            }
            if (d.equals(Alarm.Day.WEDNESDAY)){
                dayid.add(3);
            }
            if (d.equals(Alarm.Day.THURSDAY)){
                dayid.add(4);
            }
            if (d.equals(Alarm.Day.FRIDAY)){
                dayid.add(5);
            }
            if (d.equals(Alarm.Day.SATURDAY)){
                dayid.add(6);
            }
            if (d.equals(Alarm.Day.SUNDAY)){
                dayid.add(7);
            }
        }
        Log.e("list of days", dayid.toString());
        return  dayid;
    }
}
