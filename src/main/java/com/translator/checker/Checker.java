package com.translator.checker;

import com.translator.dictionary.MorseCodeDictionary;

public class Checker {

	public static boolean isNullOrEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}

	public static boolean isValidEnglishInput(String input) {
		if (isNullOrEmpty(input)) {
			throw new IllegalArgumentException("English input should not be null or empty.");
		}

		input = input.toUpperCase();
		for (char ch : input.toCharArray()) {
			if (!MorseCodeDictionary.getValidEnglishCharacters().contains(ch)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isValidMorseInput(String input) {
		if (isNullOrEmpty(input)) {
			throw new IllegalArgumentException("Morse input should not be null or empty.");
		}

		String normalized = input.toUpperCase().replaceAll("\\s*/\\s*", " ");
		for (String token : normalized.split("\\s+")) {
			if (!MorseCodeDictionary.getValidMorseStrings().contains(token)) {
				return false;
			}
		}
		return true;
	}
}
