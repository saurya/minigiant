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
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;

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
public class TestCTZ1 extends Activity implements  OnGestureListener,SimpleGestureListener{
	private GestureLibrary gLib;
	private static final String TAG = "com.ctz";
	 boolean bingo,done;private GestureDetector gestureScanner ;
	 static Toast toast;
		private final Handler toastHandler = new Handler();
	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
		
	EditText edittext;//
	private TextView mGetQuestionString;
	private TextView mgetAnswerString;
	private EditText mtimerTextField;
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
	CountDownTimer runtimer;
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
	String getquestionString = "";// and the next ..
	String[] nearlygood_10 = new String[100];// deceptive answerset
	String[] funny1_10 = new String[100];// fake answerset 1
	String[] funny2_10 = new String[100];// fake answerset 2
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

	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 detector = new SimpleGestureFilter(this,this);
		bingo=false;
		gestureScanner = new GestureDetector(this);
		setContentView(R.layout.main2);//following lines 'must' to follow layout
		
	
		setDefaultKeyMode(DEFAULT_KEYS_DISABLE);
		// getMultiChoiceData();
		// get ten random qns, and corresponding answers and their original
		// numbers
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		nearlygood_10 = bundle.getStringArray("nearlygood_10");
		funny1_10 = bundle.getStringArray("funny1_10");
		funny2_10 = bundle.getStringArray("funny2_10");
		qnlist = bundle.getStringArray("randomqns");
		anslist = bundle.getStringArray("anstorandomqns");
		originalQNums = bundle.getIntArray("originalQNums");
		 userselectQns= bundle.getInt("userselectQns");
		 userselecttiming= bundle.getInt("userselecttiming");
		// form a tring to verify if user got the answer right
		correctanswerstring = "";
		for (int i = 0; i < 100; i++)
			correctanswerstring += originalQNums[i] + ",";
		getAnswerString0 = anslist[0];
		getquestionString0 = "Question# 1: " + qnlist[0];// first question
		
		
		 int hrs=0,mins=0;String timedisplay="";
		 
		
		 mgetReport=(TextView) findViewById(R.id.getReport);
		mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
		mgetAnswerString = (TextView) findViewById(R.id.getAnswerString);
		mScore=(EditText)findViewById(R.id.scoreField);
	//	mnext = (Button) findViewById(R.id.next);
		
		
	
		//mnext.setClickable(false);
		mtimerTextField=(EditText) findViewById(R.id. timerTextField );
		 mtotalView=(RelativeLayout)findViewById(R.id.totalView);		
		 mbtnclose_normal=(ImageButton)	findViewById(R.id.btn_close_normal);
		 mbtn_check_on=(ImageButton)	findViewById(R.id.btn_check_on);
		//mGetQuestionString.setBackgroundColor(color.maroon);
		mGetQuestionString.setText(getquestionString0);
		//mgetAnswerString.setText(getAnswerString0);
		mgetAnswerString.setVisibility(View.GONE); mbtnclose_normal.setVisibility(View.GONE);
		 mbtn_check_on.setVisibility(View.GONE);
		mgetReport.setVisibility(View.GONE);
	   // mnext.setVisibility(View.GONE);
		 ;
		calltimer(PreferenceManager.getDefaultSharedPreferences(this));
	
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
	 public void onDoubleTap() {
		 mgetAnswerString.setVisibility(View.VISIBLE);mbtnclose_normal.setVisibility(View.VISIBLE);
		 mbtn_check_on.setVisibility(View.VISIBLE); mbtnclose_normal.setClickable(true);
		 mbtn_check_on.setClickable(true);
	   // Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show(); 
	 }
	 public boolean onSingleTapUp() {
		 mgetAnswerString.setVisibility(View.VISIBLE);mbtnclose_normal.setVisibility(View.VISIBLE);
		 mbtn_check_on.setVisibility(View.VISIBLE);
			 mbtnclose_normal.setClickable(true);
			 mbtn_check_on.setClickable(true);
		  //  Toast.makeText(this, "Single Tap", Toast.LENGTH_SHORT).show(); 
		    return false;
		 }
	public void newOnclick2(View v){
	//Toast.makeText(TestCTZ1.this,anslist[cnt],
		//Toast.LENGTH_SHORT).show();
		
	bingo=false;
	callrestofthecode();
	}
	
