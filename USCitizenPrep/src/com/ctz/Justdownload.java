package com.ctz;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
public class Justdownload {
   
	static ArrayList<String> strings=new ArrayList<String>();
	String[]statenames=new String[50];
  Justdownload(String[]statenames) {
    	   statenames=statenames;
    try{	
    	
    	URL[]urlarray=new URL[2];
    	 urlarray[0]=  new URL("http://www.senate.gov/general/contact_information/senators_cfm.xml");
    	 urlarray[1]=  new URL("http://en.wikipedia.org/wiki/List_of_current_United_States_governors");
    	DownloadFile dfl=new DownloadFile();
    	dfl.execute(urlarray);
    	 for(String str:strings) {
    		  
    	Log.d("STRING  :",str);}}
    	 catch (Exception e) {
        
    		 Log.d("STRING  :","nothingggggggggggggggggggggggggggggggggggggggggggggggggggg");
    	 }	 
       
    }
    
final class DownloadFile extends AsyncTask<URL, Integer, String> {
	HashMap<String, String> currentgovernors;
	   String[] firstname=new String[100];
	  String[] lastname=new String[100];
	  String inputLine,currstate;
      InputStream input;
      BufferedReader br ;
      OutputStream output; 
      Writer out;
	  String[] state=new String[100];
        @Override
        protected String doInBackground(URL... sUrl) {
        	
        	 int count=sUrl.length;
        	 int cnt=0;int p=0;int q=0;int counter=0;
        for(int i=0;i<count;i++)
        {URL url=sUrl[i];	
        { try {
            	
                URLConnection connection = url.openConnection();
                connection.connect();
                // this will be useful so that you can show a typical 0-100% progress bar
              int fileLength = connection.getContentLength();

                // download the file
             
                //different criterion for selecting senator and governor
              //now just experimenting
              input = new BufferedInputStream(url.openStream());
              br = new BufferedReader(new InputStreamReader(input, "UTF-8")); 
              String filename="";
              int statecounter=-1;	 String[]states=new String[50];currentgovernors=new HashMap<String, String>(); 
              if (i==1){filename="/sdcard/govdataactual.txt";
              output = new FileOutputStream(filename);   
              out = new OutputStreamWriter(output, "UTF-8");
              
         	 
          	statecounter++;currstate=states[statecounter];
          	
               while ((inputLine = br.readLine()) != null )
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
              if (i==0){filename="/sdcard/senatordataactual.txt";
              output = new FileOutputStream(filename);   
              out = new OutputStreamWriter(output, "UTF-8");
    					 cnt=0;
    						        String str,str1,str2,str3,str4,str5,str6;
    						        while ((inputLine = br.readLine()) != null )
    						        { 
    						        	if (inputLine.contains("first_name"))firstname[cnt]=inputLine;
    						        	if (inputLine.contains("last_name"))lastname[cnt]=inputLine;
    						        	if (inputLine.contains("state")){state[cnt]=inputLine;
    						        	out.write(state[cnt]+firstname[cnt]+","+lastname[cnt]+","+"\n");
    						        	cnt++;}
    						        }
    						       
    					            	output.close();
    					              input.close();
    							        
          
                
            }
              
        }catch (Exception e) {Log.d("STRING  :","nothingggggggggggggggggggggggggggggggggggggggggggggggggggg");
            }}}
           
            
            return null;
        }    
    
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
          //  mProgressDialog.setProgress(progress[0]);
        }
        
    
        protected void onPostExecute(Integer... progress) {
        // for (int i=0;i<100;i++)
        	// Log.d("haha data",firstname[i]+","+lastname[i]+","+state[i]);
        //    mProgressDialog.setProgress(progress[0]);
           // mProgressDialog.show();
        }
        
        
        
}}


    



    
    
    
    
    
    
    
    
    
    
