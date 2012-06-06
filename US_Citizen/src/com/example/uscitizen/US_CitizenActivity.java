package com.example.uscitizen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class US_CitizenActivity extends ListActivity {
	
	static int question_bank_size = 100;
	static Toast toast;
	private final Handler toastHandler = new Handler();
	static int duration_of_toast_display = 2000;

	int[] randoms = new int[10];

	String selected;
	String user_selection;
	ArrayList<String> questions_list, answers_list;
	static int positionprev = 0;
	String[] allquestions = new String[question_bank_size];
	String[] nearlygood = new String[question_bank_size];
	String[] funny1 = new String[question_bank_size];
	String[] funny2 = new String[question_bank_size];
	String[] allanswers = new String[question_bank_size];;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();

		read_Rawdata();
		user_selection = bundle.getString(user_selection);

		questions_list = new ArrayList<String>();
		answers_list = new ArrayList<String>();
		if (user_selection.contains("2")) {
			copy("random");
			Intent myIntent2 = new Intent();
			myIntent2.setClassName("com.testctz", "com.testctz.TestCTZ");
			Bundle qnbundle = new Bundle();
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
			
			myIntent2.putExtras(qnbundle);
			startActivity(myIntent2);
		}

		else {
			if (user_selection.contains("1"))
				copy("total");

			else if (user_selection.contains("3"))
				copy("senior");

			setContentView(com.example.uscitizen.R.layout.listview);
			ListView lv = getListView();
			lv.setTextFilterEnabled(true);

			setListAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, questions_list));

		}
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
			// return (String[]) qns.toArray();
		}

		else if (s.equals("random")) {
			answers_list.clear();
			questions_list.clear();
			Random rands = new Random();
			String randomstr = "aaa,";
			int nums = allquestions.length;// lengths are differing?

			// int nums = answers.length;
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
			for (int i = start; i < end; i++)
				if (allquestions[i].contains("?*")) {
					questions_list.add(allquestions[i]);
					answers_list.add("" + allanswers[i]);
				}
			System.out.println(answers_list.size() + "is the length");
		}

	}

	public void showDoubleToast(int repeats) {

		toast.show();
		for (int i = 1; i <= repeats; i++) {
			// show again
			toastHandler.postDelayed(new Runnable() {

				public void run() {
					toast.show();
				}
			}, i * duration_of_toast_display);
		}
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {

		parent.setScrollIndicators(v, v);
		parent.setSelection(position);

		toast = Toast.makeText(this, questions_list.get(position) + '\n' + '\n'
				+ answers_list.get(position), 2 * Toast.LENGTH_LONG);

		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);

		TextView v2 = (TextView) toast.getView().findViewById(
				android.R.id.message);
		v2.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.android_normal));
		v2.setTextSize(28);

		showDoubleToast(2);

	}

	// read all data into program. filenames reveal content
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
				for (int i = 0; i < 3; i++) {
					testMC[i] = brMC1.readLine();
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
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}