package fr.nessar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;

public class GenAnoCode {

	private static class Pair {
		private final String key;
		private final String value;

		public Pair(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

	}

	public static String encrypt(String key, String input) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			int inputDigit = Character.getNumericValue(input.charAt(i));
			int keyDigit = Character.getNumericValue(key.charAt(i % key.length()));
			int encryptedDigit = (inputDigit + keyDigit) % 10;
			output.append(encryptedDigit);
		}
		return output.toString();
	}

	public static String decrypt(String key, String input) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			int inputDigit = Character.getNumericValue(input.charAt(i));
			int keyDigit = Character.getNumericValue(key.charAt(i % key.length()));
			int decryptedDigit = (inputDigit + 10 - keyDigit) % 10;
			output.append(decryptedDigit);
		}
		return output.toString();
	}

	private static List<Pair> encryptCodes(String key, List<String> input) {
		List<Pair> output = new ArrayList<>();
		for (String s : input) {
			output.add(new Pair(encrypt(key, s), s));
		}
		return output;
	}

	public static void saveAnoCodeToCsv(String key, List<String> input, String path) {
		List<Pair> anoCode = encryptCodes(key, input);
		StringBuilder csv = new StringBuilder();
		csv.append("anoCode,student\n");
		for (Pair pair : anoCode) {
			csv.append(pair.getKey()).append(",").append(pair.getValue()).append("\n");
		}
		try {
			Files.write(csv.toString().getBytes(), new File(path));
		} catch (IOException e) {
			System.out.println("Error saving the CSV file" + e.getMessage());
		}
	}
}