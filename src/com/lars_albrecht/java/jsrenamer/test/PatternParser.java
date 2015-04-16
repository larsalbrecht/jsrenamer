/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * @author lalbrecht
 *
 */
public class PatternParser {

	public static final int LOOK_FORWARD = 0;
	public static final int LOOK_BACKWARD = 1;

	private ArrayList<String> inputList = null;
	private String pattern = null;
	private String separator = null;

	public PatternParser(final ArrayList<String> inputList, final String pattern, final String separator) {
		this.inputList = inputList;
		this.pattern = pattern;
		if(separator != null){
			this.separator = Pattern.quote(separator);
		}
	}

	public void parse() {
		ArrayList<ArrayList<String>> separatedList = this.separateBySeparator(inputList, separator);
		ArrayList<String> baseStringList = this.getLongestItem(separatedList);
		CompareResult compareResultForward = this.getCompared(LOOK_FORWARD, baseStringList, separatedList);
		CompareResult compareResultBackward = this.getCompared(LOOK_BACKWARD, baseStringList, separatedList);
		System.out.println("CompareResult Forward: " + compareResultForward.getResultList());
		System.out.println("CompareResult Forward: " + compareResultForward.getStart() + " - " + compareResultForward.getEnd());
		System.out.println("CompareResult Backward: " + compareResultBackward.getResultList());
		System.out.println("CompareResult Backward: " + compareResultBackward.getStart() + " - " + compareResultBackward.getEnd());
		CompareResult compareResult = this.getMergedCompareResult(compareResultForward, compareResultBackward);
		System.out.println("CompareResult: " + compareResult.getResultList());
	}

	private CompareResult getMergedCompareResult(
			CompareResult compareResultForward,
			CompareResult compareResultBackward) {
		ArrayList<String> resultList = compareResultForward.getResultList();
		for(int i = 0; i < resultList.size(); i++){
			if(resultList.get(i) == null && compareResultBackward.getResultList().get(i) != null){
				resultList.set(i, compareResultBackward.getResultList().get(i));
			}
		}

		return new CompareResult(resultList, 0, 0);
	}

	private CompareResult getCompared(int compareDirection,
			ArrayList<String> baseStringList,
			ArrayList<ArrayList<String>> separatedList) {

		int start = -1;
		int end = -1;

		if(compareDirection == PatternParser.LOOK_BACKWARD){
			Collections.reverse(baseStringList);
		}

		ArrayList<String> resultList = new ArrayList<String>();
		for (ArrayList<String> testSeparatedList : separatedList) {
			if(!testSeparatedList.equals(baseStringList)){
				if(compareDirection == PatternParser.LOOK_BACKWARD){
					Collections.reverse(testSeparatedList);
				}
				for(int i = 0; i < testSeparatedList.size(); i++){
					if(testSeparatedList.get(i).equals(baseStringList.get(i))){
						if(start == -1){
							start = i;
						}
						if(resultList.size() <= i || (resultList.size() > i && resultList.get(i) != null)){
							if(resultList.size() > i){
								resultList.set(i, testSeparatedList.get(i));
							} else {
								resultList.add(testSeparatedList.get(i));
							}
						}
					} else {
						if(end == -1){
							end = i;
							end--;
							start = -1;
						}
						if(resultList.size() > i){
							if(resultList.get(i) != null){
								start = -1;
							}
							resultList.set(i, null);
						} else {
							resultList.add(null);
						}
					}
				}
			}
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


}
