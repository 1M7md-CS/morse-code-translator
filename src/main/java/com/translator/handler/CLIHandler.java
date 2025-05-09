package com.translator.handler;

import com.translator.core.Translator;
import java.util.Scanner;

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

	private static final int OPTION_ENGLISH_TO_MORSE = 1;
	private static final int OPTION_MORSE_TO_ENGLISH = 2;
	private static final int OPTION_HELP = 3;
	private static final int OPTION_EXIT = 4;

	private static final Scanner scanner = new Scanner(System.in);
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

	}

	private static void handleUserChoice(int choice) {
		try {
			switch (choice) {
				case OPTION_ENGLISH_TO_MORSE:
					handleEnglishToMorse();
					break;
				case OPTION_MORSE_TO_ENGLISH:
					handleMorseToEnglish();
					break;
				case OPTION_HELP:
					displayHelp();
					break;
				case OPTION_EXIT:
					isRunning = false;
					break;
				default:
					System.out.println(ERROR_COLOR + "Invalid option. Please enter a number from 1 to 4." + RESET);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR_COLOR + "Error: " + e.getMessage() + RESET);
		}
	}

	private static void handleEnglishToMorse() {
		System.out.print(INPUT_COLOR + "Enter English text: " + RESET);
		String english = scanner.nextLine().toUpperCase();
		String result = Translator.translateEnglishToMorse(english);
		System.out.println(OUTPUT_COLOR + "Morse Code: " + RESET + result);
	}

	private static void handleMorseToEnglish() {
		System.out.print(INPUT_COLOR + "Enter Morse code: " + RESET);
		String morse = scanner.nextLine();
		String result = Translator.translateMorseToEnglish(morse);
		System.out.println(OUTPUT_COLOR + "English Text: " + RESET + result);
	}

	private static int getUserChoice() {
		while (true) {
			System.out.print(INPUT_COLOR + "Choose an option (1-4): " + RESET);
			String input = scanner.nextLine().trim();
			try {
				int choice = Integer.parseInt(input);
				if (choice >= 1 && choice <= 4) {
					return choice;
				} else {
					System.out.println(ERROR_COLOR + "Invalid option. Please enter a number from 1 to 4." + RESET);
				}
			} catch (NumberFormatException e) {
				System.out.println(ERROR_COLOR + "Please enter a valid number." + RESET);
			}
		}
	}

	private static void displayMenu() {
		String title = "MORSE CODE TRANSLATOR";

		String[] options = {
				"1. Translate English → Morse",
				"2. Translate Morse → English",
				"3. Help",
				"4. Exit"
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
				DARK_BLUE + "English → Morse:" + RESET,
				WHITE + "- Converts letters, numbers, and common symbols to Morse code" + RESET,
				WHITE + "- Spaces between letters are represented by spaces" + RESET,
				WHITE + "- Words are separated by '/' in Morse code" + RESET,
				"",
				DARK_BLUE + "Morse → English:" + RESET,
				WHITE + "- Use dots (.) and dashes (-) for Morse code symbols" + RESET,
				WHITE + "- Use space between letters and '/' between words" + RESET,
				OLIVE_GREEN + "- Example: \".... . .-.. .-.. --- / .-- --- .-. .-.. -..\" → \"HELLO WORLD\"" + RESET,
				"",
				DARK_BLUE + "Tips:" + RESET,
				WHITE + "- Input is case-insensitive for English" + RESET,
				WHITE + "- Invalid characters will generate an error message" + RESET,
		};

		int maxLen = title.length();
		for (String line : lines) {
			maxLen = Math.max(maxLen, line.replaceAll("\u001B\\[[;\\d]*m", "").length());
		}
		maxLen = Math.max(maxLen, 40);

		System.out.println(DARK_GRAY + "╭" + "─".repeat(maxLen + 4) + "╮" + RESET);
		System.out.println(DARK_GRAY + "│ " + DARK_BLUE + BOLD + centerText(title, maxLen + 2) + RESET + " " + DARK_GRAY + "│" + RESET);
		System.out.println(DARK_GRAY + "├" + "─".repeat(maxLen + 4) + "┤" + RESET);

		for (String line : lines) {
			String plainLine = line.replaceAll("\u001B\\[[;\\d]*m", "");
			System.out.println(DARK_GRAY + "│  " + RESET + line + " ".repeat(maxLen - plainLine.length()) + "  " + DARK_GRAY + "│" + RESET);
		}

		System.out.println(DARK_GRAY + "╰" + "─".repeat(maxLen + 4) + "╯" + RESET);
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
		System.out.print(INPUT_COLOR + "Press ENTER to continue..." + RESET);
		scanner.nextLine();
	}

}
