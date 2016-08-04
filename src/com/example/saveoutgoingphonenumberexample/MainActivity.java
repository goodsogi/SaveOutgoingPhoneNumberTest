package com.example.saveoutgoingphonenumberexample;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	public static final String INTENT_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";
	private static final int REQUEST_SAVE_NUMBER_CONTACT = 22;
	protected String mOutgoingnNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Catch outgoing call number with broadcast receiver
		registerOutgoingCallReceiver();
	}

	/**
	 * Catch outgoing call number with broadcast receiver
	 */
	private void registerOutgoingCallReceiver() {
		IntentFilter intentFilter = new IntentFilter(INTENT_OUTGOING_CALL);
		BroadcastReceiver outGoingCallReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				mOutgoingnNumber = intent
						.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
				Toast.makeText(context, "outgoingnum =" + mOutgoingnNumber,
						Toast.LENGTH_LONG).show();
				// Insert call number to contact
				insertNumberContact(null);
			}
		};
		registerReceiver(outGoingCallReceiver, intentFilter);

	}

	/**
	 * Insert call number to contact
	 * 
	 * @param outgoingnNumber
	 */
	public void insertNumberContact(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

		intent.putExtra(ContactsContract.Intents.Insert.NAME, "Test");
		intent.putExtra(ContactsContract.Intents.Insert.PHONE, mOutgoingnNumber);
		intent.putExtra(ContactsContract.Intents.Insert.EMAIL,
				"goodsogi@gmail.com");
		intent.putExtra(ContactsContract.Intents.Insert.NOTES,
				"Imported from moodle");
		intent.putExtra(ContactsContract.Intents.Insert.POSTAL, "Seoul" + " "
				+ "Korea");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivityForResult(intent, REQUEST_SAVE_NUMBER_CONTACT);
		
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, REQUEST_SAVE_NUMBER_CONTACT, intent, 0);
		
		
		try {
			contentIntent.send(this, 0, intent);
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_SAVE_NUMBER_CONTACT:
				Toast.makeText(this, "saved outgoing number to contacts!",
						Toast.LENGTH_LONG).show();
				return;
			}
		}
	}

}
