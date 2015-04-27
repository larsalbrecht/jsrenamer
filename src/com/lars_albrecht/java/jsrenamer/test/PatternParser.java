/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author lalbrecht
 *
 */
public class PatternParser {

	public static final int					LOOK_FORWARD	= 0;
	public static final int					LOOK_BACKWARD	= 1;

	private ArrayList<String>				inputList		= null;
	private ArrayList<ArrayList<String>>	separatedList	= null;
	private String							pattern			= null;
	private String							separator		= null;
	private ArrayList<StringEx>				result			= null;

	public PatternParser(final ArrayList<String> inputList, final String pattern, final String separator) {
		this.inputList = inputList;
		this.pattern = pattern;
		if (separator != null) {
			this.separator = Pattern.quote(separator);
		}
	}

	/**
	 * This method parses the given strings and set a pattern as result.
	 */
	public void parse() {
		this.separatedList = this.separateBySeparator(inputList, separator);
		ArrayList<String> baseStringList = this.getLongestItem(separatedList);
		CompareResult compareResultForward = this.getCompared(LOOK_FORWARD, baseStringList, separatedList);
		CompareResult compareResultBackward = this.getCompared(LOOK_BACKWARD, baseStringList, separatedList);

		this.result = this.getMergedResult(compareResultForward, compareResultBackward);
		this.result = this.cleanUpResult(this.result);
		System.out.println(compareResultForward.getResultList());
		System.out.println(compareResultBackward.getResultList());
		System.out.println(this.result);
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}

	/**
	 * This cleans up the result of the merge. It finds elements of type
	 * StringEx.TYPE_UNKNOWN and removes consecutive elements (the last stays)
	 * using getNextUnknown (recursive function).
	 *
	 * @param input
	 * @return cleaned up results
	 */
	private ArrayList<StringEx> cleanUpResult(ArrayList<StringEx> input) {
		ArrayList<StringEx> result = input;
		int start = -1;
		for (int i = 0; i < input.size(); i++) {
			if (result.get(i).getType().equals(StringEx.TYPE_UNKNOWN)) {
				start = i;
				int lastUnknown = this.getNextUnknown(input, start);
				if (lastUnknown > start) {
					for (int j = start; j < lastUnknown; j++) {
						result.remove(j);
					}
					StringEx temp = result.get(start);
					temp.setType(StringEx.TYPE_STRING);
					result.set(start, temp);
					result = this.cleanUpResult(result);
					break;
				}
			}
		}
		return result;
	}

	private int getNextUnknown(final ArrayList<StringEx> input, int index) {
		index++;
		if (input.size() >= index && input.get(index).getType().equals(StringEx.TYPE_UNKNOWN)) {
			return this.getNextUnknown(input, index);
		}
		return --index;
	}

	public void getStrings() {
		String tempStr = null;
		System.out.println("GET STRINGS NOW");
		System.out.println("");
		String splitter = " - ";
		ArrayList<String> splittedPattern = new ArrayList<String>(Arrays.asList(pattern.split(splitter)));
		System.out.println("ALLLIST: " + this.separatedList);

		/*
		 * each SplittedPattern if(matches...) each string
		 */
		int patternIndex = 0;
		for (ArrayList<String> stringList : this.separatedList) {
			tempStr = "";
			patternIndex = 0;
			boolean added = Boolean.FALSE;
			for (String string : splittedPattern) {
				added = Boolean.FALSE;
				if (string.matches("^\\[SH\\]{1}$")) {
					for (int i = 0; i < this.result.size(); i++) {
						int type = this.result.get(i).getType();
						if (type == StringEx.TYPE_HARDSTRING) {
							tempStr += this.result.get(i).getString();
							added = Boolean.TRUE;
						}
					}
				} else if (string.matches("^\\[S\\]{1}$")) {
					for (int i = 0; i < this.result.size(); i++) {
						int type = this.result.get(i).getType();
						if (type == StringEx.TYPE_STRING) {
							tempStr += ">CONCATINATE STRINGS FOR THIS POSITION<"; // TODO
																					// Fill
																					// this
																					// correctly
							added = Boolean.TRUE;
						}
					}
				} else if (string.matches("^\\[SI\\]{1}$")) {
					for (int i = 0; i < this.result.size(); i++) {
						int type = this.result.get(i).getType();
						if (type == StringEx.TYPE_STRINGINTEGER) {
							tempStr += this.getStringForIntPosition(this.result.get(i), stringList.get(this.result.get(
									i).getListIndex()), this.result.get(i).getPlaceholderIntPositions());
							added = Boolean.TRUE;
						}
					}
				} else if (string.matches("^\\[D\\]{1}$")) {
					/*
					 * for (int i = 0; i < this.result.size(); i++) { int type =
					 * this.result.get(i).getType(); if(type ==
					 * StringEx.TYPE_DUMMY){ tempStr += "X"; added =
					 * Boolean.TRUE; System.out.println("XXXXX"); } }
					 */
				}
				if (patternIndex < (splittedPattern.size() - 1) && added) {
					tempStr += splitter;
				}
				patternIndex++;
			}
			System.out.println(tempStr);
		}

		/*
		 * for (ArrayList<String> stringList : this.separatedList) {
		 * System.out.println(stringList); tempStr = "";
		 * System.out.println("Results: " + this.result.size()); for (int i = 0;
		 * i < this.result.size(); i++) { int type =
		 * this.result.get(i).getType(); System.out.println("Find type: " +
		 * this.result.get(i).getType()); for (String string : splittedPattern)
		 * { if (string.matches("^\\[SH\\]{1}$") && type ==
		 * StringEx.TYPE_HARDSTRING) { tempStr += this.result.get(i).getString()
		 * + " "; } else if (string.matches("^\\[S\\]{1}$")) {
		 *
		 * } else if (string.matches("^\\[SI\\]{1}$") && type ==
		 * StringEx.TYPE_STRINGINTEGER) { tempStr +=
		 * this.getStringForIntPosition(this.result.get(i),
		 * stringList.get(this.result.get(i).getListIndex()),
		 * this.result.get(i).getPlaceholderIntPositions()); } else if
		 * (string.matches("^\\[D\\]{1}$") && type == StringEx.TYPE_DUMMY) {
		 *
		 * } } } System.out.println(tempStr); }
		 */
	}

