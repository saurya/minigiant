package com.ctz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PrepNoTimer extends ListActivity {
  static Set multansqnnums = new HashSet(Arrays.asList(new String[] { "1", "3",
      "5", "7", "8", "11", "12", "13", "15", "24", "27", "28", "35", "36",
      "40", "41", "47", "48", "49", "50", "51", "52", "54", "56", "57", "60",
      "63", "64", "66", "67", "70", "71", "72", "73", "74", "75", "76", "77",
      "84", "86", "92", "94", "95", "96", "99" }));
  boolean bingo, done;
  // set the answerlist array element to this string for
  // tokenizing and displaying a dropdown listview of
  // possible multiple answers for a few qns
  static Toast toast;
  ArrayList<String> multipleAnswersItems = new ArrayList<String>();
  @SuppressLint({ "NewApi", "NewApi", "NewApi" })
  EditText edittext;//
  private TextView mGetQuestionString;
  private TextView mgetAnswerString;
  private ListView mmultipleanswerlist;
  private Button mmultipleAns;
  private TextView mScore;
  private Button mnext;
  private Button mprev;

  int cnt = 0;
  int score = 0;
  int questionnumber;
  int cnt1, ctz_ans, correct_ans;

  String correctanswerstring;

  final static long seconds_in_milllies = 1000L;
  final static long minutes_in_millies = seconds_in_milllies * 60;
  final static long hours_in_millies = minutes_in_millies * 60;
  static AlertDialog.Builder testscores_dialog_builder, builder;
  static AlertDialog testscores_dialog, alert;
  // in onTick
  // final Runnable r;
  // Array containing randomly generated question numbers
  int[] originalQNums = new int[100];
  ImageButton mbtnclose_normal;
  TextView mgetReport;

  ImageButton mbtn_check_on;
  String getansString1 = "";
  String getansString2 = "";
  String getansString3 = "";
  String getansString4 = "";
  String getAnswerString0 = "";
  String getquestionString0 = "";// set first question to be displayed
  String multipleChoiceAnswer = "";

  String getAnswerString = "";
  String getquestionString = "";// and the next .

  String[] allquestions = new String[100];

  String[] allanswers = new String[100];;

  String[] nearlygood_10; // deceptive answerset
  String[] funny1_10; // fake answerset 1
  String[] funny2_10; // fake answerset 2
  int userselectQns;
  String multipleChoiceAnswers = "";
  String setanswerString = "";
  String scoreString = "Your Score Now Is";
  int userselecttiming;
  String[] qnlist;// retrieve current 100 questions of the test
  String[] anslist;// retrieve current 100 answers of the test
  String[] ctznanslist;// corresponding answerset
  Handler handler = new Handler();
  String ctznans;// user-selected answerset
  int currentdisplay;
  String prtmultiplestrg;
  int originalQnumber;
  static String finalswipe;
  int numberofrounds;
  ArrayAdapter<String> adapter;
  boolean addItemscalled = false;
  ArrayList<String> multipleAnswerslist = new ArrayList<String>();

  public void addItems(View v) {
    addItemscalled = true;
    if (addItemscalled)
      adapter.clear();
    prtmultiplestrg = anslist[cnt];
    multipleAnswersItems = tokens(prtmultiplestrg);

    adapter.notifyDataSetChanged();
    // prtmultiplestrg = anslist[k];

    mmultipleanswerlist.setVisibility(View.VISIBLE);
  }

  public void callrestofthecode() {

    {
      cnt++;
      ctz_ans = -1;
      getquestionString = getnextqn(cnt);
      if (getquestionString.equals("Done!")) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PrepNoTimer.this);
        builder
            .setMessage(
                "Congratulations! You just finished Practice of all Questions! ")
            .setCancelable(false)
            .setPositiveButton("Home", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                PrepNoTimer.this.finish();
              }
            });

        AlertDialog alert = builder.create();
        alert.show();

      }
      mGetQuestionString.setText(getquestionString);
      getAnswerString = getnextanswer(cnt);

      if (multansqnnums.contains(cnt + "")) {
        mmultipleAns.setVisibility(View.VISIBLE);
        int till = getAnswerString.indexOf("*");
        mgetAnswerString.setText(getAnswerString.substring(0, till));
      } else {
        mmultipleAns.setVisibility(View.GONE);

        mgetAnswerString.setVisibility(View.VISIBLE);
        mgetAnswerString.setText(getAnswerString);
      }

    }
  }

  public String getnextanswer(int cnt) {
    int qnnumber = cnt + 1;

    return "A: " + anslist[cnt];

  }

  public String getnextqn(int cnt) {
    if (cnt < 0)
      cnt = 0;
    if (cnt > 99)
      cnt = 99;
    int qnnumber = cnt + 1;

    mScore.setText((qnnumber) + "/100");
    return "Q: " + (qnnumber) + ": " + qnlist[cnt];
  }

  public String getpreviousanswer(int cnt) {

    return "A: " + ": " + anslist[cnt];

  }

  public String getpreviousqn(int cnt) {
    if (cnt < 0)
      cnt = 0;
    if (cnt > 99)
      cnt = 99;
    int qnnumber = cnt;
    mScore.setText((qnnumber + 1) + "/100");
    return "Q: " + (qnnumber + 1) + ": " + qnlist[cnt];
  }

  /** Called when the activity is first created. */

  @SuppressWarnings("deprecation")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, multipleAnswersItems);
    this.setListAdapter(adapter);
    bingo = false;
    int score = 0;

    setContentView(R.layout.main5);// following lines 'must' to follow layout
    mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
    mgetAnswerString = (TextView) findViewById(R.id.getAnswerString);
    mmultipleanswerlist = (ListView) findViewById(android.R.id.list);
    mmultipleAns = (Button) findViewById(R.id.multipleAns);
    mmultipleAns.setVisibility(View.GONE);
    mmultipleanswerlist.setVisibility(View.GONE);
    mScore = (TextView) findViewById(R.id.scoreField);
    mnext = (Button) findViewById(com.ctz.R.id.next);
    mprev = (Button) findViewById(com.ctz.R.id.prev);
    mprev.setText("<");
    setDefaultKeyMode(DEFAULT_KEYS_DISABLE);
    mnext.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        cnt++;
        Log.d("Next: ", cnt + "*****************************************");
        if (cnt > 99) {

          AlertDialog.Builder testscores_dialog_builder = new AlertDialog.Builder(
              PrepNoTimer.this);
          testscores_dialog_builder
              .setMessage(
                  "Congratulations! You just finished Practice of all Questions! ")
              .setCancelable(false)
              .setPositiveButton("Home", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                  PrepNoTimer.this.finish();
                }
              });
          testscores_dialog = testscores_dialog_builder.create();
          testscores_dialog.show();
        }

        else {
          getquestionString = getnextqn(cnt);

          mGetQuestionString.setText(getquestionString);
          getAnswerString = getnextanswer(cnt);
          if (multansqnnums.contains(cnt + "")) {
            mmultipleAns.setVisibility(View.VISIBLE);
            // mmultipleanswerlist.setVisibility(View.VISIBLE);
            int till = getAnswerString.indexOf("*");
            mgetAnswerString.setText(getAnswerString.substring(0, till - 1));
          } else {
            mmultipleanswerlist.setVisibility(View.GONE);
            mmultipleAns.setVisibility(View.GONE);
            mgetAnswerString.setVisibility(View.VISIBLE);
            mgetAnswerString.setText(getAnswerString);
          }

        }
      }
    }

    );

    mprev.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        if (cnt > 99)
          cnt = 99;

        if (cnt <= 0) {

          cnt = 0;
          mGetQuestionString.setText(getquestionString0);
          mgetAnswerString.setText(getAnswerString0);
        }

        else {

          cnt--;
          getquestionString = getpreviousqn(cnt);
          mGetQuestionString.setText(getquestionString);
          getAnswerString = getpreviousanswer(cnt);
          if (multansqnnums.contains(cnt + "")) {
            mmultipleAns.setVisibility(View.VISIBLE);
            // mmultipleanswerlist.setVisibility(View.VISIBLE);
            int till = getAnswerString.indexOf("*");
            mgetAnswerString.setText(getAnswerString.substring(0, till));
          } else {
            mmultipleanswerlist.setVisibility(View.GONE);
            mmultipleAns.setVisibility(View.GONE);
            mgetAnswerString.setVisibility(View.VISIBLE);
            mgetAnswerString.setText(getAnswerString);
          }
        }
      }
    }

    );

    Bundle bundle = new Bundle();
    bundle = this.getIntent().getExtras();

    qnlist = bundle.getStringArray("allquestions");
    anslist = bundle.getStringArray("allanswers");
    int begin = 0, end = 0;
    begin = 0;
    end = qnlist.length;

    Log.d("length: ", qnlist.length + ">*>*>&>*>&>*>&>*>&*>&>*>&>&*&"
        + anslist.length);
    getAnswerString0 = anslist[begin];
    getquestionString0 = "Q: " + qnlist[begin];// first question
    mGetQuestionString.setText(getquestionString0);
    mgetAnswerString.setText(getAnswerString0);
    currentdisplay = 0;
    originalQnumber = originalQNums[0];
  }

  private ArrayList<String> tokens(String str) {

    StringTokenizer stringTokenizer = new StringTokenizer(str, "*");
    Log.d("try:  ",
        "The total no. of tokens generated :  " + stringTokenizer.countTokens());

    while (stringTokenizer.hasMoreTokens()) {

      multipleAnswersItems.add(stringTokenizer.nextToken());
    }

    return multipleAnswersItems;
  }

}
