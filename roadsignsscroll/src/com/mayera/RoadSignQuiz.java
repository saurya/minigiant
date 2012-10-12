package com.mayera;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RoadSignQuiz extends Activity {
  List<RoadSign> roadSigns;
  Iterator it;
  RoadSign previous;
  private final String question = "Identify the sign by picking the correct answer:";
  List<RadioButton> multipleAnswers = new ArrayList<RadioButton>();
  TextView mQuestionString;
  private int score;
  private TextView mPrintScores;
  private ImageView mIcon;
  private Button mButtonNext;
  static int[] radioButtonIdsCopy;
  static final int SizeOfQuestionBank = 54;

  private void assignAnswers(RoadSign roadSign) {
    List<String> answers = roadSign.getAnswers();
    Collections.shuffle(answers);
    for (int i = 0; i < multipleAnswers.size(); i++) {
      multipleAnswers.get(i).setText(answers.get(i).trim());
    }
  }

  private void assignIcon(RoadSign roadSign) {

    mIcon.setBackgroundResource(roadSign.getIcon());
  }

  private int checkCorrectness(RoadSign roadSign) {
    for (RadioButton r : multipleAnswers) {
      if (r.isChecked()) {
        ((RadioGroup) findViewById(com.mayera.R.id.radiobtnGrp)).clearCheck();
        if (r.getText().equals(roadSign.getCorrectAnswer().trim())) {

          return 1;
        }

      }

    }

    return 0;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.mayera.R.layout.exam);
    InputStream is = this.getResources().openRawResource(
        com.mayera.R.raw.firstanswercorrect);// dmvsignsonly0);
    roadSigns = RoadSign.populateRoadSign(is);

    Collections.shuffle(roadSigns);
    it = roadSigns.iterator();
    int[] radioButtonIds = { com.mayera.R.id.radioA, com.mayera.R.id.radioB,
        com.mayera.R.id.radioC, com.mayera.R.id.radioD };
    radioButtonIdsCopy = radioButtonIds;
    for (int i : radioButtonIds) {
      multipleAnswers.add((RadioButton) findViewById(i));
    }
    mQuestionString = (TextView) findViewById(com.mayera.R.id.questionString);
    mQuestionString.setText(question);
    mPrintScores = (TextView) findViewById(com.mayera.R.id.printScores);
    mIcon = (ImageView) findViewById(com.mayera.R.id.iconic);
    mButtonNext = (Button) findViewById(com.mayera.R.id.buttonNext);
    transitionToNextQuestion();
    mButtonNext.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        transitionToNextQuestion();
      }
    });

  }

  private void printScores() {
    // TODO Auto-generated method stub
    String scoreString = "Your Score is " + score + "/" + SizeOfQuestionBank;
    Builder scoreDB = new AlertDialog.Builder(RoadSignQuiz.this);
    scoreDB.setMessage(scoreString).setCancelable(false)
        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            finish();
          }
        });
    AlertDialog scoresDialog = scoreDB.create();
    scoresDialog.show();
    return;

  }

  private void transitionToNextQuestion() {
    if (previous != null) {
      score += checkCorrectness(previous);
      mPrintScores.setText(score + "/" + SizeOfQuestionBank);
    }
    if (it.hasNext()) {
      RoadSign roadSign = (RoadSign) it.next();
      assignAnswers(roadSign);
      assignIcon(roadSign);
      previous = roadSign;

    } else {
      printScores();
    }

  }

}
