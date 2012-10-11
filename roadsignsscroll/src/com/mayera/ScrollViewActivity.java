package com.mayera;

import java.io.InputStream;
import java.util.List;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScrollViewActivity extends ListActivity {
  TextView mList;
  ImageView mimage;
  static Drawable[] signIcon;
  static final int NumberOfRoadSigns = 54;
  ListView lv;
  Toast toast;
  static int[] roadsigncopy = new int[NumberOfRoadSigns];
  static final String[] ROAD_STRINGS = new String[] { "signs1", "signs2",
      "signs3", "signs4", "signs5", "signs6", "signs7", "signs8", "signs9",
      "signs10", "signs11", "signs12", "signs13", "signs14", "signs15",
      "signs16", "signs17", "signs18", "signs19", "signs20", "signs21",
      "signs22", "signs23", "signs24", "signs25", "signs26", "signs27",
      "signs28", "signs29", "signs30", "signs31", "signs32", "signs33",
      "signs34", "signs35", "signs36", "signs37", "signs38", "signs39",
      "signs40", "signs41", "signs42", "signs43", "signs44", "signs45",
      "signs46", "signs47", "signs48", "signs49", "signs50", "signs51",
      "signs52", "signs53", "signs54" };

  static final String[] correctAnswers = { "Bus/car pool lane", "Detour",
      " Exit marker", "Interstate", " Left turn only", " No standing any time",
      " Road closed", "State route marker", "Two-lane  traffic",
      "U.S. route marker", " Workers", "Cattle crossing", "Flagman ahead",
      " Left or straight only", " Multiple turning lanes", "Hill",
      " Left turn only", "No right turn", "No passing", " Rest area",
      "County road marker", "Do not pass", " Mile marker", "No pedestrians",
      " Hospital", "Merge", "Reserved Parking", "Signal Ahead", "Yield ahead",
      " Slippery when wet", " Railroad", "Lane reduction ahead", "Cross road",
      "Noparking", "Advisory ramp speed", "Sharp turn ahead", "Winding road",
      "Keep right", "No turns", "Road narrows", "Divided highway",
      "Do not enter", "Handicapped", " No trucks", " No U-turn", "One way",
      " Road closed", " Yield", " School", "Wrong way", "Side road",
      "Stop ahead", "stop here on red", " Stop"

  };

  private final Handler toastHandler = new Handler();
  int DURATION_OF_TOAST_DISPALY = 2000;;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.mayera.R.layout.main);
    InputStream is = this.getResources().openRawResource(
        R.raw.firstanswercorrect);
    List<RoadSign> roadSigns = RoadSign.populateRoadSign(is);
    setListAdapter(new RoadSignsArray(this, 0, roadSigns));
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {

    RoadSign selectedValue = (RoadSign) getListAdapter().getItem(position);
    String correctAnswer = selectedValue.getCorrectAnswer();
    // Toast.makeText(this, correctAnswer, Toast.LENGTH_SHORT).show();

  }
}