package com.abhinavgianey.caldining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;

public class DataPull {
    
    public static String mapHelper(int diner) {
        String ret;
		switch (diner) {
			case 1: ret =  "Crossroads Breakfast";
                    break;
			case 2: ret = "Cafe 3 Breakfast";
                    break;
			case 3: ret = "Foothill Breakfast";
                    break;
			case 4: ret = "Clark Kerr Breakfast";
                    break;
			case 5: ret = "Crossroads Lunch";
                    break;
			case 6: ret = "Cafe 3 Lunch";
                    break;
			case 7: ret = "Foothill Lunch";
                    break;
			case 8: ret = "Clark Kerr Lunch";
                    break;
			case 9: ret = "Crossroads Dinner";
                    break;
			case 10: ret = "Cafe 3 Dinner";
                    break;
			case 11: ret = "Foothill Dinner";
                    break;
			case 12: ret = "Clark Kerr Dinner";
                    break;
			default: ret = "Gibberish!!!";
		}
		return ret;
	}
	
	public static Hashtable<String, ArrayList<String>>[] pullData() throws IOException {
		
		Hashtable hash1 = new Hashtable<String, ArrayList<String>>();
		Hashtable hash2 = new Hashtable<String, ArrayList<String>>();
		Hashtable<String, ArrayList<String>>[] ret = new Hashtable[2];
		ret[0] = hash1;
		ret[1] = hash2;
		 
		URL url = new URL("http://services.housing.berkeley.edu/FoodPro/dining/static/todaysentrees.asp");

        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;

        boolean search = false;
        int diners = 0;
        while ((line = br.readLine()) != null) {
            if (line.contains("<br><br></td>")) {
        		search = false;
        	}
            int start = line.indexOf("><font color='");
            int end = line.indexOf("</font>");
        	if (search && (line.indexOf("Closed") == -1) && (start != -1) && (end != -1)) {
        		if (hash1.containsKey(mapHelper(diners))) {
        			ArrayList tempList = (ArrayList) hash1.get(mapHelper(diners));
                    //System.out.println(line);
                    //System.out.println(start);
        			tempList.add(line.substring(start + 23, end));
        			hash1.put(mapHelper(diners), tempList);
        		} else {                                                                                                                                                                                                                                                                                                                                  
        			ArrayList<String> newList = new ArrayList<String>();
        			newList.add(line.substring(start + 23, end));
        			hash1.put(mapHelper(diners), newList);
        		}
                String temp = line.substring(start + 23, end).trim().toLowerCase();
        		if (hash2.containsKey(temp)) {
        			ArrayList tempList2 = (ArrayList) hash2.get(temp);
        			tempList2.add(mapHelper(diners));
        			hash2.put(temp, tempList2);
        		} else {
        			ArrayList<String> newList2 = new ArrayList<String>();
        			newList2.add(mapHelper(diners));
        			hash2.put(line.substring(start + 23, end).trim().toLowerCase(), newList2);
        		}
        	}
        	if (line.contains("Breakfast") || line.contains("Lunch") || line.contains("Dinner")) {
        		search = true;
        		diners++;
        	}
        }
        return ret;
	}
	public static void main(String[] args) throws IOException {
        Hashtable<String, ArrayList<String>>[] temp = pullData();
        Hashtable<String, ArrayList<String>> temp1 = temp[0];
        ArrayList<String> temp2 = temp1.get("Crossroads Dinner");
        for (String s: temp2) {
            System.out.println(s);
        }
    }
}