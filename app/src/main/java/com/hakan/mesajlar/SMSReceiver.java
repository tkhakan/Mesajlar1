package com.hakan.mesajlar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.telephony.SmsMessage;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
//Take a look at BroadCastReceivers you must implement and register a Reciever for android.provider.Telephony.SMS_RECEIVED  
  
//Here is a code snippet that lets you read messages as they arrive.  
public class SMSReceiver extends BroadcastReceiver 
{ 
	
public void onReceive(Context context, Intent intent) 
{ 
Bundle myBundle = intent.getExtras(); 
SmsMessage [] messages = null; 
String strMessage = ""; 
 
if (myBundle != null) 
{ 
Object [] pdus = (Object[]) myBundle.get("pdus"); 
messages = new SmsMessage[pdus.length]; 
 
for (int i = 0; i < messages.length; i++) 
{ 
messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]); 
strMessage += "GÖNDEREN: " + messages[i].getOriginatingAddress(); 
strMessage += "  "; 
strMessage += "  ";
strMessage += messages[i].getMessageBody(); 

}
  
  
//Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
Toast toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
View view = toast.getView();

view.setBackgroundResource(R.drawable.sms1);
toast.show();


Toast toastt = Toast.makeText(context, strMessage, Toast.LENGTH_LONG);

//view.setBackgroundResource(R.drawable.sms2);
toastt.setGravity(Gravity.CENTER, 50, 50);
toastt.getView().setPadding(10, 10, 10, 10);

toastt.getView().setBackgroundColor(Color.RED);

toastt.show();

}  
}  
}  