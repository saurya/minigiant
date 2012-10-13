package com.mayera;

import java.io.InputStream;
import java.util.List;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class KnowledgeActivity extends ListActivity {
  TextView mList;
  ImageView mimage;
  static Drawable[] signIcon;
  ListView lv;
  Toast toast;
  
  private final Handler toastHandler = new Handler();
  int DURATION_OF_TOAST_DISPALY = 2000;;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.mayera.R.layout.main);
    InputStream is = this.getResources().openRawResource(
    		com.mayera.R.raw.nj_driver_qns);
    List<Question> knowledge = Question.populateKnowledge(is);
    Log.d("Length of the list:","****************************************************************************"+knowledge.size()+"done");
    setListAdapter(new KnowledgeArray(this, 0, knowledge));
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {

    Question selectedValue = (Question) getListAdapter().getItem(position);
    String correctAnswer = selectedValue.getCorrectAnswer();
    Toast.makeText(this, correctAnswer, Toast.LENGTH_SHORT).show();

  }
}