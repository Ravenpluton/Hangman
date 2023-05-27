/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Font;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	
	// instance variables used
	private String incorrectLetters = "";
	private GLabel letters = new GLabel("");
	private GLabel display = new GLabel("");

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		
		int x = getWidth() / 2;
		int height = getHeight() / 2 - 2 * (SCAFFOLD_HEIGHT / 3);
		
		drawScaffold(x, height);
		drawBeam(x, height);
		drawRope(x, height);
	}

	// draws rope
	private void drawRope(int x, int y1) {
		int y2 = y1 + ROPE_LENGTH;
		GLine rope = new GLine(x, y1, x, y2);
		add(rope);
	}

	// draws beam
	private void drawBeam(int x2, int y) {
		int x1 = x2 - BEAM_LENGTH;
		GLine beam = new GLine(x1, y, x2, y);
		add(beam);
	}

	// draws main part of the scaffold
	private void drawScaffold(int center, int y2) {
		int x = center - BEAM_LENGTH;
		int y1 = getHeight() / 2 + SCAFFOLD_HEIGHT / 3;
		GLine scaffold = new GLine(x, y1, x, y2);
		add(scaffold);
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
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(char letter, int guessCount) {
		updateLabel(letter);

		int handY = getHeight() / 2 - 2 * (SCAFFOLD_HEIGHT / 3) + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD;
		int leftHandX = UPPER_ARM_LENGTH;
		int rightHandX = -UPPER_ARM_LENGTH;

		int legY = getHeight() / 2 - 2 * (SCAFFOLD_HEIGHT / 3) + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH;
		int leftLegX = HIP_WIDTH;
		int rightLegX = -HIP_WIDTH;

		int footY = getHeight() / 2 - 2 * (SCAFFOLD_HEIGHT / 3) + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH
				+ LEG_LENGTH;
		int leftFootX = HIP_WIDTH + FOOT_LENGTH;
		int rightFootX = -HIP_WIDTH - FOOT_LENGTH;

		switch (guessCount) {
		case 7:
			drawHead();
			break;
		case 6:
			drawBody();
			break;
		case 5:
			drawHand(handY, leftHandX);
			break;
		case 4:
			drawHand(handY, rightHandX);
			break;
		case 3:
			drawLeg(legY, leftLegX);
			break;
		case 2:
			drawLeg(legY, rightLegX);
			break;
		case 1:
			drawFoot(footY, leftFootX, leftLegX);
			break;
		case 0:
			drawFoot(footY, rightFootX, rightLegX);
			break;
		}

	}


	// updates label with guessed letter
	private void updateLabel(char letter) {
		incorrectLetters += letter;
		letters.setLabel(incorrectLetters);
		double x = getWidth() / 4;
		double y = getHeight() - LABEL_OFFSET_FROM_BOTTOM - letters.getAscent();
		add(letters, x, y);
	}

	// draws foot
	private void drawFoot(int y, int footX, int legX) {
		int x1 = getWidth() / 2 - footX;
		int x2 = getWidth() / 2 - legX;
		GLine foot = new GLine(x1, y, x2, y);
		add(foot);
	}

	// draws leg
	private void drawLeg(int y, int legX) {
		drawUpperLeg(y, legX);
		drawLowerLeg(y, legX);
	}

	// draws lower part of the leg
	private void drawLowerLeg(int y, int legX) {
		int x = getWidth() / 2 - legX;
		int y2 = y + LEG_LENGTH;
		GLine lowerLeg = new GLine(x, y, x, y2);
		add(lowerLeg);
	}

	// draws upper part of the leg
	private void drawUpperLeg(int y, int legX) {
		int x1 = getWidth() / 2 - legX;
		int x2 = getWidth() / 2;
		GLine upperLeg = new GLine(x1, y, x2, y);
		add(upperLeg);
	}

	// draws hand
	private void drawHand(int y, int x) {
		drawUpperArm(y, x);
		drawLowerArm(y, x);
	}

	// draws lower part of the hand
	private void drawLowerArm(int y, int armX) {
		int x = getWidth() / 2 - armX;
		int y2 = y + LOWER_ARM_LENGTH;
		GLine lowerArm = new GLine(x, y, x, y2);
		add(lowerArm);
	}

	// draws upper part of the hand
	private void drawUpperArm(int y, int x) {
		int x1 = getWidth() / 2 - x;
		int x2 = getWidth() / 2;
		GLine upperArm = new GLine(x1, y, x2, y);
		add(upperArm);
	}

	// draws body
	private void drawBody() {
		int x = getWidth() / 2;
		int y1 = getHeight() / 2 - 2 * (SCAFFOLD_HEIGHT / 3) + ROPE_LENGTH + HEAD_RADIUS * 2;
		int y2 = getHeight() / 2 - 2 * (SCAFFOLD_HEIGHT / 3) + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH;
		GLine body = new GLine(x, y1, x, y2);
		add(body);
	}

	// draws head
	private void drawHead() {
		GOval head = new GOval(HEAD_RADIUS * 2, HEAD_RADIUS * 2);
		int x = getWidth() / 2 - HEAD_RADIUS;
		int y = getHeight() / 2 - 2 * (SCAFFOLD_HEIGHT / 3) + ROPE_LENGTH;
		add(head, x, y);
	}
	
	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	private static final int LABEL_OFFSET_FROM_BOTTOM = 50;
	private static final int DIF_BETWEEN_LABELS = 20;
}
