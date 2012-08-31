package com.ctz;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ctz.SimpleGestureFilter.SimpleGestureListener;

@SuppressLint("NewApi")
public class TestCTZ1best extends ListActivity implements OnGestureListener,
    SimpleGestureListener {

  private GestureDetector gestureScanner;
  private SimpleGestureFilter detector;

  @SuppressLint({ "NewApi", "NewApi", "NewApi" })
  OnClickListener myListener;

  // GUI for wysiwyg
  private View main2_View;
  private TextView mGetQuestionString;
  private TextView mgetAnswerString;
  private TextView mrightViewText, mwrongViewText, mdisabledViewText;
  private TextView mtimerTextField;
  private TextView mScore;
  String scoreString = "Your Score Now Is";
  String coveredstring1, coveredstring2;// random and original strings of
                                        // question numbers covered
  String prtmultiplestrg;

  // DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW

  int questionnumber, current_Qn_length;
  int cnt = 0;
  int score = 0;
  boolean test_interrupted;
  int cnt1, correct_ans, begin, end;
  // LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
  ArrayList<String> multipleAnswersItems = new ArrayList<String>();
  ArrayAdapter<String> adapter;

  // timer GUI and variables
  CountDownTimer runtimer;
  final static long seconds_in_milllies = 1000L;
  final static long minutes_in_millies = seconds_in_milllies * 60;
  final static long hours_in_millies = minutes_in_millies * 60;
  static AlertDialog.Builder testscores_dialog_builder, builder;
  static AlertDialog testscores_dialog, alert;
  ImageView mbtnclose_normal;
  TextView mgetReport;
  ImageView mbtn_check_on;
  ImageView mbtn_check_on_disabled;;

  // GUI and variables for dropdown
  Button magichandle, invertimage;
  private ListView mmultipleanswerlist;
  private ImageView mslideHandleButton;
  private SlidingDrawer mslidingDrawer;
  boolean mslidingdrawer_open;
  HashSet<Integer> multansqnnums;
  boolean i_got_it, time_out;

  String getAnswerString0 = ""; // set first answer to be displayed
  String getquestionString0 = "";// set first question to be displayed
  String getAnswerString = "";// and the next answer
  String getquestionString = "";// and the next question
  String[] question_list;
  String[] answer_list;
  int[] randomqn_original_index = new int[100];
  int justfound;
  int userselectQns;
  int userselecttiming;
  int counter_for_display;
  int numberofrounds;

  Toast mToast;;

  boolean addItemscalled = false;

  public void addItems(View v) {

    mmultipleanswerlist.setVisibility(View.GONE);
    addItemscalled = true;
    if (addItemscalled)
      adapter.clear();
    prtmultiplestrg = answer_list[cnt];
    multipleAnswersItems = tokens(prtmultiplestrg);
    adapter.notifyDataSetChanged();
    mmultipleanswerlist.setVisibility(View.VISIBLE);
  }

  public void calculateScores() {

    if (i_got_it)
      if (!time_out)
        score++;
    scoreString = score + "/" + counter_for_display;
    i_got_it = false;

  }

  public void callrestofthecode() {

    runtimer.cancel();
    {

      coveredstring1 += cnt + ",";
      coveredstring2 += randomqn_original_index[cnt] + ",";
      cnt++;
      counter_for_display++;

      {
        calculateScores();
        mScore.setText(scoreString);
      }
      if (counter_for_display == userselectQns || cnt == justfound) {
        decideVisibility(true);

        mgetReport.setText(getreportString());
        mgetReport.setVisibility(View.VISIBLE);
      } else {
        decideVisibility(false);
        mbtn_check_on.setClickable(true);
        getquestionString = getnextqn(cnt);

        mGetQuestionString.setText("cnt here" + cnt + " "
            + randomqn_original_index[cnt] + getquestionString);
        getAnswerString = "cnt here" + cnt + answer_list[cnt];
        // check_if_multipleanswers();
        display_answer();
        cnt1 = randomqn_original_index[cnt];
        runtimer.start();
      }
    }

  }

  private void calltimer(SharedPreferences prefs) {

    mbtn_check_on.setClickable(true);
    magichandle.setVisibility(View.GONE);
    invertimage.setVisibility(View.GONE);
    time_out = false;
    long timer = prefs.getLong("TIME", userselecttiming * 1000);
    runtimer = new CountDownTimer(timer, 1000) {

      @Override
      public void onFinish() {
        mtimerTextField.setText("done!");

        if (getAnswerString.contains("*")) {
          magichandle.setVisibility(View.VISIBLE);
          mslidingDrawer.setVisibility(View.VISIBLE);
          mslideHandleButton.setVisibility(View.VISIBLE);
        } else {
          magichandle.setVisibility(View.GONE);
          mslidingDrawer.setVisibility(View.GONE);
          mslideHandleButton.setVisibility(View.GONE);
        }

        display_answer();
        mgetAnswerString.setVisibility(View.VISIBLE);
        if (magichandle.getVisibility() == View.VISIBLE
            && !(getAnswerString.contains("*")))
          magichandle.setVisibility(View.GONE);
        if (magichandle.getVisibility() == View.GONE
            && getAnswerString.contains("*"))
          magichandle.setVisibility(View.VISIBLE);
        mdisabledViewText.setVisibility(View.VISIBLE);
        mbtn_check_on_disabled.setVisibility(View.VISIBLE);
        mbtnclose_normal.setVisibility(View.VISIBLE);
        mwrongViewText.setVisibility(View.VISIBLE);
        i_got_it = false;
        mbtn_check_on.setVisibility(View.GONE);
        mrightViewText.setVisibility(View.GONE);

      }

      @Override
      public void onTick(long elapsed) {

        mtimerTextField.setText("" + elapsed / 1000);
      }
    }.start();

  }

  private void check_if_multipleanswers() {// you need a sliding drawer if so

    if (getAnswerString.contains("*")) {
      mToast.setText("cnt= " + cnt + "," + randomqn_original_index[cnt]
          + "show button");
      mToast.show();
      mslidingDrawer.setVisibility(View.VISIBLE);
      mslideHandleButton.setVisibility(View.VISIBLE);

      magichandle.setVisibility(View.VISIBLE);
    } else {

      magichandle.setVisibility(View.GONE);

      mslideHandleButton.setVisibility(View.GONE);
      mslidingDrawer.setVisibility(View.GONE);
      mmultipleanswerlist.setVisibility(View.GONE);

      mslidingDrawer.setVisibility(View.GONE);
      mgetAnswerString.setVisibility(View.VISIBLE);

    }

  }

  public void decideVisibility(boolean hide) {

    if (hide) {// for report hide all components
      mbtn_check_on.setVisibility(View.GONE);
      mbtnclose_normal.setVisibility(View.GONE);
      mrightViewText.setVisibility(View.GONE);
      mwrongViewText.setVisibility(View.GONE);
      mgetAnswerString.setVisibility(View.GONE);
      magichandle.setVisibility(View.GONE);
      mGetQuestionString.setVisibility(View.GONE);
      mtimerTextField.setVisibility(View.GONE);

    } else {
      // otherwise always stay in view
      mGetQuestionString.setVisibility(View.VISIBLE);
      mtimerTextField.setVisibility(View.VISIBLE);

      // come into view only when user taps
      mbtnclose_normal.setVisibility(View.GONE);
      mbtn_check_on.setVisibility(View.GONE);
      mbtn_check_on.setClickable(true);
      mbtnclose_normal.setClickable(true);
      mrightViewText.setVisibility(View.GONE);
      mwrongViewText.setVisibility(View.GONE);
      mbtn_check_on_disabled.setVisibility(View.GONE);
      mdisabledViewText.setVisibility(View.GONE);
      mgetAnswerString.setVisibility(View.GONE);
      magichandle.setVisibility(View.GONE);
    }

  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent me) {

    super.dispatchTouchEvent(me);
    this.detector.onTouchEvent(me);

    return super.dispatchTouchEvent(me);
  }

  private void display_answer() {// display either the only answer or first or 2
                                 // or 3 depending on the question

    String answer_left = "", second_answer = "", third_answer = "", remaining = "";
    String display = getAnswerString;// default, the whole string
    int till = getAnswerString.indexOf("*");// multiple answers separated by a *
                                            // in data

    if (till > 0) {
      display = (getAnswerString.substring(0, till));// yanked first answer

      if ((getquestionString.contains("two") && !getquestionString
          .contains("parts")) || getquestionString.contains("three")) {
        answer_left = getAnswerString.substring(till + 1);
        till = answer_left.indexOf("*");
        if (till > 0) {
          second_answer = answer_left.substring(0, till);
          display += ", " + second_answer;
        }
        if (getquestionString.contains("three")) {
          remaining = answer_left.substring(till + 1);
          till = remaining.indexOf("*");
          if (till > 0) {
            third_answer = remaining.substring(0, till);
            display += ", " + third_answer;
          }
        }
      }
    }
    mgetAnswerString.setText(display);

  }

  public String getnextqn(int cnt) {

    if (cnt >= 100) {
      mToast
          .setText("Congratulations. You just completed practice of 100 questions");
      mToast.show();
      finish();
    }
    ;
    return question_list[cnt];
  }

  public String getreportString() {
    return ("\n\n\n" + "   Scores Based On Self-Evaluation " + "\n\n\n"
        + "Questions Prescribed On Website:  100" + "\n\n"
        + " random Questions " + userselectQns + "\n\n" + "Timer "
        + userselecttiming + " seconds \n\n" + "Right: " + score
        + " Question(s)\n\n" + "Wrong: " + (userselectQns - score))
        + coveredstring1
        + " "
        + coveredstring2
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
    if (end > 100)// if 40 questions per round third round is 3*40=120 So push
                  // back to 100
      end = 100;
    if (begin >= 100) {
      mToast
          .setText("Congratulations. You just completed practice of 100 qustions");
      mToast.show();
      finish();
    }
    cnt = begin;
    getAnswerString = answer_list[cnt];

    getquestionString0 = question_list[cnt];
    mGetQuestionString.setText((numberofrounds) + " " + begin + " "
        + getquestionString0);
    mGetQuestionString.setVisibility(View.VISIBLE);

    calltimer(PreferenceManager.getDefaultSharedPreferences(this));
    getAnswerString = answer_list[begin];
    cnt = begin;
    display_answer();
    init_disappear();
    counter_for_display = 0;

    // check_if_multipleanswers();

  }

  private void init_disappear() {

    mrightViewText.setVisibility(View.GONE);
    mwrongViewText.setVisibility(View.GONE);
    mbtn_check_on_disabled.setVisibility(View.GONE);
    mdisabledViewText.setVisibility(View.GONE);
    mgetAnswerString.setVisibility(View.GONE);
    magichandle.setVisibility(View.GONE);
    mbtnclose_normal.setVisibility(View.GONE);
    mbtn_check_on.setVisibility(View.GONE);
    mgetReport.setVisibility(View.GONE);
    mslideHandleButton.setVisibility(View.GONE);
    mslidingDrawer.setVisibility(View.GONE);
    mmultipleanswerlist.setVisibility(View.GONE);
    magichandle.setVisibility(View.GONE);
    invertimage.setVisibility(View.GONE);

  }

  private void multansqnums() {

    multansqnnums = new HashSet<Integer>();
    String str = "";
    int intvalue;
    Iterator<?> it = (PrepNoTimer.multansqnnums).iterator();

    while (it.hasNext()) {
      intvalue = Integer.parseInt((String) it.next());
      str = "(" + intvalue + "," + randomqn_original_index[intvalue] + "),";

      for (int i = 0; i < 100; i++)
        if (randomqn_original_index[i] == intvalue) {
          str = "(" + i + " ORIG " + randomqn_original_index[i] + ")";
          ;
          multansqnnums.add((i));
          break;

        }
    }

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
      testscores_dialog_builder = new AlertDialog.Builder(TestCTZ1best.this);
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
      cnt = begin;
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
    counter_for_display = 0;

    Bundle bundle = new Bundle();
    bundle = this.getIntent().getExtras();

    setContentView(R.layout.main2);
    setDefaultKeyMode(DEFAULT_KEYS_DISABLE);

    {
      userselectQns = bundle.getInt("userselectQns");
      if (userselectQns == 0)
        userselectQns = 3;// default
      question_list = bundle.getStringArray("randomqns");
      answer_list = bundle.getStringArray("anstorandomqns");
      randomqn_original_index = bundle.getIntArray("originalQNums");
      userselecttiming = bundle.getInt("userselecttiming");

      if (userselecttiming == 0)
        userselecttiming = 3;
    }
    multansqnums();
    String temp = "";
    for (int i = 0; i < question_list.length; i++)
      if (question_list[i].contains("*")) {
        temp = question_list[i].replace("*", "");
        question_list[i] = temp;

      }
    multansqnums();

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

    getquestionString = question_list[begin];
    getAnswerString0 = answer_list[begin];
    cnt = begin;
    getAnswerString = getAnswerString0;

    main2_View = findViewById(R.layout.main2);
    mgetReport = (TextView) findViewById(R.id.getReport);
    mGetQuestionString = (TextView) findViewById(R.id.getquestionString);
    mgetAnswerString = (TextView) findViewById(R.id.getAnswerString);
    mrightViewText = (TextView) findViewById(R.id.rightViewText);
    mwrongViewText = (TextView) findViewById(R.id.wrongViewText);
    mdisabledViewText = (TextView) findViewById(R.id.disabled_ViewText);
    mtimerTextField = (TextView) findViewById(R.id.timerTextField);
    mbtnclose_normal = (ImageView) findViewById(R.id.btn_close_normal);
    mbtn_check_on = (ImageView) findViewById(R.id.btn_check_on);
    mbtn_check_on_disabled = (ImageView) findViewById(R.id.btn_check_on_disabled);
    mmultipleanswerlist = (ListView) findViewById(android.R.id.list);
    mslideHandleButton = (ImageView) findViewById(R.id.slideHandleButton);
    mslidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
    magichandle = (Button) findViewById(R.id.magichandle);
    invertimage = (Button) findViewById(R.id.invertimage);
    magichandle = (Button) findViewById(R.id.magichandle);
    invertimage = (Button) findViewById(R.id.invertimage);
    mScore = (TextView) findViewById(R.id.scoreField);

    adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, multipleAnswersItems);
    this.setListAdapter(adapter);

    mGetQuestionString.setText(getquestionString);
    display_answer();

    // Just listen
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

    mbtn_check_on.setOnClickListener(myListener);
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

    getquestionString0 = question_list[cnt];
    mGetQuestionString.setText(getquestionString0);
    mGetQuestionString.setVisibility(View.VISIBLE);

    init_disappear();

    calltimer(PreferenceManager.getDefaultSharedPreferences(this));

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
    if (mbtn_check_on.getVisibility() == View.GONE)
      return;

    if (getAnswerString.contains("*")) {
      check_if_multipleanswers();
      open_answer_check();
    }
    runtimer.cancel();
    display_answer();

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
    if ((getAnswerString.contains("*"))) {
      check_if_multipleanswers();
      open_answer_check();
    }

    runtimer.cancel();
    display_answer();

    return true;
  }

  public boolean onSingleTapUp(MotionEvent e) {

    if (mbtn_check_on.getVisibility() == View.GONE)
      return false;
    if (getAnswerString.contains("*")) {
      check_if_multipleanswers();
      open_answer_check();
    }

    runtimer.cancel();
    display_answer();

    return true;

  }

  @Override
  public boolean onTouchEvent(MotionEvent me) {
    return gestureScanner.onTouchEvent(me);
  }

  private void open_answer_check() {

    if (getAnswerString.contains("*"))
      magichandle.setVisibility(View.VISIBLE);
    else
      magichandle.setVisibility(View.GONE);
    mgetAnswerString.setVisibility(View.VISIBLE);
    mbtnclose_normal.setVisibility(View.VISIBLE);
    mbtn_check_on.setVisibility(View.VISIBLE);
    mrightViewText.setVisibility(View.VISIBLE);
    mwrongViewText.setVisibility(View.VISIBLE);
    mbtnclose_normal.setClickable(true);
    mbtn_check_on.setClickable(true);

  }

  private ArrayList<String> tokens(String str) {// call for multiple answers

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