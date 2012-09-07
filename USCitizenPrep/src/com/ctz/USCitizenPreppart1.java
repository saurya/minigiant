package com.ctz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
  // static String senator;
  static SimpleDateFormat dateFormat;
  private static boolean gov_exist, sen_exist;
  static Toast toast;
  static int duration_of_toast_display = 2000;
  // Foll vars used across once a state and senior selected
  static String original42, original19, currentcapital43;
  boolean is_a_senior2;;
  static boolean is_a_senior;
  static int positionprev = 0;
  static String[] statenames;
  static String str42 = "Answers will vary.  [District of Columbia residents should answer that D.C. does not have a Governor]";
  String str19 = "Answers will vary.  [District of Columbia residents and residents"
      + " of U.S. territories should answer that D.C.  (or the territory where the applicant lives) has no U.S. Senators.";
  static String statename = "a";
  static String governor = "";
  static boolean strSavedMem2;
  static int strSavedMem1;

  public static void fillgovernordata() {

    governor = "";

    FileInputStream fIn = null;
    BufferedReader myReader = null;
    try {
      String filename = "/sdcard/govdataactual.txt";
      File f = new File(filename);
      fIn = new FileInputStream("/sdcard/govdataactual.txt");

      myReader = new BufferedReader(new InputStreamReader(fIn));

      String onlyone = "";

      while ((onlyone = myReader.readLine()) != null) {
        governor += onlyone + "\n";
        if (governor.contains(currentstate)) {

          original42 = governor + "as on "
              + (dateFormat.format((new Date(f.lastModified())))) + " EST"; // (new

          gov_exist = true;// for popup message
        } else

        {
          original42 = str42;

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
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

  };

  // that's it
  private boolean changedstate;// if user selects a different state in the same
  // session
  String selected;

  static String currentstate;// just to keep it going
  HashMap<String, String> currentgovernors;

  String[] states = new String[50];
  String senator_of_state = "a", governor_of_state = "a";
  String[] state = new String[100];

  String[] sentr = new String[100];
  HashMap<String, String> statesncaps;

  HashMap<String, String> statenabbrevs;
  private String selected_type;
  private Button go_Button, confirm_button;
  // private Button exit_Button;
  private RadioButton radio_prepare_for_interview;
  private RadioButton radio_test_yourself;
  private CheckBox radio_senior_prepare_for_interview;
  private static String user_selection;
  static String currentcapital, previouscapital;

  @SuppressWarnings("deprecation")
  public static void fillsenatordata() {// close ???

    String filename = "/sdcard/senatordataactual.txt";
    File f = new File(filename);
    String senator = " ";
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
        if (myReader != null)
          myReader.close();
        if (fIn != null)
          fIn.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    if (senator.contains(currentstate)) {
      original19 = senator + "as on "
          + (dateFormat.format((new Date(f.lastModified())))) + " EST";
      ;
      sen_exist = true;
    }
  }

  private GestureLibrary gLib;

  int spinnerPosition;

  public boolean capital() {
    changedstate = false;
    Log.d("correct?", currentstate + "   " + statenames[49] + " " + states[49]
        + statesncaps.get(currentstate) + "*&*&*&^*&^&*&*&^*&^");
    currentcapital = statesncaps.get(currentstate);
    currentcapital43 = currentcapital;

    if (previouscapital.contains(currentcapital)
        || currentcapital.contains(previouscapital)) {
      ;
    } else {
      changedstate = true;
      previouscapital = currentcapital;
    }
    return (changedstate);
  }

  private void initspecials() {
    is_a_senior = false;

    gov_exist = false;
    sen_exist = false;
    currentcapital43 = "Montgomery";
    original19 = str19;
    original42 = "Answers will vary.  [District of Columbia residents should answer that D.C. does not have a Governor]";
  }

  private void LoadPreferences() {
    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    strSavedMem1 = sharedPreferences.getInt("spinnerposition", spinnerPosition);
    strSavedMem2 = sharedPreferences.getBoolean("is_senior", is_a_senior);

  }

  /** Called when the activity is first created. */
  @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String appVersion = this.getString(R.string.version_number);
    VersionNumber.setAppVersion(appVersion);// Use it somewhere
    stateData();
    dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS");
    dateFormat.setTimeZone(TimeZone.getTimeZone("EST5EDT"));
    currentstate = "Alabama";
    previouscapital = "Montgomery";
    setContentView(com.ctz.R.layout.mainforpart1);

    gov_exist = false;// olddata exists or
    sen_exist = false;// governor and senator

    final Spinner spinner = (Spinner) findViewById(R.id.state_spinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.states, R.layout.spinnerview);

    adapter.setDropDownViewResource(R.layout.spinnerview);
    spinner.setAdapter(adapter);
    spinner.setFocusable(true);
    radio_senior_prepare_for_interview = (CheckBox) findViewById(com.ctz.R.id.radio_senior_prepare_for_interview);
    LoadPreferences();
    spinner.setSelection(strSavedMem1);
    radio_senior_prepare_for_interview.setChecked(strSavedMem2);
    go_Button = (Button) findViewById(com.ctz.R.id.go1);

    go_Button.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        initspecials();

        Intent myIntent2 = new Intent();

        is_a_senior = radio_senior_prepare_for_interview.isChecked();
        // SavePreferences(" is_senior", is_a_senior);

        currentstate = spinner.getSelectedItem().toString();
        spinnerPosition = spinner.getSelectedItemPosition();
        SavePreferences();

        changedstate = capital();// currentstate has to be fulllength for this
                                 // method

        // send state- abbreviation for the foll 6/20/2012
        Iterator<String> it = statenabbrevs.keySet().iterator();

        while (it.hasNext()) {

          String currentstateabbrev = it.next();

          if (statenabbrevs.get(currentstateabbrev).equals(currentstate))

          {
            currentstate = currentstateabbrev;
            break;
          }

        }

        new Justdownload(currentstate);

        myIntent2.setClassName("com.ctz", "com.ctz.USCitizenPreppart2");
        startActivity(myIntent2);

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

  private void SavePreferences() {
    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean("is_senior", is_a_senior);
    editor.putInt("spinnerposition", spinnerPosition);
    editor.commit();
  }

  private void SavePreferences(String key, boolean value) {
    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(key, value);
    editor.commit();
  }

  private void SavePreferences(String key, int value) {
    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(key, value);
    editor.commit();
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
    // statesncaps.put("Wyoming", "Cheyenne");// hack
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