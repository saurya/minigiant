package com.ctz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

public class USCitizenPrep extends Activity {
	private String testtype;
	private View.OnClickListener radio_listener = new View.OnClickListener() {
		public void onClick(View v) {
			// Perform action on clicks
			RadioButton rb = (RadioButton) v;
			Toast.makeText(USCitizenPrep.this, rb.getText(), Toast.LENGTH_SHORT)
					.show();
		}
	};
	private Button goButton;
	private Button exitButton;
	private RadioButton radio_prep;
	private RadioButton radio_test;
	private RadioButton radio_state;
	private RadioButton radio_senior;

	private TextView msgtxt;
	private static String param1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.ctz.R.layout.main);
		final Intent myIntent = new Intent();
		;
		;
		final Bundle bundle = new Bundle();
		// myIntent.setClassName("com.example.uscitizen",
		// "com.example.uscitizen.US_Citizen");
		 final EditText edittext = (EditText) findViewById(R.id.edittext);
		// edittext.setTextSize(Float.parseFloat("2"));
		 
			      edittext.setOnKeyListener(new OnKeyListener() {
			            public boolean onKey(View v, int keyCode, KeyEvent event) {
			                // If the event is a key-down event on the "enter" button
			                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
			                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
			                  // Perform action on key press
			                  Toast.makeText(USCitizenPrep.this, edittext.getText(), Toast.LENGTH_SHORT).show();
			                  return true;
			                }
			                return false;
			            }
			        });

		
		
		
		radio_prep = (RadioButton) findViewById(com.ctz.R.id.radio_prep);
		radio_test = (RadioButton) findViewById(com.ctz.R.id.radio_test);
		radio_state = (RadioButton) findViewById(com.ctz.R.id.radio_state);
		radio_senior = (RadioButton) findViewById(com.ctz.R.id.radio_senior);
		//msgtxt = (TextView) findViewById(com.ctz.R.id.selection);
		radio_prep.setOnClickListener(radio_listener);
		radio_test.setOnClickListener(radio_listener);
		radio_state.setOnClickListener(radio_listener);
		radio_senior.setOnClickListener(radio_listener);
		goButton = (Button) findViewById(com.ctz.R.id.go);
		exitButton = (Button) findViewById(com.ctz.R.id.exit);
		exitButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				finish();
			}
		});

		goButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				v.setBackgroundResource(R.drawable.android_pressed);
	            Toast.makeText(USCitizenPrep.this, "Button clicked", Toast.LENGTH_SHORT).show();

				Intent myIntent = new Intent();
				myIntent.setClassName("com.example.uscitizen",
						"com.example.uscitizen.US_CitizenActivity");

				Bundle bundle = new Bundle();

				if (v == goButton) {
					if (radio_prep.isChecked() == true) {
						testtype = new String("1");

						//msgtxt.setText("Selected is : " + radio_prep.getText());

					}
					// msgtxt.setVisibility(BIND_AUTO_CREATE);
					if (radio_test.isChecked() == true) {

						testtype = new String("2");
						//msgtxt.setText("Selected is : " + radio_test.getText());
					}
					if (radio_senior.isChecked() == true) {
					//	msgtxt.setText("Selected is : "
								//+ radio_senior.getText());
						testtype = new String("4");
					}
					if (radio_state.isChecked() == true) {
						//msgtxt.setText("Selected is : " + radio_state.getText());
						testtype = new String("3");
					}
				}
				StringBuilder sb = new StringBuilder().append(testtype);
				bundle.putString(param1, sb.toString());
				myIntent.putExtras(bundle);

				startActivity(myIntent);

			}
		});
	}

}