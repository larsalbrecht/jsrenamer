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
		String tempLine = line;
		tempLine = tempLine.replaceAll(separator + separator, separator);
		return tempLine.split(separator);
	}

	/**
	 * Prepares a string to write to a csv file.
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
			line += string.replaceAll(separator, separator + separator);
			if ((i + 1) != (strings.length)) {
				line += separator;
			}
		}

		return line;
	}

}
