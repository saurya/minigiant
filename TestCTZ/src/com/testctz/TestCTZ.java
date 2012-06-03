package com.testctz;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TestCTZ extends Activity {
	EditText edittext;// How to get back to final and still access from new
						// onclick to make ""
	private TextView mGetQuestionString;
	int questionnumber;
	private Button mnext;
	int cnt = 0, score = 0;
	private Button getscores;
	private Button mcancel;
	int cnt1;
	String delimeter="\'";

	private RadioButton radio_ans1;
	private RadioButton radio_ans2;
	private RadioButton radio_ans3;
	private RadioButton radio_ans4;
	private RadioGroup radiobtnGrp;
	int[]  originalQNums=new int[10];
	private Map<String, Integer> map = new HashMap<String, Integer>();
	String  getansString4="";
	String  getansString1="";String  getansString2="";String  getansString3="";
	String getquestionString0 = "";
	String getquestionString = "";
	String multipleChoiceAnswer="";
	 String multipleChoiceAnswers="";
	String setanswerString = "";
	String scoreString = "Your Score Now Is";
	String [] nearlygood=new String[100];
	String [] funny1=new String[100];
	String [] funny2=new String[100];
	String[] qnlist;
	String[] anslist;
	String[] ctznanslist;
	String ctznans;
	String word1, word2, word3;
	String[] ans47 = { "Citizens 18 and older ",
			"Citizens 18 and older can vote",
			"Citizens eighteen and older can vote.",
			"Citizens eighteen and older ", "You don’t have to pay to vote",
			"You don’t have to pay a poll tax to vote", "Any citizen can vote",
			"Women and men can vote", "A male citizen of any race" };
	String[] ans53 = { "eighteen and older", "18 and older" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// InputMethodManager imm = (InputMethodManager) SearchActivity.this
		// .getSystemService(Context.INPUT_METHOD_SERVICE);

		// if (imm != null) {
		// imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		// }
		setDefaultKeyMode(DEFAULT_KEYS_DISABLE);
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
	 originalQNums=bundle.getIntArray("originalQNums");
	 getMultiChoiceData();
		 String  Qs="";
		 	for(int i=0;i<10;i++)
		 			 Qs+= originalQNums[i];
		 		Log.d(" QNums", Qs);
		qnlist = bundle.getStringArray("randomqns");
		anslist = bundle.getStringArray("anstorandomqns");
		
		getquestionString0 = "Question# 1:" +delimeter+qnlist[0];
		super.onCreate(savedInstanceState);
		// this.getWindow().setSoftInputMode(WindowManager.LayoutParams.ADJUST_RESIZE);
		setContentView(com.testctz.R.layout.main1);
		mGetQuestionString = (TextView) findViewById(com.testctz.R.id.getquestionString);
		mGetQuestionString.setBackgroundColor(Color.WHITE);
		mGetQuestionString.setText(getquestionString0);

		mnext = (Button) findViewById(com.testctz.R.id.next);
		mcancel = (Button) findViewById(com.testctz.R.id.cancel);
		getscores = (Button) findViewById(com.testctz.R.id.getscores);
		// android.view.inputmethod.InputMethodManager imm =
		// (android.view.inputmethod.InputMethodManager)
		// getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.hideSoftInputFromWindow(edittext.getApplicationWindowToken(), 0);
	//	final RadioGroup radiobtnGrp = (RadioGroup)findViewById(R.id.MultipleChoiceGroup);
		radio_ans1 = (RadioButton) findViewById(com.testctz.R.id.radio_ans1);
		
		radio_ans2 = (RadioButton) findViewById(com.testctz.R.id.radio_ans2);
		radio_ans3 = (RadioButton) findViewById(com.testctz.R.id.radio_ans3);
		radio_ans4 = (RadioButton) findViewById(com.testctz.R.id.radio_ans4);
		//radio_ans3.setDuplicateParentStateEnabled(true);radio_ans2.setDuplicateParentStateEnabled(true);radio_ans1.setDuplicateParentStateEnabled(true);
		 multipleChoiceAnswers=anslist[0]+delimeter+nearlygood[originalQNums[0]]+delimeter+funny1[originalQNums[0]]+delimeter+funny2[originalQNums[0]];
		setRadioButtonText(multipleChoiceAnswers);
		
		mnext.setOnClickListener(new OnClickListener() {
		
			public void onClick(View v) {
				cnt++;
				
				if (cnt == 10) 			{
					checklast(cnt);
					Toast.makeText(TestCTZ.this,
							"Your score is " + score + "\nRestarting!",
							Toast.LENGTH_LONG).show();
					score = 0;
					cnt = 0;
					mGetQuestionString.setText(getquestionString0);
			setRadioButtonText(multipleChoiceAnswers);
			
				} 
					
					
					
					
					else{
					getquestionString = getnextqn(cnt);
					mGetQuestionString.setText(getquestionString);
					cnt1=originalQNums[cnt];
				 multipleChoiceAnswer=anslist[cnt]+delimeter+nearlygood[cnt1]+delimeter+funny1[cnt1]+delimeter+funny2[cnt1];
				 
		setRadioButtonText(multipleChoiceAnswer);
			
				}}}
			
		);
		mcancel.setOnClickListener(new OnClickListener() {
		
			public void onClick(View v) {
				finish();
			}
		});

		getscores.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				calculateScores();
				Toast.makeText(TestCTZ.this, scoreString, Toast.LENGTH_LONG)
						.show();
				
			}
		});
	

	}
	public void onRadioButtonClicked(View v) {
	    RadioButton rb = (RadioButton) v;
	
	    Toast.makeText(TestCTZ.this, rb.getText(), Toast.LENGTH_SHORT).show();
	}

	
	
	private void setRadioButtonText(String multipleChoiceAnswers){
		 String[] strArrtext =multipleChoiceAnswers.split(delimeter);
		 getansString4=strArrtext[3];
		 getansString3=strArrtext[2];
		 getansString2=strArrtext[1];
		 getansString1=strArrtext[0];
		 radio_ans1.setText( getansString1);
		 radio_ans2.setText( getansString2);
		 radio_ans3.setText( getansString3);
		 radio_ans4.setText( getansString4);
	//	 radiobtnGrp.addView( radio_ans1,0);
		// radiobtnGrp.addView( radio_ans2,1);
		// radiobtnGrp.addView( radio_ans3,2);
		    //for (int i = 0; i < radiobtnGrp.getChildCount(); i++) {
		       // ((RadioButton) radiobtnGrp.getChildAt(i)).setText(strArrtext[i]);
//	}
	}
	
	public void calculateScores() {

		scoreString = "Your score is  " + score;

	}

	// from web
	public void hideKeyBoard(EditText et) {

		InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(et.getWindowToken(),
				InputMethodManager.HIDE_IMPLICIT_ONLY);

	}

	public void checklast(int cnt) {
		//String ctzn_ans = (ctznans.trim()).toLowerCase();
		
		String ans = "";
		Set<String> keys = map.keySet();
		for (String s : keys)
			ans += s + " ";
		//if (map.containsKey(ctzn_ans)) 
		{
			score++;

		}
	}

	public String getnextqn(int cnt) {
        int qnnumber = cnt + 1;
		return "Qusestion# " + qnnumber + qnlist[cnt] + anslist[cnt - 1];
			

	}
	
	public void getMultiChoiceData(){
		for (int i=0;i<50;i++)
		{nearlygood[i]="a";
		funny1[i]="a";
		funny2[i]="a";}
		
		for (int i=50;i<100;i++)
		{nearlygood[i]="b";
		funny1[i]="b";
		funny2[i]="b";}
		
	    nearlygood[0]="President's verdict";
		funny1[0]="Law as decided by governors of states";
		funny2[0]="God";
		nearlygood[1]="protects Americans";
		funny1[1]="passes immigration laws";
		funny2[1]="Facilitates senators to attend international sports events";
		 nearlygood[2]="We the Nation";
		funny1[2]="star spangled banner";
		funny2[2]="(in)God we trust";
		nearlygood[3]="correction to law";
		funny1[3]="correcting society";
		funny2[3]="apologizing for racial slurs";
	     nearlygood[4]="Ten commandments";
		funny1[4]="Bill of women's rights";
		funny2[4]="Foreign Policy";
		nearlygood[5]="Free school education";
		funny1[5]="liberty to suit  medical professionals";
		funny2[5]="right for unemployment allowance";
		 nearlygood[6]="ten";
		funny1[6]="500";
		funny2[6]="50 for 50 states";
		nearlygood[7]="announced freedom from slavery";
		funny1[7]="freed Japanese from camps";
		funny2[7]="Declared India to be independent";
	nearlygood[8]="right to vote and right to life";
		funny1[8]="right to drink in pursuit of happiness";
		funny2[8]="right to petition the goverment and right to speech";
		nearlygood[9]="freedom to propagate religions other than Christianity";
		funny1[9]="Allows following differnt types of Christianity ";
		funny2[9]="Allows gay marriages";
	nearlygood[10]=" Casinos and sports";
		funny1[10]="marxist economy";
		funny2[10]="consumer is always right- economy";

		nearlygood[11]="Army is permitted to defy Law";
		funny1[11]="Michell Obama dictates rules of Law";
		funny2[11]="President i above any type of law";
		 nearlygood[12]="speaker";
		funny1[12]="Postal";
		funny2[12]="Malls";
		nearlygood[13]="public criticism";
		funny1[13]="elections";
		funny2[13]="War Expenditure";
	nearlygood[14]="Vice-President";
		funny1[14]="Supreme Court";
		funny2[14]="WhiteHouse";
		nearlygood[15]="People";
		funny1[15]="State Representatives";
		funny2[15]="Journalists";
		 nearlygood[16]="House of commons, house of lords";
		funny1[16]="east wing, west wing";
		funny2[16]="Senators and Republicans";
		nearlygood[17]="can vary ";
		funny1[17]="50, one per state";
		funny2[17]="300 , one per million population";
	nearlygood[18]="2";
		funny1[18]="10";
		funny2[18]="no elections. A senator is always nominated";
		nearlygood[19]="Will Smith";
		funny1[19]="Donald Sabastian";
		funny2[19]="Obama";
	nearlygood[20]="6";
		funny1[20]="no elections. A representative is always nominated";
		funny2[20]="4";
	nearlygood[21]="1 year";
		funny1[21]="5";
		funny2[21]="6";
		 nearlygood[22]="need to look up";
		funny1[22]="Schwazinegar";
		funny2[22]="Oprah ";
		nearlygood[23]="Billonaires of his state";
		funny1[23]="Sportsmen of his state";
		funny2[23]="people below the povertyline from his state";
	     nearlygood[24]="because they come from a wealthier state";
		funny1[24]="State average of family size is big";
		funny2[24]="because the state has more agricultural land";
		nearlygood[25]="once any war stops";
		funny1[25]="once a recession begins";
		funny2[25]="once the current president fails to win peoples confidence";
		 nearlygood[26]="this is not a specific month";
		funny1[26]="normally in summer";
		funny2[26]="before snowfall ";
		nearlygood[27]="Michell Obama";
		funny1[27]="Manmohan Singh";
		funny2[27]="Gadafi";
	nearlygood[28]="There is no vicepresident";
		funny1[28]="Hilary Clinton";
		funny2[28]="First Lady is naturally the Vice President";
		nearlygood[29]="The speaker";
		funny1[29]="Alaskan candidate";
		funny2[29]="President of Canada";
	nearlygood[30]=" Vice President";
		funny1[30]="Secretary of State";
		funny2[30]="WallStreet ";
	nearlygood[31]="Vice President";
		funny1[31]="Hilary Clinton";
		funny2[31]="Nancy Polosi";
		nearlygood[32]="Chief Justice";
		funny1[32]="Army Chief";
		funny2[32]="President of Canada";
	nearlygood[33]=" Any senator";
		funny1[33]="NAvy chief";
		funny2[33]="Aviation Chief ";
	nearlygood[34]=" inaugurates functions";
		funny1[34]="attends parliament house on daily basis";
		funny2[34]="debate on issues relevant to elections";
	nearlygood[35]=" Secretary of Treaury, Secretary of Hulu";
		funny1[35]="Secretary of transport and secretary of civil works";
		funny2[35]="Secretary of Aviation and Secretary of sports ";
	nearlygood[36]=" tries to control non-judicial branches";
		funny1[36]="Appoints judges for opencourts on tv shows";
		funny2[36]="brings a stay on unsolvable cases";
	nearlygood[37]=" Peoples court";
		funny1[37]="International Court of Justice";
		funny2[37]="Secretary of Aviation and Secretary of sports ";
	}

	
	
	
}
