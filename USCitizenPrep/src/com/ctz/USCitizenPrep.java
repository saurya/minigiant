package com.ctz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class USCitizenPrep extends Activity implements
    OnGesturePerformedListener {

	static int userselectQns;;
	static int userselecttiming;

	static Toast toast;
	static int duration_of_toast_display = 2000;

	int[] randoms = new int[100];

	String selected;
	static String currentstate;// just to keep it going
	HashMap<String, String> currentgovernors;
	ArrayList<String> questions_list, answers_list;
	static int positionprev = 0;
	String[] states = new String[50];
	static int question_bank_size = 100;
	static String[] allquestions = new String[question_bank_size];
	static String[] allanswers = new String[question_bank_size];
	static String[] statenames;
	static String statename = "";

	String senator_of_state = "", governor_of_state = "";
	String[] state = new String[100];
	String[] sentr = new String[100];

	HashMap<String, String> statesncaps;
	HashMap<String, String> statenabbrevs;

	private String selected_type;
	private Button go_Button;
	// private Button exit_Button;

	private RadioButton radio_prepare_for_interview;
	private RadioButton radio_test_yourself;
	private CheckBox radio_senior_prepare_for_interview;
	private static String user_selection;

	private GestureLibrary gLib;

	/** Called when the activity is first created. */
	@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stateData();
		read_Rawdata();

		setContentView(com.ctz.R.layout.main);
		/*
		 * GestureOverlayView gOverlay = (GestureOverlayView)
		 * findViewById(R.id.gestures);
		 * gOverlay.addOnGesturePerformedListener(USCitizenPrep.this); gLib =
		 * GestureLibraries.fromRawResource(this, R.raw.gestures); if (!gLib.load())
		 * { finish(); }
		 */
		questions_list = new ArrayList<String>();
		answers_list = new ArrayList<String>();
		final Spinner spinner = (Spinner) findViewById(R.id.state_spinner);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
		    android.R.layout.simple_spinner_item, states);

		adapter
		    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setFocusable(true);

		radio_senior_prepare_for_interview = (CheckBox) findViewById(com.ctz.R.id.radio_senior_prepare_for_interview);
		radio_prepare_for_interview = (RadioButton) findViewById(com.ctz.R.id.radio_prepare_for_interview);
		radio_test_yourself = (RadioButton) findViewById(com.ctz.R.id.radio_test_yourself);

		go_Button = (Button) findViewById(com.ctz.R.id.go);
		/*
		 * exit_Button = (Button) findViewById(com.ctz.R.id.exit);
		 * 
		 * exit_Button.setOnClickListener(new OnClickListener() {
		 * 
		 * public void onClick(View v) {
		 * 
		 * AlertDialog.Builder builder = new
		 * AlertDialog.Builder(USCitizenPrep.this);
		 * builder.setMessage("Are you sure you want to exit?")
		 * .setCancelable(false) .setPositiveButton("Yes", new
		 * DialogInterface.OnClickListener() { public void onClick(DialogInterface
		 * dialog, int id) { USCitizenPrep.this.finish(); } })
		 * .setNegativeButton("No", new DialogInterface.OnClickListener() { public
		 * void onClick(DialogInterface dialog, int id) { dialog.cancel(); } });
		 * AlertDialog alert = builder.create(); alert.show();
		 * 
		 * } });
		 */
		go_Button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Bundle bundle = new Bundle();
				Bundle qnbundle = new Bundle();
				Intent myIntent2 = new Intent();
				File file = new File("/sdcard/govdataactual.txt");
				if (file.exists())
					file.delete();
				file = new File("/sdcard/senatordataactual.txt");
				if (file.exists())
					file.delete();

				if (v == go_Button) {
					currentstate = "AK";// default in case nothing selected
					currentstate = spinner.getSelectedItem().toString();

					new Justdownload(currentstate);
					allanswers[43] = capital();
					Log.d("Spinner Selection", currentstate
					    + "***************&&&&&&&&&&&&&&&&&&^^^^^^^^^^^^^^^^^^"
					    + allanswers[19] + allanswers[42]);
					if (radio_test_yourself.isChecked() == true
					    && radio_senior_prepare_for_interview.isChecked() == false) {
						selected_type = new String("2");
						{
							copy("random");
							String[] qns = new String[questions_list.size()];
							String[] ans = new String[answers_list.size()];
							Object[] qnobarr = questions_list.toArray();
							Object[] ansobarr = answers_list.toArray();
							for (int i = 0; i < questions_list.size(); i++) {
								qns[i] = (String) qnobarr[i];// (String[])
								                             // questions_list.toArray()
								ans[i] = (String) ansobarr[i];
							}
							qnbundle.putIntArray("originalQNums", randoms);
							qnbundle.putStringArray("randomqns", qns);
							qnbundle.putStringArray("anstorandomqns", ans);

						}
					}

					if (radio_prepare_for_interview.isChecked() == true
					    && radio_senior_prepare_for_interview.isChecked() == false) {
						selected_type = new String("1");
						copy("total");
					}

					if (radio_senior_prepare_for_interview.isChecked() == true
					    && (radio_prepare_for_interview.isChecked() == true || radio_test_yourself
					        .isChecked() == true)) {
						selected_type = new String("1");
						copy("senior");
					}
				}

				if (selected_type == null) {
					return;
				}

				if (radio_prepare_for_interview.isChecked() == true
				    && radio_senior_prepare_for_interview.isChecked() == false) {
					StringBuilder sb = new StringBuilder().append(selected_type);
					bundle.putString(user_selection, sb.toString());
					bundle.putStringArray("allquestions", allquestions);
					bundle.putStringArray("allanswers", allanswers);

					myIntent2.putExtras(bundle);
					myIntent2.setClassName("com.ctz", "com.ctz.PrepNoTimer");
					startActivity(myIntent2);
				}
				if (selected_type.equals("2")
				    && radio_senior_prepare_for_interview.isChecked() == false) {
					myIntent2.setClassName("com.ctz", "com.ctz.Gather_UserChoices");
					myIntent2.putExtras(qnbundle);
					startActivity(myIntent2);
				}

				if ((selected_type.equals("1") || selected_type.equals("2"))
				    && radio_senior_prepare_for_interview.isChecked() == true) {

					qnbundle.clear();
					copy("senior");
					String[] qns = new String[questions_list.size()];
					String[] ans = new String[answers_list.size()];
					Object[] qnobarr = questions_list.toArray();
					Object[] ansobarr = answers_list.toArray();
					for (int i = 0; i < questions_list.size(); i++) {
						qns[i] = (String) qnobarr[i];// (String[]) questions_list.toArray()
						ans[i] = (String) ansobarr[i];
					}
					qnbundle.putStringArray("allquestions", qns);
					qnbundle.putStringArray("allanswers", ans);
					myIntent2.setClassName("com.ctz", "com.ctz.PrepNoTimer");
					myIntent2.putExtras(qnbundle);
					startActivity(myIntent2);
				}

			}
		});
	}

	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	public void onItemSelected(AdapterView<?> parent, View v, int position,
	    long id) {
		Toast.makeText(USCitizenPrep.this, "Button clicked" + statenames[position],
		    Toast.LENGTH_SHORT).show();

	}

	@SuppressLint({ "NewApi", "NewApi" })
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = gLib.recognize(gesture);

		if (predictions.size() > 0 && predictions.get(0).score > 1.0) {

			String action = predictions.get(0).name;

			Toast.makeText(USCitizenPrep.this, action, Toast.LENGTH_SHORT).show();
		}
	}

	public void onNothingSelected(AdapterView<?> parent) {
		Toast.makeText(USCitizenPrep.this, "", Toast.LENGTH_SHORT).show();
	}

	public String capital() {
		String currcapital;
		currcapital = statenabbrevs.get(currentstate);
		return (statesncaps.get(currcapital));
	}

	public void copy(String s) {
		int start, end;
		answers_list.clear();
		questions_list.clear();
		if (s.equals("total")) {

			start = 0;
			end = allquestions.length;
			for (int i = start; i < end; i++) {

				questions_list.add(allquestions[i]);
				answers_list.add(allanswers[i]);
			}

		} else if (s.equals("random")) {
			Random rands = new Random();
			String randomstr = "aaa,";
			int nums = 10;
			Set<Integer> collect = new HashSet<Integer>();

			int count = 0, k = 0;
			int justcount = 0;
			for (int countslice = 1; countslice <= 10; countslice++) {
				rands = new Random();
				randomstr = "aaa";
				count = 0;
				while (count < 10) {
					if (collect.size() == 100)
						break;
					k = rands.nextInt(nums);
					if (!randomstr.contains(k + "")) {
						randomstr += (k);
						randoms[justcount] = 10 * (countslice - 1) + k;
						count++;
						justcount++;
						collect.add(new Integer(10 * (countslice - 1) + k));

					}
				}
				if (collect.size() == 100)
					break;
			}

			Iterator<Integer> it = collect.iterator();
			while (it.hasNext()) {
				k = it.next();
				questions_list.add("" + allquestions[randoms[k]]);
				answers_list.add("" + allanswers[randoms[k]]);
			}

		}

		else if (s.equals("senior")) {

			start = 0;

			end = allquestions.length;
			Log.d(
			    "Length: ",
			    end
			        + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			for (int i = start; i < end; i++)
				if (allquestions[i].contains("?*")) {
					questions_list.add(allquestions[i]);
					answers_list.add("" + allanswers[i]);
				}

		}

	}

	public void stateData() {
		String line;
		int cnt = 0;
		statenames = new String[50];
		String key = "";
		String value = "";
		statesncaps = new HashMap<String, String>();
		statenabbrevs = new HashMap<String, String>();
		try {
			InputStream is1 = this.getResources().openRawResource(R.raw.statencaps);
			InputStreamReader isr1 = new InputStreamReader(is1);
			BufferedReader br = new BufferedReader(isr1);
			while ((line = br.readLine()) != null) {
				if (cnt % 3 == 0) {
					key = line;
				}
				if (cnt % 3 == 1) {
					statenabbrevs.put(line, key);

				}
				if (cnt % 3 == 2) {
					value = line;
					statesncaps.put(key, value);

				}
				cnt++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		states = new String[50];

		Iterator<String> it = statenabbrevs.keySet().iterator();
		int cntr = -1;
		while (it.hasNext()) {
			cntr++;
			String s = it.next();
			statenames[cntr] = s;
			states[cntr] = statenabbrevs.get(s);

		}
		for (int i = 0; i < 50; i++)
			System.out.println(states[i].toString());
		Arrays.sort(states);
		Arrays.sort(statenames);
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
			while (true) {
				if (qnnum > 99)
					break;
				for (int i = 0; i < 3; i++) {
					testMC[i] = brMC1.readLine();
					// Log.d("testMC:", testMC[i]);
					if (testMC[i] == null)
						break;
				}
				if (testMC[0] == null || testMC[1] == null || testMC[2] == null)
					break;

				test = br1.readLine();

				if (test == null)
					break;

				// StringEntity entity = new StringEntity(jsonTaakkaart.toString(),
				// "UTF-8");
				allquestions[qnnum] = test.toString();
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

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}