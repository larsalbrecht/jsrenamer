/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.test.old;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lalbrecht
 * 
 */
public class Helper {
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
		final int pos = Helper.nthOccurrence(string, toFind, n);
		return (string.substring(0, pos) + string.substring(pos).replaceFirst(toFind, replacement));
	}

	private void test() {

		System.exit(-1);
		final String startString = "This is my Name - S01E10 - An Example Title";
		final String myPattern = "[STATIC] - [STATIC][VAR] - [STATIC]";
		String testString = "This.is.my.Name.S02E08.My.Title.GERMAN.X.TEST.TEST.TEST.TEST.TEST";

		final int dist = StringUtils.getLevenshteinDistance(startString, testString);
		final int newDist = -1;
		String diff = null;
		int startDiff = -1;
		final String newString = "";
		final ConcurrentHashMap<Integer, String> matches = new ConcurrentHashMap<Integer, String>();
		for (int i = 0; i < 10; i++) { // test only 10 iterations
			diff = StringUtils.difference(startString, testString);
			startDiff = testString.indexOf(diff);
			matches.put(startDiff, testString.substring(0, startDiff));
			System.out.println(StringUtils.difference(startString.substring(startDiff + 1), testString.substring(startDiff + 1)));
		}

		System.exit(-1);

		final String s = "The red car, the red bike, and the red truck went down the red street.";
		System.out.println(s.substring(Helper.nthOccurrence(s, "bike", 1)));
		System.out.println(Helper.nthOccurrence(s, "bike", 1));
		System.out.println(Helper.replaceNthOccurrence(s, "bike", 1, "TEEST"));

		// 1. find separators (ignore here) (possible: "." " " "," "_" "-")
		final String startStringSep = " ";
		final String testStringSep = "\\.";

		// 2. replace separators in test string with start string sep
		testString = testString.replaceAll(testStringSep, startStringSep);

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
}
