package com.mayera;

import java.io.InputStream;
import java.util.List;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class KnowledgeActivity extends ListActivity {
  TextView mList;
  ImageView mimage;
 View currentItem;
  ListView lv;
  static Toast toast;
  
  private final Handler toastHandler = new Handler();
  int DURATION_OF_TOAST_DISPALY = 2000;
  

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(com.mayera.R.layout.main);
    toast = Toast.makeText(KnowledgeActivity.this, "", Toast.LENGTH_SHORT);
   
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
   
  toast= Toast.makeText( this.getApplicationContext(),correctAnswer,2000);
   getView(toast);toast.show();
  }
  
  public View getView(Toast toast) {
      
  currentItem = toast.getView();
		   
		    {
		    	
		    	Animation stringrotate = AnimationUtils.loadAnimation(KnowledgeActivity.this, R.animator.property_stringanimator);
		    	stringrotate.setDuration(1000);
		    	currentItem .setAnimation(stringrotate);
		    		
		    }
		    
		   
		    return new View(this.getBaseContext());
  }

  
  
  
  
}