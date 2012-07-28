package com.ctz;


import java.util.ArrayList;





import android.app.AlertDialog;

import android.app.ListActivity;
import android.content.Context;

import android.graphics.Canvas;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class US_CitizenActivity extends ListActivity {
	AlertDialog.Builder builder;
	AlertDialog alertDialog;
	Context mContext;
	View layout;
	TextView dialog_text;
	Canvas canvas;
	ImageView image ;
	
	static int question_bank_size = 100;
	static Toast toast;
	private final Handler toastHandler = new Handler();
	
	static int duration_of_toast_display = 2000;
	LinearLayout activeItem;
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
		mContext = US_CitizenActivity.this;		
		LinearLayout  layoutoriginal=new LinearLayout(mContext);		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.custom_dialog,
		                               (ViewGroup) findViewById(R.id.layout_root));
		dialog_text = (TextView) layout.findViewById(R.id.text);
		
		image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(R.drawable.android_normal);

		builder = new AlertDialog.Builder(mContext);
		builder.setView(layout);
		alertDialog = builder.create();
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
			ArrayAdapter<String> myarraylist=		
			new ArrayAdapter<String>(
		    this,R.layout.list_item,R.id.list_content, questions_list);
		    setListAdapter(myarraylist);
		    canvas=new Canvas();		   
		    layoutoriginal.draw(canvas);
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
	private int selectedItem;
	public void setSelectedItem(int position) {
	    selectedItem = position;
	}
    public View getView(int position, View convertView, ViewGroup parent) {
        setSelectedItem(position);
        activeItem = (LinearLayout) convertView;
		    if (position == selectedItem  )
		    {
		    	
		    	Animation stringrotate = AnimationUtils.loadAnimation(mContext, R.animator.property_stringanimator);
		    	stringrotate.setDuration(1000);
		    	activeItem.setAnimation(stringrotate);
		    		
		    }
		    
		   
        return new View(parent.getContext());
    }
	
	public void onListItemClick(ListView parent, View v, int position, long id) {

		parent.setSelection(position);
		setSelectedItem(position);
		getView( position, v,  parent);
		dialog_text.setText(answers_list.get(position));
		alertDialog.show();
	
	}
	
	}

