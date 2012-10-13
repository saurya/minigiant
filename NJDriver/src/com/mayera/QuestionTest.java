package com.mayera;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.mayera.Question;

import junit.framework.TestCase;

public class QuestionTest extends AndroidTestCase {

	@Test
	public void testPopulateQuestion() throws FileNotFoundException {
		InputStream is = new FileInputStream("C:/test.xml");
		List<Question> questions = Question.populateKnowledge(is);
		assertEquals(questions.size(), 90);
	}
}
