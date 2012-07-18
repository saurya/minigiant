package com.ctz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
	
	String[] allanswers = new String[question_bank_size];;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = new Bundle();
		
	
		bundle = this.getIntent().getExtras();
		user_selection = bundle.getString(user_selection);
		allquestions = bundle.getStringArray("allquestions");
		 allanswers = bundle.getStringArray(" allanswers");
		

		questions_list = new ArrayList<String>();
		answers_list = new ArrayList<String>();
		
		 {
			if (user_selection.contains("1"))
				copy("total");

			else if (user_selection.contains("3"))
				copy("senior");

			//setContentView(com.example.uscitizen.R.layout.listview);
			setListAdapter(new ArrayAdapter<String>(
		            this,R.layout.list_item,R.id.list_content, questions_list));
			ListView lv = getListView();//comnow lv.setBackgroundColor(R.color.silver);
			lv.setTextFilterEnabled(true);
		

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

	//	parent.setScrollIndicators(v, v);
		
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
	
	}

