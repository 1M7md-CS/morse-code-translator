package com.translator.validator;

import com.translator.cli.ConsoleColors;
import com.translator.dictionary.MorseCodeDictionary;

public class MorseInputValidator {

	public static void validateNotNullOrEmpty(String input) {
		if (input == null || input.trim().isEmpty()) {
			throw new IllegalArgumentException(ConsoleColors.ERROR_COLOR + "Input should not be null or empty." + ConsoleColors.RESET);
		}
	}

	public static void validateEnglishInput(String input) {

		validateNotNullOrEmpty(input);
		input = input.toUpperCase();
		for (char ch : input.toCharArray()) {
			if (ch > 127) {
				throw new IllegalArgumentException(ConsoleColors.ERROR_COLOR + "Non-ASCII character detected: '" + ch + "'" + ConsoleColors.RESET);
			}
			if (!MorseCodeDictionary.getValidEnglishCharacters().contains(ch)) {
				throw new IllegalArgumentException(ConsoleColors.ERROR_COLOR + "Invalid character. Please see 'Show Supported Characters' in the main menu for the list of allowed characters." + ConsoleColors.RESET);
			}
		}
	}

	public static void validateMorseInput(String input) {
		validateNotNullOrEmpty(input);
		String normalized = input.toUpperCase().replaceAll("\\s*/\\s*", " ");
		for (String token : normalized.split("\\s+")) {
			if (!MorseCodeDictionary.getValidMorseStrings().contains(token)) {
				throw new IllegalArgumentException(ConsoleColors.ERROR_COLOR + "Invalid Morse code." + ConsoleColors.RESET);
			}
		}

	}


}
