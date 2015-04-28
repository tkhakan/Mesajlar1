package com.hakan.mesajlar;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class GidenMesaj extends ActionBarActivity {
	private static final int TYPE_INCOMING_MESSAGE = 2;
	private ListView messageList;
	private MessageListAdapter messageListAdapter;
	private ArrayList<Message> recordsStored;
	private ArrayList<Message> listInboxMessages;
	private ProgressDialog progressDialogInbox;
	private CustomHandler customHandler;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gelen_mesaj);
		initViews();
		
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public void onResume() {
		super.onResume();
		populateMessageList();
	}

	private void initViews() {
		customHandler = new CustomHandler(this);
		progressDialogInbox = new ProgressDialog(this);

		recordsStored = new ArrayList<Message>();

		messageList = (ListView) findViewById(R.id.messageList);
		populateMessageList();
	}

	public void populateMessageList() {
		fetchInboxMessages();

		messageListAdapter = new MessageListAdapter(this,
				R.layout.message_list_item, recordsStored);
		messageList.setAdapter(messageListAdapter);
	}

	private void showProgressDialog(String message) {
		progressDialogInbox.setMessage(message);
		progressDialogInbox.setIndeterminate(true);
		progressDialogInbox.setCancelable(true);
		progressDialogInbox.show();
		
	}

	private void fetchInboxMessages() {
		if (listInboxMessages == null) {
			showProgressDialog("Giden Mesajlar...");
			
			startThread();
		} else {
			// messageType = TYPE_INCOMING_MESSAGE;
			recordsStored = listInboxMessages;
			messageListAdapter.setArrayList(recordsStored);
		}
	}

	public class FetchMessageThread extends Thread {

		public int tag = -1;

		public FetchMessageThread(int tag) {
			this.tag = tag;
		}

		@Override
		public void run() {

			recordsStored = fetchInboxSms(TYPE_INCOMING_MESSAGE);
			listInboxMessages = recordsStored;
			customHandler.sendEmptyMessage(0);

		}

	}

	public ArrayList<Message> fetchInboxSms(int type) {
		ArrayList<Message> smsInbox = new ArrayList<Message>();

		Uri uriSms = Uri.parse("content://sms");
	
		Cursor cursor = this.getContentResolver()
				.query(uriSms,
						new String[] { "_id", "address", "date", "body",
								"type", "read" }, "type=" + type, null,
						"date" + " COLLATE LOCALIZED ASC");
		if (cursor != null) {
			cursor.moveToLast();
			if (cursor.getCount() > 0) {

				do {
					String date =  cursor.getString(cursor.getColumnIndex("date"));
                    Long timestamp = Long.parseLong(date);    
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timestamp);
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
					Message message = new Message();
					message.messageNumber = cursor.getString(cursor
							.getColumnIndex("address"));
					message.messageContent = cursor.getString(cursor
							.getColumnIndex("body"));
					message.messageDate = formatter.format(calendar.getTime());
					
					smsInbox.add(message);
					
				
				} while (cursor.moveToPrevious());
			}
		}

		return smsInbox;

	}


	private FetchMessageThread fetchMessageThread;

	private int currentCount = 0;

	public synchronized void startThread() {

		if (fetchMessageThread == null) {
			fetchMessageThread = new FetchMessageThread(currentCount);
			fetchMessageThread.start();
		}
	}

	public synchronized void stopThread() {
		if (fetchMessageThread != null) {
			Log.i("Cancel thread", "stop thread");
			FetchMessageThread moribund = fetchMessageThread;
			currentCount = fetchMessageThread.tag == 0 ? 1 : 0;
			fetchMessageThread = null;
			moribund.interrupt();
		}
	}

	static class CustomHandler extends Handler {
		private final WeakReference<GidenMesaj> activityHolder;

		CustomHandler(GidenMesaj inboxListActivity) {
			activityHolder = new WeakReference<GidenMesaj>(inboxListActivity);
		}

		@Override
		public void handleMessage(android.os.Message msg) {

			GidenMesaj inboxListActivity = activityHolder.get();
			if (inboxListActivity.fetchMessageThread != null
					&& inboxListActivity.currentCount == inboxListActivity.fetchMessageThread.tag) {
				Log.i("received result", "received result");
				inboxListActivity.fetchMessageThread = null;
				
				inboxListActivity.messageListAdapter
						.setArrayList(inboxListActivity.recordsStored);
				inboxListActivity.progressDialogInbox.dismiss();
			}
		}
	}

	private OnCancelListener dialogCancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			stopThread();
		}

	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.gelen_mesaj, menu);
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