package mshopmobile.app.com.videostreaming;

/**
 * Created by root on 08/06/16.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import SimpleClass.Image;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SecuritrixStreaming.db";

    public static final String IMAGE_TABLE_NAME = "image";
    public static final String IMAGE_COLUMN_ID = "id";
    public static final String IMAGE_COLUMN_FILENAME = "fileName";
    public static final String IMAGE_COLUMN_PATH = "path";
    public static final String IMAGE_COLUMN_HOURS = "hours";
    public static final String IMAGE_COLUMN_DATE = "date";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table image " +
                        "(id integer primary key, fileName text,path text,hours text, date text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS image");
        onCreate(db);
    }

    public boolean insertImage(String fileName, String path, String hours, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_COLUMN_FILENAME, fileName);
        contentValues.put(IMAGE_COLUMN_PATH, path);
        contentValues.put(IMAGE_COLUMN_HOURS, hours);
        contentValues.put(IMAGE_COLUMN_DATE,date);
        db.insert(IMAGE_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from image where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, IMAGE_TABLE_NAME);
        return numRows;
    }

    public boolean updateImage(Integer id,String fileName, String path, String hours, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_COLUMN_FILENAME,fileName);
        contentValues.put(IMAGE_COLUMN_PATH, path);
        contentValues.put(IMAGE_COLUMN_HOURS, hours);
        contentValues.put(IMAGE_COLUMN_DATE, date);

        db.update(IMAGE_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteImage (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(IMAGE_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    /*
    public ArrayList<String> getAllImage()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from image", null );

        res.moveToLast();

        while(res.isBeforeFirst() == false){
            array_list.add(res.getString(res.getColumnIndex(IMAGE_COLUMN_PATH)));
            res.moveToPrevious();
        }
        return array_list;
    }
    */
    public ArrayList<Image> getAllImage()
    {
        ArrayList<Image> array_list = new ArrayList<Image>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from image", null );

        res.moveToLast();

        while(res.isBeforeFirst() == false){
            array_list.add(new Image(res.getString(res.getColumnIndex(IMAGE_COLUMN_FILENAME)),res.getString(res.getColumnIndex(IMAGE_COLUMN_PATH)),res.getString(res.getColumnIndex(IMAGE_COLUMN_HOURS)),res.getString(res.getColumnIndex(IMAGE_COLUMN_DATE))));
            res.moveToPrevious();
        }
        return array_list;
    }

    public static boolean hasSDCard() { // SD????????
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }
    public static String getSDCardPath() {
        File path = Environment.getExternalStorageDirectory();
        return path.getAbsolutePath();
    }

}
