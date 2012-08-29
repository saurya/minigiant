package com.ctz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ctz.SimpleGestureFilter.SimpleGestureListener;

@SuppressLint("NewApi")
public class TestCTZ10 extends ListActivity implements OnGestureListener,
    SimpleGestureListener {
  boolean mslidingdrawer_open;
  boolean i_got_it, time_out;
  private GestureDetector gestureScanner;
  private SimpleGestureFilter detector;
  boolean addItemscalled = false;
  @SuppressLint({ "NewApi", "NewApi", "NewApi" })
  OnClickListener myListener;
  EditText edittext;//
  private TextView mGetQuestionString;
  private TextView mgetAnswerString;
  private ListView mlistView;
  private TextView mrightViewText, mwrongViewText, mdisabledViewText;
  private TextView mtimerTextField;
  private TextView mScore;
  String prtmultiplestrg;
  // LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
  ArrayList<String> multipleAnswersItems = new ArrayList<String>();
  private View main2_View;
  // DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
  ArrayAdapter<String> adapter;
  int questionnumber, current_Qn_length;
  int cnt = 0;
  int score = 0;
  int cnt1, ctz_ans, correct_ans, begin, end;
  CountDownTimer runtimer;
  String correctanswerstring;
  final static long seconds_in_milllies = 1000L;
  final static long minutes_in_millies = seconds_in_milllies * 60;
  final static long hours_in_millies = minutes_in_millies * 60;
  static AlertDialog.Builder testscores_dialog_builder, builder;
  static AlertDialog testscores_dialog, alert;
  Button magichandle, invertimage;
  private ListView mmultipleanswerlist;
  private ImageView mslideHandleButton;
  private SlidingDrawer mslidingDrawer;
  int[] originalQNums = new int[100];
  ImageView mbtnclose_normal;
  TextView mgetReport;
  ImageView mbtn_check_on;
  ImageView mbtn_check_on_disabled;;
  String getansString1 = "";
  String getansString2 = "";
  String getansString3 = "";
  String getansString4 = "";
  String getAnswerString0 = "";
  String getquestionString0 = "";// set first question to be displayed
  String multipleChoiceAnswer = "";
  String getAnswerString = "";
  String getquestionString = "";// and the next .
  boolean test_interrupted;
  int justfound;

  int userselectQns;
  String multipleChoiceAnswers = "";
  String setanswerString = "";
  String scoreString = "Your Score Now Is";
  int userselecttiming;
  String[] qnlist;
  String[] anslist;
  String[] ctznanslist;

  String ctznans;
  Toast mToast;;
  int currentdisplay;
  int originalQnumber;
  static String finalswipe;
  int numberofrounds;

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

  public void calculateScores() {
    if (i_got_it)
      if (!time_out)
        score++;
    scoreString = score + "/" + currentdisplay;
    i_got_it = false;

  }

  public void callrestofthecode() {
    runtimer.cancel();
    {
      SharedPreferences prefs = PreferenceManager
          .getDefaultSharedPreferences(this);
      ;
      cnt++;
      currentdisplay++;
      ctz_ans = -1;
      {
        calculateScores();
        mScore.setText(scoreString);
      }
      if (currentdisplay == userselectQns || cnt == justfound) {
        decideVisibility(true);
        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);

        mgetReport.setText(getreportString());
        mgetReport.setVisibility(View.VISIBLE);
      } else {
        decideVisibility(false);
        mbtn_check_on.setClickable(true);
        getquestionString = getnextqn(cnt);
        mGetQuestionString.setText(getquestionString);

        getAnswerString = anslist[cnt];

        if ((PrepNoTimer.multansqnnums.contains(originalQNums[cnt] + "")))
          // display_answer();
          ;
        else
          mgetAnswerString.setText(getAnswerString);
        check_if_multipleanswers();
        cnt1 = originalQNums[cnt];
        runtimer.start();
      }
    }
  }

  private void calltimer(SharedPreferences prefs) {
    /*
     * { mbtn_check_on.setVisibility(View.VISIBLE);
     * 
     * mbtn_check_on.setEnabled(true);
     */
    mbtn_check_on.setClickable(true);

    magichandle.setVisibility(View.GONE);
    invertimage.setVisibility(View.GONE);
    time_out = false;
    long timer = prefs.getLong("TIME", userselecttiming * 1000);
    runtimer = new CountDownTimer(timer, 1000) {

      @Override
      public void onFinish() {
        mtimerTextField.setText("done!");

        if (mmultipleanswerlist.getVisibility() == View.VISIBLE)
          mmultipleanswerlist.setVisibility(View.GONE);
        mslidingDrawer.setVisibility(View.VISIBLE);
        mslideHandleButton.setVisibility(View.VISIBLE);

        if ((PrepNoTimer.multansqnnums.contains(originalQNums[cnt] + ""))) {
          magichandle.setVisibility(View.VISIBLE);

          // display_answer();
        } else
          mgetAnswerString.setText(getAnswerString);

        mgetAnswerString.setVisibility(View.VISIBLE);
        mbtnclose_normal.setVisibility(View.VISIBLE);
        // mrightViewText.setVisibility(View.GONE);
        mwrongViewText.setVisibility(View.VISIBLE);
        i_got_it = false;
        {
          mbtn_check_on.setVisibility(View.GONE);
          mrightViewText.setVisibility(View.GONE);

          mbtn_check_on.setEnabled(true);
          mbtn_check_on_disabled.setVisibility(View.VISIBLE);
          mdisabledViewText.setVisibility(View.VISIBLE);
        }

      }

      @Override
      public void onTick(long elapsed) {

        mtimerTextField.setText("" + elapsed / 1000);
      }
    }.start();

  }

  private void check_if_multipleanswers() {// you need a sliding drqwer if so

    if ((PrepNoTimer.multansqnnums.contains(originalQNums[cnt] + ""))) {

      if (mmultipleanswerlist.getVisibility() == View.VISIBLE)
        mmultipleanswerlist.setVisibility(View.GONE);
      mslidingDrawer.setVisibility(View.VISIBLE);
      mslideHandleButton.setVisibility(View.VISIBLE);

    } else {
      if (mslidingDrawer.getVisibility() == View.VISIBLE)
        mslidingDrawer.setVisibility(View.GONE);
      mslideHandleButton.setVisibility(View.GONE);
      mmultipleanswerlist.setVisibility(View.GONE);
      magichandle.setVisibility(View.GONE);
      mslidingDrawer.setVisibility(View.GONE);
      mgetAnswerString.setVisibility(View.GONE);
      mgetAnswerString.setText(getAnswerString);
    }

  }

  public void decideVisibility(boolean hide) {

    if (hide) {// for report
      mbtn_check_on.setVisibility(View.GONE);
      mbtnclose_normal.setVisibility(View.GONE);
      mrightViewText.setVisibility(View.GONE);
      mwrongViewText.setVisibility(View.GONE);
      mgetAnswerString.setVisibility(View.GONE);
      mGetQuestionString.setVisibility(View.GONE);
      mtimerTextField.setVisibility(View.GONE);

    } else {

      magichandle.setVisibility(View.GONE);
      mGetQuestionString.setVisibility(View.VISIBLE);
      mtimerTextField.setVisibility(View.VISIBLE);
      mbtnclose_normal.setVisibility(View.GONE);
      mbtn_check_on.setVisibility(View.GONE);
      mrightViewText.setVisibility(View.GONE);
      mwrongViewText.setVisibility(View.GONE);
      mbtn_check_on_disabled.setVisibility(View.GONE);
      mdisabledViewText.setVisibility(View.GONE);
      mbtn_check_on.setClickable(true);
      mbtnclose_normal.setClickable(true);
      mgetAnswerString.setVisibility(View.GONE);
    }
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent me) {
    super.dispatchTouchEvent(me);
    this.detector.onTouchEvent(me);
    return super.dispatchTouchEvent(me);
  }

  private void display_answer() {
    String display = getAnswerString;
    int till = getAnswerString.indexOf("*");
    String small = "";
    if (till > 0) {
      display = (getAnswerString.substring(0, till));

      if (getquestionString.contains("two")) {
        small = getAnswerString.substring(till);
        till = small.indexOf("*");
        if (till > 0)
          display += (small.substring(0, till));
      }
    }
    mgetAnswerString.setText(display);
  }

  public String getnextqn(int cnt) {
    int qnnumber = cnt + 1;
    return qnlist[cnt];
  }

  public String getreportString() {
    return ("\n\n\n" + "   Scores Based On Self-Evaluation " + "\n\n\n"
        + "Questions Prescribed On Website:  100" + "\n\n"
        + " random Questions " + userselectQns + "\n\n" + "Timer "
        + userselecttiming + " seconds \n\n" + "Right: " + score
        + " Question(s)\n\n" + "Wrong: " + (userselectQns - score))
        + " \nComment:\n\n In the actual test you are required to get answers for 5 to 6 questions right out of 10."
        + " Real Test does not involve any time factor "
        + " Visit Official website for more details.";
  }

  private void go_For_NextRound(int begin, int end) {
    ++numberofrounds;
    mToast.setText("Round# " + (numberofrounds));
    mToast.show();
    if (begin == 100)
      return;
    if (end > 100)
      end = 100;
    // getAnswerString0 = anslist[begin];
    // getAnswerString = getAnswerString0;
    // check_if_multipleanswers();
    getquestionString0 = qnlist[begin];

    mGetQuestionString.setText((numberofrounds) + " " + begin + " "
        + getquestionString0);
    magichandle.setVisibility(0);
    invertimage.setVisibility(0);
    mGetQuestionString.setVisibility(View.VISIBLE);
    mgetAnswerString.setVisibility(View.GONE);
    mbtnclose_normal.setVisibility(View.GONE);
    mbtn_check_on.setVisibility(View.GONE);
    mrightViewText.setVisibility(View.GONE);
    mwrongViewText.setVisibility(View.GONE);
    mbtn_check_on_disabled.setVisibility(View.GONE);
    mdisabledViewText.setVisibility(View.GONE);

    mgetReport.setVisibility(View.GONE);
    calltimer(PreferenceManager.getDefaultSharedPreferences(this));
    getAnswerString = anslist[begin];
    cnt = begin;
    display_answer();

    check_if_multipleanswers();
    cnt = begin;

    currentdisplay = 0;
    originalQnumber = originalQNums[0];
    // check_if_multipleanswers();
  }

  public void newOnclick(View v) {

    if (!time_out) {
      if (!mslidingdrawer_open) {
        mbtn_check_on.setClickable(true);
        i_got_it = true;
        callrestofthecode();
      } else {
        // mbtn_check_on.setClickable(false);
        mToast.setText("Close ...less");
        mToast.show();
      }
    }

  }

  public void newOnclick2(View v) {
    if (!mslidingdrawer_open) {
      i_got_it = false;
      callrestofthecode();
    } else {

      mToast.setText("Close ...less");
      mToast.show();
    }

  }

  @Override
  public void onBackPressed() {
    if (cnt < userselectQns) {// user in the middle of a test
      testscores_dialog_builder = new AlertDialog.Builder(TestCTZ10.this);
      testscores_dialog_builder.setMessage("Exiting the test! Are you sure?")
          .setCancelable(false)
          .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              finish();

            }
          }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
              return;

            }
          });
      testscores_dialog = testscores_dialog_builder.create();
      testscores_dialog.show();
      return;
    } else if (mgetReport.getVisibility() == View.VISIBLE) {// current test done
                                                            // Generate report
      begin = end;
      end = end + userselectQns;

      if (end > 100) {
        justfound = 100 % userselectQns;
        end = 100;

      }

      mToast.setText("Next Round: " + begin + "to" + end);
      mToast.show();
      cnt = 0;
      score = 0;
      current_Qn_length = userselectQns;
      // mScore.setText(" 0/0 ");
      mtimerTextField.setVisibility(View.VISIBLE);
      runtimer.start();
      String str = "";
      for (int i = 0; i < userselectQns; i++)
        str += originalQNums[i];
      Log.d(
          "String val",
          str
              + "**********************************************************************************");
      go_For_NextRound(begin + 1, end);

    }

    else {
      super.onBackPressed();
    }

  }

  /** Called when the activity is first created. */

  @SuppressWarnings("deprecation")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    detector = new SimpleGestureFilter(this, this);
    gestureScanner = new GestureDetector(this);
    mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    test_interrupted = false;
    justfound = -1;
    i_got_it = false;
    numberofrounds = 1;

    Bundle bundle = new Bundle();
    bundle = this.getIntent().getExtras();

    setContentView(R.layout.main2);
    setDefaultKeyMode(DEFAULT_KEYS_DISABLE);

    {
      userselectQns = bundle.getInt("userselectQns");
      if (userselectQns == 0)
        userselectQns = 3;// default
      qnlist = bundle.getStringArray("randomqns");
      anslist = bundle.getStringArray("anstorandomqns");
      originalQNums = bundle.getIntArray("originalQNums");
      userselecttiming = bundle.getInt("userselecttiming");

      if (userselecttiming == 0)
        userselecttiming = 3;
    }

    String temp = "";
    for (int i = 0; i < qnlist.length; i++)
      if (qnlist[i].contains("*")) {
        temp = qnlist[i].replace("*", "");
        qnlist[i] = temp;
      }

    correctanswerstring = "";
    begin = 0;
    end = userselectQns;
    begin = (numberofrounds - 1) * userselectQns;
    end = begin + userselectQns;
    if (end > 100)
      end = 100;
    if (begin >= 100) {
      mToast
          .setText("Congratulations. You just completed practice of 100 qustions");
      mToast.show();
      finish();
    }
    for (int i = begin; i < end; i++)
      correctanswerstring += originalQNums[i] + ",";
    getquestionString = qnlist[begin];
    getAnswerString0 = anslist[begin];
    cnt = begin;
    getAnswerString = getAnswerString0;
    String display = getAnswerString;
    int till = getAnswerString.indexOf("*");
    String small = "";
    if (till > 0)
      display = (getAnswerString.substring(0, till));

    if (getquestionString.contains("two")) {
      small = getAnswerString.substring(till);
      till = small.indexOf("*");
      if (till > 0)
        display += (small.substring(0, till));
    }

    main2_View = findViewById(R.layout.main2);
    mgetReport = (TextView) findViewById(R.id.getReport);
    mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
    mgetAnswerString = (TextView) findViewById(R.id.getAnswerString);
    mGetQuestionString.setText(getquestionString);
    mgetAnswerString.setText(display);
    magichandle = (Button) findViewById(R.id.magichandle);
    adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, multipleAnswersItems);
    this.setListAdapter(adapter);
    invertimage = (Button) findViewById(R.id.invertimage);
    mScore = (TextView) findViewById(R.id.scoreField);
    magichandle.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {

        {
          mslidingDrawer.open();
          mslidingdrawer_open = true;
          invertimage.setVisibility(View.VISIBLE);

        }

      }
    });

    invertimage.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {

        {
          mslidingdrawer_open = false;
          mslidingDrawer.close();
          invertimage.setVisibility(View.GONE);
        }

      }
    });
    myListener = new View.OnClickListener() {
      public void onClick(View v) {
        newOnclick(v);
      }
    };
    mrightViewText = (TextView) findViewById(R.id.rightViewText);
    mwrongViewText = (TextView) findViewById(R.id.wrongViewText);
    mdisabledViewText = (TextView) findViewById(R.id.disabled_ViewText);
    mtimerTextField = (TextView) findViewById(R.id.timerTextField);
    mbtnclose_normal = (ImageView) findViewById(R.id.btn_close_normal);
    mbtn_check_on = (ImageView) findViewById(R.id.btn_check_on);
    mbtn_check_on_disabled = (ImageView) findViewById(R.id.btn_check_on_disabled);
    mbtn_check_on_disabled.setVisibility(View.GONE);
    mdisabledViewText.setVisibility(View.GONE);
    mbtn_check_on_disabled.setOnClickListener(null);
    mbtn_check_on.setOnClickListener(myListener);

    mGetQuestionString.setText(getquestionString0);
    mgetAnswerString.setVisibility(View.GONE);

    mbtnclose_normal.setVisibility(View.GONE);
    mbtn_check_on.setVisibility(View.GONE);
    mgetReport.setVisibility(View.GONE);
    mmultipleanswerlist = (ListView) findViewById(android.R.id.list);
    mslideHandleButton = (ImageView) findViewById(R.id.slideHandleButton);
    mslidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
    mslideHandleButton.setVisibility(View.GONE);
    mslidingDrawer.setVisibility(View.GONE);
    mmultipleanswerlist.setVisibility(View.GONE);
    magichandle = (Button) findViewById(R.id.magichandle);
    invertimage = (Button) findViewById(R.id.invertimage);
    magichandle.setVisibility(View.GONE);
    invertimage.setVisibility(View.GONE);
    mslidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {

      public void onDrawerOpened() {

        addItems(main2_View);

      }
    });

    mslidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

      public void onDrawerClosed() {

        mmultipleanswerlist.setVisibility(View.GONE);
      }
    });

    calltimer(PreferenceManager.getDefaultSharedPreferences(this));

    getAnswerString = getAnswerString0;

    check_if_multipleanswers();
    currentdisplay = 0;
    originalQnumber = originalQNums[0];
  }

  public void onDoubleTap() {

  }

  public boolean onDown(MotionEvent e) {

    return false;
  }

  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
      float velocityY) {

    return false;
  }

  public void onLongPress(MotionEvent e) {

    check_if_multipleanswers();
    if ((!USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
        .contains(originalQNums[cnt] + ""))
        || (USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
            .contains(originalQNums[cnt])))
      magichandle.setVisibility(View.VISIBLE);

    runtimer.cancel();
    String display = getAnswerString;
    int till = getAnswerString.indexOf("*");
    String small = "";
    if (till > 0) {
      display = (getAnswerString.substring(0, till));

      if (getquestionString.contains("two")) {
        small = getAnswerString.substring(till);
        till = small.indexOf("*");
        if (till > 0)
          display += (small.substring(0, till));
      }
    }
    mgetAnswerString.setText(display);
    mgetAnswerString.setVisibility(View.VISIBLE);
    mbtnclose_normal.setVisibility(View.VISIBLE);
    mbtn_check_on.setVisibility(View.VISIBLE);

    mrightViewText.setVisibility(View.VISIBLE);
    mwrongViewText.setVisibility(View.VISIBLE);
    ;
    mbtnclose_normal.setClickable(true);
    mbtn_check_on.setClickable(true);

    return;
  }

  public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
      float distanceY) {

    return false;
  }

  public void onShowPress(MotionEvent e) {
    return;
  }

  public boolean onSingleTapUp() {

    if (mbtn_check_on.getVisibility() == View.GONE)
      return false;
    check_if_multipleanswers();
    if ((!USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
        .contains(originalQNums[cnt] + ""))
        || (USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
            .contains(originalQNums[cnt])))
      magichandle.setVisibility(View.VISIBLE);
    runtimer.cancel();
    mgetAnswerString.setVisibility(View.VISIBLE);
    mbtnclose_normal.setVisibility(View.VISIBLE);
    mbtn_check_on.setVisibility(View.VISIBLE);
    mrightViewText.setVisibility(View.VISIBLE);
    mwrongViewText.setVisibility(View.VISIBLE);
    ;
    mbtnclose_normal.setClickable(true);
    mbtn_check_on.setClickable(true);
    // check_if_multipleanswers();
    return true;
  }

  public boolean onSingleTapUp(MotionEvent e) {
    if (mbtn_check_on.getVisibility() == View.GONE)
      return false;
    if ((!USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
        .contains(originalQNums[cnt] + ""))
        || (USCitizenPreppart1.is_a_senior && PrepNoTimer.multansqnnums
            .contains(originalQNums[cnt])))
      magichandle.setVisibility(View.VISIBLE);
    runtimer.cancel();
    mgetAnswerString.setVisibility(View.VISIBLE);
    mbtnclose_normal.setVisibility(View.VISIBLE);
    mbtn_check_on.setVisibility(View.VISIBLE);
    mrightViewText.setVisibility(View.VISIBLE);
    mwrongViewText.setVisibility(View.VISIBLE);
    ;
    // mbtn_check_on.setOnClickListener(myListener);
    mbtnclose_normal.setClickable(true);
    mbtn_check_on.setClickable(true);

    return true;

  }

  @Override
  public boolean onTouchEvent(MotionEvent me) {
    return gestureScanner.onTouchEvent(me);
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
    if (getquestionString.contains("two") && stringTokenizer.countTokens() > 0)
      multipleAnswersItems.remove(0);
    return multipleAnswersItems;
  }
}