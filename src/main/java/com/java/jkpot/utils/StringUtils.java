package com.java.jkpot.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static String getOnlyStrings(String s) {

		Pattern pattern = Pattern.compile("[^a-z A-Z]");
		Matcher matcher = pattern.matcher(s);
		String number = matcher.replaceAll("");
		return number;
	}

	public static String getOnlyNumbers(String s) {

		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(s);
		String number = matcher.replaceAll("");
		return number;
	}

	public static String toCamelCase(final String init) {
		if (init == null)
			return null;

		final StringBuilder ret = new StringBuilder(init.length());

		for (final String word : init.split(" ")) {
			if (!word.isEmpty()) {
				ret.append(Character.toUpperCase(word.charAt(0)));
				ret.append(word.substring(1).toLowerCase());
			}
			if (!(ret.length() == init.length()))
				ret.append(" ");
		}

		return ret.toString();
	}
}
