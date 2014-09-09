package com.abhinavgianey.caldining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class AlertPreferencesActivity extends Activity {
	private TextView alertPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if ((MenuManager.NOT_STARTED).equals(MenuManager.getMenuStatus())) {
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
								finish();
								return;
							}
				})
				.show();
				return;
			}
		}
		setContentView(R.layout.activity_alert_preferences);
		alertPref = (TextView) findViewById(R.id.alert_preference);
		loadPreferences();
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alert_preferences, menu);
		return true;
	}
	
	private void loadPreferences() {
		SharedPreferences prefs = getSharedPreferences("1", 0);
		String currPreferences = prefs.getString("preferences", "");
		String[] preferences = currPreferences.split("\n");
		while (MenuManager.getMenu2() == null) {}
		StringBuilder allDiners = new StringBuilder();
		for (String pref:preferences) {
			String prefLowerCase = pref.toLowerCase();
			ArrayList<String> diners = MenuManager.getDiningCommonsArrayList(prefLowerCase);
			if (diners != null) {
				allDiners.append(pref).append(":\n");
				for (String diner:diners) {
					allDiners.append(diner).append("\n");
				}
				allDiners.append("\n");
			}
		}
		alertPref.setText(allDiners.toString());
		if (allDiners.toString().isEmpty()) {
			alertPref.setText("None of your food preferences are being served today :(");
		}
	}
	
	
	

}
