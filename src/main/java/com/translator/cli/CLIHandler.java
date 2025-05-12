package com.translator.cli;

import com.translator.core.Translator;
import com.translator.dictionary.MorseCodeDictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class CLIHandler {

	private static final String RESET = "\u001B[0m";
	private static final String BOLD = "\u001B[1m";

	private static final String WHITE = "\u001B[37m";
	private static final String LIGHT_BLUE = "\u001B[96m";
	private static final String LIGHT_PURPLE = "\u001B[95m";
	private static final String LIGHT_GREEN = "\u001B[92m";
	private static final String LIGHT_CYAN = "\u001B[96m";
	private static final String LIGHT_GRAY = "\u001B[37m";
	private static final String BRIGHT_YELLOW = "\u001B[93m";
	private static final String BRIGHT_RED = "\u001B[91m";
	private static final String DARK_GRAY = "\u001B[90m";
	private static final String DARK_BLUE = "\u001B[94m";
	private static final String OLIVE_GREEN = "\u001B[32m";

	private static final String FRAME_COLOR = LIGHT_GRAY;
	private static final String TITLE_COLOR = LIGHT_BLUE + BOLD;
	private static final String OPTION_COLOR = LIGHT_PURPLE;
	private static final String INPUT_COLOR = LIGHT_CYAN;
	private static final String OUTPUT_COLOR = LIGHT_GREEN;
	private static final String ERROR_COLOR = BRIGHT_RED + BOLD;
	private static final String EXIT_COLOR = BRIGHT_YELLOW + BOLD;
	private static final String HELP_COLOR = WHITE + BOLD;
	private static final String HELP_TITLE_COLOR = DARK_BLUE + BOLD;


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
			int choice = getUserChoice();
			handleUserChoice(choice);

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
				case OPTION_ENGLISH_TO_MORSE -> handleEnglishToMorse();
				case OPTION_MORSE_TO_ENGLISH -> handleMorseToEnglish();
				case OPTION_SHOW_SUPPORTED_CHARACTERS -> showSupportedCharacters();
				case OPTION_HELP -> displayHelp();
				case OPTION_EXIT -> isRunning = false;
				default ->
						System.out.println(ERROR_COLOR + "Invalid option. Please enter a number from 1 to 5." + RESET);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR_COLOR + e.getMessage() + RESET);
		}
	}

	private static void handleEnglishToMorse() {
		try {
			System.out.print(INPUT_COLOR + "Enter English text: " + RESET);
			String english = reader.readLine();
			String result = Translator.translateEnglishToMorse(english);
			System.out.println(OUTPUT_COLOR + "Morse Code: " + RESET + result);
		} catch (IOException ioEx) {
			System.out.println(ERROR_COLOR + ioEx.getMessage() + RESET);
		}
	}

	private static void handleMorseToEnglish() {
		try {
			System.out.print(INPUT_COLOR + "Enter Morse code: " + RESET);
			String morse = reader.readLine();
			String result = Translator.translateMorseToEnglish(morse);
			System.out.println(OUTPUT_COLOR + "English Text: " + RESET + result);

		} catch (IOException ioEx) {
			System.out.println(ERROR_COLOR + ioEx.getMessage() + RESET);
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
				WHITE + BOLD + "Letters: " + letters.toString() + RESET,
				WHITE + BOLD + "Numbers: " + numbers.toString() + RESET,
				WHITE + BOLD + "Symbols: " + symbols.toString() + RESET
		};

		int maxLen = title.length();
		for (String line : lines) {
			maxLen = Math.max(maxLen, line.replaceAll("\u001B\\[[;\\d]*m", "").length());
		}
		maxLen = Math.max(maxLen, 40);

		System.out.println(DARK_GRAY + "┌" + "─".repeat(maxLen + 4) + "┐" + RESET);
		System.out.println(DARK_GRAY + "│ " + DARK_BLUE + BOLD + centerText(title, maxLen + 2) + RESET + " " + DARK_GRAY + "│" + RESET);
		System.out.println(DARK_GRAY + "├" + "─".repeat(maxLen + 4) + "┤" + RESET);

		for (String line : lines) {
			String plainLine = line.replaceAll("\u001B\\[[;\\d]*m", "");
			System.out.println(DARK_GRAY + "│  " + RESET + line + " ".repeat(maxLen - plainLine.length()) + "  " + DARK_GRAY + "│" + RESET);
		}

		System.out.println(DARK_GRAY + "└" + "─".repeat(maxLen + 4) + "┘" + RESET);

	}


	private static int getUserChoice() {
		while (true) {
			System.out.print(INPUT_COLOR + "Choose an option (1-5): " + RESET);
			try {
				String input = reader.readLine().trim();
				int choice = Integer.parseInt(input);
				if (choice >= 1 && choice <= 5) {
					return choice;
				} else {
					System.out.println(ERROR_COLOR + "Invalid option. Please enter a number from 1 to 5." + RESET);
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println(ERROR_COLOR + "Please enter a valid number." + RESET);
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

		int width = title.length();
		for (String opt : options) {
			width = Math.max(width, opt.length());
		}
		width = Math.max(width, 30);

		printFrame(title, options, width);
	}

	private static void printFrame(String title, String[] options, int width) {
		System.out.println(FRAME_COLOR + "╔" + "═".repeat(width + 6) + "╗" + RESET);
		System.out.println(FRAME_COLOR + "║" + RESET + "   " + TITLE_COLOR + centerText(title, width) + RESET + "   " + FRAME_COLOR + "║" + RESET);
		System.out.println(FRAME_COLOR + "╠" + "═".repeat(width + 6) + "╣" + RESET);

		for (String opt : options) {
			System.out.println(FRAME_COLOR + "║" + RESET + "   " + OPTION_COLOR + opt + RESET + " ".repeat(width - opt.length()) + "   " + FRAME_COLOR + "║" + RESET);
		}

		System.out.println(FRAME_COLOR + "╚" + "═".repeat(width + 6) + "╝" + RESET);
	}

	private static void displayHelp() {
		String title = "MORSE CODE TRANSLATOR - HELP GUIDE";
		String[] lines = {

				HELP_TITLE_COLOR + "English -> Morse:" + RESET,
				HELP_COLOR + "- Converts letters, numbers, and common symbols to Morse code" + RESET,
				HELP_COLOR + "- Spaces between letters are represented by spaces" + RESET,
				HELP_COLOR + "- Words are separated by '/' in Morse code" + RESET,
				"",

				HELP_TITLE_COLOR + "Morse -> English:" + RESET,
				HELP_COLOR + "- Use dots (.) and dashes (-) for Morse code symbols" + RESET,
				HELP_COLOR + "- Use space between letters and '/' between words" + RESET,
				OLIVE_GREEN + BOLD +  "- Example: \".... . .-.. .-.. --- / .-- --- .-. .-.. -..\" → \"HELLO WORLD\"" + RESET,
				"",

				HELP_TITLE_COLOR + "Tips:" + RESET,
				HELP_COLOR + "- Input is case-insensitive for English" + RESET,
				HELP_COLOR + "- Invalid characters will generate an error message" + RESET,
		};

		int maxLen = title.length();
		for (String line : lines) {
			maxLen = Math.max(maxLen, line.replaceAll("\u001B\\[[;\\d]*m", "").length());
		}
		maxLen = Math.max(maxLen, 40);

		System.out.println(DARK_GRAY + "┌" + "─".repeat(maxLen + 4) + "┐" + RESET);
		System.out.println(DARK_GRAY + "│ " + DARK_BLUE + BOLD + centerText(title, maxLen + 2) + RESET + " " + DARK_GRAY + "│" + RESET);
		System.out.println(DARK_GRAY + "├" + "─".repeat(maxLen + 4) + "┤" + RESET);

		for (String line : lines) {
			String plainLine = line.replaceAll("\u001B\\[[;\\d]*m", "");
			System.out.println(DARK_GRAY + "│  " + RESET + line + " ".repeat(maxLen - plainLine.length()) + "  " + DARK_GRAY + "│" + RESET);
		}

		System.out.println(DARK_GRAY + "└" + "─".repeat(maxLen + 4) + "┘" + RESET);
	}

	private static void displayExitMessage() {
		String message = "Thank you for using the Morse Code Translator!";
		int width = message.length();
		width = Math.max(width, 40);

		System.out.println(FRAME_COLOR + "╔" + "═".repeat(width + 4) + "╗" + RESET);
		System.out.println(FRAME_COLOR + "║  " + EXIT_COLOR + centerText(message, width) + FRAME_COLOR + "  ║" + RESET);
		System.out.println(FRAME_COLOR + "╚" + "═".repeat(width + 4) + "╝" + RESET);
	}

	private static String centerText(String text, int width) {
		if (text.length() >= width) {
			return text;
		}
		int padding = (width - text.length()) / 2;
		return " ".repeat(padding) + text + " ".repeat(width - padding - text.length());
	}

	private static void promptEnterKey() {

		try {

			System.out.print(INPUT_COLOR + "Press ENTER to continue..." + RESET);
			reader.readLine();

		} catch (IOException ioEx) {
			System.out.println(ERROR_COLOR + ioEx.getMessage() + RESET);
		}

	}

	private static void closeBufferedReader() {
		try {
			reader.close();
		} catch (IOException e) {
			System.out.println(ERROR_COLOR + e.getMessage() + RESET);
		}

	}

}
