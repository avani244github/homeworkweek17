package org.student.path.utils;

import java.util.Random;

public class TestUtils {

	public static String getRandomValue(){
		Random random = new Random();
		int randomInt = random.nextInt(100000);
		return Integer.toString(randomInt);
	}
	public static Integer getRandomNumber(){
		Random random = new Random();
		int randomInt = random.nextInt(100000);
		return Integer.valueOf(randomInt);
	}
}
