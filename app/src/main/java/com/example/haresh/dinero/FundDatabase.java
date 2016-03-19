package com.example.haresh.dinero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ajayrahul on 16/3/16.
 */
public class FundDatabase extends SQLiteOpenHelper{

    private static final int DATABSE_VERSION = 1;
    private static final String DATABASE_NAME = "FundInfoDatabase",
            TABLE_NAME ="FUNDINFO",
            KEY_ID = "id",
            KEY_NAME = "name",
            KEY_FUNDNAME ="fund",
            KEY_TRANS="money",
            KEY_DETAILS="details"
                    ;


    public FundDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_FUNDNAME + " TEXT," + KEY_TRANS + " INTEGER," + KEY_DETAILS + " TEXT" + ")");
        
    }


    public funddata getuser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_FUNDNAME, KEY_TRANS}, KEY_NAME + "=?", new String[]{name}, null, null , null, null);
        if (cursor != null)
            cursor.moveToFirst();
        funddata fd = new funddata( cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),cursor.getString(4));
        return fd;
    }


    public void addfund (funddata fd) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(KEY_ID,fd.get_id());
        values.put(KEY_NAME, fd.get_name()); // Contact name
        values.put(KEY_FUNDNAME, fd.get_fundname()); //
        values.put(KEY_TRANS, fd.get_trans()); //
        values.put(KEY_DETAILS, fd.get_details()); //


        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        //sortTable();
    }

    public int getsum(String f) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur =null;
         cur = db.rawQuery("SELECT SUM(money) FROM FUNDINFO WHERE fund ='"+f+"' ", null);
        if (cur.moveToFirst()){
            return cur.getInt(0);


        }
        else return 0;
    }

    public int getsumuser(String f,String u) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur =null;
        cur = db.rawQuery("SELECT SUM(money) FROM FUNDINFO WHERE fund ='"+f+"' AND name = '"+u+"' ", null);
        if (cur.moveToFirst()){
            return cur.getInt(0);


        }
        else return 0;
    }


}