	public void newOnclick(View v){
		
		if(!done)
		{bingo=true;
	callrestofthecode();
	
	}
	
	}
	public void callrestofthecode(){	
	runtimer.cancel();
		//if ( mbtn_check_on.isSelected())
//	if(!bingo)mgetAnswerString.setVisibility(View.VISIBLE);
	
		{SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);;
					cnt++;
					ctz_ans = -1;
					 {//mnext.setClickable(false);
					  //  calltimer(prefs);
					    calculateScores();
						mScore.setText( scoreString);
						
					}

			
					if (cnt ==userselectQns) {
						/*runtimer.cancel();
						Handler handler = new Handler(); 
					    handler.postDelayed(new Runnable() { 
					         public void run() { 
					        	
					         } 
					    }, 45);*/
						decideVisibility(true);//mbtn_check_on.setClickable(true);//mbtnclose_normal.setClickable(true);
						mgetReport.setText(getreportString());
						mgetReport.setVisibility(View.VISIBLE);
						
						
					/*	testscores_dialog_builder = new AlertDialog.Builder(TestCTZ1.this);mgetReport.setText(getreportString());
						testscores_dialog_builder.setMessage("Your score is " + score + "\nRestart the test?")
						       .setCancelable(false)
						       .setPositiveButton("Generate Score Report?", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) { 
						        	   
						        	   mgetReport.setVisibility(View.VISIBLE);decideVisibility(true);
						      	 dialog.cancel();
						      	decideVisibility(false);
						         runtimer.start();
	                            }
						       })
						       .setNegativeButton("No", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						               finish();
						        	  
						           }
						       });
						testscores_dialog = testscores_dialog_builder.create();
	                    testscores_dialog.show();
						
						score = 0;
						cnt = 0;
						mGetQuestionString.setText(getquestionString0);
					//	mgetAnswerString.setText(getAnswerString0);
						originalQnumber = originalQNums[cnt];
						
						mGetQuestionString.setVisibility(View.VISIBLE);
					//	mgetAnswerString.setVisibility(View.VISIBLE);
					//	mnext.setVisibility(View.VISIBLE);
						mtimerTextField.setVisibility(View.VISIBLE);;
						mScore.setVisibility(View.VISIBLE);mScore.setText( "    ");mbtn_check_on.setClickable(true);
						//mnext.setVisibility(View.VISIBLE);;
					//	mnext.setClickable(false);
*/
					} else {
						decideVisibility(false);
						//mgetAnswerString.setVisibility(View.GONE);
						getquestionString = getnextqn(cnt);
						mGetQuestionString.setText(getquestionString);
						
						mgetAnswerString.setText(anslist[cnt]);
						mgetAnswerString.setVisibility(View.GONE);
						cnt1 = originalQNums[cnt];runtimer.start();

					}
				}
			}
	public String getreportString(){
		return("Your Scores Based On Self-Evaluation: "+"\n"+"Total Questions Available From  Website: \n 100"+"\n"+
		       "Number of Questions you selected to practise in non-random fashion \n"+
				userselectQns+"\n"+
		       "Time I allocated to answer each Question "+"\n"+
				userselecttiming+"seconds \n"+"I know answers for: "+"\n"+
		        score+"\n"+
				"I Missed answers for: "+"\n"+
		        (userselectQns-score) +" Questions\n"
		        	);
		}
	public void decideVisibility(boolean hide){
		
		if(hide)
		{mbtn_check_on.setVisibility(View.GONE);
		mbtnclose_normal.setVisibility(View.GONE);
			mgetAnswerString.setVisibility(View.GONE);
		mGetQuestionString.setVisibility(View.GONE);
		mtimerTextField.setVisibility(View.GONE);
		
		 }
		else
		{
			mgetAnswerString.setVisibility(View.VISIBLE);
		mGetQuestionString.setVisibility(View.VISIBLE);
		mtimerTextField.setVisibility(View.VISIBLE);
		mbtnclose_normal.setVisibility(View.GONE);
		 mbtn_check_on.setVisibility(View.GONE);
		 mbtn_check_on.setClickable(true);
			mbtnclose_normal.setClickable(true);}
		
		
		
	}
	
	private void calltimer( SharedPreferences prefs){mbtn_check_on.setClickable(true);
		//mcheckBox2.setFocusable(false);
		// mnext.setEnabled(false);
	/*	if(mcheckBox2.isChecked()){
 			mcheckBox2.toggle();
 			score++;
 		   
 		};
		 if(mbtn_check_on.isSelected())
		 {
	 			mcheckBox2.toggle();
	 			score++;
	 		   
	 		};*/
		//mnext.setFocusable(true);
		int hrs=0,mins=0;String timedisplay="";done=false;
		 long timer = prefs.getLong("TIME", userselecttiming* 1000);//set your testtime limit here
		runtimer=	 new CountDownTimer(timer,1000) {
         
          public void onTick(long elapsed) {
		    	
		    		
		    	 mtimerTextField.setText(""+elapsed/1000);
		     }
          
		     public void onFinish() {
		    	 mtimerTextField.setText("done!");//mbtn_check_on.setClickable(false);
		    	// done=true;
		    	 bingo=false;
		    	 mgetAnswerString.setVisibility(View.VISIBLE);
		    	 
		 		
		    	 
		    	// mcheckBox2.setFocusable(true);
		    	// mnext.setVisibility(View.VISIBLE);
		    	 
		    	// if (finalswipe=="Right" || mnext.isSelected())getNext();
		    	// mnext.setEnabled(true);
		     }
		  }.start();
		 
}
	//Use this method for hours duration
	
	private void calltimerinhrs( SharedPreferences prefs){
		int hrs=0,mins=0;String timedisplay="";
		 long timer = prefs.getLong("TIME", 3700000);//set your testtime limit here
		runtimer=	 new CountDownTimer(timer,1000) {
         
          public void onTick(long elapsed) {
        	  
		    		
		    	 mtimerTextField.setText(( elapsed/3600000)%60+ ":" +(( elapsed/60000)%60 <10?"0"+( elapsed/60000)%60:( elapsed/60000)%60)+" :"+(elapsed/1000)%60);
		     }
          
		     public void onFinish() {
		    	 mtimerTextField.setText("done!");done=true;
		     }
		  }.start();
		 
		
	}
	  public void clickNew(View v)
	    {
	        Toast.makeText(this, "Show some text on the screen.", Toast.LENGTH_LONG).show();
	    }
	    
	public void onRadioButtonClicked(View v) {
		RadioButton rb = (RadioButton) v;

	}

	public void calculateScores() {//if (done)return;
      if(bingo )
    	  if (!done)score++;
      	scoreString =  score+"/"+cnt;
        bingo=false;
     
    	  
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
	    {mgetAnswerString.setVisibility(View.VISIBLE);
	    mbtnclose_normal.setVisibility(View.VISIBLE);
		 mbtn_check_on.setVisibility(View.VISIBLE);
		 mbtnclose_normal.setClickable(true);
		 mbtn_check_on.setClickable(true);
	       // Toast.makeText(TestCTZ1.this, "Single-Tap event ", Toast.LENGTH_LONG).show();
	        return true;
	    }
	public String getnextqn(int cnt) {
		int qnnumber = cnt + 1;
		return "Qusestion# " + qnnumber +": "+ qnlist[cnt] ;
	}

}
