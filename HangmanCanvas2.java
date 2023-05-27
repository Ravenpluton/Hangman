/*

 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class HangmanCanvas2 extends GCanvas {

	// instance variables used
	private GLabel label = new GLabel("");
	private GLabel display = new GLabel("");
	GImage house = new GImage("house.png", getWidth(), getHeight()); // house image from animation Up!
	GImage lose = new GImage("lose.jpg"); // image displayed when user loses

	private ArrayList<GOval> list = new ArrayList<>(); // array list for balloon ball part
	private ArrayList<GLine> list2 = new ArrayList<>(); // array list for balloon thread part 

	private RandomGenerator rgen = RandomGenerator.getInstance();

	/* Constants for the balloons and labels */
	private static final int LABEL_OFFSET_FROM_BOTTOM = 50;
	private static final int DIF_BETWEEN_LABELS = 20;
	private final static int BALLOON_HEIGHT = 40;
	private final static int BALLOON_WIDTH = 30;

	/** Resets the display so that the house and balloons appear */
	public void reset() {
		removeAll();
		add(house);
		for (int i = 0; i < 8; i++) {
			createBalloon();
		}
	}

	// creates balloons
	private void createBalloon() {
		double x = rgen.nextDouble(BALLOON_WIDTH, getWidth() - BALLOON_WIDTH);
		double y = rgen.nextDouble(BALLOON_HEIGHT, getHeight() / 2);
		line(x, y);
		balloon(x, y);
	}

	// creates random part of the balloon, color generated randomly
	private void balloon(double x2l, double y2l) {
		GOval ball = new GOval(BALLOON_WIDTH, BALLOON_HEIGHT);
		Color color = rgen.nextColor();
		ball.setFilled(true);
		ball.setFillColor(color);
		add(ball, x2l - BALLOON_WIDTH / 2, y2l - BALLOON_HEIGHT);
		list.add(ball);
	}

	// creates thread part of the balloon starting point fixed and ending point
	// randomly generated
	private void line(double x2l, double y2l) {
		double x1l = getWidth() / 2;
		double y1l = 265;
		GLine line = new GLine(x1l, y1l, x2l, y2l);
		add(line);
		list2.add(line);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		display.setLabel(word);
		display.setFont("-20");
		double x = getWidth() / 4;
		double y = getHeight() - LABEL_OFFSET_FROM_BOTTOM - DIF_BETWEEN_LABELS - display.getAscent();
		add(display, x, y);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes one balloon to pop and adds the letter to the list of
	 * incorrect guesses that appears at the bottom of the window.
	 * 
	 * @return
	 */
	public void noteIncorrectGuess(String letters, int guessCount) {
		updateLabel(letters);

		audio();
		this.remove(list.get(guessCount));
		this.remove(list2.get(guessCount));

	}

	// adds balloon popping audio
	private void audio() {
		try {
			AudioInputStream win = AudioSystem.getAudioInputStream(new File("balloonpop.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(win);
			clip.start();
		} catch (Exception e) {
		}
	}

	// updates incorrectly guessed letters label
	private void updateLabel(String letters) {
		label.setLabel(letters);
		double x = getWidth() / 4;
		double y = getHeight() - LABEL_OFFSET_FROM_BOTTOM - label.getAscent();
		add(label, x, y);
	}

	/*
	 * method that displays image on full canvas when it is called.
	 * 
	 */
	public void displayImage() {
		add(lose);
	}
}
