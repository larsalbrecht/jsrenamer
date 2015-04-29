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

	public static final int		LOOK_FORWARD	= 0;
	public static final int		LOOK_BACKWARD	= 1;

	private static final int	TYPE_LONGEST_ITEM	= 0;
	private static final int	TYPE_SHORTEST_ITEM	= 1;

	private ArrayList<String>	inputList		= null;
	private String				inputPattern	= null;
	private String				outputPattern	= null;

	/**
	 * "inputList" is a list of terms to compare.<br>
	 * <br>
	 * "inputPattern" is a pattern to define the input string: [HS][HIS][S][D]<br>
	 * HS = Hard String - Fixed string that can be found in every term<br>
	 * HIS = Hard Integer String - Fixed string with variable integer that can
	 * be found in every term<br>
	 * S = String - Variable string that can be found in every term<br>
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

	/**
	 * This method parses the given strings and set a pattern as result.
	 */
	public void parse() {
		String baseString = this.getBaseItem(this.inputList);
		this.getCompared(LOOK_FORWARD, baseString, this.inputList);
	}

	private void getCompared(int compareDirection, String baseString, ArrayList<String> inputList) {
		ArrayList<CharacterEx> resultList = new ArrayList<CharacterEx>();
		Character[] baseCharArr = ArrayUtils.toObject(baseString.toCharArray());
		for (String testString : inputList) {
			Character[] testCharArr = ArrayUtils.toObject(testString.toCharArray());
			for (int i = 0; i < testCharArr.length; i++) {
//				Character baseChar = baseCharArr[i];
//				Character testChar = testCharArr[i];
			}
		}
		System.out.println(resultList);

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
