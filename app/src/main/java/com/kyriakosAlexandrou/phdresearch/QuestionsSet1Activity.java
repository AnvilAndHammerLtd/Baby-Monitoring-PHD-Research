package com.kyriakosAlexandrou.phdresearch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.kyriakosAlexandrou.phdresearch.targetdefault.MyTarget;

public class QuestionsSet1Activity extends Activity implements OnTouchListener{
	private ImageView btnImgAnswer1a;
	private ImageView btnImgAnswer1b;
	private ImageView btnImgAnswer1c;
	private ImageView btnImgAnswer1d;
	private ImageView btnImgAnswer1e;
	private ImageView btnImgAnswer1f;
	private WriteToTxtFile writeToTxtFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions_set1);
		
		MyService.isActivityActive = true;
		bindViews();
		setBtnDefaultValues();
//		btnImgAnswersOnTouch();
		writeToTxtFile = new WriteToTxtFile(this);
		
		
		
		//TO BE REMOVED AFTER TESTING
		   final Handler handler = new Handler();   
			final Runnable r = new Runnable()
			{
			    public void run() 
			    {
					btnImgAnswersOnClick(btnImgAnswer1b);									
			    }
			};
			handler.postDelayed(r, 2000); 
	}
	
	public void setBtnDefaultValues(){
		setBtnBgDefaultStyle(btnImgAnswer1a);
		setBtnBgDefaultStyle(btnImgAnswer1b);
		setBtnBgDefaultStyle(btnImgAnswer1c);
		setBtnBgDefaultStyle(btnImgAnswer1d);
		setBtnBgDefaultStyle(btnImgAnswer1e);
		setBtnBgDefaultStyle(btnImgAnswer1f);
		
		btnImgAnswer1a.setOnTouchListener(this);
		btnImgAnswer1b.setOnTouchListener(this);
		btnImgAnswer1c.setOnTouchListener(this);
		btnImgAnswer1d.setOnTouchListener(this);
		btnImgAnswer1e.setOnTouchListener(this);
		btnImgAnswer1f.setOnTouchListener(this);
	}
	
	public void bindViews(){
		btnImgAnswer1a = (ImageView) findViewById(R.id.btnImgAnswer1a);
		btnImgAnswer1b = (ImageView) findViewById(R.id.btnImgAnswer1b);
		btnImgAnswer1c = (ImageView) findViewById(R.id.btnImgAnswer1c);
		btnImgAnswer1d = (ImageView) findViewById(R.id.btnImgAnswer1d);
		btnImgAnswer1e = (ImageView) findViewById(R.id.btnImgAnswer1e);
		btnImgAnswer1f = (ImageView) findViewById(R.id.btnImgAnswer1f);
	}
	
	public void setBtnBgDefaultStyle(ImageView btn){
		Drawable replacer = getResources().getDrawable(R.drawable.box1);
		btn.setImageDrawable(replacer);
		btn.invalidate();
	}
	
	public void setBtnBgOnTouchStyle(ImageView btn){
		Drawable replacer = getResources().getDrawable(R.drawable.box2);
		btn.setImageDrawable(replacer);
		btn.invalidate();
	}
	
	//when one of the questions is selected
	public void btnImgAnswersOnClick(View view){
		
		String date = MyTarget.getCurrentDate();
		String time = MyTarget.getCurrentTime();
		String questionAndAnswer = getString(R.string.answer_q1a);
		String completeRow;
		
		switch(view.getId()){
			case R.id.btnImgAnswer1a:
				MyTarget.targetDebugMsg("btnbtnAnswer1a selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q1a);
				setBtnBgOnTouchStyle(btnImgAnswer1a);             
				break;
			case R.id.btnImgAnswer1b:
				MyTarget.targetDebugMsg("btnbtnAnswer1b selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q1b);
				setBtnBgOnTouchStyle(btnImgAnswer1b);
				break;
			case R.id.btnImgAnswer1c:
				MyTarget.targetDebugMsg("btnbtnAnswer1c selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q1c);
				setBtnBgOnTouchStyle(btnImgAnswer1c);
				break;
			case R.id.btnImgAnswer1d:
				MyTarget.targetDebugMsg("btnbtnAnswer1d selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q1d);
				setBtnBgOnTouchStyle(btnImgAnswer1d);
				break;
			case R.id.btnImgAnswer1e:
				MyTarget.targetDebugMsg("btnbtnAnswer1e selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q1e);
				setBtnBgOnTouchStyle(btnImgAnswer1e);
				break;
			case R.id.btnImgAnswer1f:
				MyTarget.targetDebugMsg("btnbtnAnswer1f selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q1f);
				setBtnBgOnTouchStyle(btnImgAnswer1f);
				break;
			default:
				break;	
		}
		
		completeRow = date + "\t" + time + "\t" + questionAndAnswer;
		writeToTxtFile.writeToFile(completeRow, MyTarget.FILENAME_PHD_RESEARCH_DATA, true);
		
		// after selecting an answer display the new set of questions
		MyTarget.targetToastMsg(getApplicationContext(), "Success 1!!");
		Intent i = new Intent();
		i.setClass(this, QuestionsSet2Activity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
	}
	
//	public void btnImgAnswersOnTouch(){
//		btnImgAnswer1a.setOnTouchListener(new View.OnTouchListener() {
//			@Override			
//		    public boolean onTouch(View view, MotionEvent event) {
//				
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//                	//on touch
//                	setBtnBgOnTouchStyle(btnImgAnswer1a);
//                }
//                else if (event.getAction() == MotionEvent.ACTION_UP){
//                	//on release
//                	setBtnBgDefaultStyle(btnImgAnswer1a);
//                }
//                return false;
//		    }
//	    });
//		
//		btnImgAnswer1b.setOnTouchListener(new View.OnTouchListener() {
//			@Override			
//		    public boolean onTouch(View view, MotionEvent event) {
//				
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//                	//on touch
//                	setBtnBgOnTouchStyle(btnImgAnswer1b);
//                }
//                else if (event.getAction() == MotionEvent.ACTION_UP){
//                	//on release
//                	setBtnBgDefaultStyle(btnImgAnswer1b);
//                }
//                return false;
//		    }
//	    });
//	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		ImageView currentImageView = (ImageView) view;
		
        if (event.getAction() == MotionEvent.ACTION_DOWN){
        	//on touch
        	setBtnBgOnTouchStyle(currentImageView);
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
        	//on release
        	setBtnBgDefaultStyle(currentImageView);
        }
		return false;
	}
	
	@Override
	public void onBackPressed(){
//		MyTarget.targetToastMsg(getApplicationContext(), "Please Select One Of The Above Answers");
		MyService.isActivityActive = false;
		finish();
	}
	
	@Override
	public void onDestroy(){
		MyTarget.targetDebugMsg("onDestroy", MyTarget.DEBUG_MODE);
		super.onDestroy();
		MyService.isActivityActive = false;
	}
}