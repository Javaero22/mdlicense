package com.md.mdcms.util;

/**
 * Method convert(String str) Implemented as a class method.
 * 
 * Takes one argument: a string that may contain international characters.
 * Returns: a string with the international characters converted to hex-encoded
 * unicode. A hex-encoded unicode character has this format: \ u x x x x (spaces
 * added for emphasis) where xxxx is the hex-encoded value of the character. The
 * xxxx part is always 4 digits, 0 filled to make 4 digits.
 * 
 * We change the \ u to & # x x x x x ; because middleware understands it like
 * that
 * 
 * Example input/output: Input string: Constitución Encoded output:
 * Constituci\u00f3n or &#x00f3;n
 * 
 * Example call: String term = unicodeString.convert("Constitución");
 */

public class UnicodeString {

	private static char ahead = 0x0000;

	public static String convertResponse(String str) {

		int strlength = str.length();
		int stopAheadRead = strlength - 8;
		boolean isUTF8 = false;
		String unicodeString;

		StringBuffer ostr = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {

			/*
			 * because strings could come already in UTF-8
			 */
			ahead = 0x0000;
			isUTF8 = false;
			if (i < stopAheadRead) {
				ahead = str.charAt(i);
				// U == 0x0055
				if (ahead == 0x0055) {
					ahead = str.charAt(i + 1);
					// + == 0x002B
					if (ahead == 0x002B) {
						ahead = str.charAt(i + 2);
						// x == 0x0078
						if (ahead == 0x0078) {
							ahead = str.charAt(i + 7);
							// ; == 0x003B
							if (ahead == 0x003B) {
								unicodeString = str.substring(i, i + 8);
								if (checkUnicodeIsCorrect(unicodeString)) {
									isUTF8 = true;
								}
							}
						}
					}
				}
				// if (aheadFirst.startsWith("U")) {
				// ahead = str.charAt(i + 1);
				// ahead = str.substring(i + 1, i + 8);
				// if (ahead.startsWith("+")) {
				// ahead = str.substring(i + 2, i + 8);
				// if (ahead.startsWith("x")) {
				// ahead = str.substring(i + 7, i + 8);
				// if (ahead.startsWith("x")) {
				// }
				// }
				// }
				// }
			}

			if (isUTF8) {

				// &
				// ostr.append(0x0026);
				ostr.append("&#x");
				// #
				// ostr.append(0x0023);
				// x
				// ostr.append(0x0078);

				for (int k = i + 3; k < (i + 8); k++) {
					char ch = str.charAt(k);
					ostr.append(ch);
				}

				// String chString = "&#x" + str.substring(i + 3, i + 8);
				// char[] chars = chString.toCharArray();
				// for (int j = 0; j < chars.length; j++) {
				// ostr.append(chars[j]);
				// }
				i = i + 7;
			} else {
				char ch = str.charAt(i);
				ostr.append(ch);
			}
		}

		return (new String(ostr.toString()));
	}

	private static boolean checkUnicodeIsCorrect(String unicodeString) {
		// byte[] bytes = unicodeString.getBytes("UTF-8");
		// char ch = new Character(unicodeString.getBytes());
		return true;
	}

	public static String convert(String str) {

		// int strlength = str.length();
		// int stopAheadRead = strlength - 8;
		// boolean alreadyUTF8 = false;

		StringBuffer ostr = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {

			/*
			 * because strings could come already in UTF-8
			 */
			// ahead = "";
			// alreadyUTF8 = false;
			// if (i < stopAheadRead) {
			// ahead = str.substring(i, i + 8);
			// if (ahead.startsWith("&#x")) {
			// alreadyUTF8 = true;
			// }
			// }
			//
			// if (alreadyUTF8) {
			// ostr.append(ahead);
			// i = i + 7;
			// } else {
			// ==> normal handling underneath
			// }
			/*
			 * because strings could come already in UTF-8
			 */

			char ch = str.charAt(i);

			/*
			 * until 20120221
			 */
			/*
			 * does the char need to be converted to unicode?
			 */
			// if ((ch >= 0x0020) && (ch <= 0x007e)) {
			// if ((ch == 0x0020) || ((ch >= 0x0030) && (ch <= 0x0039)) // 0-9
			// || ((ch >= 0x0041) && (ch <= 0x005A)) // A-Z
			// || ((ch >= 0x0061) && (ch <= 0x007A)) // a-z
			// || ((ch >= 0x0028) && (ch <= 0x0029)) // ()
			// || ((ch >= 0x002C) && (ch <= 0x002E))) // ,-.
			// {
			/*
			 * no
			 */
			// ostr.append(ch);

			/*
			 * since 20120221
			 */
			/*
			 * 0-9 A-Z a-z . * + ( ) ; & , % _ ? - / : = space
			 */

			// space
			if (ch == 0x0020
			// ( ) * + , - / 0-9 : ; = ?
					|| (ch >= 0x0028) && (ch <= 0x0039)
					// =
					|| ch == 0x003D
					// ?
					|| ch == 0x003F
					// A - Z
					|| ((ch >= 0x0041) && (ch <= 0x005A))
					// a - z
					|| ((ch >= 0x0061) && (ch <= 0x007A))
					// % &
					|| (ch == 0x0025) // && (ch <= 0x0026))
					// _
					|| ch == 0x005F) {
				/*
				 * no
				 */
				ostr.append(ch);

			} else {
				/*
				 * yes
				 */
				// ostr.append("\\u"); // standard unicode format.
				// ostr.append("&#x"); until 20120221
				ostr.append("U+x"); // since 20120221
				/*
				 * get hex value of the char
				 */
				String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);
				for (int j = 0; j < 4 - hex.length(); j++) {
					/*
					 * prepend zeros because unicode requires 4 digits
					 */
					ostr.append("0");
				}
				/*
				 * standard unicode format - upper case
				 */
				ostr.append(hex.toUpperCase());
				// ostr.append(hex.toLowerCase(Locale.ENGLISH));
				ostr.append(";");
			}
		}
		return (new String(ostr));
	}
}
