package com.abhinavgianey.caldining;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ListMenuActivity extends Activity {
	private TextView theMenu;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Loading...");
		mProgressDialog.show();
		setContentView(R.layout.activity_list_menu);
		theMenu = (TextView) findViewById(R.id.menu);	
		theMenu.setText(MenuManager.getDiner(getIntent().getStringExtra("diner")));
		mProgressDialog.dismiss();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_menu, menu);
		return true;
	}

}
