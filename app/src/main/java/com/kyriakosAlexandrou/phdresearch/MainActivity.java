package com.kyriakosAlexandrou.phdresearch;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.kyriakosAlexandrou.phdresearch.sqlite.DatabaseHelper;
import com.kyriakosAlexandrou.phdresearch.targetdefault.MyTarget;

public class MainActivity extends FragmentActivity  {
	public static String btnCurrentString;
	public final static String startTimerString = "Start Timer";
	public final static String stopTimerString = "Stop Timer";
	public boolean isInfantAgeSet = false;
	
	private WriteToTxtFile writeToTxtFile;
	private static PendingIntent pIntent;
	private static AlarmManager alarm;
	private Button btnStartStopTimer;
	private DatabaseHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		MyService.isActivityActive = true;
		bindViews();
		writeToTxtFile = new WriteToTxtFile(this);

		databaseHelper = new DatabaseHelper(this);
//		databaseHelper.storeTimerTimeToDB(10);
		databaseHelper.getCursorValues();
		databaseHelper.getIntTimerTime();
		MyService.TIME_IN_SECONDS = databaseHelper.getIntTimerTime();
		
		setDefaultValues();
		
		
		MyTarget.targetDebugMsg("btnCurrentString: " + MainActivity.btnCurrentString, MyTarget.DEBUG_MODE);
		//check in case our btnCurrentString is null
		if(MainActivity.btnCurrentString == null){
			Log.d("KYR", "MainActivity.btnCurrentString: " + MainActivity.btnCurrentString);
			MainActivity.btnCurrentString = MainActivity.startTimerString;
		}
		
		setBtnStartStopTimerValue(MainActivity.btnCurrentString);
		btnStartStopTimerOnTouchOnClick();
	}
	
	public void setDefaultValues(){
		writeToTxtFile.readDefaultSettings();
	}
	
	public void setBtnStartStopTimerValue(String valueToDisplay){
		MainActivity.btnCurrentString = valueToDisplay;
		btnStartStopTimer.setText(MainActivity.btnCurrentString);
		setBtnStartStopTimerDefaultStyle();
		MyTarget.targetDebugMsg("btnStartStopTimer.getText() " + btnStartStopTimer.getText(), MyTarget.DEBUG_MODE);
	}
	
	public void setBtnStartStopTimerDefaultStyle(){
		// default colors
		if(MainActivity.btnCurrentString.equals(MainActivity.startTimerString)){
			btnStartStopTimer.setBackgroundColor(Color.parseColor(MyTarget.START_TIMER_BTN_DEFAULT_COLOR));
		}else{
			btnStartStopTimer.setBackgroundColor(Color.parseColor(MyTarget.STOP_TIMER_BTN_DEFAULT_COLOR));
		}
	}
	
	public void setBtnBgStyle(Button btn, String colorInString){
		btn.setBackgroundColor(Color.parseColor(colorInString));
	}
	
	public void bindViews(){
		btnStartStopTimer = (Button) findViewById(R.id.btnStartStopTimer);
	}
	
	public void startTimer(){
		Calendar cal;
		cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, MyService.TIME_IN_SECONDS);
		Intent intent;
//		MyService.isActivityActive = false;
		
		intent = new Intent(getBaseContext(), MyService.class);
		pIntent = PendingIntent.getService(getBaseContext(), 0, intent, 0);
		alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), MyService.TIME_IN_SECONDS * 1000, pIntent);
//		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),MyService.TIME_IN_SECONDS * 1000, pIntent);
		startService(intent);
	}
	
	public void terminateTimer(){
		if(alarm != null)alarm.cancel(pIntent);
		
		alarm = null;
		pIntent = null;
		stopService(new Intent(getBaseContext(), MyService.class));
	}
	
	//when button is pressed/touched to start/stop timer
	public void btnStartStopTimerOnTouchOnClick(){
		btnStartStopTimer.setOnTouchListener(new View.OnTouchListener() {
			@Override			
		    public boolean onTouch(View view, MotionEvent event) {
				
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                	//on touch
                	setBtnBgStyle(btnStartStopTimer, MyTarget.START_STOP_TIMER_BTN_ON_TOUCH_COLOR);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                	//on release
                	setBtnStartStopTimerDefaultStyle();                
                }
                return false;
		    }
	    });
		
		btnStartStopTimer.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
