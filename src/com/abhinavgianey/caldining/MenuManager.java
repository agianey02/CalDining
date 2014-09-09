package com.abhinavgianey.caldining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

public class MenuManager {
	
	private static Hashtable<String, ArrayList<String>> menu1 = null;
	private static Hashtable<String, ArrayList<String>> menu2 = null;
	public static String NOT_STARTED = "not started";
	public static String IN_PROGRESS = "in progress";
	public static String DONE = "done";
	private static String menuStatus = NOT_STARTED;
	
	public static void setMenu() {
		new DataPullTask().execute();
	}
	
	public static String getMenuStatus() {
		return menuStatus;
	}
	
	public static Hashtable<String, ArrayList<String>> getMenu1() {
		return menu1;
	}
	
	public static Hashtable<String, ArrayList<String>> getMenu2() {
		return menu2;
	}
	
	public static Boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
	
	public static String getDiner(String diner) {
		String toRet = "";
		StringBuilder output = new StringBuilder();
		while (menu1==null) {}
		if (menu1 != null) {
			ArrayList<String> temp = menu1.get(diner);
			if (temp == null) {
				output.append("There is nothing being served");
			} else {
				for (String s : temp) {
					output.append(s).append("\n");
				}
			}
			toRet = output.toString();
		}
		return toRet;
	}
	
	public static ArrayList<String> getDiningCommonsArrayList(String pref) {
		while (menu2==null) {}
		return menu2.get(pref);
	}
	
	public static String capitalize(String line) {
        if (line.isEmpty()) {
            return "";
        }
    	String[] temp = line.split(" ");
		String toRet = "";
		for (String s:temp) {
			toRet += " " + Character.toUpperCase(s.charAt(0)) + s.substring(1);
		}
		return toRet.substring(1);
	}
	
	private static class DataPullTask extends AsyncTask<Void, Void, Void> {
		
		protected Void doInBackground(Void... v) {
			try {
				menuStatus = IN_PROGRESS;
				Hashtable<String, ArrayList<String>>[] menus = DataPull.pullData();
				menu1 = menus[0];
				menu2 = menus[1];
				menuStatus = DONE;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
