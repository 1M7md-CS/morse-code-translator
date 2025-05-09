package com.translator.core;

import com.translator.checker.Checker;
import com.translator.dictionary.MorseCodeDictionary;

public class Translator {

	public static String translateEnglishToMorse(String input) {
		if (!Checker.isValidEnglishInput(input)) {
			throw new IllegalArgumentException("Invalid English input.");
		}

		input = input.trim().toUpperCase();

		String[] words = input.split("\\s+");
		StringBuilder builder = new StringBuilder();


		for (int i = 0; i < words.length; i++) {
			char[] letters = words[i].toCharArray();

			for (char letter : letters) {
				builder.append(MorseCodeDictionary.getEnglishToMorseMap().get(letter)).append(" ");
			}

			if (i < words.length - 1) {
				builder.append("/ ");
			}
		}

		return builder.toString().trim();
	}

	public static String translateMorseToEnglish(String input) {
		if (!Checker.isValidMorseInput(input)) {
			throw new IllegalArgumentException("Invalid Morse code input.");
		}

		String[] morseWords = input.split("\\s*/\\s*");
		StringBuilder builder = new StringBuilder();

		for (String morseWord : morseWords) {
			String[] morseLetters = morseWord.split("\\s+");

			for (String morseLetter : morseLetters) {
				builder.append(MorseCodeDictionary.getMorseToEnglishMap().get(morseLetter));
			}

			builder.append(" ");
		}
		return builder.toString().trim();
	}

}