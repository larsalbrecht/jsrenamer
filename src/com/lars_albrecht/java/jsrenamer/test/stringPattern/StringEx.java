/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test.stringPattern;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author lalbrecht
 *
 */
public class StringEx implements Iterable<CharacterEx> {

	private ArrayList<CharacterEx> stringList = null;

	/**
	 * Has StringEx placeholders?
	 */
	private boolean containsPlaceholder = Boolean.FALSE;

	/**
	 * Is complete StringEx int?
	 */
	private boolean isInt = Boolean.FALSE;

	private static boolean containsPlaceholder(final ArrayList<CharacterEx> stringList){
		for (CharacterEx characterEx : stringList) {
			if(characterEx.isPlaceholder()){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	private static boolean isInt(final ArrayList<CharacterEx> stringList){
		for (CharacterEx characterEx : stringList) {
			if(characterEx.getType() != CharacterEx.TYPE_INTEGER){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	public StringEx(final ArrayList<CharacterEx> stringList) {
		this.stringList = new ArrayList<CharacterEx>();

		this.containsPlaceholder = StringEx.containsPlaceholder(this.stringList);
		this.isInt = StringEx.isInt(this.stringList);
	}

	/**
	 * @return the containsPlaceholder
	 */
	public boolean isContainsPlaceholder() {
		return containsPlaceholder;
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
		return this.stringList.iterator();
	}



}
