package com.ctz;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Random;

import com.ctz.R.color;
import com.ctz.SimpleGestureFilter.SimpleGestureListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
//import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import android.widget.TextView;
import android.widget.Toast;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;
@SuppressLint("NewApi")
public class PrepNoTimer extends Activity implements  OnGestureListener,SimpleGestureListener{
	private GestureLibrary gLib;
	private static final String TAG = "com.ctz";
	 boolean bingo,done;private GestureDetector gestureScanner ;
	 static Toast toast;
		private final Handler toastHandler = new Handler();
	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
		
	EditText edittext;//
	private TextView mGetQuestionString;
	private TextView mgetAnswerString;
//	private EditText mtimerTextField;
	private EditText mScore;
	private CheckBox mcheckBox2;
	private RelativeLayout mtotalView;
	 private SimpleGestureFilter detector;
	int questionnumber;
	//private Button mnext;
	private Button exit_button;
	int cnt = 0; int  score = 0;
	private Button getscores;
	//private Button mcancel;
	int cnt1, ctz_ans, correct_ans;
	//CountDownTimer runtimer;
	String correctanswerstring;
	// multiple choice answers display
	private RadioGroup radiobtnGrp;
	private RadioButton radio_ans1;
	private RadioButton radio_ans2;
	private RadioButton radio_ans3;
	private RadioButton radio_ans4;
	final static long seconds_in_milllies = 1000L;
	final static long minutes_in_millies = seconds_in_milllies * 60;
	final static long hours_in_millies = minutes_in_millies * 60;
	static AlertDialog.Builder testscores_dialog_builder,builder;
	static AlertDialog testscores_dialog,alert;
	// in onTick
	//final Runnable r; 
	// Array containing randomly generated question numbers
	int[] originalQNums = new int[100];
	ImageButton  mbtnclose_normal;
	TextView mgetReport;
	ImageButton  mbtn_check_on;
	String getansString1 = "";
	String getansString2 = "";
	String getansString3 = "";
	String getansString4 = "";
	String getAnswerString0 = "";
	String getquestionString0 = "";// set first question to be displayed
	String multipleChoiceAnswer = "";
	String getAnswerString = "";
	String getquestionString = "";// and the next .
	
String[] allquestions = new String[100];
	
	String[] allanswers = new String[100];;
	
	String[] nearlygood_10; // deceptive answerset
	String[] funny1_10; // fake answerset 1
	String[] funny2_10; // fake answerset 2
	int userselectQns;
	String multipleChoiceAnswers = "";
	String setanswerString = "";
	String scoreString = "Your Score Now Is";
	int userselecttiming;
	String[] qnlist;// retrieve current 100 questions of the test
	String[] anslist;// retrieve current 100 answers of the test
	String[] ctznanslist;// corresponding answerset
	Handler handler = new Handler(); 
	String ctznans;// user-selected answerset
	int currentdisplay;
	int originalQnumber;
	static String finalswipe;
int numberofrounds;
	/** Called when the activity is first created. */
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 detector = new SimpleGestureFilter(this,this);
		bingo=false;
		gestureScanner = new GestureDetector(this);
		
		setContentView(R.layout.main5);//following lines 'must' to follow layout
		setDefaultKeyMode(DEFAULT_KEYS_DISABLE);
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		
		qnlist = bundle.getStringArray("allquestions");
		anslist = bundle.getStringArray(" allanswers");
	
	
		int begin=0,end=0;boolean ended=false;;
		begin=0;end=100;
		
		for (int i = begin; i <  end; i++)
			
		    getAnswerString0 = anslist[begin];
		getquestionString0 = "Question# 1: " + qnlist[begin];// first question
		
		
		
		mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
		mgetAnswerString = (TextView) findViewById(R.id.getAnswerString);
		
		mGetQuestionString.setText(getquestionString0);
		mgetAnswerString.setText(getAnswerString0);
		currentdisplay = 0;
		originalQnumber = originalQNums[0];
	}
	 @Override 
	 public boolean dispatchTouchEvent(MotionEvent me){ 
		  super.dispatchTouchEvent(me);
	   this.detector.onTouchEvent(me);
	  return super.dispatchTouchEvent(me); 
	 }
	 public void onDoubleTap() {callrestofthecode();
		
	 }
	 public boolean onSingleTapUp() {
		 callrestofthecode();
		
		    return false;
		 }
	
	
	
	public void callrestofthecode(){	

	
		{SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);;
					cnt++;
					ctz_ans = -1;
					

				
						getquestionString = getnextqn(cnt);
						if(getquestionString==null)this.finish();
						mGetQuestionString.setText(getquestionString);
						getAnswerString = getnextanswer(cnt);
						mgetAnswerString.setText(getAnswerString);
						
						

					}
				}
			
	

	
	
	 

	
	 @Override
	    public boolean onTouchEvent(MotionEvent me)
	    {
	        return gestureScanner.onTouchEvent(me);
	    }
	 
	    public boolean onDown(MotionEvent e)
	    {
	     
	        return false;
	    }
	 
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	    {
	      
	        return false;
	    }
	 
	    public void onLongPress(MotionEvent e)
	    {
	    	return ;
	    }
	 
	    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	    {
	        
	        return false;
	    }
	 
	    public void onShowPress(MotionEvent e)
	    {
	        return;
	    }
	 
	    public boolean onSingleTapUp(MotionEvent e)
	    {
	   
		 
	        return true;
	    }
	public String getnextqn(int cnt) {
		int qnnumber = cnt + 1;if(qnnumber>100)return null;;
		return "Qusestion# " + qnnumber +": "+ qnlist[cnt] ;
	}
	

	
	 public String  getnextanswer(int cnt) {
			int qnnumber = cnt + 1;if(qnnumber>100)return null;;
			return "Answer"  +": "+anslist[cnt] ;
			
		}
	
	

}
