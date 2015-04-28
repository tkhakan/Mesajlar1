package com.hakan.mesajlar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MesajYaz extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_yaz);
        
       final EditText telno=(EditText) findViewById(R.id.editText1);
       final  EditText mesaj=(EditText) findViewById(R.id.editText2);
        ImageButton gonder=(ImageButton) findViewById(R.id.imageButton1);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        gonder.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				android.telephony.SmsManager sms=android.telephony.SmsManager.getDefault();
				sms.sendTextMessage(telno.getText().toString(),null, mesaj.getText().toString(),null, null);
				Toast.makeText(getBaseContext(),  "MESAJINIZ GÖNDERÝLÝYOR", Toast.LENGTH_LONG).show();
				Toast.makeText(getBaseContext(),  "ÝLETÝLDÝ", Toast.LENGTH_LONG).show();
			}
		});
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.mesaj_yaz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		
		switch (item.getItemId()) {
		
		
		
			
		case android.R.id.home:
	        Intent upIntent = NavUtils.getParentActivityIntent(this);
	        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
	            // This activity is NOT part of this app's task, so create a new task
	            // when navigating up, with a synthesized back stack.
	            TaskStackBuilder.create(this)
	                    // Add all of this activity's parents to the back stack
	                    .addNextIntentWithParentStack(upIntent)
	                    // Navigate up to the closest parent
	                    .startActivities();
	        } else {
	            // This activity is part of this app's task, so simply
	            // navigate up to the logical parent activity.
	            NavUtils.navigateUpTo(this, upIntent);
	        }
	        return true;
		}
		return super.onOptionsItemSelected(item);
	}
     
    
    	}
