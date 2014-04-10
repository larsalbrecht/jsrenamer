/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.test;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lalbrecht
 * 
 */
public class MagicTest {

	public static int getHammingDistance(final String sequence1, final String sequence2) {
		final char[] s1 = sequence1.toCharArray();
		final char[] s2 = sequence2.toCharArray();

		final int shorter = Math.min(s1.length, s2.length);
		final int longest = Math.max(s1.length, s2.length);

		int result = 0;
		for (int i = 0; i < shorter; i++) {
			if (s1[i] != s2[i]) {
				result++;
			}
		}

		result += longest - shorter;

		return result;
	}

	public static void main(final String[] args) {
		new MagicTest();
	}

	/**
	 * @see "http://stackoverflow.com/questions/3976616/how-to-find-nth-occurrence-of-character-in-a-string"
	 * 
	 * @param string
	 * @param toFind
	 * @param n
	 * @return
	 */
	public static int nthOccurrence(final String string, final String toFind, int n) {
		n--;
		int pos = string.indexOf(toFind, 0);
		while ((n-- > 0) && (pos != -1)) {
			pos = string.indexOf(toFind, pos + 1);
		}
		return pos;
	}

	public static String replaceNthOccurrence(final String string, final String toFind, final int n, final String replacement) {
		final int pos = MagicTest.nthOccurrence(string, toFind, n);
		return (string.substring(0, pos) + string.substring(pos).replaceFirst(toFind, replacement));
	}

	public MagicTest() {
		final String s = "The red car, the red bike, and the red truck went down the red street.";
		System.out.println(s.substring(MagicTest.nthOccurrence(s, "bike", 1)));
		System.out.println(MagicTest.nthOccurrence(s, "bike", 1));
		System.out.println(MagicTest.replaceNthOccurrence(s, "bike", 1, "TEEST"));

		System.exit(-1);

		final String startString = "This is my Name - S01E10 - An Example Title";
		final String myPattern = "[VARSTRING] - [VARSTRING] - [VARSTRING]";
		String testString = "This.is.my.Name.S02E08.My.Title.GERMAN.X.TEST.TEST.TEST.TEST.TEST";

		this.printDistances(startString, testString);

		// 1. find separators (ignore here) (possible: "." " " "," "_" "-")
		final String startStringSep = " ";
		final String testStringSep = "\\.";

		// 2. replace separators in test string with start string sep
		testString = testString.replaceAll(testStringSep, startStringSep);

		this.printDistances(startString, testString);

		// 3. find alt separators ("-" "_")
		final String startStringAltSep = " - ";
		final String testStringAltSep = "";

		// 4. replace each separator for one. If distance is lower, it will be
		// saved, otherwise, it will be reversed
		int distance = 0;
		final int replaceCount = 1;
		boolean doRun = true;
		while (doRun) {
			distance = StringUtils.getLevenshteinDistance(startString, testString);
			System.out.println(distance);
			for (int i = 0; i < replaceCount; i++) {
				testString = testString.replaceFirst(startStringSep, startStringAltSep);
			}
			System.out.println(StringUtils.getLevenshteinDistance(startString, testString));
			if (distance <= StringUtils.getLevenshteinDistance(startString, testString)) {

			}
			System.out.println("test");
			doRun = false;
		}

	}

	private void printDistances(final String a, final String b) {
		System.out.println("\"" + a + "\" vs \"" + b + "\"");
		System.out.println("Levenshtein: " + StringUtils.getLevenshteinDistance(a, b));
		System.out.println("JaroWinkler: " + StringUtils.getJaroWinklerDistance(a, b));
		System.out.println("Difference : " + StringUtils.difference(a, b));

		System.out.println(" ");
	}

}
