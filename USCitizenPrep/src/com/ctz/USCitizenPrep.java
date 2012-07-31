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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.CheckBox;
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
	String selected;static String currentstate="CA";//just to keep it going
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
	static String statename="";
	private RadioButton radio_prepare_for_interview;
	private RadioButton radio_test_yourself;
private CheckBox radio_senior_prepare_for_interview;
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
		File file = new File("/sdcard/govdataactual.txt");boolean deleted;
		if(file.exists())
		deleted = file.delete();
		file = new File("/sdcard/senatordataactual.txt");
		if(file.exists())
		 deleted = file.delete();
		//getGovernor();
		setContentView(com.ctz.R.layout.main);
		 GestureOverlayView gOverlay = (GestureOverlayView) findViewById(R.id.gestures);
	        gOverlay.addOnGesturePerformedListener(USCitizenPrep.this); 	
		gLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gLib.load()) {
            finish();
       }
        
       
		questions_list = new ArrayList<String>();
		answers_list = new ArrayList<String>();
	final Spinner	 spinner = (Spinner) findViewById(R.id.state_spinner);
		//findViewById(R.id.state_spinner).setBackgroundColor(R.color.yyellow);
		// Create an ArrayAdapter using the string array and a default spinner layout
		//ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		      //  R.array.states_array, android.R.layout.simple_spinner_item);
		//spinner.setBackgroundColor(color.holo_blue_bright);
	ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
						this,
						android.R.layout.simple_spinner_item, 
						statenames);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		//DialogInterface dialog;
	//	spinner.setBackgroundColor(color.background_light);
		spinner.setAdapter(adapter);spinner.setFocusable(true);
		//spinner.setVisibility(1);
		 radio_senior_prepare_for_interview=(CheckBox) findViewById(com.ctz.R.id.radio_senior_prepare_for_interview);
		radio_prepare_for_interview = (RadioButton) findViewById(com.ctz.R.id.radio_prepare_for_interview);
		radio_test_yourself = (RadioButton) findViewById(com.ctz.R.id.radio_test_yourself);
		//radio_senior_prepare_for_interview = (RadioButton) findViewById(com.ctz.R.id.radio_senior_prepare_for_interview);

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
				
				Bundle bundle = new Bundle();
				Bundle qnbundle = new Bundle();
				Intent myIntent2 = new Intent();
	
				if (v == go_Button) {
					currentstate=spinner.getSelectedItem().toString();	
					new Justdownload();
					if (radio_test_yourself.isChecked() == true  &&  radio_senior_prepare_for_interview.isChecked()==false) {
						selected_type = new String("2");						
				        {
							copy("random");
							String[] qns = new String[questions_list.size()];
							String[] ans = new String[answers_list.size()];
							Object[] qnobarr = questions_list.toArray();
							Object[] ansobarr = answers_list.toArray();
							for (int i = 0; i <questions_list.size(); i++) {
								qns[i] = (String) qnobarr[i];// (String[]) questions_list.toArray()
								ans[i] = (String) ansobarr[i];
							}
							
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
							
							// bundle up randomly selected question numbers needed for scoring
							qnbundle.putIntArray("originalQNums", randoms);
							qnbundle.putStringArray("randomqns", qns);
												
							qnbundle.putStringArray("anstorandomqns", ans);	
							
						}
					}
					
					
					
					
					if (radio_prepare_for_interview.isChecked() == true &&  radio_senior_prepare_for_interview.isChecked()==false) {
						selected_type = new String("1");
						Toast.makeText(USCitizenPrep.this, "No Seniorji",
								Toast.LENGTH_SHORT).show();
						copy("total");
					}
                         
				
					if (radio_senior_prepare_for_interview.isChecked() == true && (radio_prepare_for_interview.isChecked() == true ||radio_test_yourself.isChecked() == true)) {
						Toast.makeText(USCitizenPrep.this, "SeniorjiOK",Toast.LENGTH_SHORT).show();
						selected_type = new String("1");
						copy("senior");
					}
						}
			
				
					if(selected_type==null){
					                      return;
					}
				
				if(radio_prepare_for_interview.isChecked() == true && radio_senior_prepare_for_interview.isChecked() == false)
					{
				StringBuilder sb = new StringBuilder().append(selected_type);
				bundle.putString(user_selection, sb.toString());
				bundle.putStringArray("allquestions",allquestions);
			    bundle.putStringArray("allanswers",allanswers );
				myIntent2.putExtras(bundle);
				
				myIntent2.setClassName("com.ctz",
				"com.ctz.PrepNoTimer");
				startActivity(myIntent2);
					}
                if(selected_type.equals("2") && radio_senior_prepare_for_interview.isChecked() == false)
				{	
				myIntent2.setClassName("com.ctz","com.ctz.Gather_UserChoices");
                myIntent2.putExtras(qnbundle);
				startActivity(myIntent2);
				}
				
                if((selected_type.equals("1")||selected_type.equals("2")) && radio_senior_prepare_for_interview.isChecked() == true)
				{
				
                qnbundle.clear();
				copy("senior");
				String[] qns = new String[questions_list.size()];
				String[] ans = new String[answers_list.size()];
				Object[] qnobarr = questions_list.toArray();
				Object[] ansobarr = answers_list.toArray();
				for (int i = 0; i <questions_list.size(); i++) {
					qns[i] = (String) qnobarr[i];// (String[]) questions_list.toArray()
					ans[i] = (String) ansobarr[i];
				}
					qnbundle.putStringArray("allquestions",qns);
					qnbundle.putStringArray("allanswers",ans);
				    myIntent2.setClassName("com.ctz","com.ctz.PrepNoTimer");
				    myIntent2.putExtras(qnbundle);
                    startActivity(myIntent2);
				}
			
			}
			}
		    );
}
	public void filldata(){//close ???
		String senator = "";
		try{
		FileInputStream fIn = new FileInputStream("/sdcard/senatordataactual.txt");
		BufferedReader myReader = new BufferedReader(
				new InputStreamReader(fIn));
		
		String onlyone = "";
		while ((onlyone = myReader.readLine()) != null) {
			senator += onlyone + "\n";
		}
		}catch(Exception e){
			
		}
		
		allanswers[19]=senator;
		
		
		String governor = "";
		try{
		FileInputStream fIn = new FileInputStream("/sdcard/govdataactual.txt");
		BufferedReader myReader = new BufferedReader(
				new InputStreamReader(fIn));
		
		String onlyone = "";
		while ((onlyone = myReader.readLine()) != null) {
			governor += onlyone + "\n";
		}
		}catch(Exception e){
			
		}
		allanswers[42]=governor;
		
	}
	    
	@SuppressLint({ "NewApi", "NewApi", "NewApi" })
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
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
		answers_list.clear();
		questions_list.clear();
		if (s.equals("total")) {
			
			start = 0;
			end = allquestions.length;
			for (int i = start; i < end; i++) {
				
				
				questions_list.add(allquestions[i]);
				answers_list.add(allanswers[i]);
		}
			
		}
	  else if (s.equals("random")) {
            Random rands = new Random();
			String randomstr = "aaa,";
			int nums = 10;			
		Set collect=new HashSet<Integer>();

			int count=0,k=0;int justcount=0;
			for (int countslice=1;countslice<=10;countslice++)
			{rands = new Random();
				randomstr="aaa";count=0;
				while ( count< 10) {
					if (collect.size()==100)break;
					k = rands.nextInt(nums);
				if (!randomstr.contains(k + "")) {
					randomstr += (k);
					randoms[justcount] = 10*(countslice-1)+k;
					count++;justcount++;
					collect.add(new Integer(10*(countslice-1)+k));
				
				}
			} 
				if (collect.size()==100)break;	
		}
		
			Iterator it=collect.iterator();
			while(it.hasNext())
		{k=((Integer)it.next());
				questions_list.add("" + allquestions[randoms[k]]);
			answers_list.add("" + allanswers[randoms[k]]);}
			
			
			
			
			}

		else if (s.equals("senior")) {
			
			start = 0;

			end = allquestions.length;
			Log.d("Length: ",end+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			for (int i = start; i < end; i++)
				if (allquestions[i].contains("?*")) {
					questions_list.add(allquestions[i]);
					answers_list.add("" + allanswers[i]);
				}
			
		}

	}
	
	
	private String getSenators(){
		try {
				
			
			
		  URL senators=  new URL("http://www.senate.gov/general/contact_information/senators_cfm.xml");
		Log.d("printinfo","gotit ***************??????????????????????????>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		new DownloadFilesTask().execute(senators);
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
		String senator_of_state="old";
		
		  for (int i=0;i<100;i++){	
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
	
	 	int statecounter=-1;	 
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
				
				//StringEntity entity = new StringEntity(jsonTaakkaart.toString(), "UTF-8");
				allquestions[qnnum] = test.toString();
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
	private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
	     protected Long doInBackground(URL... urls) {
	         int count = urls.length;
	         long totalSize = 0;
	         for (int i = 0; i < count; i++) {
	           
	             publishProgress((int) ((i / (float) count) * 100));
	             // Escape early if cancel() is called
	             if (isCancelled()) break;
	         }
	         return totalSize;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	    	// setProgressPercent(progress[0]);
	     }

	    
	 }
}