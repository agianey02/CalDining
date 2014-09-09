package com.abhinavgianey.caldining;

import java.util.ArrayList;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
	private Context applicationContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		applicationContext = context;
		String diners = loadPreferences();
		if (!diners.isEmpty()) {
			NotificationCompat.Builder notificationBuilder =
			        new NotificationCompat.Builder(context)
			        .setContentTitle("My notification")
			        .setContentText("Hello World!");
			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(context, DisplayPreferenceDinersActivity.class);
			resultIntent.putExtra("all diners", diners);
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(applicationContext);
	        // Adds the back stack for the Intent (but not the Intent itself)
	        stackBuilder.addParentStack(MainActivity.class);
	        // Adds the Intent that starts the Activity to the top of the stack
	        stackBuilder.addNextIntent(resultIntent);
	        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	        notificationBuilder.setContentIntent(resultPendingIntent);
	        NotificationManager mNotificationManager = (NotificationManager) applicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
	        mNotificationManager.notify(1, notificationBuilder.build());
		}
	}
	
	private String loadPreferences() {
		SharedPreferences prefs = applicationContext.getSharedPreferences("1", 0);
		String currPreferences = prefs.getString("preferences", "");
		String[] preferences = currPreferences.split("\n");
		while (MenuManager.getMenu2() == null) {}
		StringBuilder allDiners = new StringBuilder();
		for (String pref:preferences) {
			ArrayList<String> diners = MenuManager.getDiningCommonsArrayList(pref);
			if (diners != null) {
				allDiners.append(MenuManager.capitalize(pref)).append(":\n");
				for (String diner:diners) {
					allDiners.append(diner).append("\n");
				}
				allDiners.append("\n");
			}
		}
		return allDiners.toString();
	}

}
