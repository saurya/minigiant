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
import android.widget.Toast;
import android.widget.EditText;

public class USCitizenPrep extends Activity {
	
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
		setContentView(com.ctz.R.layout.main);
		final Intent myIntent = new Intent();
		final Bundle bundle = new Bundle();
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
				Toast.makeText(USCitizenPrep.this, "Button clicked",
						Toast.LENGTH_SHORT).show();

				Intent myIntent = new Intent();
				myIntent.setClassName("com.ctz",
						"com.ctz.US_CitizenActivity");

				Bundle bundle = new Bundle();

				if (v == go_Button) {
					if (radio_prepare_for_interview.isChecked() == true) {
						selected_type = new String("1");

					}

					if (radio_test_yourself.isChecked() == true) {

						selected_type = new String("2");

					}
					if (radio_senior_prepare_for_interview.isChecked() == true) {

						selected_type = new String("3");
					}

				}
				
				StringBuilder sb = new StringBuilder().append(selected_type);
				bundle.putString(user_selection, sb.toString());
				myIntent.putExtras(bundle);

				startActivity(myIntent);

			}
		});
	}

}