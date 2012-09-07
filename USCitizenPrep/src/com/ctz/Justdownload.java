//Write the user's state governor and senators to different files on SDCard

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

import android.os.AsyncTask;

public class Justdownload {
  final class DownloadFile extends AsyncTask<URL, Integer, String> {
    HashMap<String, String> currentgovernors;
    String[] firstname = new String[100];
    String[] lastname = new String[100];
    String inputLine, currstate;
    InputStream input;
    BufferedReader br;
    OutputStream output;
    Writer out;
    String[] state = new String[100];

    @Override
    protected String doInBackground(URL... sUrl) {
      String[] sentr = new String[100];

      int cnt = 0;
      int p = 0;
      int q = 0;
      for (int i = 0; i < 2; i++) {
        URL url = sUrl[i];
        String filename = "";
        int statecounter = -1;
        currentgovernors = new HashMap<String, String>();
        {
          try {

            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(30000);
            connection.connect();

            input = new BufferedInputStream(url.openStream());
            br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            if (i == 1) {
              filename = "/sdcard/govdataactual.txt";
              output = new FileOutputStream(filename);
              out = new OutputStreamWriter(output, "UTF-8");
              while ((inputLine = br.readLine()) != null) {
                if (statecounter >= 50)
                  break;
                if (inputLine.contains(",") && !inputLine.contains("20")
                    && !inputLine.contains("img")) {
                  if (cnt > 210) {
                    statecounter++;
                    currstate = state[statecounter];
                    p = inputLine.indexOf("\">");
                    q = inputLine.indexOf("</");
                    if (p < 0 || q < 0 || q - p < 0)
                      continue;

                    String currgov = inputLine.substring(p + 2, q);

                    if (currstate.equals(currentstate)) {

                      USCitizenPreppart1.original42 = currgov; // currgov;Note:8/24/2012

                      out.write(currgov + "   (" + currstate + "   )" + "\n");
                      out.close();
                      break;
                    }

                  }

                }
                cnt++;

              }
              output.close();
              out.close();
            }

            if (i == 0) {
              filename = "/sdcard/senatordataactual.txt";
              output = new FileOutputStream(filename);
              out = new OutputStreamWriter(output, "UTF-8");
              cnt = 0;
              String str, str1, str2, str3, str4, str5, str6, str7, str8;
              while ((inputLine = br.readLine()) != null) {
                if (inputLine.contains("first_name"))
                  firstname[cnt] = inputLine;
                if (inputLine.contains("last_name"))
                  lastname[cnt] = inputLine;
                if (inputLine.contains("state")) {
                  state[cnt] = inputLine;
                  cnt++;
                }
              }
              for (int id = 0; id < 100; id++) {

                str1 = ((firstname[id].replace("<first_name>", "")).trim())
                    .replaceAll("\\n", "");
                ;
                str2 = ((str1.replace("</first_name>", ""))).trim().replaceAll(
                    "\\n", "");
                ;
                ;
                str3 = ((lastname[id].replace("<last_name>", "")).trim())
                    .replaceAll("\\n", "");
                ;
                ;
                str4 = ((str3.replace("</last_name>", "")).trim()).replaceAll(
                    "\\n", "");
                ;
                ;
                str5 = str2 + " " + str4;
                str6 = ((str5)).trim().replaceAll("\\n", "");
                ;
                ;
                sentr[id] = str6.replaceAll("\\\\n", "");
                str7 = ((state[id].replace("<state>", "")).trim()).replaceAll(
                    "\\n", "");
                ;
                str8 = ((str7.replace("</state>", "")).trim()).replaceAll(
                    "\\n", "");
                ;
                state[id] = str8;

                if (state[id].equals(currentstate)) {
                  USCitizenPreppart1.original42 =
                  // USCitizenPrep.allanswers[19] =
                  sentr[id] + "   (" + state[id] + "   )";
                  out.write(sentr[id] + "   (" + state[id] + "   )" + "\n");
                  break;
                }
              }
            }

          } catch (Exception e) {
            timedout = true;
            e.printStackTrace();
          }

          finally {
            try {
              if (out != null)
                out.close();// caused nullpointerexception
              if (input != null)
                input.close();
              if (output != null)
                output.close();
            } catch (IOException e) {
              e.printStackTrace();

            }

          }

        }
      }

      return null;
    }

    protected void onPostExecute(Boolean timedout) {

      if (timedout)
        return;// exit gracefully
    }

    @Override
    protected void onPreExecute() {
      super.onPreExecute();

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
      super.onProgressUpdate(progress);

    }

  }

  static boolean timedout = false;
  static ArrayList<String> strings = new ArrayList<String>();
  String currentstate;

  Justdownload(String currentstate) {

    this.currentstate = currentstate;
    // if(returningUser)fillData();
    try {

      URL[] urlarray = new URL[2];
      urlarray[0] = new URL(
          "http://www.senate.gov/general/contact_information/senators_cfm.xml");// 2
                                                                                // letter
                                                                                // naneof
                                                                                // state
      urlarray[1] = new URL(
          "http://en.wikipedia.org/wiki/List_of_current_United_States_governors");// name
                                                                                  // of
                                                                                  // state
                                                                                  // full
      DownloadFile dfl = new DownloadFile();
      dfl.execute(urlarray);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
