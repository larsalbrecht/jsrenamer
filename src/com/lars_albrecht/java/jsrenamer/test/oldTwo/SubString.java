/**
 *
 */
package com.lars_albrecht.java.jsrenamer.test.oldTwo;

import com.lars_albrecht.java.jsrenamer.test.oldTwo.CharacterEx;
import com.lars_albrecht.java.jsrenamer.test.oldTwo.StringEx;

/**
 * @author lalbrecht
 *
 */
public class SubString {

	private StringEx subString = null;
	private char placeholder;
	private int stringPlaceholderCount = 0;
	private int stringNotPlaceholderCount = 0;
	private int stringLength = 0;
	private double percentOfPlaceholder = 0.0;
	private double percentOfReal = 0.0;

	private boolean isDummy = Boolean.FALSE;

	public SubString(StringEx subString) {
		this.subString = subString;

		this.countStringPlaceholder();
		this.countStrings();
		this.calcPercentOfPlaceholder();

		if(percentOfPlaceholder > 60.0){
			isDummy = Boolean.TRUE;
		}
	}

	private void calcPercentOfPlaceholder() {
		double ground = (this.stringPlaceholderCount + this.stringNotPlaceholderCount);
		double oneGround = 100/ground;
		this.percentOfPlaceholder = oneGround * this.stringPlaceholderCount;
		this.percentOfReal = oneGround * this.stringNotPlaceholderCount;
	}

	private void countStrings() {
		for (CharacterEx characterEx : subString) {
			if(!characterEx.isPlaceholder() && characterEx.getType() == CharacterEx.TYPE_STRING){
				this.stringNotPlaceholderCount++;
			}
		}
	}

	private void countStringPlaceholder() {
		for (CharacterEx characterEx : subString) {
			if(characterEx.isPlaceholder() && characterEx.getType() == CharacterEx.TYPE_STRING){
				this.stringPlaceholderCount++;
			}
		}
	}


	/**
	 * @return the subString
	 */
	public StringEx getSubString() {
		return subString;
	}

	/**
	 * @param subString the subString to set
	 */
	public void setSubString(StringEx subString) {
		this.subString = subString;
	}

	/**
	 * @return the placeholder
	 */
	public char getPlaceholder() {
		return placeholder;
	}

	/**
	 * @param placeholder the placeholder to set
	 */
	public void setPlaceholder(char placeholder) {
		this.placeholder = placeholder;
	}

	/**
	 * @return the stringPlaceholderCount
	 */
	public int getStringPlaceholderCount() {
		return stringPlaceholderCount;
	}

	/**
	 * @param stringPlaceholderCount the stringPlaceholderCount to set
	 */
	public void setStringPlaceholderCount(int stringPlaceholderCount) {
		this.stringPlaceholderCount = stringPlaceholderCount;
	}

	/**
	 * @return the stringNotPlaceholderCount
	 */
	public int getStringNotPlaceholderCount() {
		return stringNotPlaceholderCount;
	}

	/**
	 * @param stringNotPlaceholderCount the stringNotPlaceholderCount to set
	 */
	public void setStringNotPlaceholderCount(int stringNotPlaceholderCount) {
		this.stringNotPlaceholderCount = stringNotPlaceholderCount;
	}

	/**
	 * @return the stringLength
	 */
	public int getStringLength() {
		return stringLength;
	}

	/**
	 * @param stringLength the stringLength to set
	 */
	public void setStringLength(int stringLength) {
		this.stringLength = stringLength;
	}

	/**
	 * @return the percentOfPlaceholder
	 */
	public double getPercentOfPlaceholder() {
		return percentOfPlaceholder;
	}

	/**
	 * @param percentOfPlaceholder the percentOfPlaceholder to set
	 */
	public void setPercentOfPlaceholder(double percentOfPlaceholder) {
		this.percentOfPlaceholder = percentOfPlaceholder;
	}

	/**
	 * @return the percentOfReal
	 */
	public double getPercentOfReal() {
		return percentOfReal;
	}

	/**
	 * @param percentOfReal the percentOfReal to set
	 */
	public void setPercentOfReal(double percentOfReal) {
		this.percentOfReal = percentOfReal;
	}

	/**
	 * @return the isTemplate
	 */
	public boolean isDummy() {
		return isDummy;
	}

	/**
	 * @param isDummy the isDummy to set
	 */
	public void setDummy(boolean isDummy) {
		this.isDummy = isDummy;
	}

	@Override
	public String toString() {
		return this.subString.toString();
	}

}
