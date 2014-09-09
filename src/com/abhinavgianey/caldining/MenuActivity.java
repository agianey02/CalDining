package com.abhinavgianey.caldining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener {
	
	private Button crossroadsBreakfast;
	private Button crossroadsLunch;
	private Button crossroadsDinner;
	private Button cafe3Breakfast;
	private Button cafe3Lunch;
	private Button cafe3Dinner;
	private Button clarkKerrBreakfast;
	private Button clarkKerrLunch;
	private Button clarkKerrDinner;
	private Button foothillBreakfast;
	private Button foothillLunch;
	private Button foothillDinner;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		setContentView(R.layout.activity_menu);
		crossroadsBreakfast = (Button) findViewById(R.id.crossroads_breakfast);
		crossroadsLunch = (Button) findViewById(R.id.crossroads_lunch);
		crossroadsDinner = (Button) findViewById(R.id.crossroads_dinner);
		cafe3Breakfast = (Button) findViewById(R.id.cafe3_breakfast);
		cafe3Lunch = (Button) findViewById(R.id.cafe3_lunch);
		cafe3Dinner = (Button) findViewById(R.id.cafe3_dinner);
		clarkKerrBreakfast = (Button) findViewById(R.id.clark_kerr_breakfast);
		clarkKerrLunch = (Button) findViewById(R.id.clark_kerr_lunch);
		clarkKerrDinner = (Button) findViewById(R.id.clark_kerr_dinner);
		foothillBreakfast = (Button) findViewById(R.id.foothill_breakfast);
		foothillLunch = (Button) findViewById(R.id.foothill_lunch);
		foothillDinner = (Button) findViewById(R.id.foothill_dinner);
		crossroadsBreakfast.setOnClickListener(this);
		crossroadsLunch.setOnClickListener(this);
		crossroadsDinner.setOnClickListener(this);
		cafe3Breakfast.setOnClickListener(this);
		cafe3Lunch.setOnClickListener(this);
		cafe3Dinner.setOnClickListener(this);
		clarkKerrBreakfast.setOnClickListener(this);
		clarkKerrLunch.setOnClickListener(this);
		clarkKerrDinner.setOnClickListener(this);
		foothillBreakfast.setOnClickListener(this);
		foothillLunch.setOnClickListener(this);
		foothillDinner.setOnClickListener(this);
	}

	public void onClick(View v) {
		Intent i = new Intent(this, ListMenuActivity.class);
		i.putExtra("diner", ((Button) v).getText().toString());
		startActivity(i);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
