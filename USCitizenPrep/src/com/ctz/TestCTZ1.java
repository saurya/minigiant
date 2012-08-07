package com.ctz;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ctz.SimpleGestureFilter.SimpleGestureListener;

@SuppressLint("NewApi")
public class TestCTZ1 extends Activity implements OnGestureListener,
    SimpleGestureListener {

  boolean i_got_it, time_out;
  private GestureDetector gestureScanner;
  private SimpleGestureFilter detector;

  @SuppressLint({ "NewApi", "NewApi", "NewApi" })
  OnClickListener myListener;
  EditText edittext;//
  private TextView mGetQuestionString;
  private TextView mgetAnswerString;
  private TextView mtimerTextField;
  private TextView mScore;

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

  int[] originalQNums = new int[100];
  ImageView mbtnclose_normal;
  TextView mgetReport;
  ImageView mbtn_check_on, mbtn_check_on_disabled;;
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

  public void calculateScores() {
    if (i_got_it)
      if (!time_out)
        score++;
    scoreString = score + "/" + cnt;
    i_got_it = false;

  }

  public void callrestofthecode() {
    runtimer.cancel();
    {
      SharedPreferences prefs = PreferenceManager
          .getDefaultSharedPreferences(this);
      ;
      cnt++;
      ctz_ans = -1;
      {
        calculateScores();
        mScore.setText(scoreString);
      }
      if (cnt == userselectQns || cnt == justfound) {
        decideVisibility(true);
        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);

        mgetReport.setText(getreportString());
        mgetReport.setVisibility(View.VISIBLE);
      } else {
        decideVisibility(false);
        getquestionString = getnextqn(cnt);
        mGetQuestionString.setText(getquestionString);
        mgetAnswerString.setText(anslist[cnt]);
        mgetAnswerString.setVisibility(View.GONE);
        mbtn_check_on.setEnabled(true);
        cnt1 = originalQNums[cnt];
        runtimer.start();
      }
    }
  }

  private void calltimer(SharedPreferences prefs) {
    /*
     * { mbtn_check_on.setVisibility(View.VISIBLE);
     * 
     * mbtn_check_on.setEnabled(true); mbtn_check_on.setClickable(true); }
     */

    time_out = false;
    long timer = prefs.getLong("TIME", userselecttiming * 1000);
    runtimer = new CountDownTimer(timer, 1000) {

      @Override
      public void onFinish() {
        mtimerTextField.setText("done!");
        i_got_it = false;
        mgetAnswerString.setVisibility(View.VISIBLE);
        mbtnclose_normal.setVisibility(View.VISIBLE);
        {
          mbtn_check_on.setVisibility(View.GONE);
          // mbtn_check_on.setOnClickListener(null);
          mbtn_check_on.setEnabled(false);
          mbtn_check_on_disabled.setVisibility(View.VISIBLE);
        }
        // mToast.setText("Sorry, time is up!");

        // mToast.show();
        mbtn_check_on_disabled.setVisibility(View.VISIBLE);
      }

      @Override
      public void onTick(long elapsed) {

        mtimerTextField.setText("" + elapsed / 1000);
      }
    }.start();

  }

  public void decideVisibility(boolean hide) {

    if (hide) {
      mbtn_check_on.setVisibility(View.GONE);
      mbtnclose_normal.setVisibility(View.GONE);
      mgetAnswerString.setVisibility(View.GONE);
      mGetQuestionString.setVisibility(View.GONE);
      mtimerTextField.setVisibility(View.GONE);

    } else {
      mgetAnswerString.setVisibility(View.VISIBLE);
      mGetQuestionString.setVisibility(View.VISIBLE);
      mtimerTextField.setVisibility(View.VISIBLE);
      mbtnclose_normal.setVisibility(View.GONE);
      mbtn_check_on.setVisibility(View.GONE);

      mbtn_check_on_disabled.setVisibility(View.GONE);
      // mbtn_check_on.setOnClickListener(myListener);
      mbtn_check_on.setClickable(true);
      mbtnclose_normal.setClickable(true);
      Log.d("status5 ",
          "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
              + mbtn_check_on.getVisibility());
    }
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent me) {
    super.dispatchTouchEvent(me);
    this.detector.onTouchEvent(me);
    return super.dispatchTouchEvent(me);
  }

  public String getnextqn(int cnt) {
    int qnnumber = cnt + 1;
    return "Qusestion# " + qnnumber + ": " + qnlist[cnt];
  }

  public String getreportString() {
    return ("\n\n\n" + "   Scores Based On Self-Evaluation " + "\n\n\n"
        + "Questions Prescribed On Website:  100" + "\n\n"
        + "Number of Questions I selected in random fashion:  " + userselectQns
        + "\n\n" + "Time per Question: " + userselecttiming + " seconds \n\n"
        + "I know answers for: " + score + " Question(s)\n\n"
        + "I Missed answers for: " + (userselectQns - score) + " Question(s)\n\n")
        + "Comment:\n\n In the actual test you are required to get answers for 5 to 6 questions right out of 10."
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
    getAnswerString0 = anslist[begin];
    getquestionString0 = "Question# 1: " + qnlist[begin];
    mGetQuestionString.setText(getquestionString0);
    mGetQuestionString.setVisibility(View.VISIBLE);
    mgetAnswerString.setVisibility(View.GONE);
    mbtnclose_normal.setVisibility(View.GONE);
    mbtn_check_on.setVisibility(View.GONE);
    mgetReport.setVisibility(View.GONE);
    calltimer(PreferenceManager.getDefaultSharedPreferences(this));
    mgetAnswerString.setText(getAnswerString0);
    currentdisplay = 0;
    originalQnumber = originalQNums[0];
  }

  public void newOnclick(View v) {

    if (!time_out) {
      i_got_it = true;
      callrestofthecode();

    }

  }

  public void newOnclick2(View v) {

    i_got_it = false;
    callrestofthecode();
  }

  @Override
  public void onBackPressed() {
    if (cnt < userselectQns) {
      testscores_dialog_builder = new AlertDialog.Builder(TestCTZ1.this);
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
    } else if (mgetReport.getVisibility() == View.VISIBLE) {
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
      mScore.setText(" 0/0 ");
      mtimerTextField.setVisibility(View.VISIBLE);
      runtimer.start();
      go_For_NextRound(begin, end);
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

    getAnswerString0 = anslist[begin];
    getquestionString0 = "Question# 1: " + qnlist[begin];

    mgetReport = (TextView) findViewById(R.id.getReport);
    mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
    mgetAnswerString = (TextView) findViewById(R.id.getAnswerString);
    mScore = (TextView) findViewById(R.id.scoreField);

    myListener = new View.OnClickListener() {
      public void onClick(View v) {
        newOnclick(v);
      }
    };

    mtimerTextField = (TextView) findViewById(R.id.timerTextField);
    mbtnclose_normal = (ImageView) findViewById(R.id.btn_close_normal);
    mbtn_check_on = (ImageView) findViewById(R.id.btn_check_on);
    mbtn_check_on_disabled = (ImageView) findViewById(R.id.btn_check_on_disabled);
    mbtn_check_on_disabled.setOnClickListener(null);
    mbtn_check_on.setOnClickListener(myListener);
    mGetQuestionString.setText(getquestionString0);
    mgetAnswerString.setVisibility(View.GONE);
    mbtnclose_normal.setVisibility(View.GONE);
    mbtn_check_on.setVisibility(View.GONE);
    mgetReport.setVisibility(View.GONE);

    calltimer(PreferenceManager.getDefaultSharedPreferences(this));
    mgetAnswerString.setText(getAnswerString0);
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
    runtimer.cancel();
    mgetAnswerString.setVisibility(View.VISIBLE);
    mbtnclose_normal.setVisibility(View.VISIBLE);
    mbtn_check_on.setVisibility(View.VISIBLE);
    mbtnclose_normal.setClickable(true);
    mbtn_check_on.setClickable(true);
    Log.d("status3 ",
        "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
            + mbtn_check_on.getVisibility());
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
    runtimer.cancel();
    mgetAnswerString.setVisibility(View.VISIBLE);
    mbtnclose_normal.setVisibility(View.VISIBLE);
    mbtn_check_on.setVisibility(View.VISIBLE);
    mbtnclose_normal.setClickable(true);
    mbtn_check_on.setClickable(true);
    Log.d("status2 ",
        "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
            + mbtn_check_on.getVisibility());
    return true;
  }

  public boolean onSingleTapUp(MotionEvent e) {
    if (mbtn_check_on.getVisibility() == View.GONE)
      return false;
    runtimer.cancel();
    mgetAnswerString.setVisibility(View.VISIBLE);
    mbtnclose_normal.setVisibility(View.VISIBLE);
    mbtn_check_on.setVisibility(View.VISIBLE);
    // mbtn_check_on.setOnClickListener(myListener);
    mbtnclose_normal.setClickable(true);
    mbtn_check_on.setClickable(true);
    Log.d("status4 ",
        "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
            + mbtn_check_on.getVisibility());
    return true;

  }

  @Override
  public boolean onTouchEvent(MotionEvent me) {
    return gestureScanner.onTouchEvent(me);
  }
}