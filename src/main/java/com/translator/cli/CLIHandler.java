package com.translator.cli;

import com.translator.core.Translator;
import com.translator.dictionary.MorseCodeDictionary;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class CLIHandler {

	private static final int OPTION_ENGLISH_TO_MORSE = 1;
	private static final int OPTION_MORSE_TO_ENGLISH = 2;
	private static final int OPTION_SHOW_SUPPORTED_CHARACTERS = 3;
	private static final int OPTION_HELP = 4;
	private static final int OPTION_EXIT = 5;

	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static boolean isRunning = true;

	public static void run() {

		while (isRunning) {
			displayMenu();
			handleUserChoice(getUserChoice());

			if (isRunning) {
				System.out.println();
				promptEnterKey();
			}
		}

		displayExitMessage();
		closeBufferedReader();

	}

	private static void handleUserChoice(int choice) {
		try {
			switch (choice) {
				case OPTION_ENGLISH_TO_MORSE -> handleConvert("English");
				case OPTION_MORSE_TO_ENGLISH -> handleConvert("Morse");
				case OPTION_SHOW_SUPPORTED_CHARACTERS -> showSupportedCharacters();
				case OPTION_HELP -> displayHelp();
				case OPTION_EXIT -> isRunning = false;
				default -> System.out.println(ConsoleColors.ERROR_COLOR + "Invalid option. Please enter a number from 1 to 5." + ConsoleColors.RESET);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ConsoleColors.ERROR_COLOR + e.getMessage() + ConsoleColors.RESET);
		}
	}

	private static void handleConvert(String choice) {
		try {

			if (choice.equalsIgnoreCase("English")) {
				System.out.print(ConsoleColors.INPUT_COLOR + "Enter English text: " + ConsoleColors.RESET);
				String english = reader.readLine();
				String result = Translator.translateEnglishToMorse(english);
				System.out.println(ConsoleColors.OUTPUT_COLOR + "Morse Code: " + ConsoleColors.RESET + result);

			} else if (choice.equalsIgnoreCase("Morse")) {
				System.out.print(ConsoleColors.INPUT_COLOR + "Enter Morse code: " + ConsoleColors.RESET);
				String morse = reader.readLine();
				String result = Translator.translateMorseToEnglish(morse);
				System.out.println(ConsoleColors.OUTPUT_COLOR + "English Text: " + ConsoleColors.RESET + result);

			}

		} catch (IOException e) {
			System.out.println(ConsoleColors.ERROR_COLOR + e.getMessage() + ConsoleColors.RESET);
		}
	}

	private static void showSupportedCharacters() {

		StringBuilder letters = new StringBuilder();
		StringBuilder numbers = new StringBuilder();
		StringBuilder symbols = new StringBuilder();

		for (char ch : MorseCodeDictionary.getValidEnglishCharacters()) {
			if (Character.isLetter(ch)) {
				letters.append(ch).append(" ");
			} else if (Character.isDigit(ch)) {
				numbers.append(ch).append(" ");
			} else if (ch == ' ') {
				symbols.append("(Space) ");
			} else {
				symbols.append(ch).append(" ");
			}
		}

		String title = "MORSE CODE TRANSLATOR - Supported English Characters";
		String[] lines = {
				"Letters: " + letters,
				"Numbers: " + numbers,
				"Symbols: " + symbols
		};

		int longestLineLength = Frame.getLongestLineLength(title, lines);
		Frame.printMenuFrame(title, lines, longestLineLength,6);

	}

	private static int getUserChoice() {
		while (true) {
			System.out.print(ConsoleColors.INPUT_COLOR + "Choose an option (1-5): " + ConsoleColors.RESET);
			try {
				String input = reader.readLine().trim();
				int choice = Integer.parseInt(input);
				if (choice >= 1 && choice <= 5) {
					return choice;
				} else {
					System.out.println(ConsoleColors.ERROR_COLOR + "Invalid option. Please enter a number from 1 to 5." + ConsoleColors.RESET);
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println(ConsoleColors.ERROR_COLOR + "Please enter a valid number." + ConsoleColors.RESET);
			}
		}
	}

	private static void displayMenu() {
		String title = "MORSE CODE TRANSLATOR";

		String[] options = {
				"1. Translate English -> Morse",
				"2. Translate Morse -> English",
				"3. Show Supported Characters",
				"4. Help",
				"5. Exit"
		};

		int longestLineLength = Frame.getLongestLineLength(title, options);
		Frame.printMenuFrame(title, options, longestLineLength,6);


	}

	private static void displayHelp() {
		String title = "MORSE CODE TRANSLATOR - HELP GUIDE";
		String[] lines = {
				"English -> Morse:",
				"- Converts letters, numbers, and common symbols to Morse code",
				"- Spaces between letters are represented by spaces",
				"- Words are separated by '/' in Morse code",
				"",
				"Morse -> English:",
				"- Use dots (.) and dashes (-) for Morse code symbols",
				"- Use space between letters and '/' between words",
				"- Example: \".... . .-.. .-.. --- / .-- --- .-. .-.. -..\" → \"HELLO WORLD\"",
				"",
				"Tips:",
				"- Input is case-insensitive for English",
				"- Invalid characters will generate an error message"
		};

		int longestLineLength = Frame.getLongestLineLength(title, lines);
		Frame.printMenuFrame(title, lines, longestLineLength,6);
	}

	private static void displayExitMessage() {
		String exitMessage = "Thank you for using the Morse Code Translator!";

		Frame.printExitFrame(exitMessage, 6);

	}

	private static void promptEnterKey() {
		try {
			System.out.print(ConsoleColors.INPUT_COLOR + "Press ENTER to continue..." + ConsoleColors.RESET);
			reader.readLine();
		} catch (IOException e) {
			System.out.println(ConsoleColors.ERROR_COLOR + e.getMessage() + ConsoleColors.RESET);
		}
	}

	private static void closeBufferedReader() {
		try {
			reader.close();
		} catch (IOException e) {
			System.out.println(ConsoleColors.ERROR_COLOR + e.getMessage() + ConsoleColors.RESET);
		}

	}



}
