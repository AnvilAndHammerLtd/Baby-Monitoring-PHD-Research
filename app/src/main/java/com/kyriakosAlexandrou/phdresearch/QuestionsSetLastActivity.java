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

public class QuestionsSetLastActivity extends Activity implements OnTouchListener{
	private ImageView btnImgAnswerLastA;
	private ImageView btnImgAnswerLastB;
	private ImageView btnImgAnswerLastC;
	private ImageView btnImgAnswerLastD;
	private WriteToTxtFile writeToTxtFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions_set_last);
		
		bindViews();
		setBtnDefaultValues();
		writeToTxtFile = new WriteToTxtFile(this);
		
		//TO BE REMOVED AFTER TESTING
		   final Handler handler = new Handler();   
			final Runnable r = new Runnable()
			{
			    public void run() 
			    {
					btnImgAnswersOnClick(btnImgAnswerLastB);									
			    }
			};
			handler.postDelayed(r, 5000); 
	}
	
	public void setBtnDefaultValues(){
		setBtnBgDefaultStyle(btnImgAnswerLastA);
		setBtnBgDefaultStyle(btnImgAnswerLastB);
		setBtnBgDefaultStyle(btnImgAnswerLastC);
		setBtnBgDefaultStyle(btnImgAnswerLastD);
		
		btnImgAnswerLastA.setOnTouchListener(this);
		btnImgAnswerLastB.setOnTouchListener(this);
		btnImgAnswerLastC.setOnTouchListener(this);
		btnImgAnswerLastD.setOnTouchListener(this);
	}
	
	public void bindViews(){
		btnImgAnswerLastA = (ImageView) findViewById(R.id.btnImgAnswerLastA);
		btnImgAnswerLastB = (ImageView) findViewById(R.id.btnImgAnswerLastB);
		btnImgAnswerLastC = (ImageView) findViewById(R.id.btnImgAnswerLastC);
		btnImgAnswerLastD = (ImageView) findViewById(R.id.btnImgAnswerLastD);
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
			case R.id.btnImgAnswerLastA:
				MyTarget.targetDebugMsg("btnImgAnswerLastA selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_last_a);
				setBtnBgOnTouchStyle(btnImgAnswerLastA);             
				break;
			case R.id.btnImgAnswerLastB:
				MyTarget.targetDebugMsg("btnImgAnswerLastB selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_last_b);
				setBtnBgOnTouchStyle(btnImgAnswerLastB);
				break;
			case R.id.btnImgAnswerLastC:
				MyTarget.targetDebugMsg("btnImgAnswerLastC selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_last_c);
				setBtnBgOnTouchStyle(btnImgAnswerLastC);
				break;
			case R.id.btnImgAnswerLastD:
				MyTarget.targetDebugMsg("btnImgAnswerLastD selected", MyTarget.DEBUG_MODE);
				questionAndAnswer = getString(R.string.answer_last_d);
				setBtnBgOnTouchStyle(btnImgAnswerLastD);
				break;
			default:
				break;	
		}
		
		completeRow = "\r\n" + date + "\t" + time + "\t" + questionAndAnswer + "\r\n**********************************************************\r\n";
		writeToTxtFile.writeToFile(completeRow, MyTarget.FILENAME_PHD_RESEARCH_DATA, true);
				
		// after selecting an answer display the new set of questions
		MyTarget.targetToastMsg(getApplicationContext(), "Thank You Very Much For Your Time");
		finish();
	}
	
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
}