/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;

import com.lars_albrecht.java.jsrenamer.test.oldThree.StringEx;

/**
 * @author lalbrecht
 *
 */
public class PatternParser {

	public static final int		LOOK_FORWARD		= 0;
	public static final int		LOOK_BACKWARD		= 1;

	private static final int	TYPE_LONGEST_ITEM	= 0;
	private static final int	TYPE_SHORTEST_ITEM	= 1;

	private ArrayList<String>	inputList			= null;
	private String				inputPattern		= null;
	private String				outputPattern		= null;

	/**
	 * "inputList" is a list of terms to compare.<br>
	 * <br>
	 * "inputPattern" is a pattern to define the input string: [HS][HIS][S][D]<br>
	 * HS = Hard String - Fixed string that can be found in every term. Can be
	 * an integer also.<br>
	 * HIS = Hard Integer String - Fixed string with variable integer that can
	 * be found in every term.<br>
	 * S = String - Variable string that can be found in every term.<br>
	 * D = Dummy - Not needed string that can eventually found in every term.
	 * Can be missing<br>
	 * <br>
	 * "outputPattern" is a pattern to define the output string:
	 * [HS|1][HIS|1][S|1][D|1]<br>
	 * The |<number> is the index of the defined pattern type. If there are more
	 * than one in the inputPattern (like: [HS][HIS][HS]) than it will be
	 * enumerated like: [HS|1][HIS|1][HS|2]<br>
	 *
	 *
	 *
	 * @param inputList
	 *            ArrayList<String>
	 * @param inputPattern
	 *            String
	 * @param outputPattern
	 *            String
	 */
	public PatternParser(final ArrayList<String> inputList, final String inputPattern, final String outputPattern) {
		this.inputList = inputList;
		this.inputPattern = inputPattern;
		this.outputPattern = outputPattern;
	}

	public static void debugPringCharacterEx(ArrayList<CharacterEx> list) {
		if (list != null) {
			System.out.println("DEBUG:");
			for (CharacterEx characterEx : list) {
				System.out.print(PatternParser.padLeft(characterEx.getCharacter() != null ? characterEx.getCharacter().toString() : "*", 3));
			}
			System.out.println("");
			for (CharacterEx characterEx : list) {
				System.out.print(PatternParser.padLeft(Integer.toString(characterEx.getCharacterType()), 3));
			}
			System.out.println("");
			for (CharacterEx characterEx : list) {
				System.out.print(PatternParser.padLeft(characterEx.getCompareDirection() == 0 ? "<" : ">", 3));
			}
			System.out.println("");
			for(int i = 0; i < list.size()-1; i++){
				System.out.print(PatternParser.padLeft(Integer.toString(i), 3));
			}
			System.out.println("");
			System.out.println("");
		}
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}

	/**
	 * This method parses the given strings and set a pattern as result.
	 */
	public void parse() {
		ArrayList<CharacterEx> mergedString = this.getComparedString();
		System.out.println(mergedString);

	}

	private ArrayList<CharacterEx> getComparedString() {
		String baseString = this.getBaseItem(this.inputList);
		ArrayList<CharacterEx> forwardString = this.getCompared(LOOK_FORWARD, baseString, this.inputList);
//		ArrayList<CharacterEx> backwardString = this.getCompared(LOOK_BACKWARD, baseString, this.inputList);
//		ArrayList<CharacterEx> mergedString = this.getMergedResult(forwardString, backwardString);
//		ArrayList<CharacterEx> cleanedString = this.getCleanedResult(mergedString);
		System.out.println("FORWARD: " + forwardString);
		debugPringCharacterEx(forwardString);
//		System.out.println("BACKWAR: " + backwardString);
//		debugPringCharacterEx(backwardString);
//		System.out.println("MERGED : " + mergedString);
//		debugPringCharacterEx(mergedString);
//		System.out.println("CLEANED: " + cleanedString);
//		debugPringCharacterEx(cleanedString);
//		System.out.println("");
//		return cleanedString;
		return null;
	}

	/**
	 * This cleans up the result of the merge. It finds elements of type
	 * StringEx.TYPE_UNKNOWN and removes consecutive elements (the last stays)
	 * using getNextUnknown.
	 *
	 * @param input
	 * @return cleaned up results
	 */
	private int getNextUnknown(final ArrayList<CharacterEx> input, int index) {
		index++;
		if (input.size() >= index && input.get(index).getCharacterType() == CharacterEx.CHARACTER_TYPE_UNKNOWN) {
			return getNextUnknown(input, index);
		}
		return --index;
	}

