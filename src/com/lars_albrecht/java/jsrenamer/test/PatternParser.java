/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author lalbrecht
 *
 */
public class PatternParser {

	public static final int					LOOK_FORWARD		= 0;
	public static final int					LOOK_BACKWARD		= 1;

	private ArrayList<String>				inputList			= null;
	private ArrayList<ArrayList<String>>	separatedList		= null;
	private ArrayList<ArrayList<StringEx>>	separatedTypedList	= null;
	private String							pattern				= null;
	private String							separator			= null;
	private String							splitter			= null;
	private ArrayList<StringEx>				result				= null;

	public PatternParser(final ArrayList<String> inputList, final String pattern, final String separator, final String splitter) {
		this.inputList = inputList;
		this.pattern = pattern;
		if (separator != null) {
			this.separator = Pattern.quote(separator);
		}
		this.splitter = splitter;
	}

	/**
	 * This method parses the given strings and set a pattern as result.
	 */
	public void parse() {
		this.separatedList = this.separateBySeparator(inputList, separator);
		ArrayList<String> baseStringList = this.getLongestItem(separatedList);
		CompareResult compareResultForward = this.getCompared(LOOK_FORWARD, baseStringList, separatedList);
		System.out.println(compareResultForward.getResultList());
		CompareResult compareResultBackward = this.getCompared(LOOK_BACKWARD, baseStringList, separatedList);
		System.out.println(compareResultBackward.getResultList());

		this.result = this.getMergedResult(compareResultForward, compareResultBackward);
		System.out.println(this.result);
		this.result = this.cleanUpResult(this.result);
		System.out.println(this.result);
		this.separatedTypedList = this.getTypedList(this.separatedList, this.result);
	}

	/**
	 *
	 * Create a new ArrayList of ArrayList<StringEx> with the needed values
	 * (StringEx.TYPE_HARDSTRING and StringEx.TYPE_STRINGINTEGER)
	 *
	 * @param separatedList
	 * @param resultList
	 * @return separatedTypedList
	 */
	private ArrayList<ArrayList<StringEx>> getTypedList(ArrayList<ArrayList<String>> separatedList, ArrayList<StringEx> resultList) {
		ArrayList<ArrayList<StringEx>> separatedTypedList = new ArrayList<ArrayList<StringEx>>();
		ArrayList<StringEx> tempList = null;
		for (ArrayList<String> arrayList : separatedList) { // for all strings
			tempList = new ArrayList<StringEx>();
			for (int i = 0; i < arrayList.size(); i++) { // for each string
				if (i < resultList.size() && this.addToTypedList(arrayList.get(i), resultList.get(i))) {
					tempList.add(new StringEx(arrayList.get(i), StringEx.TYPE_HARDSTRING, i));
				} else {
					tempList.add(new StringEx(arrayList.get(i), StringEx.TYPE_UNKNOWN, i, Boolean.FALSE));
				}
			}
			tempList = this.cleanUpTempTypedList(tempList, resultList);
			separatedTypedList.add(tempList);
		}
		return separatedTypedList;
	}

	/**
	 * Cleans the typedList. Remove StringEx.TYPE_DUMMY entries and set entries
	 * with type StringEx.TYPE_UNKNOWN to type StringEx.TYPE_STRING.
	 *
	 * @param typedList
	 * @param resultList
	 * @return typedList
	 */
	private ArrayList<StringEx> cleanUpTempTypedList(final ArrayList<StringEx> typedList, final ArrayList<StringEx> resultList) {
		// reverse lists to work from end to start easily
		Collections.reverse(resultList);
		Collections.reverse(typedList);
		int i = 0;
		boolean removeComplete = Boolean.FALSE;
		for (Iterator<StringEx> iterator = typedList.iterator(); iterator.hasNext();) {
			StringEx string = iterator.next();
			if (!removeComplete && string.getString().equals(resultList.get(i).getString())) {
				iterator.remove();
			} else {
				removeComplete = Boolean.TRUE;
				if (string.getType().equals(StringEx.TYPE_UNKNOWN)) {
					string.setType(StringEx.TYPE_STRING);
				}
			}
			i++;
		}

		Collections.reverse(resultList);
		Collections.reverse(typedList);
		return typedList;
	}

