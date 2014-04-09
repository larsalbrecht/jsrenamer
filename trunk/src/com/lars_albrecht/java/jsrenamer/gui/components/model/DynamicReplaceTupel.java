/**
 * 
 */
package com.lars_albrecht.java.jsrenamer.gui.components.model;

import javax.swing.JTextField;

/**
 * @author lalbrecht
 * 
 */
public class DynamicReplaceTupel {

	private JTextField	patternField	= null;
	private JTextField	replaceField	= null;

	public DynamicReplaceTupel(final JTextField patternField, final JTextField replaceField) {
		this.patternField = patternField;
		this.replaceField = replaceField;
	}

	/**
	 * @return the patternField
	 */
	public final JTextField getPatternField() {
		return this.patternField;
	}

	/**
	 * @return the replaceField
	 */
	public final JTextField getReplaceField() {
		return this.replaceField;
	}

	/**
	 * @param patternField
	 *            the patternField to set
	 */
	public final void setPatternField(final JTextField patternField) {
		this.patternField = patternField;
	}

	/**
	 * @param replaceField
	 *            the replaceField to set
	 */
	public final void setReplaceField(final JTextField replaceField) {
		this.replaceField = replaceField;
	}

}
