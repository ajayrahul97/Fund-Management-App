package com.example.haresh.dinero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.TestCase;


/**
 * Created by ajayrahul on 17/3/16.
 */
public class Details extends Activity{

    TextView grpname,balance;
    Button add,make;
    FundDatabase fd;
    int total;
    String s;
    String type;
    String array[];
    SQLiteDatabase db;
    UserDatabase usd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fund_details);
        fd =new FundDatabase(Details.this);
       grpname = (TextView)findViewById(R.id.Fund_name);
       balance = (TextView)findViewById(R.id.total);
        add = (Button)findViewById(R.id.add);
        make = (Button)findViewById(R.id.make);

        type= getIntent().getExtras().getString("type");

        if(type.contentEquals("c")) {
            s = getIntent().getExtras().getString("fund_name");
        }
        else if(type.contentEquals("m"))
        {
            s = getIntent().getExtras().getString("F");

        }
        else if(type.contentEquals("t"))
        {
            s = getIntent().getExtras().getString("newtrans");

        }
        grpname.setText("FundGroup: "+s);
        total = fd.getsum(s);
        String s1= Integer.toString(total);
        balance.setText("Current Balance : "+s1);
        populateListView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Details.this, ListUser.class);
                startActivity(i);
                finish();
            }
        });

        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Details.this, Transaction.class);
                i.putExtra("dfname",s);
                startActivity(i);
                finish();


            }
        });


    }
    private void populateListView() {

        db=openOrCreateDatabase("MemberFundDatabase", Context.MODE_PRIVATE, null);

        Cursor crs =null;
        crs = db.rawQuery("SELECT * FROM MEMBERFUNDINFO  ", null);
        array= new String[crs.getCount()];
        int i = 0;
        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("name"));
            array[i] = uname;

            i++;
        }


        // TODO: CHANGE THE [[ to a less than, ]] to greater than.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,R.layout.data,array); // Context for the activity. R.layout.da_item, // Layout to use (create) myItems); // Items to be displayed // Configure the list view.
        ListView list = (ListView) findViewById(R.id.listview2);
        list.setAdapter(adapter);
    }


    public void onBackPressed() {
        Intent n = new Intent(Details.this,MainActivity.class);
        startActivity(n);
        finish();

    }


}
