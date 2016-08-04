package com.kyriakosAlexandrou.phdresearch;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.kyriakosAlexandrou.phdresearch.targetdefault.MyTarget;

public class QuestionsSet2Activity extends Activity implements OnTouchListener{
	private ImageView btnImgAnswer2a;
	private ImageView btnImgAnswer2b;
	private ImageView btnImgAnswer2c;
	private ImageView btnImgAnswer2d;
	private ImageView btnImgAnswer2e;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions_set2);
		
		MyService.isActivityActive = true;
		bindViews();
		setBtnDefaultValues();
//		btnImgAnswersOnTouch();
		
		   final Handler handler = new Handler();
		   
			final Runnable r = new Runnable()
			{
			    public void run() 
			    {
					btnImgAnswersOnClick(btnImgAnswer2c);									
			    }
			};
			handler.postDelayed(r, 2000); 
	}
	
	public void setBtnDefaultValues(){
		setBtnBgDefaultStyle(btnImgAnswer2a);
		setBtnBgDefaultStyle(btnImgAnswer2b);
		setBtnBgDefaultStyle(btnImgAnswer2c);
		setBtnBgDefaultStyle(btnImgAnswer2d);
		setBtnBgDefaultStyle(btnImgAnswer2e);
		
		btnImgAnswer2a.setOnTouchListener(this);
		btnImgAnswer2b.setOnTouchListener(this);
		btnImgAnswer2c.setOnTouchListener(this);
		btnImgAnswer2d.setOnTouchListener(this);
		btnImgAnswer2e.setOnTouchListener(this);
	}
	
	public void bindViews(){
		btnImgAnswer2a = (ImageView) findViewById(R.id.btnImgAnswer2a);
		btnImgAnswer2b = (ImageView) findViewById(R.id.btnImgAnswer2b);
		btnImgAnswer2c = (ImageView) findViewById(R.id.btnImgAnswer2c);
		btnImgAnswer2d = (ImageView) findViewById(R.id.btnImgAnswer2d);
		btnImgAnswer2e = (ImageView) findViewById(R.id.btnImgAnswer2e);
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
		WriteToTxtFile writeToTxtFile = new WriteToTxtFile(this);
		
		String questionAndAnswer = getString(R.string.answer_q2a);
		String completeRow;
		
		switch(view.getId()){
			case R.id.btnImgAnswer2a:
				MyTarget.targetDebugMsg("btnImgAnswer2a selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q2a);
				setBtnBgOnTouchStyle(btnImgAnswer2a);             
				break;
			case R.id.btnImgAnswer2b:
				MyTarget.targetDebugMsg("btnImgAnswer2b selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q2b);
				setBtnBgOnTouchStyle(btnImgAnswer2b);
				break;
			case R.id.btnImgAnswer2c:
				MyTarget.targetDebugMsg("btnImgAnswer2c selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q2c);
				setBtnBgOnTouchStyle(btnImgAnswer2c);             
				break;
			case R.id.btnImgAnswer2d:
				MyTarget.targetDebugMsg("btnImgAnswer2d selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q2d);
				setBtnBgOnTouchStyle(btnImgAnswer2d);
				break;
			case R.id.btnImgAnswer2e:
				MyTarget.targetDebugMsg("btnImgAnswer2e selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_q2e);
				setBtnBgOnTouchStyle(btnImgAnswer2e);
				break;
			default:
				break;	
		}
		
		completeRow = "\t" + questionAndAnswer + "\r\n";
		writeToTxtFile.writeToFile(completeRow, MyTarget.FILENAME_PHD_RESEARCH_DATA, true);
		
		// after selecting an answer close the last set of questions
		MyTarget.targetToastMsg(getApplicationContext(), "Success 2!!");
		finish();
	}
	
//	public void btnImgAnswersOnTouch(){
//		btnImgAnswer2a.setOnTouchListener(new View.OnTouchListener() {
//			@Override			
//		    public boolean onTouch(View view, MotionEvent event) {
//				
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//                	//on touch
//                	setBtnBgOnTouchStyle(btnImgAnswer2a);
//                }
//                else if (event.getAction() == MotionEvent.ACTION_UP){
//                	//on release
//                	setBtnBgDefaultStyle(btnImgAnswer2a);
//                }
//                return false;
//		    }
//	    });
//		
//		btnImgAnswer2b.setOnTouchListener(new View.OnTouchListener() {
//			@Override			
//		    public boolean onTouch(View view, MotionEvent event) {
//				
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//                	//on touch
//                	setBtnBgOnTouchStyle(btnImgAnswer2b);
//                }
//                else if (event.getAction() == MotionEvent.ACTION_UP){
//                	//on release
//                	setBtnBgDefaultStyle(btnImgAnswer2b);
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