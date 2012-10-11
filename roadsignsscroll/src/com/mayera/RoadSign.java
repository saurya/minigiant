package com.mayera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoadSign {
  static final int QUESTION_BANK_SIZE = 54;

  public static List<RoadSign> populateRoadSign(InputStream is) {
    final int[] icons = { com.mayera.R.drawable.signs1,
        com.mayera.R.drawable.signs2, com.mayera.R.drawable.signs3,
        com.mayera.R.drawable.signs4, com.mayera.R.drawable.signs5,
        com.mayera.R.drawable.signs6, com.mayera.R.drawable.signs7,
        com.mayera.R.drawable.signs8, com.mayera.R.drawable.signs9,
        com.mayera.R.drawable.signs10, com.mayera.R.drawable.signs11,
        com.mayera.R.drawable.signs12, com.mayera.R.drawable.signs13,
        com.mayera.R.drawable.signs14, com.mayera.R.drawable.signs15,
        com.mayera.R.drawable.signs16, com.mayera.R.drawable.signs17,
        com.mayera.R.drawable.signs18, com.mayera.R.drawable.signs19,
        com.mayera.R.drawable.signs20, com.mayera.R.drawable.signs21,
        com.mayera.R.drawable.signs22, com.mayera.R.drawable.signs23,
        com.mayera.R.drawable.signs24, com.mayera.R.drawable.signs25,
        com.mayera.R.drawable.signs26, com.mayera.R.drawable.signs27,
        com.mayera.R.drawable.signs28, com.mayera.R.drawable.signs29,
        com.mayera.R.drawable.signs30, com.mayera.R.drawable.signs31,
        com.mayera.R.drawable.signs32, com.mayera.R.drawable.signs33,
        com.mayera.R.drawable.signs34, com.mayera.R.drawable.signs35,
        com.mayera.R.drawable.signs36, com.mayera.R.drawable.signs37,
        com.mayera.R.drawable.signs38, com.mayera.R.drawable.signs39,
        com.mayera.R.drawable.signs40, com.mayera.R.drawable.signs41,
        com.mayera.R.drawable.signs42, com.mayera.R.drawable.signs43,
        com.mayera.R.drawable.signs44, com.mayera.R.drawable.signs45,
        com.mayera.R.drawable.signs46, com.mayera.R.drawable.signs47,
        com.mayera.R.drawable.signs48, com.mayera.R.drawable.signs49,
        com.mayera.R.drawable.signs50, com.mayera.R.drawable.signs51,
        com.mayera.R.drawable.signs52, com.mayera.R.drawable.signs53,
        com.mayera.R.drawable.signs54

    };
    List<RoadSign> roadSigns = new ArrayList<RoadSign>();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String answer = "";
    List<String> answers;
    int id = 0;
    try {

      while (((answer = br.readLine()) != null && id < QUESTION_BANK_SIZE)) {
        answers = Arrays.asList(answer.split(","));
        roadSigns.add(new RoadSign(answers.get(0), id, answers, icons[id]));
        id++;
      }

      br.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return roadSigns;
  }

  String correctAnswer;
  int identifier;
  int icon;
  List<String> answers = new ArrayList<String>();

  private RoadSign(String correctAnswer, int identifier, List<String> answers,
      int icons) {
    this.correctAnswer = correctAnswer;
    this.identifier = identifier;
    this.answers = answers;
    this.icon = icons;
  }

  public List<String> getAnswers() {
    return this.answers;
  }

  public String getCorrectAnswer() {
    return this.correctAnswer;
  }

  public int getIcon() {

    return this.icon;
  }

  public int getIdentifier() {
    return this.identifier;
  }

}
