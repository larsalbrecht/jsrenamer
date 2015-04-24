/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test;

import java.awt.Point;
import java.util.ArrayList;

import com.lars_albrecht.java.jsrenamer.test.CharacterEx;

/**
 * @author lalbrecht
 *
 */
public class StringEx {

	public static final int TYPE_UNKNOWN = -1; // unknown
	public static final int TYPE_HARDSTRING = 0; // for hard strings
	public static final int TYPE_STRINGINTEGER = 1; // for hard strings with
													// integer to parse
	public static final int TYPE_INTEGER = 2; // for integer to parse
	public static final int TYPE_STRING = 3; // for dynamic strings
	public static final int TYPE_DUMMY = 4; // dummy (will be removed later)

	private String string = null;

	// TODO fill only when needed
	private ArrayList<CharacterEx> characterList = null;

	private int type = StringEx.TYPE_UNKNOWN;

	private int listIndex = -1;

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

	public StringEx(final String string, final Integer type, final int listIndex) {
		this.string = string;
		this.listIndex = listIndex;
		if (type > StringEx.TYPE_DUMMY || type < StringEx.TYPE_UNKNOWN) {
			this.type = StringEx.TYPE_UNKNOWN;
		} else {
			this.type = type;
		}
	}

	public StringEx(final String string, final Integer type,
			final int listIndex, ArrayList<CharacterEx> characterList) {
		this.string = string;
		this.listIndex = listIndex;
		this.characterList = characterList;
		if (type > StringEx.TYPE_DUMMY && type <= StringEx.TYPE_UNKNOWN) {
			this.type = StringEx.TYPE_UNKNOWN;
		} else {
			this.type = type;
		}

		if (this.characterList != null) {
			this.parseCharacters(false);
		}
	}

	/**
	 * Parse characters and define this type.
	 *
	 * @param output
	 */
	private void parseCharacters(boolean output) {
		this.containsPlaceholder = StringEx
				.containsPlaceholder(this.characterList);
		this.containsIntPlaceholder = StringEx
				.containsIntPlaceholder(this.characterList);
		this.isInt = StringEx.isInt(this.characterList);

		if (this.containsIntPlaceholder) {
			this.placeholderIntPositions = StringEx.getIntPositions(
					this.characterList, Boolean.TRUE);

			if (output) {
				System.out.println("");
				System.out.println("|------------------------|");
				System.out.println("|  INTEGER POSITION (PH) |");
				System.out.println("|------------------------|");
				System.out.println("");
				for (Point point : this.placeholderIntPositions) {
					System.out.println("Ints @ pos (" + point.x + " / "
							+ point.y + "):");
					int count = point.y - point.x + 1;
					for (int i = 0; i < count; i++) {
						System.out.print(this.characterList.get(point.x + i));
					}
					System.out.println("");
				}
			}
		}
		this.intPositions = StringEx.getIntPositions(this.characterList,
				Boolean.FALSE);
		if (output) {
			System.out.println("");
			System.out.println("|------------------------|");
			System.out.println("|    INTEGER POSITION    |");
			System.out.println("|------------------------|");
			System.out.println("");
			for (Point point : this.intPositions) {
				System.out.println("Ints @ pos (" + point.x + " / " + point.y
						+ "):");
				int count = point.y - point.x + 1;
				for (int i = 0; i < count; i++) {
					System.out.print(this.characterList.get(point.x + i));
				}
				System.out.println("");
			}
		}

		if (output) {
			System.out.println("");
			System.out.println("|------------------------|");
			System.out.println("|         RESULT         |");
			System.out.println("|------------------------|");
			System.out.println("");
			for (CharacterEx character : this.characterList) {
				System.out.print(character);
			}
			System.out.println("");
			for (CharacterEx character : this.characterList) {
				System.out
						.print(character.getType() == CharacterEx.TYPE_INTEGER ? "^"
								: " ");
			}
			System.out.println("");
			System.out.println("|------------------------|");
			System.out.println("|         GENERAL        |");
			System.out.println("|------------------------|");
			System.out.println("");
			System.out.println("Contains Placeholder: "
					+ this.containsPlaceholder);
			System.out.println("Contains Integer Placeholder: "
					+ this.containsIntPlaceholder);
			System.out.println("Is int: " + this.isInt);
		}

		if (isInt) {
			this.type = StringEx.TYPE_INTEGER;
		} else if (containsIntPlaceholder && containsPlaceholder && !isInt) {
			this.type = StringEx.TYPE_STRINGINTEGER;
		} else if (!containsIntPlaceholder && containsPlaceholder && !isInt) {
			this.type = StringEx.TYPE_STRING;

			if (this.string == null && this.characterList != null
					&& characterList.size() > 0) {
				this.string = "";
				for (CharacterEx characterEx : characterList) {
					this.string += characterEx.getC();
				}
			}
		}

	}

	@Override
	public String toString() {
		String result = this.string;
		if (characterList != null) {
			result = "";
			for (CharacterEx characterEx : characterList) {
				result += characterEx.toString();
			}
		}

		return result;
	}

	private static ArrayList<Point> getIntPositions(
			final ArrayList<CharacterEx> characterList,
			final boolean onlyPlaceholder) {
		ArrayList<Point> tempList = new ArrayList<Point>();
		int start = -1;
		int count = 0;
		for (CharacterEx characterEx : characterList) {
			if ((onlyPlaceholder && characterEx.isPlaceholder() && characterEx
					.getType() == CharacterEx.TYPE_INTEGER)
					|| (!onlyPlaceholder && characterEx.getType() == CharacterEx.TYPE_INTEGER)) {
				if (start == -1) {
					count = 0;
					start = characterList.indexOf(characterEx);
				}
				count++;
				if(characterList.indexOf(characterEx) == characterList.size()-1){ // if last element
					tempList.add(new Point(start, start + count - 1));
				}
			} else {
				if (count > 0) {
					tempList.add(new Point(start, start + count - 1));
				}
				count = 0;
				start = -1;
			}
		}
		return tempList;
	}

	private static boolean containsIntPlaceholder(
			final ArrayList<CharacterEx> characterList) {
		for (CharacterEx characterEx : characterList) {
			if (characterEx.isPlaceholder()
					&& characterEx.getType() == CharacterEx.TYPE_INTEGER) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	private static boolean containsPlaceholder(
			final ArrayList<CharacterEx> characterList) {
		for (CharacterEx characterEx : characterList) {
			if (characterEx.isPlaceholder()) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	private static boolean isInt(final ArrayList<CharacterEx> characterList) {
		for (CharacterEx characterEx : characterList) {
			if (characterEx.getType() != CharacterEx.TYPE_INTEGER) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * @return the string
	 */
	public String getString() {
		return string;
	}

	/**
	 * @param string
	 *            the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the listIndex
	 */
	public int getListIndex() {
		return listIndex;
	}

	/**
	 * @param listIndex
	 *            the listIndex to set
	 */
	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}

	/**
	 * @return the placeholderIntPositions
	 */
	public final ArrayList<Point> getPlaceholderIntPositions() {
		return placeholderIntPositions;
	}

}
