package com.md.mdcms.base;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.List;

public final class EscapeChars {

	private static final List<String> specialChar = Arrays.asList("â", "ä",
			"à", "á", "ã", "å", "ç", "ñ", "<", "&", "é", "ê", "ë", "è", "í",
			"î", "ï", "ì", "ß", "Â", "Ä", "À", "Á", "Ã", "Å", "Ç", "Ñ", "¦",
			">", "ø", "É", "Ê", "Ë", "È", "Í", "Î", "Ï", "Ì", "'", "\"", "Ø",
			"«", "»", "ð", "ý", "þ", "±", "°", "ª", "º", "æ", "¸", "Æ", "¤",
			"µ", "¡", "¿", "Ð", "Ý", "Þ", "®", "^", "£", "¥", "·", "©", "§",
			"¶", "¼", "½", "¾", "[", "¯", "¨", "´", "×", "­", "ô", "ö", "ò",
			"ó", "õ", "¹", "û", "ü", "ù", "ú", "ÿ", "÷", "²", "Ô", "Ö", "Ò",
			"Ó", "Õ", "³", "Û", "Ü", "Ù", "Ú");

	private static final List<String> specialCharUnicode = Arrays.asList(
			"&#226;", "&#228;", "&#224;", "&#225;", "&#227;", "&#229;",
			"&#231;", "&#241;", "&#60;", "&#38;", "&#233;", "&#234;", "&#235;",
			"&#232;", "&#237;", "&#238;", "&#239;", "&#236;", "&#223;",
			"&#194;", "&#196;", "&#192;", "&#193;", "&#195;", "&#197;",
			"&#199;", "&#209;", "&#166;", "&#62;", "&#248;", "&#201;",
			"&#202;", "&#203;", "&#200;", "&#205;", "&#206;", "&#207;",
			"&#204;", "&#39;", "&#34;", "&#216;", "&#171;", "&#187;", "&#240;",
			"&#253;", "&#254;", "&#177;", "&#176;", "&#170;", "&#186;",
			"&#230;", "&#184;", "&#198;", "&#164;", "&#181;", "&#161;",
			"&#191;", "&#208;", "&#221;", "&#222;", "&#174;", "&#162;",
			"&#163;", "&#165;", "&#183;", "&#169;", "&#167;", "&#182;",
			"&#188;", "&#189;", "&#190;", "&#172;", "&#175;", "&#168;",
			"&#180;", "&#215;", "&#173;", "&#244;", "&#246;", "&#242;",
			"&#243;", "&#245;", "&#185;", "&#251;", "&#252;", "&#249;",
			"&#250;", "&#255;", "&#247;", "&#178;", "&#212;", "&#214;",
			"&#210;", "&#211;", "&#213;", "&#179;", "&#219;", "&#220;",
			"&#217;", "&#218;");

	/*
	 * 34=", 38=&, 64=@, 224=�, 225=�, 226=�, 232=�, 233=�
	 */
	private static int[] chars = { 64, 224, 225, 226, 232, 233 };

	// public static String forXML(String aText){
	// final StringBuilder result = new StringBuilder();
	// final StringCharacterIterator iterator = new
	// StringCharacterIterator(aText);
	// char character = iterator.current();
	// while (character != CharacterIterator.DONE ){
	// if(character == '�') {
	// result.append("&#232;");
	// }
	/*
	 * if(character == '�') { result.append("&#233;"); } if (character == '<') {
	 * result.append("&lt;"); } else if (character == '>') {
	 * result.append("&gt;"); } else if (character == '\"') {
	 * result.append("&quot;"); } else if (character == '\'') {
	 * result.append("&#039;"); } else if (character == '&') {
	 * result.append("&amp;"); }
	 */
	// else {
	// the char is not a special one
	// add it to the result as is
	// result.append(character);
	// }
	// character = iterator.next();
	// }
	// return result.toString();
	// }

	public static String forHTML(String aText) {
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(
				aText);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			} else if (character == '>') {
				result.append("&gt;");
			}
			/*
			 * else if (character == '&') { result.append("&amp;"); } else if
			 * (character == '\"') { result.append("&quot;"); } else if
			 * (character == '\'') { result.append("&#039;"); } else if
			 * (character == '(') { result.append("&#040;"); } else if
			 * (character == ')') { result.append("&#041;"); } else if
			 * (character == '#') { result.append("&#035;"); } else if
			 * (character == '%') { result.append("&#037;"); } else if
			 * (character == ';') { result.append("&#059;"); } else if
			 * (character == '+') { result.append("&#043;"); } else if
			 * (character == '-') { result.append("&#045;"); }
			 */
			else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	public static String XmlAllEncode(String text) {

		for (int i = 0; i < chars.length - 1; i++) {
			text = text.replaceAll(String.valueOf((char) chars[i]), "&#"
					+ chars[i] + ";");
		}
		return text;
	}

	public static String XmlAllDecode(String text) {
		return null;
	}

	public static String XmlEncode(String text) {
		text = text.replaceAll("&gt;", ">");
		text = text.replaceAll("&lt;", "<");
		text = text.replaceAll("&amp;", "&");
		text = text.replaceAll("&quot;", "&#34;");
		text = text.replaceAll("&apos;", "'");
		return text;
	}

	public static String XmlDecode(String text) {
		text = text.replace("&#38;#34;", "&quot;");
		return text;
	}

}
