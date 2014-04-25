/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.helper;

/**
 * @author lalbrecht
 * 
 */
public class CSVHelper {

	public static String[] parseToRead(final String separator, final String line) {
		final String tempLine = line;
		// lookbehind to check if separator is escaped
		final String[] items = tempLine.split("(?<!\\\\),");
		for (int i = 0; i < items.length; i++) {
			items[i] = items[i].replaceAll("\\,", ",");
		}
		return items;
	}

	/**
	 * Prepares a string to write to a csv file. Replace the separator (if found
	 * in text) with \separator.
	 * 
	 * @param separator
	 * @param strings
	 * @return
	 */
	public static String parseToWrite(final String separator, final String... strings) {
		String line = "";
		String string = null;
		for (int i = 0; i < strings.length; i++) {
			string = strings[i];
			// escape separator in text
			line += string.replaceAll(separator, "\\\\" + separator);
			if ((i + 1) != (strings.length)) {
				line += separator;
			}
		}

		return line + "\n";
	}

}
