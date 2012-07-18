package com.ctz;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;


public class USCitizenPrep extends Activity implements OnGesturePerformedListener{
	static int userselectQns;;
	static int userselecttiming;
	static int question_bank_size = 100;
	static Toast toast;
	private final Handler toastHandler = new Handler();
	static int duration_of_toast_display = 2000;
	private float mScaleFactor = 1.f;
	int[] randoms = new int[100];
	private ScaleGestureDetector mScaleDetector;
	String selected,currentstate="";
	HashMap<String, String> currentgovernors;
	ArrayList<String> questions_list, answers_list;
	static int positionprev = 0;
	String[] allquestions = new String[question_bank_size];
	String[] nearlygood = new String[question_bank_size];
	String[] funny1 = new String[question_bank_size];
	String[] funny2 = new String[question_bank_size];
	String[] allanswers = new String[question_bank_size];;
	String senator_of_state="",governor_of_state="";
	String[] state=new String[100];
	private static  String []statenames;
	String[] sentr=new String[100];
	private String selected_type;
	private Button go_Button;
	private Button exit_Button;
	private RadioButton radio_prepare_for_interview;
	private RadioButton radio_test_yourself;
	private RadioButton radio_senior_prepare_for_interview;
    private static String user_selection;
    private static String user_state;
    private GestureLibrary gLib;
	/** Called when the activity is first created. */
	@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		read_Rawdata();
		governorData();
		getSenators();
	//	getGovernor();
		setContentView(com.ctz.R.layout.main);
		 GestureOverlayView gOverlay = (GestureOverlayView) findViewById(R.id.gestures);
	        gOverlay.addOnGesturePerformedListener(USCitizenPrep.this); 	
		gLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gLib.load()) {
            finish();
       }
        
       
		questions_list = new ArrayList<String>();
		answers_list = new ArrayList<String>();
		final Spinner spinner = (Spinner) findViewById(R.id.state_spinner);
		//findViewById(R.id.state_spinner).setBackgroundColor(R.color.yyellow);
		// Create an ArrayAdapter using the string array and a default spinner layout
		//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		      //  R.array.states_array, android.R.layout.simple_spinner_item);
		//spinner.setBackgroundColor(color.holo_blue_bright);
				ArrayAdapter adapter = new ArrayAdapter(
						this,
						android.R.layout.simple_spinner_item, 
						statenames);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		//DialogInterface dialog;
		//spinner.setBackgroundColor(color.transparent);
		spinner.setAdapter(adapter);spinner.setFocusable(true);
		//spinner.setOnItemSelectedListener(adapter..OnItemSelectedListener();
		//spinner.onClick( new DialogInterface.OnClickListener(), Toast.LENGTH_SHORT);
		
		
	/*	final EditText edittext = (EditText) findViewById(R.id.edittext);

		edittext.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					user_state=(edittext.getText()).toString();
					
					Toast.makeText(USCitizenPrep.this,user_state,
							Toast.LENGTH_SHORT).show();
					return true;
				}
				return false;
			}
		});
		
		*<EditText
        android:id="@+id/edittext"
        android:maxLength="2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center_vertical"></EditText>  
        </LinearLayout>
		
		*/

		radio_prepare_for_interview = (RadioButton) findViewById(com.ctz.R.id.radio_prepare_for_interview);
		radio_test_yourself = (RadioButton) findViewById(com.ctz.R.id.radio_test_yourself);
		radio_senior_prepare_for_interview = (RadioButton) findViewById(com.ctz.R.id.radio_senior_prepare_for_interview);

		go_Button = (Button) findViewById(com.ctz.R.id.go);
		exit_Button = (Button) findViewById(com.ctz.R.id.exit);
		
		exit_Button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				 AlertDialog.Builder builder = new AlertDialog.Builder(USCitizenPrep.this);
				builder.setMessage("Are you sure you want to exit?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   USCitizenPrep.this.finish();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
				//finish();
			}
		});
		go_Button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//v.setBackgroundResource(R.drawable.android_pressed);
				//findViewById(R.id.state_spinner).setBackgroundResource(R.drawable.android_pressed);
				//Toast.makeText(USCitizenPrep.this, "Button clicked",
						//Toast.LENGTH_SHORT).show();

				String current_activity="";
				Bundle bundle = new Bundle();
				Bundle qnbundle = new Bundle();
				Intent myIntent2 = new Intent();
	
				if (v == go_Button) {
					currentstate=spinner.getSelectedItem().toString();	
					//Toast.makeText(USCitizenPrep.this, String.valueOf("State: "+spinner.getSelectedItem()),Toast.LENGTH_SHORT).show();
					if (radio_test_yourself.isChecked() == true) {

						selected_type = new String("2");
						
				 {
							copy("random");
							
							Log.d("CAme :","thisfar1*********************************************************");
							String[] qns = new String[questions_list.size()];
							String[] ans = new String[answers_list.size()];
							Object[] qnobarr = questions_list.toArray();
							Object[] ansobarr = answers_list.toArray();
							for (int i = 0; i < 100; i++) {
								qns[i] = (String) qnobarr[i];// (String[]) questions_list.toArray()
								ans[i] = (String) ansobarr[i];
							}
							Log.d("CAme :","thisfar2*********************************************************");
							// bundle up the 10 relevant MCQ wrong answers
							String[] nearlygood_10 = new String[100];
							String[] funny1_10 = new String[100];
							String[] funny2_10 = new String[100];
							for (int i = 0; i < 100; i++) {
								nearlygood_10[i] = nearlygood[randoms[i]];
								funny1_10[i] = funny1[randoms[i]];
								funny2_10[i] = funny2[randoms[i]];
							}
							qnbundle.putStringArray("nearlygood_10", nearlygood_10);// deceptive
							qnbundle.putStringArray("funny1_10", funny1_10);// fake answerset 1
							qnbundle.putStringArray("funny2_10", funny2_10);// fake answerset 2
							Log.d("Came :","thisfar3*********************************************************");
							// bundle up randomly selected question numbers needed for scoring
							qnbundle.putIntArray("originalQNums", randoms);
							
							
							// bundle up randomly selected test questions and answers
							qnbundle.putStringArray("randomqns", qns);
												
							qnbundle.putStringArray("anstorandomqns", ans);	
							Log.d("CAme :","thisfar4*********************************************************");
							//finish();
						}
					}
					
					
					
					
					if (radio_prepare_for_interview.isChecked() == true) {
						selected_type = new String("1");
						
						copy("total");
						

					}
					if (radio_senior_prepare_for_interview.isChecked() == true) {

						selected_type = new String("3");
						copy("senior");
						
						
					}

				}
				
				//String current_class="";
				if(selected_type==null){//Toast.makeText(USCitizenPrep.this, "Plkease choose an activity",
						//Toast.LENGTH_SHORT).show();
					return;}
				if(selected_type.equals("1") ||selected_type.equals("3"))
					{
				StringBuilder sb = new StringBuilder().append(selected_type);
				bundle.putString(user_selection, sb.toString());
				
				
				
			bundle.putStringArray("allquestions",allquestions);
			senator_of_state=getSenator(currentstate);	
			allanswers[19]=senator_of_state;//update here based on user-provided info
			//general category Seniors don't remember anyway. Just kidding. The list for seniors
			governor_of_state=getGovernor(currentstate);
			 bundle.putStringArray(" allanswers",allanswers );
				myIntent2.putExtras(bundle);
				myIntent2.setClassName("com.ctz",
						"com.ctz.US_CitizenActivity");
				startActivity(myIntent2);
					}
				
				
				if(selected_type.equals("2") )
				{
					
				myIntent2.setClassName("com.ctz",
						"com.ctz.Gather_UserChoices");
				
				myIntent2.putExtras(qnbundle);
				startActivity(myIntent2);

				
				
			//	Log.d("userselectQns= userselecttiming=",userselectQns+" "+userselecttiming+"****************************?????????????");
				}
				

			}
		});
		
	}
	 
	    
	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {//v.setBackgroundResource(R.drawable.android_pressed);
		Toast.makeText(USCitizenPrep.this, "Button clicked"+statenames[position],
		Toast.LENGTH_SHORT).show();
		
	}
	@SuppressLint({ "NewApi", "NewApi" })
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
        ArrayList<Prediction> predictions = gLib.recognize(gesture);
        
        if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
            
        	String action = predictions.get(0).name;
        	
            Toast.makeText(USCitizenPrep.this, action, Toast.LENGTH_SHORT).show();
        }
    }
	public void onNothingSelected(AdapterView<?> parent) {
		Toast.makeText(USCitizenPrep.this, "",
				Toast.LENGTH_SHORT).show();
	}
	public void copy(String s) {
		int start, end;

		if (s.equals("total")) {
			answers_list.clear();
			questions_list.clear();
			start = 0;
			end = allquestions.length;
			for (int i = start; i < end; i++) {
				questions_list.add(allquestions[i]);
				answers_list.add(allanswers[i]);
			}
			
		}

		else if (s.equals("random")) {
			answers_list.clear();
			questions_list.clear();
			Random rands = new Random();
			String randomstr = "aaa,";
			int nums = allquestions.length;			
			int k, count = 0;
			
			randoms = new int[100];
			for (int i = 0; i < 100; i++) {
				questions_list.add(allquestions[i]);
				answers_list.add(allanswers[i]);
				randoms[i]=i;
			}
		/*	while (count < 100) {
				k = rands.nextInt(nums);
				if (!randomstr.contains(k + "")) {
					randomstr += (k);
					randoms[count] = k;

					count++;
					questions_list.add("" + allquestions[k]);
					answers_list.add("" + allanswers[k]);
				}
			} // return (String[]) qns.toArray();
			*/
		}

		else if (s.equals("senior")) {
			answers_list.clear();
			questions_list.clear();
			start = 0;

			end = allquestions.length;
			Log.d("Length: ",end+"");
			for (int i = start; i < end; i++)
				if (allquestions[i].contains("?*")) {
					questions_list.add(allquestions[i]);
					answers_list.add("" + allanswers[i]);
				}
			
		}

	}
	
	
	private String getSenators(){
		try {
			//  Log.d("printinfo","gettingit ***************");
		  URL senators=  new URL("http://www.senate.gov/general/contact_information/senators_cfm.xml");
		//  Log.d("printinfo","gotit ***************");
		 
		  String[] firstname=new String[100];
		  String[] lastname=new String[100];
		  
		  BufferedReader in = new BufferedReader(
			        new InputStreamReader(senators.openStream()));
		int cnt=0;
			        String inputLine,str,str1,str2,str3,str4,str5,str6;
			        while ((inputLine = in.readLine()) != null )
			        { 
			        	if (inputLine.contains("first_name"))firstname[cnt]=inputLine;
			        	if (inputLine.contains("last_name"))lastname[cnt]=inputLine;
			        	if (inputLine.contains("state")){state[cnt]=inputLine;
			        	cnt++;}
			        }
			        
		  for (int i=0;i<100;i++)
		  {
			   str1= ((firstname[i].replace("<first_name>","")).trim()).replaceAll("\\n", "");;			  
			   str2= ((str1.replace("</first_name>",""))).trim().replaceAll("\\n", "");;;
			   
			   str3= ((lastname[i].replace("<last_name>","")).trim()).replaceAll("\\n", "");;;
			   str4= ((str3.replace("</last_name>","")).trim()).replaceAll("\\n", "");;;
			  str5=str2+" "+str4;
			   str6= ((str5)).trim().replaceAll("\\n", "");;;
			   
			   sentr[i]= str6.replaceAll("\\\\n", "");
			  
			  }
		  
		  
		  
		  } catch (Exception e) {
		  e.printStackTrace();
		  }
		
		
		return senator_of_state;
		
		
	}
	private String getSenator(String ofstate){
		String senator_of_state="";
	
		  for (int i=0;i<100;i++){	
			 
			  if ((state[i]).contains(ofstate))
			   senator_of_state +=sentr[i]+"\n";
			  
		  }
		return   senator_of_state;
	}
	
	public  void governorData(){
		 String         line;int cnt=0;
		 statenames=new String[50];
		 String key="";String value="";String val="";
		 HashMap<String, String> statesncaps = new HashMap<String,  String>(); 
		 HashMap<String, String> statenabbrevs = new HashMap<String,  String>(); 
		 try{InputStream is1 = this.getResources().openRawResource(R.raw.statencaps);
			InputStreamReader isr1 = new InputStreamReader(is1);
			BufferedReader br = new BufferedReader(isr1);
		//, Charset.forName("UTF-8")));
			
				while ((line = br.readLine()) != null) {
					
					if(cnt %3==0){val=line;
						key=line;
					}
					if(cnt % 3==1)
					{
					statenabbrevs.put(line,key);
					
					}
					if(cnt%3==2){
						value=line;
					statesncaps.put(key,value);
					
					}
			    cnt++;
			}
			}catch (Exception e) {
				  e.printStackTrace();
			  }
		 String[]states=new String[50];
		 String[]mystates=new String[50];
		 Iterator<String> it = statenabbrevs.keySet().iterator();
		 int cntr=-1;
	     while (it.hasNext()) {cntr++;
	        String s = (String) it.next();
	        statenames[cntr]=s;
	        //System.out.println(statenabbrevs.get(s) + "\t" + s+statenames[cntr]+"*********************");
	        states[cntr]=statenabbrevs.get(s);
	     }
	     for(int i=0;i<50;i++)
	    	 System.out.println(states[i].toString());
	Arrays.sort(states);Arrays.sort(statenames);
	 	int getcount=0,statecounter=-1;	 
	 currentgovernors=new HashMap<String, String>();
	  try {
	  URL governors=  new URL("http://en.wikipedia.org/wiki/List_of_current_United_States_governors");
	
	  
	  
	  BufferedReader in = new BufferedReader(
		        new InputStreamReader(governors.openStream()));

	  String inputLine,currstate;
	 
	statecounter++;currstate=states[statecounter];
	 cnt=0;int p=0;int q=0;int counter=0;
     while ((inputLine = in.readLine()) != null )
     		{if(counter>=50)break;
     	
     	if(inputLine.contains(",") && ! inputLine.contains("20") && !inputLine.contains("img") )	{
     		if (cnt>210){//System.out.println(cnt+"\n"+inputLine);
     		currstate=statenames[counter];
     		 p=inputLine.indexOf("\">");
     		 q=inputLine.indexOf("</"); 
     		 if(p<0 ||q<0 ||q-p<0)continue;
     		// System.out.println(p+" "+q);
     		String currgov=inputLine.substring(p+2, q);
     		currentgovernors.put(currstate, currgov);
     		//Log.d("currstate and currgov",currstate+"\n"+currgov);counter++;
     		
     	}
     	}
      	cnt++;
     }
		        		
		        	
		   
		        
	  } 
	   catch (Exception e) {
	  e.printStackTrace();
	  }
	 
		 }
	
	private 
	String getGovernor(String ofstate){
		String governor_of_state="";
		
		Iterator<String> it = currentgovernors.keySet().iterator();
		 
	     while (it.hasNext()) {
	        String s = (String) it.next();
	      if (s.equals(ofstate))
	        
	        governor_of_state=currentgovernors.get(s).toString();
	     }
		
		
		//Log.d("governor_of_state:???????? ",governor_of_state+" of "+ofstate);
		
		return governor_of_state;
	}
	
	
	private void read_Rawdata() {

		InputStream is1 = this.getResources().openRawResource(R.raw.allquestions);
		InputStreamReader isr1 = new InputStreamReader(is1);
		BufferedReader br1 = new BufferedReader(isr1);
		InputStream is2 = this.getResources().openRawResource(R.raw.allanswers);
		InputStreamReader isr2 = new InputStreamReader(is2);
		BufferedReader br2 = new BufferedReader(isr2);

		InputStream isMC1 = this.getResources().openRawResource(
				R.raw.multiplechoicewrongs);
		InputStreamReader isrMC1 = new InputStreamReader(isMC1);
		BufferedReader brMC1 = new BufferedReader(isrMC1);
		String[] testMC = new String[3];

		try {
			String test;
			int qnnum = 0;
			while (true) {if (qnnum>99)break;
				for (int i = 0; i < 3; i++) {
					testMC[i] = brMC1.readLine();
					//Log.d("testMC:", testMC[i]);
					if (testMC[i] == null)
						break;
				}
				if (testMC[0] == null || testMC[1] == null || testMC[2] == null)
					break;

				nearlygood[qnnum] = testMC[0];
				funny1[qnnum] = testMC[1];
				funny2[qnnum] = testMC[2];

				test = br1.readLine();

				if (test == null)
					break;
				allquestions[qnnum] = test;
				test = br2.readLine();

				if (test == null)
					break;
				allanswers[qnnum] = test;
				qnnum++;
			}
			isr1.close();
			isMC1.close();
			isr2.close();
			isrMC1.close();
			br1.close();
			brMC1.close();
			br2.close();
			is1.close();
			is2.close();
			int end = allquestions.length;
			//Log.d("Length: ",end+"");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}