package com.mayera;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UserChoice extends Activity {
  Button mPractise, mTest;
  Button mGoButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.mayera.R.layout.frontpage);
    mPractise = (Button) findViewById(com.mayera.R.id.practise);
    mTest = (Button) findViewById(com.mayera.R.id.test);

    mPractise.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        {
          Intent myIntent2User = new Intent();
          {
            myIntent2User.setClassName("com.mayera",
                "com.mayera.KnowledgeActivity");
            startActivity(myIntent2User);
          }
        }
      }
    });

    mTest.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        {
          Intent myIntent2User = new Intent();
          {
            myIntent2User.setClassName("com.mayera", "com.mayera.KnowledgeQuiz");
            startActivity(myIntent2User);
          }
        }
      }
    });

  }
}
