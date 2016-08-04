package com.kyriakosAlexandrou.phdresearch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.kyriakosAlexandrou.phdresearch.targetdefault.MyTarget;

public class MyService  extends Service {

	public static boolean isActivityActive; // so that the activity doesn't open twice
//	private int counter = 0;
//	private MediaPlayer mp;
	

	public static int TIME_IN_SECONDS;
	
	@Override
	public IBinder onBind(Intent intent) {
		MyTarget.targetDebugMsg("onBind", MyTarget.DEBUG_MODE);
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		MyTarget.targetDebugMsg("onCreate of MyService", MyTarget.DEBUG_MODE);
//		Toast.makeText(getApplicationContext(), "Service Created", Toast.LENGTH_SHORT).show();
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		MyTarget.targetDebugMsg("onDestroy of MyService", MyTarget.DEBUG_MODE);
//		MyTarget.targetToastMsg(getApplicationContext(), "Service Destroyed");
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		MyTarget.targetDebugMsg("onStartCommand FAILURE: " + counter, MyTarget.DEBUG_MODE);
		
//		counter += 1;
//		if(counter > 1){ //skip the first call to the questions
			if(isActivityActive == false){
//				MyTarget.targetToastMsg(getApplicationContext(), "onStartCommand POPUP SUCCESS");

	//			mp = MediaPlayer.create(getApplicationContext(), R.raw.notification);
	//			mp.start();
				
				Intent i = new Intent();
				i.setClass(this, QuestionsSet1Activity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			} else{
				Toast.makeText(getApplicationContext(), "Please Select An Asnwer", Toast.LENGTH_LONG).show();

			}
//		}
		return START_STICKY;
//		return super.onStartCommand(intent, flags, startId);
	}
}