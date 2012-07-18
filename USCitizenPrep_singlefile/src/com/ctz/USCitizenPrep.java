package com.ctz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.EditText;

public class USCitizenPrep extends Activity {
	
	static int question_bank_size = 100;
	static Toast toast;
	private final Handler toastHandler = new Handler();
	static int duration_of_toast_display = 2000;

	int[] randoms = new int[10];

	String selected;
	
	ArrayList<String> questions_list, answers_list;
	static int positionprev = 0;
	String[] allquestions = new String[question_bank_size];
	String[] nearlygood = new String[question_bank_size];
	String[] funny1 = new String[question_bank_size];
	String[] funny2 = new String[question_bank_size];
	String[] allanswers = new String[question_bank_size];;
	
	
	
	
	private String selected_type;
	private Button go_Button;
	private Button exit_Button;
	private RadioButton radio_prepare_for_interview;
	private RadioButton radio_test_yourself;
	private RadioButton radio_senior_prepare_for_interview;
    private static String user_selection;
   
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		read_Rawdata();
		setContentView(com.ctz.R.layout.main);
		questions_list = new ArrayList<String>();
		answers_list = new ArrayList<String>();
		
		final EditText edittext = (EditText) findViewById(R.id.edittext);

		edittext.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {

					Toast.makeText(USCitizenPrep.this, edittext.getText(),
							Toast.LENGTH_SHORT).show();
					return true;
				}
				return false;
			}
		});

		radio_prepare_for_interview = (RadioButton) findViewById(com.ctz.R.id.radio_prepare_for_interview);
		radio_test_yourself = (RadioButton) findViewById(com.ctz.R.id.radio_test_yourself);
		radio_senior_prepare_for_interview = (RadioButton) findViewById(com.ctz.R.id.radio_senior_prepare_for_interview);

		go_Button = (Button) findViewById(com.ctz.R.id.go);
		exit_Button = (Button) findViewById(com.ctz.R.id.exit);
		exit_Button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

		go_Button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				v.setBackgroundResource(R.drawable.android_pressed);
				//Toast.makeText(USCitizenPrep.this, "Button clicked",
						//Toast.LENGTH_SHORT).show();

				String current_activity="";
				Bundle bundle = new Bundle();
				Bundle qnbundle = new Bundle();
				Intent myIntent2 = new Intent();
				
				if (v == go_Button) {
					

					if (radio_test_yourself.isChecked() == true) {

						selected_type = new String("2");
						
				 {
							copy("random");
							
							
							String[] qns = new String[questions_list.size()];
							String[] ans = new String[answers_list.size()];
							Object[] qnobarr = questions_list.toArray();
							Object[] ansobarr = answers_list.toArray();
							for (int i = 0; i < 10; i++) {
								qns[i] = (String) qnobarr[i];// (String[]) questions_list.toArray()
								ans[i] = (String) ansobarr[i];
							}
							
							// bundle up the 10 relevant MCQ wrong answers
							String[] nearlygood_10 = new String[10];
							String[] funny1_10 = new String[10];
							String[] funny2_10 = new String[10];
							for (int i = 0; i < 10; i++) {
								nearlygood_10[i] = nearlygood[randoms[i]];
								funny1_10[i] = funny1[randoms[i]];
								funny2_10[i] = funny2[randoms[i]];
							}
							qnbundle.putStringArray("nearlygood_10", nearlygood_10);// deceptive
							qnbundle.putStringArray("funny1_10", funny1_10);// fake answerset 1
							qnbundle.putStringArray("funny2_10", funny2_10);// fake answerset 2
							
							// bundle up randomly selected question numbers needed for scoring
							qnbundle.putIntArray("originalQNums", randoms);
							
							// bundle up randomly selected test questions and answers
							qnbundle.putStringArray("randomqns", qns);
							qnbundle.putStringArray("anstorandomqns", ans);
							
							
						}
						
						
						
						

					}
					
					
					
					
					if (radio_prepare_for_interview.isChecked() == true) {
						selected_type = new String("1");
						
						copy("total");
						

					}
					if (radio_senior_prepare_for_interview.isChecked() == true) {

						selected_type = new String("3");
						copy("senior");
						
						
					}

				}
				
				String current_class="";
				if(selected_type.equals("1") ||selected_type.equals("3"))
					{
				StringBuilder sb = new StringBuilder().append(selected_type);
				bundle.putString(user_selection, sb.toString());
				
				
				
			bundle.putStringArray("allquestions",allquestions);
			 bundle.putStringArray(" allanswers",allanswers );
				myIntent2.putExtras(bundle);
				myIntent2.setClassName("com.ctz",
						"com.ctz.US_CitizenActivity");
				startActivity(myIntent2);
					}
				
				
				if(selected_type.equals("2") )
				{myIntent2.setClassName("com.ctz",
						"com.ctz.TestCTZ");
				
				myIntent2.putExtras(qnbundle);
				startActivity(myIntent2);
				}
				

			}
		});
	}
	public void copy(String s) {
		int start, end;

		if (s.equals("total")) {
			answers_list.clear();
			questions_list.clear();
			start = 0;
			end = allquestions.length;
			for (int i = start; i < end; i++) {
				questions_list.add(allquestions[i]);
				answers_list.add(allanswers[i]);
			}
			
		}

		else if (s.equals("random")) {
			answers_list.clear();
			questions_list.clear();
			Random rands = new Random();
			String randomstr = "aaa,";
			int nums = allquestions.length;			
			int k, count = 0;
			
			randoms = new int[10];
			while (count < 10) {
				k = rands.nextInt(nums);
				if (!randomstr.contains(k + "")) {
					randomstr += (k);
					randoms[count] = k;

					count++;
					questions_list.add("" + allquestions[k]);
					answers_list.add("" + allanswers[k]);
				}
			} // return (String[]) qns.toArray();
		}

		else if (s.equals("senior")) {
			answers_list.clear();
			questions_list.clear();
			start = 0;

			end = allquestions.length;
			Log.d("Length: ",end+"");
			for (int i = start; i < end; i++)
				if (allquestions[i].contains("?*")) {
					questions_list.add(allquestions[i]);
					answers_list.add("" + allanswers[i]);
				}
			
		}

	}
	private void read_Rawdata() {

		InputStream is1 = this.getResources().openRawResource(R.raw.allquestions);
		InputStreamReader isr1 = new InputStreamReader(is1);
		BufferedReader br1 = new BufferedReader(isr1);
		InputStream is2 = this.getResources().openRawResource(R.raw.allanswers);
		InputStreamReader isr2 = new InputStreamReader(is2);
		BufferedReader br2 = new BufferedReader(isr2);

		InputStream isMC1 = this.getResources().openRawResource(
				R.raw.multiplechoicewrongs);
		InputStreamReader isrMC1 = new InputStreamReader(isMC1);
		BufferedReader brMC1 = new BufferedReader(isrMC1);
		String[] testMC = new String[3];

		try {
			String test;
			int qnnum = 0;
			while (true) {if (qnnum>99)break;
				for (int i = 0; i < 3; i++) {
					testMC[i] = brMC1.readLine();
					Log.d("testMC:", testMC[i]);
					if (testMC[i] == null)
						break;
				}
				if (testMC[0] == null || testMC[1] == null || testMC[2] == null)
					break;

				nearlygood[qnnum] = testMC[0];
				funny1[qnnum] = testMC[1];
				funny2[qnnum] = testMC[2];

				test = br1.readLine();

				if (test == null)
					break;
				allquestions[qnnum] = test;
				test = br2.readLine();

				if (test == null)
					break;
				allanswers[qnnum] = test;
				qnnum++;
			}
			isr1.close();
			isMC1.close();
			isr2.close();
			isrMC1.close();
			br1.close();
			brMC1.close();
			br2.close();
			is1.close();
			is2.close();
			int end = allquestions.length;
			Log.d("Length: ",end+"");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}