//				MyTarget.targetToastMsg(getApplicationContext(), getExternalFilesDir("Quantitative Research").toString());
				
				if(MainActivity.btnCurrentString.equals(MainActivity.startTimerString)){
					// start timer was pressed
					// first insert the age of the infant in days
					if(isInfantAgeSet == false){
						insertAgeDialog();
						return;
					}
					// timer is not started, lets start it
					MyTarget.targetToastMsg(getApplicationContext(), "Timer is set for every: " + MyService.TIME_IN_SECONDS + " Seconds");
					
					setBtnStartStopTimerValue(MainActivity.stopTimerString);
					writeToTxtFile.updateDefaultSettings(MainActivity.stopTimerString);
					startTimer();
					isInfantAgeSet = false;
					MyTarget.targetDebugMsg("Timer Has Started", MyTarget.DEBUG_MODE);
				} else if(MainActivity.btnCurrentString.equals(MainActivity.stopTimerString)){
					//timer is running, lets stop it
					setBtnStartStopTimerValue(MainActivity.startTimerString);
					terminateTimer();
					writeToTxtFile.updateDefaultSettings(MainActivity.startTimerString);
					
					
					// popup the last question
					MyTarget.targetToastMsg(getApplicationContext(), "Last Question !!");
					Intent i = new Intent(MainActivity.this, QuestionsSetLastActivity.class);
					startActivity(i);
					
					MyTarget.targetDebugMsg("service stopped", MyTarget.DEBUG_MODE);
					MyTarget.targetToastMsg(getApplicationContext(), "Timer Has Been Stopped");
				}
			}
		});
	}
	
	@Override
	public void onBackPressed(){
//		MyService.isActivityActive = false;
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Dialog dialog;
		final EditText etEditTimerTime;
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.action_settings:
	    	
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        //get the layout inflater
		        LayoutInflater inflater = this.getLayoutInflater();
		        final View view = inflater.inflate(R.layout.fragment_popup,null);
		        etEditTimerTime = (EditText) view.findViewById(R.id.editTimerTime);
		        
		        //inflate and set the layout for the dialog
		        //pass null as the parent view because its going in the dialog layout
		        builder.setView(view);
		        builder.setMessage("Edit Timer Time")
		               .setPositiveButton("Done", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   final String timerTime = etEditTimerTime.getText().toString();
		                	   
		                	   int temp = Integer.parseInt(timerTime);
		                	   databaseHelper.storeChangedTimerTimeToDB(temp);
		                	   MyService.TIME_IN_SECONDS = temp;
		                	   MyTarget.targetToastMsg(getApplicationContext(), "Timer Has Been Set To: " + MyService.TIME_IN_SECONDS + " Seconds");
		                   }
		               })
		               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                       // User cancelled the dialog                	   
		                   }
		               });
		        dialog = builder.create();
		        // automatically open keypad
		        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		        dialog.show();
		        
				Log.d("KYR", "OLD TIME: " + MyService.TIME_IN_SECONDS);
	        return true;
	    default:
			databaseHelper.getCursorValues();
			databaseHelper.getIntTimerTime();
			MyService.TIME_IN_SECONDS = databaseHelper.getIntTimerTime();
			Log.d("KYR", "NEW TIME: " + MyService.TIME_IN_SECONDS);
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void insertAgeDialog(){
        //get the layout inflater
		final EditText infantAgeEditView;
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_insert_age_popup,null);
        infantAgeEditView = (EditText) view.findViewById(R.id.infantAgeEditView);
        
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		Dialog dialog;
		
        //inflate and set the layout for the dialog
        //pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        builder.setMessage("How Old Is The Infant In Days?")
               .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   String infantAgeString = infantAgeEditView.getText().toString();
                	   if(infantAgeString.length() > 0)
                	   {
                    	   int infantAgeInt = Integer.parseInt(infantAgeString);
                    	   
                    	   if(infantAgeInt > 0)
                    	   {
                        	   isInfantAgeSet = true;
                        	   writeToTxtFile.writeToFile("Infant Age: " + infantAgeInt, MyTarget.FILENAME_INFANT_AGE, false);
//                        	   int temp = Integer.parseInt(infantAgeString);
//                        	   databaseHelper.storeChangedTimerTimeToDB(temp);
                    		   MyTarget.targetToastMsg(getApplicationContext(), "Congratulations on your new arrival :)");
                    		   final Handler handler = new Handler();
                    		   
								final Runnable r = new Runnable()
								{
								    public void run() 
								    {
										btnStartStopTimer.performClick();
										finish();
								    }
								};
								handler.postDelayed(r, 2500);                    		   
                    	   }
                	   } else{
                		   MyTarget.targetToastMsg(getApplicationContext(), "Please enter a valid age");
                		   return;
            		   }
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                	   isInfantAgeSet = false;
//       				MyTarget.targetToastMsg(getApplicationContext(), "To set the timer you must add the age of the infant");
                	   return;
                   }
               });
        
        dialog = builder.create();
        // automatically open keypad
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
	}
}