	private String getStringForIntPosition(StringEx originalStringItem, String replaceStringItem,
			ArrayList<Point> placeholderIntPositions) {
		String result = originalStringItem.getString();
		StringBuilder resultBuilder = null;
		for (Point point : placeholderIntPositions) {
			for (int i = point.x; i <= point.y; i++) {
				if (i <= replaceStringItem.length()) {
					resultBuilder = new StringBuilder(result);
					resultBuilder.setCharAt(i, replaceStringItem.charAt(i));
					result = resultBuilder.toString();
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param compareResultForward
	 * @param compareResultBackward
	 * @return ArrayList<StringEx>
	 */
	private ArrayList<StringEx> getMergedResult(CompareResult compareResultForward, CompareResult compareResultBackward) {
		ArrayList<StringEx> resultList = compareResultForward.getResultList();
		for (int i = 0; i < resultList.size(); i++) {
			if ((resultList.get(i).getType().equals(compareResultBackward.getResultList().get(i).getType()) && compareResultBackward
					.getResultList().get(i).getType() >= StringEx.TYPE_INTEGER)
					|| (compareResultBackward.getResultList().get(i).getType() != StringEx.TYPE_UNKNOWN)) {
				StringEx tempStringEx = compareResultBackward.getResultList().get(i);
				if (tempStringEx.getType().equals(StringEx.TYPE_HARDSTRING)) {
					tempStringEx.setType(compareResultForward.getResultList().get(i).getType());
				}
				resultList.set(i, tempStringEx);
			}
		}

		return resultList;
	}

	/**
	 * @param compareDirection
	 * @param baseStringList
	 * @param separatedList
	 * @return CompareResult
	 */
	private CompareResult getCompared(int compareDirection, ArrayList<String> baseStringList,
			ArrayList<ArrayList<String>> separatedList) {

		ArrayList<StringEx> resultList = new ArrayList<StringEx>();

		int start = -1;
		int end = -1;

		if (compareDirection == PatternParser.LOOK_BACKWARD) {
			Collections.reverse(baseStringList);
		}

		// compare each list entry with the baseStringList.
		for (ArrayList<String> testSeparatedList : separatedList) {
			if (!testSeparatedList.equals(baseStringList)) {

				// if direction is backwards, reverse list
				if (compareDirection == PatternParser.LOOK_BACKWARD) {
					Collections.reverse(testSeparatedList);
				}
				// for each item (string) in list
				for (int i = 0; i < testSeparatedList.size(); i++) {

					// if entries are equals, then set/add string as hardstring
					if (testSeparatedList.get(i).equals(baseStringList.get(i))) { // equals
						if (start == -1) {
							start = i;
						}
						if (resultList.size() <= i || (resultList.size() > i && resultList.get(i) != null)) {
							int type = StringEx.TYPE_HARDSTRING;
							if (compareDirection == PatternParser.LOOK_BACKWARD) {
								type = StringEx.TYPE_DUMMY;
							}
							if (resultList.size() > i) {
								resultList.set(i, new StringEx(testSeparatedList.get(i), type, i));
							} else {
								type = StringEx.TYPE_HARDSTRING;
								resultList.add(new StringEx(testSeparatedList.get(i), type, i));
							}
						}
					} else { // not equals
						if (end == -1) {
							end = i;
							end--;
							start = -1;
						} else if (resultList.size() > i && resultList.get(i) != null) {
							start = -1;
						}
						int index = i;
						if (compareDirection == PatternParser.LOOK_BACKWARD) {
							// get real index
							String value = testSeparatedList.get(i);
							Collections.reverse(testSeparatedList);
							index = testSeparatedList.indexOf(value);
							Collections.reverse(testSeparatedList);
						}
						// compare by characters
						ArrayList<CharacterEx> characterList = this.getCharacterList(baseStringList.get(i),
								testSeparatedList.get(i));
						StringEx tempStringEx = new StringEx(null, StringEx.TYPE_UNKNOWN, index, characterList);

						if (i < resultList.size()) {
							if ((resultList.get(i).getType() != tempStringEx.getType() && tempStringEx.getType() > resultList
									.get(i).getType())
									|| resultList.get(i).getType() == tempStringEx.getType()
									&& (resultList.get(i).getPlaceholderIntPositions() == null || resultList.get(i)
											.getPlaceholderIntPositions().size() == tempStringEx
											.getPlaceholderIntPositions().size())) {
								resultList.set(i, tempStringEx);
							}
						} else {
							resultList.add(tempStringEx);
						}
					}
				}
				// if direction is backwards, reverse list
				if (compareDirection == PatternParser.LOOK_BACKWARD) {
					Collections.reverse(testSeparatedList);
				}
			}
		}

		if (compareDirection == PatternParser.LOOK_BACKWARD) {
			Collections.reverse(baseStringList);
		}

		if (compareDirection == PatternParser.LOOK_BACKWARD) {
			Collections.reverse(resultList);
			int newStart = -1;
			int newEnd = -1;
			for (int i = 0; i < resultList.size(); i++) {
				if (resultList.get(i) != null && newStart == -1) {
					newStart = i;
				}
				if (newStart > -1 && resultList.get(i) == null) {
					newEnd = --i;
					break;
				}
			}
			start = newStart;
			end = newEnd;
		}

		return new CompareResult(resultList, start, end);
	}

	private ArrayList<CharacterEx> getCharacterList(final String baseString, final String testString) {
		ArrayList<CharacterEx> resultList = null;
		Character[] baseChars = ArrayUtils.toObject(baseString.toCharArray());
		Character[] testChars = ArrayUtils.toObject(testString.toCharArray());

		Character placeholder = '*';
		if (baseChars.length == testChars.length) {
			resultList = new ArrayList<CharacterEx>();
			for (int j = 0; j < baseChars.length; j++) {
				if (CharacterEx.getTypeForChars(baseChars[j], testChars[j]) == CharacterEx.TYPE_INTEGER) {
					for (int i = 0; i < baseChars.length; i++) {
						if (testChars.length - 1 >= i) {
							if (baseChars[i].equals(testChars[i])) {
								if (resultList.isEmpty() || resultList.size() <= i) {
									resultList.add(new CharacterEx(baseChars[i], CharacterEx
											.getTypeForChar(baseChars[i]), Boolean.FALSE));
									System.out.println("add - equal -> " + baseChars[i] + " | " + testChars[i]);
								} else if ((resultList.get(i).getC() != baseChars[i].charValue())) {
									System.out.println("set - equal -> " + baseChars[i] + " | " + testChars[i]);
									resultList.set(
											i,
											new CharacterEx(placeholder, CharacterEx.getTypeForChars(resultList.get(i),
													baseChars[i]), Boolean.TRUE));
								}
							} else {
								if (resultList.isEmpty() || resultList.size() <= i) {
									System.out.println("add - unequal -> " + baseChars[i] + " | " + testChars[i]);
									resultList.add(new CharacterEx(placeholder, CharacterEx.getTypeForChars(
											baseChars[i], testChars[i]), Boolean.TRUE));
									System.out.println(resultList.get(resultList.size() - 1));
								} else if (!(resultList.get(i).getC() == placeholder)) {
									System.out.println("set - unequal -> " + baseChars[i] + " | " + testChars[i]);
									resultList.set(
											i,
											new CharacterEx(placeholder, CharacterEx.getTypeForChars(resultList.get(i),
													testChars[i]), Boolean.TRUE));
								}
							}
						}
					}
				}
			}
		}

		return resultList;
	}

	private ArrayList<String> getLongestItem(final ArrayList<ArrayList<String>> separatedList) {
		// find longest
		int longestIndex = -1;
		int i = 0;
		for (ArrayList<String> arrayList : separatedList) {
			if (longestIndex == -1) {
				longestIndex = 0;
			} else if (arrayList.size() > separatedList.get(longestIndex).size()) {
				longestIndex = i;
			}
			i++;
		}
		return separatedList.get(longestIndex);
	}

	private ArrayList<ArrayList<String>> separateBySeparator(final ArrayList<String> inputList, final String separator) {
		ArrayList<ArrayList<String>> separatedList = new ArrayList<ArrayList<String>>();

		for (String string : inputList) {
			String[] separatedString = string.split(separator);
			separatedList.add(new ArrayList<String>(Arrays.asList(separatedString)));
		}
		return separatedList;
	}

	/**
	 * @return the result
	 */
	public final ArrayList<StringEx> getResult() {
		return result;
	}

}
