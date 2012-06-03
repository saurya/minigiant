package com.example.uscitizen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewActivity1 extends Activity {
	private Button closebutton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(com.example.uscitizen.R.layout.main);
		closebutton = (Button) findViewById(com.example.uscitizen.R.id.button_id);

		closebutton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// startTest(v);
			}
		});

	}

	public void startTest(View view) {
		startActivity(new Intent(this, com.example.uscitizen.US_CitizenActivity.class));
	}

}