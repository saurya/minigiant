package com.ctz;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

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

public class USCitizenPreppart1 extends Activity implements
    OnGesturePerformedListener {

  private boolean gov_exist, sen_exist;
  static Toast toast;
  static int duration_of_toast_display = 2000;
  // Foll vars used across once a state and senior selected
  static String original42, original19, currentcapital43;
  static boolean is_a_senior;;
  // that's it
  private boolean changedstate;// if user selects a different state in the same
                               // session

  String selected;
  static String currentstate;// just to keep it going
  HashMap<String, String> currentgovernors;

  static int positionprev = 0;
  String[] states = new String[50];

  static String[] statenames;
  static String statename = "a";

  String senator_of_state = "a", governor_of_state = "a";
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
  String currentcapital, previouscapital;
  private GestureLibrary gLib;

  public boolean capital() {
    changedstate = false;
    Log.d("correct?", currentstate + "   " + statesncaps.get(currentstate)
        + "*&*&*&^*&^&*&*&^*&^");
    currentcapital = statesncaps.get(currentstate);
    currentcapital43 = currentcapital;
    Log.d("prevcap && currcap", currentcapital + " " + previouscapital
        + "*********************^^^^^^^^^^^^^^^$%%%%%%%%%%%%%%%%%%%%");
    if (previouscapital.contains(currentcapital)
        || currentcapital.contains(previouscapital)) {
      ;
    } else {
      changedstate = true;
      previouscapital = currentcapital;
    }
    return (changedstate);
  }

  public void fillgovernordata() {
    Log.d("prevcap && currcap", currentcapital + "  trying governordata"
        + "*********************^^^^^^^^^^^^^^^$%%%%%%%%%%%%%%%%%%%%"
        + currentstate);
    String governor = "bc";

    new Justdownload(currentstate);
    FileInputStream fIn = null;
    BufferedReader myReader = null;
    try {
      fIn = new FileInputStream("/sdcard/govdataactual.txt");
      myReader = new BufferedReader(new InputStreamReader(fIn));

      String onlyone = "";

      while ((onlyone = myReader.readLine()) != null) {
        governor += onlyone + "\n";

      }
    } catch (Exception e) {

    } finally {
      try {
        if ((myReader == null) == false)
          myReader.close();
        if ((myReader == null) == false)
          fIn.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if (governor.contains(currentstate)) {

      original42 = governor + "junk";
      Log.d("onlyone ",
          "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM" + "  "
              + currentstate);
      gov_exist = true;// for popup message
    }

  }

  public void fillsenatordata() {// close ???
    Log.d("prevcap && currcap", currentcapital + "  trying senatordata"
        + "*********************^^^^^^^^^^^^^^^$%%%%%%%%%%%%%%%%%%%%");

    String senator = "ab";
    FileInputStream fIn = null;
    BufferedReader myReader = null;
    try {
      fIn = new FileInputStream("/sdcard/senatordataactual.txt");
      myReader = new BufferedReader(new InputStreamReader(fIn));

      String onlyone = "";
      while ((onlyone = myReader.readLine()) != null) {
        senator += onlyone + "\n";

      }
    } catch (Exception e) {

    } finally {
      try {
        myReader.close();
        fIn.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    Log.d("Hello onlyoneonlyone ",
        "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMsenator" + senator
            + currentstate);
    if (senator.contains(currentstate)) {
      original19 = senator;
      Log.d("Hello onlyone ",
          "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM" + senator
              + "  " + currentstate);
      sen_exist = true;
    }
  }

  /** Called when the activity is first created. */
  @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    stateData();

    currentstate = "Alabama";
    previouscapital = "Montgomery";
    setContentView(com.ctz.R.layout.mainforpart1);

    gov_exist = false;
    sen_exist = false;// governor and senator
    // olddata exists or

    final Spinner spinner = (Spinner) findViewById(R.id.state_spinner);
    // spinner.setBackgroundColor(color.aspinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.states, R.layout.spinnerview);

    adapter.setDropDownViewResource(R.layout.spinnerview);
    spinner.setAdapter(adapter);
    spinner.setFocusable(true);

    radio_senior_prepare_for_interview = (CheckBox) findViewById(com.ctz.R.id.radio_senior_prepare_for_interview);

    go_Button = (Button) findViewById(com.ctz.R.id.go);

    go_Button.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        original19 = "Senator: ";
        original42 = "Governor: ";

        currentcapital43 = "C";
        is_a_senior = false;
        Log.d("prev Spinner Selection0", currentstate
            + "***************&&&&&&&&&&&&&&&&&&^^^^^^^^^^^^^^^^^^"
            + original19 + original42 + currentcapital43);

        gov_exist = false;
        sen_exist = false;
        // not

        Intent myIntent2 = new Intent();

        {
          is_a_senior = radio_senior_prepare_for_interview.isChecked();

          currentstate = spinner.getSelectedItem().toString();
          changedstate = capital();// currentstate has to be fulllength for this
                                   // method

          // send abbreviation 6/20/2012
          Iterator<String> it = statenabbrevs.keySet().iterator();

          while (it.hasNext()) {

            String currentstateabbrev = it.next();

            if (statenabbrevs.get(currentstateabbrev).equals(currentstate))

            {
              currentstate = currentstateabbrev;
              break;
            }

          }

          fillgovernordata();// staledata still can use

          fillsenatordata();
          myIntent2.setClassName("com.ctz", "com.ctz.USCitizenPreppart2");
          startActivity(myIntent2);
        }

      }
    });
  }

  @SuppressLint({ "NewApi", "NewApi" })
  public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
    ArrayList<Prediction> predictions = gLib.recognize(gesture);

    if (predictions.size() > 0 && predictions.get(0).score > 1.0) {

      String action = predictions.get(0).name;

      Toast.makeText(USCitizenPreppart1.this, action, Toast.LENGTH_SHORT)
          .show();
    }
  }

  @SuppressLint({ "NewApi", "NewApi", "NewApi" })
  public void onItemSelected(AdapterView<?> parent, View v, int position,
      long id) {
    Toast.makeText(USCitizenPreppart1.this,
        "Button clicked" + statenames[position], Toast.LENGTH_SHORT).show();

  }

  public void onNothingSelected(AdapterView<?> parent) {
    Toast.makeText(USCitizenPreppart1.this, "", Toast.LENGTH_SHORT).show();
  }

  public void stateData() {
    String line;
    int cnt = 0;
    statenames = new String[50];
    String key = "";
    String value = "";
    statesncaps = new HashMap<String, String>();
    statenabbrevs = new HashMap<String, String>();
    currentstate = "AL";// default in case nothing selected
    previouscapital = "Montgomery";// previously selected state's capital
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
          Log.d("getitrightabbrevs", line + "  " + key);
        }
        if (cnt % 3 == 2) {
          value = line;
          statesncaps.put(key, value);
          Log.d("getitrightcaps", key + "  " + value);
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
      System.out.println((states[i].toString()).trim());
    Arrays.sort(states);
    Arrays.sort(statenames);
  }

}