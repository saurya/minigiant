package com.ctz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PrepNoTimer extends Activity {

	boolean bingo, done;
	private GestureDetector gestureScanner;
	static Toast toast;

	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	EditText edittext;//
	private TextView mGetQuestionString;
	private TextView mgetAnswerString;

	private TextView mScore;
	private Button mnext;
	private Button mprev;

	int cnt = 0;
	int score = 0;
	int questionnumber;
	int cnt1, ctz_ans, correct_ans;

	String correctanswerstring;

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

		bingo = false;
		int score = 0;

		setContentView(R.layout.main5);// following lines 'must' to follow layout
		mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
		mgetAnswerString = (TextView) findViewById(R.id.getAnswerString);
		mScore = (TextView) findViewById(R.id.scoreField);
		mnext = (Button) findViewById(com.ctz.R.id.next);
		mprev = (Button) findViewById(com.ctz.R.id.prev);
		mprev.setText("<");
		setDefaultKeyMode(DEFAULT_KEYS_DISABLE);
		mnext.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				cnt++;
				Log.d("Next: ", cnt + "*****************************************");
				if (cnt > 99) {

					AlertDialog.Builder testscores_dialog_builder = new AlertDialog.Builder(
					    PrepNoTimer.this);
					testscores_dialog_builder
					    .setMessage(
					        "Congratulations! You just finished Practice of all Questions! ")
					    .setCancelable(false)
					    .setPositiveButton("Home", new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog, int id) {
							    PrepNoTimer.this.finish();
						    }
					    });
					testscores_dialog = testscores_dialog_builder.create();
					testscores_dialog.show();
				}

				else {
					getquestionString = getnextqn(cnt);
					Log.d("Next: ", cnt
					    + "*****************************************else1");
					mGetQuestionString.setText(getquestionString);
					getAnswerString = getnextanswer(cnt);
					Log.d("Next: ", cnt
					    + "*****************************************else2");
					mgetAnswerString.setText(getAnswerString);
					Log.d("Next: ", cnt
					    + "*****************************************else3");
				}
			}
		}

		);

		mprev.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (cnt > 99)
					cnt = 99;

				if (cnt <= 0) {

					cnt = 0;
					mGetQuestionString.setText(getquestionString0);
					mgetAnswerString.setText(getAnswerString0);
				}

				else {

					cnt--;
					getquestionString = getpreviousqn(cnt);
					mGetQuestionString.setText(getquestionString);
					getAnswerString = getpreviousanswer(cnt);
					mgetAnswerString.setText(getAnswerString);
				}
			}
		}

		);

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
		getquestionString0 = "Q: " + qnlist[begin];// first question
		mGetQuestionString.setText(getquestionString0);
		mgetAnswerString.setText(getAnswerString0);
		currentdisplay = 0;
		originalQnumber = originalQNums[0];
	}

	public void callrestofthecode() {

		{
			cnt++;
			ctz_ans = -1;
			getquestionString = getnextqn(cnt);
			if (getquestionString.equals("Done!")) {
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

	public String getpreviousqn(int cnt) {
		if (cnt < 0)
			cnt = 0;
		if (cnt > 99)
			cnt = 99;
		int qnnumber = cnt;
		mScore.setText((qnnumber + 1) + "/100");
		return "Q: " + (qnnumber + 1) + ": " + qnlist[cnt];
	}

	public String getpreviousanswer(int cnt) {

		return "A: " + ": " + anslist[cnt];

	}

	public String getnextqn(int cnt) {
		if (cnt < 0)
			cnt = 0;
		if (cnt > 99)
			cnt = 99;
		int qnnumber = cnt + 1;

		mScore.setText((qnnumber) + "/100");
		return "Qusestion# " + (qnnumber) + ": " + qnlist[cnt];
	}

	public String getnextanswer(int cnt) {
		int qnnumber = cnt + 1;

		return "A: " + anslist[cnt];

	}

}
