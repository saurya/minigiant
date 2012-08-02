package com.ctz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ctz.SimpleGestureFilter.SimpleGestureListener;

@SuppressLint("NewApi")
public class PrepNoTimer extends Activity implements OnGestureListener,
    SimpleGestureListener {

	boolean bingo, done;
	private GestureDetector gestureScanner;
	static Toast toast;

	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	EditText edittext;//
	private TextView mGetQuestionString;
	private TextView mgetAnswerString;
	// private EditText mtimerTextField;
	private TextView mScore;
	private SimpleGestureFilter detector;
	int questionnumber;
	// private Button mnext;

	int cnt = 0;
	int score = 0;

	// private Button mcancel;
	int cnt1, ctz_ans, correct_ans;
	// CountDownTimer runtimer;
	String correctanswerstring;
	// multiple choice answers display

	final static long seconds_in_milllies = 1000L;
	final static long minutes_in_millies = seconds_in_milllies * 60;
	final static long hours_in_millies = minutes_in_millies * 60;
	static AlertDialog.Builder testscores_dialog_builder, builder;
	static AlertDialog testscores_dialog, alert;
	// in onTick
	// final Runnable r;
	// Array containing randomly generated question numbers
	int[] originalQNums = new int[100];
	ImageButton mbtnclose_normal;
	TextView mgetReport;
	ImageButton mbtn_check_on;
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
		detector = new SimpleGestureFilter(this, this);
		bingo = false;
		int score = 0;
		gestureScanner = new GestureDetector(this);

		setContentView(R.layout.main5);// following lines 'must' to follow layout
		mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
		mgetAnswerString = (TextView) findViewById(R.id.getAnswerString);
		mScore = (TextView) findViewById(R.id.scoreField);
		setDefaultKeyMode(DEFAULT_KEYS_DISABLE);
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();

		qnlist = bundle.getStringArray("allquestions");
		anslist = bundle.getStringArray("allanswers");
		int begin = 0, end = 0;
		begin = 0;
		end = qnlist.length;

		Log.d("length: ", qnlist.length + ">*>*>&>*>&>*>&>*>&*>&>*>&>&*&"
		    + anslist.length);
		getAnswerString0 = anslist[begin];
		getquestionString0 = "Question# 1: " + qnlist[begin];// first question
		mGetQuestionString.setText(getquestionString0);
		mgetAnswerString.setText(getAnswerString0);
		currentdisplay = 0;
		originalQnumber = originalQNums[0];
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		super.dispatchTouchEvent(me);
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	public void onDoubleTap() {
		callrestofthecode();

	}

	public boolean onSingleTapUp() {
		callrestofthecode();

		return false;
	}

	public void callrestofthecode() {

		{
			cnt++;
			ctz_ans = -1;
			getquestionString = getnextqn(cnt);
			if (getquestionString == null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(PrepNoTimer.this);
				builder
				    .setMessage(
				        "Congratulations! You just finished Practice of all Questions! ")
				    .setCancelable(false)
				    .setPositiveButton("Home", new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int id) {
						    PrepNoTimer.this.finish();
					    }
				    });

				AlertDialog alert = builder.create();
				alert.show();

			}
			mGetQuestionString.setText(getquestionString);
			getAnswerString = getnextanswer(cnt);
			mgetAnswerString.setText(getAnswerString);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		return gestureScanner.onTouchEvent(me);
	}

	public boolean onDown(MotionEvent e) {

		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	    float velocityY) {

		return false;
	}

	public void onLongPress(MotionEvent e) {
		return;
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	    float distanceY) {

		return false;
	}

	public void onShowPress(MotionEvent e) {
		return;
	}

	public boolean onSingleTapUp(MotionEvent e) {

		return true;
	}

	public String getnextqn(int cnt) {
		int qnnumber = cnt + 1;
		if (qnnumber > qnlist.length)
			return null;
		;
		mScore.setText(qnnumber + "/100");
		return "Qusestion# " + qnnumber + ": " + qnlist[cnt];
	}

	public String getnextanswer(int cnt) {
		int qnnumber = cnt + 1;
		if (qnnumber > anslist.length) {

			return null;
		}
		return "Answer" + ": " + anslist[cnt];

	}

}
