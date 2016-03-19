package com.example.haresh.dinero;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "MainActivity";

    SQLiteDatabase db;
    FundNamedb f;

    String array[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f=new FundNamedb(MainActivity.this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(Helpers.main_visited!=1) {
            populateListView();
            registerClickCallback();
        }

        View header=navigationView.getHeaderView(0);
        TextView _name = (TextView) header.findViewById(R.id.nav_name);
        TextView _email = (TextView) header.findViewById(R.id.nav_email);

        if (_name != null) {
            _name.setText(Helpers.user_name);
        }
        if (_email != null) {
            _email.setText(Helpers.user_email);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
    //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
    //                        .setAction("Action", null).show();
                    Intent i = new Intent(MainActivity.this,CreateFund.class);
                    startActivity(i);
                    finish();


                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void populateListView() {

        db=openOrCreateDatabase("FundListDatabase", Context.MODE_PRIVATE, null);

        Cursor crs =null;
        crs = db.rawQuery("SELECT * FROM FUNDLIST", null);
        array= new String[crs.getCount()];
        int i = 0;
        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("fund"));
            array[i] = uname;

            i++;
        }



        // Create list of items
//        String[] myItems = {"Blue", "Green", "Purple", "Red"}; // Build Adapter
        // TODO: CHANGE THE [[ to a less than, ]] to greater than.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,R.layout.data,array); // Context for the activity. R.layout.da_item, // Layout to use (create) myItems); // Items to be displayed // Configure the list view.
        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
// TODO: CHANGE THE [[ to a less than, ]] to greater than.
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String fund_name =  textView.getText().toString();
                Intent d = new Intent(MainActivity.this,Details.class);
                d.putExtra("F",fund_name);
                d.putExtra("type","m");
                startActivity(d);
            }
        });

    }
    int count =0;
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            count++;
            if(count==2){
                finish();
            }
            else{
                Toast.makeText(MainActivity.this,"Press Once More To Exit",Toast.LENGTH_SHORT);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_fund) {
            // Handle the camera action
            Intent i = new Intent(MainActivity.this,CreateFund.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_change_password) {

        } else if (id == R.id.nav_change_picture) {

            Intent t= new Intent(MainActivity.this,MyProfile.class);
            startActivity(t);
            finish();




        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_share) {
            // TODO Auto-generated method stub
            //Intent next= new Intent(getApplicationContext(),Menu.class);
            //startActivity(next);
            PackageManager pm=getPackageManager();
            try {

                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                //String text = "YOUR TEXT HERE"
                String text= "Hey Starwar Fan...check out this awesome app ......i foyu want it on your smartphone ,it only costs $2.99....contact Ajay!";
                ;

                PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                //Check if package exists or not. If not then code
                //in catch block will be called
                waIntent.setPackage("com.whatsapp");

                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(waIntent, "Share with"));

            } catch (PackageManager.NameNotFoundException e) {

            }

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
