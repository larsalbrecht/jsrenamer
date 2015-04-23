/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

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

	public static final int LOOK_FORWARD = 0;
	public static final int LOOK_BACKWARD = 1;

	private ArrayList<String> inputList = null;
	private ArrayList<ArrayList<String>> separatedList = null;
	private String pattern = null;
	private String separator = null;
	private ArrayList<StringEx> result = null;

	public PatternParser(final ArrayList<String> inputList, final String pattern, final String separator) {
		this.inputList = inputList;
		this.pattern = pattern;
		if(separator != null){
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
		System.out.println(compareResultForward.getResultList());
		System.out.println(compareResultBackward.getResultList());
		System.out.println(this.result);
		System.out.println("");
		System.out.println("");
		System.out.println("");
	}

	public void getStrings(){
		for (ArrayList<String> stringList : this.separatedList) {
			System.out.println(stringList);
			for (StringEx resultStr : this.result) {
				if(resultStr.getListIndex() < stringList.size()){
					System.out.print(stringList.get(resultStr.getListIndex()));
					System.out.print(" - ");
				}
			}
			System.out.println("");
		}
	}

	/**
	 *
	 * @param compareResultForward
	 * @param compareResultBackward
	 * @return ArrayList<StringEx>
	 */
	private ArrayList<StringEx> getMergedResult(
			CompareResult compareResultForward,
			CompareResult compareResultBackward) {
		ArrayList<StringEx> resultList = compareResultForward.getResultList();
		for(int i = 0; i < resultList.size(); i++){
			if((resultList.get(i).getType().equals(compareResultBackward.getResultList().get(i).getType()) && compareResultBackward.getResultList().get(i).getType() >= StringEx.TYPE_INTEGER) || (compareResultBackward.getResultList().get(i).getType() != StringEx.TYPE_UNKNOWN)){
				resultList.set(i, compareResultBackward.getResultList().get(i));
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
	private CompareResult getCompared(int compareDirection,
			ArrayList<String> baseStringList,
			ArrayList<ArrayList<String>> separatedList) {

		ArrayList<StringEx> resultList = new ArrayList<StringEx>();

		int start = -1;
		int end = -1;

		if(compareDirection == PatternParser.LOOK_BACKWARD){
			Collections.reverse(baseStringList);
		}

		// compare each list entry with the baseStringList.
		for (ArrayList<String> testSeparatedList : separatedList) {
			if(!testSeparatedList.equals(baseStringList)){

				// if direction is backwards, reverse list
				if(compareDirection == PatternParser.LOOK_BACKWARD){
					Collections.reverse(testSeparatedList);
				}
				// for each item (string) in list
				for(int i = 0; i < testSeparatedList.size(); i++){

					// if entries are equals, then set/add string as hardstring
					if(testSeparatedList.get(i).equals(baseStringList.get(i))){ // equals
						if(start == -1){
							start = i;
						}
						if(resultList.size() <= i || (resultList.size() > i && resultList.get(i) != null)){
							int type = StringEx.TYPE_HARDSTRING;
							if(compareDirection == PatternParser.LOOK_BACKWARD){
								type = StringEx.TYPE_DUMMY;
							}
							if(resultList.size() > i){
								resultList.set(i, new StringEx(testSeparatedList.get(i), type, i));
							} else {
								resultList.add(new StringEx(testSeparatedList.get(i), type, i));
							}
						}
					} else { // not equals
						if(end == -1){
							end = i;
							end--;
							start = -1;
						} else if(resultList.size() > i && resultList.get(i) != null){
							start = -1;
						}
						// compare by characters
						ArrayList<CharacterEx> characterList = this.getCharacterList(baseStringList.get(i), testSeparatedList.get(i));
						StringEx tempStringEx = new StringEx(null, StringEx.TYPE_UNKNOWN, i, characterList);

						if(i < resultList.size()){
							if((resultList.get(i).getType() != tempStringEx.getType() && tempStringEx.getType() > resultList.get(i).getType()) || resultList.get(i).getType() == tempStringEx.getType() && (resultList.get(i).getPlaceholderIntPositions() == null || resultList.get(i).getPlaceholderIntPositions().size() == tempStringEx.getPlaceholderIntPositions().size())){
								resultList.set(i, tempStringEx);
							}
						} else {
							resultList.add(tempStringEx);
						}
					}
				}
				// if direction is backwards, reverse list
				if(compareDirection == PatternParser.LOOK_BACKWARD){
					Collections.reverse(testSeparatedList);
				}
			}
		}

		if(compareDirection == PatternParser.LOOK_BACKWARD){
			Collections.reverse(baseStringList);
		}

		if(compareDirection == PatternParser.LOOK_BACKWARD){
			Collections.reverse(resultList);
			int newStart = -1;
			int newEnd = -1;
			for (int i = 0; i < resultList.size(); i++) {
				if(resultList.get(i) != null && newStart == -1){
					newStart = i;
				}
				if(newStart > -1 && resultList.get(i) == null){
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
		if(baseChars.length == testChars.length){
			resultList = new ArrayList<CharacterEx>();
			for(int j = 0; j < baseChars.length; j++){
				if(CharacterEx.getTypeForChars(baseChars[j], testChars[j]) == CharacterEx.TYPE_INTEGER){
					for(int i = 0; i < baseChars.length; i++){
						if(testChars.length-1 >= i){
							if(baseChars[i].equals(testChars[i])){
								if(resultList.isEmpty() || resultList.size() <= i){
									resultList.add(new CharacterEx(baseChars[i], CharacterEx.getTypeForChar(baseChars[i]), Boolean.FALSE));
								} else if(!(resultList.get(i).getC() == baseChars[i].charValue())){
									resultList.set(i, new CharacterEx(placeholder, CharacterEx.getTypeForChars(resultList.get(i), baseChars[i]), Boolean.TRUE));
								}
							} else {
								if(resultList.isEmpty() || resultList.size() <= i){
									resultList.add(new CharacterEx(placeholder, CharacterEx.getTypeForChars(baseChars[i], testChars[i]), Boolean.TRUE));
								} else if(!(resultList.get(i).getC() == placeholder)){
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
			if(longestIndex == -1){
				longestIndex = 0;
			} else if(arrayList.size() > separatedList.get(longestIndex).size()){
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
