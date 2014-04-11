/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lalbrecht
 * 
 */
public class MagicTest {

	public static void main(final String[] args) {
		new MagicTest();
	}

	private static ArrayList<String> step1(final String[] tempArr) {
		final ConcurrentHashMap<Integer, String> staticStrings = new ConcurrentHashMap<Integer, String>();
		final ConcurrentHashMap<String, ConcurrentHashMap<Integer, String>> variableStrings = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, String>>();

		final ArrayList<String> tempList = new ArrayList<String>();

		// remove "other"
		for (final String string : tempArr) {
			boolean stringCheck = true;
			for (final String savedString : tempList) {
				if (string.length() <= StringUtils.difference(string, savedString).length()) {
					stringCheck = false;
					break;
				}
			}
			if (stringCheck) {
				tempList.add(string);
			}
		}

		Tupel<ConcurrentHashMap<Integer, String>, ConcurrentHashMap<Integer, String>> tempTupel = null;

		for (int i = 0; i < tempList.size(); i++) {
			for (int j = 0; j < tempList.size(); j++) {
				if (!tempList.get(i).equals(tempList.get(j))) {
					tempTupel = MagicTest.step1Helper(tempList.get(i), tempList.get(j), 0);
					staticStrings.putAll(tempTupel.getObjectA());
					variableStrings.put(tempList.get(i), tempTupel.getObjectB());
				}
			}
		}

		System.out.println(staticStrings);
		System.out.println(variableStrings);
		// for (Entry<String, Map> entry : variableStrings) {
		// entry.getValue()
		// }

		/*
		 * diff = StringUtils.difference(startString, testString); startDiff =
		 * testString.indexOf(diff); matches.put(startDiff,
		 * testString.substring(0, startDiff));
		 */

		return tempList;
	}

	private static Tupel<ConcurrentHashMap<Integer, String>, ConcurrentHashMap<Integer, String>> step1Helper(final String a,
			final String b,
			int position) {
		final ConcurrentHashMap<Integer, String> staticStrings = new ConcurrentHashMap<Integer, String>();
		final ConcurrentHashMap<Integer, String> variableStrings = new ConcurrentHashMap<Integer, String>();

		// get static
		String stringDiffA = StringUtils.difference(a, b);
		String stringDiffB = StringUtils.difference(b, a);
		final String staticString = b.substring(0, b.indexOf(stringDiffA));
		staticStrings.put(position, staticString);
		// search variable
		boolean foundMore = false;
		// for (int i = 1; i < (stringDiffA.length() - 1); i++) {
		int i = 1;
		while (i < (stringDiffA.length() - 1)) {
			if (i < (stringDiffA.length() - 1)) {
				break;
			}
			stringDiffA = stringDiffA.substring(i);
			stringDiffB = stringDiffB.substring(i);
			final String diffTestA = StringUtils.difference(stringDiffA, stringDiffB);
			if (stringDiffB.indexOf(diffTestA) > 0) {
				System.out.println("\t\tput: " + stringDiffA.substring(0, stringDiffB.indexOf(diffTestA)));
				staticStrings.put(++position, stringDiffA.substring(0, stringDiffB.indexOf(diffTestA)));
				foundMore = true;
				break;
			}
			i++;

		}
		if (foundMore) {
			final Tupel<ConcurrentHashMap<Integer, String>, ConcurrentHashMap<Integer, String>> result = MagicTest.step1Helper(stringDiffA,
					stringDiffB, ++position);
			staticStrings.putAll(result.getObjectA());
			variableStrings.putAll(result.getObjectB());
		}

		return new Tupel<ConcurrentHashMap<Integer, String>, ConcurrentHashMap<Integer, String>>(staticStrings, variableStrings);
	}

	public MagicTest() {
		final String[] tempArr = new String[] {
				"The.Vampire.Diaries.S04E02.ESTA.720p.HDTV.X264-DIMENSION.mkv",
				"The.Vampire.Diaries.S04E03.TSTB.TESTBB.720p.HDTV.X264-DIMENSION.mkv",
				"The.Vampire.Diaries.S04E04.TETC.720p.HDTV.X264-DIMENSION.mkv",
				"The.Vampire.Diaries.S04E05.TESTD.TESTD.720p.HDTV.X264-DIMENSION.mkv",
				"The.Vampire.Diaries.S04E06.TSTE.720p.HDTV.X264-DIMENSION.mkv",
				"The.Vampire.Diaries.S04E07.TETF.720p.HDTV.X264-DIMENSION.mkv",
		};

		for (final String string : MagicTest.step1(tempArr)) {
			System.out.println(string);
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
