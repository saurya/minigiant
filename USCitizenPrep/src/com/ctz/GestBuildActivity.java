package com.ctz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi", "NewApi", "NewApi", "NewApi" })
public class GestBuildActivity extends Activity  implements OnGesturePerformedListener{
	

		 
		private GestureLibrary gLib;
		private static final String TAG = "com.trial";

		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.main3);

		     gLib = GestureLibraries.fromRawResource(this,R.raw.gestures);
		     if (!gLib.load()) {
		          Log.w(TAG, "could not load gesture library");
		       finish();
		}

		    GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		  // gestures.addOnGesturePerformedListener(handleGestureListener);
		    
		    gestures.addOnGesturePerformedListener(this);

		}

		 public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		        ArrayList<Prediction> predictions = gLib.recognize(gesture);
		        
		        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
		            
		        	String action = predictions.get(0).name;
		        	
		            Toast.makeText(this, action, Toast.LENGTH_SHORT).show();
		        }
		    }
		    

		}