package com.abhinavgianey.caldining;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PreferencesActivity extends Activity implements OnClickListener, TextWatcher{
	private Button addPreference;
	private EditText preference;
	private TextView myPreferences;
	private Button removePreference;
	private EditText remove;
	private TextView yourPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferences);
		addPreference = (Button) findViewById(R.id.add_preference);
		preference = (EditText) findViewById(R.id.preference);
		preference.addTextChangedListener(this);
		myPreferences = (TextView) findViewById(R.id.my_preferences);
		addPreference.setOnClickListener(this);
		addPreference.setEnabled(false);
		removePreference = (Button) findViewById(R.id.remove_preference);
		remove = (EditText) findViewById(R.id.remove);
		remove.addTextChangedListener(this);
		removePreference.setOnClickListener(this);
		removePreference.setEnabled(false);
		SharedPreferences prefs = getSharedPreferences("1",0);
		String temp = prefs.getString("preferences", "");
		myPreferences.setText(temp);
		yourPreferences = (TextView) findViewById(R.id.your_preferences);
		if (temp.isEmpty()) {
			yourPreferences.setText("You don't have any food preferences!");
		} else {
			yourPreferences.setText("Your Preferences:");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preferences, menu);
		return true;
	}
	
	public void beforeTextChanged(CharSequence s, int start, int before, int count) {}
	
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		CharSequence toAdd = (preference != null) ? preference.getText() : null;
		CharSequence toRemove = (remove != null) ? remove.getText() : null;
		if (preference != null && toAdd.toString().trim().length() > 0) {
			addPreference.setEnabled(true);
		} else {
			addPreference.setEnabled(false);
		}
		if (remove != null && toRemove.toString().trim().length() > 0) {
			removePreference.setEnabled(true);
		} else {
			removePreference.setEnabled(false);
		}
	}
	
	public void afterTextChanged(Editable s) {}

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.add_preference:
			addPreference();
			break;
		case R.id.remove_preference:
			removePreference();
			break;
		}
	}
	
	private void addPreference() {
		String toAdd = MenuManager.capitalize(preference.getText().toString().trim().toLowerCase());
		String[] preferences = myPreferences.getText().toString().split("\n");
		boolean bugFix = false;
		int num = 0;
		for (String pref: preferences) {
			if (toAdd.equals(pref)) {
				Toast.makeText(getApplicationContext(), "You already have this preference!", Toast.LENGTH_SHORT).show();
				return;
			}
			num = pref.length();
			if (pref.equals("    ")) {
				bugFix = true;
			}
		}
		toAdd += "\n";
		SharedPreferences prefs = getSharedPreferences("1", 0);
		SharedPreferences.Editor editor = prefs.edit();
		String newPreferences = prefs.getString("preferences", "");
		if (bugFix) {
			newPreferences = newPreferences.substring(0, newPreferences.length()-4);
		}
		newPreferences += toAdd;
		editor.putString("preferences", newPreferences);
		editor.commit();
		myPreferences.setText(newPreferences);
		preference.setText("");
		if (!newPreferences.isEmpty()) {
			yourPreferences.setText("Your Preferences:");
		}
	}
	
	private void removePreference() {
		String toRemove = MenuManager.capitalize(remove.getText().toString().trim().toLowerCase());
		String[] preferences = myPreferences.getText().toString().split("\n");
		for (String pref: preferences) {
			if (toRemove.equals(pref)) {
				SharedPreferences prefs = getSharedPreferences("1", 0);
				SharedPreferences.Editor editor = prefs.edit();
				String currPreferences = prefs.getString("preferences", "");
				int index = currPreferences.indexOf(toRemove);
				String newPreferences = "";
				if (index>0) {
					newPreferences += currPreferences.substring(0, index);
				}
				if (index + toRemove.length() + 3 < currPreferences.length()) {
					newPreferences += currPreferences.substring(index+toRemove.length() + 1, currPreferences.length());
				}
				editor.putString("preferences", newPreferences);
				editor.commit();
				myPreferences.setText(newPreferences);
				remove.setText("");
				if (newPreferences.isEmpty()) {
					yourPreferences.setText("You don't have any food preferences!");
				}
				return;
			}
		}
		Toast.makeText(getApplicationContext(), "You don't have this preference!", Toast.LENGTH_SHORT).show();	
	}
	
}
