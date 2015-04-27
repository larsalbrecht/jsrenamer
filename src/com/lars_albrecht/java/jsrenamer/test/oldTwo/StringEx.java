/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test.oldTwo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author lalbrecht
 *
 */
public class StringEx implements Iterable<CharacterEx> {

	private ArrayList<CharacterEx> characterList = null;
	private ArrayList<String> stringList = null;
	private char placeholder = '*';

	private ArrayList<Point> placeholderIntPositions = null;
	private ArrayList<Point> intPositions = null;

	/**
	 * Has StringEx placeholders?
	 */
	private boolean containsPlaceholder = Boolean.FALSE;

	/**
	 * Has StringEx placeholders of type Int?
	 */
	private boolean containsIntPlaceholder = Boolean.FALSE;

	/**
	 * Is complete StringEx int?
	 */
	private boolean isInt = Boolean.FALSE;

	public int startIndex = -1;
	public int endIndex = -1;

	public int length;

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

	private static ArrayList<Point> getIntPositions(final ArrayList<CharacterEx> characterList, final boolean onlyPlaceholder){
		ArrayList<Point> tempList = new ArrayList<Point>();
		int start = -1;
		int count = 0;
		for (CharacterEx characterEx : characterList) {
			if((onlyPlaceholder && characterEx.isPlaceholder() && characterEx.getType() == CharacterEx.TYPE_INTEGER) || (!onlyPlaceholder && characterEx.getType() == CharacterEx.TYPE_INTEGER)){
				if(start == -1){
					count = 0;
					start = characterList.indexOf(characterEx);
				}
				count++;
			} else {
				if(count > 0){
					tempList.add(new Point(start, start+count-1));
				}
				count = 0;
				start = -1;
			}
		}
		return tempList;
	}

