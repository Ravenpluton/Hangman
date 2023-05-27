/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.*;

public class Hangman2 extends ConsoleProgram {

	// instance variables used in program
	private HangmanLexicon hangmanWords;
	private String randomWord, hiddenWord;
	private int guessCount;
	private int hintCount;
	private String incorrectLetters;
	private HangmanCanvas2 canvas;

	private String answer = "yes";
	private boolean b = true;
	private RandomGenerator rgen = RandomGenerator.getInstance(); // random generator

	// initializes canvas and adds welcome line
	public void init() {
		canvas = new HangmanCanvas2();
		add(canvas);
		welcome();
	}

	// prints welcome line and rules
	private void welcome() {
		println("Welcome to Hangman: Up edition!");
		println("There are three rules!");
		println("Rule one: you get certain amount of balloons equal to your tries, you lose one when you guess letter incorectly.");
		println("Rule two: There are three levels, you need to clear all of them to win.");
		println("Rule three: you can get one hint. Just enter 'hint' instead of input to get it.");
	}

	// main method, runs program and includes levels
	public void run() {
		for (int i = 0; i < 3; i++) {
			incorrectLetters = "";
			if (b == false) {
				break;
			} else if (answer.equals("yes")) {
				canvas.reset();
				guessCount = 8 - i;
				hintCount = 1;
				setUpGame();
				playGame();
			}

		}
		endCredits();
	}

	// prints end lines either when user does not want to continue or when user wins
	// all the levels
	private void endCredits() {
		if (answer.equals("no")) {
			println("It was nice playing with you. Come back soon.");
		} else if (guessCount > 0) {
			println("You cleared all of the levels.");
			println("Congratulations. You win.");
		}
	}

	/*
	 * sets up game. gets word to play with, prints that word and tells user how
	 * many guesses they have.
	 * 
	 */
	private void setUpGame() {
		getWord();
		println("The word now looks like this: " + hiddenWord);
		println("You have " + guessCount + " guesses left.");
	}

	/*
	 * method for getting for divided in two parts: getting a random word and hiding
	 * it. method also displays hidden word on canvas
	 */
	private void getWord() {
		randomWord();
		hideWord();
		canvas.displayWord(hiddenWord);
	}

	/*
	 * method for getting a random word that becomes word to play with. new hangman
	 * lexicon is created and then index of word is randomly generated.
	 * 
	 */
	private void randomWord() {
		hangmanWords = new HangmanLexicon();
		int i = rgen.nextInt(0, hangmanWords.getWordCount() - 1);
		randomWord = hangmanWords.getWord(i);
	}

	// method that hides word and turns it into string of -
	private void hideWord() {
		hiddenWord = "";
		for (int i = 0; i < randomWord.length(); i++) {
			hiddenWord += "-";
		}
	}

	/*
	 * main method for playing the game. it enters into a while loop for guessing.
	 * in while cycle first thing program does is getting users guess, then checking
	 * it and then checking if it should come out of while loop (if word is guessed
	 * case)
	 *
	 */
	private void playGame() {
		while (guessCount > 0) {
			String letter = getUsersGuess();
			checkInWord(letter);
			if (hiddenWord.equals(randomWord)) {
				break;
			}
		}

	}

	/*
	 * this method asks user to enter their guess. then it checks if user wants to
	 * get hint (if they do it returns hint as a guessed letter), if user is putting
	 * in only one character and if that character is a letter and if it is, method
	 * returns that character in form of a string (turned into upper case letter if
	 * input is lower case).
	 * 
	 */
	private String getUsersGuess() {
		String userInput = readLine("Your guess: ");
		while (true) {
			if (userInput.equals("hint") && hintCount > 0) {
				String hint = getHint();
				return hint;
			} else if (userInput.length() > 1) {
				userInput = readLine("Please enter one letter at a time: ");
			} else if (!Character.isLetter(userInput.charAt(0))) {
				userInput = readLine("Invalid entry, try again: ");
			} else {
				return userInput.toUpperCase();
			}
		}
	}

	// gets hint
	private String getHint() {
		String s = "";
		char c = randomWord.charAt(rgen.nextInt(0, randomWord.length()));
		s += c;
		hintCount--;
		return s;
	}
	
	// checks if game word contains users guessed letter and moves to next method
		// accordingly
	private void checkInWord(String letter) {
		if (randomWord.contains(letter)) {
			containsLetter(letter);
		} else {
			noLetter(letter);
		}

	}

	/*
	 * method if word doesn't contain guessed letter. it prints that letter is not
	 * in word, decreases number of guesses and prints that, pops balloon on
	 * canvas and checks if user has guesses left.
	 * 
	 */
	private void noLetter(String letter) {
		println("there are no " + letter + "'s in the word.");
		guessCount--;
		incorrectLetters += letter.charAt(0);
		canvas.noteIncorrectGuess(incorrectLetters, guessCount);
		if (guessCount == 0) {
			lose();
		} else {
			println("you have " + guessCount + " guesses left.");
		}
	}

	/*
	 * method if word contains guessed letter. prints that it does contain letter,
	 * gets letter in word, checks if word was guessed, prints how word looks like
	 * after a guess and displays that on canvas
	 * 
	 */
	private void containsLetter(String letter) {
		println("That guess is correct");
		getLetter(letter);
		if (hiddenWord.equals(randomWord)) {
			canvas.displayWord(hiddenWord);
			win();
		} else {
			println("The word now looks like this: " + hiddenWord);
			println("you have " + guessCount + " guesses left.");
		}
		canvas.displayWord(hiddenWord);
	}

	/*
	 * this method gets letter in hidden word and creates new string were everything
	 * else is hidden but that word is displayed
	 * 
	 */
	private void getLetter(String letter) {
		for (int i = 0; i < randomWord.length(); i++) {
			if (randomWord.charAt(i) == letter.charAt(0)) {
				hiddenWord = hiddenWord.substring(0, i) + letter.charAt(0)
						+ hiddenWord.substring(i + 1, hiddenWord.length());
			}
		}
	}

	// prints that user won and asks them if they want to continue
	private void win() {
		println("You guessed the word: " + hiddenWord);
		answer = getAnswer();
	}

	// gets users answer and checks if it yes or no
	private String getAnswer() {
		String input = readLine("Do you wish to continue on next level: ");
		if (!input.equals("yes") && !input.equals("no")) {
			input = readLine("Please enter yes or no: ");
		}
		return input;
	}

	// prints that user lost and puts famous meme on canvas
	private void lose() {
		b = false;
		println("You lost all the balloons.");
		println("The word was: " + randomWord);
		canvas.displayImage();
		println("You were not exactly hung but enjoy meme.");
		println("You lose.");
	}

}
