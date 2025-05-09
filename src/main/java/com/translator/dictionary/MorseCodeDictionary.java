package com.translator.dictionary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class MorseCodeDictionary {

	private static final Map<Character, String> ENGLISH_TO_MORSE_MAP = new HashMap<>();
	private static final Map<String, Character> MORSE_TO_ENGLISH_MAP = new HashMap<>();
	private static final Set<Character> VALID_CHARACTERS = new HashSet<>();
	private static final Set<String> VALID_MORSE_STRINGS = new HashSet<>();

	static {

		ENGLISH_TO_MORSE_MAP.put('A', ".-");
		ENGLISH_TO_MORSE_MAP.put('B', "-...");
		ENGLISH_TO_MORSE_MAP.put('C', "-.-.");
		ENGLISH_TO_MORSE_MAP.put('D', "-..");
		ENGLISH_TO_MORSE_MAP.put('E', ".");
		ENGLISH_TO_MORSE_MAP.put('F', "..-.");
		ENGLISH_TO_MORSE_MAP.put('G', "--.");
		ENGLISH_TO_MORSE_MAP.put('H', "....");
		ENGLISH_TO_MORSE_MAP.put('I', "..");
		ENGLISH_TO_MORSE_MAP.put('J', ".---");
		ENGLISH_TO_MORSE_MAP.put('K', "-.-");
		ENGLISH_TO_MORSE_MAP.put('L', ".-..");
		ENGLISH_TO_MORSE_MAP.put('M', "--");
		ENGLISH_TO_MORSE_MAP.put('N', "-.");
		ENGLISH_TO_MORSE_MAP.put('O', "---");
		ENGLISH_TO_MORSE_MAP.put('P', ".--.");
		ENGLISH_TO_MORSE_MAP.put('Q', "--.-");
		ENGLISH_TO_MORSE_MAP.put('R', ".-.");
		ENGLISH_TO_MORSE_MAP.put('S', "...");
		ENGLISH_TO_MORSE_MAP.put('T', "-");
		ENGLISH_TO_MORSE_MAP.put('U', "..-");
		ENGLISH_TO_MORSE_MAP.put('V', "...-");
		ENGLISH_TO_MORSE_MAP.put('W', ".--");
		ENGLISH_TO_MORSE_MAP.put('X', "-..-");
		ENGLISH_TO_MORSE_MAP.put('Y', "-.--");
		ENGLISH_TO_MORSE_MAP.put('Z', "--..");

		ENGLISH_TO_MORSE_MAP.put('0', "-----");
		ENGLISH_TO_MORSE_MAP.put('1', ".----");
		ENGLISH_TO_MORSE_MAP.put('2', "..---");
		ENGLISH_TO_MORSE_MAP.put('3', "...--");
		ENGLISH_TO_MORSE_MAP.put('4', "....-");
		ENGLISH_TO_MORSE_MAP.put('5', ".....");
		ENGLISH_TO_MORSE_MAP.put('6', "-....");
		ENGLISH_TO_MORSE_MAP.put('7', "--...");
		ENGLISH_TO_MORSE_MAP.put('8', "---..");
		ENGLISH_TO_MORSE_MAP.put('9', "----.");

		ENGLISH_TO_MORSE_MAP.put(',', "--..--");
		ENGLISH_TO_MORSE_MAP.put('.', ".-.-.-");
		ENGLISH_TO_MORSE_MAP.put(':', "---...");
		ENGLISH_TO_MORSE_MAP.put('-', "-....-");
		ENGLISH_TO_MORSE_MAP.put('?', "..--..");
		ENGLISH_TO_MORSE_MAP.put('!', "-.-.--");
		ENGLISH_TO_MORSE_MAP.put('@', ".--.-.");
		ENGLISH_TO_MORSE_MAP.put('&', ".-...");
		ENGLISH_TO_MORSE_MAP.put('/', "-..-.");
		ENGLISH_TO_MORSE_MAP.put('(', "-.--.");
		ENGLISH_TO_MORSE_MAP.put(')', "-.--.-");
		ENGLISH_TO_MORSE_MAP.put('+', ".-.-.");
		ENGLISH_TO_MORSE_MAP.put('=', "-...-");
		ENGLISH_TO_MORSE_MAP.put('"', ".-..-.");
		ENGLISH_TO_MORSE_MAP.put('\'', ".----.");
		ENGLISH_TO_MORSE_MAP.put(';', "-.-.-.");
		ENGLISH_TO_MORSE_MAP.put(' ', " ");

		for (Map.Entry<Character, String> entry : ENGLISH_TO_MORSE_MAP.entrySet()) {
			MORSE_TO_ENGLISH_MAP.put(entry.getValue(), entry.getKey());
		}

		VALID_CHARACTERS.addAll(ENGLISH_TO_MORSE_MAP.keySet());

		VALID_MORSE_STRINGS.addAll(MORSE_TO_ENGLISH_MAP.keySet());
	}

	public static Map<Character, String> getEnglishToMorseMap() {
		return ENGLISH_TO_MORSE_MAP;
	}

	public static Map<String, Character> getMorseToEnglishMap() {
		return MORSE_TO_ENGLISH_MAP;
	}

	public static Set<Character> getValidEnglishCharacters() {
		return VALID_CHARACTERS;
	}

	public static Set<String> getValidMorseStrings() {
		return VALID_MORSE_STRINGS;
	}
}