/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

/**
 * @author lalbrecht
 *
 */
public class CharacterEx {

	public static final int	CHARACTER_TYPE_UNKNOWN	= -1;
	public static final int	CHARACTER_TYPE_STRING	= 0;
	public static final int	CHARACTER_TYPE_INTEGER	= 1;

	private Character		character				= null;
	private int				compareDirection;
	private int				position = -1;
	private int				characterType			= CharacterEx.CHARACTER_TYPE_UNKNOWN;

	public CharacterEx(final Character character, final int compareDirection, final int position) {
		this.character = character;
		this.compareDirection = compareDirection;
		this.position = position;
	}

	public CharacterEx(final Character character, final int compareDirection, final int position, final int characterType) {
		this.character = character;
		this.compareDirection = compareDirection;
		this.position = position;
		this.characterType = characterType;
	}

	/******************************** GETTER / SETTER ********************************/

	/**
	 * @return the character
	 */
	public final Character getCharacter() {
		return character;
	}

	/**
	 * @param character
	 *            the character to set
	 */
	public final void setCharacter(Character character) {
		this.character = character;
	}

	/**
	 * @return the compareDirection
	 */
	public final int getCompareDirection() {
		return compareDirection;
	}

	/**
	 * @param compareDirection
	 *            the compareDirection to set
	 */
	public final void setCompareDirection(int compareDirection) {
		this.compareDirection = compareDirection;
	}

	/**
	 * @return the position
	 */
	public final int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public final void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the characterType
	 */
	public final int getCharacterType() {
		return characterType;
	}

	/**
	 * @param characterType
	 *            the characterType to set
	 */
	public final void setCharacterType(int characterType) {
		this.characterType = characterType;
	}




	/******************************** OVERRIDE ********************************/

	@Override
	public String toString() {
		return (this.character != null ? this.character.toString() : null);
	}

	/******************************** STATIC ********************************/

	public static int getCharacterType(Character baseChar, Character testChar) {
		if (Utilities.isInteger(baseChar.toString()) && Utilities.isInteger(testChar.toString())) {
			return CharacterEx.CHARACTER_TYPE_INTEGER;
		}
		return CharacterEx.CHARACTER_TYPE_STRING;
	}

	public static int getCharacterType(Character testChar, Character testChar2, CharacterEx baseChar) {
		if(Utilities.isInteger(testChar.toString()) && Utilities.isInteger(testChar2.toString()) && baseChar.getCharacterType() == CHARACTER_TYPE_INTEGER) {
			return CharacterEx.CHARACTER_TYPE_INTEGER;
		}
		return CharacterEx.CHARACTER_TYPE_UNKNOWN;
	}

}
