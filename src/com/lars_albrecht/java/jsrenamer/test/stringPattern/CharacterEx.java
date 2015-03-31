/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test.stringPattern;

/**
 * @author lalbrecht
 *
 */
public class CharacterEx {

	public static final int TYPE_UNKNOWN = -1;
	public static final int TYPE_STRING = 0;
	public static final int TYPE_INTEGER = 1;


	private char c;
	private int type = CharacterEx.TYPE_UNKNOWN;
	private boolean isPlaceholder = Boolean.FALSE;

	@SuppressWarnings("unused")
	private CharacterEx(){}

	public CharacterEx(Character c) {
		this.c = c;
	}

	public CharacterEx(Character c, int type) {
		this.c = c;
		this.type = type;
	}

	public CharacterEx(Character c, int type, boolean isPlaceholder) {
		this.c = c;
		this.type = type;
		this.isPlaceholder = isPlaceholder;
	}

	/**
	 * @return the c
	 */
	public char getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(char c) {
		this.c = c;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the isPlaceholder
	 */
	public boolean isPlaceholder() {
		return isPlaceholder;
	}

	/**
	 * @param isPlaceholder the isPlaceholder to set
	 */
	public void setPlaceholder(boolean isPlaceholder) {
		this.isPlaceholder = isPlaceholder;
	}

	@Override
	public String toString() {
		return Character.toString(this.c);
	}

	public static int getTypeForChar(final char c){
		String cS = Character.toString(c);
		if(PatternTest.isInteger(cS)){
			return CharacterEx.TYPE_INTEGER;
		}
		return CharacterEx.TYPE_STRING;
	}

	public static int getTypeForChars(final CharacterEx c1, final char c2){
		String cS1 = Character.toString(c1.getC());
		String cS2 = Character.toString(c2);
		if(c1.isPlaceholder()){
			if(PatternTest.isInteger(cS2)){
				return CharacterEx.TYPE_INTEGER;
			} else {
				return CharacterEx.TYPE_STRING;
			}
		} else {
			return CharacterEx.getTypeForChars(cS1.charAt(0), cS2.charAt(0));
		}
	}

	public static int getTypeForChars(final char c1, final char c2){
		String cS1 = Character.toString(c1);
		String cS2 = Character.toString(c2);
		if(PatternTest.isInteger(cS1) && PatternTest.isInteger(cS2)){
			return CharacterEx.TYPE_INTEGER;
		}
		return CharacterEx.TYPE_STRING;
	}

}