	private ArrayList<CharacterEx> getCleanedResult(ArrayList<CharacterEx> mergedString) {
		@SuppressWarnings("unchecked")
		ArrayList<CharacterEx> result = (ArrayList<CharacterEx>) mergedString.clone();
		int start = -1;
		for (int index = 0; index < mergedString.size(); index++) {
			if (result.get(index).getCharacterType() == CharacterEx.CHARACTER_TYPE_UNKNOWN) {
				start = index;
				int lastUnknown = this.getNextUnknown(mergedString, start);
				if (lastUnknown > start) {
					for (int i = lastUnknown ; i > start; i--) {
						result.remove(i);
					}
					CharacterEx temp = result.get(start);
					temp.setCharacterType(CharacterEx.CHARACTER_TYPE_UNKNOWN);
					result.set(start, temp);
					result = this.getCleanedResult(result);
					break;
				}
			}
		}
		return result;
	}

	private ArrayList<CharacterEx> getMergedResult(ArrayList<CharacterEx> forwardString, ArrayList<CharacterEx> backwardString) {
		ArrayList<CharacterEx> resultList = new ArrayList<CharacterEx>();
		for (int index = 0; index < forwardString.size(); index++) {
			CharacterEx forwardChar = forwardString.get(index);
			CharacterEx backwardChar = backwardString.get(index);
			if (((forwardChar.getCharacter() == null && backwardChar.getCharacter() == null) || (forwardChar.getCharacter() != null && forwardChar.getCharacter().equals(
					backwardChar.getCharacter())))) {
				resultList.add(forwardChar);
			} else if (forwardChar.getCharacterType() == backwardChar.getCharacterType()) {
				resultList.add(forwardChar);
			} else if (backwardChar.getCharacterType() > CharacterEx.CHARACTER_TYPE_UNKNOWN) {
				resultList.add(backwardChar);
			} else if (forwardChar.getCharacterType() > CharacterEx.CHARACTER_TYPE_UNKNOWN) {
				resultList.add(forwardChar);
			}
		}

		return resultList;
	}

	private ArrayList<CharacterEx> getCompared(int compareDirection, String baseString, ArrayList<String> inputList) {
		if (compareDirection == PatternParser.LOOK_BACKWARD) {
			baseString = new StringBuilder(baseString).reverse().toString();
		}
		ArrayList<CharacterEx> resultList = new ArrayList<CharacterEx>();
		Character[] baseCharArr = ArrayUtils.toObject(baseString.toCharArray());
		boolean toLong = Boolean.FALSE;
		int toLongDiff = -1;
		int diff = -1;
		for (String testString : inputList) {
			if (compareDirection == PatternParser.LOOK_BACKWARD) {
				testString = new StringBuilder(testString).reverse().toString();
			}
			Character[] testCharArr = ArrayUtils.toObject(testString.toCharArray());
			diff = testCharArr.length - baseCharArr.length;
			for (int index = 0; index < testCharArr.length; index++) {
				if (baseCharArr.length > index) {
					Character baseChar = baseCharArr[index];
					Character testChar = testCharArr[index];
					if (index >= resultList.size()) { // add
						if(index == 57){
							System.out.println("X");
						}
						if (baseChar.equals(testChar)) {
							resultList.add(new CharacterEx(baseChar, compareDirection, CharacterEx.getCharacterType(baseChar, testChar)));
						} else {
							resultList.add(new CharacterEx(null, compareDirection, CharacterEx.getCharacterType(baseChar, testChar)));
						}
					} else { // replace
						if (!testChar.equals(resultList.get(index).getCharacter())) {
							if(index == 57){
								System.out.println("X");
							}
							resultList.set(index, new CharacterEx(null, compareDirection, CharacterEx.getCharacterType(baseChar, resultList.get(index))));
						}
					}
				} else if (!toLong || diff > toLongDiff) { // add empty
															// characters
					toLong = Boolean.TRUE;
					for (int i = 0; i < diff; i++) {
						resultList.add(new CharacterEx(null, compareDirection));
					}
					toLongDiff = diff;
				} else {
					// do nothing
					System.out.println("baseCharArr.length > index" + baseCharArr.length + " > " + index);
				}
			}
		}
		if (compareDirection == PatternParser.LOOK_BACKWARD) {
			Collections.reverse(resultList);
		}
		return resultList;
	}

	private String getStringByLength(ArrayList<String> inputList, final int type) {
		String resultString = inputList.get(0);
		for (String string : inputList) {
			switch (type) {
				case PatternParser.TYPE_LONGEST_ITEM:
					if (string.length() > resultString.length()) {
						resultString = string;
					}
					break;
				case PatternParser.TYPE_SHORTEST_ITEM:
					if (string.length() < resultString.length()) {
						resultString = string;
					}
					break;
			}
		}
		return resultString;
	}

	private String getBaseItem(final ArrayList<String> inputList) {
		return this.getStringByLength(inputList, PatternParser.TYPE_SHORTEST_ITEM);
	}

	public ArrayList<String> getStrings() {
		return new ArrayList<String>();
	}

}
