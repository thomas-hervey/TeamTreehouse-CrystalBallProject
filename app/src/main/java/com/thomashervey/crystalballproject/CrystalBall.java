package com.thomashervey.crystalballproject;

import java.util.Random;

/**
 * 			  This class supports the MainActivity.java class by providing
 * 			  the list of 'predicted' crystal ball responses and randomly
 * 			  assigning one.
 *
 * 			  This project was created while following the teamtreehouse.com
 * 			  Build A Simple Android App project
 *
 * @version   Completed Jan 21, 2014
 * @author    Thomas Hervey <thomasahervey@gmail.com>
 */
public class CrystalBall {

    // crystal ball responses
	public String[] mAnswers = { 
			"It is certain", 
			"It is decidedly so",
			"All signs say YES", 
			"The stars are not aligned",
			"My reply is no", 
			"It is doubtful",
			"Better not tell you now", 
			"Concentrate and ask again",
			"Unable to answer now" };

    /**
     * Randomly pick a crystal ball response
     *
     * @param  none
     * @return String answer - response answer
     */
	public String getAnAnswer() { 

		String answer = "";
		
		// Randomly select one of three answers: Yes, No, or Maybe
		Random randomGenerator = new Random(); // Construct a new Random number generator
		int randomNumber = randomGenerator.nextInt(mAnswers.length);
		
		answer = mAnswers[randomNumber];
		
		return answer;
	}
}
