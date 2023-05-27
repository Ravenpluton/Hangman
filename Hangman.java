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

public class Hangman extends ConsoleProgram {

	private RandomGenerator rgen = RandomGenerator.getInstance(); // random generator

	// instance variables used in program
	private HangmanLexicon hangmanWords;
	private String randomWord, hiddenWord;
	private HangmanCanvas canvas;
	private int guessCount = 8;

	// initializes canvas
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	// main method, runs program
	public void run() {
		setUpGame();
		playGame();
	}

	/*
	 * sets up game. prints welcome, gets word to play with, prints that word and
	 * tells user how many guesses they have.
	 * 
	 */
	private void setUpGame() {
		println("Welcome to Hangman!");
		getWord();
		println("The word now looks like this: " + hiddenWord);
		println("You have " + guessCount + " guesses left.");
	}

	/*
	 * method for getting for divided in two parts: getting a random word and hiding
	 * it. method also displays hidden word on canvas.
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
	 * main method for playing the game at first it prints hidden word on canvas,
	 * then enters into a while loop for guessing. in while cycle first thing
	 * program does is getting users guess, then checking it and then checking if it
	 * should come out of while loop (if word is guessed case)
	 *
	 */
	private void playGame() {
		canvas.reset();
		while (guessCount > 0) {
			String letter = getUsersGuess();
			checkInWord(letter);
			if (hiddenWord.equals(randomWord)) {
				break;
			}
		}

	}

	/*
	 * this method asks user to enter their guess. then it checks if user is putting
	 * in only one character and if that character is a letter and if it is, method
	 * returns that character in form of a string (turned into upper case letter if
	 * input is lower case).
	 * 
	 */
	private String getUsersGuess() {
		String userInput = readLine("Your guess: ");
		while (true) {
			if (userInput.length() > 1) {
				userInput = readLine("Please enter one letter at a time: ");
			} else if (!Character.isLetter(userInput.charAt(0))) {
				userInput = readLine("Invalid entry, try again: ");
			} else {
				return userInput.toUpperCase();
			}
		}
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
	 * in word, decreases number of guesses and prints that, displays body part on
	 * canvas and checks if user has guesses left.
	 * 
	 */
	private void noLetter(String letter) {
		println("there are no " + letter + "'s in the word.");
		guessCount--;
		canvas.noteIncorrectGuess(letter.charAt(0), guessCount);
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

	// prints that user won.
	private void win() {
		println("You guessed the word: " + hiddenWord);
		println("You win.");
	}

	// prints that user lost.
	private void lose() {
		println("You're completely hung.");
		println("The word was: " + randomWord);
		println("You lose.");
	}

}
