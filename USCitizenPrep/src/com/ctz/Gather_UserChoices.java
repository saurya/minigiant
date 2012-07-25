package com.ctz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;


public class Gather_UserChoices extends Activity {
	
	static int question_bank_size = 100;
	static Toast toast;
	private final Handler toastHandler = new Handler();
	static int duration_of_toast_display = 2000;
	private float mScaleFactor = 1.f;
	int[] randoms = new int[100];
	private ScaleGestureDetector mScaleDetector;
	String selected,currentstate="";
	HashMap<String, String> currentgovernors;
	ArrayList<String> questions_list, answers_list;
	static int positionprev = 0;
	String[] allquestions = new String[question_bank_size];
	String[] nearlygood = new String[question_bank_size];
	String[] funny1 = new String[question_bank_size];
	String[] funny2 = new String[question_bank_size];
	String[] allanswers = new String[question_bank_size];;
	String senator_of_state="",governor_of_state="";
	String[] state=new String[100];
	private static  String []statenames;
	String[] sentr=new String[100];
	private String selected_type;
	
	
	private static  String []questionslice_size={"10","30","100"};;
	private static  String []timerslice_size={"10","15","0"};
	
	int userselectQns;
	int userselecttiming;
	
	private RadioButton radio_prepare_for_interview;
	private RadioButton radio_test_yourself;
	private RadioButton radio_senior_prepare_for_interview;
    private static String user_selection;
    private static String user_state;
  private int numberofrounds=0;
   //  private TextView  mnumberofQns;
   //  private TextView mtimingperqn;
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
        
		//mnumberofQns= (TextView) findViewById(com.ctz.R.id.numberofQns);
	//	 mtimingperqn=(TextView) findViewById(com.ctz.R.id.timingperqn);
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
				//finish();
			}
		});
		go_Button.setOnClickListener(new OnClickListener() {
//sending questions too many tims But works for now MAjor Fix required 23rd July
			public void onClick(View v) {numberofrounds++;
				//v.setBackgroundResource(R.drawable.android_pressed);
				//findViewById(R.id.state_spinner).setBackgroundResource(R.drawable.android_pressed);
				Toast.makeText(Gather_UserChoices.this, "Practice Test# "+numberofrounds,
						Toast.LENGTH_SHORT).show();

				//it is go_button, da!!
				if (v == go_Button) {
					
					
				//	userselecttiming=Integer.parseInt((mtimingperqn.getText()).toString());
					//userselectQns=Integer.parseInt((mnumberofQns.getText()).toString());
					
					userselecttiming=Integer.parseInt(timerslice_spinner.getSelectedItem().toString());	
					userselectQns=Integer.parseInt(questionslice_spinner.getSelectedItem().toString());	
					
					setvalues();
					
					Intent myIntent2User = new Intent();
					// if( numberofrounds>1)bundle.clear();
					bundle.putInt("numberofrounds", numberofrounds);
					
					myIntent2User.putExtras(bundle);
				
					myIntent2User.setClassName("com.ctz",
					"com.ctz.TestCTZ1");
			startActivity(myIntent2User);
					}
				
				
				
				

			}
		});
		
	}
	 
	    
	
	
	
	
	
	
	
	

	}
