package com.example.haresh.dinero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by ajayrahul on 17/3/16.
 */
public class Transaction extends Activity {

    EditText amount ,details;
    Button b;
    RadioButton d,w;
    FundDatabase fdb;
    String dts,amt,fname;
    int trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fundtransaction);
        amount= (EditText) findViewById(R.id.transaction);
        fdb= new FundDatabase(Transaction.this);
        details= (EditText) findViewById(R.id.details);
        b= (Button) findViewById(R.id.checkout);
        d= (RadioButton) findViewById(R.id.deposit);
        w= (RadioButton) findViewById(R.id.withdraw);
        fname = getIntent().getExtras().getString("dfname");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dts = details.getText().toString();
                amt = amount.getText().toString();
                trans= Integer.parseInt(amt);
                if(w.isChecked())
                {
                    trans *=-1;
                }
                else if ((!d.isChecked())&&(!w.isChecked()))
                {
                    Toast.makeText(Transaction.this,"Please select an option",Toast.LENGTH_SHORT);
                }
                funddata fd = new funddata(Helpers.user_name,fname,trans,dts);
                Intent n = new Intent(Transaction.this,Details.class);
                n.putExtra("type","t");
                n.putExtra("newtrans",fname);
                startActivity(n);
                finish();





            }
        });

    }


    public void onBackPressed() {
        Intent n = new Intent(Transaction.this,Details.class);
        startActivity(n);
        finish();

    }
}
