package com.mayera.dbliteex;



public class QAPair {
	 
    //private variables
    int _id;
    String _question;
    String answer;
 
    // Empty constructor
    public QAPair(){
 
    }
    // constructor
    public QAPair(int id, String question, String answer){
        this._id = id;
        this._question = question;
        this.answer = answer;
    }
 
    // constructor
    public QAPair(String question, String answer){
        this._question = question;
        this.answer = answer;
    }
    // getting ID
    public int getID(){
        return this._id;
    }
 
    // setting id
    public void setID(int id){
        this._id = id;
    }
 
    // getting question
    public String getQuestion(){
        return this._question;
    }
 
    // setting question
    public void setQuestion(String question){
        this._question = question;
    }
 
    // getting phone number
    public String getAnswer(){
        return this.answer;
    }
 
    // setting phone number
    public void setAnswer(String answer){
        this.answer = answer;
    }
}


