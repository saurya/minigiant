package com.mayera;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class KnowledgeQuiz extends Activity {
  List<Question> roadSigns;
  Iterator it;
  Question previous;
  private  String question;
  List<RadioButton> multipleAnswers = new ArrayList<RadioButton>();
  TextView mQuestionString;
  private int score;
  private TextView mPrintScores;
  private ImageView mIcon;
  private Button mButtonNext;
Dialog scoresDialog;
private int numberOfExamQuestions=50;
int currentsession;
  static int[] radioButtonIdsCopy;
  static final int SizeOfQuestionBank = 82;

  private void assignAnswers(Question roadSign) {
    List<String> answers = roadSign.getAnswers();
    Collections.shuffle(answers);
    for (int i = 0; i < multipleAnswers.size(); i++) {
      multipleAnswers.get(i).setText(answers.get(i).trim());
    }
  }

  private void assignQuestion(Question roadSign) {
	  mQuestionString.setText(roadSign.getQuestion());    
	  }

  private int checkCorrectness(Question roadSign) {
	  int scoreValue=0;
    for (RadioButton r : multipleAnswers) {
    	Log.d("roadSign.getCorrectAnswer()",roadSign.getCorrectAnswer()+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+r.getText());
      if (r.isChecked() && ((String)roadSign.getCorrectAnswer().trim()).
    		  equals((r.getText()).toString().trim())) {        	
          scoreValue= 1;
 }
   
  }
    ((RadioGroup) findViewById(com.mayera.R.id.radiobtnGrp)).clearCheck();
    return scoreValue;
  }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.mayera.R.layout.exam);
    InputStream is = this.getResources().openRawResource(
        com.mayera.R.raw.nj_driver_qns);// dmvsignsonly0);
    //Get QuestionBankSize number of Questions
    roadSigns = Question.populateKnowledge(is);
    Collections.shuffle(roadSigns);
    currentsession = numberOfExamQuestions+1;
    it = roadSigns.iterator();
    int[] radioButtonIds = { com.mayera.R.id.radioA, com.mayera.R.id.radioB,
        com.mayera.R.id.radioC, com.mayera.R.id.radioD };
    radioButtonIdsCopy = radioButtonIds;
    for (int i : radioButtonIds) {
      multipleAnswers.add((RadioButton) findViewById(i));
    }
    mQuestionString = (TextView) findViewById(com.mayera.R.id.questionString);   
    mPrintScores = (TextView) findViewById(com.mayera.R.id.printScores);
    //mIcon = (ImageView) findViewById(com.mayera.R.id.iconic);
    mButtonNext = (Button) findViewById(com.mayera.R.id.buttonNext);
    transitionToNextQuestion();
    mButtonNext.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        transitionToNextQuestion();
      
      }});

  }

  private void printScores() {
    
    String scoreString = "Your Score is " + score + "/" + numberOfExamQuestions+"which is "+score*(100/ numberOfExamQuestions)+"%"+"\n"+
                          "Minimum requirements: 80 percent, or 40 correct answers out of 50 questions. ";
    Builder scoreDB = new AlertDialog.Builder(KnowledgeQuiz.this);
    scoreDB.setMessage(scoreString)
           .setCancelable(false)
           .setPositiveButton("Back", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
        	 finish();
          }
        });

   scoresDialog = scoreDB.create();
    scoresDialog.show();
    


  }

  private void transitionToNextQuestion() {
    if (previous != null) {
    	//Go through only what is numberOfExamQuestions
    	currentsession--;
      score += checkCorrectness(previous);
      mPrintScores.setText(score + "/" + numberOfExamQuestions);
    }
    if (it.hasNext() && currentsession>0) {
      Question roadSign = (Question) it.next();
      assignAnswers(roadSign);
      assignQuestion(roadSign);
      Log.d("Question", roadSign.getQuestion()+"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
      previous = roadSign;

    } else if(currentsession==0){
      printScores();
     return;
 
    }

  }

}
