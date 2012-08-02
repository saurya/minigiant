package com.ctz;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Gather_UserChoices extends Activity {

	static int question_bank_size = 100;
	static Toast toast;
	static int duration_of_toast_display = 2000;

	int[] randoms = new int[100];
	String selected, currentstate = "";
	HashMap<String, String> currentgovernors;
	ArrayList<String> questions_list, answers_list;
	static int positionprev = 0;
	String[] allquestions = new String[question_bank_size];
	String[] allanswers = new String[question_bank_size];;
	String senator_of_state = "", governor_of_state = "";
	String[] state = new String[100];
	String[] sentr = new String[100];

	private static String[] questionslice_size = { "3", "10", "30", "45", "100" };
	private static String[] timerslice_size = { "3", "10", "15", "0" };

	int userselectQns;
	int userselecttiming;

	private final int numberofrounds = 0;
	Bundle bundle;
	private Button go_Button;

	/** Called when the activity is first created. */
	@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	public void setvalues() {
		bundle.putInt("userselecttiming", userselecttiming);
		bundle.putInt("userselectQns", userselectQns);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bundle = new Bundle();
		bundle = this.getIntent().getExtras();

		setContentView(com.ctz.R.layout.main4);

		final Spinner questionslice_spinner = (Spinner) findViewById(R.id.questionslice_spinner);
		final Spinner timerslice_spinner = (Spinner) findViewById(R.id.timerslice_spinner);
		ArrayAdapter<CharSequence> qnadapter = new ArrayAdapter<CharSequence>(this,
		    android.R.layout.simple_spinner_item, questionslice_size);

		ArrayAdapter<CharSequence> timeadapter = new ArrayAdapter<CharSequence>(
		    this, android.R.layout.simple_spinner_item, timerslice_size);

		qnadapter
		    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		questionslice_spinner.setAdapter(qnadapter);
		questionslice_spinner.setFocusable(true);

		timeadapter
		    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timerslice_spinner.setAdapter(timeadapter);
		timerslice_spinner.setFocusable(true);

		go_Button = (Button) findViewById(com.ctz.R.id.go);

		go_Button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(Gather_UserChoices.this, "Practice Test# 1",
				    Toast.LENGTH_SHORT).show();
				{// avoid 0's
					userselecttiming = 3;
					userselectQns = 3;

					userselecttiming = Integer.parseInt(timerslice_spinner
					    .getSelectedItem().toString());
					userselectQns = Integer.parseInt(questionslice_spinner
					    .getSelectedItem().toString());

					setvalues();
					Intent myIntent2User = new Intent();
					myIntent2User.putExtras(bundle);

					myIntent2User.setClassName("com.ctz", "com.ctz.TestCTZ1");
					startActivity(myIntent2User);
				}
			}
		});

	}

}
