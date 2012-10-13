package com.mayera;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Question {
	static final int QUESTION_BANK_SIZE = 90;

	public static List<Question> populateKnowledge(InputStream is) {

		List<Question> questions = new ArrayList<Question>();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String questionAnswer = "";
		List<String> answers;
		int id = 0;
		String question = "";
		String correctAnswer=null;
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			NodeList questionList = doc.getElementsByTagName("question");

			for (int temp = 0; temp < questionList.getLength(); temp++) {
                
				Node questionNode = questionList.item(temp);
				if (questionNode.getNodeType() == Node.ELEMENT_NODE) {

					question = (questionNode.getFirstChild().getNodeValue());
					System.out.println(question+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$1") ;
					Element questionElement = (Element) questionNode;
					NodeList answerList = questionElement.getElementsByTagName("answer");
                    answers = new ArrayList<String>();
                    for (int tempans = 0; tempans < answerList.getLength(); tempans++){
                    	Node answerNode = answerList.item(tempans);
                    	answers.add(answerNode.getFirstChild().getNodeValue());
                    	Node isCorrectNode = answerNode.getAttributes().getNamedItem("correct");
                    	if (isCorrectNode != null) {
                    		correctAnswer =  answerNode.getFirstChild().getNodeValue();
                    		System.out.println("THIS ANSWER IS CORRECT " + correctAnswer);
                    	}
                   //Check here for correctness 	
                    }
                    System.out.println(question+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$2") ;
                   questions.add(new Question(correctAnswer,temp,answers,question));
                   

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return questions;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	String question;
	String correctAnswer;
	int identifier;
	List<String> answers = new ArrayList<String>();

	private Question(String correctAnswer, int identifier,
			List<String> answers, String question) {
		this.correctAnswer = correctAnswer;
		this.identifier = identifier;
		this.answers = answers;
		this.question = question;

	}

	public List<String> getAnswers() {
		return this.answers;
	}

	public String getQuestion() {
		return this.question;
	}

	public String getCorrectAnswer() {
		return this.correctAnswer;
	}

	public int getIdentifier() {
		return this.identifier;
	}
}
