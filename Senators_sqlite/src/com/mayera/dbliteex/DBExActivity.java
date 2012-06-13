package com.mayera.dbliteex;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;







import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class DBExActivity extends ListActivity {
    /** Called when the activity is first created. */
	 static  String[] questions= new String[100];; 
     static  String[] answers=  new String[100];
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        DatabaseHandler db = new DatabaseHandler(this.getBaseContext());
        
 
        /**
         * CRUD Operations
        
        questions[0]="What is the supreme law of the land∫ √ ∫x^2 ∏≤∑ ∂y/∂x?";
		
		 
		questions[1]="What does the Constitution do?";

		questions[2]="The idea of self-government is in the first three words of the Constitution. What are these words?";
			 
		questions[3]="What is an amendment?";

		questions[4]="What do we call the first ten amendments to the Constitution?";
		 
		questions[5]="What is one right or freedom from the First Amendment?*";
		                          	 
		questions[6]="How many amendments does the Constitution have?";
					   
		questions[7]="What did the Declaration of Independence do?";

		questions[8]="What are two rights in the Declaration of Independence?";

		questions[9]="What is freedom of religion?";
			 
		questions[10]="What is the economic system in the United States?*";

		questions[11]="What is the “rule of law”?";
		 
		questions[12]="Name one branch or part of the government.*";
		                          	 
		questions[13]="What stops one branch of government from becoming too powerful?";                            		   
		               
		questions[14]="Who is in charge of the executive branch?";

		questions[15]="Who makes federal laws?";

		questions[16]="What are the two parts of the U.S. Congress?*";
			 
		questions[17]="How many U.S. Senators are there?";

		questions[18]="We elect a U.S. Senator for how many years?";
		 
		questions[19]="Who is one of your state's U.S. Senators now?*";
		                          	 
		questions[20]="The House of Representatives has how many voting members?";
					   
		questions[21]="We elect a U.S. Representative for how many years?";

		questions[22]="Name your U.S. Representative";

		questions[23]="Who does a U.S. Senator represent?";
			 
		questions[24]="Why do some states have more Representatives than other states?";

		questions[25]="We elect a President for how many years?";
		 
		questions[26]="In what month do we vote for President?*";
		                          	 
		questions[27]="What is the name of the President of the United States now?*"; 

		questions[28]="What is the name of the Vice President of the United States now?";

		questions[29]="If the President can no longer serve, who becomes President?";

		questions[30]="If both the President and the Vice President can no longer serve, who becomes President?";
			 
		questions[31]="Who is the Commander in Chief of the military?";

		questions[32]="Who signs bills to become laws?";
		 
		questions[33]="Who vetoes bills?";
		                          	 
		questions[34]="What does the President’s Cabinet do?";
					   
		questions[35]="What are two Cabinet-level positions?";

		questions[36]="What does the judicial branch do?";

		questions[37]="What is the highest court in the United States?";
			 
		questions[38]="How many justices are on the Supreme Court?";

		questions[39]="Who is the Chief Justice of the United States now?";
		 
		questions[40]="Under our Constitution, some powers belong to the federal government. What is one power of the federal government?";
		                          	 
		questions[41]="Under our Constitution, some powers belong to the states. What is one power of the states?";                            		   
		               
		questions[42]="Who is the Governor of New Jersey now?";

		questions[43]="What is the capital of New Jersey?*";

		questions[44]="What are the two major political parties in the United States?*";
			 
		questions[45]="What is the political party of the President now?";

		questions[46]="What is the name of the Speaker of the House of Representatives now?";
		 
		questions[47]="There are four amendments to the Constitution about who can vote. Describe one of them.";
		                          	 
		questions[48]="What is one responsibility that is only for United States citizens?*";
					   
		questions[49]="Name one right only for United States citizens.";

		questions[50]="What are two rights of everyone living in the United States?";

		questions[51]="What do we show loyalty to when we say the Pledge of Allegiance?";
			 
		questions[52]="What is one promise you make when you become a United States citizen?";

		questions[53]="How old do citizens have to be to vote for President?*";
		 
		questions[54]="What are two ways that Americans can participate in their democracy?";
		                          	 
		questions[55]="When is the last day you can send in federal income tax forms?*"; 

		questions[56]="When must all men register for the Selective Service?";

		questions[57]="What is one reason colonists came to America?";

		questions[58]="Who lived in America before the Europeans arrived?";
			 
		questions[59]="What group of people was taken to America and sold as slaves?";

		questions[60]="Why did the colonists fight the British?";
		 
		questions[61]="Who wrote the Declaration of Independence?";
		                          	 
		questions[62]="When was the Declaration of Independence adopted?";
					   
		questions[63]="There were 13 original states. Name three.";

		questions[64]="What happened at the Constitutional Convention?";

		questions[65]="When was the Constitution written?";
			 
		questions[66]="The Federalist Papers supported the passage of the U.S. Constitution. Name one of the writers.";

		questions[67]="What is one thing Benjamin Franklin is famous for?";
		 
		questions[68]="Who is the “Father of Our Country”?";
		                          	 
		questions[69]="Who was the first President?*";                            		   
		               
		questions[70]="What territory did the United States buy from France in 1803?";

		questions[71]="Name one war fought by the United States in the 1800s.";

		questions[72]="Name the U.S. war between the North and the South.";
			 
		questions[73]="Name one problem that led to the Civil War.";

		questions[74]="What was one important thing that Abraham Lincoln did?*";
		 
		questions[75]="What did the Emancipation Proclamation do?";
		                          	 
		questions[76]="What did Susan B. Anthony do?";
					   
		questions[77]="Name one war fought by the United States in the 1900s.*";

		questions[78]="Who was President during World War I?";

		questions[79]="Who was President during the Great Depression and World War II?";
			 
		questions[80]="Who did the United States fight in World War II?";

		questions[81]="Before he was President, Eisenhower was a general. What war was he in?";
		 
		questions[82]="During the Cold War, what was the main concern of the United States?";
		                          	 
		questions[83]="What movement tried to end racial discrimination?"; 

		questions[84]="What did Martin Luther King, Jr. do?*";

		questions[85]="What major event happened on September 11, 2001, in the United States?";

		questions[86]="Name one American Indian tribe in the United States.";
			 
		questions[87]="Name one of the two longest rivers in the United States.";

		questions[88]="What ocean is on the West Coast of the United States?";
		 
		questions[89]="What ocean is on the East Coast of the United States?";
		                          	 
		questions[90]="Name one U.S. territory.";
					   
		questions[91]="Name one state that borders Canada.";

		questions[92]="Name one state that borders Mexico.";

		questions[93]="What is the capital of the United States?*";
			 
		questions[94]="Where is the Statue of Liberty?*";

		questions[95]="Why does the flag have 13 stripes?";
		 
		questions[96]="Why does the flag have 50 stars?*?";
		                          	 
		questions[97]="What is the name of the national anthem?";                            		   
		               
		questions[98]="When do we celebrate Independence Day?*";

		questions[99]="Name two national U.S. holidays.";
		

		answers[0] = "the Constitution";
			 
		answers[1] = "sets up the government, defines the government, protects basic rights of Americans";

		answers[2] = "We the People";
			 
		answers[3] = "a change (to the Constitution), an addition (to the Constitution)";

		answers[4] = "the Bill of Rights";
			 
		answers[5] = "speech, religion, assembly, press, petition the government";

		answers[6] = "twenty-seven (27)";

		answers[7] = "announced our independence (from Great Britain), declared our independence (from Great Britain), said that the United States is free (from Great Britain)";

		answers[8] = "life, liberty, pursuit of happiness";

		answers[9] = "You can practice any religion, or not practice a religion.";

		answers[10] = "capitalist economy, market economy";

		answers[11] = "Everyone must follow the law, Leaders must obey the law, Government must obey the law, No one is above the law.";

		answers[12] = "Congress, legislative, President, executive, the courts, judicial";

		answers[13] = "checks and balances, separation of powers";

		answers[14] = "the President";

		answers[15] = "Congress, Senate and House (of Representatives), (U.S. or national) legislature";

		answers[16] = "the Senate and House (of Representatives)";

		answers[17] = "one hundred (100)";

		answers[18] = "six (6)";

		answers[19] = "";

		answers[20] = "four hundred thirty-five (435)";

		answers[21] = "two (2)";

		answers[22] = "";
			 
		answers[23] = "all people of the state";

		answers[24] = "(because of) the state’s population, (because) they have more people, (because) some states have more people";

		answers[25] = "four (4)";

		answers[26] = "November";

		answers[27] = "Barack Obama, Obama";

		answers[28] = "Joseph R. Biden, Jr. Joe Biden, Biden";

		answers[29] = "the Vice President";

		answers[30] = "the Speaker of the House";

		answers[31] = "the President";

		answers[32] = "the President";

		answers[33] = "the President";

		answers[34] = "advises the President";

		answers[35] = "Secretary of Agriculture, Secretary of Commerce, Secretary of Defense, Secretary of Education, Secretary of Energy, Secretary of Health and Human Services, Secretary of Homeland Security, Secretary of Housing and Urban Development, Secretary of the Interior, Secretary of Labor, Secretary of State, Secretary of Transportation, Secretary of the Treasury, Secretary of Veterans Affairs, Attorney General, Vice President";

		answers[36] = "reviews laws, explains laws, resolves disputes (disagreements), decides if a law goes against the Constitution";

		answers[37] = "the Supreme Court";

		answers[38] = "nine (9)";

		answers[39] = "John Roberts (John G. Roberts, Jr.)";

		answers[40] = "to print money, to declare war, to create an army, to make treaties";
			 
		answers[41] = "provide schooling and education, provide protection (police), provide safety (fire departments), give a driver’s license, approve zoning and land use";

		answers[42] = "Chris Christie, Christie";

		answers[43] = "Trenton";

		answers[44] = "Democratic and Republican";

		answers[45] = "Democratic (Party)";

		answers[46] = "(John) Boehner";

		answers[47] = "Citizens eighteen (18) and older (can vote), You don’t have to pay (a poll tax) to vote. Any citizen can vote. (Women and men can vote.), A male citizen of any race (can vote).";

		answers[48] = "serve on a jury, vote in a federal election";

		answers[49] = "vote in a federal election, run for federal office";

		answers[50] = "freedom of expression, freedom of speech, freedom of assembly, freedom to petition the government, freedom of worship, the right to bear arms";

		answers[51] = "the United States, the flag";

		answers[52] = "give up loyalty to other countries, defend the Constitution and laws of the United States, obey the laws of the United States, serve in the U.S. military (if needed), serve (do important work for) the nation (if needed), be loyal to the United States";

		answers[53] = "eighteen (18) and older";

		answers[54] = "vote, join a political party, help with a campaign, join a civic group, join a community group, give an elected official your opinion on an issue, call Senators and Representatives, publicly support or oppose an issue or policy, run for office, write to a newspaper";

		answers[55] = "April 15";

		answers[56] = "at age eighteen (18), between eighteen (18) and twenty-six (26)";

		answers[57] = "freedom, political liberty, religious freedom, economic opportunity, practice their religion, escape persecution";

		answers[58] = "American Indians, Native Americans";
			 
		answers[59] = "Africans, people from Africa";

		answers[60] = "because of high taxes (taxation without representation), because the British army stayed in their houses, boarding, quartering), because they didn’t have self-government";

		answers[61] = "(Thomas) Jefferson";

		answers[62] = "July 4, 1776";

		answers[63] = "New Hampshire, Delaware, Massachusetts, Maryland, Rhode Island, Virginia, Connecticut, North Carolina, New York, South Carolina, New Jersey, Georgia, Pennsylvania";

		answers[64] = "The Constitution was written, The Founding Fathers wrote the Constitution.";

		answers[65] = "1787";

		answers[66] = "(James) Madison, (Alexander) Hamilton, (John) Jay, Publius";

		answers[67] = "U.S. diplomat, oldest member of the Constitutional Convention, first Postmaster General of the United States, writer of “Poor Richard’s Almanac”, started the first free libraries";

		answers[68] = "(George) Washington";

		answers[69] = "(George) Washington";

		answers[70] = "the Louisiana Territory, Louisiana";

		answers[71] = "War of 1812, Mexican-American War, Civil War, Spanish-American War";

		answers[72] = "the Civil War, the War between the States";

		answers[73] = "slavery, economic reasons, states’ rights";

		answers[74] = "freed the slaves (Emancipation Proclamation), saved (or preserved) the Union, led the United States during the Civil War";

		answers[75] = "freed the slaves, freed slaves in the Confederacy, freed slaves in the Confederate states, freed slaves in most Southern states";

		answers[76] = "fought for women’s rights, fought for civil rights";
			 
		answers[77] = "World War I, World War II, Korean War, Vietnam War, (Persian) Gulf War";

		answers[78] = "(Woodrow) Wilson";

		answers[79] = "(Franklin) Roosevelt";

		answers[80] = "Japan, Germany, and Italy";

		answers[81] = "World War II";

		answers[82] = "Communism";

		answers[83] = "civil rights (movement)";

		answers[84] = "fought for civil rights, worked for equality for all Americans";

		answers[85] = "Terrorists attacked the United States.";

		answers[86] = "Cherokee, Cheyenne, Navajo, Arawak, Sioux, Shawnee, Chippewa, Mohegan, Choctaw, Huron, Pueblo, Oneida, Apache, Lakota, Iroquois, Crow, Creek, Teton, Blackfeet, Hopi, Seminole, Inuit";

		answers[87] = "Missouri (River), Mississippi (River)";

		answers[88] = "Pacific (Ocean)";

		answers[89] = "Atlantic (Ocean)";

		answers[90] = "Puerto Rico, U.S. Virgin Islands, American Samoa, Northern Mariana Islands, Guam";

		answers[91] = "Maine, Minnesota, New Hampshire, North Dakota, Vermont, Montana, New York, Idaho, Pennsylvania, Washington, Ohio, Alaska, Michigan";

		answers[92] = "California, Arizona, New Mexico, Texas";

		answers[93] = "Washington, D.C.";

		answers[94] = "New York (Harbor), Liberty Island, [Also acceptable are New Jersey, near New York City, and on the Hudson (River).]";

		answers[95] = "because there were 13 original colonies, because the stripes represent the original colonies";

		answers[96] = "because there is one star for each state, because each star represents a state, because there are 50 states";

		answers[97] = "The Star-Spangled Banner";

		answers[98] = "July 4";

		answers[99] = "New Year’s Day, Martin Luther King, Jr. Day, Presidents’ Day, Memorial Day, Independence Day, Labor Day, Columbus Day, Veterans Day, Thanksgiving, Christmas";
       * */
		/*
		  for(int i=0;i<100;i++)
		{ 
        db.addQAPair(new QAPair(questions[i],answers[i]));}
       
        */
		//grabs info about state and senators from .gov URL and Stores data in a database. This table must be accessed by the application as a whole and 
        //cached data shd provide the required answers programatically
        //Do a similar thing fo house of repr
		try {
			  URL senators=  new URL("http://www.senate.gov/general/contact_information/senators_cfm.xml");
			//  BufferedWriter file = new BufferedWriter(new FileWriter("test.xml"));
			  String[] firstname=new String[100];
			  String[] lastname=new String[100];
			  String[] state=new String[100];
			  BufferedReader in = new BufferedReader(
				        new InputStreamReader(senators.openStream()));
			            int cnt=0;
				        String inputLine,str,str1;
				        while ((inputLine = in.readLine()) != null )
				        { 
				        	if (inputLine.contains("first_name"))firstname[cnt]=inputLine;
				        	if (inputLine.contains("last_name"))lastname[cnt]=inputLine;
				        	if (inputLine.contains("state")){state[cnt]=inputLine;
				        	cnt++;}
				        }
				        
			  for (int i=0;i<100;i++)
				  {str="FirstName: "+firstname[i]+"\n "+"LastName: "+lastname[i];
				  questions[i]=state[i];
				   str1= str.replace("<first_name>","");
				   str= str1.replace("</first_name>","");
				   str1= str.replace("<last_name>","");
				   str= str1.replace("</last_name>","");
				   answers[i]=str;
				  
				  }
			  } catch (Exception e) {
			  e.printStackTrace();
			  }
			
		for(int i=0;i<100;i++)
		{ 
        db.addQAPair(new QAPair(questions[i]+"jh",answers[i]));
        questions[i]="a";
        answers[i]="a";
        }
		
		
		
		List<QAPair> qnans = db.getAllQAPairs(); int cnt=0 ; 
	   

	    QAPair qp=db.getQAPair(7);
	    Log.d("question: ",qp._question+"************************??????????????????????????");
		for (QAPair qa : qnans){if(cnt>=100)break;
			questions[cnt]=qa.getQuestion()+"blah";
			answers[cnt]=qa.getAnswer();
			cnt++;
		}
		db.close();
		setListAdapter(new ArrayAdapter<String>(
	            this,R.layout.list_item,R.id.list_content, questions));
	            ListView lv = getListView();
	            lv.setBackgroundColor(Color.WHITE);
	            lv.setTextFilterEnabled(true);
	            


/*
 *  private void fillData() {
        // Get all of the rows from the database and create the item list
       List<QAPair> qnans = db.getAllQAPairs(); int cnt=0 ; 


for (QAPair qa : qnans){if(cnt>=100)break;
	questions[cnt]=qa.getQuestion();
	answers[cnt]=qa.getAnswer();
	cnt++;
	
}
        int[] to = new int[]{R.id.text1};

        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter notes = 
            new SimpleCursorAdapter(this, R.layout.notes_row, mNotesCursor, from, to);
        setListAdapter(notes);
    }
 * 
 * 
 * 
 */

	  lv.setOnItemClickListener(new OnItemClickListener() {//(TextView) view).getText()
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) { 
	    	
		    	view.setSelected(true);
	    		
	    	
	    	
	    	     Toast toast = Toast.makeText(getApplicationContext(), answers[position],
	    		          Toast.LENGTH_SHORT);
	    	     toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	    	     TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
	    	     v.setTextColor(Color.BLUE);v.setBackgroundColor(Color.YELLOW);
	    	     toast.show();

	    }
	  });
    /*    List<QAPair> qnans = db.getAllQAPairs();       
 
        for (QAPair qa : qnans) {
            String log = "Id: "+qa.getID()+" ,Question: " + qa.getQuestion() + " ,Answer: " + qa.getAnswer();
                // Writing Contacts to log
        Log.d("Name: ", log);*/
    }
    }
    
