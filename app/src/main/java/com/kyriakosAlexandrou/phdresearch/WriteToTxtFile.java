package com.kyriakosAlexandrou.phdresearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;

import com.kyriakosAlexandrou.phdresearch.targetdefault.MyTarget;

public class WriteToTxtFile {
	private Context context;
	
	public WriteToTxtFile(Context context){
		this.context = context;
	}
    
	public void writeToFile(String data, String filename, boolean isOverride) {
		
//		MyTarget.targetToastMsg(context.getApplicationContext(), context.getExternalFilesDir("Quantitative Research") + "/" + filename);

//		data = data + "\r\n"; //add new line after each value         
		try {
		    String storageState = Environment.getExternalStorageState();
		    if (storageState.equals(Environment.MEDIA_MOUNTED)) {
				MyTarget.targetDebugMsg("writeToFile: Writing useful data", MyTarget.DEBUG_MODE);
			    File sd = new File(Environment.getExternalStorageDirectory() + "/Quantitative_Research");
			    sd.mkdirs();
			    
		        File file = new File(sd, filename);
		        FileOutputStream fos;
		        if(isOverride == true){
		        	fos = new FileOutputStream(file, true);
		        } else{
		        	fos = new FileOutputStream(file, false);
		        }
		        fos.write(data.getBytes());
		        fos.close();
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
	
	
	
	public void updateDefaultSettings(String data) {
		try {
			MyTarget.targetDebugMsg("updateDefaultSettings: storing new button value", MyTarget.DEBUG_MODE);

//			data = data + "\n";
			//if file does not exist it will create it
		    FileOutputStream fos = context.openFileOutput("settings", Context.MODE_PRIVATE);
		    fos.write(data.getBytes());
		    fos.close();
		    
		    // update current button value
//		    String value = data;
			MyTarget.targetDebugMsg("updateDefaultSettings data: " + data, MyTarget.DEBUG_MODE);

		    if(data.equals(MainActivity.startTimerString)){
		    	MainActivity.btnCurrentString = MainActivity.startTimerString;
		    } else{
		    	MainActivity.btnCurrentString = MainActivity.stopTimerString;
		    }
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public void readDefaultSettings() {
		try {
			//check if file exists before reading
			File file = context.getFileStreamPath("settings");
			if(file.exists()){
				MyTarget.targetDebugMsg("readDefaultSetting: FILE EXISTS", MyTarget.DEBUG_MODE);

				// file exists
			    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
			            context.openFileInput("settings")));
			    String inputString;
			    StringBuffer stringBuffer = new StringBuffer();                
			    while ((inputString = inputReader.readLine()) != null) {
			        stringBuffer.append(inputString);
			    }
			    // + "\n"
				MyTarget.targetDebugMsg("readDefaultSettings data: " + stringBuffer.toString(), MyTarget.DEBUG_MODE);

			    // update current button value
			    String value = stringBuffer.toString();
			    if(value.equals(MainActivity.startTimerString)){
			    	MainActivity.btnCurrentString = MainActivity.startTimerString;
			    } else{
			    	MainActivity.btnCurrentString = MainActivity.stopTimerString;
			    }
			}else{
				MyTarget.targetDebugMsg("readDefaultSetting: FILE DOES NOT EXIST", MyTarget.DEBUG_MODE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	private void exportDB(){
//		File sd = Environment.getExternalStorageDirectory();
//		File data = Environment.getDataDirectory();
//		
//	    FileChannel source=null;
//	    FileChannel destination=null;
//	    String currentDBPath = "/data/"+ "com.GreenUxbridge" +"/databases/"+ "Green_Uxbridge";
//	    String backupDBPath = "Green_Uxbridge";
//	    File currentDB = new File(data, currentDBPath);
//	    File backupDB = new File(sd, backupDBPath);
//	    try {
//	    	source = new FileInputStream(currentDB).getChannel();
//	        destination = new FileOutputStream(backupDB).getChannel();
//	        destination.transferFrom(source, 0, source.size());
//	        source.close();
//	        destination.close();
////	        Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
//	    }catch(IOException e) {
//	    	e.printStackTrace();
//	    }
//	}
	
//	public void exportDB(String filename){
//		File sd = Environment.getExternalStorageDirectory();
//		File data = Environment.getDataDirectory();
//		
//	    FileChannel source=null;
//	    FileChannel destination=null;
//	    String currentDBPath = "/data/com.kyriakosalexandrou.phdresearch/files/Quantitative Research/PHD Research Data.txt";
//	    String backupDBPath = "PHDResearch";
//	    File currentDB = new File(data, currentDBPath);
//	    File backupDB = new File(sd, backupDBPath);
//	    try {
//	    	source = new FileInputStream(currentDB).getChannel();
//	        destination = new FileOutputStream(backupDB).getChannel();
//	        destination.transferFrom(source, 0, source.size());
//	        source.close();
//	        destination.close();
//	    }catch(IOException e) {
//	    	e.printStackTrace();
//	    }
//	}
}