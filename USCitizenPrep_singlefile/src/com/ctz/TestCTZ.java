package com.ctz;

import java.util.HashMap;

import java.util.Random;

import com.ctz.R.color;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;

public class TestCTZ<radiobtnGrp> extends Activity {
	EditText edittext;//
	private TextView mGetQuestionString;
	int questionnumber;
	private Button mnext;
	int cnt = 0, score = 0;
	private Button getscores;
	private Button mcancel;
	int cnt1, ctz_ans, correct_ans;

	String correctanswerstring;
	// multiple choice answers display
	private RadioGroup radiobtnGrp;
	private RadioButton radio_ans1;
	private RadioButton radio_ans2;
	private RadioButton radio_ans3;
	private RadioButton radio_ans4;

	// Array containing randomly generated question numbers
	int[] originalQNums = new int[10];

	String getansString1 = "";
	String getansString2 = "";
	String getansString3 = "";
	String getansString4 = "";

	String getquestionString0 = "";// set first question to be displayed
	String multipleChoiceAnswer = "";

	String getquestionString = "";// and the next ..
	String[] nearlygood_10 = new String[10];// deceptive answerset
	String[] funny1_10 = new String[10];// fake answerset 1
	String[] funny2_10 = new String[10];// fake answerset 2

	String multipleChoiceAnswers = "";
	String setanswerString = "";
	String scoreString = "Your Score Now Is";

	String[] qnlist;// retrieve current 10 questions of the test
	String[] anslist;// retrieve current 10 answers of the test
	String[] ctznanslist;// corresponding answerset

	String ctznans;// user-selected answerset
	int currentdisplay;
	int originalQnumber;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

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

		// form a tring to verify if user got the answer right
		correctanswerstring = "";
		for (int i = 0; i < 10; i++)
			correctanswerstring += originalQNums[i] + ",";

		getquestionString0 = "Question# 1: " + qnlist[0];// first question
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main2);
		mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
		mGetQuestionString.setBackgroundColor(color.maroon);
		mGetQuestionString.setText(getquestionString0);

		mnext = (Button) findViewById(R.id.next);
		mcancel = (Button) findViewById(R.id.cancel);
		getscores = (Button) findViewById(R.id.getscores);
		radiobtnGrp = (RadioGroup) findViewById(R.id.radiobtnGrp);
		radio_ans1 = (RadioButton) findViewById(R.id.radio_ans1);
		radio_ans2 = (RadioButton) findViewById(R.id.radio_ans2);
		radio_ans3 = (RadioButton) findViewById(R.id.radio_ans3);
		radio_ans4 = (RadioButton) findViewById(R.id.radio_ans4);

		currentdisplay = 0;
		originalQnumber = originalQNums[0];

		setRadioButtonText(currentdisplay, originalQnumber);

		mnext.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				cnt++;
				ctz_ans = -1;
				if (v == mnext) {
					int checkedRadioButton = radiobtnGrp
							.getCheckedRadioButtonId();

					switch (checkedRadioButton) {
					case R.id.radio_ans1:
						ctz_ans = 0;
						break;
					case R.id.radio_ans2:
						ctz_ans = 1;
						break;
					case R.id.radio_ans3:
						ctz_ans = 2;
						break;
					case R.id.radio_ans4:
						ctz_ans = 3;
						break;
					}
					if (ctz_ans == correct_ans)
						score++;
				}

				radiobtnGrp.clearCheck();
				if (cnt == 10) {

					Toast.makeText(TestCTZ.this,
							"Your score is " + score + "\nRestarting!",
							Toast.LENGTH_LONG).show();
					score = 0;
					cnt = 0;
					mGetQuestionString.setText(getquestionString0);
					originalQnumber = originalQNums[cnt];
					setRadioButtonText(cnt, originalQnumber);

				} else {
					getquestionString = getnextqn(cnt);
					mGetQuestionString.setText(getquestionString);
					cnt1 = originalQNums[cnt];

					setRadioButtonText(cnt, cnt1);

				}
			}
		}

		);
		mcancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

		getscores.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				calculateScores();
				Toast.makeText(TestCTZ.this, scoreString, Toast.LENGTH_LONG)
						.show();

			}
		});

	}

	public void onRadioButtonClicked(View v) {
		RadioButton rb = (RadioButton) v;

		Toast.makeText(TestCTZ.this, rb.getText(), Toast.LENGTH_SHORT).show();
	}

	private void setRadioButtonText(int currentans, int correspondingxtraans) {
		int count = 0;
		String randomstr = "";
		Random rands = new Random();
		int k;
		int[] randoms;
		randoms = new int[4];
		while (count < 4) {
			k = rands.nextInt(4);
			if (!randomstr.contains(k + "")) {
				randomstr += (k);
				randoms[count] = k;
				count++;
			}
		}

		HashMap<Integer, String> map = new HashMap<Integer, String>(
				randoms.length);
		map.put(0, anslist[currentans]);
		map.put(1, nearlygood_10[currentans]);
		map.put(2, funny1_10[currentans]);
		map.put(3, funny2_10[currentans]);

		radio_ans1.setText((String) map.get(randoms[0]));
		radio_ans2.setText((String) map.get(randoms[1]));
		radio_ans3.setText((String) map.get(randoms[2]));
		radio_ans4.setText((String) map.get(randoms[3]));

		for (int i = 0; i < 4; i++)
			if (randoms[i] == 0)
				correct_ans = i;

	}

	public void calculateScores() {

		scoreString = "Your score is  " + score;

	}

	public String getnextqn(int cnt) {
		int qnnumber = cnt + 1;
		return "Qusestion# " + qnnumber +": "+ qnlist[cnt] +"Answer to previous Question: "+ anslist[cnt - 1];
	}

}
