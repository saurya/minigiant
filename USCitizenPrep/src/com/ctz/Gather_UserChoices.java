package com.ctz;


import java.util.ArrayList;
import java.util.HashMap;

import android.R.color;
import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
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
	String selected,currentstate="";
	HashMap<String, String> currentgovernors;
	ArrayList<String> questions_list, answers_list;
	static int positionprev = 0;
	String[] allquestions = new String[question_bank_size];
	String[] allanswers = new String[question_bank_size];;
	String senator_of_state="",governor_of_state="";
	String[] state=new String[100];
	String[] sentr=new String[100];

	private static  String []questionslice_size={"3","10","30","45","100"};
	private static  String []timerslice_size={"3","10","15","0"};
	
	int userselectQns;
	int userselecttiming;
	

  private int numberofrounds=0;
    Bundle bundle;
    private Button go_Button;
	private Button exit_Button;
	/** Called when the activity is first created. */
	@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	
	public void  setvalues(){bundle.putInt("userselecttiming", userselecttiming);
	bundle.putInt("userselectQns", userselectQns);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		
	
		setContentView(com.ctz.R.layout.main4);
	
	
		final Spinner	 questionslice_spinner = (Spinner) findViewById(R.id.questionslice_spinner);
		final Spinner	 timerslice_spinner = (Spinner) findViewById(R.id.timerslice_spinner);
		ArrayAdapter qnadapter = new ArrayAdapter(
								this,
								android.R.layout.simple_spinner_item, 
								questionslice_size);
	
		ArrayAdapter timeadapter = new ArrayAdapter(
								this,
								android.R.layout.simple_spinner_item, 
								timerslice_size);
		
		qnadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		questionslice_spinner.setBackgroundColor(color.background_dark);
				questionslice_spinner.setAdapter(qnadapter);questionslice_spinner.setFocusable(true);
				questionslice_spinner.setVisibility(1);

		timeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timerslice_spinner.setBackgroundColor(color.background_dark);
		timerslice_spinner.setAdapter(timeadapter);timerslice_spinner.setFocusable(true);
		timerslice_spinner.setVisibility(1);
		go_Button = (Button) findViewById(com.ctz.R.id.go);
		exit_Button = (Button) findViewById(com.ctz.R.id.exit);
		exit_Button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				 AlertDialog.Builder builder = new AlertDialog.Builder(Gather_UserChoices.this);
				builder.setMessage("Are you sure you want to exit?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   Gather_UserChoices.this.finish();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
			}
		});
		go_Button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(Gather_UserChoices.this, "Practice Test# 1",
						Toast.LENGTH_SHORT).show();
				{//avoid 0's
				    userselecttiming=3;
					userselectQns=3;
					
					userselecttiming=Integer.parseInt(timerslice_spinner.getSelectedItem().toString());	
					userselectQns=Integer.parseInt(questionslice_spinner.getSelectedItem().toString());	
					
					setvalues();
					Intent myIntent2User = new Intent();
					myIntent2User.putExtras(bundle);
				
					myIntent2User.setClassName("com.ctz",
					"com.ctz.TestCTZ1");
			startActivity(myIntent2User);
					}
			}
		});
		
	}

	}
