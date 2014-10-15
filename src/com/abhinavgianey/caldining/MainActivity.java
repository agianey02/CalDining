package com.abhinavgianey.caldining;

import java.util.Calendar;

/**
import com.example.notificationtest.MainActivity;
import com.example.notificationtest.MyReceiver;
**/ 

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button todaysMenu;
	private Button preferences;
	private Button alerts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		todaysMenu = (Button) findViewById(R.id.today_menu);
		todaysMenu.setOnClickListener(this);
		preferences = (Button) findViewById(R.id.preferences);
		preferences.setOnClickListener(this);
		alerts = (Button) findViewById(R.id.alerts);
		alerts.setOnClickListener(this);
		SharedPreferences prefs = getSharedPreferences("1",0);
		/**
        boolean isFirstTime = prefs.getBoolean("firstTime", true);
      
        if (isFirstTime) {
    	  
    	    SharedPreferences.Editor editor = prefs.edit();
    	    editor.putBoolean("firstTime", false);
    	    editor.commit();
     
	        Calendar calendar = Calendar.getInstance();
	     
	        calendar.set(Calendar.MONTH, Calendar.AUGUST);
	        calendar.set(Calendar.YEAR, 2014);
	        calendar.set(Calendar.DAY_OF_MONTH, 17);
	 
	        calendar.set(Calendar.HOUR_OF_DAY, 16);
	        calendar.set(Calendar.MINUTE, 36);
	        calendar.set(Calendar.SECOND, 0);
	        Log.d("firstTime", Boolean.toString(isFirstTime));
	     
	        Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
	        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);
	     
	        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 1000*60*5, pendingIntent);
	        
        }
        **/
		if (MenuManager.isNetworkAvailable(getApplicationContext())) {
			MenuManager.setMenu();
		} else {
			new AlertDialog.Builder(this)
			.setTitle("No Network Connection")
			.setMessage("Please turn on your internet")
			.setPositiveButton("Okay", 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int width) {
							dialog.dismiss();
							return;
						}
			})
			.show();
		}
		//createNotification();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.today_menu:
				menuActivity();
				break;
			case R.id.preferences:
				preferencesActivity();
				break;
			case R.id.alerts:
				alertPreferencesActivity();
				break;
		}
	}
	
	private void createNotification() {
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), alarmIntent);
	}
	
	private void menuActivity() {
		Intent i = new Intent(this, MenuActivity.class);
		startActivity(i);
	}
	
	private void preferencesActivity() {
		Intent i = new Intent(this, PreferencesActivity.class);
		startActivity(i);
	}
	
	private void alertPreferencesActivity() {
		Intent i = new Intent(this, AlertPreferencesActivity.class);
		startActivity(i);
	}

}