	private static boolean containsIntPlaceholder(final ArrayList<CharacterEx> characterList){
		for (CharacterEx characterEx : characterList) {
			if(characterEx.isPlaceholder() && characterEx.getType() == CharacterEx.TYPE_INTEGER){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	private static boolean containsPlaceholder(final ArrayList<CharacterEx> characterList){
		for (CharacterEx characterEx : characterList) {
			if(characterEx.isPlaceholder()){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	private static boolean isInt(final ArrayList<CharacterEx> characterList){
		for (CharacterEx characterEx : characterList) {
			if(characterEx.getType() != CharacterEx.TYPE_INTEGER){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	public StringEx() {
		this.characterList = new ArrayList<CharacterEx>();
		this.placeholderIntPositions = new ArrayList<Point>();
	}

	public void setStrings(final ArrayList<String> stringList){
		this.stringList = stringList;
	}

	public void parseStrings(boolean output){
		String baseString = null;
		Character[] baseChars = null;
		if(output){
			System.out.println("");
			System.out.println("|------------------------|");
			System.out.println("|     STRING MATCHES     |");
			System.out.println("|------------------------|");
			System.out.println("");
		}


		baseString = this.findBaseString();
		if(baseString != null){
			char[] tempCharArr = baseString.toCharArray();
			baseChars = ArrayUtils.toObject(tempCharArr);
			if(output){
				System.out.println("Basestring: ");
				System.out.println(baseString);
				System.out.println("");
			}

			for (String testString : this.stringList) {
				if(!baseString.equals(testString)){
					char[] charArray = testString.toCharArray();
					Character[] testCharArray = ArrayUtils.toObject(charArray);

					for(int i = 0; i < baseChars.length; i++){
						if(testCharArray.length-1 >= i){
							if(baseChars[i].equals(testCharArray[i])){
								if(this.characterList.isEmpty() || this.characterList.size() <= i){
									this.characterList.add(new CharacterEx(baseChars[i], CharacterEx.getTypeForChar(baseChars[i]), Boolean.FALSE));
								} else if(!(this.characterList.get(i).getC() == baseChars[i].charValue())){
									this.characterList.set(i, new CharacterEx(this.placeholder, CharacterEx.getTypeForChars(this.characterList.get(i), baseChars[i]), Boolean.TRUE));
								}
								if(output){
									System.out.print(new CharacterEx(baseChars[i], StringEx.isInteger(baseChars[i].toString()) ? CharacterEx.TYPE_INTEGER : CharacterEx.TYPE_STRING));
								}
							} else {
								if(this.characterList.isEmpty() || this.characterList.size() <= i){
									this.characterList.add(new CharacterEx(this.placeholder, CharacterEx.getTypeForChars(baseChars[i], testCharArray[i]), Boolean.TRUE));
								} else if(!(this.characterList.get(i).getC() == this.placeholder)){
									this.characterList.set(i, new CharacterEx(this.placeholder, CharacterEx.getTypeForChars(this.characterList.get(i), testCharArray[i]), Boolean.TRUE));
								}
								if(output){
									System.out.print(new CharacterEx(this.placeholder));
								}
							}
						}
					}
					if(output){
						System.out.println("");
					}
				}
			}
		}

		this.parseCharacters(output);
	}

	/**
	 * Find the base string. It is the longest string.
	 *
	 * @return baseString
	 */
	private String findBaseString() {
		String baseString = null;
		for (String string : stringList) {
			if(baseString == null){
				baseString = string;
			} else if(baseString.length() < string.length()){
				baseString = string;
			}
		}
		return baseString;
	}

	/**
	 * @return the containsPlaceholder
	 */
	public boolean containsPlaceholder() {
		return this.containsPlaceholder;
	}

	/**
	 * @return the containsIntPlaceholder
	 */
	public boolean containsIntPlaceholder() {
		return this.containsIntPlaceholder;
	}

	/**
	 * @param containsPlaceholder the containsPlaceholder to set
	 */
	public void setContainsPlaceholder(boolean containsPlaceholder) {
		this.containsPlaceholder = containsPlaceholder;
	}

	/**
	 * @return the isInt
	 */
	public boolean isInt() {
		return isInt;
	}

	/**
	 * @param isInt the isInt to set
	 */
	public void setInt(boolean isInt) {
		this.isInt = isInt;
	}

	@Override
	public Iterator<CharacterEx> iterator() {
		return this.characterList.iterator();
	}

	public void setPlaceholder(final char placeholder) {
		this.placeholder = placeholder;
	}

	public ArrayList<SubString> getSubStrings(final String splitter) {
		ArrayList<SubString> tempResultList = new ArrayList<SubString>();
		ArrayList<ArrayList<CharacterEx>> tempList = new ArrayList<ArrayList<CharacterEx>>();
		ArrayList<String> tempStrList = new ArrayList<String>();
		String tempString = "";
		for (CharacterEx characterEx : characterList) {
			tempString += characterEx.toString();
		}
		String splitterQuoted = Pattern.quote(splitter);
		tempStrList = new ArrayList<String>(Arrays.asList(tempString.split(splitterQuoted)));

		int startIndex = 0;
		ArrayList<CharacterEx> tempCharList = null;
		for (String string : tempStrList) {
			tempCharList = new ArrayList<CharacterEx>();
			for(int i = startIndex; i < (startIndex + string.length()); i++){
				tempCharList.add(this.characterList.get(i));
			}
			tempList.add(tempCharList);
			startIndex = startIndex + (string.length() + splitter.length());
		}

		StringEx temp = null;
		for (ArrayList<CharacterEx> characterList : tempList) {
			temp = new StringEx();
			temp.setCharacters(characterList);
			temp.parseCharacters(Boolean.FALSE);
			tempResultList.add(new SubString(temp));
		}

		return tempResultList;
	}

	private void parseCharacters(boolean output) {
		this.containsPlaceholder = StringEx.containsPlaceholder(this.characterList);
		this.containsIntPlaceholder = StringEx.containsIntPlaceholder(this.characterList);
		this.isInt = StringEx.isInt(this.characterList);

		if(this.containsIntPlaceholder){
			this.placeholderIntPositions = StringEx.getIntPositions(this.characterList, Boolean.TRUE);

			if(output){
				System.out.println("");
				System.out.println("|------------------------|");
				System.out.println("|  INTEGER POSITION (PH) |");
				System.out.println("|------------------------|");
				System.out.println("");
				for (Point point : this.placeholderIntPositions) {
					System.out.println("Ints @ pos (" + point.x + " / "+ point.y + "):");
					int count = point.y - point.x + 1;
					for(int i = 0; i < count; i++){
						System.out.print(this.characterList.get(point.x + i));
					}
					System.out.println("");
				}
			}
		}
		this.intPositions = StringEx.getIntPositions(this.characterList, Boolean.FALSE);
		if(output){
			System.out.println("");
			System.out.println("|------------------------|");
			System.out.println("|    INTEGER POSITION    |");
			System.out.println("|------------------------|");
			System.out.println("");
			for (Point point : this.intPositions) {
				System.out.println("Ints @ pos (" + point.x + " / "+ point.y + "):");
				int count = point.y - point.x + 1;
				for(int i = 0; i < count; i++){
					System.out.print(this.characterList.get(point.x + i));
				}
				System.out.println("");
			}
		}

		if(output){
			System.out.println("");
			System.out.println("|------------------------|");
			System.out.println("|         RESULT         |");
			System.out.println("|------------------------|");
			System.out.println("");
			for (CharacterEx character : this) {
				System.out.print(character);
			}
			System.out.println("");
			for (CharacterEx character : this) {
				System.out.print(character.getType() == CharacterEx.TYPE_INTEGER ? "^" : " ");
			}
			System.out.println("");
			System.out.println("|------------------------|");
			System.out.println("|         GENERAL        |");
			System.out.println("|------------------------|");
			System.out.println("");
			System.out.println("Contains Placeholder: " + this.containsPlaceholder());
			System.out.println("Contains Integer Placeholder: " + this.containsIntPlaceholder());
			System.out.println("Is int: " + this.isInt());
		}
	}

	private void setCharacters(ArrayList<CharacterEx> characterList) {
		this.characterList = characterList;
	}


	@Override
	public String toString() {
		String temp = "";
		for (CharacterEx characterEx : characterList) {
			temp += characterEx.toString();
		}
		return temp;
	}

}
