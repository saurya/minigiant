package com.example.uscitizen;

// http://mobiforge.com/designing/story/understanding-user-interface-android-part-2-views
//http://www.androidpeople.com/android-radiobutton-example/   helpful
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.uscitizen.R.color;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class US_CitizenActivity extends ListActivity {
	protected static final int REFRESH = 0;
	private Timer timer;
	private TimerTask refresher;
	static Toast toast;
	private final Handler toastHandler = new Handler();
	 static int     duration = 2000;
	
	int[]randoms=new int[10];
	
	String selected;
	String param1;
	int testnum = 0;// number of test questions
	ArrayList<String> qnlist, anslist;
	String[] tenquestions = new String[testnum];
static int positionprev=0;
	String[] allquestions =new String[100];;

/*
		{	"What is the supreme law of the land?",
			"What does the Constitution do?",
			"The idea of self-government is in the first three words of the "
					+ "Constitution.What are these words?",
			" What is an amendment?",
			"What do we call the first ten amendments to the Constitution? ",
			"  What is one right or freedom from the First Amendment? ",
			" How many amendments does the Constitution have?*",
			" What did the Declaration of Independence do ",

			" What are two rights in the Declaration of Independence?",
			"What is freedom of religion? ",
			"What is the economic system in the United States? ",
			"What is the “rule of law”?*",
			"Name one branch or part of the government. ",
			"What stops one branch of government from becoming too powerful?",
			"Who is in charge of the executive branch?",
			"Who makes federal laws?",
			"What are the two parts of the U.S. Congress? ",
			"How many U.S. Senators are there?*",
			"We elect a U.S. Senator for how many years?",
			"Who is one of your state’s U.S. Senators now? ",

			"The House of Representatives has how many voting members?",
			"We elect a U.S. Representative for how many years?",
			"Name your U.S. Representative.",
			"Who does a U.S. Senator represent",
			"Why do some states have more Representatives than other states?*",
			"We elect a President for how many years",
			"In what month do we vote for President?",
			"What is the name of the President of the United States now?",
			"What is the name of the Vice President of the United States now?",
			"If the President can no longer serve, who becomes President?",
			"If both the President and the Vice President can no longer serve, who becomes President?",
			"Who is the Commander in Chief of the military",
			"Who signs bills to become laws?",
			"Who vetoes bills?",
			"What does the President’s Cabinet do",
			"What are two Cabinet-level positions?",
			" What does the judicial branch do",
			"What is the highest court in the United States?",
			"How many justices are on the Supreme Court?",
			"Who is the Chief Justice of the United States now",
			"Under our Constitution, some powers belong to the federal government.  What is one power of the federal government",
			"  Under our Constitution, some powers belong to the states.  What is one power of the states?",

			"Who is the Governor of your state now?",
			"What is the capital of your state? ",
			" What are the two major political parties in the United States?",
			"What is the political party of the President now?",

			"What is the name of the Speaker of the House of Representatives now?",

			"There are four amendments to the Constitution about who can vote.  Describe one of them.",
			"What is one responsibility that is only for United States citizens? ",
			" Name one right only for United States citizens.",

			"What are two rights of everyone living in the United States?",
			"What do we show loyalty to when we say the Pledge of Allegiance?",
			"What is one promise you make when you become a United States citizen?",
			"How old do citizens have to be to vote for President?*",
			"What are two ways that Americans can participate in their democracy?",
			"When is the last day you can send in federal income tax forms?*",
			"When must all men register for the Selective Service?",
			"What is one reason colonists came to America?",
			"Who lived in America before the Europeans arrived?",
			"What group of people was taken to America and sold as slaves? ",
			"Why did the colonists fight the British?",
			"Who wrote the Declaration of Independence?",
			"When was the Declaration of Independence adopted?",
			"There were 13 original states. Name three.",
			"What happened at the Constitutional Convention?",
			"When was the Constitution written?",
			"The Federalist Papers supported the passage of the U.S. Constitution. Name one of the writers.",
			"What is one thing Benjamin Franklin is famous for?",
			"Who is the “Father of Our Country”?",
			"Who was the first President?*",
			"What territory did the United States buy from France in 1803?",
			"Name one war fought by the United States in the 1800s.",
			"Name the U.S. war between the North and the South.",
			"Name one problem that led to the Civil War.",
			"What was one important thing that Abraham Lincoln did?*",
			"What did the Emancipation Proclamation do?",
			"What did Susan B. Anthony do?",
			"Name one war fought by the United States in the 1900s.*",
			"Who was President during World War I?",
			"Who was President during the Great Depression and World War II?",
			"Who did the United States fight in World War II?",
			"Before he was President, Eisenhower was a general. What war was he in?",
			"During the Cold War, what was the main concern of the United States?",
			"What movement tried to end racial discrimination?",
			"What did Martin Luther King, Jr. do?*",
			"What major event happened on September 11, 2001, in the United States?",
			"Name one American Indian tribe in the United States.",
			"Name one of the two longest rivers in the United States.",
			"What ocean is on the West Coast of the United States?",
			"What ocean is on the East Coast of the United States?",
			"Name one U.S. territory.", "Name one state that borders Canada.",
			"Name one state that borders Mexico.",
			"What is the capital of the United States?* ",
			"Where is the Statue of Liberty?*",
			"Why does the flag have 13 stripes?",
			"Why does the flag have 50 stars?*",
			"What is the name of the national anthem? ",
			"When do we celebrate Independence Day?*",
			"Name two national U.S. holidays."

	};*/
	 
	String[] answers =  new String[100];;/*{
			" the Constitution",
			"  sets up the government" + "\n" + " defines the government"
					+ "\n" + " protects basic rights of Americans",
			" We the People",

			" a change (to the Constitution)" + "\n"
					+ "an addition (to the Constitution) ",
			"the Bill of Rights",
			"    speech" + "\n" + "religion" + "\n" + "assembly" + "\n"
					+ "press" + "\n" + "petition the government ",
			"  twenty-seven (27)",
			"  announced our independence (from Great Britain)"
					+ "\n"
					+ "declared our independence (from Great Britain)"
					+ "\n"
					+ "said that the United States is free (from Great Britain ",
			"  life" + "\n" + "liberty" + "\n" +

			"pursuit of happiness",
			" You can practice any religion, or not practice a religion.",
			" capitalist economy" + "\n" + "market economy ",
			"  Everyone must follow the law" + "\n"
					+ " Leaders must obey the law" + "\n"
					+ " Government must obey the law" + "\n"
					+ "No one is above the law.",
			"Congress" + "\n" + "legislative" +

			"\n" + "President" + "\n" + "executive" + "\n" +

			"the courts" + "\n" + "judicial",
			"checks and balances" + "\n" +

			"separation of powers",
			"the President",
			" Congress" + "\n" + "\n"
					+ " Senate and House (of Representatives)" + "\n"
					+ " (U.S. or national) legislature",
			"the Senate and House (of Representatives)",
			"one hundred (100)",
			"six (6)",
			"Answers will vary.  [District of Columbia residents "
					+ "and residents of U.S. territories should answer that D.C. "
					+ "(or the territory where the applicant lives) has no U.S. Senators.",
			" four hundred thirty-five (435)",
			" two (2)",
			"Answers will vary.  [Residents of territories with nonvoting Delegates or Resident Commissioners may "
					+ "provide the name of that Delegate or Commissioner.  Also acceptable is any statement that the territory has "
					+ "no (voting) Representatives in Congress.]",

			"all people of the state",

			"(because of) the state’s population" + "\n"
					+ "(because) they have more people" + "\n"
					+ "(because) some states have more people",

			"four (4)",
			" November",

			" Barack Obama" + "\n" + "Obama",

			"Joseph R. Biden" + "\n" + " Jr.  Joe Biden" + "\n" + "Biden",

			"the Vice President",
			"the Speaker of the House",
			"the President",
			"the President",
			"the President",
			"advises the President",

			"Secretary of Agriculture" + "\n" +

			"Secretary of Commerce" + "\n" + "Secretary of Defense" +

			"\n" + "Secretary of Education" + "\n" + "Secretary of Energy"
					+ "\n" + "Secretary of Health and Human Services" + "\n"
					+ "Secretary of Homeland Security" + "\n"
					+ "Secretary of Housing and Urban Development" + "\n"
					+ "Secretary of the Interior" + "\n" + "Secretary of Labor"
					+ "\n" + "Secretary of State" + "\n"
					+ "Secretary of Transportation" + "\n"
					+ "Secretary of the Treasury" + "\n"
					+ "Secretary of Veterans Affairs" + "\n"
					+ "Attorney General" + "\n" + "Vice President",
			"reviews laws" + "\n" + "explains laws" + "\n"
					+ "resolves disputes(disagreements)" + "\n"
					+ "decides if a law goes against the Constitution",
			"the Supreme Court",
			"nine (9)",
			"John Roberts (John G. Roberts, Jr.)",
			"to print money" + "\n" + "to declare war"

			+ "\n" + "to create an army" +

			"\n" + "to make treaties",

			" provide schooling and education" + "\n"
					+ "provide protection (police)" + "\n"
					+ "provide safety (fire departments)" + "\n"
					+ "give a driver’s license" + "\n"
					+ "approve zoning and land use",
			"Answers will vary.  [District of Columbia residents should answer that D.C. does not have a Governor",

			"Answers will vary.  [District of Columbia residents should answer that"
					+ "\n"
					+ "D.C. is not a state and does not have a capital. "
					+ "\n"
					+ "Residents of U.S. territories should name the capital of the territory.]",
			"Democratic and Republican",
			"Democratic (Party)",
			"(Nancy) Pelosi",
			"Citizens eighteen (18) and older (can vote)." + "\n"
					+ "You don’t have to pay (a poll tax) to vote." + "\n"
					+ "Any citizen can vote.  (Women and men can vote.)" + "\n"
					+ "A male citizen of any race (can vote).",
			"serve on a jury" + "\n" + "vote in a federal election",
			"vote in a federal election" + "\n" + "run for federal office",
			"freedom of expression" + "\n" + "" + "\n" + "freedom of assembly"
					+ "\n" + "freedom to petition the government" + "\n"
					+ "freedom of worship" + "\n" + "the right to bear arms",
			"the United States" + "\n" + "the flag",
			"give up loyalty to other countries" + "\n"
					+ "defend the Constitution and laws of the United States"
					+ "\n" + "obey the laws of the United States" + "\n"
					+ "serve in the U.S. military (if needed)" + "\n"
					+ "serve (do important work for) the nation (if needed)"
					+ "\n" + "be loyal to the United States",

			"eighteen (18) and older",

			"vote" + "\n" + "join a political party" + "\n"
					+ "help with a campaign" + "\n" + "join a civic group"
					+ "\n" + "join a community group" + "\n"
					+ "give an elected official your opinion on an issue"
					+ "\n" + "call Senators and Representatives" + "\n"
					+ "publicly support or oppose an issue or policy" + "\n"
					+ "run for office" + "\n" + "write to a newspaper",
			"April 15",
			"at age eighteen (18)" + "\n"
					+ "between eighteen (18) and twenty-six (26)",
			"freedom" + "\n" + "political liberty" + "\n" + "religious freedom"
					+ "\n" + "economic opportunity" + "\n"
					+ "practice their religion" + "\n" + "escape persecution",
			" American Indians" + "\n" + "Native Americans",
			"Africans" + "\n" + "people from Africa",
			"because of high taxes (taxation without representation) "
					+ "\n"
					+ "because the British army stayed in their houses (boarding, quartering)"
					+ "\n" + "because they didn’t have self-government",
			"(Thomas) Jefferson",
			"July 4, 1776",
			"New Hampshire" + "\n" + "Massachusetts" + "\n" + "Rhode Island"
					+ "\n" + "Connecticut" + "\n" + "New York" + "\n"
					+ "New Jersey" + "\n" + "Pennsylvania" + "\n" + "Delaware"
					+ "\n" + "Maryland" + "\n" + "Virginia" + "\n"
					+ "North Carolina" + "\n" + "South Carolina" + "\n"
					+ "Georgia",
			"The Constitution was written." + "\n"
					+ "The Founding Fathers wrote the Constitution.",
			"1787",

			"(James) Madison." + "\n" + "(Alexander) Hamilton." + "\n"
					+ "(John) Jay." + "\n" + "Publius",
			"U.S. diplomat." + "\n"
					+ "oldest member of the Constitutional Convention." + "\n"
					+ "first Postmaster General of the United States." + "\n"
					+ "writer of “Poor Richard’s Almanac”." + "\n"
					+ "started the first free libraries.",
			"(George) Washington.",
			"(George) Washington.",
			"the Louisiana Territory." + "\n" +

			"Louisiana.",
			" War of 1812." + "\n" + "Mexican-American War." + "\n" +

			" Civil War." + "\n" + "Spanish-American War",

			"the Civil War." + "\n" + " the War between the States",
			"slavery." + "\n" + "economic reasons." + "\n" + "states’ rights",
			"freed the slaves (Emancipation Proclamation)." + "\n"
					+ " saved (or preserved) the Union." + "\n"
					+ "led the United States during the Civil War",

			"freed the slaves." + "\n" + " freed slaves in the Confederacy."
					+ "\n" + " freed slaves in the Confederate states." + "\n"
					+ " freed slaves in most Southern states",
			"fought for women’s rights." + "\n" + " fought for civil rights",
			"World War I." + "\n" + " World War II." + "\n" + "Korean War."
					+ "\n" + " Vietnam War." + "\n" + "(Persian) Gulf War",
			"(Woodrow) Wilson",
			"(Franklin) Roosevelt",
			" Japan, Germany, and Italy",
			" World War II",
			"Communism",
			"civil rights (movement) ",
			"fought for civil rights" + "\n"
					+ "worked for equality for all Americans",

			"Terrorists attacked the United States.",
			"[USCIS Officers will be supplied with a list of federally " + "\n"
					+ "recognized American Indian tribes.]." + "\n"
					+ " Cherokee." + "\n" + "Navajo."

					+ "\n" + "Sioux." + "\n" + " Chippewa."

					+ "\n" + " Choctaw." + "\n" + " Pueblo."

					+ "\n" + "Apache." + "\n" + " Iroquois."

					+ "\n" + "Creek." + "\n" + " Blackfeet."

					+ "\n" + " Cheyenne." + "\n" + " Arawak."

					+ "\n" + "Shawnee." + "\n" + " Mohegan."

					+ "\n" + "Oneida." + "\n" + " Huron."

					+ "\n" + "Lakota." + "\n" + "Crow."

					+ "\n" + " Teton." + "\n" + "Hopi." + "\n" + "Inuit",
			"Missouri (River)." + "\n" + " Mississippi (River)",
			"Pacific (Ocean)",
			"Atlantic (Ocean)",
			"Puerto Rico" + "\n" + " U.S. Virgin Islands  " + "\n"
					+ " American Samoa " + "\n" + " Northern Mariana Islands"
					+ "\n" + " Guam",

			"Maine" + "\n" + " New Hampshire" + "\n" + "Vermont" + "\n"
					+ "New York" + "\n" + "Pennsylvania" + "\n" + "Ohio" + "\n"
					+ "Michigan" + "\n" + "Minnesota" + "\n" + " North Dakota"
					+ "\n" + "Montana" + "\n" + " Idaho" + "\n" + " Washington"
					+ "\n" + " Alaska",
			"California" + "\n" + "Arizona" + "\n" + "New Mexico" + "\n"
					+ "Texas",

			"Washington, D.C.",

			"New York (Harbor) "
					+ "\n"
					+ " Liberty Island"
					+ "\n"
					+ "[Also acceptable are New Jersey, near New York City, and on the Hudson (River).]",
			"because there were 13 original colonies" + "\n"
					+ "because the stripes represent the original colonies",
			"because there is one star for each state" + "\n"
					+ "because each star represents a state" + "\n"
					+ "because there are 50 states",
			"The Star-Spangled Banner",
			"July 4",
			"New Year’s Day" + "\n" + " Martin Luther King, Jr. Day" + "\n"
					+ "Presidents’ Day" + "\n" + "Memorial Day" + "\n"
					+ "Independence Day" + "\n" + "Labor Day" + "\n"
					+ " Columbus Day" + "\n" + "Veterans Day" + "\n"
					+ "Thanksgiving" + "\n" + "Christmas" };
*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		
		checkExternalMedia();
		readRaw();
		Log.d("first",allquestions[99]);
		param1 = bundle.getString(param1);

		qnlist = new ArrayList<String>();
		anslist = new ArrayList<String>();
		if (param1.contains("2")) {
			copy("random");
			Intent myIntent2 = new Intent();
			myIntent2.setClassName("com.testctz", "com.testctz.TestCTZ");

			Bundle qnbundle = new Bundle();
			String[] qns = new String[qnlist.size()];
			String[] ans = new String[anslist.size()];
			Object[] qnobarr = qnlist.toArray();
			Object[] ansobarr = anslist.toArray();
			for (int i = 0; i < 10; i++) {
				qns[i] = (String) qnobarr[i];// (String[]) qnlist.toArray()
				ans[i] = (String) ansobarr[i];
			}
			qnbundle.putIntArray("originalQNums", randoms);
			qnbundle.putStringArray("randomqns", qns);
			qnbundle.putStringArray("anstorandomqns", ans);
			myIntent2.putExtras(qnbundle);

			startActivity(myIntent2);

		}

		else {
			if (param1.contains("1"))
				copy("total");

			// if (param1.contains("3"))
			else if (param1.contains("4"))
				copy("senior");
			setContentView(com.example.uscitizen.R.layout.listview);
			ListView lv=getListView();
			lv.setTextFilterEnabled(true);
			//lv.setVisibility(3);
			setListAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, qnlist));
			
	
		}
	}

	public void copy(String s) {
		int start, end;

		if (s.equals("total")) {
			anslist.clear();
		    qnlist.clear();
			start = 0;
			end = allquestions.length;
			for (int i = start; i < end; i++)
				{qnlist.add(allquestions[i]);
				 anslist.add(answers[i]);
				}
			// return (String[]) qns.toArray();
		}

		else if (s.equals("random")) {
			anslist.clear();
		    qnlist.clear();
			Random rands = new Random();
			String randomstr = "aaa,";
			int nums = allquestions.length;// lengths are differing?

			// int nums = answers.length;
			int k, count = 0;
			//randoms=new int[10];
			while (count < 10) {
				k = rands.nextInt(nums);
				if (!randomstr.contains(k + "")) {
					randomstr += (k);
					randoms[count]=k;

					count++;
					qnlist.add("" + allquestions[k]);
					anslist.add("" + answers[k]);
				}
			} // return (String[]) qns.toArray();
		}

		else if (s.equals("senior")) {
			anslist.clear();
			qnlist.clear();
			start = 0;
			
			end = allquestions.length;
			for (int i = start; i < end; i++)
				if (allquestions[i].contains("?*")) {
					qnlist.add(allquestions[i]);
					 anslist.add("" + answers[i]);
				}
			System.out.println(anslist.size()+"is the length");
		}

	}
	public void showDoubleToast(int numrepeats) {
	    
		toast.show();
	    for (int i = 1; i <= numrepeats; i++) {
	        // show again
	        toastHandler.postDelayed(new Runnable() {

	           
	            public void run() {
	            	toast.show();
	            }
	        }, i * duration);
	    }
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		
parent.setScrollIndicators(v,v);
		parent.setSelection(position);
		
		 toast=Toast.makeText(this,qnlist.get(position)+'\n'+'\n'+anslist.get(position), 2*Toast.LENGTH_LONG);
		
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		 
		
	     TextView v2 = (TextView) toast.getView().findViewById(android.R.id.message); 
	     v2.setBackgroundDrawable(getResources().getDrawable(R.drawable.android_normal));
	     v2.setTextSize(28);
	     
	        
	     showDoubleToast(2);
	    
	}
	 private void checkExternalMedia(){
         boolean mExternalStorageAvailable = false;
         boolean mExternalStorageWriteable = false;
         String state = Environment.getExternalStorageState();
 
         if (Environment.MEDIA_MOUNTED.equals(state)) {
             // Can read and write the media
             mExternalStorageAvailable = mExternalStorageWriteable = true;
         } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
             // Can only read the media
             mExternalStorageAvailable = true;
             mExternalStorageWriteable = false;
         } else {
             // Can't read or write
             mExternalStorageAvailable = mExternalStorageWriteable = false;
         }   
        // tv.append("\n\nExternal Media: readable="
              //       +mExternalStorageAvailable+" writable="+mExternalStorageWriteable);
     }
	
	private void readRaw(){
       
        InputStream is1 = this.getResources().openRawResource(R.raw.qstns);
        InputStreamReader isr1 = new InputStreamReader(is1);
        BufferedReader br1 = new BufferedReader(isr1);   
        InputStream is2 = this.getResources().openRawResource(R.raw.answers);
        InputStreamReader isr2 = new InputStreamReader(is2);
        BufferedReader br2 = new BufferedReader(isr2);   
      
        
        try {
            String test;	int qnnum=0;
            while (true){				
                test = br1.readLine();   
               
                if(test == null) break;
                allquestions[qnnum]=test;
                test = br2.readLine();   
                
                if(test == null) break;
                answers[qnnum]=test;
                qnnum++;
            }
            isr1.close();
            is2.close();
            br1.close();
            br2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
	
	
	
	
	
	
	
	
	
	
	
	
}