	/**
	 * Checks if the given original String can be added to list. The element
	 * will be added to list if:<br>
	 * - compareable is not null<br>
	 * - both are of type StringEx.TYPE_HARDSTRING or type
	 * StringEx.TYPE_STRINGINTEGER<br>
	 *
	 * @param original
	 * @param compareable
	 * @return Boolean.TRUE|Boolean.FALSE
	 */
	private boolean addToTypedList(final String original, final StringEx compareable) {
		return (compareable.getString() != null && compareable.getType().equals(StringEx.TYPE_HARDSTRING) || compareable.getType().equals(StringEx.TYPE_STRINGINTEGER));
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

	public ArrayList<String> getStrings() {
		ArrayList<String> resultStrings = new ArrayList<String>();
		String tempStr = null;
		ArrayList<String> splittedPattern = new ArrayList<String>(Arrays.asList(pattern.split(this.splitter)));

		/*
		 * each SplittedPattern if(matches...) each string
		 */
		int patternIndex = 0;
		for (ArrayList<StringEx> stringList : this.separatedTypedList) {
			tempStr = "";
			patternIndex = 0;
			boolean added = Boolean.FALSE;
			for (String string : splittedPattern) {
				added = Boolean.FALSE;
				if (string.matches("^\\[SH\\]{1}$")) {
					for (int i = 0; i < this.result.size(); i++) {
						int type = this.result.get(i).getType();
						if (type == StringEx.TYPE_HARDSTRING) {
							tempStr += this.result.get(i).getString() + " ";
							added = Boolean.TRUE;
						}
					}
					tempStr = tempStr.trim();
				} else if (string.matches("^\\[S\\]{1}$")) {
					for (int i = 0; i < stringList.size(); i++) {
						int type = stringList.get(i).getType();
						if (type == StringEx.TYPE_STRING) {
							tempStr += stringList.get(i).getString().trim() + " ";
							added = Boolean.TRUE;
						}
					}
					tempStr = tempStr.trim();
				} else if (string.matches("^\\[SI\\]{1}$")) {
					for (int i = 0; i < this.result.size(); i++) {
						int type = this.result.get(i).getType();
						if (type == StringEx.TYPE_STRINGINTEGER) {
							tempStr += this.getStringForIntPosition(this.result.get(i), stringList.get(this.result.get(i).getListIndex()).getString(), this.result.get(i)
									.getPlaceholderIntPositions());
							added = Boolean.TRUE;
						}
					}
				} else if (string.matches("^\\[D\\]{1}$")) {
					// ITS DUMMY, DONT DO ANYTHING
				}
				if (patternIndex < (splittedPattern.size() - 1) && added) {
					tempStr += this.splitter;
				}
				patternIndex++;
			}
			resultStrings.add(tempStr);
		}

		return resultStrings;
	}

	private String getStringForIntPosition(StringEx originalStringItem, String replaceStringItem, ArrayList<Point> placeholderIntPositions) {
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
			if ((resultList.get(i).getType().equals(compareResultBackward.getResultList().get(i).getType()) && compareResultBackward.getResultList().get(i).getType() >= StringEx.TYPE_INTEGER)
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
	private CompareResult getCompared(int compareDirection, ArrayList<String> baseStringList, ArrayList<ArrayList<String>> separatedList) {

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
						ArrayList<CharacterEx> characterList = this.getCharacterList(baseStringList.get(i), testSeparatedList.get(i));
						StringEx tempStringEx = new StringEx(null, StringEx.TYPE_UNKNOWN, index, characterList);

						if (i < resultList.size()) {
							if ((resultList.get(i).getType() != tempStringEx.getType() && tempStringEx.getType() > resultList.get(i).getType())
									|| resultList.get(i).getType() == tempStringEx.getType()
									&& (resultList.get(i).getPlaceholderIntPositions() == null || resultList.get(i).getPlaceholderIntPositions().size() == tempStringEx
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
									resultList.add(new CharacterEx(baseChars[i], CharacterEx.getTypeForChar(baseChars[i]), Boolean.FALSE));
								} else if ((resultList.get(i).getC() != baseChars[i].charValue())) {
									resultList.set(i, new CharacterEx(placeholder, CharacterEx.getTypeForChars(resultList.get(i), baseChars[i]), Boolean.TRUE));
								}
							} else {
								if (resultList.isEmpty() || resultList.size() <= i) {
									resultList.add(new CharacterEx(placeholder, CharacterEx.getTypeForChars(baseChars[i], testChars[i]), Boolean.TRUE));
								} else if (!(resultList.get(i).getC() == placeholder)) {
									resultList.set(i, new CharacterEx(placeholder, CharacterEx.getTypeForChars(resultList.get(i), testChars[i]), Boolean.TRUE));
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
