/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test.stringPattern;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author lalbrecht
 *
 */
public class PatternTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PatternTest();
	}

	public PatternTest() {

		Character placeHolder = '*';
		Character placeHolderS = '#';

		ArrayList<String> testList = new ArrayList<String>();
		testList.add("Dexter - S01E01 - Test 9");
		testList.add("Dexter - S01E02 - Zweiter Test");
		testList.add("Dexter - S02E01 - Dritter Test");
		testList.add("Dexter - S10E11 - Vierter Test");
		testList.add("Dexter - S03E05 - Fünfter Test");

		ArrayList<CharacterEx> resultList = new ArrayList<CharacterEx>();


		String baseString = null;
		Character[] baseChars = null;
		for (String testString : testList) {
			if(baseString == null){
				baseString = testString;
				char[] tempCharArr = baseString.toCharArray();
				baseChars = ArrayUtils.toObject(tempCharArr);
			}

			if(!baseString.equals(testString)){
				char[] charArray = testString.toCharArray();
				Character[] testCharArray = ArrayUtils.toObject(charArray);
				for(int i = 0; i < baseChars.length; i++){
					if(testCharArray.length-1 >= i){
						if(baseChars[i].equals(testCharArray[i])){
							if(resultList.isEmpty() || resultList.size() <= i){
								resultList.add(new CharacterEx(baseChars[i], CharacterEx.getTypeForChar(baseChars[i]), Boolean.FALSE));
							} else if(!(resultList.get(i).getC() == baseChars[i].charValue())){
								resultList.set(i, new CharacterEx(placeHolder, CharacterEx.getTypeForChars(resultList.get(i), baseChars[i]), Boolean.TRUE));
							}
							System.out.print(new CharacterEx(baseChars[i], PatternTest.isInteger(baseChars[i].toString()) ? CharacterEx.TYPE_INTEGER : CharacterEx.TYPE_STRING));
						} else {
							if(resultList.isEmpty() || resultList.size() <= i){
								resultList.add(new CharacterEx(placeHolder, CharacterEx.getTypeForChars(baseChars[i], testCharArray[i]), Boolean.TRUE));
							} else if(!(resultList.get(i).getC() == placeHolder.charValue())){
								resultList.set(i, new CharacterEx(placeHolder, CharacterEx.getTypeForChars(resultList.get(i), testCharArray[i]), Boolean.TRUE));
							}
							System.out.print(new CharacterEx(placeHolder));
						}
					}
				}
				System.out.println("");
			}
		}

		System.out.println("");
		System.out.println("-----------> OUTPUT: ");
		System.out.println("");
		for (CharacterEx character : resultList) {
			System.out.print(character);
		}
		System.out.println("");
		for (CharacterEx character : resultList) {
			System.out.print(character.getType() == CharacterEx.TYPE_INTEGER ? "^" : " ");
		}
	}

	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}

}
