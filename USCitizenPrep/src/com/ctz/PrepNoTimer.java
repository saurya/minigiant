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
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ctz.R.color;

@SuppressLint("NewApi")
public class PrepNoTimer extends ListActivity {
  static Set multansqnnums = new HashSet(Arrays.asList(new String[] { "1", "3",
      "5", "7", "8", "10", "11", "12", "13", "15", "24", "27", "28", "35",
      "36", "40", "41", "47", "48", "49", "50", "51", "52", "54", "56", "57",
      "58", "59", "60", "63", "64", "66", "67", "70", "71", "72", "73", "74",
      "75", "76", "77", "84", "86", "87", "90", "91", "92", "94", "95", "96",
      "99" }));
  static String[] seniordata = { "5", "6", "10", "11", "12", "16", "17", "19",
      "24", "26", "27", "43", "44", "48", "53", "55", "69", "74", "77", "84",
      "93", "94", "96", "98" };
  boolean bingo, done;
  int current_number_of_questions;
  ImageButton magichandle, invertimage;
  private int clickcounter;
  // set the answerlist array element to this string for
  // tokenizing and displaying a draw-up listview of
  // possible multiple answers for aboveqns
  static Toast toast;
  ArrayList<String> multipleAnswersItems = new ArrayList<String>();
  @SuppressLint({ "NewApi", "NewApi", "NewApi" })
  EditText edittext;//
  private TextView mGetQuestionString;
  private TextView mgetAnswerString;
  private ListView mmultipleanswerlist;
  private View main5_View;
  private TextView mScore;
  private Button mnext;
  private Button mprev;
  private ImageView mslideHandleButton;

  private SlidingDrawer mslidingDrawer;
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

  int[] originalQNums;// = new int[100];
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

  // String[] allquestions; = new String[current_number_of_questions];

  // String[] allanswers;// = new String[current_number_of_questions];;

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
    mmultipleanswerlist.setVisibility(View.GONE);
    addItemscalled = true;
    if (addItemscalled)
      adapter.clear();
    prtmultiplestrg = anslist[cnt];
    multipleAnswersItems = tokens(prtmultiplestrg);
    adapter.notifyDataSetChanged();
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
      if (getquestionString.endsWith("*"))
        getquestionString.replace("*", " ");
      mGetQuestionString.setText(getquestionString);
      getAnswerString = getnextanswer(cnt);

