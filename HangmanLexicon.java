/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import acmx.export.java.io.FileReader;

public class HangmanLexicon {

	private ArrayList<String> myArrayList = new ArrayList<>();

	// This is the HangmanLexicon constructor
	public HangmanLexicon() {

		try {
			// reads file line by line
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt")); 

			while (true) {

				String line = "";
				try {
					line = rd.readLine();
					myArrayList.add(line); // adds one line to new array list

					if (line == null) {

						break;

					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			try {
				rd.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return myArrayList.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return myArrayList.get(index);
	};

}
