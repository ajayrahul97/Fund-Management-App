package com.example.haresh.dinero;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by ajayrahul on 19/3/16.
 */
public class MyProfile extends Activity {
    TextView myname,email;
    SQLiteDatabase db;
    FundDatabase fd;
    String array[];
    String array2[];
    String text[];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile);
        myname = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);
        fd =new FundDatabase(MyProfile.this);
        myname.setText("Name :"+Helpers.user_name);
        email.setText("Email Id : "+Helpers.user_email);
        populateListView();
    }

    private void populateListView() {

        db=openOrCreateDatabase("FundInfoDatabase", Context.MODE_PRIVATE, null);

        Cursor crs =null;
        crs = db.rawQuery("SELECT * FROM FUNDINFO  ", null);
        array= new String[crs.getCount()];
        int i = 0;

        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("fund"));
            int total =fd.getsumuser(uname,Helpers.user_name);
            String t= Integer.toString(total);
//            array2[i]=t;
            array[i] = uname+" "+total;
//            text[i]= uname+"    "+ total;
            i++;
        }


        // TODO: CHANGE THE [[ to a less than, ]] to greater than.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,R.layout.data,array); // Context for the activity. R.layout.da_item, // Layout to use (create) myItems); // Items to be displayed // Configure the list view.
        ListView list = (ListView) findViewById(R.id.listview3);
        list.setAdapter(adapter);
    }
}
