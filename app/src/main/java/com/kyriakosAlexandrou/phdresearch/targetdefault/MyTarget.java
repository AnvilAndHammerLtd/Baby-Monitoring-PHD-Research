package com.kyriakosAlexandrou.phdresearch.targetdefault;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class MyTarget {
	public static final boolean DEBUG_MODE = false;
	public static final String FILENAME_PHD_RESEARCH_DATA = "PHD Research Data.txt";
	public static final String FILENAME_INFANT_AGE = "Infant Age.txt";
	
	//colors for buttons
	public static final String QUESTION_BTN_ON_TOUCH_COLOR = "#80E41B17";
	public static final String QUESTION_BTN_DEFAULT_COLOR = "#8000FF00";
	public static final String START_STOP_TIMER_BTN_ON_TOUCH_COLOR = "#807D1B7E";
	public static final String START_TIMER_BTN_DEFAULT_COLOR = "#80000080";
	public static final String STOP_TIMER_BTN_DEFAULT_COLOR = "#80990012";
	
	
	
	public static void targetDebugMsg(String msg, boolean display){
		if(display == true){
			Log.d("KYR", msg);
		}
	}
	
	public static void targetToastMsg(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static String getCurrentTime(){		
		final Calendar c = Calendar.getInstance();
		MyTarget.targetDebugMsg(DateFormat.format("kk:mm:ss", c).toString(), MyTarget.DEBUG_MODE);
		return DateFormat.format("kk:mm:ss", c).toString();
		
	}
	
	public static String getCurrentDate(){
			final Calendar c = Calendar.getInstance();
			MyTarget.targetDebugMsg(DateFormat.format("dd/MM/yyyy", c).toString(), MyTarget.DEBUG_MODE);
			return DateFormat.format("dd/MM/yyyy", c).toString();
	}
}