      if ((!USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
          .contains(cnt + ""))
          || (USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
              .contains(PrepNoTimer.seniordata[cnt]))) {
        if (mmultipleanswerlist.getVisibility() == View.VISIBLE)
          mmultipleanswerlist.setVisibility(View.GONE);
        mmultipleanswerlist.setVisibility(View.GONE);// remove staledata

        mslideHandleButton.setVisibility(View.VISIBLE);
        mslidingDrawer.setVisibility(View.VISIBLE);

        int till = getAnswerString.indexOf("*");
        mgetAnswerString.setText("hello1" + getAnswerString.substring(0, till));
      } else {
        magichandle.setVisibility(View.GONE);
        mslidingDrawer.setVisibility(View.GONE);
        mslideHandleButton.setVisibility(View.GONE);
        mmultipleanswerlist.setVisibility(View.GONE);
        mgetAnswerString.setVisibility(View.VISIBLE);
        mgetAnswerString.setText("hello2" + getAnswerString);
      }

    }
  }

  private void check_if_multipleanswers() {
    if ((!USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
        .contains(cnt + ""))
        || (USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
            .contains(PrepNoTimer.seniordata[cnt]))) {
      magichandle.setVisibility(View.VISIBLE);
      if (mmultipleanswerlist.getVisibility() == View.VISIBLE)
        mmultipleanswerlist.setVisibility(View.GONE);
      mslidingDrawer.setVisibility(View.VISIBLE);
      mslideHandleButton.setVisibility(View.VISIBLE);

      int till = getAnswerString.indexOf("*");

      if (till > 0)
        mgetAnswerString.setText("hello3" + getAnswerString.substring(0, till));

    } else {
      if (mslidingDrawer.getVisibility() == View.VISIBLE)
        mslidingDrawer.setVisibility(View.GONE);
      mslideHandleButton.setVisibility(View.GONE);
      mmultipleanswerlist.setVisibility(View.GONE);
      magichandle.setVisibility(View.GONE);
      mslidingDrawer.setVisibility(View.GONE);
      mgetAnswerString.setVisibility(View.VISIBLE);
      mgetAnswerString.setText("hello4" + getAnswerString);
    }

  }

  public String getnextanswer(int cnt) {
    int qnnumber = cnt + 1;
    return anslist[cnt];
  }

  public String getnextqn(int cnt) {
    if (cnt < 0)
      cnt = 0;
    if (cnt > current_number_of_questions - 1)
      cnt = current_number_of_questions - 1;
    int qnnumber = cnt + 1;

    mScore.setText((qnnumber) + "/" + current_number_of_questions);
    return qnlist[cnt];
  }

  public String getpreviousanswer(int cnt) {

    return anslist[cnt];

  }

  public String getpreviousqn(int cnt) {
    if (cnt < 0)
      cnt = 0;
    if (cnt > current_number_of_questions - 1)
      cnt = current_number_of_questions - 1;
    int qnnumber = cnt;
    mScore.setText((qnnumber + 1) + "/" + current_number_of_questions);
    return qnlist[cnt];
  }

  @SuppressWarnings("deprecation")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // allquestions = new String[current_number_of_questions];

    // allanswers = new String[current_number_of_questions];

    adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, multipleAnswersItems);
    this.setListAdapter(adapter);
    bingo = false;
    int score = 0;
    main5_View = findViewById(R.layout.main5);
    setContentView(R.layout.main5);// following lines 'must' to follow layout
    mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
    mgetAnswerString = (TextView) findViewById(R.id.getAnswerString);
    mmultipleanswerlist = (ListView) findViewById(android.R.id.list);
    mmultipleanswerlist.setScrollbarFadingEnabled(false);
    mslideHandleButton = (ImageView) findViewById(R.id.slideHandleButton);
    mslidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
    mslideHandleButton.setVisibility(View.GONE);
    mslidingDrawer.setVisibility(View.GONE);
    mmultipleanswerlist.setVisibility(View.GONE);
    mScore = (TextView) findViewById(R.id.scoreField);
    magichandle = (ImageButton) findViewById(R.id.magichandle);
    invertimage = (ImageButton) findViewById(R.id.invertimage);
    mslidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {

      public void onDrawerOpened() {

        addItems(main5_View);
        mprev.setTextColor(color.anstxt3);
        mprev.setEnabled(false);
        mnext.setTextColor(color.anstxt3);
        mnext.setEnabled(false);
      }
    });

    mslidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

      public void onDrawerClosed() {
        mprev.setTextColor(Color.WHITE);
        mprev.setEnabled(true);
        mnext.setTextColor(Color.WHITE);
        mnext.setEnabled(true);
        mmultipleanswerlist.setVisibility(View.GONE);
      }
    });
    // BEGIN************this code not for TestCtz1*************
    mnext = (Button) findViewById(com.ctz.R.id.next);
    mprev = (Button) findViewById(com.ctz.R.id.prev);
    mprev.setText("<");
    setDefaultKeyMode(DEFAULT_KEYS_DISABLE);
    mnext.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        cnt++;
        Log.d("Next: ", cnt + "*****************************************");
        if (cnt > current_number_of_questions - 1) {

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
          // not sequential numbers as in senior data
          if ((!USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
              .contains(cnt + ""))
              || (USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
                  .contains(PrepNoTimer.seniordata[cnt]))) {
            magichandle.setVisibility(View.VISIBLE);
            if (mmultipleanswerlist.getVisibility() == View.VISIBLE)
              mmultipleanswerlist.setVisibility(View.GONE);
            mmultipleanswerlist.setVisibility(View.GONE);
            mslidingDrawer.setVisibility(View.VISIBLE);
            int till = getAnswerString.indexOf("*");
            if (till > 0)
              mgetAnswerString.setText("hello5"
                  + getAnswerString.substring(0, till));
          } else {
            magichandle.setVisibility(View.GONE);
            adapter.clear();
            mmultipleanswerlist.setVisibility(View.GONE);

            mslideHandleButton.setVisibility(View.GONE);
            mslidingDrawer.setVisibility(View.GONE);
            mgetAnswerString.setVisibility(View.VISIBLE);
            mgetAnswerString.setText("hello6" + getAnswerString);
          }

        }
      }
    }

    );
    magichandle.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {

        {
          mslidingDrawer.open();
          invertimage.setVisibility(View.VISIBLE);
        }

      }
    });

    invertimage.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {

        {
          mslidingDrawer.close();
          invertimage.setVisibility(View.GONE);
        }

      }
    });

    mprev.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        if (cnt > current_number_of_questions - 1)
          cnt = current_number_of_questions - 1;

        if (cnt <= 0) {

          cnt = 0;
          mGetQuestionString.setText(getquestionString0);
          getAnswerString = getAnswerString0;
          int till = getAnswerString.indexOf("*");
          check_if_multipleanswers();
        }

        else {

          cnt--;
          getquestionString = getpreviousqn(cnt);
          mGetQuestionString.setText(getquestionString);
          getAnswerString = getpreviousanswer(cnt);
          check_if_multipleanswers();

          // call with getAnswerstring
          /*
           * if ((!USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
           * .contains(cnt + "")) || (USCitizenPreppart1.is_a_senior &&
           * PrepNoTimer.multansqnnums .contains(PrepNoTimer.seniordata[cnt])))
           * { if (mmultipleanswerlist.getVisibility() == View.VISIBLE)
           * mmultipleanswerlist.setVisibility(View.GONE);
           * mslidingDrawer.setVisibility(View.GONE);
           * mslidingDrawer.setVisibility(View.VISIBLE); int till =
           * getAnswerString.indexOf("*"); if (till > 0)
           * mgetAnswerString.setText(getAnswerString.substring(0, till)); }
           * else { if (mslidingDrawer.getVisibility() == View.VISIBLE)
           * mslidingDrawer.setVisibility(View.GONE);
           * mslideHandleButton.setVisibility(View.GONE);
           * mmultipleanswerlist.setVisibility(View.GONE);
           * mslideHandleButton.setVisibility(View.GONE);
           * mslidingDrawer.setVisibility(View.GONE);
           * mgetAnswerString.setVisibility(View.VISIBLE);
           * mgetAnswerString.setText(getAnswerString); }
           */
        }
      }
    }

    );

    Bundle bundle = new Bundle();
    bundle = this.getIntent().getExtras();

    qnlist = bundle.getStringArray("allquestions");
    anslist = bundle.getStringArray("allanswers");
    current_number_of_questions = qnlist.length;
    originalQNums = new int[current_number_of_questions];
    int begin = 0, end = 0;
    begin = 0;
    end = qnlist.length;

    Log.d("length: ", qnlist.length + ">*>*>&>*>&>*>&>*>&*>&>*>&>&*&"
        + anslist.length);
    getAnswerString0 = anslist[begin];
    getquestionString0 = qnlist[begin];// first question
    mGetQuestionString.setText(getquestionString0);
    getAnswerString = anslist[begin];
    check_if_multipleanswers();
    currentdisplay = 0;
    originalQnumber = originalQNums[0];
  }

  private ArrayList<String> tokens(String str) {
    int skip = 0;
    StringTokenizer stringTokenizer = new StringTokenizer(str, "*");
    Log.d("try:  ", "The total no. of tokens generated for:  " + str + " is "
        + stringTokenizer.countTokens());

    while (stringTokenizer.hasMoreTokens()) {

      multipleAnswersItems.add(stringTokenizer.nextToken());
    }
    multipleAnswersItems.remove(0);
    return multipleAnswersItems;
  }
}