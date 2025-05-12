package com.translator.validator;

import com.translator.dictionary.MorseCodeDictionary;

public class MorseInputValidator {

	public static void validateNotNullOrEmpty(String input) {
		if (input == null || input.trim().isEmpty()) {
			throw new IllegalArgumentException("Input should not be null or empty.");
		}
	}

	public static void validateEnglishInput(String input) {

		validateNotNullOrEmpty(input);
		input = input.toUpperCase();
		for (char ch : input.toCharArray()) {
			if (ch > 127) {
				throw new IllegalArgumentException("Non-ASCII character detected: '" + ch + "'");
			}
			if (!MorseCodeDictionary.getValidEnglishCharacters().contains(ch)) {
				throw new IllegalArgumentException("Invalid character. Please see 'Show Supported Characters' in the main menu for the list of allowed characters.");
			}
		}
	}

	public static void validateMorseInput(String input) {
		validateNotNullOrEmpty(input);
		String normalized = input.toUpperCase().replaceAll("\\s*/\\s*", " ");
		for (String token : normalized.split("\\s+")) {
			if (!MorseCodeDictionary.getValidMorseStrings().contains(token)) {
				throw new IllegalArgumentException("Invalid Morse code.");
			}
		}

	}


}
