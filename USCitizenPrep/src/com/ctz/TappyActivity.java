package com.ctz;

import android.app.Activity;
import android.os.Bundle;




import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater.Filter;
import android.view.View.OnClickListener;
import android.widget.Toast;
 
public  class TappyActivity extends Activity implements OnGestureListener
{
    private GestureDetector gestureScanner;
 
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
 
        gestureScanner = new GestureDetector(this);
        setContentView(R.layout.main);
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent me)
    {
        return gestureScanner.onTouchEvent(me);
    }
 
    public boolean onDown(MotionEvent e)
    {
     
        return false;
    }
 
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
      
        return false;
    }
 
    public void onLongPress(MotionEvent e)
    {
    	return ;
    }
 
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        
        return false;
    }
 
    public void onShowPress(MotionEvent e)
    {
        return;
    }
 
    public boolean onSingleTapUp(MotionEvent e)
    {
        Toast.makeText(TappyActivity.this, "Single-Tap event ", Toast.LENGTH_LONG).show();
        return true;
    }
}